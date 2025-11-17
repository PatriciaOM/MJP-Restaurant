package com.mjprestaurant.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.WorkerFrame;

/**
 * Tests unitarios para WorkerController sin usar mocks.
 */
public class WorkerControllerTest {

    // Dummy WorkerFrame para evitar NPE
    private static class DummyWorkerFrame extends WorkerFrame {

        private JButton btnAdd = new JButton();
        private JButton btnDelete = new JButton();

        public DummyWorkerFrame() {
            super("dummy", List.of()); // título dummy, lista vacía, controller null
        }

        @Override
        public JButton getButtonAdd() { return btnAdd; }

        @Override
        public JButton getButtonDelete() { return btnDelete; }

        @Override
        public void initLogout(LoginFrame login) { /* no hace nada */ }

        @Override
        public void reloadUsersTable(String token) { /* no hace nada */ }
    }

    private DummyWorkerFrame workerFrame;
    private LoginFrame loginFrame;
    private WorkerController controller;

    @BeforeEach
    public void setUp() {
        workerFrame = new DummyWorkerFrame();
        loginFrame = new LoginFrame();
        controller = new WorkerController(workerFrame, loginFrame, "dummyToken");
    }

    @Test
    public void testConstructor() {
        assertNotNull(controller);
        assertEquals("dummyToken", controller.getToken());
    }

    @Test
    public void testActionPerformedAddButton() {
        ActionEvent event = new ActionEvent(workerFrame.getButtonAdd(), ActionEvent.ACTION_PERFORMED, "add");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testActionPerformedDeleteButton() {
        ActionEvent event = new ActionEvent(workerFrame.getButtonDelete(), ActionEvent.ACTION_PERFORMED, "delete");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testActionPerformedUnknownSource() {
        JButton unknownButton = new JButton();
        ActionEvent event = new ActionEvent(unknownButton, ActionEvent.ACTION_PERFORMED, "unknown");
        assertDoesNotThrow(() -> controller.actionPerformed(event));
    }

    @Test
    public void testCreateWorkerMethod() {
        assertDoesNotThrow(() -> controller.createWorker());
    }

    @Test
    public void testDeleteWorkerMethod() {
        assertDoesNotThrow(() -> controller.deleteWorker("dummyToken"));
    }

    @Test
    public void testEditWorkerMethod() {
        // Como getAllWorkers hace llamada HTTP, no probamos la parte de conexión
        // Solo verificamos que no lance excepciones con ID dummy
        assertDoesNotThrow(() -> controller.editWorker(1));
    }

}
