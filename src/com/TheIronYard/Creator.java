package com.TheIronYard;
import java.util.Scanner;

// Create a class named "Creator" to create work orders
public class Creator {
    public void createWorkOrders() throws InterruptedException {

        // Allow user to input new information
        //Scanner class is a simple text scanner which can parse primitive types and strings using regular expressions
        Scanner input = new Scanner(System.in);

        while(true){
            System.out.println("******** WELCOME TO THE WORK ORDER *************");
            System.out.println("WHO IS REQUESTING THIS WORK ORDER");
            String requestor = input.nextLine();

            System.out.println("THANK YOU");
            Thread.sleep(500);

            System.out.println("WHAT DO YOU WISH FOR?");
            String thing = input.nextLine();

            System.out.println("YOUR REQUEST HAS BEEN NOTED");
            WorkOrder wo = new WorkOrder(thing, requestor);
            wo.persist();
        }
    }

    // In Creator, have a public static void main that creates an instance of Creator and calls the instance method that
    // loops to get the user input and create work order files.
    public static void main(String args[]) throws InterruptedException {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}

/*
SIDENOTE:

-   A private field is only accessible within the same class as it is declared.
-   A member with no access modifier is only accessible within classes in the same package.
-   A protected field is accessible within all classes in the same package and within subclasses in other packages.
-   A public field is accessible to all classes (unless it resides in a module that does not export the package it is declared in).
*/



