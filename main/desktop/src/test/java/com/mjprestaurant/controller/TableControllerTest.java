package com.mjprestaurant.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.TableFrame;
import com.mjprestaurant.model.table.TableRestaurant;

/**
 * Tests unitarios para TableController sin usar mocks.
 */
public class TableControllerTest {

    private static class DummyTableFrame extends TableFrame {
        private JButton btnAdd = new JButton();
        private JButton btnDelete = new JButton();

        public DummyTableFrame() {
            super("dummy", List.of(), null);
        }

        @Override
        public JButton getButtonAdd() { return btnAdd; }

        @Override
        public JButton getButtonDelete() { return btnDelete; }

        @Override
        public void initLogout(LoginFrame login) { /* no hace nada */ }

        @Override
        public void reloadTables(String token) { /* no hace nada */ }
    }

    private DummyTableFrame tableFrame;
    private LoginFrame loginFrame;
    private TableController controller;

    @BeforeEach
    public void setUp() {
        tableFrame = new DummyTableFrame();
        loginFrame = new LoginFrame();
        controller = new TableController(tableFrame, loginFrame, "dummyToken");
    }

    @Test
    public void testConstructor() {
        assertNotNull(controller);
        assertEquals("dummyToken", controller.getToken());
    }

    @Test
    public void testActionPerformedAddButton() {
        ActionEvent event = new ActionEvent(tableFrame.getButtonAdd(), ActionEvent.ACTION_PERFORMED, "add");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testActionPerformedDeleteButton() {
        ActionEvent event = new ActionEvent(tableFrame.getButtonDelete(), ActionEvent.ACTION_PERFORMED, "delete");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testActionPerformedUnknownSource() {
        JButton unknownButton = new JButton();
        ActionEvent event = new ActionEvent(unknownButton, ActionEvent.ACTION_PERFORMED, "unknown");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testCreateTableMethod() {
        assertDoesNotThrow(() -> controller.createTable());
    }

    @Test
    public void testDeleteTableMethod() {
        assertDoesNotThrow(() -> controller.deleteTable("dummyToken"));
    }

    @Test
    public void testEditTableMethod() {
        TableRestaurant table = new TableRestaurant(1, 4);
        assertDoesNotThrow(() -> controller.editTable(table));
    }

}
