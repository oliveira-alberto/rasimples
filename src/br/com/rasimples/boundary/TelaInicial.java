package br.com.rasimples.boundary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.rasimples.control.ControlMarcador;
import br.com.rasimples.control.ControlObjeto;
import br.com.rasimples.control.ControlSistema;
import br.com.rasimples.entity.Marcador;
import br.com.rasimples.entity.Objeto;

public class TelaInicial extends JFrame implements ActionListener, MouseListener, WindowFocusListener{
	
	private JPanel panel;
	private JMenuItem cadObjeto;
	private JMenuItem assObjeto;
	private JMenuItem altObjeto;
	private JMenuItem cadMarcador;
	private JMenuItem exe;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JScrollPane scroll;
	private List<JLabel> labelsObjetos;
	private List<JLabel> labelsMarcadores;
	private HashMap<JLabel, Objeto> mapObjeto;
	private HashMap<JLabel, Marcador> mapMarcador;
	private List<Objeto> objetos;
	private List<Marcador> marcadores;
	private JMenuBar menuBar;
	private ControlSistema controlSistema;
	private ControlObjeto controlObjeto;
	private ControlMarcador controlMarcador;
	private boolean isUpdate;
	private boolean isCad;
	private Thread thread;
	
	public TelaInicial() {
		super("RA Simples");
		controlSistema = new ControlSistema();
		controlObjeto = new ControlObjeto();
		controlMarcador = new ControlMarcador();
		isUpdate = false;
		isCad = false;
		init();
	}
	
	public void init(){
		criarBarraDeMenu();
		exibeObjetosMacadores();
		getContentPane().add(scroll);
		setupWindow();
		repaint();
		validate();
		if(isUpdate){
			isUpdate = false;
		}
	}
	
	private void criarBarraDeMenu(){
		if(scroll != null){
			getContentPane().remove(scroll);
		}
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		layout = new GridBagLayout();
		panel = new JPanel();
		panel.setLayout(layout);
		scroll = new JScrollPane();
		scroll.setBounds(50, 50, 780, 460);  
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
		scroll.setViewportBorder(BorderFactory.createLoweredBevelBorder());  
		scroll.setAutoscrolls(true);  
		scroll.setViewportView(panel);
		Font fontMenu = new Font("verdana", Font.PLAIN, 14);
		Font fontItem = new Font("verdana", Font.PLAIN, 12);
		JMenu objeto = new JMenu("Objeto");
		objeto.setFont(fontMenu);
		JMenu marcador = new JMenu("Marcador");
		marcador.setFont(fontMenu);
		JMenu executar = new JMenu("Executar");
		executar.setFont(fontMenu);
		
		//Menu Objeto
		cadObjeto = new JMenuItem("Cadastra Novo Objeto");
		objeto.add(cadObjeto);
		cadObjeto.addActionListener(this);
		cadObjeto.setFont(fontItem);
		altObjeto = new JMenuItem("Alterar Propriedades do Objeto");
		objeto.add(altObjeto);
		altObjeto.addActionListener(this);
		altObjeto.setFont(fontItem);
		assObjeto = new JMenuItem("Associar Objeto ao Marcador");
		objeto.add(assObjeto);
		assObjeto.addActionListener(this);
		assObjeto.setFont(fontItem);
		
		//Menu marcador
		cadMarcador = new JMenuItem("Cadastra Novo Marcador");
		marcador.add(cadMarcador);
		cadMarcador.addActionListener(this);
		cadMarcador.setFont(fontItem);
		
		//Menu executar
		exe = new JMenuItem("Executar");
		executar.add(exe);
		exe.addActionListener(this);
		exe.setFont(fontItem);
		menuBar.add(marcador);
		menuBar.add(objeto);
		menuBar.add(executar);
	}
	
