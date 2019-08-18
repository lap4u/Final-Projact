package com.company;
import Parts.OS;
import Positions.Position;
import Softwares.FindGames;
import Softwares.SoftwareGame;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        CreateHtmlPages.startCreate();

        /*
         ArrayList<SoftwareGame> gamesArray = new ArrayList<>();
        FindGames.FindGames(gamesArray);
        System.out.println("Size Games Array: " + gamesArray.size());
        //printAllGames(gamesArray);
        SoftwareGame[] a = Softwares.ParseJsonToArray.Parse();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (SoftwareGame c :   gamesArray)
        {
            em.persist(c);
        }
        for (SoftwareGame b : a) {
            em.persist(b);
        }
        em.getTransaction().commit();
        */
/*
        ArrayList<Laptop> LaptopsArray = new ArrayList<Laptop>();
        Microsoft.Find_Laptops(LaptopsArray); // Lenovo + ASUS + MSI + Ryzer + HP
        System.out.println("Microsoft is ok!");
        Acer.Find_Laptops(LaptopsArray);
        System.out.println("Acer is ok!");
        Dell.FindDellLaptops(LaptopsArray);
        System.out.println("Dell is ok!");
        LG.FindLGLaptops(LaptopsArray);
        System.out.println("LG is ok!");
        System.out.println("Finish pull the computers!");
        // Can Print the laptops: printAllLaptops(LaptopsArray);
        // Now this time: "LaptopsArray" contained all laptops
        System.out.println("Total Comps: " + LaptopsArray.size());

*/
        // Now We build the GPU \ CPU Positions List From json.
        //ArrayList<Position> gpuPositions = new ArrayList<>();
        //ArrayList<Position> cpuPositions = new ArrayList<>();
        // Action the method that convert the json file to the arrayLists.

        // Now we pull the games from the games site.
    }

    private static void printAllLaptops(List<Laptop> laptopArray) {
        for (Laptop laptop : laptopArray) {
            laptop.printLaptop();
        }
    }

    private static void printAllGames(List<SoftwareGame> gamesArray) {
        for (SoftwareGame game : gamesArray) {
            game.printGame();
        }
    }



    }

//List<Struct> CPU_List = new ArrayList<Struct>();
//List<Struct> GPU_List = new ArrayList<Struct>();
// FindGames.FindGames();
// createHtmlFiles(LaptopArray2);
// CreateJSONFile.writeList(LaptopArray2);

//ParseJsonToArray.Parse();
// GPU_List = Data.PartsData.Parse_Data("https://www.videocardbenchmark.net/gpu_list.php");
// CPU_List = Data.PartsData.Parse_Data("https://www.cpubenchmark.net/cpu_list.php");
//  DB dataBase = new DB();

//EntityManager entityManager =  dataBase.getEntityManager();
//entityManager.getTransaction().begin();
// for (int i = 0; i < LaptopArray2.size(); i++) {
// Laptop laptop = LaptopArray2.get(i);
//entityManager.persist(laptop);
// }

//   entityManager.getTransaction().commit();

//TypedQuery<Laptop> query = entityManager.createQuery("SELECT laptop FROM Laptop laptop WHERE laptop.touch_screen = true ", Laptop.class);

//        List<Laptop> results = query.getResultList();
//      for (Laptop p : results) {
//       p.printLaptop();
//  }

//  CreateJSONFile_Parts.WriteToJsonFile(CPU_List,"cpu_list.json");
//  CreateJSONFile_Parts.WriteToJsonFile(GPU_List,"gpu_list.json");