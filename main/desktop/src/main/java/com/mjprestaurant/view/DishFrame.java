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

import com.mjprestaurant.model.CustomComponents;
import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.controller.DishController;

/**
 * Classe de la pantalla dels plats
 * @author Patricia Oliva
 */
public class DishFrame extends AbstractFrame {
    JButton buttonAdd, buttonDelete, btnBack;
    private JTable dishTable;
    private DefaultTableModel tableModel;
    private DishController controller;

    /**
     * Constructor principal amb la llista de plats i el títol
     * @param title títol per la pantalla
     * @param dishes llista de plats a visualitzar
     */
    public DishFrame(String title, List<Dish> dishes) {
        super(title);
        loadDishes(dishes);
    }

    @Override
    protected void initComponents() {
        CustomComponents customComponent = new CustomComponents();
        JLabel title = new JLabel("Administració de plats: ", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 20, 0));
        titlePanel.add(title, BorderLayout.CENTER);

        // Columnes: ID | Nom | Preu | Categoria | Disponible | Accions
        String[] columnNames = {"ID", "Nom", "Preu", "Categoria", "Disponible", "Accions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // No editem directament a la taula
                return false;
            }
        };
        dishTable = new JTable(tableModel);

        // Estil taula
        dishTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dishTable.setRowHeight(35);
        dishTable.setShowVerticalLines(false);
        dishTable.setGridColor(new java.awt.Color(220, 220, 220));

        dishTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        dishTable.getTableHeader().setBackground(new java.awt.Color(240, 240, 240));
        dishTable.getTableHeader().setForeground(new java.awt.Color(60, 60, 60));

        dishTable.setSelectionBackground(new java.awt.Color(230, 255, 230));
        dishTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        JScrollPane scrollPane = new JScrollPane(dishTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        customComponent.setCustomButton("Afegir");
        buttonAdd = customComponent.getCustomButton();
        customComponent.setCustomButton("Eliminar");
        buttonDelete = customComponent.getCustomButton();

        JPanel dishPanel = new JPanel();
        dishPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        dishPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 50, 0));
        dishPanel.add(buttonAdd);
        dishPanel.add(buttonDelete);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(dishPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void createMenuBar() {
        super.createMenuBar();

        CustomComponents customComponent = new CustomComponents();
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            customComponent.setCustomButton("Enrere");
            btnBack = customComponent.getCustomButton();
            menuBar.add(btnBack, 0);
            menuBar.revalidate();
            menuBar.repaint();
        }
    }

    /**
     * Mètode que retorna el botó enrere
     * @return botó enrere
     */
    public JButton getBtnBack() {
        return btnBack;
    }

    /**
     * Carregar plats
     */
    public void loadDishes(List<Dish> dishes) {
        if (tableModel == null) return;
        tableModel.setRowCount(0);

        if (dishes != null) {
            for (Dish d : dishes) {
                String cat = (d.getCategory() == null) ? "" : d.getCategory().name();
                String available = d.getAvailable() ? "Sí" : "No";
                tableModel.addRow(new Object[]{d.getId(), d.getName(), d.getPrice(), cat, available, "Editar"});
            }
        }

        // Renderer del botó Accions
        dishTable.getColumn("Accions").setCellRenderer(new ButtonRenderer());

        dishTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = dishTable.rowAtPoint(e.getPoint());
                int column = dishTable.columnAtPoint(e.getPoint());

                if (column == dishTable.getColumn("Accions").getModelIndex()) {
                    Object idObj = dishTable.getValueAt(row, 0);
                    if (idObj == null) return;
                    long dishId = Long.parseLong(idObj.toString());
                    System.out.println("Editar plat amb ID: " + dishId);
                    if (controller != null) {
                        controller.editDish(dishId);
                    } else {
                        System.err.println("DishController no assignat a DishFrame.");
                    }
                }
            }
        });
    }

    public void reloadDishesTable(String token) {
        try {
            List<Dish> dishes = DishController.getAllDishes(token);
            loadDishes(dishes);
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No s'han pogut carregar els plats.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Renderer intern per al botó Editar
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        private final JButton btnEdit;

        public ButtonRenderer() {
            CustomComponents customComponent = new CustomComponents();
            customComponent.setCustomButton("Editar");
            this.btnEdit = customComponent.getCustomButton();
            this.btnEdit.setOpaque(true);
            this.btnEdit.setFocusPainted(false);
            this.btnEdit.setBorderPainted(false);
            this.btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            btnEdit.setText((value == null) ? "Editar" : value.toString());
            if (isSelected) {
                btnEdit.setBackground(new java.awt.Color(0, 128, 0));
                btnEdit.setForeground(java.awt.Color.WHITE);
            } else {
                btnEdit.setBackground(new java.awt.Color(230, 230, 230));
                btnEdit.setForeground(java.awt.Color.BLACK);
            }
            return btnEdit;
        }
    }

    // Getters / Setters
    public JButton getButtonAdd() { return buttonAdd; }
    public JButton getButtonDelete() { return buttonDelete; }
    public JTable getDishTable() { return dishTable; }
    public void setController(DishController controller) { this.controller = controller; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public void setTableModel(DefaultTableModel tableModel) { this.tableModel = tableModel; }
    public void setBtnBack(JButton btnBack) { this.btnBack = btnBack; }
}
