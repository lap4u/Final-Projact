package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Lenovo {

    public Lenovo(){}

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
                Elements com = comp_urls.select("a");
                //System.out.println(document3.outerHtml());
                //System.out.println("size of List: " + comp_urls.size());
                for (Element comp : com) {
                    is_exist = false;
                    //Element atar= comp.select()
                    //comp = comp.select("a").first();
                    for (String link : links) {
                        if (comp.attr("href").equals(link)) {
                            is_exist = true;
                        }
                    }
                    if (is_exist == false) {
                        String atar = comp.attr("href");
                        //ParseLaptop("https://www.lenovo.com" + atar, LaptopArray);
                        newParseLaptop("https://www.lenovo.com" + atar, LaptopArray);
                        links.add(atar);
                    }
                    //System.out.println(atar);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public static void newParseLaptop(String url, List<Laptop> LaptopArray) {

        System.out.println("url: "+url);

        String[] attributes_lables;
        String[] attributes_values;

        try {
            //url="https://www.lenovo.com/us/en/laptops/thinkpad/thinkpad-x/ThinkPad-X1-Extreme/p/22TP2TXX1E1";
            final Document LenovoLeptopUrlDocument = Jsoup.connect(url).get();
            final String LaptopName = LenovoLeptopUrlDocument.select("h1.desktopHeader").text();

            //System.out.println(LaptopName);


            final Element table = LenovoLeptopUrlDocument.select("ol").first();
            final Elements versionModel = table.select("div.tabbedBrowse-productListing");
            for(Element version:versionModel) {
                Laptop laptop=new Laptop();
                laptop.setCompany_name("Lenovo");
                laptop.setUrl_model(url);
                String FinalModelName=version.select(".tabbedBrowse-productListing-header > .tabbedBrowse-productListing-title").text();
                String FinalPrice = version.select(".tabbedBrowse-productListing-body > .pricingSummary > .pricingSummary-details > .pricingSummary-details-final-price.saleprice").text();
                laptop.setModel_name(FinalModelName);
                laptop.setPrice(FinalPrice);
                final Elements details =version.select("dl");

                int i=0;
                Element values=null;
                //Element price=null;
                //String finalPrice;
                for(Element det:details)
                {
                    //if(i==0)
                        //price=det;
                    if(i==1)
                        values=det;
                    i++;
                }

                Elements a=values.select("dt");
                Elements b=values.select("dd");
                attributes_lables=new String[a.size()];
                attributes_values=new String[b.size()];
                i=0;
                for(Element c:a) {

                    attributes_lables[i]=c.text();
                    i++;
                }
                i=0;
                for(Element d:b){
                    attributes_values[i]=d.text();
                    i++;
                }
               // System.out.println(values);
                //finalPrice=price.select("dd").text();
                for(i=0;i<attributes_lables.length;i++)
                {
                    if(attributes_lables[i].equals("Operating System"))
                        laptop.setOperation_system(attributes_values[i]);
                    if (attributes_lables[i].equals("Processor"))
                        laptop.setProcessor(attributes_values[i]);
                    if (attributes_lables[i].equals("Memory"))
                        laptop.setMemory(attributes_values[i]);
                    if (attributes_lables[i].equals("Graphics"))
                        laptop.setGpu(attributes_values[i]);
                    if (attributes_lables[i].equals("Hard Drive"))
                        laptop.setStorage(attributes_values[i]);
                    if (attributes_lables[i].equals("Display Type")) {
                        laptop.setScreen_size(attributes_values[i]);
                        if(attributes_values[i].toLowerCase().contains("multi-touch"))
                            laptop.setTouch_screen(true);
                        else
                            laptop.setTouch_screen(false);
                    }
                    if (attributes_lables[i].equals("Battery"))
                        laptop.setBattery(attributes_values[i]);
                }
                if(laptop.NotAllAttributeisFilled())
                {
                    ParseLaptop(url,laptop);
                }

                laptop.printLaptop();
                LaptopArray.add(laptop);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void ParseLaptop(String url, Laptop laptop) {
        try {
            final Document LenovoLeptopUrlDocument = Jsoup.connect(url).get();
            //final String LaptopName = LenovoLeptopUrlDocument.select("h1.desktopHeader").text();
            final Elements table = LenovoLeptopUrlDocument.select(".techSpecs-table");
            for (Element line : table.select("tr")) {
                String attribute_lable = (line.select("td:nth-child(1)").text());
                attribute_lable = attribute_lable.split("\\?")[0].trim();
                Elements attribute_value = line.select("td:nth-child(2)");
                if (line.select("td:nth-child(2)").select("li").size() != 0) {
                    for (Element li : line.select("td:nth-child(2)").select("li")) {
                        //System.out.println(li);
                        parseLenovoElement(laptop,attribute_value,attribute_lable);
                    }
                } else {
                    parseLenovoElements(laptop,attribute_value,attribute_lable);
                }

            }

            /*if (!LaptopName.equals("")) {
                laptop = new Laptop(0, LaptopName, url, "Lenovo", processor, memory, operation_system, gpu, storage, screen_size, weight, battery, touch_screen);
                LaptopArray.add(laptop);
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void parseLenovoElements(Laptop laptop, Elements elememt, String attribute_lable) {
        if (attribute_lable.equals("Processor") && laptop.getProcessor()==null)
            laptop.setProcessor(elememt.text());
        if (attribute_lable.equals("Memory") && laptop.getMemory()==null)
            laptop.setMemory(elememt.text());
        if (attribute_lable.equals("Operating System") && laptop.getOperation_system()==null)
            laptop.setOperation_system(elememt.text());
        if (attribute_lable.equals("Graphics") && laptop.getGpu()==null)
            laptop.setGpu(elememt.text());
        if (attribute_lable.equals("Storage") && laptop.getStorage()==null)
            laptop.setStorage(elememt.text());
        if (attribute_lable.equals("Display") && laptop.getScreen_size()==null)
            laptop.setScreen_size(elememt.text());
        if (attribute_lable.equals("Weight") && laptop.getWeight()==null)
            laptop.setWeight(elememt.text());
        if (attribute_lable.equals("Battery") && laptop.getBattery()==null)
            laptop.setBattery(elememt.text());
        if (attribute_lable.equals("Touch Screen") && laptop.getTouch_screen()==null) {
            if (elememt.text().contains("multi-touch"))
                laptop.setTouch_screen(true);
            else
                laptop.setTouch_screen(false);
        }
    }

    public static void parseLenovoElement(Laptop laptop, Elements elememt, String attribute_lable)
    {
        if (attribute_lable.equals("Processor") && laptop.getProcessor()==null)
            laptop.setProcessor(elememt.text());
        if (attribute_lable.equals("Memory") && laptop.getMemory()==null)
            laptop.setMemory(elememt.text());
        if (attribute_lable.equals("Operating System") && laptop.getOperation_system()==null)
            laptop.setOperation_system(elememt.text());
        if (attribute_lable.equals("Graphics") && laptop.getGpu()==null)
            laptop.setGpu(elememt.text());
        if (attribute_lable.equals("Storage") && laptop.getStorage()==null)
            laptop.setStorage(elememt.text());
        if (attribute_lable.equals("Display") && laptop.getScreen_size()==null)
            laptop.setScreen_size(elememt.text());
        if (attribute_lable.equals("Weight") && laptop.getWeight()==null)
            laptop.setWeight(elememt.text());
        if (attribute_lable.equals("Battery") && laptop.getBattery()==null)
            laptop.setBattery(elememt.text());
        if (attribute_lable.equals("Touch Screen") && laptop.getTouch_screen()==null) {
            if (elememt.text().contains("multi-touch"))
                laptop.setTouch_screen(true);
            else
                laptop.setTouch_screen(false);
        }
    }
}
