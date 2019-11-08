package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class DescontoParaBancos implements Desconto {

	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal preco) {
		return preco.subtract(trintaPorCentoSobre(preco));
	}
	public BigDecimal trintaPorCentoSobre(BigDecimal preco) {
		return preco.multiply(new BigDecimal("0.3"));
	}
}
