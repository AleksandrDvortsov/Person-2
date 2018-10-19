package server_fin;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoUsers implements DAOcrud {

	ArrayList<User> user;
	ArrayList<User> users;

	@Override
	public void create(String name, String pass) {
		// TODO Auto-generated method stub

	}

	@Override
	public String read(String name, boolean admin) {
		if (!admin) {
			user = new ArrayList<User>();
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw_bd_24.07", "root", "");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM person WHERE name=" + "'" + name + "'");
				while (rs.next()) {
					user.add(new User(rs.getInt(1), rs.getString("name"), rs.getString("pass"),
							rs.getString("secrets")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// System.out.println(user.toString());
			return user.toString();
		} else {
			users = new ArrayList<User>();
			String query = "SELECT * FROM person";
			Connection con;
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw_bd_24.07", "root", "");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					users.add(new User(rs.getInt(1), rs.getString("name"), rs.getString("pass"),
							rs.getString("secrets")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(users.toString());
			return users.toString();
		}
	}

	@Override
	public void updata(String name, String newSecrets) {
		read(name, false);
		// System.out.println(users.toString().substring(7,8));
		int id = Integer.parseInt(user.toString().substring(7, 8));
		String str = "UPDATE person SET secrets=" + "\"" + newSecrets + "\"" + " WHERE id=" + id + ";";

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw_bd_24.07", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate(str);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dellete(String name) {
		// TODO Auto-generated method stub

	}
}
