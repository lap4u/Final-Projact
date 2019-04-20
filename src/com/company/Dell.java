package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Dell {

    public Dell() {
    }

    public static void FindDellLaptops(ArrayList<Laptop> i_ArrLaptops) {

        Laptop laptop;
        final String site_url = "https://deals.dell.com";
        final String main_url = "https://deals.dell.com/en-us/category/laptops";

        try {
            final Document document = Jsoup.connect(main_url).get();
            Elements comp_urls = document.select("a.btn.btn-success.btn-block.margin-top-10");
            //System.out.println("size of List: " + comp_urls.size());

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

    private static Laptop compSaveDell(String url, int id_laptop) {

        final String site_url = "https://deals.dell.com";
        Laptop laptop = null;

        try {
            final Document document2 = Jsoup.connect(url).get();
            final String companyName = "Dell";
            final String modelName = document2.select("h1").text();
            final String processor = document2.select("div.tech-spec-title:contains(Processor)").select("div.tech-spec-content").text();
            final String operatingSystem = document2.select("div.tech-spec-title:contains(Operating System)").select("div.tech-spec-content").text();

            // Memory

            String excludeString = "(additional memory sold separately)";
            String memory = document2.select("div.tech-spec-title:contains(Memory)").select("div.tech-spec-content").text();
            if(memory.contains(excludeString))
                memory =  memory.replace(excludeString, " ");

            final String storage = document2.select("div.tech-spec-title:contains(Hard Drive)").select("div.tech-spec-content").text();
            final String graphicCard = document2.select("div.tech-spec-title:contains(Video Card)").select("div.tech-spec-content").text();

            final String price = (document2.select("div.col-xs-6.col-sm-5.col-md-7.text-right").select("span.price").text().split(" ")[0]) + "$";
            final String desc = document2.select("div#heroDescription.hidden-xs").text();
            final String imgURL = "http:" + document2.select("img#heroStaticImage.max-width-100.margin-bottom-10").first().attr("src");


            // CHeck screen size & touch screen
            Boolean isTouchScreen;

            String screenSize = null;
            String[] screenWords;

            final String screen = document2.select("div.tech-spec-title:contains(Display)").select("div.tech-spec-content").text();
            if(screen.contains("Touch") && !screen.contains("Non-Touch"))
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



            // Calculate Weight
            final String dimensions  = document2.select("div.tech-spec-title:contains(Weight)").text();
            String[] dimensionsWords;
            String weight = null;
            dimensionsWords = dimensions.split(" ");
            for(int i=0; i<dimensionsWords.length;i++)
            {
                if(dimensionsWords[i].contains("lbs") || dimensionsWords[i].contains("lb"))
                    if(dimensionsWords[i].equals("lbs"))
                        weight = dimensionsWords[i-1] + " lbs";
                    else
                        weight = dimensionsWords[i];
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
                battery = "?";

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


          // Update it:  laptop = new Laptop(id_laptop, modelName, url, companyName, processor, memory, operatingSystem, graphicCard, storage, screenSize, weight, battery, isTouchScreen, price, imgURL, desc);

            //Prints
            //   System.out.println("ID:  " + id_laptop);
            //  System.out.println("URL:  " + url);
            //  System.out.println("Desc:  " + desc);
            //System.out.println("Img URL:  " + imgURL);
            // System.out.println("Company name:  " + companyName);
            // System.out.println("Laptop name:  " + modelName);
            // System.out.println("Processor:  " + processor);
            // System.out.println("Memory:  " + memory);
            //   System.out.println("Screen Size:  " + screenSize);
            // System.out.println("Operating System:  " + operatingSystem);
            //  System.out.println("Storage:  " + storage);
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

        if (i_Url.equals("https://deals.dell.com/en-us/productdetail/27og"))
            isOkComp = false;
        else
        if (i_Url.equals("https://deals.dell.com/en-us/productdetail/274s"))
            isOkComp = false;
        else
        if (i_Url.equals("https://deals.dell.com/en-us/productdetail/275k"))
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

