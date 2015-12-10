package br.com.rasimples.boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import br.com.rasimples.control.ControlObjeto;
import br.com.rasimples.entity.Objeto;
import com.jeta.forms.components.panel.FormPanel;

public class AlterarObjetoGui extends JDialog implements ActionListener{

	private Objeto objeto;
	private FormPanel panel;
	private JButton bCancela;
	private JButton bAlterar;
	private JTextField tfTranslationX, tfTranslationY, tfTranslationZ;
	private JTextField tfRotationX, tfRotationY, tfRotationZ;
	private JTextField tfScaleX, tfScaleY, tfScaleZ;
	private JLabel lNome;
	private JFrame container;
	private ControlObjeto controlObjeto;
	
	public AlterarObjetoGui(JFrame container, Objeto objeto) {
		this.objeto = objeto;
		this.container = container;
		controlObjeto = new ControlObjeto();
		init();
	}
	
	private void init(){
		panel = new FormPanel("forms/propiedades.jfrm");
		add(panel);
		createGui();
		setPropriedade();
		setTitle("Alterar propriedades do objeto");
		pack();
		setLocationRelativeTo(container);
		setModal(true);
		setVisible(true);
		tfTranslationX.requestFocusInWindow();
	}
	
	private void createGui(){
		tfTranslationX = (JTextField) panel.getComponentByName("tfTranslationX");
		tfTranslationX.addActionListener(this);
		tfTranslationY = (JTextField) panel.getComponentByName("tfTranslationY");
		tfTranslationY.addActionListener(this);
		tfTranslationZ = (JTextField) panel.getComponentByName("tfTranslationZ");
		tfTranslationZ.addActionListener(this);
		tfScaleX = (JTextField) panel.getComponentByName("tfScaleX");
		tfScaleX.addActionListener(this);
		tfScaleY = (JTextField) panel.getComponentByName("tfScaleY");
		tfScaleY.addActionListener(this);
		tfScaleZ = (JTextField) panel.getComponentByName("tfScaleZ");
		tfScaleZ.addActionListener(this);
		tfRotationX = (JTextField) panel.getComponentByName("tfRotationX");
		tfRotationX.addActionListener(this);
		tfRotationY = (JTextField) panel.getComponentByName("tfRotationY");
		tfRotationY.addActionListener(this);
		tfRotationZ = (JTextField) panel.getComponentByName("tfRotationZ");
		tfRotationZ.addActionListener(this);
		Font font = new Font("verdana", Font.PLAIN, 10);
		bAlterar = (JButton) panel.getComponentByName("bAlterar");
		bAlterar.addActionListener(this);
		bAlterar.setFont(font);
		bCancela = (JButton) panel.getComponentByName("bCancela");
		bCancela.addActionListener(this);
		bCancela.setFont(font);
		lNome = (JLabel) panel.getComponentByName("lNome");		
	}
	
	private void setPropriedade(){
		lNome.setText(objeto.getNome());
		tfTranslationX.setText(objeto.getTranslacao()[0]+"");
		tfTranslationY.setText(objeto.getTranslacao()[1]+"");
		tfTranslationZ.setText(objeto.getTranslacao()[2]+"");
		tfRotationX.setText(objeto.getRotacao()[0]+"");
		tfRotationY.setText(objeto.getRotacao()[1]+"");
		tfRotationZ.setText(objeto.getRotacao()[2]+"");
		tfScaleX.setText(objeto.getEscala()[0]+"");
		tfScaleY.setText(objeto.getEscala()[1]+"");
		tfScaleZ.setText(objeto.getEscala()[2]+"");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(bCancela)){
			dispose();
			return;
		}
		
		if(e.getSource().equals(bAlterar)){
			double[] t = new double[3]; 
			t[0] =	new Double(tfTranslationX.getText());
			t[1] =	new Double(tfTranslationY.getText());
			t[2] =	new Double(tfTranslationZ.getText());
			double[] r = new double[4];			
			r[0] =	new Double(tfRotationX.getText());
			r[1] =	new Double(tfRotationY.getText());
			r[2] =	new Double(tfRotationZ.getText());
			r[3] = new Double("0.0");
			int[] s = new int[3]; 
			s[0] =	new Integer(tfScaleX.getText());
			s[1] =	new Integer(tfScaleY.getText());
			s[2] =	new Integer(tfScaleZ.getText());
			objeto.setTranslacao(t);
			objeto.setRotacao(r);
			objeto.setEscala(s);
			controlObjeto.alterarPropriedades(objeto);
			dispose();
		}
	}
}
