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
		String operation = Operations.SIGN_UP.getCode() + "/" + name + "/" + pwd
				+ "/" + email + "/" + birthDate;
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

	public boolean signIn(String email, String pwd)
			throws UnknownHostException, IOException {
		String operation = Operations.SIGN_IN.getCode() + "/" + email + "/"
				+ pwd;
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

	public void closeConnection() throws IOException {
		socket.close();
	}
}
