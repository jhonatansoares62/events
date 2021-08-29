package exceptions;

public class ResourceNotYetImplementedException extends RuntimeException {

	private static final long serialVersionUID = 2843832704820505798L;

	private int code;

	public ResourceNotYetImplementedException() {
		super("Recurso nao implementado.");
		this.code = ErrorCode.SERVER_ERROR.getCode();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
