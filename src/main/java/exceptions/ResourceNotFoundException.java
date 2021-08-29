package exceptions;

public class ResourceNotFoundException extends DAOExeption {

	private static final long serialVersionUID = -5563342758700725242L;

	public ResourceNotFoundException(String message, int code) {
		super(message, code);
	}
}
