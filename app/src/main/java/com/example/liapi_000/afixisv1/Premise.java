package com.example.liapi_000.afixisv1;

import com.google.android.gms.maps.model.LatLng;

public class Premise{
    private String id;
    private String name;
    int available = 0;
    LatLng location;
    int used = 0;
    int [] tables;
    int soonAvailable = 0;
    int stat = 0;



    public Premise(String id, String name, int[] tables, LatLng loc) {
        this.id = id;
        this.name = name;
        this.tables = tables;
       this.location = loc;
        makeData();
    }

    public int status(String id){


        if( available >0) {
            stat = 1;

        } else { if( available ==0 && soonAvailable == 0){

                    stat = 0;
                    }
                    else {
                        stat = 2;
                         }


        }




        return stat;
    }

    public int getStatus(){
       stat =  status(this.id);
        return stat;
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
                used ++;
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
        for(int i=0; i<tables.length; i++){
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


    public int getTotalTables() {
        return tables.length;
    }


    public int getSoonAvailable() {
        return soonAvailable;
    }

}
