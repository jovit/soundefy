package soundefy.client;

public enum ServerResponse {
	HI(0);
	
	private int ind;
	
	ServerResponse(int ind){
		this.setInd(ind);
	}

	public static ServerResponse getRequest(int ind2) {
		switch (ind2){
			case 0:{
				return ServerResponse.HI;
			}
		}
		return null;
	}

	public int getInd() {
		return ind;
	}

	public void setInd(int ind) {
		this.ind = ind;
	}
}
