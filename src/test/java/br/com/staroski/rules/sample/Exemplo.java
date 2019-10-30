package br.com.staroski.rules.sample;

import br.com.staroski.rules.*;

public class Exemplo {

	public static void main(String[] args) {
		// instanciamos as regras a partir das especificações 
		Rule<Pessoa> nome = Rule.create(new Nome());
		Rule<Pessoa> idade = Rule.create(new Idade());
		Rule<Pessoa> sexo = Rule.create(new Sexo());

		// criamos uma pessoa com nome, idade e sexo validos
		Pessoa pessoa = new Pessoa("Fulano", 30, 'M');
		// criamos uma regra só que corresponde às três regras: nome, idade e sexo
		// e validamos com um único if
		if (nome.and(idade).and(sexo).isSatisfiedBy(pessoa)) {
			System.out.println("Teste 1");
			System.out.println("O nome, idade e sexo da pessoa atendem as regras\n");
		}



		// criamos uma pessoa com nome, idade e sexo inválidos
		pessoa = new Pessoa("FuLaNo", -1, 'S');
		// criamos uma regra só que corresponde às três regras: nome, idade e sexo
		// armazenamos essa regra numa variável
		Rule<Pessoa> regra = nome.and(idade).and(sexo);
		// assim, validamos as três regras, com um único if
		if (regra.not().isSatisfiedBy(pessoa)) {
			System.out.println("Teste 2");
			System.out.println("A pessoa nao atendeu as seguintes regras:");
			// se a pessoa não atendeu às regras,
			// usamos a variável declarada para obter os detalhes
			for (String detalhe : regra.getDetails()) {
				System.out.println(detalhe);
			}
		}
	}
}
