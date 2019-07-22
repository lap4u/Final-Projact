package Softwares;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class ParseJsonToArray {
    public static void Parse() {
        try {
            InputStream is = new FileInputStream("C:\\Users\\OL\\IdeaProjects\\FinalProjactLap4U\\src\\Softwares\\system_requirement.json");
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
            SoftwareGame obj_Software = new SoftwareGame();
            obj_Software = obj_objectMapper.readValue(fileAsString, SoftwareGame.class);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
