package server_main;

public enum ClientRequest {
	HI(0);
	
	private int ind;
	
	ClientRequest(int ind){
		this.ind = ind;
	}

	public static ClientRequest getRequest(int ind2) {
		switch (ind2){
			case 0:
				return ClientRequest.HI;
		}
		return null;
	}
}
