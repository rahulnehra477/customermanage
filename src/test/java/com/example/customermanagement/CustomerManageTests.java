/*
package com.example.customermanagement;

import com.example.customermanagement.Controller.CustomerManageController;
import com.example.customermanagement.Dao.CustomerRepository;
import com.example.customermanagement.Dao.exception.DAOException;
import com.example.customermanagement.Model.Customer;
import com.example.customermanagement.Services.ICustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@WebFluxTest(controllers = CustomerManageController.class)
class CustomerManageTests {

	@Test
	void contextLoads() {
	}

    @Autowired
    WebTestClient webTestClient;

	@MockBean
    ICustomerService iCustomerService;

	@MockBean
    CustomerRepository customerRepository;



    @Test
    public void mono(){
        Customer customer = new Customer();
        customer.setFirstName("rahul");
        customer.setLastName("nehra");
        customer.setUserName("rnehra");
        customer.setPassword("7632873874gr");
        Integer expectedValue = new Integer(1);

        webTestClient.post()
                .uri( "/create/" )
                .body( BodyInserters.fromObject( customer) )
                .exchange().expectStatus().isCreated();

    }

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

    @Test
    public void test5DeleteGithubRepository() {
        webTestClient.delete()
                .uri("/customer/delete/{customerId}", "RNEHRA926655")
                .exchange()
                .expectStatus().isOk();
    }
}
*/
