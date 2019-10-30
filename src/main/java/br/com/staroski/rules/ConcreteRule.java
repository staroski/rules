package br.com.staroski.rules;

final class ConcreteRule<T> extends Rule<T> {

	private Specification<T> spec;

	protected ConcreteRule(Specification<T> spec) {
		this.spec = spec;
	}

	/**
	 * Obt&eacute;m a {@link Specification especifica&ccedil;&atilde;o} que foi utilizada para criar esta {@link Rule regra}.
	 * 
	 * @return a {@link Specification especifica&ccedil;&atilde;o} desta {@link Rule regra}.
	 */
	protected final Specification<T> getSpecification() {
		return spec;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(final T object) {
		boolean satisfied = false;
		try {
			getSpecification().verify(object);
			satisfied = true;
		} catch (UnattendedException e) {
			getModifiableDetails().add(e.getMessage());
		}
		return satisfied;
	}
}