package com.TheIronYard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// WORK ORDER JAVA BEAN
public class WorkOrder {
    private int id;
    static int STATIC_ID;
    private String description;
    private String senderName;
    private Status status;

    public WorkOrder() {
    }

    public WorkOrder(String description, String senderName) {
        // Set an ID for the current work order
        this.id = STATIC_ID++;

        //Set the STATUS TO INITIAL
        this.status = status.INITIAL;

        this.description = description;
        this.senderName = senderName;

        // Write this object to a file
        persist();
    }

    void persist(){

        /*
        The simplest way to parse JSON with Jackson is via the Jackson databind ObjectMapper
        (com.fasterxml.jackson.databind.ObjectMapper). Jackson's ObjectMapper can parse a JSON from a string, stream
        or file, and create an object graph representing the parsed JSON.
        */

        ObjectMapper mapper = new ObjectMapper();

        try {
            String objString = mapper.writeValueAsString(this);

            // Save object json string ot disk
            File f = new File(this.id + ".json");
            FileWriter fw = new FileWriter(f);

            // write this object, as json, to the file using file writer
            fw.append(objString);

            fw.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", senderName='" + senderName + '\'' +
                ", status=" + status +
                '}';
    }
}



/*
SIDENOTE:

-   A private field is only accessible within the same class as it is declared.
-   A member with no access modifier is only accessible within classes in the same package.
-   A protected field is accessible within all classes in the same package and within subclasses in other packages.
-   A public field is accessible to all classes (unless it resides in a module that does not export the package it is declared in).
*/
