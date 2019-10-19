import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    private static double unambiguity = 0;
    private static double readability = 56;
    private static double singularity = 0;
    private static double unsubjectivity = 0;
    private static double completeness = 100;

    private static ArrayList<Requirement> requirements = new ArrayList<Requirement>();
    private static SheetsQuickstart updater = new SheetsQuickstart();
    private static Readability read = new Readability();
    private static final String  REQ_PATH = "C:\\Users\\Sergey\\IdeaProjects\\GQMify\\src\\main\\resources\\reqs.txt";

    public static void main(String[] args) {

        requirements = ReadingFromFile.getReqs(REQ_PATH);

//        requirements.add(new Requirement(1, "The system may respond within 5 seconds."));
//        requirements.add(new Requirement(2, "The system may respond within 5 seconds or within 15 easy."));

        String url = "https://api.openreq.eu/prs-improving-requirements-quality/check-quality";
        String json = ReadingFromFile.getReqJson(REQ_PATH);
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .requestBody(json).validateTLSCertificates(false).post();
        } catch (IOException e) { e.printStackTrace(); }
        String response = doc.body().toString().replaceAll("<body>", "").replaceAll("</body>", "");
        System.out.println(response);

        int numberOfReq = requirements.size();

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
            requirement.setUnambiguity(getPercent(requirement.getAmbigueTerms(), countWords(requirement.getReqText())));
            System.out.println("connectivityTerms: " + requirement.getConnectiveTerms());
            requirement.setSingularity(getPercent(requirement.getConnectiveTerms(), countWords(requirement.getReqText())));
            System.out.println("SubjectivityTerms: " + requirement.getSubjectivityTerms());
            requirement.setUnsubjectivity(getPercent(requirement.getSubjectivityTerms(), countWords(requirement.getReqText())));
            System.out.println("Unsubjectivity: " + requirement.getUnsubjectivity());
            unsubjectivity = (unsubjectivity+= requirement.getUnsubjectivity());
            System.out.println("Singularity: " + requirement.getSingularity());
            singularity = (singularity+= requirement.getSingularity());
            System.out.println("Unambiguity: " + requirement.getUnambiguity());
            unambiguity = (unambiguity+= requirement.getUnambiguity());
//            requirement.setReadability(Readability.getReadability(requirement.getReqText()));
        }

//        System.out.println("Readabilyty: " + requirement.getReadability());
//        readability = Readability.getReadability(Readability.usingBufferedReader("C:\\Users\\Sergey\\IdeaProjects\\GQMify\\src\\main\\resources\\reqs.txt"));

        System.out.println();
        System.out.println("Overall unambiguity: " + unambiguity/requirements.size());
        System.out.println("Overall singularity: " + singularity/requirements.size());
        System.out.println("Overall unsubjectivity: " + unsubjectivity/requirements.size());
        System.out.println("Overall readability: " + readability);
        System.out.println("Overall completeness: " + completeness);

        try {
            SheetsQuickstart.loadFirstTable(requirements);
            SheetsQuickstart.loadParams(unambiguity/requirements.size(), singularity/requirements.size(), unsubjectivity/requirements.size(), readability, completeness);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
