package br.com.staroski.rules;

/**
 * Exce&ccedil;&atilde;o lan&ccedil;ada quando um objeto n&atilde;o atende &agrave; {@link Specification especifica&ccedil;&atilde;o} de uma {@link Rule regra}.
 *
 * @author Ricardo Artur Staroski
 */
public class UnattendedException extends Exception {

	private static final long serialVersionUID = 1;

	/**
	 * @see Exception#Exception(String)
	 */
	public UnattendedException(String message) {
		super(message);
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public UnattendedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @see Exception#Exception(Throwable)
	 */
	public UnattendedException(Throwable cause) {
		super(cause);
	}

}