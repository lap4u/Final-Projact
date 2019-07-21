package com.company;

import Parts.OS;
import Parts.PartStruct;
import Parts.Storage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
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

            //Processor
            final String processor = document2.select("div.tech-spec-title:contains(Processor)").select("div.tech-spec-content").text();
            String processorManufacture, processorModel = " ";

            String[] splitCPU = processor.split(" ");
            if(processor.contains("Intel"))
                processorManufacture = "Intel";
            else
                processorManufacture = "AMD";

            for(int j=0;j<splitCPU.length;j++)
            {
                // Because LG work only with Intel i3\i5\i7 so its fine with this if.
                if(splitCPU[j].contains("i3") || splitCPU[j].contains("i7") || splitCPU[j].contains("i5") || splitCPU[j].contains("N4000") || splitCPU[j].contains("N5000"))
                {
                    processorModel = splitCPU[j];
                }
                else
                    if(splitCPU[j].contains("AMD"))
                    {
                        if(splitCPU[j+1].contains("Ryzen"))
                        {
                            processorModel = "Ryzen " + splitCPU[j+2] + " " + splitCPU[j+3];
                        }
                        else {
                            processorModel = splitCPU[j + 1];
                        }
                    }

            }
            PartStruct CPU = new PartStruct(processorManufacture, processorModel);


            // OS
            final String operatingSystemStr = document2.select("div.tech-spec-title:contains(Operating System)").select("div.tech-spec-content").text();

            String[] splitOS = operatingSystemStr.split(" ");
            String OS_Manufacture = splitOS[0];
            String OS_Version = splitOS[2];
            int OS_Serie = Integer.parseInt(splitOS[1]);
            int OS_Bit = 32;

            for(int i=0;i<splitOS.length;i++)
            {
                if(splitOS[i].contains("64")) {
                    OS_Bit = 64;
                    break;
                }
            }

            OS operatingSystem = new OS(OS_Manufacture,OS_Version, OS_Serie, OS_Bit);


            // Memory
            Elements memories = document2.select("div.tech-spec-title:contains(Memory)").select("div.tech-spec-content");
            String memory;
            if(memories.size() >1) // Because Elements Memories can be 2 (with the Graphic Card Memory, So we need the second (The regular memory)
            {
                memory = memories.eq(1).text();
            }
            else
                memory = memories.text();

            String arrMemory[] = memory.split(" ", 2);
            String sizeMemory = arrMemory[0];
            sizeMemory = sizeMemory.replaceAll("GB", "");
            sizeMemory = sizeMemory.replaceAll(",", "");
           int memoryNumber =  Integer.parseInt(sizeMemory);


            //Storage
            String storageString = document2.select("div.tech-spec-title:contains(Hard Drive)").select("div.tech-spec-content").text();
            int storageCapacity;

            boolean isSSD;
            if(storageString.contains("SSD") ||storageString.contains("Solid State Drive") )
                isSSD = true;
            else
                isSSD = false;


            String[] arrStorage = storageString.split(" ", 2);
            String GBString;

            if(arrStorage[0].contains("TB"))
            {
                GBString = arrStorage[0].replaceAll("TB", "");
                storageCapacity = 1024 * Integer.parseInt(GBString);
            }
            else
            {
                GBString = arrStorage[0].replaceAll("GB", "");
                storageCapacity = Integer.parseInt(GBString);
            }

            Storage storageObject = new Storage(isSSD, storageCapacity);


            // GPU - iN THE END
            final String graphicCard = document2.select("div.tech-spec-title:contains(Video Card)").select("div.tech-spec-content").text();
            /*

                        String[] splitGPU = graphicCardString.split(" ", 2);
            String manufactureGPU = splitGPU[0].replaceAll("®", "");
            String modelGPU = splitGPU[1];
            PartStruct GPU = new PartStruct(manufactureGPU, modelGPU);

             */

            Double priceNumber = 0.0;
            final String price = (document2.select("div.col-xs-6.col-sm-5.col-md-7.text-right").select("span.price").text().split(" ")[0]);
            if(!price.equals(""))
                priceNumber = Double.parseDouble(price);


            final String desc = document2.select("div#heroDescription.hidden-xs").text();

            String imgURL = "";
            Element checkImgURL = document2.select("img#heroStaticImage.max-width-100.margin-bottom-10").first();

            if(checkImgURL == null)
                imgURL = "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/dell_client_products/notebooks/inspiron_notebooks/15_3567/pdp/dell-laptops-inspiron-15-3000-intel-turis-pdp-hero.jpg?wid=570&hei=400";

            else
                imgURL = "http:" + checkImgURL.attr("src");


            // CHeck screen size & touch screen
            Boolean isTouchScreen;

            String screenSize = null;
            String[] screenWords;

            final String screen = document2.select("div.tech-spec-title:contains(Display)").select("div.tech-spec-content").text();

            if((screen.contains("Touch") || screen.contains("touch")) && screen.contains("Non-Touch") == false && screen.contains("Non-touch") == false)
                isTouchScreen = true;
            else
                isTouchScreen = false;

            screenWords = screen.split(" ");
            if(screenWords[0].contains("inch") || screenWords[0].contains("\"") || screenWords[0].contains("”") || isNumeric(screenWords[0]))
            {
                screenSize = screenWords[0];
            }
            else
            if(screenWords[1].contains("\""))
            {
                screenSize = screenWords[1];
            }

            screenSize = screenSize.replaceAll("\"", "").replaceAll("\\”", "").replaceAll("-inch","");
            double screenSizeNum = Double.parseDouble(screenSize);

        System.out.println(screen);
            System.out.println(isTouchScreen);
            System.out.println(screenSizeNum + "\n");


            // Calculate Weight
            final String dimensions  = document2.select("div.tech-spec-title:contains(Weight)").text();
            String[] dimensionsWords;
            String weight = null;
            dimensionsWords = dimensions.split(" ");
            for(int i=0; i<dimensionsWords.length;i++)
            {

                if(dimensionsWords[i].contains("lbs") || dimensionsWords[i].contains("lb"))
                    if(dimensionsWords[i].startsWith("lb"))
                        weight = dimensionsWords[i-1].replaceAll("\\(", "");
                    else {
                        weight = dimensionsWords[i];
                        weight = weight.replaceAll("lbs.", "").replaceAll("lbs", "").replaceAll("lb", "").replaceAll("\\(", "").replaceAll("\\)", "");

                    }
            }

            double weightNum;
            if(weight == null)
                weightNum = 0;
            else {
                weightNum = 0.45 * Double.parseDouble(weight); // Lbs to KG
                weightNum = Double.parseDouble(decmialFormat.format(weightNum));
            }



            // Calculate Battery
            String battery = "";
            String[] batteryWords;
            String cellBattery = "";
            String whrBattery = "";
            String batteryString = null;
            batteryString = document2.select("div.tech-spec-title:contains(Primary Battery)").select("div.tech-spec-content").text();
            if(batteryString.equals(""))
                batteryString = document2.select("div.tech-spec-title:contains(Power)").select("div.tech-spec-content:contains(Whr)").text();

            batteryWords = batteryString.split(" ");
            for(int j=0;j<batteryWords.length;j++)
            {
                if(batteryWords[j].toLowerCase().contains("whr"))
                    if(batteryWords[j].toLowerCase().equals("whr,") || batteryWords[j].toLowerCase().equals("whr"))
                    {
                        whrBattery = batteryWords[j-1] + " Whr";
                    }
                    else
                        whrBattery = batteryWords[j];

                if(batteryWords[j].toLowerCase().contains("cell"))
                    if(batteryWords[j].toLowerCase().equals("cell") || batteryWords[j].toLowerCase().equals("cell)"))
                        cellBattery = batteryWords[j-1] + " Cell";
                    else
                        cellBattery = batteryWords[j];
            }



            // Check Again
            if(battery.equals(""))
            {
                for(int p=0; p<dimensionsWords.length;p++)
                {
                    if(dimensionsWords[p].toLowerCase().contains("whr"))
                    {
                        whrBattery = dimensionsWords[p];
                        if(isTouchScreen == false) {
                            break;
                        }
                    }
                }
            }

            battery = whrBattery + " " + cellBattery;

            // Check Again
            if(battery.equals(" "))
                battery = "0";

            // Battery is complex becuase half the info of battery in "Power" and half in "Primary Battery" , So we check it:
          /*  String battery = "";
            Elements checkBattery = document2.select("div.tech-spec-title:contains(Power)").select("div.tech-spec-content:contains(Whr)");
            if (checkBattery.text().equals(""))
                battery = document2.select("div.tech-spec-title:contains(Power)").select("div.tech-spec-content:contains(Watt)").text();
            else
                battery = checkBattery.text();

            if(battery.equals(""))
                battery = document2.select("div.tech-spec-title:contains(Primary Battery)").select("div.tech-spec-content").text();
            */
            // <div id="heroDescription" class="hidden-xs">Ultra-portable 11" laptop that makes every step on your journey more fun. Featuring AMD processors, plus CinemaStream and CinemaSound.<br><br><em>Looking for something with more versatility? Check out our <a href="//www.dell.com/en-us/shop/dell-laptops/inspiron-11-3000-2-in-1/spd/inspiron-11-3185-2-in-1-laptop" target="_blank">Inspiron 11 3000 2-in-1</a>&nbsp;with four modes, including tent and tablet mode.</em></div>

            //Build Object of Laptop with this data.


            //New: laptop - new Laptop(i_IdLaptop, modelName, i_Url, companyName, CPU, memoryNumber ,operatingSystem,

          // Update it:  laptop = new Laptop(id_laptop, modelName, url, companyName, processor, memory, operatingSystem, graphicCard, storage, screenSize, weight, battery, isTouchScreen, price, imgURL, desc);

            //Prints
            //   System.out.println("ID:  " + id_laptop);
             //System.out.println("URL:  " + url);
            //  System.out.println("Desc:  " + desc);
            //System.out.println("Img URL:  " + imgURL);
            // System.out.println("Company name:  " + companyName);
            // System.out.println("Laptop name:  " + modelName);
            // System.out.println("Processor:  " + processor);
            // System.out.println("Memory:  " + memory);
            //   System.out.println("Screen Size:  " + screenSize);
            // System.out.println("Operating System:  " + operatingSystem);
            // System.out.println("Storage:  " + storage);
            //   System.out.println("Graphic Card:  " + graphicCard);
            // System.out.println("Weight: " + weight);
            // System.out.println("Battery: " + battery);
            // System.out.println("Price: " + price);
            //System.out.println("Is Touch Screen? " + isTouchScreen);


            // Battery Problem

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }


    private static void printLaptop(Laptop i_Laptop)
    {

    }

    private static boolean Exclude_Dell_Comps(String i_Url) {
        boolean isOkComp = true;

        if (i_Url.equals("https://deals.dell.com/en-us/productdetail/2qam"))
            isOkComp = false;

        return isOkComp;
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c) && c !='.') return false;
        }
        return true;
    }
}

