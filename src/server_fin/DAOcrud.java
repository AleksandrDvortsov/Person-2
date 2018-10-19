package server_fin;

public interface DAOcrud {

	void create(String name, String pass);

	String read(String name, boolean adminOrUser);

	void updata(String name, String newSecrets);

	void dellete(String name);

}
