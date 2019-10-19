import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadingFromFile {

    public static String getReqJson(String path) {
        File file = new File(path);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONArray ja = new JSONArray();

        while (sc.hasNextLine()) {

            String pp = sc.nextLine();
            String[] ppps = pp.split("\\.");
            String pps1 = ppps[0];
            String pps2 = ppps[1];
            JSONObject jo = new JSONObject();
            jo.put("id", pps1);
            jo.put("text", pps2);

            ja.put(jo);

        }

        JSONObject mainObj = new JSONObject();
        mainObj.put("requirements", ja);

        return mainObj.toString();
    }

    public static void main(String[] args) throws Exception {

        File file = new File("C:\\Users\\Sergey\\IdeaProjects\\GQMify\\src\\main\\resources\\reqs.txt");
        Scanner sc = new Scanner(file);
        
        JSONArray ja = new JSONArray();

        while (sc.hasNextLine()) {

            String pp = sc.nextLine();
            String[] ppps = pp.split("\\.");
            String pps1 = ppps[0];
            String pps2 = ppps[1];
            JSONObject jo = new JSONObject();
            jo.put("id", pps1);
            jo.put("text", pps2);

            ja.put(jo);

        }

        JSONObject mainObj = new JSONObject();
        mainObj.put("requirements", ja);

        System.out.println(mainObj);

    }

    public static ArrayList<Requirement> getReqs(String path) {
        ArrayList<Requirement> requirements = new ArrayList<>();
        File file = new File(path);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()) {

            String pp = sc.nextLine();
            String[] ppps = pp.split("\\.");
            String pps1 = ppps[0];
            String pps2 = ppps[1];
            requirements.add(new Requirement(Integer.valueOf(pps1), pps2));
        }

        return requirements;
    }

}
