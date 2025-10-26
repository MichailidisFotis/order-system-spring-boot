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
        String query = "SELECT * FROM ORDERS";

        List<Order> orders = jdbcTemplate.query(query, new BeanPropertyRowMapper<Order>(Order.class));

        populateProducts(orders);


        return ResponseHandler.responseBuilder("Orders Retrieved", HttpStatus.OK, orders, true);

    }


    @Override
    public ResponseEntity<Object> getByCustomer(String customer_id) {
        List<Order> orders = jdbcTemplate.query("SELECT * FROM ORDERS WHERE CUSTOMER_ID=?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, customer_id);
                    }
                },
                new BeanPropertyRowMapper<Order>(Order.class));

        if (orders.isEmpty())
            return ResponseHandler.responseBuilder("No order found for this customer", HttpStatus.NOT_FOUND, null, true);

        populateProducts(orders);


        return ResponseHandler.responseBuilder("Orders Retrieved!!!", HttpStatus.OK, orders, true);

    }

    @Override
    public ResponseEntity<Object> getByState(String state) {

        String query = "SELECT * FROM ORDERS WHERE STATE=?";

        List<Order> orders = jdbcTemplate.query(query,
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, state);
                    }
                }, new BeanPropertyRowMapper<Order>(Order.class));


        populateProducts(orders);

        return ResponseHandler.responseBuilder("Orders Retrieved!!!", HttpStatus.OK, orders, true);

    }


    @Override
    public ResponseEntity<Object> delete(String id) {

        List<Order> orders = jdbcTemplate.query("SELECT * FROM orders WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Order>(Order.class));

        if (orders.isEmpty())
            return ResponseHandler.responseBuilder("Product not found", HttpStatus.NOT_FOUND, null, true);


        jdbcTemplate.update("DELETE FROM ORDERS_PRODUCTS WHERE order_id=?" , orders.get(0).getId());
        jdbcTemplate.update("DELETE FROM orders WHERE id=? ", orders.get(0).getId());

        return ResponseHandler.responseBuilder("Order Deleted", HttpStatus.OK, null, true);


    }


    @Override
    public ResponseEntity<Object> create(Order order) {

        double price = 0.0;
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

            Product productInDB = ordersProducts.get(0);

            price += (productInDB.getPrice() * quantity);

        }

        order.setId(randomUuid.toString());
        order.setDateCreated(dateNow);

        String insertOrderQuery = "INSERT INTO orders(id, price , dateCreated,customer_id, state, payment_method) VALUES (?,?,?,?,'created',?)";


        jdbcTemplate.update(insertOrderQuery, order.getId(), price, order.getDateCreated(), order.getCustomer_id(), order.getPayment_method());

        for (OrdersProducts ordersProduct : order.getProducts()) {
            jdbcTemplate.update("INSERT INTO ORDERS_PRODUCTS(order_id, product_id , quantity) VALUES (?,?,?)",
                    order.getId(),
                    ordersProduct.getProduct_id(),
                    ordersProduct.getQuantity()
            );
        }

        return ResponseHandler.responseBuilder("Order submitted", HttpStatus.CREATED, order, true);

    }

    @Override
    public ResponseEntity<Object> changeState(String id) {

        List<Order> orders = jdbcTemplate.query("SELECT * FROM orders WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Order>(Order.class));

        if (orders.isEmpty())
            return ResponseHandler.responseBuilder("Order not found", HttpStatus.NOT_FOUND, null, true);

        String dateModified =  datesHelper.getDateNowTS();

        jdbcTemplate.update("UPDATE ORDERS SET STATE ='completed' dateModified=? where id=?", id, dateModified);



        return ResponseHandler.responseBuilder("Order state changed" , HttpStatus.OK , orders.get(0), true);
    }

    @Override
    public ResponseEntity<Object> getById(String id) {

        List<Order> orders = jdbcTemplate.query("SELECT * FROM orders WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Order>(Order.class));

        if (orders.isEmpty())
            return ResponseHandler.responseBuilder("Order not found", HttpStatus.NOT_FOUND, null, true);


        for (Order o : orders) {
            String getProductsQuery = "SELECT products.id as product_id , orders_products.QUANTITY as quantity, products.description as description,\n" +
                    " products.price as price , products.brand as brand , category_id as category_id\n" +
                    " FROM orders_products\n" +
                    " INNER JOIN products on products.id  =  orders_products.PRODUCT_ID" +
                    " where order_id=?";

            List<OrdersProducts> ordersProducts = jdbcTemplate.query(getProductsQuery, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, o.getId());
                }
            }, new BeanPropertyRowMapper<OrdersProducts>(OrdersProducts.class));


            o.setProducts(ordersProducts);
        }

        return ResponseHandler.responseBuilder("Order Retrieved!!!", HttpStatus.OK, orders.get(0), true);

    }

    @Override
    public ResponseEntity<Object> getOrderProducts(String id) {


        List<Order> orders = jdbcTemplate.query("SELECT * FROM orders WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Order>(Order.class));

        if (orders.isEmpty())
            return ResponseHandler.responseBuilder("Product not found", HttpStatus.NOT_FOUND, null, true);


        String getProductsQuery = "SELECT products.id as product_id , orders_products.QUANTITY as quantity, products.description as description,\n" +
                " products.price as price , products.brand as brand , category_id as category_id\n" +
                " FROM orders_products\n" +
                " INNER JOIN products on products.id  =  orders_products.PRODUCT_ID" +
                " where order_id=?";

        List<OrdersProducts> ordersProducts = jdbcTemplate.query(getProductsQuery, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, id);
            }
        }, new BeanPropertyRowMapper<OrdersProducts>(OrdersProducts.class));

        return ResponseHandler.responseBuilder("Orders Retrieved", HttpStatus.OK, ordersProducts, true);

    }


    private void populateProducts(List<Order> orders) {

        for (Order o : orders) {
            String getProductsQuery = "SELECT products.id as product_id , orders_products.QUANTITY as quantity, products.description as description,\n" +
                    " products.price as price , products.brand as brand , category_id as category_id\n" +
                    " FROM orders_products\n" +
                    " INNER JOIN products on products.id  =  orders_products.PRODUCT_ID" +
                    " where order_id=?";

            List<OrdersProducts> ordersProducts = jdbcTemplate.query(getProductsQuery, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, o.getId());
                }
            }, new BeanPropertyRowMapper<OrdersProducts>(OrdersProducts.class));


            o.setProducts(ordersProducts);
        }


    }


}
