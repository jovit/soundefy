package server_main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	protected BD bd;

	public DataBase() throws Exception {
		bd = new BD("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://SERVIDOR:1433;databasename=BD", "USUARIO",
				"SENHA");
	}

	public int signUp(String name, String pwd) throws Exception {
		try {
			if (userExists(name, pwd)) {
				return Operations.USEREXISTS.getCode();
			} else {
				bd.execComando("insert into user values('" + name + "'," + pwd
						+ "'");
				return Operations.SIGNUPSUCCESS.getCode();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return Operations.SIGNUPSUCCESS.getCode();
	}

	public boolean userExists(String name, String pwd) {
		try {
			ResultSet result = bd
					.execConsulta("select * from user where name='" + name
							+ "' and pwd='" + pwd + "'"); // change to stored
															// procedure
			if (result.first()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
