package soundefy.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	public static final int PORT = 13174;
	private DataInputStream reader;
	private DataOutputStream writer;
	private Socket socket;
	
	
	public Client(){
		try {
			socket = new Socket("lapa17",PORT);
			writer = new DataOutputStream(socket.getOutputStream());
			reader = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(ClientRequest myRequest){
		try {
			writer.writeInt(myRequest.getInd());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ServerResponse read(){
		int ind;
		try {
			ind = reader.read();
			return ServerResponse.getRequest(ind);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
