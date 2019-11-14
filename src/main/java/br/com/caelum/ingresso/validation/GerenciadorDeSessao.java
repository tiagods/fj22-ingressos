package br.com.caelum.ingresso.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessaoDaSala;
	
	private GerenciadorDeSessao() {}
	
	public GerenciadorDeSessao(List<Sessao> sessoesDaSala) {
		this.sessaoDaSala = sessoesDaSala;
	}
	
	public boolean cabe(Sessao sessaoNova) {
		if (terminaAmanha(sessaoNova)) {
			return false;
		}
		return this.sessaoDaSala
				.stream()
				.noneMatch(sessaoExistente -> horarioIsConflitante(sessaoExistente, sessaoNova));
	}
	private boolean terminaAmanha(Sessao sessao) {
		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(sessao);
		LocalDateTime ultimoSegundoDeHoje = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		if(terminoSessaoNova.isAfter(ultimoSegundoDeHoje)){
			return true;
		}
		return false;
	}
	private boolean horarioIsConflitante(Sessao sessaoExistente, Sessao sessaoNova) {
		LocalDateTime inicioSessaoExistente = getInicioSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime terminoSessaoExistente = getTerminoSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(sessaoNova);
		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(sessaoNova);
		
		boolean sessaoNovTerminaAntesDaExistente = terminoSessaoNova.isBefore(inicioSessaoExistente);
		boolean sessaoNovaComecaDepoisDaExistente = terminoSessaoExistente.isBefore(inicioSessaoNova);
		if(sessaoNovTerminaAntesDaExistente || sessaoNovaComecaDepoisDaExistente) {
			return false;
		}
		return true;
	}
	
	private LocalDateTime getInicioSessaoComDiaDeHoje(Sessao sessao) {
		LocalDate hoje = LocalDate.now();
		return sessao.getHorario().atDate(hoje);
	}
	
	private LocalDateTime getTerminoSessaoComDiaDeHoje(Sessao sessao) {
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(sessao);
		return inicioSessaoNova.plus(sessao.getFilme().getDuracao());
	}
}
