package server_fin;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

public class myAuthenticator extends Authenticator {

	private String requestURL;
	private Connect data;
	private DaoUsers daoUsers;
	private User user;
	private String pp;

	@Override
	public Result authenticate(HttpExchange httpExchange) {
		if ("/forbidden".equals(httpExchange.getRequestURI().toString()))
			return new Failure(403);
		if ("/favicon.ico".equals(httpExchange.getRequestURI().toString()))
			return new Failure(403);
		else
			return new Success(new HttpPrincipal("c0nst", "realm"));
	}

	public String getUser() {

		Gson g = new Gson();
		data = g.fromJson(requestURL, Connect.class);
		if (data.admin.equals("login")) {
			if (data.action.equals("read")) {
				getUsers(data.name, false);
				user = g.fromJson(pp.toString().replaceAll("[\\[\\]]", ""), User.class);
				if (data.name.equals(user.name) && data.pass.equals(user.pass)) {
					if (user.name.equals("admin")) {
						long UID = System.currentTimeMillis();
						setCookie("UID " + user.name + "=" + UID + "; version=1; Path=/; Max-Age=" + (10800 + 10)
								+ "; HttpOnly");
						user.MaxAge = UID + 10000;
						EchoHandler.hm.put(UID, user);
						return "adminConnectOK";
					} else {
						long UID = System.currentTimeMillis();
						setCookie("UID " + user.name + "=" + UID + "; version=1; Path=/; Max-Age=" + (10800 + 10)
								+ "; HttpOnly");
						user.MaxAge = UID + 10000;
						EchoHandler.hm.put(UID, user);
						return "usersConnectOK";
					}
				} else
					return "There is no such person in the DataBase!";
			}
		}
		if (data.admin.equals("users")) {
			switch (data.action) {
			case "read":
				getUsers(data.name, false);
				user = g.fromJson(pp.toString().replaceAll("[\\[\\]]", ""), User.class);
				if (data.name.equals(user.name) && data.pass.equals(user.pass)) {
					for (Long key : EchoHandler.hm.keySet()) {
						for (User maxAge : EchoHandler.hm.values()) {
							maxAge.MaxAge = System.currentTimeMillis() + 10000;
							setCookie("UID " + user.name + "=" + key + "; version=1; Path=/; Max-Age=" + (10800+10)
									+ "; HttpOnly");
							EchoHandler.hm.put(key, maxAge);

						}
					}
					return user.secrets;
				}
			case "updata":
				getUpData(data.name, data.newSecrets);
				getUsers(data.name, false);
				user = g.fromJson(pp.toString().replaceAll("[\\[\\]]", ""), User.class);
				if (data.name.equals(user.name) && data.pass.equals(user.pass)) {
					for (Long key : EchoHandler.hm.keySet()) {
						for (User maxAge : EchoHandler.hm.values()) {
							maxAge.MaxAge = System.currentTimeMillis() + 10000;
							setCookie("UID " + user.name + "=" + key + "; version=1; Path=/; Max-Age=" + (10800 + 10)
									+ "; HttpOnly");
							EchoHandler.hm.put(key, maxAge);
						}
					}
					return user.secrets;
				}
			default:
				return "Error";
			}
		}
		if (data.admin.equals("admin")) {
			getAllUsers(null, true);
			return pp;
		} else
			return "ERROR";
	}

	public String checkUsersCookie() {
			for (User maxAge : EchoHandler.hm.values()) {
				if (System.currentTimeMillis() >  maxAge.MaxAge) {
					return "invalid";
				}
		}
		return "ok";
	}

	private static void setCookie(String cookie) {
		Headers respHeaders = EchoHandler.exchange.getResponseHeaders();
		EchoHandler.values = new ArrayList<>();
		EchoHandler.values.add(cookie);
		respHeaders.put("Set-Cookie", EchoHandler.values);
	}

	public void setUserID(String requestURL) {
		this.requestURL = requestURL;
	}

	private void newDAO() {
		daoUsers = new DaoUsers();
	}

	private void getUsers(String name, boolean isAdmin) {
		newDAO();
		pp = daoUsers.read(data.name, false);
	}

	private void getUpData(String userUpdata, String newSecrets) {
		newDAO();
		daoUsers.updata(userUpdata, newSecrets);
	}

	private void getAllUsers(String name, boolean isAdmin) {
		newDAO();
		pp = daoUsers.read(name, isAdmin);
	}

}
