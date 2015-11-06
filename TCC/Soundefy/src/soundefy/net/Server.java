package soundefy.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
		writer.writeBytes(Operations.SIGNUP.getCode() + "/" + name.getBytes()
				+ "/" + pwd.getBytes() + "/" + email.getBytes() + "/"
				+ birthDate.getBytes());
		byte[] pack = new byte[4];
		reader.read(pack);
		int result = PackManager.unpack(pack, 0);
		if (result == Operations.SIGNUPSUCCESS.getCode()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean signIn(String email, String pwd) throws UnknownHostException,
			IOException {
		writer.writeBytes(Operations.SIGNIN.getCode() + "/" + email.getBytes()
				+ "/" + pwd.getBytes());
		byte[] pack = new byte[4];
		reader.read(pack);
		int result = PackManager.unpack(pack, 0);
		if (result == Operations.USEREXISTS.getCode()) {
			return true;
		} else {
			return false;
		}
	}
}
