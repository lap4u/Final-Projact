package Games;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FindGames {

    public FindGames() {
    }

    public static void FindGames() {

        final String main_url = "https://www.systemrequirementslab.com/all-games-list/";

        try {
            final Document document = Jsoup.connect(main_url).get();
            Element div = document.select("div#middle-slot").first();
            Elements games = div.select("li");
            System.out.println("size of List: " + games.size());


            for (Element comp : games) {
                comp = comp.select("a").first();
                if(comp!=null) {
                    String url = comp.attr("href");
                    saveGame(url);
                }

                //laptop = compSaveLG(product_url, i_ArrLaptops.size());
                //i_ArrLaptops.add(laptop);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    private static void saveGame(String url) {

        try {
            final Document documentSite = Jsoup.connect(url).get();

            String Cpu, CPU_Speed, Ram, Os, Graphic_Card, Disk_Space;

            Cpu = cutCPU(documentSite);
            CPU_Speed = cutCPUSpeed(documentSite);
            Ram = cutRAM(documentSite);

            Os = cutOS(documentSite);

            Graphic_Card= cutGraphicCard(documentSite);
            Disk_Space = cutDiskSpace(documentSite);

            System.out.println("URL: " + url);
            System.out.println("CPU: " + Cpu);
            System.out.println("CPU SPEED: " + CPU_Speed);
            System.out.println("RAM: " + Ram);
            System.out.println("Os: " + Os);
            System.out.println("Graphic Card: " + Graphic_Card);
            System.out.println("Disc Space: " + Disk_Space);

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

    private static String cutCPUSpeed(Document i_Document)
    {
        Elements CPUSpeedElement = i_Document.select("li:contains(CPU SPEED:)");
        String CPUSpeed;

        if(CPUSpeedElement.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            CPUSpeed = CPUSpeedElement.eq(1).text().replace("CPU SPEED:", "");
        }
        else
        if(CPUSpeedElement.size() == 1) // Only Minimum
        {
            CPUSpeed = CPUSpeedElement.first().text().replace("CPU SPEED:", "");
        }
        else // Not found Any CPU Requirements
        {
            CPUSpeed = "-";
        }

        return CPUSpeed;
    }

    private static String cutRAM(Document i_Document)
    {
        String RAM;
        Elements RAMElements = i_Document.select("li:contains(RAM:)");

        for(int i=0;i<RAMElements.size();i++)
        {
            // We will remove all Elements that contains "VIDEO RAM" And not "RAM"
            if(RAMElements.eq(i).toString().contains("VIDEO"))
                RAMElements.remove(i);
        }

        if(RAMElements.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            RAM = RAMElements.eq(1).text().replace("RAM:", "");
        }
        else
        if(RAMElements.size() == 1) // Only Minimum
        {
            RAM = RAMElements.first().text().replace("RAM:", "");
        }
        else // Not found Any CPU Requirements
        {
            RAM = "-";
        }

        return RAM;
    }

    private static String cutOS(Document i_Document)
    {
        Elements OsElement = i_Document.select("li:contains(OS:)"); // Check
        String Os;

        if(OsElement.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            Os = OsElement.eq(1).text().replace("OS:", "");
        }
        else
        if(OsElement.size() == 1) // Only Minimum
        {
            Os = OsElement.first().text().replace("OS:", "");
        }
        else // Not found Any CPU Requirements
        {
            Os = "-";
        }

        return Os;
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

    private static String cutDiskSpace(Document i_Document)
    {
        Elements DiskSpaceElement = i_Document.select("li:contains(FREE DISK SPACE:)"); // Check
        String Disk_Space;

        if(DiskSpaceElement.size() == 2) // If size = 2, So have Minimum And Recommend Requirement, So get the Recommend
        {
            Disk_Space = DiskSpaceElement.eq(1).text().replace("FREE DISK SPACE:", "");
        }
        else
        if(DiskSpaceElement.size() == 1) // Only Minimum
        {
            Disk_Space = DiskSpaceElement.first().text().replace("FREE DISK SPACE:", "");
        }
        else // Not found Any CPU Requirements
        {
            Disk_Space = "-";
        }

        return Disk_Space;
    }


}

