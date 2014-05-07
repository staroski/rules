package br.com.staroski.rules;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Esta classe &eacute; repons&aacute;vel por criar regras a partir de simples especifica&ccedil;&otilde;es.<BR>
 * <BR>
 * <I> Para obter instancias desta classe, utilize o m&eacute;todo {@link #from(Specification)}.</I><BR>
 * <BR>
 * Atrav&eacute;s de uma inst&acirc;ncia de {@link Rule}, &eacute; possivel montar express&otilde;es complexas associando outras {@link Specification}s ou {@link Rule}s atrav&eacute;s
 * dos seguintes m&eacute;todos:<BR>
 * - {@link #and(Specification)}<BR>
 * - {@link #andNot(Specification)}<BR>
 * - {@link #or(Specification)}<BR>
 * - {@link #orNot(Specification)}<BR>
 * - {@link #not()} <BR>
 * Outra característica interessante desta classe &eacute; o m&eacute;todo {@link #getDetails()} que obt&eacute;m detalhes do motivo de um objeto atender, ou n&atilde;o, 
 * dependendo da situa&ccedil;&atilde;o, a especifica&ccedil;&atilde;o da regra.<BR>
 * <BR>
 * <B><I>Veja o exemplo para entender como utiliza-la:</I></B><BR>
 * <BR>
 * Uma <tt>Pessoa</tt> tem os seguintes atributos: <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt>.<BR>
 * Para validar uma <tt>Pessoa</tt>, foram definidas as seguintes regras:<BR>
 * - O nome n&atilde;o pode ser <code>null</code> e deve come&ccedil;ar com uma letra maiúscula e ter uma ou mais letras minúsculas;<BR>
 * - A idade n&atilde;o pode ser negativa;<BR>
 * - O sexo s&oacute; pode ser <code>'M'</code> ou <code>'F'</code>.<BR>
 * <BR>
 * <B>Primeiro definimos a classe <tt>Pessoa</tt> conforme abaixo:</B>
 * 
 * <PRE>
 * class Pessoa {
 * 
 *     String nome;
 *     int idade;
 *     char sexo;
 * 
 *     Pessoa(String nome, int idade, char sexo) {
 *         this.nome = nome;
 *         this.idade = idade;
 *         this.sexo = sexo;
 *     }
 * }
 * </PRE>
 * 
 * <B>Agora criamos tr&ecirc;s especifica&ccedil;&otilde;es distintas para as regras que validam <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt> da <tt>Pessoa</tt>.</B>
 * 
 * <PRE>
 * // Especifica&ccedil;&atilde;o da regra que valida o nome de uma Pessoa
 * class Nome implements Specification&lt;Pessoa, String&gt; {
 * 
 *     public boolean isSatisfiedBy(Pessoa pessoa, Collection&lt;String&gt; detalhes) {
 *         String nome = pessoa.nome;
 *         if (nome == null) {
 *             detalhes.add(&quot;Nome n&atilde;o pode ser null&quot;);
 *             return false;
 *         }
 *         if (!nome.matches(&quot;[A-Z]{1}[a-z]+&quot;)) {
 *             detalhes.add(&quot;Nome precisa come&ccedil;ar com letra maiuscula e ter pelo menos duas letras&quot;);
 *             return false;
 *         }
 *         return true;
 *     }
 * }
 * </PRE>
 * 
 * <PRE>
 * // Especifica&ccedil;&atilde;o da regra que valida a idade de Pessoa
 * class Idade implements Specification&lt;Pessoa, String&gt; {
 * 
 *     public boolean isSatisfiedBy(Pessoa pessoa, Collection&lt;String&gt; detalhes) {
 *         if (pessoa.idade &lt; 0) {
 *             detalhes.add(&quot;Idade n&atilde;o pode ser negativa&quot;);
 *             return false;
 *         }
 *         return true;
 *     }
 * }
 * </PRE>
 * 
 * <PRE>
 * // Especifica&ccedil;&atilde;o da regra que valida o sexo de uma Pessoa
 * class Sexo implements Specification&lt;Pessoa, String&gt; {
 * 
 *     public boolean isSatisfiedBy(Pessoa pessoa, Collection&lt;String&gt; detalhes) {
 *         switch (pessoa.sexo) {
 *             case 'M':
 *             case 'F':
 *                 return true;
 *             default:
 *                 detalhes.add(&quot;Sexo s&oacute; pode ser 'M' ou 'F'&quot;);
 *                 return false;
 *         }
 *     }
 * }
 * </PRE>
 * 
 * <B>Agora ja temos a classe <tt>Pessoa</tt> e as especifica&ccedil;&otilde;es das regras para <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt> criadas.<BR>
 * Podemos ent&atilde;o utilizar a classe {@link Rule} para validar instancias de <tt>Pessoa</tt> de diversas formas, por exemplo:</B>
 * 
 * <PRE>
 * // instanciamos as regras a partir das especifica&ccedil;&otilde;es 
 * Rule&lt;Pessoa, String&gt; nome = Rule.from(new Nome());
 * Rule&lt;Pessoa, String&gt; idade = Rule.from(new Idade());
 * Rule&lt;Pessoa, String&gt; sexo = Rule.from(new Sexo());
 * 
 * // criamos uma pessoa com nome, idade e sexo validos
 * Pessoa pessoa = new Pessoa(&quot;Ricardo&quot;, 29, 'M');
 * if (nome.and(idade)
 *         .and(sexo)
 *         .isSatisfiedBy(pessoa)) {
 *     // o nome, idade e sexo da pessoa atendem as regras
 * }
 * 
 * // criamos uma pessoa com nome invalido, mas idade e sexo validos
 * pessoa = new Pessoa(&quot;RicArdO&quot;, 29, 'M');
 * Rule&lt;Pessoa, String&gt; regra = nome.and(idade)
 *                                  .and(sexo);
 * if (regra.not().isSatisfiedBy(pessoa)) {
 *     // apresentamos os detalhes
 *     for (String detalhe : regra.getDetails()) {
 *         System.out.println(detalhe);
 *     }
 * }
 * </PRE>
 * 
 * @author Ricardo Artur Staroski
 * @param <O> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
 * @param <D> Tipo de dado da lista de detalhes caso o objeto n&atilde;o atenda as regras.
 */
public abstract class Rule<O, D> implements Specification<O, D> {

    /**
     * Regra interna que realiza a opera&ccedil;&atilde;o <B>E</B> entre duas especifica&ccedil;&otilde;es.
     * 
     * @param <X> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
     * @param <Y> Tipo de dado da lista de detalhes caso o objeto n&atilde;o atenda as regras.
     */
    private static final class And<X, Y> extends Rule<X, Y> {

        /**
         * A primeira especifica&ccedil;&atilde;o desta regra.
         */
        private final Specification<X, Y> spec1;
        /**
         * A segunda especifica&ccedil;&atilde;o desta regra.
         */
        private final Specification<X, Y> spec2;

        /**
         * Cria uma nova regra.
         * 
         * @param spec1 A primeira especifica&ccedil;&atilde;o desta regra.
         * @param spec2 A segunda especifica&ccedil;&atilde;o desta regra.
         */
        protected And(final Specification<X, Y> spec1, final Specification<X, Y> spec2) {
            this.spec1 = spec1;
            this.spec2 = spec2;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfiedBy(final X object, final Collection<Y> details) {
            // precisa ser '&' ao inv&eacute;s de '&&' de forma a preencher os detalhes das duas especifica&ccedil;&otilde;es
            return spec1.isSatisfiedBy(object, details) & spec2.isSatisfiedBy(object, details);
        }
    }

    /**
     * Especializa&ccedil;&atilde;o de {@link LinkedList} que n&atilde;o aceita duplicatas.
     * 
     * @param <X> Tipo de dado da lista.
     */
    @SuppressWarnings("serial")
    private static final class Details<X> extends LinkedList<X> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(final int index, final X element) {
            if (!contains(element)) {
                super.add(index, element);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(final X e) {
            if (!contains(e)) {
                return super.add(e);
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(final Collection<? extends X> c) {
            return super.addAll(getNotContained(c));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(final int index, final Collection<? extends X> c) {
            return super.addAll(index, getNotContained(c));
        }

        /**
         * Dado uma cole&ccedil;&atilde;o, obt&eacute;m os elementos desta cole&ccedil;&atilde;o que n&atilde;o inclusos na lista atual.
         * 
         * @param c Um cole&ccedil;&atilde;o qualquer
         * @return Os elementos da cole&ccedil;&atilde;o que n&atilde;o est&atilde;o inclusos na lista atual.
         */
        private Collection<X> getNotContained(final Collection<? extends X> c) {
            final List<X> elements = new LinkedList<X>();
            for (final X e : c) {
                if (!contains(e)) {
                    elements.add(e);
                }
            }
            return elements;
        }

    }

    /**
     * Regra interna que realiza criada a partir de uma especifica&ccedil;&atilde;o qualquer.
     * 
     * @param <X> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
     * @param <Y> Tipo de dado da lista de detalhes caso o objeto n&atilde;o atenda as regras.
     */
    private static final class Is<X, Y> extends Rule<X, Y> {

        /**
         * A especifica&ccedil;&atilde;o desta regra.
         */
        private final Specification<X, Y> spec;

        /**
         * Cria uma nova regra.
         * 
         * @param spec A especifica&ccedil;&atilde;o desta regra.
         */
        protected Is(final Specification<X, Y> spec) {
            this.spec = spec;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfiedBy(final X object, final Collection<Y> details) {
            return spec.isSatisfiedBy(object, details);
        }
    }

    /**
     * Regra interna que realiza a opera&ccedil;&atilde;o <B>N&Atilde;O</B> de uma especifica&ccedil;&atilde;o qualquer.
     * 
     * @param <X> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
     * @param <Y> Tipo de dado da lista de detalhes caso o objeto n&atilde;o atenda as regras.
     */
    private static final class Not<X, Y> extends Rule<X, Y> {

        /**
         * A especifica&ccedil;&atilde;o desta regra.
         */
        private final Specification<X, Y> spec;

        /**
         * Cria uma nova regra.
         * 
         * @param spec A primeira especifica&ccedil;&atilde;o desta regra.
         */
        protected Not(final Specification<X, Y> spec) {
            this.spec = spec;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfiedBy(final X object, final Collection<Y> details) {
            return !spec.isSatisfiedBy(object, details);
        }
    }

    /**
     * Regra interna que realiza a opera&ccedil;&atilde;o <B>OU</B> entre duas especifica&ccedil;&otilde;es.
     * 
     * @param <X> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
     * @param <Y> Tipo de dado da lista de detalhes caso o objeto n&atilde;o atenda as regras.
     */
    private static final class Or<X, Y> extends Rule<X, Y> {

        /**
         * A primeira especifica&ccedil;&atilde;o desta regra.
         */
        private final Specification<X, Y> spec1;
        /**
         * A segunda especifica&ccedil;&atilde;o desta regra.
         */
        private final Specification<X, Y> spec2;

        /**
         * Cria uma nova regra.
         * 
         * @param spec1 A primeira especifica&ccedil;&atilde;o desta regra.
         * @param spec2 A segunda especifica&ccedil;&atilde;o desta regra.
         */
        protected Or(final Specification<X, Y> spec1, final Specification<X, Y> spec2) {
            this.spec1 = spec1;
            this.spec2 = spec2;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfiedBy(final X object, final Collection<Y> details) {
            // precisa ser '|' ao inv&eacute;s de '||' de forma a preencher os detalhes das duas especifica&ccedil;&otilde;es
            return spec1.isSatisfiedBy(object, details) | spec2.isSatisfiedBy(object, details);
        }
    }

    /**
     * Obt&eacute;m uma regra a partir de uma simples especifica&ccedil;&atilde;o, ou seja, informa-se uma implementa&ccedil;&atilde;o de {@link Specification} correspondente a regra
     * desejada e obt&eacute;m-se uma implementa&ccedil;&atilde;o de {@link Rule}.
     * 
     * @param <X> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
     * @param <Y> Tipo de dado da lista de detalhes caso o objeto n&atilde;o atenda as regras.
     * @param spec A {@link Specification} da qual ser&aacute; criada uma {@link Rule}.
     * @return A {@link Rule} que encapsula a {@link Specification} informada.
     */
    public static final <X, Y> Rule<X, Y> from(final Specification<X, Y> spec) {
        return new Is<X, Y>(spec);
    }

    /**
     * Detalhes do motivo de um objeto nao atender a esta regra.
     */
    private Details<D> details;

    /**
     * Construtor privado, s&oacute; &eacute; acessivel pelas <I>inner classes</I> {@link Is}, {@link Not}, {@link And} e {@link Or}.
     */
    private Rule() {
        details = new Details<D>();
    }

    /**
     * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra <B>E</B> a especifica&ccedil;&atilde;o informada tamb&eacute;m
     * atenderem ao mesmo objeto.<BR>
     * <BR>
     * A opera&ccedil;&atilde;o <I>AND</I> possui o comportamento abaixo:
     * 
     * <PRE>
     * objetoAtende = objetoAtendeRegra1 &amp; objetoAtendeRegra2
     * </PRE>
     * 
     * @param spec A {@link Specification} a ser adicionada a esta regra.
     * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual e a especifica&ccedil;&atilde;o informada tamb&eacute;m atenderem a este objeto.
     */
    public final Rule<O, D> and(final Specification<O, D> spec) {
        return new And<O, D>(this, spec);
    }

    /**
     * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra atender <B>E</B> a especifica&ccedil;&atilde;o informada
     * <B>N&Atilde;O</B> atender ao mesmo objeto.<BR>
     * <BR>
     * A opera&ccedil;&atilde;o <I>AND NOT</I> possui o comportamento abaixo:
     * 
     * <PRE>
     * objetoAtende = objetoAtendeRegra1 &amp; !objetoAtendeRegra2
     * </PRE>
     * 
     * @param spec A {@link Specification} a ser adicionada a esta regra.
     * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual atender e a especifica&ccedil;&atilde;o informada n&atilde;o atender a este
     *         objeto.
     */
    public final Rule<O, D> andNot(final Specification<O, D> spec) {
        return new And<O, D>(this, new Is<O, D>(spec).not());
    }

    /**
     * Obt&eacute;m os detalhes caso um objeto n&atilde;o atenda a esta regra.<BR>
     * <BR>
     * <B>Observa&ccedil;&atilde;o:</B> Uma regra somente &eacute; populada com detalhes durante a execu&ccedil;&atilde;o do m&eacute;todo {@link #isSatisfiedBy(Object)}. Caso seja invocado o
     * m&eacute;todo {@link #isSatisfiedBy(Object, Collection)}, os detalhes estar&atilde;o na pr&oacute;pria cole&ccedil;&atilde;o passada como par&acirc;metro.
     * 
     * @return Uma cole&ccedil;&atilde;o vazia caso o objeto atenda a esta regra ou uma cole&ccedil;&atilde;o contendo contendo os detalhes do motivo de ele n&atilde;o atender a regra.
     */
    public final List<D> getDetails() {
        return Collections.unmodifiableList(details);
    }

    /**
     * Verifica se o objeto informado atende, ou n&atilde;o, a especifica&ccedil;&atilde;o desta regra.<BR>
     * <BR>
     * <B>Observa&ccedil;&atilde;o:</B> Ap&oacute;s a execu&ccedil;&atilde;o deste m&eacute;todo, caso o objeto informado n&atilde;o atenda a regra, os detalhes poder&atilde;o ser obtidos atrav&eacute;s do m&eacute;todo
     * {@link #getDetails()}.
     * 
     * @param object O objeto a ser verificado.
     * @return <code>true</code> se o objeto atender a especifica&ccedil;&atilde;o e <code>false</code> caso contr&aacute;rio.
     */
    public final boolean isSatisfiedBy(final O object) {
        details = new Details<D>();
        return isSatisfiedBy(object, details);
    }

    /**
     * {@inheritDoc}<BR>
     * <BR>
     * <B>Observa&ccedil;&atilde;o:</B> Este m&eacute;todo somente manipula a cole&ccedil;&atilde;o passada por par&acirc;metro, n&atilde;o alterando o retorno do m&eacute;todo {@link #getDetails()}, de
     * forma que, ap&oacute;s a execu&ccedil;&atilde;o dele, os detalhes estar&atilde;o contidos no pr&oacute;prio parametro.
     */
    public abstract boolean isSatisfiedBy(O object, Collection<D> details);

    /**
     * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra <B>N&Atilde;O</B> atender ao mesmo objeto.<BR>
     * <BR>
     * A opera&ccedil;&atilde;o <I>NOT</I> possui o comportamento abaixo:
     * 
     * <PRE>
     * objetoAtende = !objetoAtendeRegra
     * </PRE>
     * 
     * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual n&atilde;o atender a este objeto.
     */
    public final Rule<O, D> not() {
        return new Not<O, D>(this);
    }

    /**
     * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra <B>OU</B> a especifica&ccedil;&atilde;o informada tamb&eacute;m
     * atenderem ao mesmo objeto.<BR>
     * <BR>
     * A opera&ccedil;&atilde;o <I>OR</I> possui o comportamento abaixo:
     * 
     * <PRE>
     * objetoAtende = objetoAtendeRegra1 | objetoAtendeRegra2
     * </PRE>
     * 
     * @param spec A {@link Specification} a ser adicionada a esta regra.
     * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual ou a especifica&ccedil;&atilde;o informada tamb&eacute;m atenderem a este objeto.
     */
    public final Rule<O, D> or(final Specification<O, D> spec) {
        return new Or<O, D>(this, spec);
    }

    /**
     * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra atender <B>OU</B> a especifica&ccedil;&atilde;o informada
     * <B>N&Atilde;O</B>
     * atender ao mesmo objeto. <BR>
     * <BR>
     * A opera&ccedil;&atilde;o <I>OR NOT</I> possui o comportamento abaixo:
     * 
     * <PRE>
     * objetoAtende = objetoAtendeRegra1 | !objetoAtendeRegra2
     * </PRE>
     * 
     * @param spec A {@link Specification} a ser adicionada a esta regra.
     * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual atender ou a especifica&ccedil;&atilde;o informada n&atilde;o atender a este
     *         objeto.
     */
    public final Rule<O, D> orNot(final Specification<O, D> spec) {
        return new Or<O, D>(this, new Is<O, D>(spec).not());
    }
}
