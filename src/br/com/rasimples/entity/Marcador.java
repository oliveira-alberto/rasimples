package br.com.rasimples.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Marcador{
	
	private String nome;
	
	public Marcador() {
		
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void cadastrarMarcador(){
		try {
			Process p = Runtime.getRuntime().exec("cmd /c start /d \""+getPath()+"\\bin\\\" mk_patt.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPath() {
		String path = null;
		try {
			InputStream is = new FileInputStream("res//path");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			path = br.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public List<Marcador> getMarcodres(){
		List<Marcador> marcadores = new ArrayList<Marcador>();
		File diretorio = new File(getPath()+"\\bin\\Data\\");
		String[] arquivos = diretorio.list();
		for (String s : arquivos) {
			if(s.startsWith("patt.")){
				Marcador m = new Marcador();
				m.setNome(s);
				marcadores.add(m);
			}
		}
		return marcadores;
	}
}
