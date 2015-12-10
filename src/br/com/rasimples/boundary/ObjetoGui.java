package br.com.rasimples.boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.rasimples.control.ControlObjeto;
import br.com.rasimples.entity.Objeto;

import com.jeta.forms.components.panel.FormPanel;

public class ObjetoGui extends JDialog implements ActionListener{

	private FormPanel panel;
	private JButton bProModelo;
	private JButton bProMiniatura;
	private JButton bAddTextura;
	private JButton bCadastrar;
	private JTextField tfNome;
	private JTextField tfModelo;
	private JTextField tfMiniatura;
	private JList<String> jlTexturas;
	private DefaultListModel<String> modelList;
	private JFrame container;
	private ControlObjeto controlObjeto;
	
	public ObjetoGui(JFrame container) {
		modelList = new DefaultListModel<String>();
		controlObjeto = new ControlObjeto();
		this.container = container;
		init();
	}
	
	private void init(){
		panel = new FormPanel("forms/ObjetoGui.jfrm");
		add(panel);
		createGui();
		setTitle("Cadastro de Novo Objeto");
		pack();
		setLocationRelativeTo(container);
		setModal(true);
		setVisible(true);
		tfNome.requestFocusInWindow();
	}
	
	private void createGui(){
		tfNome = (JTextField) panel.getComponentByName("tfNome");
		tfNome.addActionListener(this);
		tfMiniatura = (JTextField) panel.getComponentByName("tfMiniatura");
		tfMiniatura.addActionListener(this);
		tfModelo = (JTextField) panel.getComponentByName("tfModelo");
		tfModelo.addActionListener(this);
		bProMiniatura = (JButton) panel.getComponentByName("bProcurarMiniatura");
		bProMiniatura.addActionListener(this);
		bProModelo = (JButton) panel.getComponentByName("bProcurarModelo");
		bProModelo.addActionListener(this);
		bAddTextura = (JButton) panel.getComponentByName("bAddTextura");
		bAddTextura.addActionListener(this);
		bCadastrar = (JButton) panel.getComponentByName("bCadastrar");
		bCadastrar.addActionListener(this);
		jlTexturas = (JList) panel.getComponentByName("jlTexturas");
		jlTexturas.setCellRenderer(new DefaultListCellRenderer());
		
	}
	
	private void cadastrarObjeto(){
		Objeto objeto = new Objeto();
		String campo = tfNome.getText().trim();
		if(!campo.isEmpty()){
			objeto.setNome(campo);
		}else{
			JOptionPane.showMessageDialog(this, "Campo nome e Obrigatório!", "Digite o Nome", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		campo = tfModelo.getText().trim();
		if(!campo.isEmpty()){
			File file = new File(campo);
			if(file.exists()){
				objeto.setModelo(file);
			}
		}else{			
			JOptionPane.showMessageDialog(this, "Campo modelo e Obrigatório!", "Indique o Modelo .wrl", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		campo = tfMiniatura.getText().trim();
		if(!campo.isEmpty()){
			String[] tokens = null;
			try {
				FileInputStream fis = new FileInputStream(campo);
				tokens = campo.split("\\\\");
				File f = new File("ImagensModelo\\"+tokens[tokens.length-1]);
				FileOutputStream fos = new FileOutputStream(f);
				int c;
				while(( c = fis.read()) != -1){
					fos.write(c);
				}
				fos.close();
				fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ImageIcon miniatura = new ImageIcon("ImagensModelo\\"+tokens[tokens.length-1]);
			objeto.setMiniatura(miniatura);
		}
		for (int i = 0; i < modelList.getSize(); i++) {
			List<File> texturas = new ArrayList<File>();
			File file = new File(modelList.get(i));
			if(file.exists()){
				texturas.add(file);
			}else{
				JOptionPane.showMessageDialog(this, "Arquivo de texturas "+modelList.get(i)+" não existe", 
						"Indique o Modelo .wrl", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		controlObjeto.cadastrarObjeto(objeto);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(bCadastrar)){
			cadastrarObjeto();
			JOptionPane.showMessageDialog(this, "Objeto foi cadastrado com sucesso.", 
					"Cadastro de Objeto", 
					JOptionPane.PLAIN_MESSAGE);
			dispose();
			return;
		}
		if(e.getSource().equals(bProModelo)){
			String path = showFileChooser("Procurar Modelo",  "Arquivos *.wrl", "wrl");
			tfModelo.setText(path);
			return;
		}
		if(e.getSource().equals(bProMiniatura)){			
			String path = showFileChooser("Procurar Miniatura",  "Arquivos *.jpeg; *.png; *.gif", "jpeg", "jpg", "gif", "png");
			tfMiniatura.setText(path);
			return;
		}
		if(e.getSource().equals(bAddTextura)){
			String path = showFileChooser("Adicionar Textura",  "Arquivos *.jpeg *.png; *.gif", "gif", "jpeg", "jpg", "png");
			if(!modelList.contains(path)){
				modelList.addElement(path);
			}
			jlTexturas.setModel(modelList);
			return;
		}
	}
	public String showFileChooser(String title, String fileFilter, String...extension) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(fileFilter, extension);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);
		String filepath = null;
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			filepath = chooser.getSelectedFile().getAbsolutePath();
		}
		return filepath;
	}
}
