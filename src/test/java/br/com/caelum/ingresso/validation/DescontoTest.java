package br.com.caelum.ingresso.validation;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;

public class DescontoTest {
	
	private static Sala sala;
	private static Filme filme;
	private static Sessao sessao;
	
	@BeforeClass
	public static void preparar() {
		sala = new Sala("Eldorado - IMAX",new BigDecimal("20.5"));
		filme = new Filme("Rogue One",Duration.ofMinutes(120),
				"SCI-FI",new BigDecimal("12"));
		sessao = new Sessao(LocalTime.parse("10:00:00"),filme,sala);
	}
	
	@Test
	public void ingressoSemDesconto() {
		TipoDeIngresso tipo = TipoDeIngresso.INTEIRO;
		Ingresso ingresso = new Ingresso(sessao, tipo,null);
		
		BigDecimal precoEsperado = new BigDecimal("32.50");
		Assert.assertNotNull(tipo.getDescricao());
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void descontoClienteBanco() {
		TipoDeIngresso tipo = TipoDeIngresso.BANCO;
		Ingresso ingresso = new Ingresso(sessao, tipo,null);
		BigDecimal precoEsperado = new BigDecimal("22.75");
		Assert.assertNotNull(tipo.getDescricao());
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void descontoClienteEstudante() {
		TipoDeIngresso tipo = TipoDeIngresso.ESTUDANTE;
		Ingresso ingresso = new Ingresso(sessao, tipo,null);
		
		BigDecimal precoEsperado = new BigDecimal("16.25");
		Assert.assertNotNull(tipo.getDescricao());
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
}
