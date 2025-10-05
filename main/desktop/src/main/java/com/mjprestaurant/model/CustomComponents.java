package com.mjprestaurant.model;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class CustomComponents {
    private static JButton customButton;

    public void setCustomButton (String text){
        customButton = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fons i border
                g2.setColor(new Color(0, 128, 0)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            public boolean isContentAreaFilled() {
                // Es necessari per a que el fons del botó no es repinti amb les opcions per defecte
                return false;
            }

        };

        customButton.setBorder(BorderFactory.createEmptyBorder());

        // Preferencies del botó
        customButton.setForeground(Color.WHITE);
        customButton.setFont(new Font("Arial", Font.BOLD, 14));
        customButton.setPreferredSize(new Dimension(150, 30));
        customButton.setMargin(new Insets(10, 32, 10, 32));
        customButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        customButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public static JButton getCustomButton(){
        return customButton;
    }
}
