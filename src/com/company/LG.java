package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LG {

    public LG(){}

    private static boolean Exclude_LG_Comps(String i_Url) {
        boolean isOkComp = true;

        if (i_Url.equals("https://www.lg.com/us/laptops/lg-15Z975-UAAS7U1-ultra-slim-laptop"))
            isOkComp = false;
        else
        if (i_Url.equals("https://www.lg.com/us/laptops/lg-13Z980-AAAS6U1"))
            isOkComp = false;
        else
        if (i_Url.equals("https://www.lg.com/us/laptops/lg-15Z970-UAAS5U1-ultra-slim-laptop"))
            isOkComp = false;
        else
        if (i_Url.equals("https://www.lg.com/us/laptops/lg-15Z975-AAAS7U1-ultra-slim-laptop"))
            isOkComp = false;
        else
        if (i_Url.equals("https://www.lg.com/us/laptops/lg-15Z975-AAAS5U1-ultra-slim-laptop"))
            isOkComp = false;

        return isOkComp;
    }

    public static void FindLGLaptops(List<Laptop> i_ArrLaptops) {

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

                if (Exclude_LG_Comps(product_url)) {
                    laptop = compSaveLG(product_url, i_ArrLaptops.size());
                    i_ArrLaptops.add(laptop);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static Laptop compSaveLG(String url, int id_laptop) {

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
            final String weight = document2.select("li#SP07591580").select("p.value").text() + document2.select("li#SP06236945").select("p.value").text();
            final String battery = document2.select("li#SP06236935").select("p.value").text() + document2.select("li#SP07707838").select("p.value").text() + document2.select("li#SP07796758").select("p.value").text();


            final String price = document2.select("div.price-default.flag").select("p.price").text();
            // Storage Calculate
            final String storageType = document2.select("li#SP06236906").select("p.value").text();
            final String storageInterface = document2.select("li#SP07578916").select("p.value").text();
            final String storageCapacity = document2.select("li#SP06236907").select("p.value").text();
            final String storageFull = storageType +  " " + storageInterface + " " + storageCapacity ;

            //laptop = new Laptop(id_laptop, modelName, url, companyName, processor, memory, operatingSystem, graphicCard, storageFull, screenSize, weight, battery, false, price);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return laptop;
    }


}
