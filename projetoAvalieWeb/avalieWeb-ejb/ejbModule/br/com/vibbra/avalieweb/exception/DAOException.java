package br.com.vibbra.avalieweb.exception;

public class DAOException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Exception exception) {
		super(exception);
	}


}
