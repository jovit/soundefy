package soundefy.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	private server_main.Server server;
	private Socket socket;

	public Server() {
		try {
			socket = new Socket("localhost", 666);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean signUp(String name, String pwd) throws UnknownHostException,
			IOException {
		DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
		DataInputStream reader = new DataInputStream(socket.getInputStream());
		writer.writeBytes(Operations.SIGNUP.getCode() + "/" + name.getBytes()
				+ "/" + pwd.getBytes());
		int result = reader.readInt();
		if (result == Operations.SIGNUPSUCCESS.getCode()) {
			return false;
		} else {
			return true;
		}
	}
}
