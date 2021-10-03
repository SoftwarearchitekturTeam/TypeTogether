package de.hswhameln.typetogether.client.runtime.commands.base;

import de.hswhameln.typetogether.client.gui.CommandPanel;

import java.util.Stack;



/**
 * Class responsible for invoking comments.
 * <p>
 * It implements an undo/redo-mechanism and updates the View's undoable and
 * redoable visualizations.
 *
 */
public class CommandInvoker {

	private final Stack<Command> undoStack;
	private final Stack<Command> redoStack;
	private CommandPanel panel;
    

	public CommandInvoker() {
		this.undoStack = new Stack<>();
		this.redoStack = new Stack<>();
	//	this.view.addEventHandler(EventId.UNDO, (params) -> this.undo());
	//	this.view.addEventHandler(EventId.REDO, (params) -> this.redo());
	}

	public void setCommandPanel(CommandPanel panel) {
		this.panel = panel;
	}

	public void execute(Command command) {
		
		command.execute();
		this.undoStack.push(command);
		this.redoStack.clear();
		updateView();
	//	updateViewValueChanged();
	}

	public void undo() {
		
		Command command = this.undoStack.pop();
	
		this.redoStack.push(command);
		command.revert();
		updateView();
	}

	public void redo() {
		
		Command command = this.redoStack.pop();
		
		this.undoStack.push(command);
		command.execute();
		//updateViewValueChanged();
	}
//TODO noch ergänzen
//	private void updateViewValueChanged() {
//		updateView();
//		this.view.notifyValuesChanged();
//	}
	
	private void updateView() {
		this.panel.changeClickableUndo(!this.undoStack.isEmpty());
		this.panel.changeClickableRedo(!this.redoStack.isEmpty());
	}
}
