package Softwares;

import Parts.OS;
import Parts.PartStruct;

public class SoftwareGame {
    private String name;
    private String description;
    private String imgURL;
    private boolean isGame;
    private int memory;
    private double hardDrive;
    private OS operatingSystem;
    private PartStruct processor;
    private PartStruct gpu;

    public SoftwareGame()
    {

    }

    public SoftwareGame(String i_Name, String i_Desc, String i_imgUrl, boolean i_IsGame, int i_Memory, double i_HardDrive, OS i_Os, PartStruct i_Processor, PartStruct i_Gpu) {
      name = i_Name;
      description = i_Desc;
      imgURL = i_imgUrl;
      isGame = i_IsGame;
      memory = i_Memory;
      hardDrive = i_HardDrive;
      operatingSystem = i_Os;
      processor = i_Processor;
      gpu = i_Gpu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGame() {
        return isGame;
    }

    public void setGame(boolean game) {
        isGame = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OS getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OS operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public PartStruct getProcessor() {
        return processor;
    }

    public void setProcessor(PartStruct processor) {
        this.processor = processor;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public PartStruct getGpu() {
        return gpu;
    }

    public void setGpu(PartStruct gpu) {
        this.gpu = gpu;
    }

    public double getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(double hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void printGame()
    {
        try {
            System.out.println("\n\nName Game: " + name
                    + "\nImg Url: " + imgURL
                    + "\nProcessor: " + processor.getManufacture() + " ~ " + processor.getModel()
                    + "\nMemory: " + memory
                    + "\nStorage: " + hardDrive + "GB"
                    + "\nis Game? " + isGame
                    + "\nOperation System: " +operatingSystem.getManufacture() + " ~ " + operatingSystem.getVersion() + " ~ " + operatingSystem.getSeries() + " ~ " + operatingSystem.getBitSize()
                    + "\nGraphics: " + gpu.getManufacture() + " ~ " + gpu.getModel()
                    + "\nDescription: " + description);

        }  catch (Exception ex) {
            System.out.println("ERROR: " + name);
            ex.printStackTrace();
        }
    }

}
