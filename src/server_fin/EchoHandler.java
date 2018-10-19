package server_fin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class EchoHandler implements HttpHandler {

	public static HashMap<Long, User> hm = new HashMap<>();
	static HttpExchange exchange;
	static List<String> values;

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		EchoHandler.exchange = exchange;
		StringBuilder builder = new StringBuilder();

		String url = exchange.getRequestURI().toString();
		Controller controller = new Controller();
		builder.append(controller.validation(url));

		
		
		byte[] bytes = builder.toString().getBytes();
		exchange.sendResponseHeaders(200, bytes.length);

		OutputStream os = exchange.getResponseBody();
		os.write(bytes);
		os.close();
	}
}
