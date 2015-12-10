package br.com.rasimples.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Sistema {

	private String path;
	public static final int OBJETO_EXISTE_ASS = 0;
	public static final int MARCADOR_EXISTE_ASS = 1;
	
	public Sistema() {
		
	}
	
	public void criarPropriedades(Objeto objeto){
		try {
			FileOutputStream fos = new FileOutputStream(getPath()+"\\bin\\Wrl\\"+objeto.getNome()+".dat");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(objeto.getNome()+".wrl");
			bw.newLine();
			bw.write("0.0 0.0 50.0# Translation");
			bw.newLine();
			bw.write("90.0 1.0 0.0 0.0# Rotation");
			bw.newLine();
			bw.write("25 25 25# Scale");
			bw.newLine();
			bw.write(objeto.getMiniatura() != null ? objeto.getMiniatura()+";" : ";");
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int verificarAssociacao(Objeto o, Marcador m){
		int existe = -1;
		File object_data = new File(getPath()+"//bin//Data//object_data_vrml");
		String objetoName = o.getNome();
		String markNome = m.getNome();
		FileInputStream fis;
		try {
			fis = new FileInputStream(object_data);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while((line = br.readLine()) != null){
				if(line.contains(objetoName)){
					existe = OBJETO_EXISTE_ASS;
					break;
				}else if(line.contains(markNome)){
					existe = MARCADOR_EXISTE_ASS;
					break;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return existe;
	}
	
	public void associar(Objeto o, Marcador m){
		String path = getPath()+"//bin//Data//object_data_vrml";
		File object_data = new File(path);
		List<String> lines = new ArrayList<String>();
		FileInputStream fis;
		FileOutputStream fos;
		try {
			fis = new FileInputStream(object_data);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while((line = br.readLine()) != null){
				lines.add(line);
			}
			br.close();
			
			Integer i = null;
			fos = new FileOutputStream(path);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (String s : lines) {
				if(!(s.length() == 1)){
					bw.write(s);
					bw.newLine();
				}else{
					i = new Integer(s);
					++i;
					bw.write(i.toString());
					bw.newLine();
				}
			}
			bw.newLine();
			bw.newLine();
			bw.write("#pattern "+i);
			bw.newLine();
			bw.newLine();
			bw.write("VRML Wrl/"+o.getNome()+".dat");
			bw.newLine();
			bw.newLine();
			bw.write("Data/"+m.getNome());
			bw.newLine();
			bw.newLine();
			bw.write("80.0");
			bw.newLine();
			bw.write("0.0 0.0");
			bw.newLine();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sobrescrever(Objeto o, Marcador m){
		String path = getPath()+"\\bin\\Data\\object_data_vrml";
		File object_data = new File(path);
		String objetoName = o.getNome();
		String markNome = m.getNome();
		List<String> lines = new ArrayList<String>();
		FileInputStream fis;
		FileOutputStream fos;
		try {
			fis = new FileInputStream(object_data);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while((line = br.readLine()) != null){
				lines.add(line);
			}
			br.close();
			
			fos = new FileOutputStream(path);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			boolean pularLinha = false;
			int cont = 1;
			for (String s : lines) {
				if(pularLinha){
					if(cont == 2)
						pularLinha = false;
					++cont;
					continue;
				}
				if(s.contains(objetoName)){
					String[] tokens = s.split("/");
					bw.write(tokens[0]+"/"+o.getNome()+".dat");
					bw.newLine();
					bw.newLine();
					bw.write("Data/"+markNome);
					bw.newLine();
					pularLinha = true;
				}else if(s.contains(markNome)){
					bw.newLine();
					String[] tokens = s.split("/");
					bw.write(tokens[0]+"/"+m.getNome());
				}else{
					bw.write(s);
					bw.newLine();
				}
			}
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		String path = getPath()+"\\bin";
		File diretorio = new File(path);
		String[] arquivos = diretorio.list();
		for (String s : arquivos) {
			if(s.contains("patt.") && !s.contains(".exe")){
				try {
					FileOutputStream fos = new FileOutputStream(path+"\\Data\\"+s);
					File fileDelete = new File(path+"\\"+s);
					fileDelete.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void executar(){
		try {
			Process p = Runtime.getRuntime().exec("cmd /c start /d \""+getPath()+"\\bin\\\" simpleVRML.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPath() {
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
	public void setPath(String path) {
		this.path = path;
	}
}
