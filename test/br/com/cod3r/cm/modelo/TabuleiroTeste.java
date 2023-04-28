package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class TabuleiroTeste {
	
	private Tabuleiro tabuleiro;
	
	@BeforeEach
	void iniciarCampo() {
		tabuleiro = new Tabuleiro(3, 3, 2);
	}

	@Test
	void testeGerarCampos() {
		assertEquals(tabuleiro.getCampos().size(), 9);
	}
	
	@Test
	void testeAssociarVizinhos() {
		assertEquals(tabuleiro.getCampos().get(4).getVizinhos().size(), 8);
		assertEquals(tabuleiro.getCampos().get(4).getLinha(), 1);
		assertEquals(tabuleiro.getCampos().get(4).getColuna(), 1);
	}

	
	@Test
	void testeSortearMinas() {
		assertEquals(tabuleiro.getCampos().stream().filter(c -> c.isMinado()).count(), 2);
	}
	
	@Test
	void testeObjetivoAlcancado() {
		tabuleiro.getCampos().stream().forEach(c -> {
			if (c.isMinado()) c.alternarMarcacao();
			if (c.isFechado()) c.abrir();
		});
		assertTrue(tabuleiro.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivoNaoAlcancadoCamposNaoAbertos() {
		tabuleiro.getCampos().stream().forEach(c -> {
			if (c.isMinado()) c.alternarMarcacao();
		});
		assertFalse(tabuleiro.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivoNaoAlcancadoMinasNaoMarcadas() {
		tabuleiro.getCampos().stream().forEach(c -> {
			if (c.isFechado() && !c.isMinado()) c.abrir();
		});
		
		assertFalse(tabuleiro.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivoNaoAlcancadoMinaExplode() {
		assertThrows(ExplosaoException.class, () -> {
			tabuleiro.getCampos().stream().forEach(c -> {
				if (c.isFechado()) c.abrir();
			});
		});
	}
	
	@Test
	void testeReiniciar() {
		tabuleiro.getCampos().stream().forEach(c -> {
			if (c.isFechado() && !c.isMinado()) c.abrir();
		});
		
		tabuleiro.reiniciar();
		assertFalse(tabuleiro.getCampos().stream().allMatch(c -> c.isAberto() && c.isMarcado()));
		assertEquals(tabuleiro.getCampos().stream().filter(c -> c.isMinado()).count(), 2);
	}
	
	@Test
	void testeAbrirCampoNaoMinado() {
		Campo campo = tabuleiro.getCampos().stream().filter(c -> !c.isMinado()).findFirst().get();
		tabuleiro.abrir(campo.getLinha(), campo.getColuna()); 
		assertTrue(campo.isAberto());
	}
	
	@Test
	void testeAbrirCampoMinado() {
		Campo campo = tabuleiro.getCampos().stream().filter(c -> c.isMinado()).findFirst().get();
		
		assertThrows(ExplosaoException.class, () -> {
			tabuleiro.abrir(campo.getLinha(), campo.getColuna());
		});
	}
	
	@Test
	void testeAlterarMarcacao() {
		tabuleiro.alternarMarcacao(1, 1);
		assertTrue(tabuleiro.getCampos().get(4).isMarcado());
		tabuleiro.alternarMarcacao(1, 1);
		assertFalse(tabuleiro.getCampos().get(4).isMarcado());
	}
}
