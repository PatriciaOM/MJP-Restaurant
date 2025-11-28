package com.mjprestaurant.view;

import java.awt.*;
import java.util.function.Supplier;
import javax.swing.*;
import java.util.List;

import com.mjprestaurant.controller.OrderController;
import com.mjprestaurant.model.CustomComponents;
import com.mjprestaurant.model.order.Order;
import com.mjprestaurant.model.table.TableStatusResponseElement;

/**
 * Classe per la pantalla de cambrers 
 */
public class WaiterFrame extends AbstractFrame {

    private JPanel tablesContainer;
    private JButton btnRefresh;
    private Supplier<List<TableStatusResponseElement>> refreshAction;
    private ImageIcon tableIcon;
    private LoginFrame login;
    private String token;

    /**
     * Constructor principal
     * @param login pantalla de login
     * @param token token de sessió
     * @param username nom de la pantalla
     * @param refreshAction llistat de taules per refrescar
     */
    public WaiterFrame(LoginFrame login, String token, String username, Supplier<List<TableStatusResponseElement>> refreshAction) {
        super("Estat de Taules");
        this.username = username;
        this.token = token;
        this.refreshAction = refreshAction;
        this.login = login;
        addListeners();
    }

    /**
     * Mètode que afegeix els listener del logout
     */
    private void addListeners() {
        this.initLogout(login);
    }

    /**
     * Mètode que inicialitza els components necessaris
     */
    @Override
    protected void initComponents() {

        tableIcon = new ImageIcon(getClass().getResource("/assets/img/table.png"));

        tablesContainer = new JPanel();
        tablesContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15)); 
        tablesContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        tablesContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tablesContainer);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        if (btnRefresh != null) {
            btnRefresh.addActionListener(e -> refresh());
        }

    }

    /**
     * Mètode que sobreescriu la barra de menú per afegir el botó de refrescar taules
     */
    @Override
    protected void createMenuBar() {
        super.createMenuBar();

        CustomComponents customComponents = new CustomComponents();
        customComponents.setCustomButton("Refresca");
        btnRefresh = customComponents.getCustomButton();

        btnRefresh.addActionListener(e -> refresh());

        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            menuBar.add(btnRefresh, 0); 
            menuBar.revalidate();
            menuBar.repaint();
        }
    }

    /**
     * Mètode que refresca les taules
     */
    public void refresh() {
        if (refreshAction == null) return;
        List<TableStatusResponseElement> tables = refreshAction.get();

        tablesContainer.removeAll();

        for (TableStatusResponseElement t : tables) {
            tablesContainer.add(createTableCard(t));
        }

        tablesContainer.revalidate();
        tablesContainer.repaint();
    }

    /**
     * Mètode que crea la card per la taula
     * @param t taula
     * @return retorna la card
     */
    private JPanel createTableCard(TableStatusResponseElement t) {

        int clients = t.getClientsAmount();
        int max = t.getMaxClients();

        Color borderColor;

        if (clients == 0) {
            borderColor = new Color(0, 180, 0); 
        } else if (clients < max) {
            borderColor = new Color(230, 180, 0); 
        } else {
            borderColor = new Color(200, 0, 0); 
        }

        CustomComponents customComponents = new CustomComponents();
    
        JPanel card = customComponents.createRoundBorderPanel(
            borderColor,
            3,      
            20,     
            200,
            200
        );

        card.setBorder(BorderFactory.createCompoundBorder( card.getBorder(), 
        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        card.setLayout(new BorderLayout());
        card.setOpaque(false); 

        Image img = tableIcon.getImage();
        Image resizedImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(resizedImg));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panell d'informació
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        JLabel tableLabel = new JLabel("Taula " + t.getId(), SwingConstants.CENTER);
        tableLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JLabel statusLabel = new JLabel(t.getClientsAmount() + " / " + t.getMaxClients(), SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoPanel.add(tableLabel);
        infoPanel.add(statusLabel);

        card.add(iconLabel, BorderLayout.CENTER);
        card.add(infoPanel, BorderLayout.SOUTH);
        
        card.setPreferredSize(new Dimension(200, 200));

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleTableClick(t.getId());
            }
        });

        return card;
    }

    /**
     * Mètode que controla el clic a les cards de les taules
     * @param id id de la taula a la que es fa clic
     */
    private void handleTableClick(Long tableId) {
        try {
            // Obtenir la comanda de la taula
            Order order = OrderController.getOrderByTableId(token, tableId);

            if (order != null && order.getState() == Order.Status.OPEN) {
                int response = JOptionPane.showConfirmDialog(
                        this,
                        "Existeix una comanda oberta, ¿ha sortit de la cuina?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    order.setState(Order.Status.SERVED);
                    OrderController.updateOrder(token, order);
                    JOptionPane.showMessageDialog(this, "Ordre servida.");
                    refresh();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay orden abierta o ya servida.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar la orden.");
        }
    }

}
