package server_main;

public enum Operations {
	SIGNUP(0), NAMEINVALID(1), PWDINVALID(2), SIGNUPSUCCESS(3), USEREXISTS(4);

	private int code;

	Operations(int c) {
		this.code = c;
	}
	
	public int getCode(){
		return code;
	}
}
