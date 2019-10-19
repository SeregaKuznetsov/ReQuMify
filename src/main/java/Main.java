import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Main {

//    private double unambiguity = 0;
//    private double readability = 0;
//    private double singularity = 0;
    private static ArrayList<Requirement> requirements = new ArrayList<Requirement>();

    public static void main(String[] args) {

        requirements.add(new Requirement(1, "The system may respond within 5 seconds."));
        requirements.add(new Requirement(2, "The system may respond within 5 seconds or within 15 easy."));

        String url = "https://api.openreq.eu/prs-improving-requirements-quality/check-quality";
        String json = "{ \"requirements\": [ { \"id\": \"1\", \"text\": \"The system may respond within 5 seconds.\" },{ \"id\": \"2\", \"text\": \"The system may respond within 5 seconds or within 15 easy.\" } ]}";
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .requestBody(json).validateTLSCertificates(false).post();
        } catch (IOException e) { e.printStackTrace(); }
        String response = doc.body().toString().replaceAll("<body>", "").replaceAll("</body>", "");
        System.out.println(response);

        int numberOfReq = 2;

        // Считываем json
        Object obj = null; // Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
        try {
            obj = new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Кастим obj в JSONObject
        JSONObject jo = (JSONObject) obj;


        for (int i = 1; i <= numberOfReq; i++) {
            JSONArray requirementsArray = (JSONArray) jo.get(String.valueOf(i));
            Iterator requirementsItr = requirementsArray.iterator();
            System.out.println("requirementsItr: " + i);
            Requirement requirement = requirements.get(i-1);
            while (requirementsItr.hasNext()) {
                JSONObject test = (JSONObject) requirementsItr.next();
//                requirement.setText((String) test.get("text"));
//                requirement.setText((String) test.get("language_construct"));
                System.out.println("- text: " + test.get("text") + ", language_construct: " + test.get("language_construct"));

                switch ((String) test.get("language_construct")) {
                    case "Subjective Language": {
                        requirement.setSubjectivityTerms(requirement.getSubjectivityTerms() + 1);
                        break;
                    }
                    case "Comparatives and Superlatives": {
                        requirement.setConnectiveTerms(requirement.getConnectiveTerms() + 1);
                        break;
                    }
                    case "Coordination": {
                        requirement.setConnectiveTerms(requirement.getConnectiveTerms() + 1);
                        break;
                    }
                    case "Nominalization": {
                        requirement.setAmbigueTerms(requirement.getAmbigueTerms() + 1);
                        break;
                    }
                    case "Vague Pronoun": {
                        requirement.setAmbigueTerms(requirement.getAmbigueTerms() + 1);
                        break;
                    }
                    case "Compound Noun": {
                        requirement.setAmbigueTerms(requirement.getAmbigueTerms() + 1);
                        break;
                    }
                    case "Negative Statement": {
                        requirement.setAmbigueTerms(requirement.getAmbigueTerms() + 1);
                        break;
                    }
                    case "Other / Misc": {
                        requirement.setAmbigueTerms(requirement.getAmbigueTerms() + 1);
                        break;
                    }
                    case "Ambiguous Adverb or Adjective": {
                        requirement.setAmbigueTerms(requirement.getAmbigueTerms() + 1);
                        break;
                    }
                }
            }
        }

        for (Requirement requirement : requirements) {
            System.out.println("ID:" + requirement.getId());
            System.out.println("AmbiguetyTerms: " + requirement.getAmbigueTerms());
            requirement.setUnambiguity(getPercent(requirement.getAmbigueTerms(), countWords(requirement.getText())));
            System.out.println("connectivityTerms: " + requirement.getConnectiveTerms());
            requirement.setSingularity(getPercent(requirement.getConnectiveTerms(), countWords(requirement.getText())));
            System.out.println("SubjectivityTerms: " + requirement.getSubjectivityTerms());
            requirement.setUnsubjectivity(getPercent(requirement.getSubjectivityTerms(), countWords(requirement.getText())));
            System.out.println("Subjectivity: " + requirement.getUnsubjectivity());
            System.out.println("Singularity: " + requirement.getSingularity());
            System.out.println("Unambiguity: " + requirement.getUnambiguity());
        }






    }

    private static int countWords(String str) {
        return str.split(" ").length;
    }

    //unsubjectivity and unambiguity
    private static double getPercent(int terms, int total) {
        return (1.0 - 1.0 * terms/total) * 100;
    }
}
