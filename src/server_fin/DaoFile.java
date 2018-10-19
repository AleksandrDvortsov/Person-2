package server_fin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class DaoFile implements DAOcrud {

	static String allFileDirectory = "C:\\xampp\\htdocs\\bd\\";

	@Override
	public void create(String name, String pass) {
		// TODO Auto-generated method stub

	}

	@Override
	public String read(String name, boolean file) {

		File f = new File(allFileDirectory + name);
		try {
			byte[] bytes = Files.readAllBytes(f.toPath());
			return new String(bytes, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Error";
	}

	@Override
	public void updata(String name, String newSecrets) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dellete(String name) {
		// TODO Auto-generated method stub

	}
}
