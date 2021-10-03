package de.hswhameln.typetogether.client.runtime.commands.base;


import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.util.logging.Logger;


/**
 * {@link Command} that creates a (second) {@link Reviewer} 
 * 
 */
public class CreateDocumentCharacterCommand extends DefaultCommand{

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private LocalDocument localDocument;
	private Document sharedDocument;
	private User user;
	private final DocumentCharacter character;
	
	public CreateDocumentCharacterCommand(DocumentCharacter character, User user, LocalDocument localDocument, Document sharedDocument) {
		this.character = character;
		this.user = user;
		this.localDocument = localDocument;
		this.sharedDocument = sharedDocument;
	}
	
	@Override
	public void execute() {
		this.localDocument.addLocalChar(this.character);
		this.sharedDocument.addChar(this.user, this.character);
		this.logger.info(String.format("Added character '%s' within command", this.character.getStringRepresentation()));
	}

	@Override
	public void revert() {
		this.localDocument.removeLocalChar(this.character);
		this.sharedDocument.removeChar(this.user, this.character);
		this.logger.info(String.format("Reverted adding character '%s' within comand", this.character.getStringRepresentation()));
	}
}
