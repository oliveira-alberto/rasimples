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

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import br.com.rasimples.control.ControlSistema;

public class Objeto {

	private String nome;
	private ImageIcon miniatura;
	private File modelo;
	private int[] escala;
	private double[] rotacao;
	private double[] translacao;
	private List<File> texturas;
	private Marcador marcador;
	private ControlSistema controlSistema;
	
	public Objeto() {
		texturas = new ArrayList<File>();
		controlSistema = new ControlSistema();
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ImageIcon getMiniatura() {
		return miniatura;
	}
	public void setMiniatura(ImageIcon miniatura) {
		this.miniatura = miniatura;
	}
	public File getModelo() {
		return modelo;
	}
	public void setModelo(File modelo) {
		this.modelo = modelo;
	}
	public int[] getEscala() {
		return escala;
	}
	public void setEscala(int[] escala) {
		this.escala = escala;
	}
	public double[] getRotacao() {
		return rotacao;
	}
	public void setRotacao(double[] rotacao) {
		this.rotacao = rotacao;
	}
	public double[] getTranslacao() {
		return translacao;
	}
	public void setTranslacao(double[] translacao) {
		this.translacao = translacao;
	}
	public List<File> getTexturas() {
		return texturas;
	}
	public void setTexturas(List<File> texturas) {
		this.texturas = texturas;
	}
	public Marcador getMarcador() {
		return marcador;
	}
	public void setMarcador(Marcador marcador) {
		this.marcador = marcador;
	}
	private String getPath() {
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
	
	private List<String> getList(File file){
		List<String> list = new ArrayList<String>();
		try {
			InputStream is = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line = br.readLine()) != null){
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void salvarArquivo(File file, String destino){
		List<String> lines = new ArrayList<String>();
		if(!file.exists()){
			JOptionPane.showMessageDialog(null, "Erro", "Arquivo não existe", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			String s = br.readLine();
			while(s != null){
				lines.add(s);
				s = br.readLine();
			}
			br.close();
			FileOutputStream fos = new FileOutputStream(destino);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (String string : lines) {
				bw.write(string);
				bw.newLine();
			}
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String associarObjetoToMarcador(Objeto objeto, Marcador marcador){
		String mensagem = null;
		int existe = controlSistema.verificarAssociacao(objeto, marcador);
		if(existe == Sistema.OBJETO_EXISTE_ASS){
			int result = JOptionPane.showConfirmDialog(null, "O objeto "+objeto.getNome()+" já está associado com um marcador"
					+ ". Deseja sobrecrever?", "Associação já existe",
							JOptionPane.YES_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION){
				controlSistema.sobrescrever(objeto, marcador);
				mensagem = "Associação sobrescrita com sucesso.";
			}
		}else if(existe == Sistema.MARCADOR_EXISTE_ASS){
			int result = JOptionPane.showConfirmDialog(null, "O marcador "+marcador.getNome()+" já está associado com outro objeto"
					+ ". Deseja sobrecrever?", "Associação já existe",
							JOptionPane.YES_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION){
				controlSistema.sobrescrever(objeto, marcador);
				mensagem = "Associação sobrescrita com sucesso.";
			}
		}else{
			controlSistema.associar(objeto, marcador);
			mensagem = "Objeto associado com sucesso";
		}
		return mensagem;
	}
	
	public String cadastrarObjeto(Objeto objeto){
		String mensagem = null;
		controlSistema.criarPropriedades(objeto);
		for (File f: objeto.getTexturas()) {
			salvarArquivo(f, getPath()+"//bin//Wrl//textures//"+f.getName());
		}
		salvarArquivo(objeto.getModelo(), getPath()+"//bin//Wrl//"+objeto.getNome()+".wrl");
		return mensagem = "Objeto cadastrado com sucesso";
	}
	
	public void alterarPropriedades(Objeto o){
		String path = getPath()+"\\bin\\Wrl\\"+o.getNome()+".dat";
		File propriedades = new File(path);
		if(propriedades.exists()){
			FileInputStream fis;
			FileOutputStream fos;
			List<String> lines = new ArrayList<String>();
			try {
				fis = new FileInputStream(propriedades);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				
				String line = null;
				while((line = br.readLine()) != null){
					lines.add(line);
				}
				br.close();
				fos = new FileOutputStream(path);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				bw.write(o.getNome()+".wrl");
				bw.newLine();
				for (String s : lines) {
					if(s.contains("#")){
						String property = s.split("#")[1];
						String[] prop = s.split(" ");
						if(property.equals(" Translation")){
							bw.write(o.getTranslacao()[0]+" "+o.getTranslacao()[1]+" "+o.getTranslacao()[2]+"#"+property);
							bw.newLine();
						}else if(property.equals(" Rotation")){
							bw.write(o.getRotacao()[0]+" "+o.getRotacao()[1]+" "+o.getRotacao()[2]+" "+o.getRotacao()[3]+"#"+property);
							bw.newLine();
						}else if(property.equals(" Scale")){
							bw.write(o.getEscala()[0]+" "+o.getEscala()[1]+" "+o.getEscala()[2]+"#"+property);
							bw.newLine();
						}
					}else if(s.contains(";")){
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
			
			JOptionPane.showMessageDialog(null, "Prorpriedades do Objeto "+o.getNome()+" alteradas com sucesso", "Alterar propriedades",
					JOptionPane.PLAIN_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "Erro ao alterar propriedades do objeto "+o.getNome(), "Alterar propriedade",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<Objeto> getObjetos(){
		List<Objeto> objetos = new ArrayList<Objeto>();
		String path = getPath()+"\\bin\\Wrl";
		File dir = new File(path);
		String[] arquivos = dir.list();
		boolean isNew = false;
		Objeto obj = new Objeto();
		for (String s : arquivos) {
			if(s.contains(".dat")){
				String nome = s.substring(0, s.length()-4);
				obj.setNome(nome);
				File f = new File(path+"\\"+s);
				for (String string : getList(f)) {
					if(string.contains("#")){
						String property = string.split("#")[1];
						String[] prop = string.split(" ");
						if(property.equals(" Translation")){
							double[] translation = new double[3];
							translation[0] = new Double(prop[0]);
							translation[1] = new Double(prop[1]);
							translation[2] = new Double(prop[2].substring(0, prop[2].length()-1));
							obj.setTranslacao(translation);
						}else if(property.equals(" Rotation")){
							double[] rotation = new double[3];
							rotation[0] = new Double(prop[0]);
							rotation[1] = new Double(prop[1]);
							rotation[2] = new Double(prop[2].substring(0, prop[2].length()-1));
							obj.setRotacao(rotation);
						}else if(property.equals(" Scale")){
							int[] scale = new int[3];
							scale[0] = new Integer(prop[0]);
							scale[1] = new Integer(prop[1]);
							scale[2] = new Integer(prop[2].substring(0, prop[2].length()-1));
							obj.setEscala(scale);
						}
					}else if(string.contains(";")){
						String[] tokens = string.split(";");
						if(tokens.length > 0){
							ImageIcon miniatura = new ImageIcon(tokens[0]);
							obj.setMiniatura(miniatura);
						}else{
							ImageIcon m = new ImageIcon("image/img3d.png");
							obj.setMiniatura(m);
						}
						isNew = true;
					}
				}
			}
			if(s.contains(".wrl")){
				List<String> nomes = new ArrayList<String>();
				File f = new File(path+"\\"+s);
				for (String string : getList(f)) {
					if(string.contains("./textures")){
						String[] array = string.split("/");
						String testuraNome = array[2].substring(0, array[2].length()-1);
						if(!nomes.contains(testuraNome)){							
							File textura = new File(path+"\\textures\\"+testuraNome);
							obj.getTexturas().add(textura);
							nomes.add(testuraNome);
						}
					}
				}
				objetos.add(obj);
				if(isNew){
					isNew = false;
					obj = new Objeto();
				}
			}
		}
		return objetos;
	}
}
