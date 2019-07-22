package Softwares;

import Parts.OS;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FindGames {

    public FindGames() {
    }

    public static void FindGames() {

        String main_url[] = {"https://www.game-debate.com/games",
                "https://www.game-debate.com/games/index.php?year=2018",
                "https://www.game-debate.com/games/index.php?year=2017",
                "https://www.game-debate.com/games/index.php?year=2016"};

        try {

            for(int i=0;i<main_url.length;i++) {
                final Document document = Jsoup.connect(main_url[i]).get();
                Element div = document.select("div.darkBox2013").first();
                Elements games = div.select("div.srRowFull");

                for (Element comp : games) {
                    comp = comp.select("a").first();
                    if (comp != null) {
                        String nameGame = comp.text();
                        String url = comp.attr("href");
                       url = url.replaceFirst(".","");
                       url = url.replaceFirst(".","");
                       url = "https://www.game-debate.com" + url;

                       System.out.println(url);
                        saveGame(nameGame, url);
                    }

                    //laptop = compSaveLG(product_url, i_ArrLaptops.size());
                    //i_ArrLaptops.add(laptop);
                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    private static void saveGame(String i_NameGame, String i_Url) {

        try {
            final Document documentSite = Jsoup.connect(i_Url).get();
            String Cpu, Graphic_Card;
            double diskSpace, Ram;
            OS Os;

            //Cpu = cutCPU(documentSite);
            //Graphic_Card= cutGraphicCard(documentSite);
           // System.out.println(Cpu);



          //  Os = cutOS(documentSite);
          //  Ram = cutRAM(documentSite);
           diskSpace = cutDiskSpace(documentSite);

            /*
            System.out.println("URL: " + url);
            System.out.println("CPU: " + Cpu);
            System.out.println("Os: " + Os);
            System.out.println("Graphic Card: " + Graphic_Card);
            System.out.println("Disc Space: " + diskSpace + "\n");
            System.out.println("RAM: " + Ram + "\n");
            */



        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private static String cutCPU(Document i_Document)
    {
        Elements CPUElement = i_Document.select("li:contains(CPU:)"); // Check
        String CPU;

        if(CPUElement.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            CPU = CPUElement.eq(1).text().replace("CPU:", "");
        }
        else
        if(CPUElement.size() == 1) // Only Minimum
        {
            CPU = CPUElement.first().text().replace("CPU:", "");
        }
        else // Not found Any CPU Requirements
        {
            CPU = "-";
        }

        return CPU;
    }


    private static double cutRAM(Document i_Document)
    {
        String RAM;
        double ramNum = 0;
        Elements RAMElements = i_Document.select("li:contains(RAM:)");

        for(int i=0;i<RAMElements.size();i++)
        {
            // We will remove all Elements that contains "VIDEO RAM" And not "RAM"
            if(RAMElements.eq(i).toString().contains("VIDEO"))
                RAMElements.remove(i);
        }

        if(RAMElements.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            RAM = RAMElements.eq(1).text().replace("RAM: ", "");
        }
        else
        if(RAMElements.size() == 1) // Only Minimum
        {
            RAM = RAMElements.first().text().replace("RAM: ", "");
        }
        else // Not found Any CPU Requirements
        {
            RAM = "0";
            ramNum = 0;
        }

        if(!RAM.equals("0"))
        {
            String[] splitRam = RAM.split(" ", 2);
            if(isNumeric(splitRam[0]))
            {
                if (RAM.contains("GB")) {
                    splitRam[0] = splitRam[0].replaceAll("GB", "");
                    ramNum = Double.parseDouble(splitRam[0]);
                } else if (RAM.contains("MB")) {
                    splitRam[0] = splitRam[0].replaceAll("MB", "");
                    ramNum = (Double.parseDouble(splitRam[0])) / 1000;
                }
            }
            else
            {
                ramNum = 0;
            }
        }

        return ramNum;
    }

    private static OS cutOS(Document i_Document)
    {
        OS operatingSystem= null;
        Elements OsElement = i_Document.select("li:contains(OS:)"); // Check
        String Os, Manufacture = "0", Version = "0";
        int  Serie = 0 ,Bit = 0;

        if(OsElement.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            Os = OsElement.eq(1).text().replace("OS: ", "");
        }
        else
        if(OsElement.size() == 1) // Only Minimum
        {
            Os = OsElement.first().text().replace("OS: ", "");
        }
        else // Not found Any CPU Requirements
        {
            Os = "0";
        }

        if(!Os.equals("0"))
        {
            if(Os.contains("Windows"))
                Manufacture = "Windows";

            if(Os.contains("64"))
                Bit = 64;
            else
                if(Os.contains("32"))
                    Bit = 32;

                if(Os.contains("XP"))
                    Version = "XP";
                else
                    if(Os.contains("Vista"))
                        Version = "Vista";
                else
                    if(Os.contains("7"))
                        Serie = 7;
                    else
                        if(Os.contains("8"))
                            Serie = 8;
                        else
                            if(Os.contains("8.1"))
                                Serie = 8; // Because Windows 8 is Windows 8.1 (In other name)
                                else
                                    if(Os.contains("10"))
                                        Serie = 10;
        }

        operatingSystem = new OS(Manufacture, Version, Serie, Bit);
        return operatingSystem;
    }



    private static String cutGraphicCard(Document i_Document)
    {
        Elements GraphicCardElement = i_Document.select("li:contains(VIDEO CARD:)"); // Check
        String GraphicCard;

        if(GraphicCardElement.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            GraphicCard = GraphicCardElement.eq(1).text().replace("VIDEO CARD:", "");
        }
        else
        if(GraphicCardElement.size() == 1) // Only Minimum
        {
            GraphicCard = GraphicCardElement.first().text().replace("VIDEO CARD:", "");
        }
        else // Not found Any CPU Requirements
        {
            GraphicCard = "-";
        }

        return GraphicCard;
    }



    private static double cutDiskSpace(Document i_Document)
    {
        Element DiskSpaceElement = i_Document.select("div.devDefSysReqRecWrapper.new-box-style.boxes-enable-opacity").first(); // Check
        String DiskSpaceString = DiskSpaceElement.select("li:contains(Storage:)").text();
        if(!DiskSpaceString.equals("")) {
            System.out.println("String: " + DiskSpaceString);
        }
        else
        {
            DiskSpaceString = DiskSpaceElement.select("li:contains(Hard Drive:)").text();
            System.out.println("HARD DRIVEE " + DiskSpaceString);
        }
       // System.out.println("Text: " + DiskSpaceElement.first().text());
       /* String Disk_Space;
        double SpaceNum = 0;

        if(DiskSpaceElement.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            Disk_Space = DiskSpaceElement.eq(1).text().replace("FREE DISK SPACE: ", "");
        }
        else
        if(DiskSpaceElement.size() == 1) // Only Minimum
        {
            Disk_Space = DiskSpaceElement.first().text().replace("FREE DISK SPACE: ", "");
        }
        else // Not found Any CPU Requirements
        {
            Disk_Space = "0";
            SpaceNum = 0;
        }

                if(!Disk_Space.equals("0")) {
            String[] splitSpace = Disk_Space.split(" ", 2);
            if (Disk_Space.contains("GB")) {
                splitSpace[0] = splitSpace[0].replaceAll("GB", "");
                SpaceNum = Double.parseDouble(splitSpace[0]);
            } else if (Disk_Space.contains("MB")) {
                splitSpace[0] = splitSpace[0].replaceAll("MB", "");
                SpaceNum = (Double.parseDouble(splitSpace[0])) / 1000;
            }
        }
        */

        //return SpaceNum;

        return 0.0;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}

