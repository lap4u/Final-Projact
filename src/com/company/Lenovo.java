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

public class Lenovo {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public Lenovo() {
    }

    public static void Find_Laptops(List<Laptop> LaptopArray) {
        Boolean is_exist = false;
        List<String> links = new ArrayList<String>();
        String SeriesList[] = {"https://www.lenovo.com/us/en/laptops/thinkpad/thinkpad-x/c/thinkpadx",
                "https://www.lenovo.com/us/en/laptops/thinkpad/thinkpad-t-series/c/thinkpadt",
                "https://www.lenovo.com/us/en/laptops/thinkpad/thinkpad-p/c/thinkpadp",
                "https://www.lenovo.com/us/en/laptops/thinkpad/thinkpad-e-series/c/thinkpade",
                "https://www.lenovo.com/us/en/laptops/thinkpad/thinkpad-a-series/c/thinkpada",
                "https://www.lenovo.com/us/en/laptops/thinkpad/11e-and-chromebooks/c/thinkpad11e"};

        try {

            for (String url : SeriesList) {
                final Document document3 = Jsoup.connect(url).get();
                Element comp_urls = document3.select(".cd-products-comparison-table").first();
                if (comp_urls != null) {
                    Elements com = comp_urls.select("a");

                    for (Element comp : com) {
                        String atar = comp.attr("href");
                        if (!links.contains("https://www.lenovo.com" + atar)) {
                            newParseLaptop("https://www.lenovo.com" + atar, LaptopArray);
                            links.add("https://www.lenovo.com" + atar);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public static void newParseLaptop(String url, List<Laptop> LaptopArray) {
        String LaptopSummeryHeader;
        String LaptopSummeryBody;
        String[] attributes_lables;
        String[] attributes_values;
        String[] StringFormatLaptop;

        try {
            final Document LenovoLeptopUrlDocument = Jsoup.connect(url).get();
            String imgUrl = "https://www.lenovo.com" + LenovoLeptopUrlDocument.select(".noeSpot.subseriesHeader > .single_img.hero-column-two.hero-column > .no-margin.rollovercartItemImg.subSeries-Hero").attr("src");
            LaptopSummeryBody = LenovoLeptopUrlDocument.select(".noeSpot.subseriesHeader > .hero-column-one.hero-column > .mediaGallery-productDescription.hero-productDescription > .mediaGallery-productDescription-body.hero-productDescription-body").text();

            final Element table = LenovoLeptopUrlDocument.select("ol").first();
            if (table != null) {
                final Elements versionModel = table.select("div.tabbedBrowse-productListing");
                for (Element version : versionModel) {
                    Laptop laptop = new Laptop();
                    laptop.setDescription(LaptopSummeryBody);
                    laptop.setImg_url(imgUrl);
                    String FinalModelName = version.select(".tabbedBrowse-productListing-header > .tabbedBrowse-productListing-title").text();
                    String FinalPrice = version.select(".tabbedBrowse-productListing-body > .pricingSummary > .pricingSummary-details > .pricingSummary-details-final-price.saleprice").text();
                    final Elements details = version.select("dl");

                    int i = 0;
                    Element values = null;
                    for (Element det : details) {

                        if (i == 1)
                            values = det;
                        i++;
                    }

                    Elements a = values.select("dt");
                    Elements b = values.select("dd");
                    attributes_lables = new String[a.size()];
                    attributes_values = new String[b.size()];
                    i = 0;
                    for (Element c : a) {

                        attributes_lables[i] = c.text();
                        i++;
                    }
                    i = 0;
                    for (Element d : b) {
                        attributes_values[i] = d.text();
                        i++;
                    }
                    for (i = 0; i < attributes_lables.length; i++) {
                        if (attributes_lables[i].equals("Operating System"))
                            laptop.setOperation_system(getFormat_OS(attributes_values[i]));
                        else if (attributes_lables[i].equals("Processor")) {
                            laptop.setProcessor(getFormat_CPU(attributes_values[i]));
                        } else if (attributes_lables[i].equals("Memory"))
                            laptop.setMemory(getFormat_Memory(attributes_values[i]));
                        else if (attributes_lables[i].equals("Graphics"))
                            laptop.setGpu(getFormat_GPU(attributes_values[i]));
                        else if (attributes_lables[i].equals("Hard Drive"))
                            laptop.setStorage(getFormat_Storage(attributes_values[i]));
                        else if (attributes_lables[i].equals("Display Type")) {
                            laptop.setScreen_size(getFormat_ScreenSize(attributes_values[i]));
                            if (attributes_values[i].toLowerCase().contains("multi-touch"))
                                laptop.setTouch_screen(true);
                            else
                                laptop.setTouch_screen(false);
                        } else if (attributes_lables[i].equals("Battery"))
                            laptop.setBattery(getFormat_Battery(attributes_values[i]));
                    }
                    if (laptop.NotAllAttributeisFilled()) {
                        ParseLaptop(url, laptop);
                    }
                    laptop.setId_prod(LaptopArray.size());
                    laptop.setCompany_name("Lenovo");
                    laptop.setUrl_model(url);
                    laptop.setModel_name(FinalModelName);
                    laptop.setPrice(getFormat_Price(FinalPrice));
//                    if (laptop.NotAllAttributeisFilled())
//                        System.out.println("FOUND NULL. "+url);
                    LaptopArray.add(laptop);
                }
            }

        } catch (Exception ex) {
            System.out.println(url);
            ex.printStackTrace();
        }
    }


    public static void ParseLaptop(String url, Laptop laptop) {
        try {
            final Document LenovoLeptopUrlDocument = Jsoup.connect(url).get();
            final Elements table = LenovoLeptopUrlDocument.select(".techSpecs-table");
            for (Element line : table.select("tr")) {
                String attribute_lable = (line.select("td:nth-child(1)").text());
                attribute_lable = attribute_lable.split("\\?")[0].trim();
                Elements attribute_value = line.select("td:nth-child(2)");
                if (line.select("td:nth-child(2)").select("li").size() != 0) {
                    for (Element li : line.select("td:nth-child(2)").select("li")) {
                        parseLenovoElement(laptop, attribute_value, attribute_lable);
                    }
                } else {
                    parseLenovoElements(laptop, attribute_value, attribute_lable);
                }
            }
        } catch (Exception ex) {
            System.out.println(url);
            ex.printStackTrace();
        }
    }

    public static void parseLenovoElements(Laptop laptop, Elements elememt, String attribute_lable) {
        if (attribute_lable.equals("Processor") && laptop.getProcessor() == null)
            laptop.setProcessor(getFormat_CPU(elememt.text()));
        if (attribute_lable.equals("Memory") && laptop.getMemory() == 0)
            laptop.setMemory(getFormat_Memory(elememt.text()));
        if (attribute_lable.equals("Operating System") && laptop.getOperation_system() == null)
            laptop.setOperation_system(getFormat_OS(elememt.text()));
        if (attribute_lable.equals("Graphics") && laptop.getGpu() == null)
            laptop.setGpu(getFormat_GPU(elememt.text()));
        if (attribute_lable.equals("Storage") && laptop.getStorage() == 0)
            laptop.setStorage(getFormat_Storage(elememt.text()));
        if (attribute_lable.equals("Display") && laptop.getScreen_size() == 0)
            laptop.setScreen_size(getFormat_ScreenSize(elememt.text()));
        if (attribute_lable.equals("Weight") && laptop.getWeight() == 0)
            laptop.setWeight(getFormat_Weight(elememt.text()));
        if (attribute_lable.equals("Battery") && laptop.getBattery() == 0)
            laptop.setBattery(getFormat_Battery(elememt.text()));
        if (attribute_lable.equals("Touch Screen") && laptop.getTouch_screen() == null) {
            if (elememt.text().contains("multi-touch"))
                laptop.setTouch_screen(true);
            else
                laptop.setTouch_screen(false);
        }
    }

    public static void parseLenovoElement(Laptop laptop, Elements elememt, String attribute_lable) {
        if (attribute_lable.equals("Processor") && laptop.getProcessor() == null)
            laptop.setProcessor(getFormat_CPU(elememt.text()));
        if (attribute_lable.equals("Memory") && laptop.getMemory() == 0)
            laptop.setMemory(getFormat_Memory(elememt.text()));
        if (attribute_lable.equals("Operating System") && laptop.getOperation_system() == null)
            laptop.setOperation_system(getFormat_OS(elememt.text()));
        if (attribute_lable.equals("Graphics") && laptop.getGpu() == null)
            laptop.setGpu(getFormat_GPU(elememt.text()));
        if (attribute_lable.equals("Storage") && laptop.getStorage() == 0)
            laptop.setStorage(getFormat_Storage(elememt.text()));
        if (attribute_lable.equals("Display") && laptop.getScreen_size() == 0)
            laptop.setScreen_size(getFormat_ScreenSize(elememt.text()));
        if (attribute_lable.equals("Weight") && laptop.getWeight() == 0)
            laptop.setWeight(getFormat_Weight(elememt.text()));
        if (attribute_lable.equals("Battery") && laptop.getBattery() == 0)
            laptop.setBattery(getFormat_Battery(elememt.text()));
        if (attribute_lable.equals("Touch Screen") && laptop.getTouch_screen() == null) {
            if (elememt.text().contains("multi-touch"))
                laptop.setTouch_screen(true);
            else
                laptop.setTouch_screen(false);
        }
    }

    public static OS getFormat_OS(String OS_String_format)
    {
        String[] OS_String;
        String Manufacture="";
        String Version="";
        int Serios = 0;
        int Bit_Siz = 0;

        if(OS_String_format.toLowerCase().contains("windows"))
        {
            OS_String = OS_String_format.split(" ");
            //System.out.println(OS_String[0]+"\n"+OS_String[1]+"\n"+OS_String[2]+"\n"+OS_String[3]+"\n");
            Manufacture = OS_String[0];
            Serios = Integer.parseInt(OS_String[1]);
            Version = OS_String[2];
            Bit_Siz = 64;
        }

        OS OS_Struct = new OS(Manufacture,Version,Serios,Bit_Siz);
        return OS_Struct;
    }


    public static PartStruct getFormat_CPU(String CPU_String_format) {
        String CPU_Split[];
        String CPU_Split1[];
        String CPU_Split2[];
        String Manufacture = "";
        String Model = "";
        if (CPU_String_format.contains("Intel")) {
            Manufacture = "Intel";
            CPU_Split = CPU_String_format.split("-");
            CPU_Split1 = CPU_Split[0].split(" ");
            CPU_Split2 = CPU_Split[1].split(" ");
            Model = CPU_Split1[CPU_Split1.length - 1] + "-" + CPU_Split2[0];

        } else if (CPU_String_format.contains("AMD")) {
            Manufacture = "AMD";
            CPU_Split = CPU_String_format.split("U");
            CPU_Split1 = CPU_Split[0].split(" ");
            Model = CPU_Split1[CPU_Split1.length - 1];
        }
        PartStruct CPUStruct = new PartStruct(Manufacture, Model);
        return CPUStruct;
    }

    public static int getFormat_Memory(String Memory_String_format) {
        int Memory = 0;
        if (Memory_String_format.toLowerCase().contains("gb"))
            Memory = Integer.parseInt(Memory_String_format.split("GB")[0].trim());
        else
            Memory = Integer.parseInt(Memory_String_format.trim());
        return (Memory);
    }

    public static int getFormat_Storage(String Storage_String_format) {
        int Storage = 0;
        if (Storage_String_format.contains("GB"))
            Storage = Integer.parseInt(Storage_String_format.split("GB")[0].trim());
        else if (Storage_String_format.contains("TB"))
            Storage = 1024 * Integer.parseInt(Storage_String_format.split("TB")[0].trim());
        return Storage;
    }

    public static double getFormat_ScreenSize(String ScreenSize_String_format) {
        String ScreenSizeString = "";
        double ScreenSize = 0;
        if (ScreenSize_String_format.contains(" FHD "))
            ScreenSizeString = ScreenSize_String_format.split("FHD")[0];
        if (ScreenSize_String_format.contains(" UHD "))
            ScreenSizeString = ScreenSize_String_format.split("UHD")[0];
        if (ScreenSize_String_format.contains(" WQHD "))
            ScreenSizeString = ScreenSize_String_format.split("WQHD")[0];
        if (ScreenSize_String_format.contains(" HDR "))
            ScreenSizeString = ScreenSize_String_format.split("HDR")[0];
        if (ScreenSize_String_format.contains(" HD "))
            ScreenSizeString = ScreenSize_String_format.split("HD")[0];

        ScreenSizeString = ScreenSizeString.replaceAll("[^\\d.]", "");
        ScreenSize = Double.parseDouble(ScreenSizeString.trim());
        return (ScreenSize);
    }

    public static double getFormat_Weight(String Weight_String_format) {
        double Weight = 0;
        df2.setRoundingMode(RoundingMode.UP);
        String[] WeightSplit;
        String WeightString;
        WeightSplit = Weight_String_format.split("lbs");
        WeightString = WeightSplit[0].trim();

        if (WeightString.contains("kg"))
            WeightString = WeightString.split("kg")[1].replaceAll("[^.?0-9]+", "");
        else
            WeightString = WeightString.replaceAll("[^.?0-9]+", "");

        Weight = 0.45 * Double.parseDouble(WeightString.trim());
        return Double.parseDouble(df2.format(Weight));

    }

    public static int getFormat_Battery(String Battery_String_format) {
        int Battery = 0;
        String[] BatterySplit;
        //System.out.println(Battery_String_format);
        if (Battery_String_format.toLowerCase().contains("whr")) {
            BatterySplit = Battery_String_format.split("Whr")[0].split(" ");
            //System.out.println(BatterySplit[BatterySplit.length - 1]);
            Battery = Integer.parseInt(BatterySplit[BatterySplit.length - 1].trim());
        } else if (Battery_String_format.toLowerCase().contains("wh")) {
            BatterySplit = Battery_String_format.split("Wh")[0].split(" ");
            //System.out.println(BatterySplit[BatterySplit.length - 1]);
            Battery = Integer.parseInt(BatterySplit[BatterySplit.length - 1].trim());
        } else
            Battery = Integer.parseInt(Battery_String_format.trim());

        //System.out.println(Battery);
        return Battery;
    }

    public static double getFormat_Price(String Price_String_format) {
        double Price = 0;
        String PriceString;

        PriceString = Price_String_format.replaceAll(",", "");
        PriceString = PriceString.replaceAll("\\$", "");
        PriceString = PriceString.split("\\.")[0];

        Price = Double.parseDouble(PriceString.trim());
        return Price;
    }

    public static PartStruct getFormat_GPU(String GPU_String_format) {
        String GPU_Split[];
        String Manufacture = "";
        String Model = "";

        if (GPU_String_format.contains("Intel")) {
            Manufacture = "Intel";
            Model = GPU_String_format.replaceAll("[^0-9]+", " ").trim();
        } else if (GPU_String_format.toLowerCase().contains("nvidia")) {
            Manufacture = "Nvidia";
            if (GPU_String_format.contains(" GTX ")) {
                GPU_Split = GPU_String_format.split(" GTX ");
                Model = ("P" + GPU_Split[1].split(" ")[0]).trim();
            } else if (GPU_String_format.contains(" P")) {
                GPU_Split = GPU_String_format.split(" P");
                Model = ("P" + GPU_Split[1].split(" ")[0]).trim();
            } else if (GPU_String_format.contains(" M")) {
                GPU_Split = GPU_String_format.split(" M");
                Model = ("M" + GPU_Split[1].split(" ")[0]).trim();

            }
        }
        PartStruct GPUStruct = new PartStruct(Manufacture, Model);
        return GPUStruct;
    }

}