package br.com.caelum.ingresso.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.caelum.ingresso.model.DetalhesDoFilme;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.rest.OmdbClient;

//@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
public class DetalhesDoFilmeTest {

	//@Autowired
	private OmdbClient client;
	
	//@Test
	public void testarDetalhes() {
		Filme filme = new Filme();
		filme.setNome("Rogue One");
		client.request(filme, DetalhesDoFilme.class);
	}
}
