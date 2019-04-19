package Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CreateJSONFile_Parts {
    public static void WriteToJsonFile(List<Struct> PartArray, String file_name)
    {

        JSONArray jsonPartArray=new JSONArray();
        for(Struct part : PartArray)
        {
            writeToFile(part,jsonPartArray);
        }
        System.out.println(jsonPartArray.toJSONString());

        File file= new File(file_name);
        try(PrintWriter writer = new PrintWriter(file)) {
            writer.print(jsonPartArray.toJSONString());
        }catch(FileNotFoundException ex){
            System.out.println(ex.toString());
        }
        System.out.println("File created successfully");
    }
    public static void writeToFile(Struct part, JSONArray jsonPartArray) {
        JSONObject obj = new JSONObject();
        obj.put("name",part.getName());
        obj.put("position",part.getPlace());

        jsonPartArray.add(obj);

    }
}
