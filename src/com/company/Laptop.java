package com.company;
import Parts.OS;
import Parts.PartStruct;
import Parts.Storage;

public class Laptop {

    // Members.
    private int id_prod;
    private String model_name;
    private String url_model;
    private String company_name;
    private PartStruct processor;
    private int memory;
    private OS operation_system;
    private PartStruct gpu;
    private Storage storage;
    private double screen_size;
    private double weight;
    private String battery;
    private Boolean touch_screen;
    private double price;
    private String img_url;
    private String description;


    // Empty CTOR
    public Laptop() {

    }

//   FOR (ELAD) 24/04/2019 19:31  DELL UPDATES
   /* Last:C'Tor. public Laptop(int i_id_prod, String i_model_name, String i_url_model, String i_company_name,
                  String i_processor, String i_memory, String i_operation_system, String i_gpu,
                  String i_storage, String i_screen_size, String i_weight, String i_battery,
                  Boolean i_touch_screen,String i_price, String i_ImgURL, String i_Description) {*/


    // C'tor
    public Laptop(int i_id_prod, String i_model_name, String i_url_model, String i_company_name,
                  PartStruct i_processor, int i_memory, OS i_operation_system, PartStruct i_gpu,
                  Storage i_storage, double i_screen_size, double i_weight, String i_battery,
                  Boolean i_touch_screen, double i_price, String i_ImgURL, String i_Description) {
        id_prod = i_id_prod;
        model_name = i_model_name;
        url_model = i_url_model;
        company_name = i_company_name;
        processor = i_processor;
        memory = i_memory;
        operation_system = i_operation_system;
        gpu = i_gpu;
        storage = i_storage;
        screen_size = i_screen_size;
        weight = i_weight;
        battery = i_battery;
        touch_screen = i_touch_screen;
        price = i_price;
        description = i_Description;
        img_url = i_ImgURL;
    }

    // Getters

    public int getId_prod() {
        return id_prod;
    }

    public String getModel_name() {
        return model_name;
    }

    public String getUrl_model() {
        return url_model;
    }

    public String getCompany_name() {
        return company_name;
    }

    public int getMemory() {
        return memory;
    }

    public PartStruct getProcessor() {
        return processor;
    }

    public OS getOperation_system() {
        return operation_system;
    }

    public PartStruct getGpu() {
        return gpu;
    }

    public Storage getStorage() {
        return storage;
    }

    public double getScreen_size() {
        return screen_size;
    }

    public double getWeight() {
        return weight;
    }

    public String getBattery() {
        return battery;
    }

    public Boolean getTouch_screen() {
        return touch_screen;
    }

    public double getPrice() {
        return price;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getDescription() {
        return description;
    }


    // Setters

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public void setUrl_model(String url_model) {
        this.url_model = url_model;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setProcessor(PartStruct i_processor) {
        this.processor = i_processor;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setOperation_system(OS operation_system) {
        this.operation_system = operation_system;
    }

    public void setGpu(PartStruct gpu) {
        this.gpu = gpu;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setScreen_size(double screen_size) {
        this.screen_size = screen_size;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public void setTouch_screen(Boolean touch_screen) {
        this.touch_screen = touch_screen;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // Methods.

    public void printLaptop() {
        try {
            System.out.println("\n\n\nId Prod: " + id_prod
                    + "\nUrl Model: " + url_model
                    + "\nModel Name: " + model_name
                    + "\nCompany Name: " + company_name
                    + "\nProcessor: " + processor.getManufacture() + " ~ " + processor.getModel()
                    + "\nMemory: " + memory
                    + "\nOperation System: " + operation_system.getManufacture() + " " + operation_system.getSeries() + " "
                    + operation_system.getVersion() + " " + operation_system.getBitSize()
                    + "\nGraphics: " + gpu.getManufacture() + " ~ " + gpu.getModel()
                    + "\nScreen Size: " + screen_size
                    + "\nWeight: " + weight
                    + "\nBattery: " + battery
                    + "\nImage URL: " + img_url
                    + "\nDescription: " + description);
            System.out.println("Storage: " + storage.getM_GB() + "GB");
            if(storage.getM_SSD())
                System.out.println("SSD: Yes");
            else
                System.out.println("SSD: NO");

            if (touch_screen == true)
                System.out.println("Touch Screen: Yes");
            else
                System.out.println("Touch Screen: No");
            System.out.println("Price: " + price);
        }  catch (Exception ex) {
        System.out.println("ERROR: " + url_model);
        ex.printStackTrace();
    }



    }


    public Boolean NotAllAttributeisFilled() {
        if (this.getProcessor() == null | this.getMemory() == 0 | this.getGpu() == null | this.getStorage() == null
                | this.getScreen_size() == 0 | this.getWeight() == 0 | this.getTouch_screen() == null | this.getOperation_system() == null)
            return true;
        else
            return false;
    }


}



