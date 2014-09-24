package br.com.staroski.rules;

/**
 * Superclasse de regras bin&aacute;rias.
 * 
 * @param <T> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
 */
abstract class BinaryRule<T> extends Rule<T> {

	/**
	 * A primeira {@link Rule regra}.
	 */
	private final Rule<T> rule1;

	/**
	 * A segunda {@link Rule regra}.
	 */
	private final Rule<T> rule2;

	/**
	 * Cria uma nova regra.
	 * 
	 * @param rule1 A primeira especifica&ccedil;&atilde;o desta regra.
	 * @param rule2 A segunda especifica&ccedil;&atilde;o desta regra.
	 */
	protected BinaryRule(final Rule<T> rule1, final Rule<T> rule2) {
		this.rule1 = rule1;
		this.rule2 = rule2;
	}

	/**
	 * @return
	 */
	protected final Rule<T> getFirstRule() {
		return rule1;
	}

	/**
	 * @return
	 */
	protected final Rule<T> getSecondRule() {
		return rule2;
	}
}