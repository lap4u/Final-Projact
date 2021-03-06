package Softwares;
import Parts.OS;
import Parts.PartStruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class FindGames {

    public FindGames() {
    }

    public static void FindGames(ArrayList<SoftwareGame> i_GamesArray) {

        String main_url[] = {"https://www.game-debate.com/games/index.php?year=2016",
                "https://www.game-debate.com/games/index.php?year=2017",
                "https://www.game-debate.com/games/index.php?year=2018",
                "https://www.game-debate.com/games"
        };

        SoftwareGame newGame = null;

        try {

            for (int i = 0; i < main_url.length; i++) {
                System.out.println("Start Part: " + (i+1) + "/" + main_url.length);
                final Document document = Jsoup.connect(main_url[i]).get();
                Element div = document.select("div.darkBox2013").first();
                Elements games = div.select("div.srRowFull");
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
                           newGame = saveGame(nameGame, url);
                           if(newGame!=null)
                            i_GamesArray.add(newGame);

                        }
                    }
                }
                System.out.println("Finish Part: " + (i+1) + "/" + main_url.length);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private static SoftwareGame saveGame(String i_NameGame, String i_Url) {

        SoftwareGame theGame = null;

        try {
            final Document documentSite = Jsoup.connect(i_Url).get();
            String Graphic_Card;

            Elements ReqMin = documentSite.select("div.devDefSysReqMinWrapper.new-box-style.boxes-enable-opacity");
            Elements ReqRec = documentSite.select("div.devDefSysReqRecWrapper.new-box-style.boxes-enable-opacity");
            PartStruct gpu = null;
            PartStruct cpu = null;

            if ((ReqMin.size() != 0 || ReqRec.size() != 0) && isNotExclude(i_Url)) {
                 cpu = getCPU(ReqMin,ReqRec);
                if(cpu!=null)
                {
                    gpu = getGPU(ReqMin,ReqRec);
                    if(gpu !=null)
                    {
                        String Description = getDesc(documentSite);
                        String imgURL = documentSite.select("img.game-boxart-image").first().attr("src");
                        boolean isGame = true;
                        int memory = getMemory(ReqMin, ReqRec);
                        double hardDrive = getHardDrive(ReqMin, ReqRec);
                        OS Os = getOs(ReqMin, ReqRec);
                        theGame = new SoftwareGame(i_NameGame, Description, imgURL, isGame, memory, hardDrive, Os, cpu, gpu); // EDIT C'TOR
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return theGame;

    }

    private static PartStruct getGPU(Elements i_ReqMin, Elements i_ReqRec) {
        PartStruct theGPU = null;
        String gpuModel = null, gpuManu = null, gpuNumber;
        String gpuString = i_ReqRec.select("li:contains(GT)").text();
        if(gpuString.equals(""))
            gpuString = i_ReqMin.select("li:contains(GT)").text();

        String[] splitGpu = gpuString.split(" ");

        if(gpuString.equals("") == false) {
            for (int i = 0; i < splitGpu.length; i++) {
                if ((splitGpu[i].equals("GTX") || splitGpu[i].equals("GT") || splitGpu[i].equals("GTS")) && (i + 1) < splitGpu.length) {
                        gpuNumber = splitGpu[i + 1].replaceAll("Radeon", "").replaceAll("-class", "").replaceAll(",", "").replaceAll("/", "").replaceAll("AMD", "").replaceAll("VGA", "").replaceAll("ti", "").replaceAll("9701060", "970").replaceAll("9701660", "970").replaceAll("\\+", "").replaceAll("\\)", "").replaceAll("\\(2GB", "").replaceAll(";", "");
                        if (isNumeric(gpuNumber)) {
                            gpuModel = "GeForce " + splitGpu[i].replaceAll("\\(", "") + " " + gpuNumber;

                            gpuModel = gpuModel.replaceAll("GTX 790", "GTX 780").replaceAll("GT 960", "GTX 960").replaceAll("GT 660", "GTX 660"); // BECAUSE IT MISTAKE GT 660 . IT GTX 660

                            if (gpuString.contains("Ti") && gpuModel.equals("GeForce GTX 670") == false && gpuModel.equals("GeForce GTX 470") == false && gpuModel.equals("GeForce GTX 970") == false)
                                gpuModel = gpuModel + " Ti";

                            gpuManu = "Nvidia";
                            theGPU = new PartStruct(gpuManu, gpuModel);
                            break;
                        }
                }
            }

        }

        return theGPU;
    }

    private static PartStruct getCPU(Elements i_ReqMin, Elements i_ReqRec) {

        String cpu_Manu = "Intel";
        String cpu_Model = null;
        PartStruct cpu = null;
        int count = 1;
        String cpuString = i_ReqRec.select("li:contains(i3)").text();
        if(cpuString.equals(""))
            cpuString =  i_ReqRec.select("li:contains(i5)").text();

        if(cpuString.equals(""))
            cpuString =  i_ReqRec.select("li:contains(i7)").text();

        if(cpuString.equals(""))
            cpuString =  i_ReqMin.select("li:contains(i3)").text();

        if(cpuString.equals(""))
            cpuString =  i_ReqMin.select("li:contains(i5)").text();

        if(cpuString.equals(""))
            cpuString =  i_ReqMin.select("li:contains(i7)").text();


        if(cpuString.equals(""))
        {
            cpu = null;
        }
        else {
            String[] splitCPU = cpuString.split(" ");

            for (int i = 0; i < splitCPU.length; i++) {
                if (splitCPU[i].contains("i3-") || splitCPU[i].contains("i5-") || splitCPU[i].contains("i7-")) {
                    if (splitCPU.equals("i3-") || splitCPU[i].equals("i5-") || splitCPU[i].equals("i7-")) {
                        if ((i + 1) < splitCPU.length)
                            cpu_Model = splitCPU[i] + splitCPU[i + 1];
                    } else {
                        cpu_Model = splitCPU[i];
                    }
                } else if (splitCPU[i].equals("i3") || splitCPU[i].equals("i5") || splitCPU[i].equals("i7"))
                {
                    if ((i + 1) < splitCPU.length)
                    {
                        if(splitCPU[i+1].length() > 2)
                        {
                            String model = new StringBuilder().append(splitCPU[i+1].charAt(0)).append(splitCPU[i+1].charAt(1)).append(splitCPU[i+1].charAt(2)).toString();
                            if(isInteger(model))
                                cpu_Model = splitCPU[i] + "-" + splitCPU[i+1];

                        }
                    }
                }
            }
        }

        if(cpu_Model!=null) {
            cpu_Model = cpu_Model.replaceAll(",", "").replaceAll("/AMD", "").replaceAll("/", "").replaceAll("\\)","").replaceAll("、～3.1GHz","").replaceAll(";","").replaceAll("（3.4GHz","").replaceAll("\\(3.30GHz","").replaceAll("CPU:","").replaceAll("--","-").replaceAll("-series","").replaceAll("@","").replaceAll("750S","750").replaceAll("k","K").replaceAll("2400s","2400S").replaceAll("\\(4","").replaceAll("3.3GHz","").replaceAll("2115C","2100").replaceAll("i5-3550K","i5-3550").replaceAll("i5-45690","i5-4590").replaceAll("i5-3770","i7-3770").replaceAll("i5-4000","i5-4430").replaceAll("i5-3000","i5-3330"); // because 3000 is 3330 (Mistake)
            cpu = new PartStruct(cpu_Manu,cpu_Model);
        }

       // System.out.println(cpuString);
        //System.out.println(cpu_Model);
        return cpu;
    }

    private static OS getOs(Elements i_ReqMin, Elements i_ReqRec) {
        String Manufacture = "Windows"; // Default Value
        String Version = "-";
        int Series = 0; // Default Value
        int BitSize = 0; // Default Value
        OS operatingSystem = null;
        String osString = i_ReqRec.select("li:contains(OS)").text();

        if(osString.equals(""))
            osString = i_ReqRec.select("li:contains(O/S:)").text();

        if(osString.equals(""))
            osString = i_ReqRec.select("li:contains(Windows)").text();

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

        if(Series == 0 && Version.equals("-"))
            Series = 7; //If not found OS so we set 7 Because 7 is default.

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
                        storageNum = Double.parseDouble(splitStorage[i - 1].replaceAll("Drive:",""));

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
            if (memories.eq(i).text().contains("Video") || memories.eq(i).text().contains("video") || memories.eq(i).text().contains("GPU") || memories.eq(i).text().contains("Graphics") || memories.eq(i).text().contains("VRAM"))
                continue;
            else {
                memoryString = memories.eq(i).text();
            }
        }


        if (memoryString.equals("") == false) {
            memoryString = memoryString.replaceAll("System Memory: ", "").replaceAll("Memory: ", "").replaceAll("System RAM: ","").replaceAll("SYSTEM RAM: ", "").replaceAll("RAM: from ","").replaceAll("RAM: ", "").replaceAll("Memory - ", "").replaceAll("RAM : ", "").replaceAll("RAM ", "").replaceAll("– ", "").replaceAll("• ", "").replaceAll("- ", "").replaceAll("Memory:", "").replaceAll("MEMORY: ","").replaceAll("\\+","");
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

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isNotExclude(String i_Url) {
        String[] urlsArray = {"https://www.game-debate.com/games/index.php?g_id=35627&game=Lords Of The Fallen 2",
                "https://www.game-debate.com/games/index.php?g_id=23932&game=Payday 3",
                "https://www.game-debate.com/games/index.php?g_id=35828&game=The Elder Scrolls Online: Elsweyr",
                "https://www.game-debate.com/games/index.php?g_id=23901&game=Bless",
                "https://www.game-debate.com/games/index.php?g_id=20894&game=Far Cry: Primal",
                "https://www.game-debate.com/games/index.php?g_id=35293&game=DRAGON BALL FighterZ",
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

