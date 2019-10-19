public class Readability {

	HttpResponse response = new DefaultHttpClient().execute(post);
	post = new HttpPost("http://api.plainrussian.ru/api/1.0/ru/measure/?text=");

    post.setEntity(new FileEntity(new File("/home/evgeny/Documents/ReQuMify/sample"), "text/plain; charset=\"UTF-8\""));
	HttpResponse response = new DefaultHttpClient().execute(post);

	System.out.println(response);
}
