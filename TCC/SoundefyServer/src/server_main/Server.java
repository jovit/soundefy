package server_main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

public class Server {
	private class ClientListener implements Runnable {

		private DataInputStream reader;
		private DataOutputStream writer;
		private DataBase db;

		public ClientListener(Socket s) {
			try {
				this.reader = new DataInputStream(s.getInputStream());
				this.writer = new DataOutputStream(s.getOutputStream());
				this.db = new DataBase();
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
				} else if (code == Operations.UPLOAD.getCode()) {
					String artistName = tokenizer.nextToken();
					String songYear = tokenizer.nextToken();
					String songName = tokenizer.nextToken();
					String songGenre = tokenizer.nextToken();
					byte[] file = new byte[1024];
					reader.read(file);
					String path = saveLocalFile(file);
					int success = upload(artistName, songYear, songName,
							songGenre, path);
					PackManager.pack(success, pack, 0);
					writer.write(pack);
				} else if (code == Operations.DOWNLOAD.getCode()) {
					String tabId = tokenizer.nextToken();
					byte[] tabData = db.downloadTab(tabId);
					writer.write(tabData);
				} else if (code == Operations.LIST_TABS.getCode()) {
					String tabs = db.getTabs();
					if(tabs.equals("")){
						tabs = String.valueOf(Operations.NO_TABS.getCode());
					}
					pack = tabs.getBytes();
					writer.write(pack);
				}else if (code == Operations.SEARCH_TAB.getCode()) {
					String tabToSearch = tokenizer.nextToken();
					String tabs = db.getTab(tabToSearch);
					if(tabs.equals("")){
						tabs = String.valueOf(Operations.NO_TABS.getCode());
					}
					pack = tabs.getBytes();
					writer.write(pack);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private String saveLocalFile(byte[] file) throws IOException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date date = new Date();
			String path = dateFormat.format(date);
			File f = new File(path + ".sdy");
			f.createNewFile();
			DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
			out.write(file);
			out.close();
			return path;
		}

		public int signUp(String name, String pwd, String email,
				String birthDate) throws Exception {
			int success = db.signUp(name, pwd, email, birthDate);
			return success;
		}

		public int signIn(String email, String pwd) throws Exception {
			int success = db.signIn(email, pwd);
			return success;
		}

		public int upload(String artistName, String songYear, String songName,
				String songGenre, String file) throws Exception {
			DataBase db = new DataBase();
			int success = db.upload(artistName, songYear, songName, songGenre,
					file);
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
