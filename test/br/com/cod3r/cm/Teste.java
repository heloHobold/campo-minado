package br.com.cod3r.cm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Teste {

	@Test // -> para definir que o método é um teste
	void testarSeIgualADois() {
		int a = 1 + 1;
		assertEquals(2, a); // verifica se o valor da variável 'a' é exatamente igual a '2'
	}
	
	@Test
	void testarSeIgualATres() {
		int x = 2 + 10 - 9;
		assertEquals(3, x);
	}

}
