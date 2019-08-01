package com.company;

import Positions.Position;
import Softwares.FindGames;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


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


        // Now We build the GPU \ CPU Positions List From json.
        ArrayList<Position> gpuPositions = new ArrayList<>();
        ArrayList<Position> cpuPositions = new ArrayList<>();
        // Action the method that convert the json file to the arrayLists.

        // Now we pull the games from the games site.
        // FindGames.FindGames();
    }

    private static void printAllLaptops(List<Laptop> laptopArray) {
        for (Laptop laptop : laptopArray) {
            laptop.printLaptop();
        }
    }

    private static void createHtmlFiles(List<Laptop> laptopArray) {
        for (int i = 0; i < 5; i++) {
            Laptop laptop = laptopArray.get(i);
            String html = "<html>" +
                    "<head>" +
                    "<title>" + laptop.getCompany_name() + ": " + laptop.getModel_name() + "</title>" +
                    "<link rel=\"stylesheet\" href=\"test.css\">" +
                    "<link href=\"https://fonts.googleapis.com/css?family=Ubuntu\" rel=\"stylesheet\">" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"headline\"><h1>" + laptop.getCompany_name() + ": " + laptop.getModel_name() + "</h1></div><br><br>" +
                    "<div>" +
                    "<img src=\"" + laptop.getImagesUrls().get(0) + "\" alt=\"laptop img\" height=\"400px\" width=\"auto\">" +
                    "<div class = \"description\">" + laptop.getDescription() + "</div>" +
                    "<div class=\"price\">" + laptop.getPrice() + "$</div>" +
                    "</div>" +
                    "<br><br><table>" +
                    "<tr><td class=\"title\">Processor</td><td class=\"value\">" + laptop.getProcessor().getManufacture() + " " + laptop.getProcessor().getModel() + "</td></tr>" +
                    "<tr><td class=\"title\">Memory</td><td class=\"value\">" + laptop.getMemory() + " GB</td></tr>" +
                    "<tr><td class=\"title\">Operation System</td><td class=\"value\">" + laptop.getOperation_system().getManufacture() + " " + laptop.getOperation_system().getSeries() + " " + laptop.getOperation_system().getVersion() + " " + laptop.getOperation_system().getBitSize() + "</td></tr>" +
                    "<tr><td class=\"title\">Graphic Card</td><td class=\"value\">" + laptop.getGpu().getManufacture() + " " + laptop.getGpu().getModel() + "</td></tr>" +
                    "<tr><td class=\"title\">Screen Size</td><td class=\"value\">" + laptop.getScreen_size() + "\"</td></tr>" +
                    "<tr><td class=\"title\">Storage</td><td class=\"value\">" + laptop.getStorage() + " GB</td></tr>" +
                    "<tr><td class=\"title\">Weight</td><td class=\"value\">" + laptop.getWeight() + " kg</td></tr>" +
                    "</td></tr></table><br><br>" +
                    "<button onclick=\"window.location.href = '" + laptop.getUrl_model() + "';\">Click Here</button></center></body></html>";
            File newHtmlFile = new File("laptop" + i + ".html");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(newHtmlFile));
                bw.write(html);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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