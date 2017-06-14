import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;


public class Apriori extends JApplet {
	private JTextField nameDatatxt;
	private JTextField nomeMinSupTxt;
	private JTextField nomeMinConfTxt;
	private JButton aprioriButton; 
	private JTextArea rulesAreaTxt;
	private JRadioButton caricamentoDb;
	private JRadioButton caricamentoFile;
	private Container c;
public Apriori() {
	c= getContentPane();
	c.setLayout(new BorderLayout());
	
	JPanel cpParameterSetting = new JPanel();
	cpParameterSetting.setLayout(new BorderLayout());
	TitledBorder title= new TitledBorder("Apriori");
	cpParameterSetting.setBorder(title);
		
		JPanel cpArioriMining = new JPanel();
		title= new TitledBorder("Selecting data source");
		cpArioriMining.setBorder(title);
		cpArioriMining.setLayout(new GridLayout(2,1));
		ButtonGroup groupRadio = new ButtonGroup();
		caricamentoDb = new JRadioButton("Loarning rules from DB");
		caricamentoFile = new JRadioButton("Loarning rules from file");
		caricamentoFile.addActionListener(new ActionRadioButton());
		caricamentoDb.addActionListener(new ActionRadioButton());
		caricamentoDb.setSelected(true);
		groupRadio.add(caricamentoDb);
		groupRadio.add(caricamentoFile);
		cpArioriMining.add(caricamentoDb);
		cpArioriMining.add(caricamentoFile);
			
		cpParameterSetting.add(cpArioriMining, BorderLayout.WEST);
	
		JPanel cpAprioriInput= new JPanel();
		title= new TitledBorder("Input parameters");
		cpAprioriInput.setBorder(title);
		JLabel labelData = new JLabel("Data");
		nameDatatxt= new JTextField(10);
		JLabel labelMinSup = new JLabel("MinSup");
		nomeMinSupTxt = new JTextField(10);
		JLabel labelMinConf = new JLabel("MinConf");
		nomeMinConfTxt = new JTextField(10);
		cpAprioriInput.add(labelData);
		cpAprioriInput.add(nameDatatxt);
		cpAprioriInput.add(labelMinSup);
		cpAprioriInput.add(nomeMinSupTxt);
		cpAprioriInput.add(labelMinConf);
		cpAprioriInput.add(nomeMinConfTxt);
			
		cpParameterSetting.add(cpAprioriInput, BorderLayout.EAST);
	c.add(cpParameterSetting, BorderLayout.NORTH);
	
		JPanel cpMininingCommand = new JPanel();
		aprioriButton = new JButton("apriori");
		aprioriButton.addActionListener(new ActionEvent());
		cpMininingCommand.add(aprioriButton);
	
	c.add(cpMininingCommand, BorderLayout.CENTER);
	
		JPanel cpRuleViewer = new JPanel();
		cpRuleViewer.setLayout(new GridLayout(1,1));
		title= new TitledBorder("Patterns and Rules");
		cpRuleViewer.setBorder(title);
		rulesAreaTxt = new JTextArea(9,10);
		rulesAreaTxt.setEditable(false);
		JScrollPane scroll = new JScrollPane(rulesAreaTxt, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		
		
		
		cpRuleViewer.add(scroll);
		
		
	c.add(cpRuleViewer, BorderLayout.SOUTH);
	
}
private class ActionRadioButton implements ActionListener{

	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if(e.getSource()==caricamentoFile && caricamentoFile.isSelected()){
			nomeMinConfTxt.setEditable(false);
			nameDatatxt.setEditable(false);
			nomeMinSupTxt.setEditable(false);
			
		}else{
			nomeMinConfTxt.setEditable(true);
			nameDatatxt.setEditable(true);
			nomeMinSupTxt.setEditable(true);
		}
			
			
		
	}
	
}
private class ActionEvent implements ActionListener{

	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		Socket s = null;
		InetAddress ip;
		try{
		ip = InetAddress.getByName("127.0.0.1");
		s= new Socket(ip,8080);
		ObjectOutputStream out= new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream in= new ObjectInputStream(s.getInputStream());
		if(caricamentoDb.isSelected()){
			if(nameDatatxt.getText().equals("")||nomeMinSupTxt.getText().equals("")||nomeMinConfTxt.getText().equals("")){
				JOptionPane.showMessageDialog(c, "Errore: nessun inserimento dati");
			}
			else{
				float minSup,minConf;
				
				out.writeObject(1);
				
				minSup=Float.parseFloat(nomeMinSupTxt.getText());
				if (minSup<0 || minSup>1){
					JOptionPane.showMessageDialog(c, "Errore: minSup non compreso tra 0 e 1");
					throw new InputException();
				}
				minConf=Float.parseFloat(nomeMinConfTxt.getText());
				if(minConf<0 || minConf>1){
					JOptionPane.showMessageDialog(c, "Errore: minConf non compreso tra 0 e 1");
					throw new InputException();
				}
				out.writeObject(nameDatatxt.getText());
				out.writeObject(minSup);
				out.writeObject(minConf);
				String ris= (String) in.readObject();
				if(ris.compareTo("CON SUCCESSO")==0){
					ris=(String)in.readObject();
					rulesAreaTxt.setText(ris);
					String nomeFile =  JOptionPane.showInputDialog ("Inserire il nome del file", ""); 
					out.writeObject(2);
					out.writeObject(nomeFile);
					JOptionPane.showMessageDialog(c,"Pattern salvati " + in.readObject());
				}
				else{
					JOptionPane.showMessageDialog(c, ris);
					
				}
				
				
			
			}
		
		}
		else{
			
			String nomeFile =  JOptionPane.showInputDialog ("Inserire il nome del file", "");
			out.writeObject(3);
			out.writeObject(nomeFile);
			String ris="Caricamento file...\n";
			rulesAreaTxt.setText(ris);
			String ris1=(String) in.readObject();
			if(ris1.equals("Nome del file inesistente")){
				rulesAreaTxt.setText("");
				JOptionPane.showMessageDialog(c, "Errore: "+ ris1);
			}else{
				ris+=ris1;
				rulesAreaTxt.setText(ris);
			}
		}
		}
		catch(IOException e1){
			JOptionPane.showMessageDialog(c, "Errore durante la connessione");
		}catch(InputException e2){
			
		} catch (ClassNotFoundException e3) {
		}finally {
		
			try {
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
}
