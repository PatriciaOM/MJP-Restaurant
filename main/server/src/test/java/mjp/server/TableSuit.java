package mjp.server;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import mjp.server.ServerMJP.DishManagerTest;
import mjp.server.ServerMJP.OrderItemManagerTest;
import mjp.server.ServerMJP.OrderManagerTest;
import mjp.server.ServerMJP.SessionServiceManagerTest;
import mjp.server.ServerMJP.TableManagementStatusTest;
import mjp.server.ServerMJP.TableManagementTest;
import mjp.server.ServerMJP.UserManagementTest;
import mjp.server.ServerMJP.ServerMjpApplicationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.platform.suite.api.SelectClasses;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


/**
 *
 * @author twiki
 */
@Suite
@SelectClasses({
//    DishManagerTest.class,
//    OrderItemManagerTest.class,
//    OrderManagerTest.class,
//    ServerMjpApplicationTest.class,
//    SessionServiceManagerTest.class,
    TableManagementTest.class,
//    UserManagementTest.class,
    TableManagementStatusTest.class
})
public class TableSuit {}
