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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (true) {
				int code = -1;
				try {
					byte[] pack = new byte[10000];
					reader.read(pack);
					String operation = new String(pack);
					StringTokenizer tokenizer = new StringTokenizer(operation,
							"/");
					code = Integer.parseInt(tokenizer.nextToken());

					if (code == Operations.SIGNUP.getCode()) {
						String name = tokenizer.nextToken();
						String pwd = tokenizer.nextToken();
						int success = signUp(name, pwd);
						writer.write(success);
					}
					/*
					 * int ind = 0; try { System.out.println("trying to read");
					 * byte[] pack = new byte[4]; reader.read(pack); ind =
					 * PackManager.unpack(pack, 0); System.out.println("read");
					 * } catch (IOException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); } try { ClientRequest cr =
					 * this.getRequest(ind); if (cr == ClientRequest.HI) {
					 * ServerResponse sr = ServerResponse.HI; byte[] pack = new
					 * byte[4]; PackManager.pack(sr.getInd(), pack, 0);
					 * writer.write(pack); writer.flush();
					 * System.out.println("wrote to client"); }/* else if () {
					 * 
					 * }
					 * 
					 * } catch (Exception e) { e.printStackTrace(); }
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
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

		public int signUp(String name, String pwd) throws Exception {
			if (name == ""){
				return Operations.NAMEINVALID.getCode();
			}
			
			if (pwd == ""){
				return Operations.PWDINVALID.getCode();
			}
			
			DataBase db = new DataBase();
			db.signUp(name, pwd);
			return 0;
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
