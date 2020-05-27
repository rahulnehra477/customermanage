package com.example.customermanagement.Controller;

import com.example.customermanagement.Dao.exception.DAOException;
import com.example.customermanagement.Model.Account;
import com.example.customermanagement.Model.AccountDTO;
import com.example.customermanagement.Model.Customer;
import com.example.customermanagement.Services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/customer/")
public class CustomerManageController {

    @Autowired
    ICustomerService iCustomerService;


    @PostMapping(value = "/create")
    Mono<ResponseEntity<Mono<Account>>> createCustomer(
            @Valid @RequestBody Customer customer) {
        return iCustomerService.createCustomer(customer).map(custo->{
            AccountDTO accountDTO=new AccountDTO();
            accountDTO.setCustomerId(custo.getCustomerId());
            accountDTO.setUserName(custo.getUserName());
            //save customer
            return iCustomerService.createAccountRest(accountDTO);
        }).map(account -> new ResponseEntity<>(account,HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }

    @GetMapping(value = "/getcustomer/")
    Mono<ResponseEntity<Customer>> getCustomerByUserName(@RequestParam(required = true, value = "username") String username) throws DAOException {
        return iCustomerService.getCustomerByUserName(username).map(customer -> new ResponseEntity<>(customer,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(value = "/getcustomer/{customerId}")
    Mono<ResponseEntity<Customer>> getCustomerByCustomerId(@PathVariable(required = true) String customerId) throws DAOException {
        return iCustomerService.getCustomerByCustomerId(customerId).map(customer -> new ResponseEntity<>(customer,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping(value = "/update/{customerId}")
    Mono<ResponseEntity<Customer>> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) throws DAOException {
        return iCustomerService.getCustomerByCustomerId(customerId).flatMap(existing-> {
            existing.setAdddress(customer.getAdddress());
            existing.setPhoneNo(customer.getPhoneNo());
            existing.setEmailId(customer.getEmailId());
            return iCustomerService.createCustomer(existing);
        }).map(custo-> new ResponseEntity<>(custo,HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));

    }

    @DeleteMapping(value = "/delete/{customerId}")
    Mono<ResponseEntity<String>> deleteByCustomerId(@PathVariable String customerId) throws DAOException {
        return iCustomerService.deleteByCustomerId(customerId).map(customer -> new ResponseEntity<>("User deleted successfully",HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>("Customer does not exist",HttpStatus.NOT_FOUND));

    }

    /**
     * Upload file for users
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadcustomers")
    public Flux<Mono<Account>> uploadCustomers(MultipartFile file) {
        //List<Customer> custoList = iCustomerService.convertFileToListOfCustomers(file);
        return iCustomerService.uploadCustomers(iCustomerService.convertFileToListOfCustomers(file)).map(custo->{
            AccountDTO accountDTO=new AccountDTO();
            accountDTO.setCustomerId(custo.getCustomerId());
            accountDTO.setUserName(custo.getUserName());
            //save customer
            return iCustomerService.createAccountRest(accountDTO);
        });
        //return  this.createCustomer(custoList.get(0));
    }

}
