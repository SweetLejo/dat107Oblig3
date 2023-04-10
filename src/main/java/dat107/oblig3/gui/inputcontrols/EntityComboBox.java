package dat107.oblig3.gui.inputcontrols;

import java.awt.Component;
import java.awt.Graphics;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.JTextComponent;

import dat107.oblig3.dao.DAO;
import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.dao.ProjectDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.gui.UITheme;

@SuppressWarnings("serial")
public class EntityComboBox<T> extends JComboBox<T> {
	
	private EntityComboBoxModel model;
	
	public EntityComboBox(Supplier<List<T>> contentSupplier) {
		setModel(new EntityComboBoxModel(contentSupplier));
		
		configureComboBox();
	}
	
	public void setModel(EntityComboBoxModel model) {
		super.setModel(model);
		
		this.model = model;
	}
	
	public void configureComboBox() {
		setOpaque(false);
		setBackground(ToggleableTextField.DEFAULT_BACKGROUND);
		
		configureRenderer();
		configureEditor();
		configureUI();
	}
	
	private void configureUI() {
		UIManager.put("ComboBox.disabledForeground", 
				ToggleableTextField.DEFAULT_UNEDITABLE_FOREGROUND);
		UIManager.put("ComboBox.disabledBackground", 
				ToggleableTextField.DEFAULT_BACKGROUND);
		
		setUI(new BasicComboBoxUI() {
			@Override
			protected void installDefaults() {
				super.installDefaults();
				LookAndFeel.uninstallBorder(comboBox);
				comboBox.setBorder(BorderFactory.createLineBorder(
						UITheme.ALTERNATIVE_BACKGROUND_COLOR, 5));
			}

			@Override
			public void configureArrowButton() {
				super.configureArrowButton();
				arrowButton.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
				arrowButton.setForeground(UITheme.LIGHT_ACCENT_COLOR);
				arrowButton.setBorder(BorderFactory.createLineBorder(UITheme.ALTERNATIVE_BACKGROUND_COLOR, 5));
			}
		});
	}
	
	private void configureRenderer() {
		setRenderer(new DefaultListCellRenderer() {
			@Override 
			public Component getListCellRendererComponent(JList<?> list, Object value,
					int index, boolean isSelected, boolean cellHasFocus) {
				Component component = super.getListCellRendererComponent(
						list, value, index, isSelected, cellHasFocus);
				
				if(!isSelected) {
					component.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
					component.setForeground(UITheme.DEFAULT_TEXT_COLOR);
				}
				
				return component;
			}
		});
	}
	
	private void configureEditor() {
		setEditor(new BasicComboBoxEditor() {
			@Override public JTextField createEditorComponent() {
		        return new ToggleableTextField(9);
			}	
		});
	}
	
	@Override
	public void setEditable(boolean editable) {
		setEnabled(editable);
		
		setBackground(ToggleableTextField.DEFAULT_BACKGROUND);
		
		if(editable) {
			setForeground(ToggleableTextField.DEFAULT_EDITABLE_FOREGROUND);
			setBorder(ToggleableTextField.DEFAULT_EDITABLE_BORDER);
		} else {
			setForeground(ToggleableTextField.DEFAULT_UNEDITABLE_FOREGROUND);
			setBorder(ToggleableTextField.DEFAULT_UNEDITABLE_BORDER);
		}
		
		ToggleableTextField editorComponent = 
				((ToggleableTextField)getEditor().getEditorComponent());
		editorComponent.setEditable(editable);
	}
	
	public void refresh() {
		model.refresh();
	}
	
	public static EntityComboBox<Department> createDepartmentComboBox() {
		DepartmentDAO dao = new DepartmentDAO();
		
		return new EntityComboBox<>(() -> dao.getAll());
	}
	
	public static EntityComboBox<Project> createProjectComboBox() {
		ProjectDAO dao = new ProjectDAO();
		
		return new EntityComboBox<>(() -> dao.getAll());
	}
	
	public static EntityComboBox<Employee> createEmployeeComboBox() {
		EmployeeDAO dao = new EmployeeDAO();
		
		return new EntityComboBox<>(() -> dao.getAll());
	}
	
	public class EntityComboBoxModel extends AbstractListModel<T> 
		implements ComboBoxModel<T> {
		
		private Supplier<List<T>> contentSupplier; 
		
		private List<T> entities;
		private int selected = -1;
		
		public EntityComboBoxModel(Supplier<List<T>> contentSupplier) {
			this.contentSupplier = contentSupplier;
			
			refresh();
		}
		
		public void refresh() {
			entities = contentSupplier.get();
			
			fireContentsChanged(this, -1, -1);
			
			setSelectedItem(null);
		}
		
		@Override
		public void setSelectedItem(Object item) {
			selected = getIndexOf(item);
			
			fireContentsChanged(this, -1, -1);
		}
		
		public int getIndexOf(Object item) {
			return entities.indexOf(item);
		}
		
		@Override
		public T getSelectedItem() {
			if(selected == -1 || selected >= entities.size()) {
				return null;
			}
			
			return entities.get(selected);
		}
		
		@Override
		public int getSize() {
			return entities.size();
		}
		
		@Override
		public T getElementAt(int index) {
			if(index == -1 || index >= entities.size()) {
				return null;
			}
			
			return entities.get(index);
		}
		
		public List<T> getList() {
			return entities;
		}
	
	}

}

