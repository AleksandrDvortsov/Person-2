package server_fin;

import com.google.gson.Gson;

public class User {
	public int id;
	public String name;
	public String pass;
	public String secrets;
	public long MaxAge;

	public User() {

	}

	public User(int id, String name, String pass, String secrets) {
		init(id, name, pass, secrets);
	}

	public void init(int id, String name, String pass, String secrets) {
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.secrets = secrets;
	}

	@Override
	public String toString() {
		Gson g = new Gson();
		return g.toJson(this);
	}
}
