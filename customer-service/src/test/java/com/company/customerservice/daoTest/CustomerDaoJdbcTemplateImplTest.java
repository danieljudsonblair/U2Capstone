package com.company.customerservice.daoTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerDaoJdbcTemplateImplTest {

    @Autowired
    CustomerDao customerDao;

    @Before
    public  void setUp(){
        List<Customer> customerList = customerDao.getAllCustomers();
        for (Customer customer: customerList){
            customerDao.deleteCustomer(customer.getCustomerId());
        }
    }

    @Test
    public void addGetDeleteCustomer(){
        Customer customer = new Customer();
        customer.setFirstName("Darrell");
        customer.setLastName("Reeves");
        customer.setStreet("123 Main st");
        customer.setCity("Charlotte");
        customer.setZip("28211");
        customer.setEmail("dpr@gmail.com");
        customer.setPhone("704 555 2345");

        customerDao.createCustomer(customer);

        Customer customer1 = customerDao.getCustomer(customer.getCustomerId());
        assertEquals(customer1, customer);

        customerDao.deleteCustomer(customer.getCustomerId());
        customer1 = customerDao.getCustomer(customer.getCustomerId());
        assertNull(customer1);
    }

    @Test
    public void updateCustomer(){
        Customer customer = new Customer();
        customer.setFirstName("Darrell");
        customer.setLastName("Reeves");
        customer.setStreet("123 Main st");
        customer.setCity("Charlotte");
        customer.setZip("28211");
        customer.setEmail("dpr@gmail.com");
        customer.setPhone("704 555 2345");

        customerDao.createCustomer(customer);

        customer.setFirstName("Darrell");
        customer.setLastName("Reeves");
        customer.setStreet("124 Main st");
        customer.setCity("Charlotte");
        customer.setZip("28211");
        customer.setEmail("dpr12@gmail.com");
        customer.setPhone("704 555 2345");

        customerDao.updateCustomer(customer);

        Customer customer1 = customerDao.getCustomer(customer.getCustomerId());
        assertEquals(customer1, customer);
    }

    @Test
    public void getAllCustomers(){
        Customer customer = new Customer();
        customer.setFirstName("Darrell");
        customer.setLastName("Reeves");
        customer.setStreet("123 Main st");
        customer.setCity("Charlotte");
        customer.setZip("28211");
        customer.setEmail("dpr@gmail.com");
        customer.setPhone("704 555 2345");

        customerDao.createCustomer(customer);

        Customer customer1 = new Customer();
        customer1.setFirstName("Pat");
        customer1.setLastName("Reeves");
        customer1.setStreet("124 Main st");
        customer1.setCity("Charlotte");
        customer1.setZip("28211");
        customer1.setEmail("dpr12@gmail.com");
        customer1.setPhone("704 555 2345");

        customerDao.createCustomer(customer1);

        List<Customer> customerList = customerDao.getAllCustomers();
        assertEquals(2, customerList.size());
    }

}
