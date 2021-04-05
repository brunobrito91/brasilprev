package com.bruno.abreu.brasilprev.controller;

import com.bruno.abreu.brasilprev.exception.CustomerNotFoundException;
import com.bruno.abreu.brasilprev.exception.error.CustomerError;
import com.bruno.abreu.brasilprev.model.Address;
import com.bruno.abreu.brasilprev.model.Customer;
import com.bruno.abreu.brasilprev.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private static List<Customer> customers = new ArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        //GIVEN
        customers.add(Customer.builder()
                .id(1L)
                .name("Customer 1")
                .cpf("11122233344")
                .address(Address.builder()
                        .id(1L)
                        .street("Street 1")
                        .number("1")
                        .district("District 1")
                        .city("City 1")
                        .state("State 1")
                        .country("Country 1")
                        .build())
                .build());
        customers.add(Customer.builder()
                .id(2L)
                .name("Customer 2")
                .cpf("55566677788")
                .address(Address.builder()
                        .id(2L)
                        .street("Street 2")
                        .number("2")
                        .district("District 2")
                        .city("City 2")
                        .state("State 2")
                        .country("Country 2")
                        .build())
                .build());
    }

    @Test
    void findAllCustomersShouldReturnStatusOk() throws Exception {

        //WHEN
        when(customerService.findAll()).thenReturn(new ArrayList<>());

        //THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void findAllCustomersShouldReturn2Customers() throws Exception {

        //WHEN
        when(customerService.findAll()).thenReturn(customers);

        //THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(customers.size())));
    }

    @Test
    void findAllCustomersShouldReturnJsonResponseBody() throws Exception {

        //WHEN
        when(customerService.findAll()).thenReturn(customers);

        //THEN
        String FIND_ALL_CUSTOMER_RESPONSE_BODY = "[{\"id\":1,\"name\":\"Customer 1\",\"cpf\":\"11122233344\",\"address\":{\"id\":1,\"street\":\"Street 1\",\"number\":\"1\",\"district\":\"District 1\",\"city\":\"City 1\",\"state\":\"State 1\",\"country\":\"Country 1\"}},{\"id\":2,\"name\":\"Customer 2\",\"cpf\":\"55566677788\",\"address\":{\"id\":2,\"street\":\"Street 2\",\"number\":\"2\",\"district\":\"District 2\",\"city\":\"City 2\",\"state\":\"State 2\",\"country\":\"Country 2\"}}]";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(FIND_ALL_CUSTOMER_RESPONSE_BODY));
    }

    @Test
    void findByIdCustomersShouldReturnCorrectId() throws Exception {

        //WHEN
        Long id = 1L;
        Customer customer1 = customers.get(0);
        when(customerService.read(id)).thenReturn(customer1);

        //THEN
        String contentAsString = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Customer customer = objectMapper.readValue(contentAsString, Customer.class);
        assertEquals(id, customer.getId());
        assertEquals(customer1, customer);

        //WHEN
        id = 2L;
        Customer customer2 = customers.get(1);
        when(customerService.read(id)).thenReturn(customer2);

        //THEN
        contentAsString = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        customer = objectMapper.readValue(contentAsString, Customer.class);
        assertEquals(id, customer.getId());
        assertEquals(customer2, customer);
    }

    @Test
    void findByIdCustomersShouldReturnNotFound() throws Exception {
        //WHEN
        Long id = 3L;
        when(customerService.read(id)).thenThrow(new CustomerNotFoundException());

        //THEN
        String contentAsString = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CustomerError customerError = objectMapper.readValue(contentAsString, CustomerError.class);
        assertEquals("Customer not found!", customerError.getErrorMessage());
        assertNotNull(customerError.getTimestamp());
    }

    @Test
    void createCustomersShouldReturnCreated() throws Exception {
        Customer customer = customers.get(0);
        when(customerService.create(customer)).thenReturn(customer);

        String content = objectMapper.writeValueAsString(customer);

        String CREATE_CUSTOMER_RESPONSE_BODY = "{\"id\":1,\"name\":\"Customer 1\",\"cpf\":\"11122233344\",\"address\":{\"id\":1,\"street\":\"Street 1\",\"number\":\"1\",\"district\":\"District 1\",\"city\":\"City 1\",\"state\":\"State 1\",\"country\":\"Country 1\"}}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/customers")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/customers/1"))
                .andExpect(MockMvcResultMatchers.content().json(CREATE_CUSTOMER_RESPONSE_BODY));
    }

    @Test
    void updateCustomersShouldReturnOk() throws Exception {
        Customer customer = customers.get(0);
        when(customerService.update(customer)).thenReturn(customer);

        String content = objectMapper.writeValueAsString(customer);

        String UPDATE_CUSTOMER_RESPONSE_BODY = "{\"id\":1,\"name\":\"Customer 1\",\"cpf\":\"11122233344\",\"address\":{\"id\":1,\"street\":\"Street 1\",\"number\":\"1\",\"district\":\"District 1\",\"city\":\"City 1\",\"state\":\"State 1\",\"country\":\"Country 1\"}}";
        Long id = 1L;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/api/customers/{id}", id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(UPDATE_CUSTOMER_RESPONSE_BODY));
    }

    @Test
    void deleteCustomersShouldReturnNoContent() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/customers/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomersShouldReturnNotFound() throws Exception {
        doThrow(new CustomerNotFoundException()).when(customerService).delete(1L);
        String contentAsString = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/customers/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CustomerError customerError = objectMapper.readValue(contentAsString, CustomerError.class);
        assertEquals("Customer not found!", customerError.getErrorMessage());
        assertNotNull(customerError.getTimestamp());
    }


}
