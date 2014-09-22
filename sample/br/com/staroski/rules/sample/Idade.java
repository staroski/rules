package br.com.staroski.rules.sample;

import br.com.staroski.rules.*;

// Especificação da regra que valida a idade de Pessoa
class Idade implements Specification<Pessoa> {

	@Override
	public void verify(Pessoa pessoa) throws UnattendedException {
		if (pessoa.idade < 0) {
			throw new UnattendedException("Idade não pode ser negativa");
		}
	}
}
