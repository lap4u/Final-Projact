package Softwares;

import Parts.OS;
import Parts.PartStruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FindGames {

    public FindGames() {
    }

    public static void FindGames() {

        String main_url[] = {"https://www.game-debate.com/games/index.php?year=2018"
                ,"https://www.game-debate.com/games",
        };

        try {

            for (int i = 0; i < main_url.length; i++) {
                final Document document = Jsoup.connect(main_url[i]).get();
                Element div = document.select("div.darkBox2013").first();
                Elements games = div.select("div.srRowFull");
                System.out.println("Total Games: " + games.size());
                for (Element comp : games) {
                    Elements checkIfPS3 = comp.select("div.srTitleFull.ps3Link");
                    Elements checkIfXBOX = comp.select("div.srTitleFull.xbox360Link");
                    if (checkIfPS3.size() == 0 && checkIfXBOX.size() == 0) {
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

            Elements ReqMin = documentSite.select("div.devDefSysReqMinWrapper.new-box-style.boxes-enable-opacity");
            Elements ReqRec = documentSite.select("div.devDefSysReqRecWrapper.new-box-style.boxes-enable-opacity");

            if ((ReqMin.size() != 0 || ReqRec.size() != 0) && isNotExclude(i_Url)) {
                String Description = getDesc(documentSite);
                String imgURL = documentSite.select("img.game-boxart-image").first().attr("src");
                boolean isGame = true;
                System.out.println(i_Url);
                int memory = getMemory(ReqMin, ReqRec);
                double hardDrive = getHardDrive(ReqMin, ReqRec);
                OS Os = getOs(ReqMin, ReqRec);
                PartStruct cpu = getCPU(ReqMin,ReqRec);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static PartStruct getCPU(Elements i_ReqMin, Elements i_ReqRec) {
        String cpuString = i_ReqRec.select("li:contains(Processor:)").text();
        if(cpuString.equals(""))
            cpuString =  i_ReqRec.select("li:contains(CPU: )").text();

        if(cpuString.equals(""))
            cpuString =  i_ReqRec.select("li:contains(CPU : )").text();

        if(cpuString.equals(""))
            cpuString =  i_ReqRec.select("li:contains(Processor (Intel): )").text();

        if(cpuString.equals(""))
            cpuString =  i_ReqMin.select("li:contains(Processor: )").text();

        if(cpuString.equals(""))
            System.out.println("~~~~~~~!~~~~~ERROR~~~~~!~~~~~~~");

        System.out.println(cpuString);
        return null;
    }

    private static OS getOs(Elements i_ReqMin, Elements i_ReqRec) {
        String Manufacture = "Windows"; // Default Value
        String Version = "-";
        int Series = 0; // Default Value
        int BitSize = 0; // Default Value
        OS operatingSystem = null;
        String osString = i_ReqRec.select("li:contains(OS)").text();
        if (osString.equals(""))
            osString = i_ReqMin.select("li:contains(OS)").text();

        if (osString.equals(""))
            osString = i_ReqMin.select("li:contains(System:)").text();

        if (osString.equals("") == false) {

            if(osString.contains("64"))
                BitSize = 64;
            else
            if(osString.contains("32"))
                BitSize = 32;


            if(osString.contains("XP") || osString.contains("Xp")) {
                Version = "XP";
                Series = 0;
            }
            else
                if(osString.contains("Vista"))
                {
                    Version = "Vista";
                    Series = 0;
                }
            else
                if(osString.contains("7"))
                    Series = 7;
                else
                    if(osString.contains("8") || osString.contains("8.1"))
                        Series = 8;
                    else
                        if(osString.contains("10"))
                            Series = 10;

        }

        operatingSystem = new OS(Manufacture,Version,Series,BitSize);
        return operatingSystem;
    }


    private static double getHardDrive(Elements i_ReqMin, Elements i_ReqRec) {
        double storageNum = 0;
        String storageString = i_ReqRec.select("li:contains(Storage:)").text();
        if (storageString.equals(""))
            storageString = i_ReqRec.select("li:contains(Hard-drive space:)").text();

        if (storageString.equals(""))
            storageString = i_ReqRec.select("li:contains(HARD DRIVE:)").text();

        if (storageString.equals(""))
            storageString = i_ReqRec.select("li:contains(Hard Drive:)").text();

        if (storageString.equals(""))
            storageString = i_ReqMin.select("li:contains(Storage)").text();

        if (storageString.equals(""))
            storageString = i_ReqMin.select("li:contains(harddrive)").text();

        if (storageString.equals(""))
            storageString = i_ReqRec.select("li:contains(Hard-Disk / SSD:)").text();

        if (storageString.equals(""))
            storageString = i_ReqRec.select("li:contains(Hard Disk Space:)").text();

        if (storageString.equals(""))
            storageString = i_ReqRec.select("li:contains(HDD space)").text();


        if (storageString.equals(""))
            storageNum = 0;
        else {
            String[] splitStorage = storageString.split(" ");
            for (int i = 0; i < splitStorage.length; i++) {
                if (splitStorage[i].contains("GB") || splitStorage[i].contains("Gb")) {
                    String takeGB = splitStorage[i].replaceAll("GB", "").replaceAll("Gb", "").replaceAll("Drive:", "").replaceAll(";","");

                    if (isNumeric(takeGB))
                        storageNum = Double.parseDouble(takeGB);
                    else
                        storageNum = Double.parseDouble(splitStorage[i - 1]);

                    break;
                } else if (splitStorage[i].contains("MB")) {
                    String takeMB = splitStorage[i].replaceAll("MB", "");
                    if (isNumeric(takeMB))
                        storageNum = Double.parseDouble(takeMB);
                    else
                        storageNum = Double.parseDouble(splitStorage[i - 1]) / 1000;

                    break;
                }
            }
        }

        if (storageNum == 0)
            storageNum = 1; // Because is between 0-1000MB.

        return storageNum;
    }


    private static int getMemory(Elements i_ReqMin, Elements i_ReqRec) {
        String memoryString = "";
        String[] memorySplit = null;
        int theMemory = 0;
        Elements memories = i_ReqRec.select("li:contains(Memory:)");

        if (memories.size() == 0)
            memories = i_ReqMin.select("li:contains(Memory:)");


        if (memories.size() == 0)
            memories = i_ReqRec.select("div.devDefSysReqRecWrapper.new-box-style.boxes-enable-opacity").select("li:contains(RAM)");


        for (int i = 0; i < memories.size(); i++) {
            if (memories.eq(i).text().contains("Video") || memories.eq(i).text().contains("video") || memories.eq(i).text().contains("GPU") || memories.eq(i).text().contains("Graphics") || memories.eq(i).text().contains("VRAM:"))
                continue;
            else {
                memoryString = memories.eq(i).text();
            }
        }

        if (memoryString.equals("") == false) {
            memoryString = memoryString.replaceAll("System Memory: ", "").replaceAll("Memory: ", "").replaceAll("SYSTEM RAM: ", "").replaceAll("RAM: from ","").replaceAll("RAM: ", "").replaceAll("Memory - ", "").replaceAll("RAM : ", "").replaceAll("RAM ", "").replaceAll("– ", "").replaceAll("• ", "").replaceAll("- ", "").replaceAll("Memory:", "").replaceAll("MEMORY: ","");
            memorySplit = memoryString.split(" ", 2);
        }


        if (memoryString.contains("MB")) {
            memorySplit[0] = memorySplit[0].replaceAll("MB", "").replaceAll("Mb", "").replaceAll("-", "".replaceAll("-", ""));
            theMemory = Integer.parseInt(memorySplit[0]) / 1000;
        } else if (memoryString.equals("") == false) {
            memorySplit[0] = memorySplit[0].replaceAll("GB", "").replaceAll("Gb", "").replaceAll("-", "").replaceAll("RAM:","");
            theMemory = Integer.parseInt(memorySplit[0]);
        }


        if (theMemory == 0)
            theMemory = 1;

        //System.out.println("String Memory: " + memoryString);
        //System.out.println("Final Memory: " + theMemory);
        return theMemory;
    }

    private static String getDesc(Document i_Document) {
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


    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isNotExclude(String i_Url) {
        String[] urlsArray = {"https://www.game-debate.com/games/index.php?g_id=35627&game=Lords Of The Fallen 2",
                "https://www.game-debate.com/games/index.php?g_id=23932&game=Payday 3",
                "https://www.game-debate.com/games/index.php?g_id=36070&game=Descent (2019)",
                "https://www.game-debate.com/games/index.php?g_id=35884&game=Conan Unconquered",
                "https://www.game-debate.com/games/index.php?g_id=35828&game=The Elder Scrolls Online: Elsweyr",
                "https://www.game-debate.com/games/index.php?g_id=35781&game=Civilization VI - Gathering Storm",
                "https://www.game-debate.com/games/index.php?g_id=23901&game=Bless",
                "https://www.game-debate.com/games/index.php?g_id=24086&game=Combat core",
                "https://www.game-debate.com/games/index.php?g_id=20894&game=Far Cry: Primal",
                "https://www.game-debate.com/games/index.php?g_id=24352&game=Dauntless",
                "https://www.game-debate.com/games/index.php?g_id=36069&game=Jupiter Hell"
        };
        boolean isNotExclude = true;

        for (int i = 0; i < urlsArray.length; i++)
            if (urlsArray[i].equals(i_Url)) {
                isNotExclude = false;
                break;
            }

        return isNotExclude;
    }

}

