package br.com.staroski.rules;

import java.util.*;

/**
 * Esta classe &eacute; repons&aacute;vel por criar regras a partir de simples especifica&ccedil;&otilde;es.<BR>
 * <BR>
 * <I> Para obter instancias desta classe, utilize o m&eacute;todo {@link #create(Specification)}.</I><BR>
 * <BR>
 * Atrav&eacute;s de uma inst&acirc;ncia de {@link Rule}, &eacute; possivel montar express&otilde;es complexas associando outras {@link Specification}s ou {@link Rule}s atrav&eacute;s
 * dos seguintes m&eacute;todos:<BR>
 * - {@link #and(Specification)}<BR>
 * - {@link #andNot(Specification)}<BR>
 * - {@link #or(Specification)}<BR>
 * - {@link #orNot(Specification)}<BR>
 * - {@link #not()} <BR>
 * Outra caracter&iacute;stica interessante desta classe &eacute; o m&eacute;todo {@link #getModifiableDetails()} que obt&eacute;m detalhes do motivo de um objeto atender, ou n&atilde;o, 
 * dependendo da situa&ccedil;&atilde;o, a especifica&ccedil;&atilde;o da regra.<BR>
 * <BR>
 * <B><I>Veja o exemplo para entender como utiliza-la:</I></B><BR>
 * <BR>
 * Uma <tt>Pessoa</tt> tem os seguintes atributos: <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt>.<BR>
 * Para validar uma <tt>Pessoa</tt>, foram definidas as seguintes regras:<BR>
 * - O nome n&atilde;o pode ser <code>null</code> e deve come&ccedil;ar com uma letra mai&uacute;scula e ter uma ou mais letras min&uacute;sculas;<BR>
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
 * Rule&lt;Pessoa, String&gt; nome = Rule.create(new Nome());
 * Rule&lt;Pessoa, String&gt; idade = Rule.create(new Idade());
 * Rule&lt;Pessoa, String&gt; sexo = Rule.create(new Sexo());
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
 * @param <T> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
 */
public abstract class Rule<T> {

	/**
	 * Obt&eacute;m uma regra a partir de uma simples especifica&ccedil;&atilde;o, ou seja, informa-se uma implementa&ccedil;&atilde;o de {@link Specification} correspondente a regra
	 * desejada e obt&eacute;m-se uma implementa&ccedil;&atilde;o de {@link Rule}.
	 * 
	 * @param <X> Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
	 * @param spec A {@link Specification} da qual ser&aacute; criada uma {@link Rule}.
	 * @return A {@link Rule} que encapsula a {@link Specification} informada.
	 */
	public static final <T> Rule<T> create(final Specification<T> spec) {
		return new ConcreteRule<T>(spec);
	}

	/**
	 * Detalhes do motivo de um objeto nao atender a esta regra.
	 */
	private Details<String> details;

	/**
	 * Construtor privado, s&oacute; &eacute; acessivel pelas <I>inner classes</I> {@link UnaryRule}, {@link Not}, {@link And} e {@link Or}.
	 */
	protected Rule() {
		details = new Details<String>();
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
	 * @param rule A {@link Rule} a ser adicionada a esta regra.
	 * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual e a especifica&ccedil;&atilde;o informada tamb&eacute;m atenderem a este objeto.
	 */
	public final And<T> and(final Rule<T> rule) {
		return new And<T>(this, rule);
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
	public final And<T> and(final Specification<T> spec) {
		return and(new ConcreteRule<T>(spec));
	}

	/**
	 * Obt&eacute;m os detalhes caso um objeto n&atilde;o atenda a esta regra.<BR>
	 * <BR>
	 * <B>Observa&ccedil;&atilde;o:</B> Uma regra somente &eacute; populada com detalhes durante a execu&ccedil;&atilde;o do m&eacute;todo {@link #isSatisfiedBy(Object)}. Caso seja invocado o
	 * m&eacute;todo {@link #isSatisfiedBy(Object, Collection)}, os detalhes estar&atilde;o na pr&oacute;pria cole&ccedil;&atilde;o passada como par&acirc;metro.
	 * 
	 * @return Uma cole&ccedil;&atilde;o vazia caso o objeto atenda a esta regra ou uma cole&ccedil;&atilde;o contendo contendo os detalhes do motivo de ele n&atilde;o atender a regra.
	 */
	public final List<String> getDetails() {
		return Collections.unmodifiableList(details);
	}

	/**
	 * Verifica se o objeto informado atende, ou n&atilde;o, a especifica&ccedil;&atilde;o desta regra.<BR>
	 * <BR>
	 * <B>Observa&ccedil;&atilde;o:</B> Ap&oacute;s a execu&ccedil;&atilde;o deste m&eacute;todo, caso o objeto informado n&atilde;o atenda a regra, os detalhes poder&atilde;o ser obtidos atrav&eacute;s do m&eacute;todo
	 * {@link #getModifiableDetails()}.
	 * 
	 * @param object O objeto a ser verificado.
	 * @return <code>true</code> se o objeto atender a especifica&ccedil;&atilde;o e <code>false</code> caso contr&aacute;rio.
	 */
	public abstract boolean isSatisfiedBy(final T object);

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
	public final Not<T> not() {
		return new Not<T>(this);
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
	public final Or<T> or(final Rule<T> rule) {
		return new Or<T>(this, rule);
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
	public final Or<T> or(final Specification<T> spec) {
		return or(new ConcreteRule<T>(spec));
	}

	/**
	 * Obt&eacute;m os detalhes caso um objeto n&atilde;o atenda a esta regra.<BR>
	 * <BR>
	 * <B>Observa&ccedil;&atilde;o:</B> Uma regra somente &eacute; populada com detalhes durante a execu&ccedil;&atilde;o do m&eacute;todo {@link #isSatisfiedBy(Object)}. Caso seja invocado o
	 * m&eacute;todo {@link #isSatisfiedBy(Object, Collection)}, os detalhes estar&atilde;o na pr&oacute;pria cole&ccedil;&atilde;o passada como par&acirc;metro.
	 * 
	 * @return Uma cole&ccedil;&atilde;o vazia caso o objeto atenda a esta regra ou uma cole&ccedil;&atilde;o contendo contendo os detalhes do motivo de ele n&atilde;o atender a regra.
	 */
	protected final List<String> getModifiableDetails() {
		return details;
	}
}