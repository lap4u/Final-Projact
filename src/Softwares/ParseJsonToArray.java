package Softwares;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class ParseJsonToArray {
    public static SoftwareGame[] Parse() {
        try {
            InputStream is = new FileInputStream("C:\\Users\\rotem\\IdeaProjects\\Final-Projact2\\src\\Softwares\\\u200F\u200Fne.json");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            String fileAsString = sb.toString();
            System.out.println(fileAsString);

            ObjectMapper obj_objectMapper = new ObjectMapper();
            SoftwareGame[] obj_Software = new SoftwareGame[]{};
            obj_Software = obj_objectMapper.readValue(fileAsString, SoftwareGame[].class);

            return obj_Software;

        }
        catch (Exception ex){
            ex.printStackTrace();

            SoftwareGame[] obj_Software = new SoftwareGame[]{};
            return obj_Software;
        }
    }
}
