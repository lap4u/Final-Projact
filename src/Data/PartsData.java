package Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class PartsData {

    public static List<Struct> Parse_Data(String url) {
        String name;
        int place;
        List<Struct> Parts_List = new ArrayList<Struct>();
        HashMap<String,Integer> Parts_Map = new HashMap<String, Integer>();

        try {
            final Document document = Jsoup.connect(url).get();
            Elements Parts_LINES = document.select("center > .content > center > table > tbody > tr");
            for (Element line : Parts_LINES) {
                name = line.select("td:nth-of-type(1)").text();

                place = Integer.parseInt(line.select("td:nth-of-type(3)").text());
                Parts_Map.put(name,place);
                Struct Part_Line = new Struct(name, place);
                Parts_List.add(Part_Line);
            }
            Map<String,Integer> sortedMap = sortByValueDesc(Parts_Map);
            //Collections.sort(Parts_List);
        } catch (Exception ex) {
            System.out.println(url);
            ex.printStackTrace();
        }
        return Parts_List;
    }

    public static Map<String, Integer> sortByValueDesc(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
