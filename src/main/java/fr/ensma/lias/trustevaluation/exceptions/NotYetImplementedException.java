package fr.ensma.lias.trustevaluation.exceptions;

/**
 * @author Mickael BARON
 */
public class NotYetImplementedException extends RuntimeException {

	private static final long serialVersionUID = -1984960053335997013L;

	public NotYetImplementedException() {
	}

	public NotYetImplementedException(String content) {
		super(content);
	}
}
