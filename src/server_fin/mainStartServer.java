package server_fin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.FileSystems;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class mainStartServer {

	public static void main(String[] args) throws IOException {

		HttpServer server = HttpServer.create();
		server.bind(new InetSocketAddress(8080), 0);
		HttpContext context = server.createContext("/", new EchoHandler());

		context.setAuthenticator(new myAuthenticator());
		server.setExecutor(null);
		server.start();
		FileSystems.getDefault().getSeparator();
		System.out.println("server start 8080");
	}
}
