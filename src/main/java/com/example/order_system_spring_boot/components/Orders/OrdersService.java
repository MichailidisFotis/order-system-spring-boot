package com.example.order_system_spring_boot.components.Orders;

import com.example.order_system_spring_boot.components.Products.Product;
import com.example.order_system_spring_boot.helpers.DatesHelper;
import com.example.order_system_spring_boot.helpers.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OrdersService implements OrdersRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    DatesHelper datesHelper = new DatesHelper();


    @Override
    public ResponseEntity<Object> getAll() {
//        String query =  "SELECT * FROM ORDERS";
//
//        List<Order> orders = jdbcTemplate.query(query, new BeanPropertyRowMapper<Order>(Order.class));
//
//        List<Order> ordersWithProducts =  new ArrayList<>();
//
//        for(Order order : orders) {
//
//            String getProductsQuery = "SELECT * from orders_products join products on products.id = orders_products.product_id where order_id=?";
//
//            jdbcTemplate.query(getProductsQuery, new )
//
//
//            ordersWithProducts.add(order);
//
//
//        }
//
//
        return null;

    }


    @Override
    public ResponseEntity<Object> getByCustomer(String customer_id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getByStatus(String status) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> create(Order order) {

        double price=0.0;
        String dateNow = datesHelper.getDateNowTS();
        UUID randomUuid = UUID.randomUUID();

        for (OrdersProducts ordersProduct : order.getProducts()) {
            int quantity = ordersProduct.getQuantity();
            String product_id = ordersProduct.getProduct_id();

            List<Product> ordersProducts = jdbcTemplate.query("SELECT * FROM PRODUCTS WHERE ID=?",
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement ps) throws SQLException {
                            ps.setString(1, product_id);
                        }
                    },
                    new BeanPropertyRowMapper<Product>(Product.class));


            if (ordersProducts.isEmpty())
                return ResponseHandler.responseBuilder("One of the products is invalid", HttpStatus.NOT_FOUND, null, false);

            Product productInDB=ordersProducts.get(0);

            price+=(productInDB.getPrice()*quantity);

        }


        order.setId(randomUuid.toString());
        order.setDateCreated(dateNow);

        String insertOrderQuery = "INSERT INTO orders(id, price , dateCreated,customer_id, payment_method) VALUES (?,?,?,?,?)";


        jdbcTemplate.update(insertOrderQuery , order.getId() , price , order.getDateCreated(),order.getCustomer_id() ,order.getPayment_method());

        for (OrdersProducts ordersProduct : order.getProducts()) {
            jdbcTemplate.update("INSERT INTO ORDERS_PRODUCTS(order_id, product_id , quantity) VALUES (?,?,?)",
                    order.getId(),
                    ordersProduct.getProduct_id(),
                    ordersProduct.getQuantity()
            );
        }

        return ResponseHandler.responseBuilder("Order submitted" , HttpStatus.CREATED , order , true);

    }

    @Override
    public ResponseEntity<Object> changeStatus(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getOrderProducts(String id) {
        return null;
    }
}
