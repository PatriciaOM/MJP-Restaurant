package com.mjprestaurant.view;

import java.awt.*;
import java.util.function.Supplier;
import javax.swing.*;
import java.util.List;

import com.mjprestaurant.model.CustomComponents;
import com.mjprestaurant.model.table.TableStatusResponseElement;

/**
 * Pantalla de camareros, versión moderna
 */
public class WaiterFrame extends AbstractFrame {

    private JPanel tablesContainer;
    private JButton btnRefresh;
    private Supplier<List<TableStatusResponseElement>> refreshAction;
    private ImageIcon tableIcon;

    public WaiterFrame(String username, Supplier<List<TableStatusResponseElement>> refreshAction) {
        super("Estat de Taules");
        this.username = username;
        this.refreshAction = refreshAction;
    }

    @Override
    protected void initComponents() {

        // Icono de mesa
        tableIcon = new ImageIcon(getClass().getResource("/assets/img/table.png"));

        // Contenedor de tarjetas con FlowLayout
        tablesContainer = new JPanel();
        tablesContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15)); // Espaciado de 15px
        tablesContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        tablesContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tablesContainer);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Acción botón refrescar
        if (btnRefresh != null) {
            btnRefresh.addActionListener(e -> refresh());
        }

    }

    @Override
    protected void createMenuBar() {
        super.createMenuBar();

        CustomComponents customComponents = new CustomComponents();
        customComponents.setCustomButton("Refresca");
        btnRefresh = customComponents.getCustomButton();

        // Acción del botón
        btnRefresh.addActionListener(e -> refresh());

        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            menuBar.add(btnRefresh, 0); // Añadimos el botón al menú
            menuBar.revalidate();
            menuBar.repaint();
        }
    }


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

    private JPanel createTableCard(TableStatusResponseElement t) {

    CustomComponents customComponents = new CustomComponents();
        JPanel card = customComponents.createRoundBorderPanel(
            new Color(220, 240, 255), // color del borde
            2,  // grosor
            20, // radio de esquinas
            200, // ancho
            200  // alto
        );

        card.setBorder(BorderFactory.createCompoundBorder(
            card.getBorder(), // RoundBorder ya existente
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // padding interno
        ));
        card.setLayout(new BorderLayout());
        card.setOpaque(false); // Transparente para que no tenga background

        
        // Redimensionar el icono de la mesa
        Image img = tableIcon.getImage();
        Image resizedImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(resizedImg));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel de información
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

        // Tamaño preferido más pequeño
        card.setPreferredSize(new Dimension(200, 200));

        return card;
    }


}
