package br.com.rasimples.control;

import br.com.rasimples.entity.Marcador;
import br.com.rasimples.entity.Objeto;
import br.com.rasimples.entity.Sistema;

public class ControlSistema {

	private Sistema sistema;
	
	public ControlSistema() {
		sistema = new Sistema();
	}
	public void criarPropriedades(Objeto objeto){
		sistema.criarPropriedades(objeto);
	}
	public int verificarAssociacao(Objeto o, Marcador m){
		return sistema.verificarAssociacao(o, m);
	}
	public void associar(Objeto o, Marcador m){
		sistema.associar(o, m);
	}
	public void sobrescrever(Objeto o, Marcador m){
		sistema.sobrescrever(o, m);
	}
	public void executar(){
		sistema.executar();
	}
	
	public void update(){
		sistema.update();
	}
}
