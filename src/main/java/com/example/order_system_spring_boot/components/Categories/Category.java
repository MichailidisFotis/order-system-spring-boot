package com.example.order_system_spring_boot.components.Categories;

import com.example.order_system_spring_boot.helpers.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;

public class Category implements ValidationGroups {


    private String id;

    @NotEmpty(message = "Description should not be empty" , groups = {OnCreate.class , OnUpdate.class})
    private String description;

    private String dateCreated ;

    private String dateModified ;


    public Category(){
    }

    public Category(String id , String description , String dateCreated ,String dateModified){
        this.id = id;
        this.description =  description;
        this.dateCreated =  dateCreated;
        this.dateModified =  dateModified;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }


    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateModified='" + dateModified + '\'' +
                '}';
    }
}
