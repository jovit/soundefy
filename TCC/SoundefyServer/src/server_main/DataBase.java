package server_main;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
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

	private int getGenreID() {
		try {
			ResultSet result = bd
					.execConsulta("select max(sdymusicgenre_ID) from SDYMusicGenre"); // change
											// to
											// stored
			// procedure
			if (result.first()) {
				int id = result.getInt(1);
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

	private int getArtistID() {
		try {
			ResultSet result = bd
					.execConsulta("select max(sdymusicartist_ID) from SDYMusicArtist"); // change
			// to
			// stored
			// procedure
			if (result.first()) {
				int id = result.getInt(1);
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

	private int getSongID(String songName, String songYear, int artistID,
			int genreID) {
		try {
			ResultSet result = bd
					.execConsulta("select * from SDYSong where sdysong_name = '"
							+ songName
							+ "' and sdysong_year = "
							+ songYear
							+ " and sdysong_artistID = "
							+ artistID
							+ " and sdysong_genreID = " + genreID);
			if (result.first()) {
				int id = result.getInt(1);
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
			String songGenre, String filePath) throws Exception {
		try {
			bd.execComando("insert into SDYMusicGenre values('" + songGenre
					+ "')");
			bd.execComando("insert into SDYMusicArtist values('" + artistName
					+ "')");
			int artistID = getArtistID();
			int genreID = getGenreID();
			bd.execComando("insert into SDYSong values('" + songName + "',"
					+ songYear + "," + artistID + "," + genreID + ")");
			int songID = getSongID(songName, songYear, artistID, genreID);
			bd.execComando("insert into SDYMusicTab values('" + filePath
					+ "'," + songID + ")");
			return Operations.UPLOAD_SUCCESS.getCode();
		} catch (SQLException e) {
			return Operations.UPLOAD_FAILED.getCode();
		}
	}

	public boolean userExists(String email, String pwd) {
		try {
			ResultSet result = bd
					.execConsulta("select * from SDYUser where sdyuser_password='"
							+ pwd + "' and sdyuser_email='" + email + "'"); 
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

	
	public byte[] downloadTab(String tabId){

		byte [] tabData = new byte[1024];
		try{
			ResultSet resultTab = bd
					.execConsulta("select sdymusictab_url from SDYMusicTab where sdymusictab_ID = " + tabId);
			if (resultTab.first()){
				String urlTab = resultTab.getString(1);
				DataInputStream reader = new DataInputStream(new FileInputStream(new File(urlTab+".sdy")));
				reader.read(tabData);
			} else {
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tabData;
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

	public void close() {
		try {
			bd.fecharConexao();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
