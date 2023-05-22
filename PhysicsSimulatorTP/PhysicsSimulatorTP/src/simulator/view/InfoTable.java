package simulator.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel{

	String _title;
	TableModel _tableModel;
	
	InfoTable(String title, TableModel tableModel){
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}
	
	private void initGUI() {
		//Cambiar el layout del panel a BorderLayout()
		//Añadir un borde con título al JPanel, con el texto _title
		//Añadir un JTable (Con barra de desplazamiento vertical) que use _tableModel
		BorderLayout bl = new BorderLayout();
		this.setLayout(bl);
		Border b = BorderFactory.createTitledBorder(_title);
        this.setBorder(b);
        JTable table = new JTable(_tableModel);
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);
	}
}
