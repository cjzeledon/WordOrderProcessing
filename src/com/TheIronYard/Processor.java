package com.TheIronYard;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Processor {

    /*
    The Map interface maps unique keys to values. A key is an object that you use to retrieve a value at a later date.
    Given a key and a value, you can store the value in a Map object. After the value is stored,
    you can retrieve it by using its key.
     */

    Map<Status, Set<WorkOrder>> orders = new HashMap<>();

    // This is your constructor
    public Processor(){

        /*
        This method (putIfAbsent) is being used to insert a new a new key-value mapping to the hashmap object if the
        respective id is not yet used. A value null will be returned if the key-value mapping is successfully added to
        The Hash Map object while if the id is already present on the hashmap the value which is already existing will
        returned instead.
        */

        orders.putIfAbsent(Status.INITIAL, new HashSet<>());
        orders.putIfAbsent(Status.ASSIGNED, new HashSet<>());
        orders.putIfAbsent(Status.IN_PROGRESS, new HashSet<>());
        orders.putIfAbsent(Status.DONE, new HashSet<>());
    }

    // Run forever in a loop. Have the loop sleep for a five seconds (or longer).
    public void processWorkOrders() throws FileNotFoundException {
        while (true) {
            // print out our map
            System.out.println(orders);
            moveIt();
            readIt();

            sleepforFiveSeconds();
            System.out.println(orders);
        }
    }

    private void sleepforFiveSeconds(){
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveIt() {
        // move work orders in map from one state to another
        moveOrders(Status.IN_PROGRESS, Status.DONE);
        moveOrders(Status.ASSIGNED, Status.IN_PROGRESS);
        moveOrders(Status.INITIAL, Status.ASSIGNED);
    }

    private void moveOrders(Status initial, Status next) {
        // set of orders from the initial status
        Set<WorkOrder> initialSet = orders.get(initial);

        // set of orders from the next status
        Set<WorkOrder> nextSet    = orders.get(next);

        // for each order in the initial set,
        for (WorkOrder order : initialSet) {
            // update that order's status
            order.setStatus(next);

            // add that order to the next set.
            nextSet.add(order);
        }

        // replace the hashset in the initial status
        orders.put(initial, new HashSet<>());
    }

    private void readIt() throws FileNotFoundException {
        // read the json files into WorkOrders and put in map
        File currentDirectory = new File(".");
        File files[] = currentDirectory.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".json")){
                // f is a reference to a json file

                // 1. use an object mapper to read in a WorkOrder
                ObjectMapper mapper = new ObjectMapper();

                // 1a. Read in the entire contents of the file
                Scanner fileScanner = new Scanner(f);

                // \\Z is END OF FILE DELIMETER
                fileScanner.useDelimiter("\\Z");

                // since we updated our delimeter to END OF FILE
                // when we call next, we get the _entire_ file.
                String fileContents = fileScanner.next();

                try {
                    WorkOrder wo = mapper.readValue(fileContents, WorkOrder.class);

                    // 2. put the work order in our orders map.
                    // get the set at the current status and
                    // add the work order to the set.
                    orders.get(wo.getStatus()).add(wo);

                    // 3. delete the file
                    f.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) throws FileNotFoundException {
        Processor processor = new Processor();
        processor.processWorkOrders();
    }
}


/*
SIDENOTE:

-   A private field is only accessible within the same class as it is declared.
-   A member with no access modifier is only accessible within classes in the same package.
-   A protected field is accessible within all classes in the same package and within subclasses in other packages.
-   A public field is accessible to all classes (unless it resides in a module that does not export the package it is declared in).
*/