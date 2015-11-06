package server_main;

public enum Operations {
	SIGNUP(0), 
	NAMEINVALID(1), 
	PWDINVALID(2), 
	EMAILINVALID(3), 
	BIRTHDATEINVALID(4), 
	SIGNUPSUCCESS(5), 
	USEREXISTS(6),
	USERDOESNOTEXISTS(7),
	SIGNUPFAIL(8),
	SIGNIN(9);

	private int code;

	Operations(int c) {
		this.code = c;
	}

	public int getCode() {
		return code;
	}
}
