package soundefy.client;

public enum ClientRequest {
	HI(0);
	
	private int ind;
	
	ClientRequest(int ind){
		this.setInd(ind);
	}

	public static ClientRequest getRequest(int ind2) {
		switch (ind2){
			case 0:{
				return ClientRequest.HI;
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
