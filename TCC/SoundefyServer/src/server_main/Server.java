package server_main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private class ClientListener implements Runnable{
		
		private DataInputStream reader;
		private DataOutputStream writer;
		
		public ClientListener(Socket s){
			try {
				reader = new DataInputStream(s.getInputStream());
				writer = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			while(true){
				int ind = reader.readInt();
				ClientRequest cr = ClientRequest.getRequest(ind);
			}
		}
	}
	
	
	private ServerSocket serverSocket;
	public static final int PORT = 13174;
	
	public Server(){
		serverSocket = null;
		try{
			serverSocket = new ServerSocket(13174);
		}catch (IOException e){
			System.err.println("Erro ao inicializar o server com a porta : " + PORT);
		}
		while (true){
			Socket client;
			try {
				client = serverSocket.accept();
				new Thread(new ClientListener(client)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
