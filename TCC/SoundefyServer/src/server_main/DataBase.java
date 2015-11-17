package server_main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	protected BD bd;

	public DataBase(){
		try{
		bd = new BD("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://regulus:1433;databasename=BD13181",
				"BD13181", "BD13181");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public int signUp(String name, String pwd, String email, String birthDate)
			throws Exception {
		try {
			if (userExists(email, pwd)) {
				return Operations.USER_EXISTS.getCode();
			} else {
				bd.execComando("insert into SDYUser values('" + name + "','"
						+ pwd + "','" + email + "','" + birthDate + "')");
				return Operations.SIGN_UP_SUCCESS.getCode();
			}
		} catch (SQLException e) {
			return Operations.SIGN_UP_FAIL.getCode();
		}
	}

	public int signIn(String email, String pwd) throws Exception {
		if (userExists(email, pwd)) {
			return Operations.USER_EXISTS.getCode();
		} else {
			return Operations.USER_DOES_NOT_EXISTS.getCode();
		}
	}

	public boolean userExists(String email, String pwd) {
		try {
			ResultSet result = bd
					.execConsulta("select * from SDYUser where sdyuser_password='"
							+ pwd + "' and sdyuser_email='" + email + "'"); // change
																			// to
																			// stored
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
