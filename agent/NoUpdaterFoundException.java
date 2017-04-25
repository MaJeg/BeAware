package agent;

public class NoUpdaterFoundException extends Exception {

	public NoUpdaterFoundException() {
		super();
	}

	public NoUpdaterFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoUpdaterFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoUpdaterFoundException(String message) {
		super(message);
	}

	public NoUpdaterFoundException(Throwable cause) {
		super(cause);
	}

}
