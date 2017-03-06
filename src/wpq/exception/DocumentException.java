package wpq.exception;

@SuppressWarnings("serial")
public class DocumentException extends RuntimeException {

	public DocumentException() {
		super();
	}

	public DocumentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DocumentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DocumentException(String arg0) {
		super(arg0);
	}

	public DocumentException(Throwable arg0) {
		super(arg0);
	}
	
}
