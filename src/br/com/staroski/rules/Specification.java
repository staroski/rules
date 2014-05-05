package br.com.staroski.rules;
import java.util.Collection;

/**
 * Interface que representa a especificação de uma regra para determinado tipo de dado.<BR>
 * A especificação pode fornecer detalhes sobre o motivo de um objeto atende-la ou não.<BR>
 * O critério pra avaliar se um objeto atende ou não a uma especificação é realizado através da implementação do método
 * {@link #isSatisfiedBy(Object, Collection)}.<BR>
 * <BR>
 * <I><B>Dica: </B> Consulte a documentação da classe {@link Rule} para mais detalhes.</I>
 * 
 * @author Ricardo Artur Staroski
 * @param <O> Tipo de dado do objeto que pode ou não atender a especificação desta interface.
 * @param <D> Tipo de dado da lista de detalhes que pode conter informações sobre o motivo de um objeto atender, ou não, a especificação desta
 *            interface.
 */
public interface Specification<O, D> {

    /**
     * Verifica se o objeto informado atende, ou não, a especificação desta interface.
     * 
     * @param object O objeto a ser verificado.
     * @param details Coleção que pode conter os detalhes sobre o motivo de um objeto atender, ou não, a esta especificação.
     * @return <code>true</code> se o objeto atender a especificação e <code>false</code> caso contrário.
     */
    public boolean isSatisfiedBy(O object, Collection<D> details);
}