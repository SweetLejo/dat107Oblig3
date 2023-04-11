package dat107.oblig3.gui.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.entity.ProjectParticipation;
import dat107.oblig3.gui.collection.ProjectParticipationList;
import dat107.oblig3.gui.controller.ParticipationController;
import dat107.oblig3.gui.inputcontrols.EntityComboBox;
import dat107.oblig3.gui.inputcontrols.NumericField;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class ParticipationsWidget extends Widget {
	
	private final ParticipationController controller;
	private final ProjectParticipationList participationsList;
	private final JScrollPane listScrollPane;
	private final ParticipationEditorWidget editorWidget;
	private final JButton deleteButton;
	private final JButton editButton;
	private final JButton newParticipationButton;
	
	public ParticipationsWidget(Screen screen) {
		super("Project Participations");
		this.controller = new ParticipationController(screen, this);
		this.participationsList = new ProjectParticipationList();
		this.listScrollPane = new JScrollPane(participationsList);
		this.editorWidget = new ParticipationEditorWidget(controller);
		this.deleteButton = createWidgetButton("Delete", e -> controller.deleteParticipation());
		this.editButton = createWidgetButton("Edit", e -> controller.editParticipation());
		this.newParticipationButton = createWidgetButton("Add New Participation", e -> controller.createNewParticipation());

		configureComponents();
		addComponents();
//		screen.validate();
	}
	
	private void configureComponents() {
		editorWidget.setBorder(BorderFactory.createEmptyBorder());
		
		participationsList.addSelectionListener(selected -> {
			controller.setSelectedParticipation(selected);
			
			setEditAndDeleteButtonsEnabled(selected != null);
		});
		
		listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		setEditAndDeleteButtonsEnabled(false);
	}
	
	private void setEditAndDeleteButtonsEnabled(boolean enable) {
		deleteButton.setEnabled(enable);
		editButton.setEnabled(enable);
	}
	
	private void addComponents() {
		addFullWidthField(listScrollPane);
		setButtons(deleteButton, editButton, newParticipationButton);
	}
	
	public void updateParticipationsList(Employee employee) {
		participationsList.setListContent(ProjectParticipationList.ListContent.PROJECT);
		
		if(employee != null) {
			participationsList.updateContent(employee.getParticipations());
		} else {
			participationsList.updateContent(Collections.emptyList());
		}
	}
	
	public void updateParticipationsList(Project project) {
		participationsList.setListContent(ProjectParticipationList.ListContent.EMPLOYEE);
		
		if(project != null) {
			participationsList.updateContent(project.getParticipations());
		} else {
			participationsList.updateContent(Collections.emptyList());
		}
	}
	
	public void showParticipationEditorWidget() {
		if(!editorWidget.isShowing()) {
			addFullWidthField(editorWidget);
		}
	}
	
	public void hideParticipationEditorWidget() {
		if(editorWidget.isShowing()) {
			removeField(editorWidget);
		}
	}
	
	public void showButtons() {
		if(!buttonsAreShowing()) {
			setButtons(deleteButton, editButton, newParticipationButton);
		}
	}
	
	public void hideButtons() {
		if(buttonsAreShowing()) {
			setButtons();
		}
	}
	
	private boolean buttonsAreShowing() {
		return editButton.isShowing() 
				&& deleteButton.isShowing() 
				&& newParticipationButton.isShowing();
	}
	
	public ProjectParticipationList getParticipationList() {
		return participationsList;
	}
	
	public ParticipationEditorWidget getParticipationEditorWidget() {
		return editorWidget;
	}
	
}
