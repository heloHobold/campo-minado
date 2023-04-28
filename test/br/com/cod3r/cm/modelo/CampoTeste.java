package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {
	
	private Campo campo;
	
	@BeforeEach // -> antes de cada método faça
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}
	
	// teste vizinhos laterais
	@Test
	void testeVizinhoDistancia1Esquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado); // true = vizinho
	}
	
	@Test
	void testeVizinhoDistancia1Direita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1EmCima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1EmBaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	// teste vizinhos diagonal
	@Test
	void testeVizinhoDistancia2EsquerdaEmCima() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia2EsquerdaEmBaixo() {
		Campo vizinho = new Campo(4, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia2DireitaEmCima() {
		Campo vizinho = new Campo(2, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia2DireitaEmBaixo() {
		Campo vizinho = new Campo(4, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	// teste não vizinhos
	@Test
	void testeNaoVizinho1() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado); // false = não vizinho
	}
	
	@Test
	void testeNaoVizinho2() {
		Campo vizinho = new Campo(5, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testeNaoVizinho3() {
		Campo vizinho = new Campo(2, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	// teste marcação
	@Test
	void testeValorPadraoAtribuidoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	// teste abrir
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}
	
	@Test
	void testeAbrirComVizinho() {
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);
		
		campo22.adicionarVizinho(campo11);
		campo.adicionarVizinho(campo22);
		
		campo.abrir();
	
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhoMinado() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		Campo campo22 = new Campo(2, 2);
		
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		campo.adicionarVizinho(campo22);
		
		campo.abrir();
	
		// campo22 só poderá ter sua vizinhança aberta se ela estiver segura (não minada)
		assertTrue(campo22.isAberto() && campo11.isFechado() && campo12.isFechado());
	}
	
	// teste objetivo alcançado
	
	@Test
	void testeObjetivoAlcancado() {
		campo.minar();
		campo.alternarMarcacao();
		
		Campo campo22 = new Campo(2, 2);
		campo22.abrir();
		
		assertTrue(campo.objetivoAlcancado() && campo22.objetivoAlcancado());
	}
	
	
	// teste objetivo não alcançado
	@Test
	void testeObjetivoNaoAlcancadoMinaNaoMarcada() {
		campo.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.abrir();
		
		assertFalse(campo.objetivoAlcancado() && campo22.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivoNaoAlcancadoCampoNaoAberto() {
		campo.minar();
		campo.alternarMarcacao();
		
		Campo campo22 = new Campo(2, 2);
		
		assertFalse(campo.objetivoAlcancado() && campo22.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivoNaoAlcancadoMinaNaoMarcadaECampoNaoAberto() {
		campo.minar();
		
		Campo campo22 = new Campo(2, 2);
		
		assertFalse(campo.objetivoAlcancado() && campo22.objetivoAlcancado());
	}
	
	// teste contagem de minas na vizinhança
	@Test
	void testeContagemDeMinasNaVizinhanca() {
		Campo campo22 = new Campo(2, 2);
		campo22.minar();
		Campo campo23 = new Campo(2, 3);
		campo23.minar();
		
		campo.adicionarVizinho(campo22);
		campo.adicionarVizinho(campo23);
		
		assertEquals(2, campo.minasNaVizinhanca());
	}
	
	// teste reiniciar
	@Test
	void testereiniciar() {
		campo.reiniciar();
		
		assertFalse(campo.isAberto() && campo.isMarcado() && campo.isMinado());
	}
}
