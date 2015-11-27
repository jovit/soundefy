package server_main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	protected BD bd;

	public DataBase() {
		try {
			bd = new BD("com.microsoft.sqlserver.jdbc.SQLServerDriver",
					"jdbc:sqlserver://regulus:1433;databasename=BD13181",
					"BD13181", "BD13181");
		} catch (Exception e) {
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

	private int getGenreID(String genre) {
		try {
			ResultSet result = bd
					.execConsulta("select * from SDYMusicGenre where sdymusicgenre_name='"
							+ genre + "'"); // change
											// to
											// stored
			// procedure
			if (result.first()) {
				int id = result.getInt(0);
				result.close();
				return id;
			} else {
				result.close();
				return -1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}
	
	private int getArtistID(String artistName) {
		try {
			ResultSet result = bd
					.execConsulta("select * from SDYMusicArtist where sdymusicartist_name='"
							+ artistName + "'"); // change
											// to
											// stored
			// procedure
			if (result.first()) {
				int id = result.getInt(0);
				result.close();
				return id;
			} else {
				result.close();
				return -1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}

	public int upload(String artistName, String songYear, String songName,
			String songGenre, String file) throws Exception {
		try {
			bd.execComando("insert into SDYMusicGenre values('" + songGenre
					+ "')");
			bd.execComando("insert into SDYMusicArtist values('" + artistName
					+ "')");
			int artistID = getArtistID(artistName);
			int genreID = getGenreID(songGenre);
			bd.execComando("insert into SDYSong values(''" + songName + "',"
					+ songYear + "," + artistID + "," + genreID + ")");
			return Operations.UPLOAD_SUCCESS.getCode();
		} catch (SQLException e) {
			return Operations.UPLOAD_FAILED.getCode();
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
				result.close();
				return true;
			} else {
				result.close();
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public String getTabs(){
		String tabs = "";
		try {
			ResultSet resultTab = bd
					.execConsulta("select SDYMusicTab.sdymusictab_ID, SDYSong.sdysong_name from SDYMusicTab, SDYSong where "
							+ "SDYMusicTab.sdymusictab_songID = SDYSong.sdysong_ID");
			while (resultTab.next()){
				int idMusicTab = resultTab.getInt(1);
				String songName = resultTab.getString(2);
				tabs += "/" + idMusicTab + "/" + songName;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		tabs = tabs.substring(1);
		return tabs;
	}
	
	public void close(){
		try {
			bd.fecharConexao();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
