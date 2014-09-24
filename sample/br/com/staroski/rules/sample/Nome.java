package br.com.staroski.rules.sample;

import br.com.staroski.rules.*;

// Especificação da regra que valida o nome de uma Pessoa
class Nome implements Specification<Pessoa> {

	@Override
	public void verify(Pessoa pessoa) throws UnattendedException {
		if (!pessoa.nome.matches("[A-Z]{1}[a-z]+")) {
			throw new UnattendedException("Nome precisa começar com letra maiuscula e ter pelo menos duas letras");
		}
	}
}
