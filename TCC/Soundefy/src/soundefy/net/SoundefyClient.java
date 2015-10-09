package soundefy.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import soundefy.net.ClientRequest;

public class SoundefyClient {
	private static final int PORT = 13174;
	private static final String HOSTNAME = "LAPA17";
	private DataInputStream  reader;
	private DataOutputStream writer;
	
	private class ServerListener implements Runnable{

		@Override
		public void run() {
			while (true){
				try {
					byte [] pack = new byte [4];
					reader.read(pack);
					int i = PackManager.unpack(pack, 0);
					ServerResponse sr = getResponse(i);
					if (sr == ServerResponse.HI){
						System.out.println("server says hi!");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		private ServerResponse getResponse(int cr) throws Exception{
			switch (cr){
				case 0:{
					return ServerResponse.HI;
				}
			}
			throw new Exception("Nao existe esse clientRequest");
		}
		
	}
	
	public SoundefyClient(){
		try {
			Socket s = new Socket(HOSTNAME, PORT);
			reader = new DataInputStream(s.getInputStream());
			writer = new DataOutputStream(s.getOutputStream());
			new Thread(new ServerListener()).start();
			while (true){
				byte pack [] = new byte[4];
				PackManager.pack(0, pack, 0);
				writer.write(pack);
				writer.flush();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
