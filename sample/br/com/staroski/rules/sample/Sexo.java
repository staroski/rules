package br.com.staroski.rules.sample;

import br.com.staroski.rules.*;

// Especificação da regra que valida o sexo de uma Pessoa
class Sexo implements Specification<Pessoa> {

	@Override
	public void verify(Pessoa pessoa) throws UnattendedException {
		switch (pessoa.sexo) {
			case 'M':
			case 'F':
				return;
			default:
				throw new UnattendedException("Sexo só pode ser 'M' ou 'F'");
		}
	}
}