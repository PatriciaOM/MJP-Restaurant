package com.mjprestaurant.model;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 * Classe per crear components custom reutilitzables
 * @author Patricia Oliva
 */
public class CustomComponents {
    private static JButton customButton;
    private static JTextField customTextField;
    private static JPasswordField customPwdField;

    //BOTONS
    /**
     * Inicialitza un botó custom amb el text passat per paràmetre
     * D'aquesta manera obtenim botons amb el mateix estil, per treballar fàcilment la coherència
     * @param text Text del botó
     */
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

    /**
     * Retorna el botó custom
     * @return botó amb estil predeterminat
     */
    public static JButton getCustomButton(){
        return customButton;
    }

    //JTEXTFIELD
    /**
     * Mètode per setejar els TextField amb un estil estandaritzat
     */
    public static void setCustomTextField() {
       customTextField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fons arrodonit
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Border
                g2.setColor(new Color(180, 180, 180));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        // Estil estàndard
        setSyles(customTextField);
    }

    /**
     * Retorna el JTextField custom
     * @return textfield amb estil predeterminat
     */
    public static JTextField getCustomTextField() {
        return customTextField;
    }

    /**
     * Mètode per setejar els PasswordField amb un estil estandaritzat
     */
    public static void setCustomPasswordField() {
        customPwdField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fons arrodonit
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Border
                g2.setColor(new Color(180, 180, 180));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        //Estil estàndard
        setSyles(customPwdField);
    }

    /**
     * Retorna el JPasswordField custom
     * @return pwdfield amb estil predeterminat
     */
    public static JPasswordField getCustomPwdField() {
        return customPwdField;
    }

    /**
     * Mètode que seteja tots els estils predeterminats als components de tipus JText
     * D'aquesta manera refactoritzem els estils de tots els JTextField i els JPasswordField
     * @param component field al que volem aplicar els estils per default
     */
    private static void setSyles(JTextComponent component){
        component.setOpaque(false);
        component.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        component.setFont(new Font("Arial", Font.PLAIN, 14));
        component.setBackground(new Color(245, 245, 245));
        component.setPreferredSize(new Dimension(200, 30));
        component.setForeground(Color.DARK_GRAY);
        component.setCaretColor(Color.DARK_GRAY);
    }

}
