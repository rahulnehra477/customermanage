package com.example.customermanagement.Services;

import com.example.customermanagement.Dao.CustomerRepository;
import com.example.customermanagement.Dao.exception.DAOException;
import com.example.customermanagement.Model.Account;
import com.example.customermanagement.Model.AccountDTO;
import com.example.customermanagement.Model.Customer;

import com.example.customermanagement.Services.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    private WebClient.Builder webClientBuilder;

    private static Customer apply(String line) {
        String[] p = line.split(",");// a CSV has comma separated lines
        Customer item = new Customer();
        item.setFirstName(p[0]);//<-- this is the first column in the csv file
        return item;
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer)  {

        if(customer.getCustomerId()==null){
            customizeCustomer().apply(customer);
        }
        //save customer
        Mono<Customer> custo=customerRepository.save(customer);
        return custo;
    }

    @Override
    public Mono<Account> createAccountRest(AccountDTO accountDTO)  {
        Mono<Account> result = webClientBuilder.build().post()
                .uri( "http://account/account/create/" )
                .body( BodyInserters.fromObject( accountDTO ) )
                .retrieve().bodyToMono(Account.class);
        return result;
    }

    @Override
    public Mono<Customer> getCustomerByUserName(String username) throws DAOException {
        return customerRepository.findByUserName(username);
    }

    @Override
    public Mono<Customer> getCustomerByCustomerId(String customerId) throws DAOException {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public Mono<Object> deleteByCustomerId(String customerId) throws DAOException {
        return customerRepository.deleteByCustomerId(customerId);
    }

    @Override
    public Mono<Customer> updateCustomer(Customer existing, Customer customer) throws ServiceException {
        existing.setPhoneNo(customer.getPhoneNo());
        existing.setAdddress(customer.getAdddress());
        return customerRepository.save(existing);
    }

    @Override
    public Flux<Customer> uploadCustomers(List<Customer> customers)  {
        List<Customer> custo = customers.stream().map(customizeCustomer()).collect(Collectors.toList());
        return customerRepository.saveAll(custo);
    }


    private String generateCustomerId(String userName) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return userName.toUpperCase() + String.valueOf(timestamp.getTime()).substring(7);
    }

    /**
     * Convert a file to list of customers
     *
     * @param file
     * @return
     */
    @Override
    public List<Customer> convertFileToListOfCustomers(MultipartFile file) {
        try {
            List<Customer> customers = new ArrayList<>();
            List<Mono<Account>> accounts = new ArrayList<>();
            InputStream is = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // skip the header of the csv
            br.lines().skip(1).forEach(line->{
                String[] p = line.split(",");// a CSV has comma separated lines
                Customer customer = new Customer();
                Account account=new Account();
                customer.setFirstName(p[0]);
                customer.setLastName(p[1]);
                customer.setUserName(p[2]);
                customer.setPassword(p[3]);
                customer.setEmailId(p[4]);
                customer.setPhoneNo( p[5]);
                customers.add(customer);
            });
            br.close();
            return customers;
        } catch (IOException e) {
                e.printStackTrace();
        }
        return null;
    }

    private Function<String, Customer> mapToCustomer() {
        return new Function<String, Customer>() {
            @Override
            public Customer apply(String line) {
                String[] p = line.split(",");// a CSV has comma separated lines
                Customer customer = new Customer();
                customer.setFirstName(p[0]);
                customer.setLastName(p[1]);
                customer.setUserName(p[2]);
                customer.setPassword(p[3]);
                return customer;
            }
        };
    }

    private Function<Customer, Customer> customizeCustomer() {
        return new Function<Customer, Customer>() {
            @Override
            public Customer apply(Customer customer) {
                customer.setCustomerId(generateCustomerId(customer.getUserName()));
                //Password encryprion
                customer.setPassword(Base64.getEncoder().encodeToString(customer.getPassword().getBytes()));
                return customer;
            }
        };
    }
}
