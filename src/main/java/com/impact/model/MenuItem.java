package com.impact.model;
import java.util.Date;

public class MenuItem {
    private int item_id;
    private String name;
    private String image;
    private String short_desc;
    private String full_desc;
    private String factory_contam;
    private String kitchen_contam;
    private String ingredients;
    private String sub_menu;
    private double price;
    private int rest_id;
    private Date sys_creation_date;
    private Date sys_update_date;
    private int user_id;
    private String application_id;
    private String version_code;

    public MenuItem(int item_id, String name, String image, String short_desc, String full_desc, 
    String factory_contam, String kitchen_contam, String ingredients, String sub_menu, 
    double price, int rest_id,Date sys_creation_date, Date sys_update_date, int user_id, String application_id, String version_code) {
        this.item_id = item_id;
        this.name = name;
        this.image=image;
        this.short_desc = short_desc;
        this.full_desc = full_desc;
        this.factory_contam = factory_contam;
        this.kitchen_contam = kitchen_contam;
        this.ingredients = ingredients;
        this.sub_menu = sub_menu;
        this.price = price;
        this.rest_id=rest_id;
        this.sys_creation_date = sys_creation_date;
        this.sys_update_date = sys_update_date;
        this.user_id = user_id;
        this.application_id = application_id;
        this.version_code = version_code;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public String getFull_desc() {
        return full_desc;
    }

    public String getFactory_contam() {
        return factory_contam;
    }

    public String getKitchen_contam() {
        return kitchen_contam;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSub_menu() {
        return sub_menu;
    }

    public double getPrice() {
        return price;
    }
    public int getRest_id() {
        return rest_id;
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