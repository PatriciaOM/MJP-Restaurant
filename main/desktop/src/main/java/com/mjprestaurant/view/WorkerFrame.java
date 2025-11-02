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

import com.mjprestaurant.controller.WorkerController;
import com.mjprestaurant.model.CustomComponents;
import com.mjprestaurant.model.Worker;

public class WorkerFrame extends AbstractFrame {
    JButton buttonAdd, buttonChange, buttonDelete, btnBack;

    private JTable workerTable;
    private DefaultTableModel tableModel;

    public WorkerFrame(String title, List<Worker> workers) {
        super(title);
        loadWorkers(workers); //carregar llista
    }

    @Override
    protected void initComponents() {
        JLabel title = new JLabel("Administració de treballadors: ", SwingConstants.CENTER);
        System.out.println(title);
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100,0,20,0));
        titlePanel.add(title, BorderLayout.CENTER);

        //Crear taula i model
        String[] columnNames = {"ID", "DNI", "Nom complet", "Torn", "Accions"};
        tableModel = new DefaultTableModel(columnNames, 0);
        workerTable = new JTable(tableModel);

        //Estil de la taula
        workerTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        workerTable.setRowHeight(35);
        workerTable.setShowVerticalLines(false);
        workerTable.setGridColor(new java.awt.Color(220, 220, 220));

        workerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        workerTable.getTableHeader().setBackground(new java.awt.Color(240, 240, 240));
        workerTable.getTableHeader().setForeground(new java.awt.Color(60, 60, 60));

        workerTable.setSelectionBackground(new java.awt.Color(230, 255, 230)); // verde suave al seleccionar
        workerTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        JScrollPane scrollPane = new JScrollPane(workerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        new CustomComponents().setCustomButton("Afegir");
        buttonAdd = CustomComponents.getCustomButton();
        new CustomComponents().setCustomButton("Eliminar");
        buttonDelete = CustomComponents.getCustomButton();
        
        JPanel workerPanel = new JPanel();
        workerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        workerPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 50, 0));
        workerPanel.add(buttonAdd);
        workerPanel.add(buttonDelete);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(workerPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void createMenuBar() {
        // Llama al método original para crear el menú base
        super.createMenuBar();

        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            new CustomComponents().setCustomButton("Enrere");
            btnBack = CustomComponents.getCustomButton();

            // Insertamos el botón antes del de logout
            menuBar.add(btnBack, 0);
            menuBar.revalidate();
            menuBar.repaint();
        }
    }


    public JButton getBtnBack() {
        return btnBack;
    }

    /**
     * Carregar treballadors
     * @param workers
     */
    public void loadWorkers(List<Worker> workers) {
        if (tableModel == null) return;
        tableModel.setRowCount(0);

        int idCounter = 1;
        for (Worker w : workers) {
            String fullName = w.getName() + " " + w.getSurname();
            tableModel.addRow(new Object[]{idCounter++, w.getDni(), fullName, w.getShift(), "Editar"});
        }

        workerTable.getColumn("Accions").setCellRenderer(new ButtonRenderer());

        // Detectar clics en el botó "Editar"
        workerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = workerTable.rowAtPoint(e.getPoint());
                int column = workerTable.columnAtPoint(e.getPoint());

                // Verificar si s'ha fet clic a la columna "Accions"
                if (column == workerTable.getColumn("Accions").getModelIndex()) {
                    int workerId = Integer.parseInt(workerTable.getValueAt(row, 0).toString());
                    System.out.println("Editar trabajador con ID: " + workerId);
                    WorkerController.editWorker(workerId);
                }
            }
        });
    }

    /**
     * Classe interna renderer del botó Editar
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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Editar" : value.toString());
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
    
    /**
     * Retorna el botó afegir
     * @return botó afegir
     */
    public JButton getButtonAdd() {
        return buttonAdd;
    }

    /**
     * Retorna el botó eliminar
     * @return botó eliminar
     */
    public JButton getButtonDelete() {
        return buttonDelete;
    }

    /**
     * Retorna la taula de treballadors
     * @return taula de treballadors
     */
    public JTable getWorkerTable() {
        return workerTable; 
    }

}
