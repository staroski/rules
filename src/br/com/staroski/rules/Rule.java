package br.com.staroski.rules;

import java.util.*;

/**
 * Esta classe &eacute; repons&aacute;vel por criar {@link Rule regras} reaproveit&aacute;veis a partir de simples {@link Specification especifica&ccedil;&otilde;es.<BR>
 * <BR> <I> Para obter inst&acirc;ncias desta classe, utilize o m&eacute;todo {@link #create(Specification)}.</I><BR>
 * <BR>
 * Atrav&eacute;s de uma inst&acirc;ncia de {@link Rule}, &eacute; poss&iacute;vel montar express&otilde;es complexas associando outras {@link Specification
 * expecifica&ccedil;&otilde;es} ou {@link Rule regras} atrav&eacute;s dos seguintes m&eacute;todos:<BR>
 * - {@link #and(Specification)}<BR>
 * - {@link #or(Specification)}<BR>
 * - {@link #not()} <BR>
 * Outra caracter&iacute;stica interessante desta classe &eacute; o m&eacute;todo {@link #getDetails()} que obt&eacute;m detalhes do motivo de um objeto
 * atender, ou n&atilde;o, dependendo da situa&ccedil;&atilde;o, a {@link Specification especifica&ccedil;&atilde;o} da regra.<BR>
 * <BR>
 * <B><I>Veja o exemplo para entender como utiliza-la:</I></B><BR>
 * <BR>
 * <BR>
 * Uma <tt>Pessoa</tt> tem os seguintes atributos: <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt>.<BR>
 * Para validar uma <tt>Pessoa</tt>, foram definidas as seguintes regras:<BR>
 * - O nome n&atilde;o deve come&ccedil;ar com uma letra mai&uacute;scula e ter uma ou mais letras min&uacute;sculas;<BR>
 * - A idade n&atilde;o pode ser negativa;<BR>
 * - O sexo s&oacute; pode ser <code>'M'</code> ou <code>'F'</code>.<BR>
 * <BR>
 * <B>Primeiro definimos a classe <tt>Pessoa</tt> conforme abaixo:</B>
 * 
 * <PRE>
 * class Pessoa {
 * 
 * 	String nome;
 * 	int idade;
 * 	char sexo;
 * 
 * 	Pessoa(String nome, int idade, char sexo) {
 * 		this.nome = nome;
 * 		this.idade = idade;
 * 		this.sexo = sexo;
 * 	}
 * }
 * </PRE>
 * 
 * <B>Agora criamos tr&ecirc;s especifica&ccedil;&otilde;es distintas para as regras que validam <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt> da
 * <tt>Pessoa</tt>.</B>
 * 
 * <PRE>
 * import br.com.staroski.rules.*;
 * 
 * // Especifica&amp;ccedil&amp;atildeo da regra que valida o nome de uma Pessoa
 * class Nome implements Specification&lt;Pessoa&gt; {
 * 
 * 	public void verify(Pessoa pessoa) throws UnattendedException {
 * 		if (!pessoa.nome.matches(&quot;[A-Z]{1}[a-z]+&quot;)) {
 * 			throw new UnattendedException(&quot;Nome precisa come&amp;ccedilar com letra mai&amp;uacutescula e ter pelo menos duas letras&quot;);
 * 		}
 * 	}
 * }
 * </PRE>
 * 
 * <PRE>
 * import br.com.staroski.rules.*;
 * 
 * // Especifica&amp;ccedil&amp;atildeo da regra que valida a idade de Pessoa
 * class Idade implements Specification&lt;Pessoa&gt; {
 * 
 * 	public void verify(Pessoa pessoa) throws UnattendedException {
 * 		if (pessoa.idade &lt; 0) {
 * 			throw new UnattendedException(&quot;Idade n&amp;atildeo pode ser negativa&quot;);
 * 		}
 * 	}
 * }
 * </PRE>
 * 
 * <PRE>
 * import br.com.staroski.rules.*;
 * 
 * // Especifica&amp;ccedil&amp;atildeo da regra que valida o sexo de uma Pessoa
 * class Sexo implements Specification&lt;Pessoa&gt; {
 * 
 * 	public void verify(Pessoa pessoa) throws UnattendedException {
 * 		switch (pessoa.sexo) {
 * 			case 'M':
 * 			case 'F':
 * 				return;
 * 			default:
 * 				throw new UnattendedException(&quot;Sexo s&amp;oacute pode ser 'M' ou 'F'&quot;);
 * 		}
 * 	}
 * }
 * </PRE>
 * 
 * <B>Agora ja temos a classe <tt>Pessoa</tt> e as especifica&ccedil;&otilde;es das regras para <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt> criadas.<BR>
 * Podemos ent&atilde;o utilizar a classe <tt>Rule</tt> para validar instancias de <tt>Pessoa</tt> de diversas formas, por exemplo:</B>
 * 
 * <PRE>
 * import br.com.staroski.rules.*;
 * 
 * public class Exemplo {
 * 
 * 	public static void main(String[] args) {
 * 		// instanciamos as regras a partir das especifica&amp;ccedil&amp;otildees 
 * 		Rule&lt;Pessoa&gt; nome = Rule.create(new Nome());
 * 		Rule&lt;Pessoa&gt; idade = Rule.create(new Idade());
 * 		Rule&lt;Pessoa&gt; sexo = Rule.create(new Sexo());
 * 
 * 		// criamos uma pessoa com nome, idade e sexo validos
 * 		Pessoa pessoa = new Pessoa(&quot;Fulano&quot;, 30, 'M');
 * 		// criamos uma regra s&amp;oacute que corresponde &amp;agraves tr&amp;ecircs regras: nome, idade e sexo
 * 		// e validamos com um &amp;uacutenico if
 * 		if (nome.and(idade).and(sexo).isSatisfiedBy(pessoa)) {
 * 			System.out.println(&quot;Teste 1&quot;);
 * 			System.out.println(&quot;O nome, idade e sexo da pessoa atendem as regras\n&quot;);
 * 		}
 * 
 * 		// criamos uma pessoa com nome, idade e sexo inv&amp;aacutelidos
 * 		pessoa = new Pessoa(&quot;FuLaNo&quot;, -1, 'S');
 * 		// criamos uma regra s&amp;oacute que corresponde &amp;agraves tr&amp;ecircs regras: nome, idade e sexo
 * 		// armazenamos essa regra numa vari&amp;aacutevel
 * 		Rule&lt;Pessoa&gt; regra = nome.and(idade).and(sexo);
 * 		// assim, validamos as tr&amp;ecircs regras, com um &amp;uacutenico if
 * 		if (regra.not().isSatisfiedBy(pessoa)) {
 * 			System.out.println(&quot;Teste 2&quot;);
 * 			System.out.println(&quot;A pessoa n&amp;atildeo atendeu &amp;agraves seguintes regras:&quot;);
 * 			// se a pessoa n&amp;atildeo atendeu &amp;agraves regras,
 * 			// usamos a vari&amp;aacutevel declarada para obter os detalhes
 * 			for (String detalhe : regra.getDetails()) {
 * 				System.out.println(detalhe);
 * 			}
 * 		}
 * 	}
 * }
 * </PRE>
 * 
 * Executando a classe <tt>Exemplo</tt> acima, obteremos a seguinte sa&iacute;da:<BR>
 * 
 * <PRE>
 * Teste 1
 * O nome, idade e sexo da pessoa atendem as regras
 * 
 * Teste 2
 * A pessoa n&atilde;o atendeu as seguintes regras:
 * Nome precisa come&ccedil;ar com letra mai&uacute;scula e ter pelo menos duas letras
 * Idade n&atilde;o pode ser negativa
 * Sexo s&oacute; pode ser 'M' ou 'F'
 * </PRE>
 * 
 * Assim podemos observar como o uso de {@link br.com.staroski.rules.Rule regras} reutiliz&aacute;veis podem facilitar a implementa&ccedil;&atilde;o e
 * manuten&ccedil;&atilde;o de valida&ccedil;&otilde;es nos mais variados sistemas.
 * 
 * @author Ricardo Artur Staroski
 * 
 * @param <T>
 *            Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
 */
