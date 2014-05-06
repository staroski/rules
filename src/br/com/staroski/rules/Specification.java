package br.com.staroski.rules;
import java.util.Collection;

/**
 * Interface que representa a especifica&ccedil;ão de uma regra para determinado tipo de dado.<BR>
 * A especifica&ccedil;ão pode fornecer detalhes sobre o motivo de um objeto atende-la ou não.<BR>
 * O crit&eacute;rio pra avaliar se um objeto atende ou não a uma especifica&ccedil;ão &eacute; realizado atrav&eacute;s da implementa&ccedil;ão do m&eacute;todo
 * {@link #isSatisfiedBy(Object, Collection)}.<BR>
 * <BR>
 * <I><B>Dica: </B> Consulte a documenta&ccedil;ão da classe {@link Rule} para mais detalhes.</I>
 * 
 * @author Ricardo Artur Staroski
 * @param <O> Tipo de dado do objeto que pode ou não atender a especifica&ccedil;ão desta interface.
 * @param <D> Tipo de dado da lista de detalhes que pode conter informa&ccedil;ões sobre o motivo de um objeto atender, ou não, a especifica&ccedil;ão desta
 *            interface.
 */
public interface Specification<O, D> {

    /**
     * Verifica se o objeto informado atende, ou não, a especifica&ccedil;ão desta interface.
     * 
     * @param object O objeto a ser verificado.
     * @param details Cole&ccedil;ão que pode conter os detalhes sobre o motivo de um objeto atender, ou não, a esta especifica&ccedil;ão.
     * @return <code>true</code> se o objeto atender a especifica&ccedil;ão e <code>false</code> caso contrário.
     */
    public boolean isSatisfiedBy(O object, Collection<D> details);
}
