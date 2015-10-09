package soundefy.net;

public enum ClientRequest {
	HI(0);
	
	private int ind;
	
	ClientRequest(int ind){
		this.setInd(ind);
	}

	public int getInd() {
		return ind;
	}

	public void setInd(int ind) {
		this.ind = ind;
	}
}
