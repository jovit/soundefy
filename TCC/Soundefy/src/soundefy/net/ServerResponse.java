package soundefy.net;

public enum ServerResponse {
	HI(0);
	
	private int ind;
	
	ServerResponse(int ind){
		this.setInd(ind);
	}

	public int getInd() {
		return ind;
	}

	public void setInd(int ind) {
		this.ind = ind;
	}
}
