package br.com.caelum.ingresso.validation;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {

	private Filme rogueOne;
	private Sala sala3d;
	private Sessao sessaoDasDez;
	private Sessao sessaoDasTreze;
	private Sessao sessaoDasDezoito;
	
	@Before
	public void preparaSessoes() {
		this.rogueOne = new Filme("Rogue One",Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		this.sala3d = new Sala("Sala 3D",BigDecimal.TEN);
		
		this.sessaoDasDez = new Sessao(LocalTime.parse("10:00:00"),this.rogueOne, this.sala3d);
		this.sessaoDasTreze = new Sessao(LocalTime.parse("13:00:00"),this.rogueOne, this.sala3d);
		this.sessaoDasDezoito = new Sessao(LocalTime.parse("18:00:00"),this.rogueOne, this.sala3d);
		
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		boolean cabe = gerenciador.cabe(sessaoDasDez);
		Assert.assertEquals(false, cabe);
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().minusHours(1),rogueOne, sala3d);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertEquals(false, gerenciador.cabe(sessao));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaJaExistente(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().minusHours(1),rogueOne, sala3d);
		Assert.assertEquals(false, gerenciador.cabe(sessao));
	}
	
	@Test
	public void garanteQueDevePermitirUmInsercaoEntreDoisFilmes() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez,sessaoDasDezoito);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertEquals(true, gerenciador.cabe(sessaoDasTreze));
	}
	
	@Test
	public void garanteQueDeveNaoPermitirUmaSessaoQueTerminaNoProximoDia() {
		List<Sessao> sessoes = Collections.emptyList();
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessaoQueTerminaAmanha = new Sessao(LocalTime.parse("23:00:00"),rogueOne,sala3d);
		Assert.assertEquals(false, gerenciador.cabe(sessaoQueTerminaAmanha));
	}
	
	@Test
	public void oPrecoDaSessaoDeveSerIgualASomaDoPrecoDaSalaMaisOPrecoDoFilme() {
		Sala sala = new Sala("Eldorado - Imax",new BigDecimal("22.5"));
		Filme filme = new Filme("Rogue One", Duration.ofMinutes(120),
				"SFI-FI",new BigDecimal("12.0"));
		
		BigDecimal somaDosPrecosDaSalaEFilme = sala.getPreco().add(filme.getPreco());
		
		Sessao sessao = new Sessao(LocalTime.parse("10:00:00"),filme,sala);
		Assert.assertEquals(somaDosPrecosDaSalaEFilme, sessao.getPreco());
	}
}
