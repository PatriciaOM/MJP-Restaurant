package com.mjprestaurant.model.table;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableStatusModel extends AbstractTableModel {

    private final String[] columnNames = {"Taula", "Ocupació"};
        private List<TableStatusResponseElement> data;

    public TableStatusModel(List<TableStatusResponseElement> data) {
        this.data = data;
    }

    public void setData(List<TableStatusResponseElement> newData) {
        this.data = newData;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        TableStatusResponseElement t = data.get(row);
        return switch (col) {
            case 0 -> "Taula " + t.getId();
            case 1 -> t.getClientsAmount() + "/" + t.getMaxClients();
            default -> "";
        };
    }

}
