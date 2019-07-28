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
      String[] url = {"https://www.microsoft.com/en-us/store/b/shop-all-pcs?manufacturer=Lenovo&categories=Laptops",
                "https://www.microsoft.com/en-us/store/b/shop-all-pcs?manufacturer=HP&categories=Laptops",
             "https://www.microsoft.com/en-us/store/b/shop-all-pcs?manufacturer=Razer&categories=Laptops",
             "https://www.microsoft.com/en-us/store/b/shop-all-pcs?manufacturer=ASUS&categories=Laptops",
             "https://www.microsoft.com/en-us/store/b/shop-all-pcs?manufacturer=MSI&categories=Laptops"};


        String mainUrl = "https://www.microsoft.com";
        Laptop laptop;

        try {
            for (int j = 0; j < url.length; j++) {
                Document document3 = Jsoup.connect(url[j]).get();
                Elements computers = document3.select("div.c-group.f-wrap-items.context-list-page").select("a");
                for (Element comp : computers) {
                    String urlComp = mainUrl + comp.attr("href");
                    laptop = buildLenovoLaptop(urlComp, i_LaptopArray.size());
                    i_LaptopArray.add(laptop);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    /*
        public Laptop(
                 PartStruct i_gpu
     */

    public static Laptop buildLenovoLaptop(String i_Url, int i_Idlaptop) {

        final String site_url = "https://www.microsoft.com";
        String url = i_Url + "?activetab=pivot:techspecstab";
        String urlDesc = i_Url + "?activetab=pivot%3aoverviewtab";
        Laptop laptop = null;

        try {
            final Document document2 = Jsoup.connect(url).get();
            final Document documentDesc = Jsoup.connect(urlDesc).get();

            final String imgURL = document2.select("div.pi-product-image").select("img").first().attr("src");
            final String modelName = document2.select("h1#DynamicHeading_productTitle").text();
            final String desc = documentDesc.select("div.c-paragraph").first().text();
            String[] splitNameCompany = modelName.split(" ", 2);
            final String companyName = splitNameCompany[0].trim();

            //Price
            String priceIfDiscount = document2.select("div#purchaseColumn").select("div#ProductPrice_productPrice_PriceContainer.pi-price-text").select("span.price-disclaimer").text();
            if (priceIfDiscount.equals(""))
                priceIfDiscount = document2.select("div#purchaseColumn").select("div#ProductPrice_productPrice_PriceContainer.pi-price-text").text();

            priceIfDiscount = priceIfDiscount.replaceAll("\\$", "").replaceAll(",", "");
            double priceNum = Double.parseDouble(priceIfDiscount);

            // Weight
            final String weightString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Weight)").select("td").text();
            String[] splitWeight = weightString.split(" ", 2);
            double weightNum = 0.45 * Double.parseDouble(splitWeight[0]);
            weightNum = Double.parseDouble(df2.format(weightNum));


            // Storage
            boolean isSSD = false;
            final String storageString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Hard drive size)").select("td").text();
            String[] splitStorage = storageString.split(" ");
            String cutStorage;
            int storageNum;
            if (splitStorage[0].contains("GB") || splitStorage[1].contains("GB")) {
                cutStorage = splitStorage[0].replaceAll("GB", "");
                storageNum = Integer.parseInt(cutStorage);
            } else {
                cutStorage = splitStorage[0].replaceAll("TB", "");
                storageNum = 1000 * Integer.parseInt(cutStorage);

            }

            if (storageString.contains("SSD"))
                isSSD = true;

            Storage theStorage = new Storage(isSSD, storageNum);


            // Battery
            String batteryString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Battery)").select("td").text();
            if (batteryString.equals(""))
                batteryString = "0";


            // Screen Size + TouchScreen
            boolean isTouchScreen = false;
            double screenSizeNum = 0;
            String screenString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Screen)").select("td").text();
            if (screenString.equals(""))
                screenString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Display)").select("td").text();

            if (screenString.contains("touchscreen") || desc.contains("touchscreen"))
                isTouchScreen = true;

            String[] splitScreen = screenString.split(" ");
            splitScreen[0] = splitScreen[0].replaceAll("-inch", "");
            screenSizeNum = Double.parseDouble(splitScreen[0]);


            //Memory
            String memoryString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Memory)").select("td").text();
            int memoryNum = 0;
            String arrayMemory[] = memoryString.split(" ", 2);
            arrayMemory[0] = arrayMemory[0].replaceAll("GB", "");
            memoryNum = Integer.parseInt(arrayMemory[0]);


            // Operating System
            String osString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Operating system)").select("td").text();
            String[] splitOS = osString.split(" ");
            String OS_Manufacture = splitOS[0];
            int OS_Serie = Integer.parseInt(splitOS[1].replaceAll(",", ""));
            int OS_Bit;

            String OS_Version;
            if (osString.contains("Home"))
                OS_Version = "Home";
            else if (osString.contains("S"))
                OS_Version = "S";
            else
                OS_Version = "0";


            if (osString.contains("32"))
                OS_Bit = 32;
            else
                OS_Bit = 64;

            OS operatingSystem = new OS(OS_Manufacture, OS_Version, OS_Serie, OS_Bit);


            // Processor
            String processorString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Processor)").select("td").text();
            String[] splitProcessor = processorString.split(" ");
            String manuProcessor = splitProcessor[0];
            String modelProcessor;
            if(splitProcessor[1].contains("i7"))
                    modelProcessor = splitProcessor[1];
            else
                modelProcessor = splitProcessor[2].replaceAll("5-", "5 ");

            PartStruct processor = new PartStruct(manuProcessor, modelProcessor);


            // GPU
            String gpuString = document2.select("div.c-table.f-divided.c-structured-list.cli_sku-variation").select("tr:contains(Video)").select("td").text();
            gpuString = gpuString.replaceAll("Integrated ", "").replaceAll("®", "").replaceAll(" graphics", "").replaceAll(" GPU", "").replaceAll("VR-ready ","");
            if (gpuString.contains("with"))
                gpuString = gpuString.substring(0, gpuString.indexOf("with"));

            if (gpuString.contains(" ("))
                gpuString = gpuString.substring(0, gpuString.indexOf(" ("));

            if(gpuString.contains(" - Max-Q"))
                gpuString = gpuString.substring(0, gpuString.indexOf(" - Max-Q"));
                else
            if(gpuString.contains("Max-Q"))
                gpuString = gpuString.substring(0, gpuString.indexOf(" Max-Q"));
            else
                if(gpuString.contains("MAX-Q"))
                    gpuString = gpuString.substring(0, gpuString.indexOf(" MAX-Q"));


            String[] splitGPU = gpuString.split(" ", 2);
           if(companyName.toUpperCase().equals("ASUS")) {
              splitGPU[1] = splitGPU[1].replaceAll(" 4GB", "").replaceAll(" 6GB", "").replaceAll(" GDDR5", "");
            }
           else
               if(companyName.equals("MSI"))
                   splitGPU[1] = splitGPU[1].replaceAll(" 6GB", "").replaceAll(" 8GB", "").replaceAll(" GDDR6", "");


            splitGPU[1] = splitGPU[1].trim();
            PartStruct gpu = new PartStruct(splitGPU[0], splitGPU[1]);


            laptop = new Laptop(i_Idlaptop, modelName, i_Url, companyName, processor, memoryNum, operatingSystem, gpu, theStorage, screenSizeNum, weightNum, batteryString, isTouchScreen, priceNum, imgURL, desc);

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