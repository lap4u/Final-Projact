package com.company;

import javax.persistence.*;
import java.util.*;
import java.io.Serializable;

public class DB {

    private  EntityManagerFactory entityManagerFactory;
    private  EntityManager entityManager;
    private  String Path = "$objectdb/db/lap4u/Final-Projact.odb";

    // Open a database connection
    // (create a new database if it doesn't exist yet):
    DB(){
        entityManagerFactory = Persistence.createEntityManagerFactory(Path);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void closeDB(){
        entityManager.close();
        entityManagerFactory.close();
    }

        // Store 1000 Point objects in the database:
       // entityManager.getTransaction().begin();

        //entityManager.persist(p);

       // entityManager.getTransaction().commit();

        // Retrieve all the Point objects from the database:

        // TypedQuery<Point> query = entityManager.createQuery("SELECT p FROM Point p", Point.class);
        //List<Point> results = query.getResultList();
        // for (Point p : results) {
        //   System.out.println(p);
        //}

        // Close the database connection:
}
