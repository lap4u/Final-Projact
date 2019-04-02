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

    private static boolean Exclude_Dell_Comps(String i_Url) {
        boolean isOkComp = true;

        if (i_Url.equals("https://deals.dell.com/en-us/productdetail/275o"))
            isOkComp = false;

        return isOkComp;
    }

    public static void FindDellLaptops(List<Laptop> i_ArrLaptops) {

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

        Laptop laptop = null;

        try {
            final Document document2 = Jsoup.connect(url).get();
            final String companyName = "Dell";
            final String modelName = document2.select("h1").text();
            final String processor = document2.select("div.tech-spec-title").eq(0).text();
            final String operatingSystem = document2.select("div.tech-spec-title").eq(1).text();
            final String memory = document2.select("div.tech-spec-title").eq(2).text();
            final String storage = document2.select("div.tech-spec-title").eq(3).text();
            final String screenSize = document2.select("div.tech-spec-title").eq(4).text();
            final String graphicCard = document2.select("div.tech-spec-title").eq(5).text();
            final String weight = document2.select("div.tech-spec-title:contains(Weight)").text();
            final String price = (document2.select("div.col-xs-6.col-sm-5.col-md-7.text-right").select("span.price").text().split(" ")[0]) + "$";

            // Battery is complex becuase half the info of battery in "Power" and half in "Primary Battery" , So we check it:
            final String battery;
            Elements checkBattery = document2.select("div.tech-spec-title:has(strong:contains(Power))");
            if (checkBattery.text().equals(""))
                battery = document2.select("div.tech-spec-title:has(strong:contains(Primary Battery))").text();
            else
                battery = checkBattery.text();

            //Build Object of Laptop with this data.
            //laptop = new Laptop(id_laptop, modelName, url, companyName, processor, memory, operatingSystem, graphicCard, storage, screenSize, weight, battery, false, price);

            //Prints
            /*
            System.out.println("ID:  " + id_laptop);
            System.out.println("URL:  " + url);
            System.out.println("Company name:  " + companyName);
            System.out.println("Laptop name:  " + modelName);
            System.out.println("Processor:  " + processor);
            System.out.println("Memory:  " + memory);
            System.out.println("Screen Size:  " + screenSize);
            System.out.println("Operating System:  " + operatingSystem);
            System.out.println("Storage:  " + storage);
            System.out.println("Graphic Card:  " + graphicCard);
            System.out.println("Weight: " + weight);
            System.out.println("Battery: " + battery);
            System.out.println("Price: " + price);
            */

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }
}

