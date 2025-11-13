package com.mjprestaurant.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.mjprestaurant.controller.TableController;
import com.mjprestaurant.model.CustomComponents;
import com.mjprestaurant.model.table.TableRestaurant;

public class TableFrame extends AbstractFrame {

    private JButton buttonAdd, buttonDelete, btnBack;
    private JTable tableRestaurant;
    private DefaultTableModel tableModel;

    public TableFrame(String title, List<TableRestaurant> tables) {
        super(title);
        loadTables(tables); // carregar la llista
    }

    @Override
    protected void initComponents() {
        JLabel title = new JLabel("Administració de taules del restaurant:", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 20, 0));
        titlePanel.add(title, BorderLayout.CENTER);

        // Crear taula i model
        String[] columnNames = {"ID", "Número de taula", "Màxim de comensals", "Accions"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableRestaurant = new JTable(tableModel);

        // Estil de la taula
        tableRestaurant.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableRestaurant.setRowHeight(35);
        tableRestaurant.setShowVerticalLines(false);
        tableRestaurant.setGridColor(new java.awt.Color(220, 220, 220));

        tableRestaurant.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableRestaurant.getTableHeader().setBackground(new java.awt.Color(240, 240, 240));
        tableRestaurant.getTableHeader().setForeground(new java.awt.Color(60, 60, 60));

        tableRestaurant.setSelectionBackground(new java.awt.Color(230, 255, 230));
        tableRestaurant.setSelectionForeground(new java.awt.Color(0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(tableRestaurant);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // Botons inferiors
        new CustomComponents().setCustomButton("Afegir");
        buttonAdd = CustomComponents.getCustomButton();
        new CustomComponents().setCustomButton("Eliminar");
        buttonDelete = CustomComponents.getCustomButton();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 50, 0));
        buttonPanel.add(buttonAdd);
        buttonPanel.add(buttonDelete);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void createMenuBar() {
        super.createMenuBar();

        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            new CustomComponents().setCustomButton("Enrere");
            btnBack = CustomComponents.getCustomButton();
            menuBar.add(btnBack, 0);
            menuBar.revalidate();
            menuBar.repaint();
        }
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    /**
     * Carrega les taules del restaurant.
     * @param tables llista de taules
     */
    public void loadTables(List<TableRestaurant> tables) {
        if (tableModel == null) return;
        tableModel.setRowCount(0);

        for (TableRestaurant t : tables) {
            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getNum(),
                    t.getMaxGuests(),
                    "Editar"
            });
        }

        tableRestaurant.getColumn("Accions").setCellRenderer(new ButtonRenderer());

        tableRestaurant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableRestaurant.rowAtPoint(e.getPoint());
                int column = tableRestaurant.columnAtPoint(e.getPoint());

                if (column == tableRestaurant.getColumn("Accions").getModelIndex()) {
                    int tableId = Integer.parseInt(tableRestaurant.getValueAt(row, 0).toString());
                    System.out.println("Editar taula amb ID: " + tableId);
                    TableController.editTable(tableId);
                }
            }
        });
    }

    public void reloadTables(String token) {
        try {
            List<TableRestaurant> tables = TableController.getAllTables(token);
            loadTables(tables);
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No s'han pogut carregar les taules.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Renderer del botó "Editar"
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        private final JButton btnEdit;

        public ButtonRenderer() {
            CustomComponents cc = new CustomComponents();
            cc.setCustomButton("Editar");
            this.btnEdit = CustomComponents.getCustomButton();
            this.btnEdit.setOpaque(true);
            this.btnEdit.setFocusPainted(false);
            this.btnEdit.setBorderPainted(false);
            this.btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            if (isSelected) {
                btnEdit.setBackground(new java.awt.Color(0, 128, 0));
                btnEdit.setForeground(java.awt.Color.WHITE);
            } else {
                btnEdit.setBackground(new java.awt.Color(230, 230, 230));
                btnEdit.setForeground(java.awt.Color.BLACK);
            }
            btnEdit.setText((value == null) ? "Editar" : value.toString());
            return btnEdit;
        }
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public JButton getButtonDelete() {
        return buttonDelete;
    }

    public JTable getTableRestaurant() {
        return tableRestaurant;
    }

}
