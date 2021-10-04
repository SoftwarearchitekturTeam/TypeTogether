package de.hswhameln.typetogether.client.runtime.commands;

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
	}

	public void setCommandPanel(CommandPanel panel) {
		this.panel = panel;
	}

	public void execute(Command command) {
		
		command.execute();
		this.undoStack.push(command);
		this.redoStack.clear();
		updateView();
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
		command.redo();
		updateView();
		//updateViewValueChanged();
	}
	
	private void updateView() {
		this.panel.changeClickableUndo(!this.undoStack.isEmpty());
		this.panel.changeClickableRedo(!this.redoStack.isEmpty());
	}
}
