package br.com.staroski.rules.sample;

import br.com.staroski.rules.*;

public class Exemplo {

	public static void main(String[] args) {
		new Exemplo().executa();
	}

	private void executa() {
		// instanciamos as regras a partir das especificações 
		Rule<Pessoa> nome = Rule.create(new Nome());
		Rule<Pessoa> idade = Rule.create(new Idade());
		Rule<Pessoa> sexo = Rule.create(new Sexo());

		// criamos uma pessoa com nome, idade e sexo validos
		Pessoa pessoa = new Pessoa("Fulano", 30, 'M');
		if (nome.and(idade).and(sexo).isSatisfiedBy(pessoa)) {
			System.out.println("o nome, idade e sexo da pessoa atendem as regras");
		}

		// criamos uma pessoa com nome invalido, mas idade e sexo validos
		pessoa = new Pessoa("FuLaNo", -1, 'S');
		Rule<Pessoa> regra = nome.and(idade).and(sexo);
		if (regra.not().isSatisfiedBy(pessoa)) {
			// apresentamos os detalhes
			for (String detalhe : regra.getDetails()) {
				System.out.println(detalhe);
			}
		}
	}
}
