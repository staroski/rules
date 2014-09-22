package br.com.staroski.rules;

/**
 * Superclasse de regras unárias. 
 * 
 * @param <T> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
 */
abstract class UnaryRule<T> extends Rule<T> {

	private Specification<T> spec;

	/**
	 * Cria uma nova regra.
	 * 
	 * @param spec A especifica&ccedil;&atilde;o desta regra.
	 */
	protected UnaryRule(final Specification<T> spec) {
		this.spec = spec;
	}

	/**
	 * Obt&eacute;m a {@link Specification especifica&ccedil;&atilde;o} que foi utilizada para criar esta {@link Rule regra}.
	 * 
	 * @return a {@link Specification especifica&ccedil;&atilde;o} desta {@link Rule regra}.
	 */
	public final Specification<T> getSpecification() {
		return spec;
	}
}