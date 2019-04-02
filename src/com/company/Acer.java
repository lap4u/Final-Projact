package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Acer {
    public static void Find_Laptops(List<Laptop> LaptopArray) {
        String url = "https://www.acer.com/ac/en/US/content/models/laptops";
        List<String> Laptops_urls = new ArrayList<String>();
        try {
            final Document document = Jsoup.connect(url).get();
            Elements Laptops_linkes = document.select("div.content");
            for (Element laptop : Laptops_linkes) {
                Element element_link = laptop.select("a").first();
                String link = element_link.attr("href");
                Laptops_urls.add(link);
            }

            for (String href : Laptops_urls) {
                ParseLaptop(href, LaptopArray);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public static void ParseLaptop(String url, List<Laptop> LaptopArray) {

        Laptop laptop = new Laptop();
        String finalUrl = "https://www.acer.com/ac/" + url;
        try {
            final Document document = Jsoup.connect(finalUrl).get();
            String ModelName=document.select(".bg-gray-2.sectionModelInfo.container > .max-width-d.container > .zh-bxsliderCont.gutter.row > .info.margin-bottom-l.margin-top-l.gutter.b-4.a-12.col > .margin-bottom-l.heading-l").text();
            ModelName=ModelName + " - "+document.select(".bg-gray-2.sectionModelInfo.container > .max-width-d.container > .zh-bxsliderCont.gutter.row > .info.margin-bottom-l.margin-top-l.gutter.b-4.a-12.col > .margin-bottom-0.text-tertiary-dark:nth-of-type(2)").text().replace("Model Name:","");
            String Price=document.select(".bg-gray-2.sectionModelInfo.container > .max-width-d.container > .zh-bxsliderCont.gutter.row > .info.margin-bottom-l.margin-top-l.gutter.b-4.a-12.col > .heading-l:nth-of-type(5)").text();
            String Description = document.select(".newStyle:nth-of-type(6) > .bg-gray-2.sectionModelInfo.container > .max-width-d.container > .zh-bxsliderCont.gutter.row > .info.margin-bottom-l.margin-top-l.gutter.b-4.a-12.col > .text-tertiary-dark:nth-of-type(4)").text();
            String imgUrl=document.select(".newStyle:nth-of-type(6) > .bg-gray-2.sectionModelInfo.container > .max-width-d.container > .zh-bxsliderCont.gutter.row > .gutter.b-7.a-12.col > .imgModelCont > .imgModel > .centerImage").attr("src");
            Elements attributes=document.select(".margin-bottom-l.margin-top-l.gutter.row > .a-12.col > .gutter.row");
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
            laptop.setPrice(Price);
            laptop.setImg_url(imgUrl);
            laptop.setUrl_model(finalUrl);
            LaptopArray.add(laptop);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public  static void operation_system_handler(Element operation_system_element, Laptop laptop) {
        String Operation_System="";
        Elements rows = operation_system_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Operating System"))
                Operation_System = attribute_value;
        }
        laptop.setOperation_system(Operation_System);
    }

    public static void processor_handler(Element processor_element, Laptop laptop) {
        String Processor="";
        Elements rows = processor_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Processor Manufacturer"))
                Processor = attribute_value;
            else if (attribute_lable.equals("Processor Model"))
                Processor += " "+attribute_value;
        }
        laptop.setProcessor(Processor);
    }

    public static void gpu_handler(Element gpu_element, Laptop laptop) {
        String Gpu="";
        String Screen_Size="";
        String Touch_Screen="";
        Elements rows = gpu_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Graphics Controller Manufacturer"))
                Gpu = attribute_value;
            else if (attribute_lable.equals("Graphics Controller Model"))
                Gpu += " "+attribute_value;
            else if (attribute_lable.equals("Screen Size"))
                Screen_Size = attribute_value;
            else if (attribute_lable.equals("Touchscreen"))
                Touch_Screen = attribute_value;
        }
        laptop.setGpu(Gpu);
        laptop.setScreen_size(Screen_Size);
        if(Touch_Screen.equals("Yes"))
            laptop.setTouch_screen(true);
        else
            laptop.setTouch_screen(false);
    }
    public static void memory_handler(Element memory_element, Laptop laptop) {
        String Memory="";
        Elements rows = memory_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Standard Memory"))
                Memory = attribute_value;
            else if (attribute_lable.equals("Memory Technology"))
                Memory += " "+attribute_value;
        }
        laptop.setMemory(Memory);
    }
    public static void storage_handler(Element storage_element, Laptop laptop) {
        String Storage="";
        Elements rows = storage_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Total Solid State Drive Capacity") | attribute_lable.equals("Total Hard Drive Capacity") | attribute_lable.equals("Flash Memory Capacity"))
                Storage = attribute_value;
        }
        laptop.setStorage(Storage);
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
    public static void weight_handler(Element battary_element, Laptop laptop) {
        String Weight="";
        Elements rows = battary_element.select(".row");
        for (Element row : rows) {
            String attribute_lable = row.select(".c-3.a-12.col").text().trim();
            String attribute_value = row.select(".bordL.c-8.a-12.gutter.col").text().trim();

            if (attribute_lable.equals("Weight (Approximate)") | attribute_lable.equals("Weight with Dock (Approximate)"))
                Weight = attribute_value;
        }
        laptop.setWeight(Weight);
    }
    public static void ParseData(List<Laptop> LaptopArray) {
        String Processor = "";
        String Memory = "";
        String GPU;
        String[] GPUSplit;
        String Battery="";
        String[] BatterySplit;
        for (Laptop laptop : LaptopArray) {
            if (laptop.getProcessor() != null) {
                if (laptop.getProcessor().contains("Intel")) {
                }
                Processor = laptop.getProcessor().replace("®", "");
                laptop.setProcessor(Processor);
            }
            if (laptop.getMemory() != null) {
                if (laptop.getMemory().toLowerCase().contains("gb")) {
                    Memory = laptop.getMemory().split("GB")[0].trim();
                } else {
                    Memory = laptop.getMemory();
                }
                laptop.setMemory(Memory + " GB");
            }
            if (laptop.getBattery() != null) {
                if (laptop.getBattery().contains("mAh")) {
                    BatterySplit = laptop.getBattery().split("mAh")[0].split(" ");
                    Battery = BatterySplit[BatterySplit.length - 1];
                } else {
                    Battery = laptop.getBattery();
                }
                laptop.setBattery(Battery.trim() + " Wh");

            }
            if (laptop.getGpu() != null) {
                if (laptop.getGpu().contains("Intel")) {
                    GPU = "Intel " + laptop.getGpu().replaceAll("[^0-9]+", " ").trim();
                    laptop.setGpu(GPU);
                } else if (laptop.getGpu().toLowerCase().contains("nvidia")) {
                    if (laptop.getGpu().contains(" GTX ")) {
                        GPUSplit = laptop.getGpu().split(" GTX ");
                        GPU = ("Nvidia P" + GPUSplit[1].split(" ")[0]).trim();
                        laptop.setGpu(GPU);
                    } else if (laptop.getGpu().contains(" P")) {
                        GPUSplit = laptop.getGpu().split(" P");
                        GPU = ("Nvidia P" + GPUSplit[1].split(" ")[0]).trim();
                        laptop.setGpu(GPU);
                    } else if (laptop.getGpu().contains(" M")) {
                        GPUSplit = laptop.getGpu().split(" M");
                        GPU = ("Nvidia M" + GPUSplit[1].split(" ")[0]).trim();
                        laptop.setGpu(GPU);
                    }
                }
            }
        }

    }
}
