package br.example.util;

public class CustomErrorType {

	private String errorMessage;
	
	private String status;

	public CustomErrorType(String status,String errorMessage) {
		this.status = status;
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public String getStatus(){
		return status;
	}

}