public abstract class Rule<T> {

	/**
	 * Obt&eacute;m uma regra a partir de uma simples especifica&ccedil;&atilde;o, ou seja, informa-se uma implementa&ccedil;&atilde;o de {@link Specification}
	 * correspondente a regra desejada e obt&eacute;m-se uma implementa&ccedil;&atilde;o de {@link Rule}.
	 * 
	 * @param <X>
	 *            Tipo de dado do objeto que pode ou n&atilde;o atender as regras.
	 * @param spec
	 *            A {@link Specification} da qual ser&aacute; criada uma {@link Rule}.
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
	 * Construtor protegido.
	 */
	protected Rule() {
		details = new Details<String>();
	}

	/**
	 * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra <B>E</B> a especifica&ccedil;&atilde;o
	 * informada tamb&eacute;m atenderem ao mesmo objeto.<BR>
	 * <BR>
	 * A opera&ccedil;&atilde;o <I>AND</I> possui o comportamento abaixo:
	 * 
	 * <PRE>
	 * objetoAtende = objetoAtendeRegra1 &amp;&amp; objetoAtendeRegra2
	 * </PRE>
	 * 
	 * @param rule
	 *            A {@link Rule} a ser adicionada a esta regra.
	 * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual e a especifica&ccedil;&atilde;o informada tamb&eacute;m
	 *         atenderem a este objeto.
	 */
	public final Rule<T> and(final Rule<T> rule) {
		return new And<T>(this, rule);
	}

