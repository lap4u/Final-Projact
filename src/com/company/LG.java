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
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public LG() {
    }

    public static void FindLGLaptops(ArrayList<Laptop> i_ArrLaptops) {

        Laptop laptop;
        final String site_url = "https://www.lg.com";
        final String main_url = "https://www.lg.com/us/laptops/view-all";

        try {
            final Document document = Jsoup.connect(main_url).get();
            Elements comp_urls = document.select("div#resultAppendTarget.product-list-box.js-model-switcher").select("ul.list-box");
            comp_urls = comp_urls.select("div.item.js-model");

            for (Element comp : comp_urls) {
                comp = comp.select("a").first();
                String atar = comp.attr("href");
                String product_url = site_url + atar;
                laptop = compSaveLG(product_url, i_ArrLaptops.size(), site_url);
                i_ArrLaptops.add(laptop);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static Laptop compSaveLG(String url, int id_laptop, String i_siteURL) {
        Laptop laptop = null;

        try {
            final Document document2 = Jsoup.connect(url).get();
            final String companyName = "LG";
            final String modelName = document2.select("h1.model-title").text();
            String processor = "", memoryString = "", screenSizeString = "", operatingSystemString = "", graphicCardString = "";
            String weightString = "", battery = "", touchScreen = "", storageType = "", storageCapacityString = "";



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

            PartStruct CPU = getCPU(processor);
            int memory = getMemory(memoryString);
            double screenSize = Double.parseDouble(screenSizeString.replace("\"", ""));
            OS operatingSystem = getOS(operatingSystemString);
            PartStruct GPU = getGPU(graphicCardString);
            double weight = getWeight(weightString);
            ArrayList<String> imageUrlsArray = getImagesUrls(document2);
            double price = getPrice(document2);
            final String desc = document2.select("div.copy.font-regular").first().text();
            Boolean isTouchScreen = getIsTouchScreen(touchScreen);
            Storage storageObject = getStorage(storageType, storageCapacityString);

            laptop = new Laptop(id_laptop, modelName, url, companyName, CPU, memory, operatingSystem, GPU, storageObject, screenSize, weight, battery, isTouchScreen, price, imageUrlsArray, desc);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }

    private static ArrayList<String> getImagesUrls(Document i_Document)
    {
        ArrayList<String> imagesUrlArr = new ArrayList<>();
        Elements getImagesURLs = i_Document.select("div.zoom-area").select("img.pc");
        for(int i=0;i<getImagesURLs.size();i++)
        {
            if(getImagesURLs.eq(i).attr("data-lazy").equals("") == false)
            imagesUrlArr.add("https://www.lg.com" + getImagesURLs.eq(i).attr("data-lazy"));
        }

        return imagesUrlArr;
    }

    private static PartStruct getCPU(String i_ProcessorString)
    {
        String processorManufacture;
        String processorModel = " ";
        String[] splitCPU = i_ProcessorString.split(" ");

        if (i_ProcessorString.contains("Intel"))
            processorManufacture = "Intel";
        else
            processorManufacture = "AMD";

        for (int i = 0; i < splitCPU.length; i++) {
            // Because LG work only with Intel i3\i5\i7 so its fine with this if.
            if (splitCPU[i].contains("i3") || splitCPU[i].contains("i5") || splitCPU[i].contains("i7")) {
                processorModel = splitCPU[i].replaceAll("5U,","5U");
            }
        }
        PartStruct CPU = new PartStruct(processorManufacture, processorModel);
        return CPU;
    }

    private static int getMemory(String i_MemoryString)
    {
        String memoryString = i_MemoryString.replaceAll("GB", "");
        String[] splitMemory = memoryString.split(" ", 2);
        int memory = Integer.parseInt(splitMemory[0]);
        return memory;
    }

    private static OS getOS(String i_OsString)
    {
        // OS
        String[] splitOS = i_OsString.split(" ");
        String OS_Manufacture = splitOS[0];
        String OS_Version = splitOS[2];
        int OS_Serie = Integer.parseInt(splitOS[1]);
        int OS_Bit;
        if (splitOS[3].contains("64"))
            OS_Bit = 64;
        else
            OS_Bit = 32;

        OS operatingSystem = new OS(OS_Manufacture, OS_Version, OS_Serie, OS_Bit);
        return operatingSystem;
    }

    private static PartStruct getGPU(String i_GPU)
    {
        String[] splitGPU = i_GPU.split(" ", 2);
        String manufactureGPU = splitGPU[0].replaceAll("®", "");
        String modelGPU = splitGPU[1];
        PartStruct GPU = new PartStruct(manufactureGPU, modelGPU);

        return GPU;
    }

    private static double getWeight(String i_WeightString)
    {
        String[] splitWeight = i_WeightString.split(" ", 2);
        double weight = 0.45 * Double.parseDouble((splitWeight[0]));
        weight = Double.parseDouble(decimalFormat.format(weight));
        return weight;
    }

    private static double getPrice(Document i_Document)
    {
        double price = 0;
        String priceString = i_Document.select("div.price").first().text();

        if (priceString.equals("$"))
            price = 0;
        else {
            priceString = priceString.replaceAll("\\$", "");
            priceString = priceString.replaceAll(",", "");
            price = Double.parseDouble(priceString);
        }

        return price;
    }

    private static boolean getIsTouchScreen(String i_Screen)
    {
        Boolean isTouchScreen;
        if (i_Screen.contains("Yes") == true)
            isTouchScreen = true;
        else {
            isTouchScreen = false;
        }

        return  isTouchScreen;
    }

    private static Storage getStorage(String i_StorageType,String i_StorageCapacity)
    {
        boolean isSSD;
        if (i_StorageType.contains("SSD"))
            isSSD = true;
        else
            isSSD = false;

        int storageCapacity;
        String onlyGBString;
        String[] splitStorage = i_StorageCapacity.split(" ", 2);
        if (splitStorage[0].contains("TB")) {
            onlyGBString = splitStorage[0].replaceAll("TB", "");
            storageCapacity = 1024 * Integer.parseInt(onlyGBString);
        } else {
            onlyGBString = splitStorage[0].replaceAll("GB", "");
            storageCapacity = Integer.parseInt(onlyGBString);
        }

        Storage storageObject = new Storage(isSSD, storageCapacity);

        return storageObject;
    }
}
