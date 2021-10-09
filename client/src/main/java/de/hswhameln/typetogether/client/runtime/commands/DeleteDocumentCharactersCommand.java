package de.hswhameln.typetogether.client.runtime.commands;

import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.util.Collection;
import java.util.logging.Logger;


/**
 * {@link Command} that deletes a (second)
 * 
 */
public class DeleteDocumentCharactersCommand implements Command {

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private LocalDocument localDocument;
	private Document sharedDocument;
	private User user;
	private final Collection<DocumentCharacter> characters;

	public DeleteDocumentCharactersCommand(Collection<DocumentCharacter> characters, User user, LocalDocument localDocument, Document sharedDocument) {
		this.characters = characters;
		this.user = user;
		this.localDocument = localDocument;
		this.sharedDocument = sharedDocument;
	}
	@Override
	public void execute() {
		this.characters.forEach(this.localDocument::removeLocalChar);
		new Thread(() -> this.sharedDocument.removeChars(this.user, this.characters)).start();
		this.logger.info(String.format("Deleted %d characters within command the first of which being %s",
				this.characters.size(),
				this.characters.stream().findAny().map(DocumentCharacter::getStringRepresentation).orElse("Empty")));
	}

	@Override
	public void revert() {
		this.localDocument.addChars(this.user, this.characters);
		new Thread(() -> this.sharedDocument.addChars(this.user, this.characters)).start();
		this.logger.info(String.format("Reverting deleted %d characters within command the first of which being %s",
				this.characters.size(),
				this.characters.stream().findAny().map(DocumentCharacter::getStringRepresentation).orElse("Empty")));
	}

	@Override
	public void redo() {
		this.localDocument.removeChars(this.user, this.characters);
		new Thread(() -> this.sharedDocument.removeChars(this.user, this.characters)).start();
		this.logger.info(String.format("Redid deleting %d characters within command the first of which being %s",
				this.characters.size(),
				this.characters.stream().findAny().map(DocumentCharacter::getStringRepresentation).orElse("Empty")));
	}
}
