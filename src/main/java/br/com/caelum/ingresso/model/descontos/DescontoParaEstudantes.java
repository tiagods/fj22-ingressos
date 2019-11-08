package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class DescontoParaEstudantes implements Desconto {

	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal preco) {
		return preco.divide(new BigDecimal("2.0"));
	}

}
