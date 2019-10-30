package br.com.staroski.rules;

import java.util.*;

/**
 * Especializa&ccedil;&atilde;o de {@link ArrayList} que n&atilde;o aceita duplicatas.
 * 
 * @param <T> Tipo de dado da lista.
 */
final class Details<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(final int index, final T element) {
		if (!contains(element)) {
			super.add(index, element);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(final T e) {
		if (!contains(e)) {
			return super.add(e);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(final Collection<? extends T> c) {
		return super.addAll(getNotContained(c));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(final int index, final Collection<? extends T> c) {
		return super.addAll(index, getNotContained(c));
	}

	/**
	 * Dado uma cole&ccedil;&atilde;o, obt&eacute;m os elementos desta cole&ccedil;&atilde;o que n&atilde;o inclusos na lista atual.
	 * 
	 * @param c Um cole&ccedil;&atilde;o qualquer
	 * @return Os elementos da cole&ccedil;&atilde;o que n&atilde;o est&atilde;o inclusos na lista atual.
	 */
	private Collection<T> getNotContained(final Collection<? extends T> c) {
		final List<T> elements = new LinkedList<T>();
		for (final T e : c) {
			if (!contains(e)) {
				elements.add(e);
			}
		}
		return elements;
	}

}