package br.com.rasimples.start;

import br.com.rasimples.boundary.TelaInicial;

public class RASimples {

	private TelaInicial gui;
	
	public RASimples() {
		gui = new TelaInicial();
	}
	
	public static void main(String[] args) {
		new RASimples();
	}
}
