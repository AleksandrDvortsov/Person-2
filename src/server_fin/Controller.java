package server_fin;

import java.io.IOException;

public class Controller {

	DaoFile daoFile;
	myAuthenticator my = new myAuthenticator();

	public String validation(String url) throws IOException {

		if (url.substring(1, 4).equals("API")) {

			String json = java.net.URLDecoder.decode(url.substring(5), "UTF-8");
			my.setUserID(json);

			if (my.checkUsersCookie().equals("ok"))
				return my.getUser();
			else if (my.checkUsersCookie().equals("invalid")) {
				EchoHandler.hm.clear();
				return "invalid";
			}
		} else {
			daoFile = new DaoFile();
			return daoFile.read(url, false);
		}
		return "";
	}
}
