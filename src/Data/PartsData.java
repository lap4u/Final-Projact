package Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartsData {

    public static List<Struct> Parse_Data(String url) {
        String name;
        int place;
        List<Struct> Parts_List = new ArrayList<Struct>();

        try {
            final Document document = Jsoup.connect(url).get();
            Elements Parts_LINES = document.select("center > .content > center > table > tbody > tr");
            for (Element line : Parts_LINES) {
                name = line.select("td:nth-of-type(1)").text();
                place = Integer.parseInt(line.select("td:nth-of-type(3)").text());
                Struct Part_Line = new Struct(name, place);
                Parts_List.add(Part_Line);
            }
            Collections.sort(Parts_List);
        } catch (Exception ex) {
            System.out.println(url);
            ex.printStackTrace();
        }
        return Parts_List;
    }
}
