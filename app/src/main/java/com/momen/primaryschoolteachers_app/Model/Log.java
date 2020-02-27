package com.momen.primaryschoolteachers_app.Model;

public class Log {

    String id ;
    String totalMalePresent , totalFemalePresent , totalMAbsent , totalFAbsent , date ;



    public Log(String totalMalePresent, String totalFemalePresent, String date) {

        this.totalMalePresent = totalMalePresent;
        this.totalFemalePresent = totalFemalePresent;
        this.date = date;
    }
/* public Log(String id, String totalMalePresent, String totalFemalePresent, String totalMAbsent, String totalFAbsent, String date) {
        this.id = id;
        this.totalMalePresent = totalMalePresent;
        this.totalFemalePresent = totalFemalePresent;
        this.totalMAbsent = totalMAbsent;
        this.totalFAbsent = totalFAbsent;
        this.date = date;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalMalePresent() {
        return totalMalePresent;
    }

    public void setTotalMalePresent(String totalMalePresent) {
        this.totalMalePresent = totalMalePresent;
    }

    public String getTotalFemalePresent() {
        return totalFemalePresent;
    }

    public void setTotalFemalePresent(String totalFemalePresent) {
        this.totalFemalePresent = totalFemalePresent;
    }

    public String getTotalMAbsent() {
        return totalMAbsent;
    }

    public void setTotalMAbsent(String totalMAbsent) {
        this.totalMAbsent = totalMAbsent;
    }

    public String getTotalFAbsent() {
        return totalFAbsent;
    }

    public void setTotalFAbsent(String totalFAbsent) {
        this.totalFAbsent = totalFAbsent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
