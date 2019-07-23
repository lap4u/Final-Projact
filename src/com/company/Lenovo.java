package com.company;

import Parts.OS;
import Parts.PartStruct;
//import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import Parts.Storage;
import com.objectdb.o.SYH;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Lenovo {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public Lenovo() {
    }


    public static void Find_Laptops(List<Laptop> i_LaptopArray) {
        String url = "https://www.microsoft.com/en-us/store/b/shop-all-pcs?manufacturer=Lenovo&icid=PC_cat_Quicklink-Lenovo-06282019";
        String mainUrl = "https://www.microsoft.com";
        Laptop laptop;

        try {
            final Document document3 = Jsoup.connect(url).get();
            Elements computers = document3.select("div.c-group.f-wrap-items.context-list-page").select("a");
            System.out.println("SIZE: " + computers.size());

            for (Element comp : computers) {
                String urlComp = mainUrl + comp.attr("href");
                laptop = buildLenovoLaptop(urlComp, i_LaptopArray.size());
               // i_LaptopArray.add(laptop);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    /*
        public Laptop(
                  PartStruct i_processor, int i_memory, OS i_operation_system, PartStruct i_gpu,
             double i_screen_size, String i_battery,
                  Boolean i_touch_screen


     */

    public static Laptop buildLenovoLaptop(String i_Url, int i_Idlaptop) {

        final String site_url = "https://www.microsoft.com";
        String url = i_Url + "?activetab=pivot:techspecstab";
        String urlDesc = i_Url + "?activetab=pivot%3aoverviewtab";
        Laptop laptop = null;

        try {
            final Document document2 = Jsoup.connect(url).get();
            final Document documentDesc = Jsoup.connect(urlDesc).get();
            final String companyName = "Lenovo";


            final String imgURL = document2.select("div.pi-product-image").select("img").first().attr("src");
            final String modelName = document2.select("h1#DynamicHeading_productTitle").text();
            final String desc = documentDesc.select("div.c-paragraph").first().text();

            //Price
            String priceIfDiscount = document2.select("div#purchaseColumn").select("div#ProductPrice_productPrice_PriceContainer.pi-price-text").select("span.price-disclaimer").text();
            if(priceIfDiscount.equals(""))
                priceIfDiscount = document2.select("div#purchaseColumn").select("div#ProductPrice_productPrice_PriceContainer.pi-price-text").text();

            priceIfDiscount = priceIfDiscount.replaceAll("\\$","").replaceAll(",","");
            double priceNum = Double.parseDouble(priceIfDiscount);

            // Weight
            final String weightString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Weight)").select("td").text();
            String[] splitWeight = weightString.split(" ", 2);
            double weightNum = 0.45 * Double.parseDouble(splitWeight[0]);

            // Storage
            boolean isSSD = false;
            final String storageString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Hard drive size)").select("td").text();
            String[] splitStorage = storageString.split(" ");
            String cutStorage;
            int storageNum;
            if(splitStorage[0].contains("GB") || splitStorage[1].contains("GB")) {
                cutStorage = splitStorage[0].replaceAll("GB", "");
                storageNum = Integer.parseInt(cutStorage);
            }
            else {
                cutStorage = splitStorage[0].replaceAll("TB", "");
                storageNum = 1000 * Integer.parseInt(cutStorage);

            }

            if(storageString.contains("SSD"))
                isSSD = true;

            Storage theStorage = new Storage(isSSD, storageNum);



            /*
            String processor = "", memoryString = "", screenSizeString = "", operatingSystemString = "", graphicCardString = "";
            String weightString = "", battery = "", touchScreen = "", storageType = "", storageCapacityString = "";
            ;

            Elements attributes = document2.select("div.tech-spacs");
            for (Element attr : attributes) {
                String h2 = attr.select("h2").text();
                if (h2.equals("PROCESSOR")) {
                    processor = attr.select("dd").text();
                } else if (h2.equals("MEMORY")) {
                    memoryString = attr.select("dd").text();
                } else if (h2.equals("DISPLAY")) {
                    screenSizeString = attr.select("dd").first().text();
                    graphicCardString = attr.select("dd").eq(3).text();
                } else if (h2.equals("OPERATING SYSTEM")) {
                    operatingSystemString = attr.select("dd").text();
                } else if (h2.equals("DIMENSIONS/WEIGHT")) {
                    weightString = attr.select("dd").eq(2).text();
                } else if (h2.equals("BATTERY/POWER")) {
                    battery = attr.select("dd").first().text();
                } else if (h2.equals("FEATURES")) {
                    for (int i = 0; i < attr.select("dt").size(); i++) {
                        if (attr.select("dt").eq(i).text().equals("Touchscreen LCD")) {
                            touchScreen = attr.select("dd").eq(i).text();
                        }
                    }
                } else if (h2.equals("HARD DRIVE")) {
                    storageType = attr.select("dd").first().text();
                    for (int i = 0; i < attr.select("dt").size(); i++) {
                        if (attr.select("dt").eq(i).text().equals("Capacity")) {
                            storageCapacityString = attr.select("dd").eq(i).text();
                        }
                    }

                }
            }

            String processorManufacture;
            String processorModel = " ";
            String[] splitCPU = processor.split(" ");

            if (processor.contains("Intel"))
                processorManufacture = "Intel";
            else
                processorManufacture = "AMD";

            for (int i = 0; i < splitCPU.length; i++) {
                // Because LG work only with Intel i3\i5\i7 so its fine with this if.
                if (splitCPU[i].contains("i3") || splitCPU[i].contains("i5") || splitCPU[i].contains("i7")) {
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
            if (splitOS[3].contains("64"))
                OS_Bit = 64;
            else
                OS_Bit = 32;

            OS operatingSystem = new OS(OS_Manufacture, OS_Version, OS_Serie, OS_Bit);


            // Graphic Card
            String[] splitGPU = graphicCardString.split(" ", 2);
            String manufactureGPU = splitGPU[0].replaceAll("®", "");
            String modelGPU = splitGPU[1];
            PartStruct GPU = new PartStruct(manufactureGPU, modelGPU);


            // Weight

            String[] splitWeight = weightString.split(" ", 2);
            double weight = 0.45 * Double.parseDouble((splitWeight[0]));
            weight = Double.parseDouble(decmialFormat.format(weight));



            // Price
            double price;
            String priceString = document2.select("div.price").first().text();

            if (priceString.equals("$"))
                price = 0;
            else {
                priceString = priceString.replaceAll("\\$", "");
                priceString = priceString.replaceAll(",", "");
                price = Double.parseDouble(priceString);
            }



            // Check Touch Screen
            Boolean isTouchScreen;
            if (touchScreen.contains("Yes") == true)
                isTouchScreen = true;
            else {
                isTouchScreen = false;
            }

            // Storage Calculate
            boolean isSSD;
            if (storageType.contains("SSD"))
                isSSD = true;
            else
                isSSD = false;


            int storageCapacity;
            String onlyGBString;
            String[] splitStorage = storageCapacityString.split(" ", 2);
            if (splitStorage[0].contains("TB")) {
                onlyGBString = splitStorage[0].replaceAll("TB", "");
                storageCapacity = 1024 * Integer.parseInt(onlyGBString);
            } else {
                onlyGBString = splitStorage[0].replaceAll("GB", "");
                storageCapacity = Integer.parseInt(onlyGBString);
            }

            Storage storageObject = new Storage(isSSD, storageCapacity);


            // Only Build The Object.
            laptop = new Laptop(id_laptop, modelName, url, companyName, CPU, memory, operatingSystem, GPU, storageObject, screenSize, weight, battery, isTouchScreen, price, imgURL, desc);
*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }


    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c) && c != '.') return false;
        }
        return true;
    }


}