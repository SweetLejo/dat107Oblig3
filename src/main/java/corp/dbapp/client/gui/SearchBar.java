package corp.dbapp.client.gui;

import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchBar extends JPanel {

	private JTextField searchField = new JTextField(20);
	private JButton searchButton = new JButton("Search");
	private JLabel searchOptionLabel = new JLabel("Search by:");
	
	private JComboBox<String> searchOptions;
	
	private Map<String, Consumer<String>> responder = new HashMap<>();
	
	public SearchBar(String[] searchByOptions) {
		this.searchOptions = new JComboBox<String>(searchByOptions);
		setLayout(new FlowLayout());
		setAlignmentX(CENTER_ALIGNMENT);
		
		searchButton.addActionListener(e -> buttonPressed());
		
		add(searchOptionLabel);
		add(searchOptions);
		add(searchField);
		add(searchButton);
	}
	
	private void buttonPressed() {
		responder.get(searchOptions.getSelectedItem()).accept(searchField.getText());
	}
	
	public void setResponder(String key, Consumer<String> onSearch) {
		responder.put(key, onSearch);
	}
	
	
}