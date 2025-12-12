package com.mjprestaurant.view;

import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mjprestaurant.controller.OrderController;
import com.mjprestaurant.controller.SessionController;
import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.CustomComponents;
import com.mjprestaurant.model.order.Order;
import com.mjprestaurant.model.order.OrderUpdateInfo;
import com.mjprestaurant.model.session.SessionService;
import com.mjprestaurant.model.session.SessionServiceGetInfo;
import com.mjprestaurant.model.session.SessionServiceGetResponse;
import com.mjprestaurant.model.session.SessionService.SessionServiceStatus;
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

    // ✅ ObjectMapper global con JavaTimeModule para LocalDateTime
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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

        card.setBorder(BorderFactory.createCompoundBorder(card.getBorder(), 
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

    /*public void handleTableClick(Long tableId, String token) {
        try {
            // 1. Obtener sesión de la mesa
           SessionService session = SessionController.getSessionByTableId(token, tableId);
            if (session == null) {
                System.out.println("No hay sesión activa para esta mesa.");
                return;
            }
            // 2. Comprobar si hay alguna orden SENDED
            Order order = OrderController.getOrderByTableId(token, tableId);
            if (order != null && order.getState() == Order.Status.SENDED) {
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
                    refresh(); // refrescar la vista
                }

                return; // salir del método, no se comprueba sesión aún
            }

            // 3. Si no hay órdenes SENDED, comprobar todas las órdenes y la sesión
           List<Order> orders = OrderController.getOrdersByTableId(token, tableId);
            boolean allServed = orders.stream().allMatch(o -> o.getState() == Order.Status.SERVED);
            boolean sessionClosed = session.getStatus() == SessionService.SessionServiceStatus.CLOSED;

            if (allServed && sessionClosed) {
                int option = JOptionPane.showConfirmDialog(
                        this,
                        "Sesión cerrada, ¿está pagada?",
                        "Confirmación de pago",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    session.setStatus(SessionService.SessionServiceStatus.PAID);
                    SessionController.updateSession(token, session);
                    JOptionPane.showMessageDialog(this, "Sesión actualizada a PAGADA.");
                    refresh(); // refrescar la vista
                }
            }

        } catch (ControllerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener sesión u órdenes: " + e.getMessage());
        }
    }*/
    private void handleTableClick(Long tableId) {
        try {
            List<Order> orders = OrderController.getOrdersByTableId(token, tableId);
            SessionService service = SessionController.getSessionByTableId(token, tableId);

            if (service == null) {
                JOptionPane.showMessageDialog(this, "No hay sesión activa para esta mesa.");
                return;
            }

            if (orders == null || orders.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay órdenes abiertas.");
                return;
            }

            // 1. Procesar cualquier orden SENDED
            for (Order o : orders) {
                if (o.getState() == Order.Status.SENDED) {
                    int response = JOptionPane.showConfirmDialog(
                            this,
                            "Existeix una comanda oberta, ¿ha sortit de la cuina?",
                            "Confirmar",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (response == JOptionPane.YES_OPTION) {
                        o.setState(Order.Status.SERVED);
                        OrderController.updateOrder(token, o);
                        JOptionPane.showMessageDialog(this, "Ordre servida.");
                        refresh();
                    }
                    return; // Si hay SENDED, no seguimos con la sesión
                }
            }

            // 2. Comprobar si todas las órdenes están SERVED
            boolean allServed = orders.stream().allMatch(o -> o.getState() == Order.Status.SERVED);

            // 3. Si todas están SERVED y la sesión está CLOSED, preguntar por PAID
            if (allServed && service.getStatus() == SessionServiceStatus.CLOSED) {
                int sessionResponse = JOptionPane.showConfirmDialog(
                        this,
                        "La sessió està tancada, pagar?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

                if (sessionResponse == JOptionPane.YES_OPTION) {
                    service.setStatus(SessionServiceStatus.PAID);
                    SessionController.updateSession(token, service);
                    JOptionPane.showMessageDialog(this, "Sessió actualitzada a PAGADA.");
                    refresh();
                }
            }

        } catch (ControllerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar la sesión u órdenes: " + e.getMessage());
        }
    }

}
