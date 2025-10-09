package com.example.order_system_spring_boot.components.Products;

import com.example.order_system_spring_boot.components.Categories.Category;
import com.example.order_system_spring_boot.components.Customers.Customer;
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
import java.util.List;
import java.util.UUID;

@Repository
public class ProductsService implements ProductsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    DatesHelper datesHelper = new DatesHelper();


    @Override
    public ResponseEntity<Object> createProduct(Product product) {

        String categoryId = product.getCategory_id().isEmpty()
                ? product.getCategory_id() : null;
        System.out.print("Category id " + categoryId);

        if (categoryId != null) {

            List<Category> categories = jdbcTemplate.query("SELECT * FROM CATEGORIES WHERE ID=?", new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, categoryId);
                }
            }, new BeanPropertyRowMapper<Category>(Category.class));


            if (categories.isEmpty())
                return ResponseHandler.responseBuilder("Wrong category ID", HttpStatus.NOT_FOUND, null, false);
        }


        String dateNow = datesHelper.getDateNowTS();
        UUID randomUuid = UUID.randomUUID();


        product.setId(randomUuid.toString());
        product.setDateCreated(dateNow);


        jdbcTemplate.update("INSERT INTO PRODUCTS (id, description, brand , dateCreated , dateModified , category_id , price) VALUES (?,?,?,?,?,?,?)"
                , product.getId(), product.getDescription(), product.getBrand(), product.getDateCreated(), null, product.getCategory_id(), product.getPrice());


        return ResponseHandler.responseBuilder("Product Created!!!", HttpStatus.CREATED, product, true);


    }

    @Override
    public ResponseEntity<Object> getAll() {

        List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCTS", new BeanPropertyRowMapper<Product>(Product.class));

        return ResponseHandler.responseBuilder("Products Retrieved", HttpStatus.OK, products, true);
    }

    @Override
    public ResponseEntity<Object> getById(String id) {

        List<Product> products = jdbcTemplate.query("SELECT * FROM products WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Product>(Product.class));

        if (products.isEmpty())
            return ResponseHandler.responseBuilder("Product not found", HttpStatus.NOT_FOUND, null, true);


        return ResponseHandler.responseBuilder("Product Retrieved!!!", HttpStatus.OK, products.get(0), true);

    }

    @Override
    public ResponseEntity<Object> delete(String id) {

        List<Product> products = jdbcTemplate.query("SELECT * FROM products WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Product>(Product.class));

        if (products.isEmpty())
            return ResponseHandler.responseBuilder("Product not found", HttpStatus.NOT_FOUND, null, true);


        jdbcTemplate.update("DELETE FROM PRODUCTS WHERE id=? ", products.get(0).getId());

        return ResponseHandler.responseBuilder("Product Deleted", HttpStatus.OK, null, true);


    }

    @Override
    public ResponseEntity<Object> updateProduct(Product product, String id) {
        List<Product> products = jdbcTemplate.query("SELECT * FROM products WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Product>(Product.class));

        if (products.isEmpty())
            return ResponseHandler.responseBuilder("Product not found", HttpStatus.NOT_FOUND, null, true);

        Product productInDb = products.get(0);
        String dateModified = datesHelper.getDateNowTS();

        String updateQuery = "UPDATE products SET description =?, brand=?, price=?, dateModified=? , category_id=? WHERE id=?";

        String description = (product.getDescription() != null && !product.getDescription().isBlank())
                ? product.getDescription()
                : productInDb.getDescription();

        String brand = (product.getBrand() != null && !product.getBrand().isBlank())
                ? product.getBrand()
                : productInDb.getBrand();

        Double price = (product.getPrice() != 0.0)
                ? product.getPrice()
                : productInDb.getPrice();

        String category_id = productInDb.getCategory_id();

        if (product.getCategory_id() != null && !product.getCategory_id().isBlank()) {

            List<Category> categories = jdbcTemplate.query("SELECT * FROM categories WHERE id = ?",
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement ps) throws SQLException {
                            ps.setString(1, product.getCategory_id());
                        }
                    },
                    new BeanPropertyRowMapper<Category>(Category.class));

            if (categories.isEmpty())
                return ResponseHandler.responseBuilder("Category not found", HttpStatus.NOT_FOUND, null, true);

            category_id = product.getCategory_id();
        }


        jdbcTemplate.update(updateQuery, description, brand, price, dateModified, category_id, id);

        return ResponseHandler.responseBuilder("Product updated", HttpStatus.OK, productInDb, true);

    }

    @Override
    public ResponseEntity<Object> changeCategory(String product_id, String categoryId) {

        List<Product> products = jdbcTemplate.query("SELECT * FROM products WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, product_id);
                    }
                },
                new BeanPropertyRowMapper<Product>(Product.class));

        if (products.isEmpty())
            return ResponseHandler.responseBuilder("Product not found", HttpStatus.NOT_FOUND, null, true);



        List<Category> categories = jdbcTemplate.query("SELECT * FROM categories WHERE id = ?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, categoryId);
                    }
                },
                new BeanPropertyRowMapper<Category>(Category.class));

        if (categories.isEmpty())
            return ResponseHandler.responseBuilder("Category not found", HttpStatus.NOT_FOUND, null, true);


        String updateQuery = "UPDATE PRODUCTS set category_id =? where id=?";

        jdbcTemplate.update(updateQuery,categoryId, product_id);

        return ResponseHandler.responseBuilder("Product category updated", HttpStatus.OK, products.get(0), true);



    }
}
