package soundefy.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	private server_main.Server server;

	public Server() {
		server = new server_main.Server();
	}

	public int signUp(String name, String pwd) throws UnknownHostException, IOException {
		int result = -1;
		Socket socket = new Socket("localhost", server_main.Server.PORT);
		return result;
	}
}
