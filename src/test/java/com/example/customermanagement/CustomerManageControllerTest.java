/*
package com.example.customermanagement;

import com.example.customermanagement.Dao.CustomerRepository;
import com.example.customermanagement.Dao.exception.DAOException;
import com.example.customermanagement.Model.Customer;
import com.example.customermanagement.Services.ICustomerService;
import org.assertj.core.api.Assertions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest
public class CustomerManageControllerTest {

    @Autowired
    WebTestClient  webTestClient;

    @MockBean
    ICustomerService iCustomerService;
    @MockBean
    CustomerRepository customerRepository;

    @Test
    public void getCustomerBycustomerId() throws DAOException {
        Customer customer = new Customer();
        customer.setFirstName("rahul");
        customer.setLastName("nehra");
        customer.setUserName("rnehra");
        customer.setPassword("7632873874gr");


        webTestClient.get().uri("/customer/getcustomer/{customerId}","RNEHRA926655")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

}
*/
