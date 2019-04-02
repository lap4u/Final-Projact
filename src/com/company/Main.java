package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;


public class Main {


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

                System.out.println("Laptop: " + ticker);

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



    public static void main(String[] args) {
        List<Laptop> LaptopArray = new ArrayList<Laptop>();
        List<String> graphics= new ArrayList<String>();
        //HP_comp();
        //Lenovo.Find_Laptops(LaptopArray);
        //Lenovo.ParseData(LaptopArray);
        Acer.Find_Laptops(LaptopArray);
        Acer.ParseData(LaptopArray);
        //Dell.FindDellLaptops(LaptopArray);
        //LG.FindLGLaptops(LaptopArray);
        //CreateJSONFile.writeList(LaptopArray);
        printAllLeptops(LaptopArray);
        //printModels(LaptopArray);
        for(Laptop laptop:LaptopArray)
        {
            if(!graphics.contains(laptop.getProcessor()))
            {
                graphics.add(laptop.getProcessor());
            }
        }
        for(String gpu:graphics)
        {
            System.out.println(gpu);
        }

    }

    private static void printAllLeptops(List<Laptop> leptopArray) {
        for (Laptop leptop : leptopArray)
        {
            leptop.printLaptop();
        }
    }
    private static void printModels(List<Laptop> leptopArray) {
        for (Laptop leptop : leptopArray)
        {
            leptop.printModels();
        }
    }

}
