package com.company;

import Parts.OS;
import Parts.PartStruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Acer {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void Find_Laptops(List<Laptop> LaptopArray) {
        String url = "https://www.acer.com/ac/en/US/content/models/laptops";
        List<String> Laptops_urls = new ArrayList<String>();
        try {
            final Document document = Jsoup.connect(url).get();
            org.jsoup.select.Elements Laptops_linkes = document.select("div.content");
            for (Element laptop : Laptops_linkes) {
                Element element_link = laptop.select("a").first();
                String link = element_link.attr("href");
                Laptops_urls.add(link);
            }

            for (String href : Laptops_urls) {
                if(isNotExclude(href))
                ParseLaptop(href, LaptopArray);
            }
        } catch (Exception ex) {
            System.out.println(url);
            ex.printStackTrace();
        }


    }

    public static void ParseLaptop(String url, List<Laptop> LaptopArray) {

        Laptop laptop = new Laptop();
        String finalUrl = "https://www.acer.com/ac/" + url;
        try {
            final Document document = Jsoup.connect(finalUrl).get();
            String ModelName = document.select(".bg-gray-2.sectionModelInfo.container > .max-width-d.container > .zh-bxsliderCont.gutter.row > .info.margin-bottom-l.margin-top-l.gutter.b-4.a-12.col > .margin-bottom-l.heading-l").text().trim();
            ModelName = ModelName + " - "+document.select(".bg-gray-2.sectionModelInfo.container > .max-width-d.container > .zh-bxsliderCont.gutter.row > .info.margin-bottom-l.margin-top-l.gutter.b-4.a-12.col > .margin-bottom-0.text-tertiary-dark:nth-of-type(2)").text().replace("Model Name:","").trim();
            String Price = document.select("p.heading-l").text();
            String Description = document.select("p.text-tertiary-dark").first().text();

            String imgUrl= "http:" + document.select(".newStyle:nth-of-type(6) > .bg-gray-2.sectionModelInfo.container > .max-width-d.container > .zh-bxsliderCont.gutter.row > .gutter.b-7.a-12.col > .imgModelCont > .imgModel > .centerImage").attr("src");
            ArrayList<String> imagesUrls = getImagesArray(document);


            org.jsoup.select.Elements attributes = document.select(".margin-bottom-l.margin-top-l.gutter.row > .a-12.col > .gutter.row");
            for(Element attribute:attributes) {
                String header = attribute.select(".bordR.c-3.a-12.col").text();
                if(header.equals("Operating System"))
                    operation_system_handler(attribute,laptop);
                if(header.equals("Processor & Chipset"))
                    processor_handler(attribute,laptop);
                if(header.equals("Display & Graphics"))
                    gpu_handler(attribute,laptop);
                if(header.equals("Memory"))
                    memory_handler(attribute,laptop);
                if(header.equals("Storage"))
                    storage_handler(attribute,laptop);
                if(header.equals("Battery Information"))
                    battary_handler(attribute,laptop);
                if(header.equals("Physical Characteristics"))
                    weight_handler(attribute,laptop);
            }
            laptop.setId_prod(LaptopArray.size());
            laptop.setCompany_name("Acer");
            laptop.setModel_name(ModelName);
            laptop.setDescription(Description);
            laptop.setPrice(Double.parseDouble(Price.replaceAll("[^\\d.]", "").trim()));
            laptop.setImagesUrls(imagesUrls);
            laptop.setUrl_model(finalUrl);
            if(!laptop.NotAllAttributeisFilled())
                LaptopArray.add(laptop);

        }catch (Exception ex) {
        System.out.println(url);
        ex.printStackTrace();
    }

    }


    public static ArrayList<String> getImagesArray(Document i_Document)
    {
        ArrayList<String> imagesArray = new ArrayList<>();
        Elements getImages = i_Document.select("div.flex.zh-galleryMobile").select("img.centerImage");


            for(int i=0;i<getImages.size();i++)
            {
                imagesArray.add("https:" + getImages.eq(i).attr("src"));
                System.out.println(imagesArray.get(i));
            }

            return imagesArray;
    }

    public  static void operation_system_handler(Element operation_system_element, Laptop laptop) {
        OS OS_Struct = null;
        Elements rows = operation_system_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Operating System"))
            {
                String[] OS_String;
                String Manufacture="";
                String Version="";
                int Series = 0;
                int Bit_Siz = 0;

                if(attribute_value.toLowerCase().contains("windows"))
                {
                    OS_String = attribute_value.split(" ");
                    Manufacture = OS_String[0];
                    Series = Integer.parseInt(OS_String[1]);
                    Version = OS_String[2];
                    Bit_Siz = 64;
                }
                else if(attribute_value.toLowerCase().contains("chrome"))
                    Manufacture = "Chrome OS";
                OS_Struct = new OS(Manufacture,Version,Series,Bit_Siz);
            }
            laptop.setOperation_system(OS_Struct);
        }
    }

    public static void processor_handler(Element processor_element, Laptop laptop) {
        String Manufacture = "";
        String Model = "";
        PartStruct CPU = null;
        Elements rows = processor_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Processor Manufacturer"))
                Manufacture = attribute_value.replaceAll("®","").trim();
            else if (Manufacture == "AMD" && attribute_lable.equals("Processor Type"))
                Model = attribute_value.trim();
            else if (attribute_lable.equals("Processor Model")) {
                if(Manufacture == "AMD")
                    Model += " " + attribute_value.trim();
                else
                    Model = attribute_value.trim();
            }

        }
        CPU = new PartStruct(Manufacture,Model);
        laptop.setProcessor(CPU);

    }

    public static void gpu_handler(Element gpu_element, Laptop laptop) {
        String Manufacture = "";
        String Model = "";
        PartStruct GPU = null;
        double Screen_Size = 0;
        String Touch_Screen = "";
        Elements rows = gpu_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Graphics Controller Manufacturer"))
                Manufacture = attribute_value.replaceAll("®","").trim();
            else if (attribute_lable.equals("Graphics Controller Model"))
                Model = attribute_value.replaceAll("GeForce®","").trim();
            else if (attribute_lable.equals("Screen Size"))
                Screen_Size = Double.parseDouble(attribute_value.replaceAll("[^\\d.]", ""));
            else if (attribute_lable.equals("Touchscreen"))
                Touch_Screen = attribute_value;
        }
        GPU = new PartStruct(Manufacture,Model);
        laptop.setGpu(GPU);
        laptop.setScreen_size(Screen_Size);
        if(Touch_Screen.equals("Yes"))
            laptop.setTouch_screen(true);
        else
            laptop.setTouch_screen(false);
    }
    public static void memory_handler(Element memory_element, Laptop laptop) {
        int Memory = 0;
        Elements rows = memory_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Standard Memory"))
                Memory = Integer.parseInt(attribute_value.replaceAll("[^\\d.]", ""));
        }
        laptop.setMemory(Memory);
    }
    public static void storage_handler(Element storage_element, Laptop laptop) {
        String Storage_String = "";
        int Storage = 0;
        Elements rows = storage_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Total Solid State Drive Capacity") | attribute_lable.equals("Total Hard Drive Capacity") | attribute_lable.equals("Flash Memory Capacity"))
                Storage_String = attribute_value;
        }
        if(Storage_String.contains("TB"))
            Storage = 1024 * Integer.parseInt(Storage_String.replaceAll("TB","").trim());
        else
            Storage = Integer.parseInt(Storage_String.replaceAll("GB","").trim());
        Parts.Storage Storage_struct = new Parts.Storage(true,Storage);
        laptop.setStorage(Storage_struct);
    }

    public static void battary_handler(Element battary_element, Laptop laptop) {
        String Battary="";
        Elements rows = battary_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Number of Cells"))
                Battary = attribute_value;
            else if(attribute_lable.equals("Battery Chemistry"))
                Battary += " " + attribute_value;
            else if(attribute_lable.equals("Battery Capacity"))
                Battary += " " + attribute_value;
        }
        laptop.setBattery(Battary);
    }
    public static void weight_handler(Element weight_element, Laptop laptop) {
        double Weight = 0;
        df2.setRoundingMode(RoundingMode.UP);

        Elements rows = weight_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();


            if (attribute_lable.equals("Weight (Approximate)") | attribute_lable.equals("Weight with Dock (Approximate)")) {
                String[] splitNumber = attribute_value.split(" ", 2);
                Weight = 0.45 * Double.parseDouble(splitNumber[0].trim());
            }
        }

        laptop.setWeight(Double.parseDouble(df2.format(Weight)));
    }

    private static boolean isNotExclude(String i_href)
    {
        boolean flag = true;
        if(i_href.equals("en/US/content/model/NX.HGMAA.001"))
            flag = false;


        return flag;
    }
}

