package server_main;

public enum Operations {
	SIGN_UP(0), 
	NAME_INVALID(1), 
	INVALID_PASSWORD(2), 
	INVALID_EMAIL(3), 
	INVALID_BIRTH_DATE(4), 
	SIGN_UP_SUCCESS(5), 
	USER_EXISTS(6),
	USER_DOES_NOT_EXISTS(7),
	SIGN_UP_FAIL(8),
	SIGN_IN(9),
	UPLOAD(10),
	UPLOAD_FAILED(11),
	UPLOAD_SUCCESS(12),
	DOWNLOAD(13),
	DOWNLOAD_FAILED(14),
	TAB_NOT_FOUND(15),
	LIST_TABS(16);


	private int code;

	Operations(int c) {
		this.code = c;
	}

	public int getCode() {
		return code;
	}
}
