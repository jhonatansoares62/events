package exceptions;

public class ValidationException extends DAOExeption {

	private static final long serialVersionUID = -5563342758700725242L;

	public ValidationException(String message, int code) {
		super(message, code);
	}
}
