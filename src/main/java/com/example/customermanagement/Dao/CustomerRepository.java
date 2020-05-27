package com.example.customermanagement.Dao;

import com.example.customermanagement.Dao.exception.DAOException;
import com.example.customermanagement.Model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

    Mono<Customer> findByUserName(String username) throws DAOException;

    Mono<Customer> findByCustomerId(String customerId) throws DAOException;

    Mono<Object> deleteByCustomerId(String customerId) throws DAOException;
}
