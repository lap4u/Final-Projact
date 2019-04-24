package com.company;

import Parts.OS;
import Parts.PartStruct;
import Parts.Storage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class LG {
    private static DecimalFormat decmialFormat = new DecimalFormat("#.##");
    public LG(){}


    public static void FindLGLaptops(ArrayList<Laptop> i_ArrLaptops) {

        Laptop laptop;
        final String site_url = "https://www.lg.com";
        final String main_url = "https://www.lg.com/us/laptops/view-all";


        try {
            final Document document = Jsoup.connect(main_url).get();
            Elements comp_urls = document.select("p.model-name.redot");
            System.out.println("size of List: " + comp_urls.size());

            for (Element comp : comp_urls) {
                comp = comp.select("a").first();
                String atar = comp.attr("href");
                String product_url = site_url + atar;

                laptop = compSaveLG(product_url, i_ArrLaptops.size());
                i_ArrLaptops.add(laptop);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public static Laptop compSaveLG(String url, int id_laptop) {

        final String site_url = "https://www.lg.com";
        Laptop laptop = null;

        try {
            final Document document2 = Jsoup.connect(url).get();
            final String companyName = "LG";

            final String modelName = document2.select("h2.improve-info-model").text();

            //Processor
            final String processor = document2.select("li#SP07578911.full").select("p.value").text() + document2.select("li#SP06236899").select("p.value").text();
            String processorManufacture;
            String processorModel = " ";
            String[] splitCPU = processor.split(" ");

            if(processor.contains("Intel"))
                processorManufacture = "Intel";
            else
                processorManufacture = "AMD";

            for(int i=0;i<splitCPU.length;i++)
            {
                // Because LG work only with Intel i3\i5\i7 so its fine with this if.
                if(splitCPU[i].contains("i3") || splitCPU[i].contains("i5") || splitCPU[i].contains("i7"))
                {
                    processorModel = splitCPU[i];
                }
            }

            PartStruct CPU = new PartStruct(processorManufacture, processorModel);


            // Memory
            String memoryString = document2.select("li#SP07578915.full").select("p.value").text() + document2.select("li#SP06236902").select("p.value").text();
            memoryString = memoryString.replaceAll("GB", "");
            String[] splitMemory = memoryString.split(" ", 2);
            int memory = Integer.parseInt(splitMemory[0]);


            // Screen Size:
            String screenSizeString = document2.select("li#SP06236908").select("p.value").text();
            screenSizeString = screenSizeString.replace("\"", "");
            double screenSize = Double.parseDouble(screenSizeString);


            // OS
            final String operatingSystemString = document2.select("li#SP06236896.full").select("p.value").text();
            String[] splitOS = operatingSystemString.split(" ");
            String OS_Manufacture = splitOS[0];
            String OS_Version = splitOS[2];
            int OS_Serie = Integer.parseInt(splitOS[1]);
            int OS_Bit;
            if(splitOS[3].contains("64"))
                OS_Bit = 64;
            else
                OS_Bit = 32;

            OS operatingSystem = new OS(OS_Manufacture,OS_Version, OS_Serie, OS_Bit);



            // Graphic Card
            final String graphicCardString = document2.select("li#SP06236911").select("p.value").text();
            String[] splitGPU = graphicCardString.split(" ", 2);
            String manufactureGPU = splitGPU[0].replaceAll("®", "");
            String modelGPU = splitGPU[1];
            PartStruct GPU = new PartStruct(manufactureGPU, modelGPU);



            // Weight
            String weightString = document2.select("li#SP07591580").select("p.value").text() + document2.select("li#SP06236945").select("p.value").text();
            String[] splitWeight = weightString.split(" ", 2);
            double weight = 0.45 * Double.parseDouble((splitWeight[0]));
            weight = Double.parseDouble(decmialFormat.format(weight));


            final String battery = document2.select("li#SP06236935").select("p.value").text() + document2.select("li#SP07707838").select("p.value").text() + document2.select("li#SP07796758").select("p.value").text();
            final String imgURL = site_url + document2.select("div.pdp-improve-visual-img").select("img").first().attr("src");


            // Price
            double price;
            String priceString = document2.select("div.price-default.flag").select("p.price").text();
            if(priceString.equals(""))
                price = 0;
            else {
               priceString = priceString.replaceAll("\\$", "");
                price = Double.parseDouble(priceString);
            }

            // Storage Calculate

            boolean isSSD;
            final String storageType = document2.select("li#SP06236906").select("p.value").text();
            if(storageType.contains("SSD"))
                isSSD = true;
            else
                isSSD = false;

            int storageCapacity;
            String storageCapacityString = document2.select("li#SP06236907").select("p.value").text();
            String onlyGBString;
            String[] splitStorage = storageCapacityString.split(" ", 2);
            if(splitStorage[0].contains("TB"))
            {
                onlyGBString = splitStorage[0].replaceAll("TB", "");
                storageCapacity = 1024 * Integer.parseInt(onlyGBString);
            }
            else
            {
                onlyGBString = splitStorage[0].replaceAll("GB", "");
                storageCapacity = Integer.parseInt(onlyGBString);
            }

            Storage storageObject = new Storage(isSSD, storageCapacity);


            // Desc
            final String desc = document2.select("div.text-block").first().select("p").text();

            // Check Touch Screen
            String touchScreen = document2.select("li#SP07382786").select("p.value").text();
            Boolean isTouchScreen;
            if(touchScreen.contains("Yes") == true)
                isTouchScreen = true;
            else {
                isTouchScreen = false;
            }



            // Only Build The Object.
            laptop = new Laptop(id_laptop, modelName, url, companyName, CPU, memory, operatingSystem, GPU, storageObject, screenSize, weight, battery, isTouchScreen, price, imgURL, desc);


            //Prints
              /*
                           System.out.println("URL: " + url);
            System.out.println("Company name: " + companyName);
            System.out.println("Laptop name: " + modelName);
           System.out.println("Processor Manufacture: " + processorManufacture);
            System.out.println("Processor Model: " + processorModel);
 System.out.println("Memory: " + memory);
             System.out.println("Screen Size: " + screenSize);
            System.out.println("Operating System: " + operatingSystemString);
            System.out.println("Operating Model: " + OS_Manufacture);
            System.out.println("Operating Version " + OS_Version);
            System.out.println("Operating Serie: " + OS_Serie);
            System.out.println("Operating Bit: " + OS_Bit);
        System.out.println("Storage: " + storageFull);
   System.out.println("Weight: " + weight);
          System.out.println("Battery: " + battery);
  System.out.println("Price: " + price);
            System.out.println("IMG URL: " + imgURL);
             System.out.println("Touch Screen: " + isTouchScreen);
            System.out.println("Desc: " + desc);
                        System.out.println("Is SSD? " + isSSD);
            System.out.println("Storage Capacity: " + storageCapacity);
               System.out.println("Graphic Card Manufacture: " + manufactureGPU);
            System.out.println("Graphic Card Model: " + modelGPU);
            */

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }
}
