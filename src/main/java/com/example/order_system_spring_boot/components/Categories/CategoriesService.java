package com.example.order_system_spring_boot.components.Categories;

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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CategoriesService implements CategoriesRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    DatesHelper datesHelper =  new DatesHelper();


    @Override
    public ResponseEntity<Object> createCategory(Category category) {

        UUID randomUuid = UUID.randomUUID();

        String query = "INSERT INTO CATEGORIES(id, description , dateCreated , dateModified) VALUES (?,?,?,NULL)";


        String dateNow = datesHelper.getDateNowTS();

        category.setId(randomUuid.toString());
        category.setDateCreated(dateNow);


        jdbcTemplate.update(query,category.getId() , category.getDescription() , category.getDateCreated());

        return ResponseHandler.responseBuilder("Category created", HttpStatus.CREATED, category, true);
    }

    @Override
    public ResponseEntity<Object> delete(String id) {

        String query =  "SELECT * FROM CATEGORIES WHERE id=?";

        List<Category> categories = jdbcTemplate.query(query,
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Category>(Category.class));

        if(categories.isEmpty())
            return ResponseHandler.responseBuilder("Category not found" , HttpStatus.NOT_FOUND , null , true);


        String deleteQuery =  "DELETE FROM CATEGORIES WHERE ID=?";


        jdbcTemplate.update(deleteQuery , id);


        return ResponseHandler.responseBuilder("Category Deleted!!!" , HttpStatus.OK , null , true);

    }

    @Override
    public ResponseEntity<Object> updateCategory(Category category, String id) {

        String query =  "SELECT * FROM CATEGORIES WHERE id=?";

        List<Category> categories = jdbcTemplate.query(query,
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Category>(Category.class));

        if(categories.isEmpty())
            return ResponseHandler.responseBuilder("Category not found" , HttpStatus.NOT_FOUND , null , true);

        String dateModified =  datesHelper.getDateNowTS();

        Category categoryInDB =  categories.get(0);

        String updateQuery = "UPDATE CATEGORIES set description=?, dateModified=? where id=?";

        String description = (category.getDescription()!=null && !category.getDescription().isBlank())
                ? category.getDescription()
                : categoryInDB.getDescription();


        jdbcTemplate.update(updateQuery , description, dateModified,id);

        return  ResponseHandler.responseBuilder("Category updated!!!!" , HttpStatus.OK , categoryInDB , true);


    }

    @Override
    public ResponseEntity<Object> getAll() {

        List<Category> categories = jdbcTemplate.query("SELECT * FROM CATEGORIES",
                new BeanPropertyRowMapper<Category>(Category.class)
        );

        if (categories.isEmpty())
            return ResponseHandler.responseBuilder("No categories found",
                    HttpStatus.NOT_FOUND,
                    null,
                    false
            );


        return ResponseHandler.responseBuilder("Categories Retrieved", HttpStatus.OK, categories, true);
    }

    @Override
    public ResponseEntity<Object> getByDescription(String description) {

        String query =  "SELECT * FROM CATEGORIES WHERE description=?";

        List<Category> categories = jdbcTemplate.query(query,
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, description);
                    }
                },
                new BeanPropertyRowMapper<Category>(Category.class));

        if(categories.isEmpty())
            return ResponseHandler.responseBuilder("Category not found" , HttpStatus.NOT_FOUND , null , true);

        Category category =  categories.get(0);

        return ResponseHandler.responseBuilder("Category retrieved" , HttpStatus.OK , category , true);
    }

    @Override
    public ResponseEntity<Object> getById(String id) {

        String query =  "SELECT * FROM CATEGORIES WHERE id=?";

        List<Category> categories = jdbcTemplate.query(query,
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, id);
                    }
                },
                new BeanPropertyRowMapper<Category>(Category.class));

        if(categories.isEmpty())
            return ResponseHandler.responseBuilder("Category not found" , HttpStatus.NOT_FOUND , null , true);

        Category category =  categories.get(0);

        return ResponseHandler.responseBuilder("Category retrieved" , HttpStatus.OK , category , true);

    }
}
