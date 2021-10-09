package de.hswhameln.typetogether.client.runtime.commands;

import de.hswhameln.typetogether.client.gui.CommandPanel;
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class responsible for invoking comments.
 * <p>
 * It implements an undo/redo-mechanism and updates the View's undoable and
 * redoable visualizations.
 *
 */
public class CommandInvoker {

	private final Logger logger = LoggerFactory.getLogger(this);
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
		this.logger.log(Level.INFO, "Undo called. undoStack has a size of " + this.undoStack.size());
		if(!this.undoStack.isEmpty()) {
			Command command = this.undoStack.pop();

			this.redoStack.push(command);
			command.revert();
			updateView();
		}
	}

	public void redo() {
		this.logger.log(Level.INFO, "Redo called. redoStack has a size of " + this.redoStack.size());
		if(!this.redoStack.isEmpty()) {
			Command command = this.redoStack.pop();

			this.undoStack.push(command);
			command.redo();
			updateView();
		}
	}
	
	private void updateView() {
		this.panel.changeClickableUndo(!this.undoStack.isEmpty());
		this.panel.changeClickableRedo(!this.redoStack.isEmpty());
	}
}