	/**
	 * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra <B>E</B> a especifica&ccedil;&atilde;o
	 * informada tamb&eacute;m atenderem ao mesmo objeto.<BR>
	 * <BR>
	 * A opera&ccedil;&atilde;o <I>AND</I> possui o comportamento abaixo:
	 * 
	 * <PRE>
	 * objetoAtende = objetoAtendeRegra1 &amp;&amp; objetoAtendeRegra2
	 * </PRE>
	 * 
	 * @param spec
	 *            A {@link Specification} a ser adicionada a esta regra.
	 * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual e a especifica&ccedil;&atilde;o informada tamb&eacute;m
	 *         atenderem a este objeto.
	 */
	public final Rule<T> and(final Specification<T> spec) {
		return and(create(spec));
	}

	/**
	 * Obt&eacute;m os detalhes caso um objeto n&atilde;o atenda a esta regra.<BR>
	 * <BR>
	 * <B>Observa&ccedil;&atilde;o:</B> Uma regra somente &eacute; populada com detalhes durante a execu&ccedil;&atilde;o do m&eacute;todo
	 * {@link #isSatisfiedBy(Object)}. Caso seja invocado o m&eacute;todo {@link #isSatisfiedBy(Object, Collection)}, os detalhes estar&atilde;o na
	 * pr&oacute;pria cole&ccedil;&atilde;o passada como par&acirc;metro.
	 * 
	 * @return Uma cole&ccedil;&atilde;o vazia caso o objeto atenda a esta regra ou uma cole&ccedil;&atilde;o contendo contendo os detalhes do motivo de ele
	 *         n&atilde;o atender a regra.
	 */
	public final List<String> getDetails() {
		// ninguém de fora pode modificar essa lista
		return Collections.unmodifiableList(details);
	}

