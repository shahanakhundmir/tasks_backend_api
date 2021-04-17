package com.impact.model;

import java.sql.Date;

public class Restaurant{

    private int rest_id;
    private String rest_name;
    private String rest_branch;
    private String allergen_safety;
    private Date sys_creation_date;
    private Date sys_update_date;
    private int user_id;
    private String application_id;
    private String version_code;



    public Restaurant(int rest_id, String rest_name, String rest_branch, String allergen_safety, Date sys_creation_date, Date sys_update_date,
     int user_id, String application_id, String version_code){
        
        this.rest_id = rest_id;
        this.rest_name = rest_name;
        this.rest_branch = rest_branch;
        this.allergen_safety = allergen_safety;
        this.sys_creation_date = sys_creation_date;
        this.sys_update_date = sys_update_date;
        this.user_id = user_id;
        this.application_id = application_id;
        this.version_code = version_code;
    }

    public int getRest_id(){
        return rest_id;
    }

    public String getRest_name(){
        return rest_name;
    }

    public String getRest_branch(){
        return rest_branch;
    }
    public String getAllergen_safety(){
        return allergen_safety;
    }

    public Date getSys_creation_date(){
        return sys_creation_date;
    }

    public Date getSys_update_date(){
        return sys_update_date;
    }
    public int getUser_id(){
        return user_id;
    }

    public String getApplication_id(){
        return application_id;
    }

    public String getVersion_code(){
        return version_code;
    }



}