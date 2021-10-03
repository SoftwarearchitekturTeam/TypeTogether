package de.hswhameln.typetogether.client.runtime.commands.base;


import de.hswhameln.typetogether.networking.types.DocumentCharacter;


/**
 * {@link Command} that creates a (second) {@link Reviewer} 
 * 
 */
public class CreateDocumentCharacterCommand extends DefaultCommand{

	
	private DocumentCharacter character; 
	
	public CreateDocumentCharacterCommand(DocumentCharacter character) {
		this.character = character;
	}
	
	@Override
	public void execute() {
		System.out.println("execute");
	}

	@Override
	public void revert() {
		
		System.out.println("reveter");
	}
}
