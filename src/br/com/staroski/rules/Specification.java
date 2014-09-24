package br.com.staroski.rules;

/**
 * Interface que representa a {@link Specification especifica&ccedil;&atilde;o} de uma {@link Rule regra} para determinado tipo de dado.<BR>
 * O crit&eacute;rio pra avaliar se um objeto atende ou n&atilde;o a uma especifica&ccedil;&atilde;o &eacute; realizado atrav&eacute;s da implementa&ccedil;&atilde;o do m&eacute;todo
 * {@link #verify(Object)}.<BR>
 * Se o objeto atende &agrave; {@link Specification especifica&ccedil;&atilde;o}, ele deve retornar normalmente.<BR>
 * Se o objeto n&atilde;o atende &agrave; {@link Specification especifica&ccedil;&atilde;o}, ele deve lan&ccedil;ar uma exce&ccedil;&atilde;o do tipo {@link UnattendedException}.<BR>
 * <BR>
 * <I><B>Dica: </B> Consulte a documenta&ccedil;&atilde;o da classe {@link Rule} para mais detalhes.</I>
 * 
 * @author Ricardo Artur Staroski
 * @param <T> Tipo de dado do objeto que pode ou n&atilde;o atender a especifica&ccedil;&atilde;o desta interface.
 */
public interface Specification<T> {

	/**
	 * Verifica se o objeto informado atende, ou n&atilde;o, a especifica&ccedil;&atilde;o desta interface.
	 * 
	 * @param object O objeto a ser verificado.
	 * 
	 * @throws UnattendedException se o objeto n&atilde;o atender a especifica&ccedil;&atilde;o
	 */
	public void verify(T object) throws UnattendedException;
}