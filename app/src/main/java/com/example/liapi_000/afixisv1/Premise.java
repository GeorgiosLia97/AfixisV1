package com.example.liapi_000.afixisv1;

import com.google.android.gms.maps.model.LatLng;

public class Premise{
    private String id;
    private String name;
    private int available = 0;
    LatLng location;
    int used = 0;
    int [] tables;
    int soonAvailable = 0;




    public Premise(String id, String name, int[] tables, double latitude, double longtitude) {
        this.id = id;
        this.name = name;
        this.tables = tables;
        location = new LatLng(latitude,longtitude);
        makeData();
    }




    public void makeData(){
        available = 0;
        used = 0;
        soonAvailable = 0;
        for(int i=0; i<tables.length;i++){
            if(tables[i]==1){
                available++;
            }
            if(tables[i]==0){
                used++;
            }
            if(tables[i]==2){
                soonAvailable++;
            }

        }
    }


    public void arriveAtPremise(){
        boolean done = false;
        for(int i=0; i<tables.length;i++){
            if(tables[i]==1 && done == false){
                tables[i] = 0;
                done = true;
            }
            if(done==true)
                break;
        }
        makeData();
    }

    public void requestBill(){
        boolean done = false;
        for(int i=0; i<tables.length; i++){
            if(tables[i]==0 && done == false){
                tables[i] = 2;
                done = true;
            }
            if(done==true)
                break;
        }
        makeData();
    }


    public void leaveThePremise(){
        boolean done = false;
        for(int i: tables){
            if(tables[i]==2 && done == false){
                tables[i] = 1;
                done = true;
            }
            if(done==true)
                break;
        }
        makeData();
    }

    public void print_Data(){
        System.out.println("Available tables: " +available);
        System.out.println("Used tables: " +used);
        System.out.println("Tables available soon: " +soonAvailable);
    }


    public String getName(){
        return name;
    }


    public int getAvailable() {
        return available;
    }





    public int getUnavailable() {
        return used;
    }


    public int[] getTotalTables() {
        return tables;
    }


    public int getSoonAvailable() {
        return soonAvailable;
    }

}
