package com.example.order_system_spring_boot.components.Customers;

import com.example.order_system_spring_boot.components.users.User;
import com.example.order_system_spring_boot.helpers.DatesHelper;
import com.example.order_system_spring_boot.helpers.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomerService implements CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    DatesHelper datesHelper =  new DatesHelper();



    @Override
    public ResponseEntity<Object> createCustomer(Customer customer) {

        String insertQuery = "insert into customers(id, firstname, surname, email , address, dateCreated, dateModified) values (?,?,?,?,?,?, null)";

        UUID randomUuid = UUID.randomUUID();

        customer.setId(randomUuid.toString());

        String dateCreated = datesHelper.getDateNowTS();
        customer.setDateCreated(dateCreated);

        jdbcTemplate.update(insertQuery, customer.getId(), customer.getFirstname(), customer.getSurname(), customer.getEmail(), customer.getAddress(), customer.getDateCreated());

        return ResponseHandler.responseBuilder("Customer Created!!", HttpStatus.CREATED, customer, true);
    }

    @Override
    public ResponseEntity<Object> getAll() {

        List<Customer> customers = jdbcTemplate.query("SELECT * FROM customers", new BeanPropertyRowMapper<Customer>(Customer.class));

        if (customers.isEmpty())
            return ResponseHandler.responseBuilder("No Customers Found!!!", HttpStatus.NOT_FOUND, new ArrayList<>(), true);

        return ResponseHandler.responseBuilder("Customers retrieved!!!", HttpStatus.OK, customers, true);
    }

    @Override
    public ResponseEntity<Object> getById(String id) {

        String query = "SELECT * FROM CUSTOMERS WHERE id=?";

        List<Customer> customers = jdbcTemplate.query("Select * from customers where id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Customer>(Customer.class));

        if (customers.isEmpty())
            return ResponseHandler.responseBuilder("Customer not found", HttpStatus.NOT_FOUND, customers, true);

        return ResponseHandler.responseBuilder("Customer retrieved", HttpStatus.OK, customers, true);

    }

    @Override
    public ResponseEntity<Object> delete(String id) {

        List<Customer> customers = jdbcTemplate.query("SELECT * FROM CUSTOMERS WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Customer>(Customer.class));

        if (customers.isEmpty())
            return ResponseHandler.responseBuilder("Customer not found", HttpStatus.NOT_FOUND, customers, true);


        jdbcTemplate.update("DELETE FROM CUSTOMERS WHERE id=? ", customers.get(0).getId());

        return ResponseHandler.responseBuilder("Customer Deleted", HttpStatus.OK, null, true);

    }

    @Override
    public ResponseEntity<Object> updateCustomer(Customer customer, String id) {


        List<Customer> customers = jdbcTemplate.query("SELECT * FROM CUSTOMERS WHERE id=?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Customer>(Customer.class)
        );

        if (customers.isEmpty())
            return ResponseHandler.responseBuilder("Customer Not Found", HttpStatus.NOT_FOUND, null, true);


        Customer customerInDb = customers.get(0);

        String firstname = (customer.getFirstname() != null && !customer.getFirstname().isBlank())
                ? customer.getFirstname()
                : customerInDb.getFirstname();

        String surname = (customer.getSurname() !=null && !customer.getSurname().isBlank())
                ? customer.getSurname()
                :customerInDb.getSurname();

        String address = (customer.getAddress() !=null && !customer.getAddress().isBlank())
                ?customer.getAddress()
                :customerInDb.getAddress();

        String email = (customer.getEmail()!=null && !customer.getEmail().isBlank())
                ?customer.getEmail()
                :customerInDb.getEmail();


        String query = "UPDATE CUSTOMERS SET firstname=?, surname=? , address=? , email=?, dateModified =? WHERE id=?";

        String dateModified = datesHelper.getDateNowTS();

        jdbcTemplate.update(query, firstname , surname , address , email, dateModified , id);

        return ResponseHandler.responseBuilder("Customer Updated!!!", HttpStatus.OK, customerInDb, true);


    }

}
