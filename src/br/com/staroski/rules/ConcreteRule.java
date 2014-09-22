package br.com.staroski.rules;

final class ConcreteRule<T> extends UnaryRule<T> {

	protected ConcreteRule(Specification<T> spec) {
		super(spec);
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
