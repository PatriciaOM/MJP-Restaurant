package com.mjprestaurant.model.table;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * returns a resume of the different the current status of the tables.
 * @author Joan Renau Valls
 */
public class TableStatusResponse {
    private ArrayList<TableStatusResponseElement> tables = new ArrayList();
    
    public ArrayList<TableStatusResponseElement> getTables() { return this.tables; }
    
    public TableStatusResponse(){}
    
    
    public void addTable(TableStatusResponseElement table) {
        this.tables.add(table);
    }

    
    
}
