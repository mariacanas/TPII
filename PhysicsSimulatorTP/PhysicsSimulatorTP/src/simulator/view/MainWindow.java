package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
		
		new InfoTable("Groups", new GroupsTableModel(_ctrl));
		new InfoTable("Bodies", new BodiesTableModel(_ctrl));
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		//Crear ControlPanel y añadirlo en PAGE_START de mainPanel
		//Crear StatusBar y añadirlo en PAGE_END de mainPanel
		ControlPanel cp = new ControlPanel(_ctrl);
		StatusBar sb = new StatusBar(_ctrl);
		mainPanel.add(cp,BorderLayout.PAGE_START);
		mainPanel.add(sb,BorderLayout.PAGE_END);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		//Crear la tabla de grupos y añadirla a contentPanel
			//Usar setPreferredSize(new Dimension(500,250)) para fijar su tamaño
		
		//Crear la tabla de cuerpos y añadirla a contentPAnel
			//Usar setPreferrededSize(new Dimension(500,250)) para fijar su tamaño
		InfoTable groupTable = new InfoTable("Groups", new GroupsTableModel(_ctrl));
		groupTable.setPreferredSize(new Dimension(500,250));
		contentPanel.add(groupTable);
		
		InfoTable bodieTable = new InfoTable("Bodies", new BodiesTableModel(_ctrl));
		bodieTable.setPreferredSize(new Dimension(500,250));
		contentPanel.add(bodieTable);
		
		//LLamar a Utils.quit(MainWindow.this) en el método windowClosing
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent even) {
				Utils.quit(MainWindow.this);
			}
		});
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
