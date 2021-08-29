package exceptions;



public class DAOExeption extends RuntimeException{
	
	private static final long serialVersionUID = 2843832704820505798L;
	
	private int code;
	
	public DAOExeption(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
