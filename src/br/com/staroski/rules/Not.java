package br.com.staroski.rules;

/**
 * Regra interna que realiza a opera&ccedil;&atilde;o <B>N&Atilde;O</B> de uma especifica&ccedil;&atilde;o qualquer.
 * 
 * @param <T> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
 */
final class Not<T> extends Rule<T> {

	private Rule<T> rule;

	/**
	 * Cria uma nova regra.
	 * 
	 * @param spec A primeira especifica&ccedil;&atilde;o desta regra.
	 */
	protected Not(Rule<T> rule) {
		this.rule = rule;
	}

	protected final Rule<T> getRule() {
		return rule;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(final T object) {
		boolean unsatisfied = !rule.isSatisfiedBy(object);
		if (unsatisfied) {
			getModifiableDetails().addAll(rule.getDetails());
		}
		return unsatisfied;
	}
}