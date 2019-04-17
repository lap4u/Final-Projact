package com.company;
import Data.Struct;
import Parts.PartStruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import Data.PartsData;


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
        List<Struct> CPU_List = new ArrayList<Struct>();
        List<Struct> GPU_List = new ArrayList<Struct>();

        //HP_comp();
        //Lenovo.Find_Laptops(LaptopArray);
        //Acer.Find_Laptops(LaptopArray);
        //Acer.ParseData(LaptopArray);
        //Dell.FindDellLaptops(LaptopArray);
        //LG.FindLGLaptops(LaptopArray);
        //CreateJSONFile.writeList(LaptopArray);
        //printAllLeptops(LaptopArray);
        CPU_List = Data.PartsData.Parse_Data("https://www.videocardbenchmark.net/gpu_list.php");
        GPU_List = Data.PartsData.Parse_Data("https://www.cpubenchmark.net/cpu_list.php");
      System.out.println("sdfd");
    }

    private static void printAllLeptops(List<Laptop> laptopArray) {
        for (Laptop laptop : laptopArray)
        {
            laptop.printLaptop();
        }
    }

}
