package server_main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server {
	private class ClientListener implements Runnable {

		private DataInputStream reader;
		private DataOutputStream writer;
		private Socket socket;
		private BD bd;

		public ClientListener(Socket s) {
			try {
				this.socket = s;
				this.reader = new DataInputStream(s.getInputStream());
				this.writer = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			int code = -1;
			try {
				byte[] pack = new byte[1024];
				reader.read(pack);
				String operation = new String(pack);
				StringTokenizer tokenizer = new StringTokenizer(
						operation.trim(), "/");
				code = Integer.parseInt(tokenizer.nextToken());

				if (code == Operations.SIGN_UP.getCode()) {
					String name = tokenizer.nextToken();
					String pwd = tokenizer.nextToken();
					String email = tokenizer.nextToken();
					String birthDate = tokenizer.nextToken();
					int success = signUp(name, pwd, email, birthDate);
					PackManager.pack(success, pack, 0);
					writer.write(pack);
				} else if (code == Operations.SIGN_IN.getCode()) {
					String name = tokenizer.nextToken();
					String pwd = tokenizer.nextToken();
					int success = signIn(name, pwd);
					PackManager.pack(success, pack, 0);
					writer.write(pack);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private ClientRequest getRequest(int cr) throws Exception {
			switch (cr) {
			case 0: {
				return ClientRequest.HI;
			}
			}
			throw new Exception("Nao existe esse client request");
		}

		public int signUp(String name, String pwd, String email,
				String birthDate) throws Exception {
			DataBase db = new DataBase();
			int success = db.signUp(name, pwd, email, birthDate);
			return success;
		}

		public int signIn(String email, String pwd) throws Exception {
			DataBase db = new DataBase();
			int success = db.signIn(email, pwd);
			return success;
		}
	}

	private ServerSocket serverSocket;
	public static final int PORT = 666;

	public Server() {
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.err.println("Erro ao inicializar o server com a porta : "
					+ PORT);
		}
		while (true) {
			Socket client;
			try {
				System.out.println("Waiting for connection...");
				client = serverSocket.accept();
				new Thread(new ClientListener(client)).start();
				System.out.println("Connection established");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
