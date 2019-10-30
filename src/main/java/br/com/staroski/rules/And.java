package br.com.staroski.rules;

/**
 * Regra interna que realiza a opera&ccedil;&atilde;o <B>E</B> entre duas especifica&ccedil;&otilde;es.
 * 
 * @param <T> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
 */
final class And<T> extends BinaryRule<T> {

	/**
	 * Cria uma nova regra.
	 * 
	 * @param rule1 A primeira especifica&ccedil;&atilde;o desta regra.
	 * @param rule2 A segunda especifica&ccedil;&atilde;o desta regra.
	 */
	protected And(final Rule<T> rule1, final Rule<T> rule2) {
		super(rule1, rule2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(final T object) {
		Rule<T> rule1 = getFirstRule();
		boolean satisfied1 = rule1.isSatisfiedBy(object);
		if (!satisfied1) {
			getModifiableDetails().addAll(rule1.getDetails());
		}
		Rule<T> rule2 = getSecondRule();
		boolean satisfied2 = rule2.isSatisfiedBy(object);
		if (!satisfied2) {
			getModifiableDetails().addAll(rule2.getDetails());
		}
		return satisfied1 && satisfied2;
	}
}