	/**
	 * Verifica se o objeto informado atende, ou n&atilde;o, a especifica&ccedil;&atilde;o desta regra.<BR>
	 * <BR>
	 * <B>Observa&ccedil;&atilde;o:</B> Ap&oacute;s a execu&ccedil;&atilde;o deste m&eacute;todo, caso o objeto informado n&atilde;o atenda a regra, os detalhes
	 * poder&atilde;o ser obtidos atrav&eacute;s do m&eacute;todo {@link #getModifiableDetails()}.
	 * 
	 * @param object
	 *            O objeto a ser verificado.
	 * @return <code>true</code> se o objeto atender a especifica&ccedil;&atilde;o e <code>false</code> caso contr&aacute;rio.
	 */
	public abstract boolean isSatisfiedBy(final T object);

	/**
	 * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra <B>N&Atilde;O</B> atender ao mesmo
	 * objeto.<BR>
	 * <BR>
	 * A opera&ccedil;&atilde;o <I>NOT</I> possui o comportamento abaixo:
	 * 
	 * <PRE>
	 * objetoAtende = !objetoAtendeRegra
	 * </PRE>
	 * 
	 * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual n&atilde;o atender a este objeto.
	 */
	public final Rule<T> not() {
		// se já está negado
		if (this instanceof Not) {
			// devolve a regra original
			return ((Not<T>) this).getRule();
		}
		// senão nega a regra
		return new Not<T>(this);
	}

	/**
	 * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra <B>OU</B> a especifica&ccedil;&atilde;o
	 * informada tamb&eacute;m atenderem ao mesmo objeto.<BR>
	 * <BR>
	 * A opera&ccedil;&atilde;o <I>OR</I> possui o comportamento abaixo:
	 * 
	 * <PRE>
	 * objetoAtende = objetoAtendeRegra1 || objetoAtendeRegra2
	 * </PRE>
	 * 
	 * @param spec
	 *            A {@link Specification} a ser adicionada a esta regra.
	 * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual ou a especifica&ccedil;&atilde;o informada tamb&eacute;m
	 *         atenderem a este objeto.
	 */
	public final Rule<T> or(final Rule<T> rule) {
		return new Or<T>(this, rule);
	}

	/**
	 * Cria uma nova regra que somente atender&aacute; a um objeto qualquer se a especifica&ccedil;&atilde;o desta regra <B>OU</B> a especifica&ccedil;&atilde;o
	 * informada tamb&eacute;m atenderem ao mesmo objeto.<BR>
	 * <BR>
	 * A opera&ccedil;&atilde;o <I>OR</I> possui o comportamento abaixo:
	 * 
	 * <PRE>
	 * objetoAtende = objetoAtendeRegra1 || objetoAtendeRegra2
	 * </PRE>
	 * 
	 * @param spec
	 *            A {@link Specification} a ser adicionada a esta regra.
	 * @return Uma {@link Rule} que atende a um objeto se a especifica&ccedil;&atilde;o da regra atual ou a especifica&ccedil;&atilde;o informada tamb&eacute;m
	 *         atenderem a este objeto.
	 */
	public final Rule<T> or(final Specification<T> spec) {
		return or(create(spec));
	}

	/**
	 * Obt&eacute;m os detalhes caso um objeto n&atilde;o atenda a esta regra.<BR>
	 * <BR>
	 * <B>Observa&ccedil;&atilde;o:</B> Uma regra somente &eacute; populada com detalhes durante a execu&ccedil;&atilde;o do m&eacute;todo
	 * {@link #isSatisfiedBy(Object)}. Caso seja invocado o m&eacute;todo {@link #isSatisfiedBy(Object, Collection)}, os detalhes estar&atilde;o na
	 * pr&oacute;pria cole&ccedil;&atilde;o passada como par&acirc;metro.
	 * 
	 * @return Uma cole&ccedil;&atilde;o vazia caso o objeto atenda a esta regra ou uma cole&ccedil;&atilde;o contendo contendo os detalhes do motivo de ele
	 *         n&atilde;o atender a regra.
	 */
	protected final List<String> getModifiableDetails() {
		return details;
	}
}