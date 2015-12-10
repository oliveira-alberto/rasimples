package br.com.rasimples.control;

import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import br.com.rasimples.entity.Marcador;
import br.com.rasimples.entity.Objeto;

public class ControlObjeto {

	private Objeto objeto;
	
	public ControlObjeto() {
		objeto = new Objeto();
	}
	public String getNome() {
		return objeto.getNome();
	}
	public void setNome(String nome) {
		this.objeto.setNome(nome);
	}
	public ImageIcon getMiniatura() {
		return objeto.getMiniatura();
	}
	public void setMiniatura(ImageIcon miniatura) {
		this.objeto.setMiniatura(miniatura);
	}
	public File getModelo() {
		return objeto.getModelo();
	}
	public void setModelo(File modelo) {
		this.objeto.setModelo(modelo);
	}
	public int[] getEscala() {
		return objeto.getEscala();
	}
	public void setEscala(int[] escala) {
		this.objeto.setEscala(escala);
	}
	public double[] getRotacao() {
		return objeto.getRotacao();
	}
	public void setRotacao(double[] rotacao) {
		this.objeto.setRotacao(rotacao);
	}
	public double[] getTranslacao() {
		return objeto.getTranslacao();
	}
	public void setTranslacao(double[] translacao) {
		this.objeto.setTranslacao(translacao);
	}
	public List<File> getTexturas() {
		return objeto.getTexturas();
	}
	public void setTexturas(List<File> texturas) {
		this.objeto.setTexturas(texturas);
	}
	public Marcador getMarcador() {
		return objeto.getMarcador();
	}
	public void setMarcador(Marcador marcador) {
		this.objeto.setMarcador(marcador);
	}
	public String associarObjetoToMarcador(Objeto objeto, Marcador marcador){
		return objeto.associarObjetoToMarcador(objeto, marcador);
	}
	public String cadastrarObjeto(Objeto objeto){
		return objeto.cadastrarObjeto(objeto);
	}
	public void alterarPropriedades(Objeto o){
		objeto.alterarPropriedades(o);
	}
	public List<Objeto> getObjetos(){
		return objeto.getObjetos();
	}
}
