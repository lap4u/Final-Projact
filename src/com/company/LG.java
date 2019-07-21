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
            Elements comp_urls = document.select("div#resultAppendTarget.product-list-box.js-model-switcher").select("ul.list-box");
            comp_urls = comp_urls.select("div.item.js-model");
            //System.out.println("size of List: " + comp_urls.size());

            for (Element comp : comp_urls) {
                comp = comp.select("a").first();
                String atar = comp.attr("href");
                String product_url = site_url + atar;
            //    System.out.println(product_url);
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



            final String modelName = document2.select("h1.model-title").text();

             String processor = "",  memoryString = "", screenSizeString = "", operatingSystemString = "", graphicCardString = "";
             String weightString = "",  battery = "", touchScreen = "", storageType = "", storageCapacityString = "";;

            Elements attributes = document2.select("div.tech-spacs");
            for (Element attr : attributes) {
              String h2 = attr.select("h2").text();
                if(h2.equals("PROCESSOR"))
                {
                   processor =  attr.select("dd").text();
                }
                else
                    if(h2.equals("MEMORY"))
                {
                    memoryString =  attr.select("dd").text();
                }
                    else
                    if(h2.equals("DISPLAY"))
                    {
                        screenSizeString =  attr.select("dd").first().text();
                        graphicCardString = attr.select("dd").eq(3).text();
                    }
                    else
                    if(h2.equals("OPERATING SYSTEM"))
                    {
                        operatingSystemString =  attr.select("dd").text();
                    }
                    else
                    if(h2.equals("DIMENSIONS/WEIGHT"))
                    {
                        weightString =  attr.select("dd").eq(2).text();
                    }
                    else
                    if(h2.equals("BATTERY/POWER"))
                    {
                        battery =  attr.select("dd").first().text();
                    }
                    else
                    if(h2.equals("FEATURES"))
                    {
                      for(int i=0;i<attr.select("dt").size();i++)
                      {
                          if(attr.select("dt").eq(i).text().equals("Touchscreen LCD"))
                          {
                              touchScreen = attr.select("dd").eq(i).text();
                          }
                      }
                    }
                    else
                    if(h2.equals("HARD DRIVE"))
                    {
                        storageType = attr.select("dd").first().text();
                        for(int i=0;i<attr.select("dt").size();i++)
                        {
                            if(attr.select("dt").eq(i).text().equals("Capacity"))
                            {
                                storageCapacityString = attr.select("dd").eq(i).text();
                            }
                        }

                    }
            }

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
            memoryString = memoryString.replaceAll("GB", "");
            String[] splitMemory = memoryString.split(" ", 2);
            int memory = Integer.parseInt(splitMemory[0]);


            // Screen Size:
            screenSizeString = screenSizeString.replace("\"", "");
            double screenSize = Double.parseDouble(screenSizeString);


            // OS
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
            String[] splitGPU = graphicCardString.split(" ", 2);
            String manufactureGPU = splitGPU[0].replaceAll("®", "");
            String modelGPU = splitGPU[1];
            PartStruct GPU = new PartStruct(manufactureGPU, modelGPU);



            // Weight

            String[] splitWeight = weightString.split(" ", 2);
            double weight = 0.45 * Double.parseDouble((splitWeight[0]));
            weight = Double.parseDouble(decmialFormat.format(weight));


            final String imgURL = site_url + document2.select("div.pdp-summary-area").select("img").first().attr("data-src");

            // Price
            double price;
            String priceString = document2.select("div.price").first().text();

            if(priceString.equals("$"))
                price = 0;
            else {
               priceString = priceString.replaceAll("\\$", "");
                priceString = priceString.replaceAll(",", "");
                price = Double.parseDouble(priceString);
            }

            // Desc
            final String desc = document2.select("div.copy.font-regular").first().text();

            // Check Touch Screen
            Boolean isTouchScreen;
            if(touchScreen.contains("Yes") == true)
                isTouchScreen = true;
            else {
                isTouchScreen = false;
            }

            // Storage Calculate
            boolean isSSD;
            if(storageType.contains("SSD"))
                isSSD = true;
            else
                isSSD = false;


            int storageCapacity;
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


            // Only Build The Object.
            laptop = new Laptop(id_laptop, modelName, url, companyName, CPU, memory, operatingSystem, GPU, storageObject, screenSize, weight, battery, isTouchScreen, price, imgURL, desc);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }
}
