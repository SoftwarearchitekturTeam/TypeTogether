package de.hswhameln.typetogether.client.runtime.commands.base;

import de.hswhameln.typetogether.networking.types.DocumentCharacter;


/**
 * {@link Command} that deletes a (second) {@link Reviewer}
 * 
 */
public class DeleteDocumentCharacterCommand extends DefaultCommand {

	private DocumentCharacter character;
	public DeleteDocumentCharacterCommand(DocumentCharacter character) {
		this.character = character;
	}

	@Override
	public void execute() {
		System.out.println("execute");
	}

	@Override
	public void revert() {
		System.out.println("revert");
	}
}
