package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Lenovo {

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
                            laptop.setOperation_system(attributes_values[i]);
                        else if (attributes_lables[i].equals("Processor"))
                            laptop.setProcessor(attributes_values[i]);
                        else if (attributes_lables[i].equals("Memory"))
                            laptop.setMemory(attributes_values[i]);
                        else if (attributes_lables[i].equals("Graphics"))
                            laptop.setGpu(attributes_values[i]);
                        else if (attributes_lables[i].equals("Hard Drive"))
                            laptop.setStorage(attributes_values[i]);
                        else if (attributes_lables[i].equals("Display Type")) {
                            laptop.setScreen_size(attributes_values[i]);
                            if (attributes_values[i].toLowerCase().contains("multi-touch"))
                                laptop.setTouch_screen(true);
                            else
                                laptop.setTouch_screen(false);
                        } else if (attributes_lables[i].equals("Battery"))
                            laptop.setBattery(attributes_values[i]);
                    }
                    if (laptop.NotAllAttributeisFilled()) {
                        ParseLaptop(url, laptop);
                    }
                    laptop.setId_prod(LaptopArray.size());
                    laptop.setCompany_name("Lenovo");
                    laptop.setUrl_model(url);
                    laptop.setModel_name(FinalModelName);
                    laptop.setPrice(FinalPrice);
                    if (laptop.NotAllAttributeisFilled())
                        System.out.println("FOUND NULL. "+url);
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
            laptop.setProcessor(elememt.text());
        if (attribute_lable.equals("Memory") && laptop.getMemory() == null)
            laptop.setMemory(elememt.text());
        if (attribute_lable.equals("Operating System") && laptop.getOperation_system() == null)
            laptop.setOperation_system(elememt.text());
        if (attribute_lable.equals("Graphics") && laptop.getGpu() == null)
            laptop.setGpu(elememt.text());
        if (attribute_lable.equals("Storage") && laptop.getStorage() == null)
            laptop.setStorage(elememt.text());
        if (attribute_lable.equals("Display") && laptop.getScreen_size() == null)
            laptop.setScreen_size(elememt.text());
        if (attribute_lable.equals("Weight") && laptop.getWeight() == null)
            laptop.setWeight(elememt.text());
        if (attribute_lable.equals("Battery") && laptop.getBattery() == null)
            laptop.setBattery(elememt.text());
        if (attribute_lable.equals("Touch Screen") && laptop.getTouch_screen() == null) {
            if (elememt.text().contains("multi-touch"))
                laptop.setTouch_screen(true);
            else
                laptop.setTouch_screen(false);
        }
    }

    public static void parseLenovoElement(Laptop laptop, Elements elememt, String attribute_lable) {
        if (attribute_lable.equals("Processor") && laptop.getProcessor() == null)
            laptop.setProcessor(elememt.text());
        if (attribute_lable.equals("Memory") && laptop.getMemory() == null)
            laptop.setMemory(elememt.text());
        if (attribute_lable.equals("Operating System") && laptop.getOperation_system() == null)
            laptop.setOperation_system(elememt.text());
        if (attribute_lable.equals("Graphics") && laptop.getGpu() == null)
            laptop.setGpu(elememt.text());
        if (attribute_lable.equals("Storage") && laptop.getStorage() == null)
            laptop.setStorage(elememt.text());
        if (attribute_lable.equals("Display") && laptop.getScreen_size() == null)
            laptop.setScreen_size(elememt.text());
        if (attribute_lable.equals("Weight") && laptop.getWeight() == null)
            laptop.setWeight(elememt.text());
        if (attribute_lable.equals("Battery") && laptop.getBattery() == null)
            laptop.setBattery(elememt.text());
        if (attribute_lable.equals("Touch Screen") && laptop.getTouch_screen() == null) {
            if (elememt.text().contains("multi-touch"))
                laptop.setTouch_screen(true);
            else
                laptop.setTouch_screen(false);
        }
    }


    public static void ParseData(List<Laptop> LaptopArray) {
        String Processor = "";
        String[] ProcessorSplit;
        String[] ProcessorSplit0;
        String[] ProcessorSplit1;
        String ScreenSize = "";
        String[] ScreenSizeSplit;
        String[] ScreenSizeSplit0;
        String[] ScreenSizeSplit1;
        String GPU;
        String[] GPUSplit;
        String[] WeightSplit;
        String[] WeightSplit1;
        String Weight;
        String Storage="";
        String Battery="";
        String[] BatterySplit;
        String Memory="";


        for (Laptop laptop : LaptopArray) {
            if (laptop.getProcessor() != null) {
                if (laptop.getProcessor().contains("Intel")) {
                    Processor = "Intel";
                    ProcessorSplit = laptop.getProcessor().split("-");
                    ProcessorSplit0 = ProcessorSplit[0].split(" ");
                    ProcessorSplit1 = ProcessorSplit[1].split(" ");
                    Processor += " " + ProcessorSplit0[ProcessorSplit0.length - 1] + "-" + ProcessorSplit1[0];
                    laptop.setProcessor(Processor);
                } else if (laptop.getProcessor().contains("AMD")) {
                    Processor = "AMD";
                    ProcessorSplit = laptop.getProcessor().split("U");
                    ProcessorSplit0 = ProcessorSplit[0].split(" ");
                    Processor += " " + ProcessorSplit0[ProcessorSplit0.length - 1];
                    laptop.setProcessor(Processor);
                }
            }
            if (laptop.getScreen_size() != null) {
                if (laptop.getScreen_size().contains(" FHD "))
                    ScreenSize = laptop.getScreen_size().split("FHD")[0];
                if (laptop.getScreen_size().contains(" UHD "))
                    ScreenSize = laptop.getScreen_size().split("UHD")[0];
                if (laptop.getScreen_size().contains(" WQHD "))
                    ScreenSize = laptop.getScreen_size().split("WQHD")[0];
                if (laptop.getScreen_size().contains(" HDR "))
                    ScreenSize = laptop.getScreen_size().split("HDR")[0];
                if (laptop.getScreen_size().contains(" HD "))
                    ScreenSize = laptop.getScreen_size().split("HD")[0];
                ScreenSize = ScreenSize.replaceAll("\"", " ");
                laptop.setScreen_size(ScreenSize);
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

            if (laptop.getWeight() != null) {
                WeightSplit=laptop.getWeight().split("lbs");
                Weight =  WeightSplit[0].trim();
                if(Weight.contains("kg"))
                    Weight=Weight.split("kg")[1].replaceAll("[^.?0-9]+", "")+" lbs";
                else
                    Weight=Weight.replaceAll("[^.?0-9]+", "")+" lbs";

                laptop.setWeight(Weight.trim());
            }

            if (laptop.getStorage() != null) {
                if(laptop.getStorage().contains("GB"))
                {
                    Storage =  laptop.getStorage().split("GB")[0].trim() +" GB";
                }
                else if(laptop.getStorage().contains("TB"))
                {
                    Storage =  laptop.getStorage().split("TB")[0].trim()+" TB";
                }
                laptop.setStorage(Storage.trim());
            }
            if (laptop.getBattery() != null) {
                if(laptop.getBattery().toLowerCase().contains("whr"))
                {
                    BatterySplit =  laptop.getBattery().split("Whr")[0].split(" ");
                    Battery = BatterySplit[BatterySplit.length-1];
                }
                else if(laptop.getBattery().toLowerCase().contains("wh"))
                {
                    BatterySplit =  laptop.getBattery().split("Wh")[0].split(" ");
                    Battery = BatterySplit[BatterySplit.length-1];
                }
                else
                {
                    Battery = laptop.getBattery();
                }
                laptop.setBattery(Battery.trim()+" Wh");
            }
            if (laptop.getMemory() != null) {
                if(laptop.getMemory().toLowerCase().contains("gb"))
                {
                    Memory =  laptop.getMemory().split("GB")[0].trim();
                }
                else
                {
                    Memory = laptop.getMemory();
                }
                laptop.setMemory(Memory+" GB");
            }
        }
    }



    public static void printProcessor(List <Laptop> LaptopArray)
    {
        for(Laptop laptop : LaptopArray)
        {
            if(laptop!=null)
                System.out.println(laptop.getScreen_size());
        }
    }
}
