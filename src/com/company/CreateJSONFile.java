package com.company;

import java.io.*;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateJSONFile {
    public static void writeList(List<Laptop>LaptopArray){
        JSONArray jsonLaptopArray=new JSONArray();
        for(Laptop laptop:LaptopArray)
        {
            writeToFile(laptop,jsonLaptopArray);
        }
        System.out.println(jsonLaptopArray.toJSONString());

        File file= new File("ListOfLaptops.json");
        try(PrintWriter writer = new PrintWriter(file)) {
            writer.print(jsonLaptopArray.toJSONString());
        }catch(FileNotFoundException ex){
            System.out.println(ex.toString());
        }
        System.out.println("File created successfully");
        /*try (FileWriter file = new FileWriter("C:/Users/OL/Desktop/file.json")) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
*/
    }
    public static void writeToFile(Laptop laptop,JSONArray jsonLaptopArray) {
        JSONObject obj = new JSONObject();
        obj.put("id",laptop.getId_prod());
        obj.put("Url",laptop.getUrl_model());
        obj.put("Model Name",laptop.getModel_name());
        obj.put("Company Name",laptop.getCompany_name());
        obj.put("Processor",laptop.getProcessor());
        obj.put("Memory",laptop.getMemory());
        obj.put("Operation System",laptop.getOperation_system());
        obj.put("Graphics",laptop.getGpu());
        obj.put("Storage",laptop.getStorage());
        obj.put("Screen Size",laptop.getScreen_size());
        obj.put("Weight",laptop.getWeight());
        obj.put("Battery",laptop.getBattery());
        obj.put("Touch Screen",laptop.getTouch_screen());
        obj.put("Price",laptop.getPrice());
        obj.put("imgURL",laptop.getImageURL());
        obj.put("Description",laptop.getDescription());

        jsonLaptopArray.add(obj);

        /**/

    }
}