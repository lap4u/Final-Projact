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

        String main_url[] = {"https://www.game-debate.com/games"};
        //  "https://www.game-debate.com/games/index.php?year=2018",
        // "https://www.game-debate.com/games/index.php?year=2017",
        // "https://www.game-debate.com/games/index.php?year=2016"};

        try {

            for (int i = 0; i < main_url.length; i++) {
                final Document document = Jsoup.connect(main_url[i]).get();
                Element div = document.select("div.darkBox2013").first();
                Elements games = div.select("div.srRowFull");
                System.out.println("Total Games: " + games.size());
                for (Element comp : games) {
                    Elements checkIfPS3 = comp.select("div.srTitleFull.ps3Link");
                    Elements checkIfXBOX = comp.select("div.srTitleFull.xbox360Link");
                    if(checkIfPS3.size() == 0 && checkIfXBOX.size() == 0)
                    {
                    comp = comp.select("a").first();
                    if (comp != null) {
                        String nameGame = comp.text();
                        String url = comp.attr("href");
                        url = url.replaceFirst(".", "");
                        url = url.replaceFirst(".", "");
                        url = "https://www.game-debate.com" + url;
                        saveGame(nameGame, url);
                    }
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

            Elements ReqMin = documentSite.select("div.devDefSysReqMinWrapper.new-box-style.boxes-enable-opacity");
            Elements ReqRec = documentSite.select("div.devDefSysReqRecWrapper.new-box-style.boxes-enable-opacity");

            if(ReqMin.size() != 0 || ReqRec.size() != 0) {
                String Description = getDesc(documentSite);
                String imgURL = documentSite.select("img.game-boxart-image").first().attr("src");
                boolean isGame = true;

                System.out.println(i_Url);
              int memory = getMemory(ReqMin, ReqRec);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static int getMemory(Elements i_ReqMin, Elements i_ReqRec)
    {
        String memoryString ="";
        String[] memorySplit = null;
        int theMemory = 0;
        Elements memories = i_ReqRec.select("li:contains(Memory:)");

        if (memories.size() == 0)
            memories = i_ReqMin.select("li:contains(Memory:)");


        if (memories.size() == 0)
            memories = i_ReqRec.select("div.devDefSysReqRecWrapper.new-box-style.boxes-enable-opacity").select("li:contains(RAM)");


        for(int i=0;i<memories.size();i++)
        {
            if(memories.eq(i).text().contains("Video") || memories.eq(i).text().contains("video") || memories.eq(i).text().contains("GPU") || memories.eq(i).text().contains("Graphics:") || memories.eq(i).text().contains("VRAM:"))
                continue;
            else {
                memoryString = memories.eq(i).text();
            }
        }

        if(memoryString.equals("") == false)
        {
            memoryString = memoryString.replaceAll("System Memory: ", "").replaceAll("Memory: ","").replaceAll("RAM: ", "").replaceAll("– ", "").replaceAll("• ","").replaceAll("RAM : ","").replaceAll("- ", "");
            memorySplit = memoryString.split(" ", 2);
        }


        if(memoryString.contains("MB"))
        {
            memorySplit[0] = memorySplit[0].replaceAll("MB", "").replaceAll("Mb", "").replaceAll("-","".replaceAll("-",""));
            theMemory = Integer.parseInt(memorySplit[0]) / 1000;
        }
        else
            if(memoryString.equals("") == false)
        {
            memorySplit[0] = memorySplit[0].replaceAll("GB", "").replaceAll("Gb", "").replaceAll("-","");
            theMemory = Integer.parseInt(memorySplit[0]);
        }


        if(theMemory == 0)
            theMemory = 1;

        //System.out.println("String Memory: " + memoryString);
        //System.out.println("Final Memory: " + theMemory);
        return theMemory;
    }

    private static String getDesc(Document i_Document)
    {
        String finalDescription = "-";
        Elements descElement = i_Document.select("div.hardwareDescriptionNew");
        if (descElement.size() != 0) {
            String descString = descElement.first().text();
            if (descString.contains("Requirements")) {
                descString = descString.substring(descString.indexOf("Requirements"));
                String[] descSplit = descString.split(" ", 2);
                if (descSplit.length > 1)
                    finalDescription = descSplit[1];
            } else {
                finalDescription = descString;
            }
        }

        return finalDescription;
    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

