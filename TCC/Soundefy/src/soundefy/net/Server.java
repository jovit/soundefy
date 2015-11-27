package soundefy.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import server_main.Operations;

public class Server {
	private Socket socket;
	private DataInputStream reader;
	private DataOutputStream writer;

	public Server() {
		try {
			socket = new Socket("localhost", 666);
			writer = new DataOutputStream(socket.getOutputStream());
			reader = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean signUp(String name, String pwd, String email,
			String birthDate) throws UnknownHostException, IOException {
		String operation = Operations.SIGN_UP.getCode() + "/" + name + "/"
				+ pwd + "/" + email + "/" + birthDate;
		writer.write(operation.getBytes());
		byte[] pack = new byte[4];
		reader.read(pack);
		int result = PackManager.unpack(pack, 0);
		if (result == Operations.SIGN_UP_SUCCESS.getCode()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean signIn(String email, String password)
			throws UnknownHostException, IOException {
		String operation = Operations.SIGN_IN.getCode() + "/" + email + "/"
				+ password;
		writer.write(operation.getBytes());
		byte[] pack = new byte[4];
		reader.read(pack);
		int result = PackManager.unpack(pack, 0);
		if (result == Operations.USER_EXISTS.getCode()) {
			return true;
		} else {
			return false;
		}
	}
	
	public String download(int tabId){
		String operation = Operations.DOWNLOAD.getCode() + "/" + tabId;
		
		try {
			writer.write(operation.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] pack = new byte[1024];
		//reader.read(pack);
		//String result = PackManager.unpack(pack, 0);
		
		return "";
	}

	public boolean upload(String artistName, String songYear, String songName,
			String songGenre, String file) throws UnknownHostException,
			IOException {
		String operation = Operations.SIGN_IN.getCode() + "/" + artistName
				+ "/" + songYear + "/" + songName + "/" + songGenre + "/"
				+ file;
		writer.write(operation.getBytes());
		byte[] pack = new byte[4];
		reader.read(pack);
		int result = PackManager.unpack(pack, 0);
		if (result == Operations.UPLOAD_SUCCESS.getCode()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean upload(String artistName, String songYear, String songName,
			String songGenre, byte[] file) throws IOException, InterruptedException {
		String operation = Operations.UPLOAD.getCode() + "/" + artistName + "/"
				+ songYear + "/" + songName + "/" + songGenre;
		writer.write(operation.getBytes());
		Thread.sleep(500);
		writer.write(file);
		byte[] pack = new byte[4];
		reader.read(pack);
		int result = PackManager.unpack(pack, 0);
		if (result == Operations.UPLOAD_SUCCESS.getCode()) {
			return true;
		} else {
			return false;
		}
	}

	public void closeConnection() throws IOException {
		socket.close();
		writer.close();
		reader.close();
	}
	
	public String getTabs(){
		String tabs = "";
		String operation = String.valueOf(Operations.LIST_TABS.getCode());
		try {
			writer.write(operation.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte [] packReceived = new byte [1024];
		try {
			reader.read(packReceived);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabs = new String(packReceived);
		tabs = tabs.trim();
		
		try{
			if(Integer.valueOf(tabs) == Operations.NO_TABS.getCode()){
				return "";
			}
		}catch(Exception e){}
		
		return tabs;
	}
}
