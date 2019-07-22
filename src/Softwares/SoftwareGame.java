package Softwares;
import Parts.OS;
import Parts.PartStruct;

public class SoftwareGame {
    private String name;
    private OS OperatingSystems[];
    private PartStruct processor;
    private int Memory;
    private PartStruct GPU;
    private double HardDrive;
    private String imgURL;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OS[] getOperatingSystems() {
        return OperatingSystems;
    }

    public void setOperatingSystems(OS[] operatingSystems) {
        OperatingSystems = operatingSystems;
    }

    public PartStruct getProcessor() {
        return processor;
    }

    public void setProcessor(PartStruct processor) {
        this.processor = processor;
    }

    public int getMemory() {
        return Memory;
    }

    public void setMemory(int memory) {
        Memory = memory;
    }

    public PartStruct getGPU() {
        return GPU;
    }

    public void setGPU(PartStruct GPU) {
        this.GPU = GPU;
    }

    public double getHardDrive() {
        return HardDrive;
    }

    public void setHardDrive(int hardDrive) {
        HardDrive = hardDrive;
    }
}
