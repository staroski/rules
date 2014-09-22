package br.com.staroski.rules;

/**
 * Regra interna que realiza a opera&ccedil;&atilde;o <B>OU</B> entre duas especifica&ccedil;&otilde;es.
 * 
 * @param <T> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
 */
public final class Or<T> extends BinaryRule<T> {

	/**
	 * Cria uma nova regra.
	 * 
	 * @param spec1 A primeira especifica&ccedil;&atilde;o desta regra.
	 * @param spec2 A segunda especifica&ccedil;&atilde;o desta regra.
	 */
	protected Or(final Rule<T> spec1, final Rule<T> spec2) {
		super(spec1, spec2);
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
		return satisfied1 || satisfied2;
	}
}