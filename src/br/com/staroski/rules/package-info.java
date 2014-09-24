/**
 * Neste pacote encontram-se classes que auxiliam na cria&ccedil;&atilde;o de {@link br.com.staroski.rules.Rule regras} que podem ser reutilizadas.<BR>
 * <BR>
 * <B><I>Para entender melhor, veja o exemplo abaixo:</I></B><BR>
 * <BR>
 * Uma <tt>Pessoa</tt> tem os seguintes atributos: <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt>.<BR>
 * Para validar uma <tt>Pessoa</tt>, foram definidas as seguintes regras:<BR>
 * - O nome n&atilde;o deve come&ccedil;ar com uma letra mai&uacute;scula e ter uma ou mais letras min&uacute;sculas;<BR>
 * - A idade n&atilde;o pode ser negativa;<BR>
 * - O sexo s&oacute; pode ser <code>'M'</code> ou <code>'F'</code>.<BR>
 * <BR>
 * <B>Primeiro definimos a classe <tt>Pessoa</tt> conforme abaixo:</B>
 * <PRE>
 *	class Pessoa {
 *		 
 *	    String nome;
 *	    int idade;
 *	    char sexo;
 *	
 *	    Pessoa(String nome, int idade, char sexo) {
 *	        this.nome = nome;
 *	        this.idade = idade;
 *	        this.sexo = sexo;
 *	    }
 *	}
 * </PRE>
 * 
 * <B>Agora criamos tr&ecirc;s especifica&ccedil;&otilde;es distintas para as regras que validam <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt> da <tt>Pessoa</tt>.</B>
 * <PRE>
 *	import br.com.staroski.rules.*;
 *	
 *	// Especifica&ccedil;&atilde;o da regra que valida o nome de uma Pessoa
 *	class Nome implements Specification<Pessoa> {
 *	
 *		public void verify(Pessoa pessoa) throws UnattendedException {
 *			if (!pessoa.nome.matches("[A-Z]{1}[a-z]+")) {
 *				throw new UnattendedException("Nome precisa come&ccedil;ar com letra mai&uacute;scula e ter pelo menos duas letras");
 *			}
 *		}
 *	}
 * </PRE>
 * 
 * <PRE>
 *	import br.com.staroski.rules.*;
 *	
 *	// Especifica&ccedil;&atilde;o da regra que valida a idade de Pessoa
 *	class Idade implements Specification<Pessoa> {
 *	
 *		public void verify(Pessoa pessoa) throws UnattendedException {
 *			if (pessoa.idade < 0) {
 *				throw new UnattendedException("Idade n&atilde;o pode ser negativa");
 *			}
 *		}
 *	}
 * </PRE>
 * 
 * <PRE>
 *	import br.com.staroski.rules.*;
 *	
 *	// Especifica&ccedil;&atilde;o da regra que valida o sexo de uma Pessoa
 *	class Sexo implements Specification<Pessoa> {
 *	
 *		public void verify(Pessoa pessoa) throws UnattendedException {
 *			switch (pessoa.sexo) {
 *				case 'M':
 *				case 'F':
 *					return;
 *				default:
 *					throw new UnattendedException("Sexo s&oacute; pode ser 'M' ou 'F'");
 *			}
 *		}
 *	}
 * </PRE>
 * 
 * <B>Agora ja temos a classe <tt>Pessoa</tt> e as especifica&ccedil;&otilde;es das regras para <tt>nome</tt>, <tt>idade</tt> e <tt>sexo</tt> criadas.<BR>
 * Podemos ent&atilde;o utilizar a classe <tt>Rule</tt> para validar instancias de <tt>Pessoa</tt> de diversas formas, por exemplo:</B>
 * <PRE>
 *	import br.com.staroski.rules.*;
 *	
 *	public class Exemplo {
 *	
 *		public static void main(String[] args) {
 *			// instanciamos as regras a partir das especifica&ccedil;&otilde;es 
 *			Rule<Pessoa> nome = Rule.create(new Nome());
 *			Rule<Pessoa> idade = Rule.create(new Idade());
 *			Rule<Pessoa> sexo = Rule.create(new Sexo());
 *	
 *			// criamos uma pessoa com nome, idade e sexo validos
 *			Pessoa pessoa = new Pessoa("Fulano", 30, 'M');
 *			// criamos uma regra s&oacute; que corresponde &agrave;s tr&ecirc;s regras: nome, idade e sexo
 *			// e validamos com um &uacute;nico if
 *			if (nome.and(idade).and(sexo).isSatisfiedBy(pessoa)) {
 *				System.out.println("Teste 1");
 *				System.out.println("O nome, idade e sexo da pessoa atendem as regras\n");
 *			}
 *	
 *			// criamos uma pessoa com nome, idade e sexo inv&aacute;lidos
 *			pessoa = new Pessoa("FuLaNo", -1, 'S');
 *			// criamos uma regra s&oacute; que corresponde &agrave;s tr&ecirc;s regras: nome, idade e sexo
 *			// armazenamos essa regra numa vari&aacute;vel
 *			Rule<Pessoa> regra = nome.and(idade).and(sexo);
 *			// assim, validamos as tr&ecirc;s regras, com um &uacute;nico if
 *			if (regra.not().isSatisfiedBy(pessoa)) {
 *				System.out.println("Teste 2");
 *				System.out.println("A pessoa n&atilde;o atendeu &agrave;s seguintes regras:");
 *				// se a pessoa n&atilde;o atendeu &agrave;s regras,
 *				// usamos a vari&aacute;vel declarada para obter os detalhes
 *				for (String detalhe : regra.getDetails()) {
 *					System.out.println(detalhe);
 *				}
 *			}
 *		}
 *	}
 * </PRE>
 * 
 * Executando a classe <tt>Exemplo</tt> acima, obteremos a seguinte sa&iacute;da:<BR>
 * <PRE>
 *	Teste 1
 *	O nome, idade e sexo da pessoa atendem as regras
 *	
 *	Teste 2
 *	A pessoa n&atilde;o atendeu as seguintes regras:
 *	Nome precisa come&ccedil;ar com letra mai&uacute;scula e ter pelo menos duas letras
 *	Idade n&atilde;o pode ser negativa
 *	Sexo s&oacute; pode ser 'M' ou 'F'
 * </PRE>
 * 
 * Assim podemos observar como o uso de {@link br.com.staroski.rules.Rule regras} reutiliz&aacute;veis podem facilitar a implementa&ccedil;&atilde;o e manuten&ccedil;&atilde;o de valida&ccedil;&otilde;es nos mais variados sistemas.
 */
package br.com.staroski.rules;