	private void exibeObjetosMacadores(){
		ControlObjeto controlObjeto = new ControlObjeto();
		ControlMarcador controlMarcador = new ControlMarcador();
		objetos = controlObjeto.getObjetos();
		marcadores = controlMarcador.getMarcodres();
		constraints = new GridBagConstraints();
		JLabel spaceVertical = new JLabel(" ");
		Font font = new Font("verdana", Font.BOLD, 14);
		JLabel labelObjeto = new JLabel("Objetos");
		labelObjeto.setFont(font);
		addComponet(labelObjeto, 2, 0, 1, 1);
		JLabel spaceHorizontal = new JLabel("   ");
		addComponet(spaceHorizontal, 2, 1, 1, 1);
		JLabel labelMarcador = new JLabel("Marcadores");
		labelMarcador.setFont(font);
		addComponet(labelMarcador, 2, 4, 1, 1);
		spaceVertical = new JLabel(" ");
		addComponet(spaceVertical, 3, 0, 1, 1);
		int line = 4;
		labelsObjetos = new ArrayList<JLabel>();
		mapObjeto = new HashMap<JLabel, Objeto>();
		
		for (int i=0; i<objetos.size(); i++) {
			Objeto o = objetos.get(i);
			JLabel imgObjeto = new JLabel(o.getMiniatura());
			imgObjeto.setBounds(imgObjeto.getX(), imgObjeto.getY(), 80, 90);
			imgObjeto.addMouseListener(this);
			constraints.anchor = GridBagConstraints.WEST;
			addComponet(imgObjeto, i+line, 0, 1, 1);
			labelsObjetos.add(imgObjeto);
			mapObjeto.put(imgObjeto, o);
			
			spaceHorizontal = new JLabel("   ");
			addComponet(spaceHorizontal, i+line, 1, 1, 1);
			
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.CENTER;
			JLabel descricao = new JLabel(o.getNome()+spaceWidth(o.getNome()));
			addComponet(descricao, i+line, 2, 1, 1);
			
			spaceVertical = new JLabel(" ");
			addComponet(spaceVertical, i+1+line, 0, 1, 1);
			line+=2;
		}
		
		
		line = 4;
		labelsMarcadores = new ArrayList<JLabel>();
		mapMarcador = new HashMap<JLabel, Marcador>();
		for (int i=0; i<marcadores.size(); i++) {
			Marcador m = marcadores.get(i);
			
			spaceHorizontal = new JLabel("   ");
			addComponet(spaceHorizontal, i+line, 3, 1, 1);
			
			ImageIcon icon = new ImageIcon("image\\patt.png");
			JLabel imgMarcador = new JLabel(icon);
			imgMarcador.addMouseListener(this);
			constraints.anchor = GridBagConstraints.WEST;
			addComponet(imgMarcador, i+line, 4, 1, 1);
			labelsMarcadores.add(imgMarcador);
			mapMarcador.put(imgMarcador, m);
			
			spaceHorizontal = new JLabel("   ");
			addComponet(spaceHorizontal, i+line, 5, 1, 1);
			
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.CENTER;
			JLabel descricao = new JLabel(m.getNome()+spaceWidth(m.getNome()));
			addComponet(descricao, i+line, 6, 1, 1);
			
			spaceVertical = new JLabel(" ");
			addComponet(spaceVertical, i+1+line, 0, 1, 1);
			line+=2;
		}
	}
	
	private void setupWindow(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 640);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		ImageIcon RASimples = new ImageIcon("image\\RASimples.png");
		this.setIconImage(RASimples.getImage());
		this.addWindowFocusListener(this);
	}
	
	private String spaceWidth(String l){
		String length = "";
		int control = 60;
		if(l == null || l.isEmpty()){
			return "";
		}
		control = (l.length() < control ? 60 - l.length(): 60);
		while (length.length() < control) {
			length+=" ";
		}
		return length;
	}
	
	private void addComponet(Component c, int row, int col, int width, int height){
		constraints.gridx = col;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		layout.setConstraints(c, constraints);
		panel.add(c);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(cadObjeto)){
			new ObjetoGui(this);
			isCad = true;
			return;
		}
		if(event.getSource().equals(altObjeto)){
			Objeto o = null;
			for (JLabel l : labelsObjetos) {
				if(l.getBorder() != null){
					o = mapObjeto.get(l);
					break;
				}
			}
			if(o != null){				
				new AlterarObjetoGui(this, o);
			}else{
				JOptionPane.showMessageDialog(this, "Objeto não selecionado. Selecione um Objeto", 
						"Selecione um Objeto", JOptionPane.WARNING_MESSAGE);
				return;
			}
			objetos = controlObjeto.getObjetos();
			return;
		}
		if(event.getSource().equals(assObjeto)){
			Marcador m = null;
			Objeto o = null;
			for (JLabel l : labelsObjetos) {
				if(l.getBorder() != null){
					o = mapObjeto.get(l);
					break;
				}
			}
			for (JLabel l : labelsMarcadores) {
				if(l.getBorder() != null){
					m = mapMarcador.get(l);
					break;
				}
			}
			if(o == null){
				JOptionPane.showMessageDialog(this, "Objeto não selecionado. Selecione um Objeto", 
						"Selecione um Objeto", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(m == null){
				JOptionPane.showMessageDialog(this, "Marcador não selecionado. Selecione um Marcador", 
						"Selecione um Marcador", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String mensagem = controlObjeto.associarObjetoToMarcador(o, m);
			if(mensagem != null){				
				JOptionPane.showMessageDialog(this, mensagem, 
						"Objeto", JOptionPane.PLAIN_MESSAGE);
			}
			return;
		}
		if(event.getSource().equals(cadMarcador)){
			controlMarcador.cadastrarMarcador();
			isCad = true;
			return;
		}
		if(event.getSource().equals(exe)){
			controlSistema.executar();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(labelsObjetos.contains(e.getSource())){			
			for (JLabel l : labelsObjetos) {
				if(e.getComponent().equals(l)){
					l.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
				}else{
					l.setBorder(null);
				}
			}
		}
		if(labelsMarcadores.contains(e.getSource())){			
			for (JLabel l : labelsMarcadores) {
				if(e.getComponent().equals(l)){
					l.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
				}else{
					l.setBorder(null);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		controlSistema.update();
		init();
		if(isCad){
			scroll.getVerticalScrollBar().setValue(830);
		}
		isUpdate = true;
		isCad = false;
	}
	@Override
	public void windowLostFocus(WindowEvent e) {
		isUpdate = false;
	}
}
