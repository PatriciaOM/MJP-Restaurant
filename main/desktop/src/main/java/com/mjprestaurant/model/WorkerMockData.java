package com.mjprestaurant.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mjprestaurant.model.Worker.Shift;


public class WorkerMockData {

    public static List<Worker> getMockWorkers() {
        List<Worker> workers = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            workers.add(new Worker("12345678A", "Jordi", "Pérez Soler",
                    sdf.parse("01/01/2023"), null, Shift.MORNING));
            workers.add(new Worker("87654321B", "Marta", "López García",
                    sdf.parse("15/02/2023"), null, Shift.AFTERNOON));
            workers.add(new Worker("11223344C", "Pau", "Ribas Font",
                    sdf.parse("10/03/2023"), null, Shift.FULL_TIME));
            workers.add(new Worker("44332211D", "Laura", "Castells Vidal",
                    sdf.parse("20/04/2023"), null, Shift.MORNING));
            workers.add(new Worker("99887766E", "Carles", "Ortega Ruiz",
                    sdf.parse("01/05/2023"), null, Shift.AFTERNOON));
            workers.add(new Worker("66778899F", "Núria", "Viladomat Pujol",
                    sdf.parse("15/05/2023"), null, Shift.FULL_TIME));
            workers.add(new Worker("55554444G", "Arnau", "Giménez Torres",
                    sdf.parse("01/06/2023"), null, Shift.MORNING));
            workers.add(new Worker("22221111H", "Clara", "Morales Puig",
                    sdf.parse("10/06/2023"), null, Shift.AFTERNOON));
            workers.add(new Worker("99990000J", "Sergi", "Reig Ferrer",
                    sdf.parse("20/06/2023"), null, Shift.FULL_TIME));
            workers.add(new Worker("11112222K", "Anna", "Martí Costa",
                    sdf.parse("01/07/2023"), null, Shift.MORNING));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workers;
    }
}

