package br.com.rasimples.control;

import java.util.List;
import br.com.rasimples.entity.Marcador;

public class ControlMarcador {
	
	private Marcador marcador;
	
	public ControlMarcador() {
		marcador = new Marcador();
	}
	public String getNome() {
		return marcador.getNome();
	}
	public void setNome(String nome) {
		this.marcador.setNome(nome);
	}
	public void cadastrarMarcador(){
		marcador.cadastrarMarcador();
	}
	public List<Marcador> getMarcodres(){
		return marcador.getMarcodres();
	}
}
