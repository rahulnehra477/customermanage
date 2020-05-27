package com.example.customermanagement.Services;

import com.example.customermanagement.Dao.exception.DAOException;
import com.example.customermanagement.Model.Account;
import com.example.customermanagement.Model.AccountDTO;
import com.example.customermanagement.Model.Customer;
import com.example.customermanagement.Services.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICustomerService {
    Mono<Customer> createCustomer(Customer customer);

    Mono<Account> createAccountRest(AccountDTO accountDTO);

    Mono<Customer> getCustomerByUserName(String username) throws DAOException;

    Mono<Customer> getCustomerByCustomerId(String customerId) throws DAOException;

    Mono<Object> deleteByCustomerId(String customerId) throws DAOException;

    Mono<Customer> updateCustomer(Customer existing, Customer customer) throws ServiceException;

    List<Customer> convertFileToListOfCustomers(MultipartFile file);

    Flux<Customer> uploadCustomers(List<Customer> customers);
}
