package com.company;

import Data.Struct;
import Softwares.FindGames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Laptop> LaptopArray = new ArrayList<Laptop>();
        List<Struct> CPU_List = new ArrayList<Struct>();
        List<Struct> GPU_List = new ArrayList<Struct>();
        ArrayList<Laptop> LaptopArray2 = new ArrayList<Laptop>();
        Lenovo.Find_Laptops(LaptopArray2);
        //Acer.Find_Laptops(LaptopArray);
      //  Dell.FindDellLaptops(LaptopArray2);
       //Dell.FindDellLaptops(LaptopArray2);
       //LG.FindLGLaptops(LaptopArray2);
        //Acer.Find_Laptops(LaptopArray2);
        printAllLaptops(LaptopArray2);
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
    }


    static void HP_comp()
    {
        final String url="https://store.hp.com/us/en/vwa/laptops/segm=Home?jumpid=ma_lt_featured_na_6_181216";
        try {
            final Document document= Jsoup.connect(url).get();
            for(Element row : document.select(
                    "div.specsContent")){
                final String ticker=row.select("div.pdtName").text();
                //final String price =row.select("div.specsContent").text();
                //System.out.println(price);
                System.out.println("\n\n*********************************");

                System.out.println("Laptop: " + ticker);

                System.out.println("*********************************");
                for(Element att : row.select(
                        "div.pdtSpecsAttr")){
                    final String attri=att.select("div.attr").text();
                    final String valatt=att.select("div.attrVal").text();
                    if(attri.equals("Operating system")||attri.equals("Storage") ||attri.equals("Processor and graphics")||attri.equals("Memory")) {
                        System.out.println(att.select("div.attr").text() + ": " + att.select("div.attrVal").text());
                    }
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }



    private static void createHtmlFiles(List<Laptop> laptopArray) {
        for(int i=0;i<5;i++) {
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
                    "<img src=\"" + laptop.getImg_url() + "\" alt=\"laptop img\" height=\"400px\" width=\"auto\">" +
                    "<div class = \"description\">" + laptop.getDescription() + "</div>" +
                    "<div class=\"price\">" + laptop.getPrice() + "$</div>"+
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

    private static void printAllLaptops(List<Laptop> laptopArray) {
        for (Laptop laptop : laptopArray)
        {
            laptop.printLaptop();
        }
    }

    private static void printAllExist(List<Laptop> laptopArray,List<Struct> PartsList,String whichPart) {
        int count;
        List<Laptop> notFound=new ArrayList<Laptop>();
        List<Laptop> Found=new ArrayList<Laptop>();
        List<String> allreadyPrinted = new ArrayList<String>();

        for (Laptop laptop : laptopArray)
        {
            count = 0;
            if(laptop.getGpu().getModel().equals("Radeon R5 Graphics"))
            {
                System.out.println(laptop.getUrl_model());
            }
//            System.out.println("######################################\n");
//            if(whichPart.equals("CPU"))
//            {
//                System.out.println(laptop.getProcessor().getManufacture() + " "
//                    + laptop.getProcessor().getModel());
//            }
//            else
//            {
//                System.out.println(laptop.getGpu().getManufacture() + " "
//                        + laptop.getGpu().getModel());
//            }
//            System.out.println("#######\n");
            for(Struct part : PartsList) {
                if(whichPart.equals("CPU")) {
                    if (part.getName().contains(laptop.getProcessor().getModel())) {
                        count += 1;
                        //System.out.println(part.getName() + "............" + part.getPlace());
                    }
                }
                else
                {
                    if (part.getName().contains(laptop.getGpu().getModel())) {
                        count += 1;
                        //System.out.println(part.getName() + "............" + part.getPlace());
                    }
                }

            }
            //System.out.println("######################################\n");
            if(count==0)
                notFound.add(laptop);
            else
                Found.add(laptop);

        }
        boolean isther;
        for(Laptop laptop : notFound)
        {
            isther=false;
            for(String not : allreadyPrinted)
            {
                if(whichPart.equals("CPU")) {
                    if (laptop.getProcessor().getModel().equals(not)) {
                        isther = true;
                    }
                }
                else
                {
                    if (laptop.getGpu().getModel().equals(not)) {
                        isther = true;
                    }
                }
            }
            if(!isther) {
                if(whichPart.equals("CPU")) {
                    allreadyPrinted.add(laptop.getProcessor().getModel());
                    System.out.println(laptop.getProcessor().getModel());
                }
                else
                {
                    allreadyPrinted.add(laptop.getGpu().getModel());
                    System.out.println(laptop.getGpu().getModel());
                }
            }
        }
    }
}
