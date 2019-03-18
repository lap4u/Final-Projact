package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Documented;



public class Main {
    static void Lenovo_comp() {
        final String main_url = "https://www.lenovo.com/us/en/think?IPromoID=LEN635973";
        try{
            final Document document3= Jsoup.connect(main_url).get();

            Elements comp_urls=document3.select(".va-item");
            //System.out.println(document3.outerHtml());
            System.out.println("size of List: "+comp_urls.size());
            for(Element comp : comp_urls)
            {
                //Element atar= comp.select()
                comp=comp.select("a").first();
                String atar=comp.attr("href");
                oneComp("https://www.lenovo.com/us/en/"+atar);
                //System.out.println(atar);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }



    }


    static void HP_comp()
    {
        final String url="https://store.hp.com/us/en/vwa/laptops/segm=Home?jumpid=ma_lt_featured_na_6_181216";
        try {
            final Document document= Jsoup.connect(url).get();
            for(Element row : document.select(
                    "div.specsContent")){
                final String ticker=row.select("div.pdtName").text();
                //final String price =row.select("div.specsContent").text();
                //System.out.println(price);
                System.out.println("\n\n*********************************");

                System.out.println("Laptop: "+ticker);

                System.out.println("*********************************");
                for(Element att : row.select(
                        "div.pdtSpecsAttr")){
                    final String attri=att.select("div.attr").text();
                    final String valatt=att.select("div.attrVal").text();
                    if(attri.equals("Operating system")||attri.equals("Storage") ||attri.equals("Processor and graphics")||attri.equals("Memory")) {
                        System.out.println(att.select("div.attr").text() + ": " + att.select("div.attrVal").text());
                    }
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void oneComp(String url)
    {
        try {
            final Document document2 = Jsoup.connect(url).get();
            final String LaptopName=document2.select("h1.desktopHeader").text();
            System.out.println("************************\n"+LaptopName+"\n***********************\n");
            final Elements table = document2.select(".techSpecs-table");
            for(Element line : table.select("tr"))
            {
                String lable=(line.select("td:nth-child(1)").text());
                lable=lable.split("\\?")[0];
                if(lable != null && !lable.isEmpty())
                    System.out.println(lable+":");
                Elements newElem= line.select("td:nth-child(2)");
                Elements newLi=newElem.select("li");
                if(newLi.size()!=0) {
                    for (Element li : line.select("td:nth-child(2)").select("li")) {
                        System.out.println("    " + li.text());
                    }
                }
                else
                {
                    System.out.println("    " + newElem.text());
                }

            }

            System.out.println("\n\n*********************************");


        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //HP_comp();
        Lenovo_comp();
    }

}
