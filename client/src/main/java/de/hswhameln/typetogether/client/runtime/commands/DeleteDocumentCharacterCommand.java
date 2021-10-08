package de.hswhameln.typetogether.client.runtime.commands;

import de.hswhameln.typetogether.client.gui.AbstractPanel;
import de.hswhameln.typetogether.client.gui.CommandPanel;
import de.hswhameln.typetogether.client.gui.EditorPanel;
import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import java.util.logging.Logger;


/**
 * {@link Command} that deletes a (second)
 * 
 */
public class DeleteDocumentCharacterCommand implements Command {

	private final Logger logger = LoggerFactory.getLogger(this);
	private LocalDocument localDocument;
	private Document sharedDocument;
	private User user;
	private final DocumentCharacter character;

	public DeleteDocumentCharacterCommand(DocumentCharacter character, User user, LocalDocument localDocument, Document sharedDocument) {
		this.character = character;
		this.user = user;
		this.localDocument = localDocument;
		this.sharedDocument = sharedDocument;
	}
	@Override
	public void execute() {
		this.localDocument.removeLocalChar(this.character);
		new Thread(() -> this.sharedDocument.removeChar(this.user, this.character)).start();
		this.logger.info(String.format("Removed character '%s' within comand", this.character.getStringRepresentation()));
	}

	@Override
	public void revert() {
		this.localDocument.addLocalChar(this.character);
		new Thread(() -> this.sharedDocument.addChar(this.user, this.character)).start();
		this.logger.info(String.format("Reverted removing character '%s' within command", this.character.getStringRepresentation()));
	}

	@Override
	public void redo() {
		this.localDocument.removeChar(this.user, this.character);
		new Thread(() -> this.sharedDocument.removeChar(this.user, this.character)).start();
		this.logger.info(String.format("Redid removing character '%s' within comand", this.character.getStringRepresentation()));
	}
}
