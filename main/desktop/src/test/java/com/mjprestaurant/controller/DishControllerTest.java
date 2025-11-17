package com.mjprestaurant.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.view.DishFrame;
import com.mjprestaurant.view.LoginFrame;

/**
 * Tests unitarios para DishController sin usar mocks.
 */
public class DishControllerTest {

    private static class DummyDishFrame extends DishFrame {
        public DummyDishFrame(String title, List<Dish> dishes) {
            super(title, dishes);
        }

        private JButton btnAdd = new JButton();
        private JButton btnDelete = new JButton();

        @Override
        public JButton getButtonAdd() { return btnAdd; }

        @Override
        public JButton getButtonDelete() { return btnDelete; }

        @Override
        public void initLogout(LoginFrame login) { /* no hace nada */ }

        @Override
        public void reloadDishesTable(String token) { /* no hace nada */ }
    }

    private DummyDishFrame dishFrame;
    private LoginFrame loginFrame;
    private DishController controller;

    @BeforeEach
    public void setUp() {
        dishFrame = new DummyDishFrame("Test", null);
        loginFrame = new LoginFrame();

        // Token dummy
        controller = new DishController(dishFrame, loginFrame, "dummyToken");
    }

    @Test
    public void testConstructor() {
        assertNotNull(controller);
    }

    @Test
    public void testActionPerformedAddButton() {
        ActionEvent event = new ActionEvent(dishFrame.getButtonAdd(), ActionEvent.ACTION_PERFORMED, "add");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testActionPerformedDeleteButton() {
        ActionEvent event = new ActionEvent(dishFrame.getButtonDelete(), ActionEvent.ACTION_PERFORMED, "delete");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testActionPerformedUnknownSource() {
        JButton unknownButton = new JButton();
        ActionEvent event = new ActionEvent(unknownButton, ActionEvent.ACTION_PERFORMED, "unknown");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testCreateDishMethod() {
        assertDoesNotThrow(() -> controller.createDish());
    }

    @Test
    public void testDeleteDishMethod() {
        assertDoesNotThrow(() -> controller.deleteDish("dummyToken"));
    }
}
