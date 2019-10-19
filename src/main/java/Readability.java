import com.squareup.okhttp.*;
import org.apache.http.HttpResponse;
		import org.apache.http.client.methods.HttpPost;
		import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Readability {

	private static String path = "C:\\Users\\Sergey\\IdeaProjects\\GQMify\\src\\main\\resources\\reqs.txt";

	public static double getReadability(String str) {
		String url = "http://api.plainrussian.ru/api/1.0/ru/measure/";
//		String text = usingBufferedReader("src/sample");
		String text = str;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).ignoreContentType(true).data("text", text).validateTLSCertificates(false).post();
		} catch (IOException e) {
			e.printStackTrace();
		}

		double value = Double.parseDouble(doc.toString().substring(doc.toString().indexOf("index_cl") + 11, doc.toString().indexOf("index_cl") + 15));
		System.out.println("CLI: " + value);
		return value;

	}

	public double oldReadability(ArrayList<Requirement> requirements) {
		double value = 0;
		double L = getL(path);
		double S = getS(path, requirements);
		return (20 - (0.0588 * L - 0.296 * S - 15.8))/10 * 100;
	}

	private double getS(String path, ArrayList<Requirement> requirements) {
		int numberOfSentences = usingBufferedReader(path).split("[!?.:]+").length - requirements.size();

		return 0;
	}

	private double getL(String path) {
		double numberOfWords = countWords(usingBufferedReader(path));
		int numberOfletters = getLetters(usingBufferedReader(path));
		return numberOfletters / (numberOfWords/100);
	}

	private int getLetters(String str) {
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetter(str.charAt(i)))
				counter++;
		}
		System.out.println(counter + " letters.");
		return counter;
	}

	private static int countWords(String str) {
		return str.split(" ").length;
	}

	public static void main(String[] args) {
		String url = "https://ipeirotis-readability-metrics.p.rapidapi.com/getReadabilityMetrics";
//		String text = usingBufferedReader("src/sample");
		String text = "";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("x-rapidapi-host", "ipeirotis-readability-metrics.p.rapidapi.com");
		headers.put("x-rapidapi-key", "081ec9f8d2msh86b15d1b25a25e7p1d8f59jsn435fd0e32e06");
		headers.put("content-type", "application/x-www-form-urlencoded");
		Document doc = null;
		try {
			doc = Jsoup.connect(url).ignoreContentType(true).headers(headers).data("text", usingBufferedReader(path)).validateTLSCertificates(false).post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(doc);

//		OkHttpClient client = new OkHttpClient();
//
//		Request request = new Request.Builder()
//				.url("https://ipeirotis-readability-metrics.p.rapidapi.com/getReadabilityMetrics?text=1.I%20want%20to%20download%20the%20list%20of%20all%20users%20in%20CSV%20or%20Excel%202.I%20want%20to%20build%20a%20support%20system%20with%20live%20chat%2C%20contact%20form%2C%20help%20and%20case%20management%203.The%20system%20must%20have%20good%20usability%204.The%20system%20shall%20work%20just%20like%20the%20previous%20one%2C%20but%20on%20a%20new%20platform%205.Professor%20user%20will%20log%20into%20the%20system%20by%20providing%20his%20username%2C%20password%20and%20other%20relevant%20information%206.Each%20page%20of%20the%20system%20will%20load%20in%20an%20acceptable%20time%20frame%207.The%20product%20shall%20provide%20status%20messages%20at%20regular%20intervals%20not%20less%20than%20every%2060%20seconds%208.The%20product%20shall%20switch%20between%20displaying%20and%20hiding%20non-printing%20characters%20instantaneously%209.The%20HTML%20Parser%20shall%20produce%20an%20HTML%20markup%20error%20report%20which%20allows%20quick%20resolution%20of%20errors%20when%20used%20by%20HTML%20novices%2010.Charge%20numbers%20should%20be%20validated%20on-line%20against%20the%20master%20corporate%20charge%20number%20list%2C%20if%20possible")
//				.post(null)
//				.addHeader("x-rapidapi-host", "ipeirotis-readability-metrics.p.rapidapi.com")
//				.addHeader("x-rapidapi-key", "SIGN-UP-FOR-KEY")
//				.addHeader("content-type", "application/x-www-form-urlencoded")
//				.build();
//
//		Response response = client.newCall(request).execute();

//		OkHttpClient client = new OkHttpClient();
//
//		Request request = new Request.Builder()
//				.url("https://ipeirotis-readability-metrics.p.rapidapi.com/getReadabilityMetrics?text=1.I%20want%20to%20download%20the%20list%20of%20all%20users%20in%20CSV%20or%20Excel%202.I%20want%20to%20build%20a%20support%20system%20with%20live%20chat%2C%20contact%20form%2C%20help%20and%20case%20management%203.The%20system%20must%20have%20good%20usability%204.The%20system%20shall%20work%20just%20like%20the%20previous%20one%2C%20but%20on%20a%20new%20platform%205.Professor%20user%20will%20log%20into%20the%20system%20by%20providing%20his%20username%2C%20password%20and%20other%20relevant%20information%206.Each%20page%20of%20the%20system%20will%20load%20in%20an%20acceptable%20time%20frame%207.The%20product%20shall%20provide%20status%20messages%20at%20regular%20intervals%20not%20less%20than%20every%2060%20seconds%208.The%20product%20shall%20switch%20between%20displaying%20and%20hiding%20non-printing%20characters%20instantaneously%209.The%20HTML%20Parser%20shall%20produce%20an%20HTML%20markup%20error%20report%20which%20allows%20quick%20resolution%20of%20errors%20when%20used%20by%20HTML%20novices%2010.Charge%20numbers%20should%20be%20validated%20on-line%20against%20the%20master%20corporate%20charge%20number%20list%2C%20if%20possible")
////				.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), new File(path)))
//				.post()
//				.addHeader("x-rapidapi-host", "ipeirotis-readability-metrics.p.rapidapi.com")
//				.addHeader("x-rapidapi-key", "081ec9f8d2msh86b15d1b25a25e7p1d8f59jsn435fd0e32e06")
//				.addHeader("content-type", "application/x-www-form-urlencoded")
//				.build();
//
//		try {
//			Response response = client.newCall(request).execute();
//			System.out.println(response);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static String usingBufferedReader(String filePath)
	{
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
		{

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null)
			{
				contentBuilder.append(sCurrentLine).append("\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}






}
