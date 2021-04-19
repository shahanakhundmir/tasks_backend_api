package com.impact.model;

import java.util.Date;


public class Allergen {
    private int allergen_id;
    private String allergen_name;
    private String allergen_image;
    private Date sys_creation_date;
    private Date sys_update_date;
    private int user_id;
    private String application_id;
    private String version_code;

    public Allergen( int allergen_id, String allergen_name, String allergen_image, Date sys_creation_date,Date sys_update_date,
                     int user_id, String application_id,String version_code){
       
        this.allergen_id = allergen_id;
        this.allergen_name = allergen_name;
        this.allergen_image = allergen_image;
        this.sys_creation_date = sys_creation_date;
        this.sys_update_date = sys_update_date;
        this. user_id = user_id;
        this.application_id = application_id;
        this.version_code = version_code;
    }



    public void setAllergen_id(int allergen_id) {
        this.allergen_id = allergen_id;
    }

    public void setAllergen_name(String allergen_name) {
        this.allergen_name = allergen_name;
    }

    public void setAllergen_image(String allergen_image) {
        this.allergen_image = allergen_image;
    }

    public void setSys_creation_date(Date sys_creation_date) {
        this.sys_creation_date = sys_creation_date;
    }

    public void setSys_update_date(Date sys_update_date) {
        this.sys_update_date = sys_update_date;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public int getAllergen_id() {
        return allergen_id;
    }

    public String getAllergen_name() {
        return allergen_name;
    }

    public String getAllergen_image() {
        return allergen_image;
    }

    public Date getSys_creation_date() {
        return sys_creation_date;
    }

    public Date getSys_update_date() {
        return sys_update_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getApplication_id() {
        return application_id;
    }
    
    public String getVersion_code() {
        return version_code;
    }
}