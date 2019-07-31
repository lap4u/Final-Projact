package com.company;

import Parts.OS;
import Parts.PartStruct;
import Parts.Storage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Dell {
    private static DecimalFormat decmialFormat = new DecimalFormat("#.##");

    public Dell() {
    }

    public static void FindDellLaptops(List<Laptop> i_ArrLaptops) {

        Laptop laptop;
        final String site_url = "https://deals.dell.com";
        final String main_url = "https://deals.dell.com/en-us/category/laptops";

        try {
            final Document document = Jsoup.connect(main_url).get();
            Elements comp_urls = document.select("a.btn.btn-success.btn-block.margin-top-10");


            for (Element comp : comp_urls) {
                comp = comp.select("a").first();
                String atar = comp.attr("href");
                String product_url = site_url + atar;
                if (Exclude_Dell_Comps(product_url)) {
                    laptop = compSaveDell(product_url, i_ArrLaptops.size());
                    i_ArrLaptops.add(laptop);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Laptop compSaveDell(String i_Url, int i_IdLaptop) {

        final String site_url = "https://deals.dell.com";
        Laptop laptop = null;

        try {
            final Document document2 = Jsoup.connect(i_Url).get();
            final String companyName = "Dell";
            final String modelName = document2.select("h1").text();
            PartStruct CPU = getCPU(document2);
            OS operatingSystem = getOS(document2);
            int memoryNumber = getMemory(document2);
            Storage storageObject = getStorage(document2);
            PartStruct GPU = getGPU(document2);
            Double priceNumber = getPrice(document2);
            String desc = document2.select("div#heroDescription.hidden-xs").text();
            ArrayList<String> imgURL = getImgURL(document2);
            Boolean isTouchScreen = getIsTouchScreen(document2);
            double screenSizeNum = getScreenSize(document2);
            double weightNum = getWeight(document2);
            String battery = getBattery(document2, isTouchScreen);

            laptop = new Laptop(i_IdLaptop, modelName, i_Url, companyName, CPU, memoryNumber, operatingSystem, GPU, storageObject, screenSizeNum, weightNum, battery, isTouchScreen, priceNumber, imgURL, desc);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }

    private static String getBattery(Document i_Document, boolean i_isTouchScreen) {
        String battery = "";
        String[] batteryWords;
        String cellBattery = "";
        String whrBattery = "";
        String batteryString = null;
        batteryString = i_Document.select("div.tech-spec-title:contains(Primary Battery)").select("div.tech-spec-content").text();
        if (batteryString.equals(""))
            batteryString = i_Document.select("div.tech-spec-title:contains(Power)").select("div.tech-spec-content:contains(Whr)").text();

        batteryWords = batteryString.split(" ");
        for (int j = 0; j < batteryWords.length; j++) {
            if (batteryWords[j].toLowerCase().contains("whr"))
                if (batteryWords[j].toLowerCase().equals("whr,") || batteryWords[j].toLowerCase().equals("whr")) {
                    whrBattery = batteryWords[j - 1] + " Whr";
                } else
                    whrBattery = batteryWords[j];

            if (batteryWords[j].toLowerCase().contains("cell"))
                if (batteryWords[j].toLowerCase().equals("cell") || batteryWords[j].toLowerCase().equals("cell)"))
                    cellBattery = batteryWords[j - 1] + " Cell";
                else
                    cellBattery = batteryWords[j];
        }

        final String dimensions = i_Document.select("div.tech-spec-title:contains(Weight)").text();
        String[] dimensionsWords;
        dimensionsWords = dimensions.split(" ");

        // Check Again Battery
        if (battery.equals("")) {
            for (int p = 0; p < dimensionsWords.length; p++) {
                if (dimensionsWords[p].toLowerCase().contains("whr")) {
                    whrBattery = dimensionsWords[p];
                    if (i_isTouchScreen == false) {
                        break;
                    }
                }
            }
        }

        battery = whrBattery + " " + cellBattery;

        // Check Again x2 Battery
        if (battery.equals(" "))
            battery = "0";


        return battery;
    }

    private static double getWeight(Document i_Document) {
        final String dimensions = i_Document.select("div.tech-spec-title:contains(Weight)").text();
        String[] dimensionsWords;
        String weight = null;
        dimensionsWords = dimensions.split(" ");
        for (int i = 0; i < dimensionsWords.length; i++) {

            if (dimensionsWords[i].contains("lbs") || dimensionsWords[i].contains("lb"))
                if (dimensionsWords[i].startsWith("lb"))
                    weight = dimensionsWords[i - 1].replaceAll("\\(", "");
                else {
                    weight = dimensionsWords[i];
                    weight = weight.replaceAll("lbs.", "").replaceAll("lbs", "").replaceAll("lb", "").replaceAll("\\(", "").replaceAll("\\)", "");

                }
        }

        double weightNum;
        if (weight == null)
            weightNum = 0;
        else {
            weightNum = 0.45 * Double.parseDouble(weight); // Lbs to KG
            weightNum = Double.parseDouble(decmialFormat.format(weightNum));
        }

        return weightNum;
    }

    private static double getScreenSize(Document i_Document) {
        String screenSize = null;
        final String screen = i_Document.select("div.tech-spec-title:contains(Display)").select("div.tech-spec-content").text();
        String[] screenWords;
        screenWords = screen.split(" ");
        if (screenWords[0].contains("inch") || screenWords[0].contains("\"") || screenWords[0].contains("”") || isNumeric(screenWords[0])) {
            screenSize = screenWords[0];
        } else if (screenWords[1].contains("\"")) {
            screenSize = screenWords[1];
        }

        screenSize = screenSize.replaceAll("\"", "").replaceAll("\\”", "").replaceAll("-inch", "");
        double screenSizeNum = Double.parseDouble(screenSize);
        return screenSizeNum;
    }

    private static boolean getIsTouchScreen(Document i_Document) {
        Boolean isTouchScreen;
        final String screen = i_Document.select("div.tech-spec-title:contains(Display)").select("div.tech-spec-content").text();

        if ((screen.contains("Touch") || screen.contains("touch")) && screen.contains("Non-Touch") == false && screen.contains("Non-touch") == false)
            isTouchScreen = true;
        else
            isTouchScreen = false;

        return isTouchScreen;
    }

    private static ArrayList<String> getImgURL(Document i_Document) {
        ArrayList<String> imagesArray = new ArrayList<>();
        String imgURL = "";
        Element checkImgURL = i_Document.select("img#heroStaticImage.max-width-100.margin-bottom-10").first();

        if (checkImgURL == null)
            imgURL = "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/dell_client_products/notebooks/inspiron_notebooks/15_3567/pdp/dell-laptops-inspiron-15-3000-intel-turis-pdp-hero.jpg?wid=570&hei=400";

        else
            imgURL = "http:" + checkImgURL.attr("src");


        imagesArray.add(imgURL);

        return imagesArray;
    }

    private static double getPrice(Document i_Document) {
        Double priceNumber = 0.0;
        final String price = (i_Document.select("div.col-xs-6.col-sm-5.col-md-7.text-right").select("span.price").text().split(" ")[0]);
        if (!price.equals(""))
            priceNumber = Double.parseDouble(price);

        return priceNumber;
    }

    private static PartStruct getGPU(Document i_Document) {
        final String graphicCard = i_Document.select("div.tech-spec-title:contains(Video Card)").select("div.tech-spec-content").text();
        String gpuManufacture, gpuModel;
        String[] splitGPU = graphicCard.split(" ", 2);
        gpuManufacture = splitGPU[0].replaceAll("®", "").replaceAll("™", "");
        if (gpuManufacture.equals("Integrated")) {
            gpuManufacture = "0";
            gpuModel = "0";
        } else {
            if (splitGPU[1].contains("with"))
                gpuModel = splitGPU[1].substring(0, splitGPU[1].indexOf("with"));
            else
                gpuModel = splitGPU[1];
        }

        gpuModel = gpuModel.replaceAll("™", "").replaceAll("®", "").replaceAll(" GDDR5","").replaceAll(" 2GB", "").replaceAll(" 4GB", "").replaceAll("0Ti", "0 Ti").replaceAll("GL Graphics ","GL");

        PartStruct GPU = new PartStruct(gpuManufacture, gpuModel);
        return GPU;
    }


    private static Storage getStorage(Document i_Document) {
        String storageString = i_Document.select("div.tech-spec-title:contains(Hard Drive)").select("div.tech-spec-content").text();
        storageString = storageString.replaceAll("M.2 ", "");
        int storageCapacity;

        boolean isSSD;
        if (storageString.contains("SSD") || storageString.contains("Solid State Drive"))
            isSSD = true;
        else
            isSSD = false;


        String[] arrStorage = storageString.split(" ", 2);
        String GBString;


        if (arrStorage[0].contains("TB")) {
            GBString = arrStorage[0].replaceAll("TB", "");
            storageCapacity = 1024 * Integer.parseInt(GBString);
        } else {
            GBString = arrStorage[0].replaceAll("GB", "");
            storageCapacity = Integer.parseInt(GBString);
        }

        Storage storageObject = new Storage(isSSD, storageCapacity);

        return storageObject;
    }

    private static int getMemory(Document i_Document) {
        Elements memories = i_Document.select("div.tech-spec-title:contains(Memory)").select("div.tech-spec-content");
        String memory;
        if (memories.size() > 1) // Because Elements Memories can be 2 (with the Graphic Card Memory, So we need the second (The regular memory)
        {
            memory = memories.eq(1).text();
        } else
            memory = memories.text();

        String arrMemory[] = memory.split(" ", 2);
        String sizeMemory = arrMemory[0];
        sizeMemory = sizeMemory.replaceAll("GB", "");
        sizeMemory = sizeMemory.replaceAll(",", "");
        int memoryNumber = Integer.parseInt(sizeMemory);
        return memoryNumber;
    }

    private static OS getOS(Document i_Document) {
        final String operatingSystemStr = i_Document.select("div.tech-spec-title:contains(Operating System)").select("div.tech-spec-content").text();
        String[] splitOS = operatingSystemStr.split(" ");
        String OS_Manufacture = splitOS[0];
        String OS_Version = splitOS[2];
        int OS_Serie = Integer.parseInt(splitOS[1]);
        int OS_Bit = 32;

        for (int i = 0; i < splitOS.length; i++) {
            if (splitOS[i].contains("64")) {
                OS_Bit = 64;
                break;
            }
        }

        OS operatingSystem = new OS(OS_Manufacture, OS_Version, OS_Serie, OS_Bit);
        return operatingSystem;
    }

    private static PartStruct getCPU(Document i_Document) {
        final String processor = i_Document.select("div.tech-spec-title:contains(Processor)").select("div.tech-spec-content").text();
        String processorManufacture, processorModel = " ";

        String[] splitCPU = processor.split(" ");
        if (processor.contains("Intel"))
            processorManufacture = "Intel";
        else
            processorManufacture = "AMD";

        for (int j = 0; j < splitCPU.length; j++) {
            // Because LG work only with Intel i3\i5\i7 so its fine with this if.
            if (splitCPU[j].contains("i3") || splitCPU[j].contains("i7") || splitCPU[j].contains("i5") || splitCPU[j].contains("N4000") || splitCPU[j].contains("N5000")) {
                processorModel = splitCPU[j];
            } else if (splitCPU[j].contains("AMD")) {
                if (splitCPU[j + 1].contains("Ryzen")) {
                    processorModel = "Ryzen " + splitCPU[j + 2] + " " + splitCPU[j + 3];
                } else {
                    processorModel = splitCPU[j + 1];
                }
            }

        }
        PartStruct CPU = new PartStruct(processorManufacture, processorModel);
        return CPU;
    }

    private static boolean Exclude_Dell_Comps(String i_Url) {
        boolean isOkComp = true;
        String[] excludeUrls = {"https://deals.dell.com/en-us/productdetail/2spq"
                ,"https://deals.dell.com/en-us/productdetail/2sps"
        };

        for (int i = 0; i < excludeUrls.length; i++) {
            if (i_Url.equals(excludeUrls[i])) {
                isOkComp = false;
                break;
            }
        }

        return isOkComp;
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c) && c != '.') return false;
        }
        return true;
    }
}

