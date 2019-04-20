package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class LG {

    public LG(){}


    public static void FindLGLaptops(ArrayList<Laptop> i_ArrLaptops) {

        Laptop laptop;
        final String site_url = "https://www.lg.com";
        final String main_url = "https://www.lg.com/us/laptops/view-all";


        try {
            final Document document = Jsoup.connect(main_url).get();
            Elements comp_urls = document.select("p.model-name.redot");
            //System.out.println("size of List: " + comp_urls.size());

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
            final String processor = document2.select("li#SP07578911.full").select("p.value").text() + document2.select("li#SP06236899").select("p.value").text();
            final String memory = document2.select("li#SP07578915.full").select("p.value").text() + document2.select("li#SP06236902").select("p.value").text();
            final String screenSize = document2.select("li#SP06236908").select("p.value").text();
            final String operatingSystem = document2.select("li#SP06236896.full").select("p.value").text();
            final String graphicCard = document2.select("li#SP06236911").select("p.value").text();

            String excludeString = "(may vary by configuration and manufacturing process)";
            String weight = document2.select("li#SP07591580").select("p.value").text() + document2.select("li#SP06236945").select("p.value").text();
            if(weight.contains(excludeString))
                weight =  weight.replace(excludeString, " ");

            final String battery = document2.select("li#SP06236935").select("p.value").text() + document2.select("li#SP07707838").select("p.value").text() + document2.select("li#SP07796758").select("p.value").text();
            final String imgURL = site_url + document2.select("div.pdp-improve-visual-img").select("img").first().attr("src");

            String price = document2.select("div.price-default.flag").select("p.price").text();
            if(price.equals(""))
                price = "?";
            // Storage Calculate
            final String storageType = document2.select("li#SP06236906").select("p.value").text();
            final String storageInterface = document2.select("li#SP07578916").select("p.value").text();
            final String storageCapacity = document2.select("li#SP06236907").select("p.value").text();
            final String storageFull = storageType +  " " + storageInterface + " " + storageCapacity ;

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
         // Update it:   laptop = new Laptop(id_laptop, modelName, url, companyName, processor, memory, operatingSystem, graphicCard, storageFull, screenSize, weight, battery, isTouchScreen, price, imgURL, desc);

            //Prints
            //  System.out.println("URL: " + url);
            // System.out.println("Company name: " + companyName);
            // System.out.println("Laptop name: " + modelName);
            //  System.out.println("Processor: " + processor);
            // System.out.println("Memory: " + memory);
            // System.out.println("Screen Size: " + screenSize);
            // System.out.println("Operating System: " + operatingSystem);
            //  System.out.println("Storage: " + storageFull);
            // System.out.println("Graphic Card: " + graphicCard);
            // System.out.println("Weight: " + weight);
            //  System.out.println("Battery: " + battery);
            // System.out.println("Price: " + price);
            //System.out.println("IMG URL: " + imgURL);
            // System.out.println("Touch Screen: " + isTouchScreen);
            //System.out.println("Desc: " + desc);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }


}
