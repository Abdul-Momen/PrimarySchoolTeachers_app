package com.momen.primaryschoolteachers_app.Model;

/**
 * Created by company on 3/11/2018.
 */

public class Data
{
    String school_id,class_id,total_male,total_female;


    public Data(String school_id, String class_id, String total_male, String total_female) {
        this.school_id = school_id;
        this.class_id = class_id;
        this.total_male = total_male;
        this.total_female = total_female;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getTotal_male() {
        return total_male;
    }

    public void setTotal_male(String total_male) {
        this.total_male = total_male;
    }

    public String getTotal_female() {
        return total_female;
    }

    public void setTotal_female(String total_female) {
        this.total_female = total_female;
    }
}
