package com.company;

public class Laptop {

    // Members.
    private int id_prod;
    private String model_name;
    private String url_model;
    private String company_name;
    private String processor;
    private String memory;
    private String operation_system;
    private String gpu;
    private String storage;
    private String screen_size;
    private String weight;
    private String battery;
    private Boolean touch_screen;
    private String price;
    private String img_url;
    private String description;


    // Empty CTOR
    public Laptop()
    {

    }

    // C'tor
    public Laptop(int i_id_prod, String i_model_name, String i_url_model, String i_company_name,
                  String i_processor, String i_memory, String i_operation_system, String i_gpu,
                  String i_storage, String i_screen_size, String i_weight, String i_battery,
                  Boolean i_touch_screen,String i_price, String i_ImgURL, String i_Description) {
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

    public String getProcessor() {
        return processor;
    }

    public String getMemory() {
        return memory;
    }

    public String getOperation_system() {
        return operation_system;
    }

    public String getGpu() {
        return gpu;
    }

    public String getStorage() {
        return storage;
    }

    public String getScreen_size() {
        return screen_size;
    }

    public String getWeight() {
        return weight;
    }

    public String getBattery() {
        return battery;
    }

    public Boolean getTouch_screen() {
        return touch_screen;
    }

    public String getPrice() {
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

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public void setOperation_system(String operation_system) {
        this.operation_system = operation_system;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setScreen_size(String screen_size) {
        this.screen_size = screen_size;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public void setTouch_screen(Boolean touch_screen) {
        this.touch_screen = touch_screen;
    }

    public void setPrice(String price) {
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
        System.out.println("\n\n\nId Prod: " + id_prod
                + "\nUrl Model: " + url_model
                + "\nModel Name: " + model_name
                + "\nCompany Name: " + company_name
                + "\nProcessor: " + processor
                + "\nMemory: " + memory
                + "\nOperation System: " + operation_system
                + "\nGraphics: " + gpu
                + "\nStorage: " + storage
                + "\nScreen Size: " + screen_size
                + "\nWeight: " + weight
                + "\nBattery: " + battery
                + "\nImage URL: " + img_url
                + "\nDescription: " + description);

        if (touch_screen == true)
            System.out.println("Touch Screen: Yes");
        else
            System.out.println("Touch Screen: No");
        System.out.println("Price: " + price);


    }

    public void printModels() {
        System.out.println(model_name);
    }

    public Boolean NotAllAttributeisFilled()
    {
        if(this.getProcessor()==null | this.getMemory()==null | this.getGpu()==null | this.getStorage()==null
                | this.getScreen_size()==null | this.getWeight()==null | this.getBattery()==null | this.getTouch_screen()==null | this.getOperation_system()==null)
            return true;
        else
            return false;
    }
}
