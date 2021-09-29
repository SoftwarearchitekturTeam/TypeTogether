package de.hswhameln.typetogether.client.businesslogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

public class LocalDocument implements Document {

    private String funcId;

    private List<DocumentCharacter> content;

    public LocalDocument() {
        this.content = new ArrayList<>();
        this.funcId = UUID.randomUUID().toString();
    }
    
    @Override
    public void addChar(User author,DocumentCharacter character) {
        this.content.add(character);
        Collections.sort(this.content);
        
        //Refresh gui (observer pattern)
    }

    @Override
    public void removeChar(User author,DocumentCharacter character) {
       this.content.remove(character);
       //Refresh gui (observer pattern)
    }

    @Override
    public String getFuncId() {
        return this.funcId;
    }

    public int getIndexOfChar(char c, char[] arr) {
        return 0;
    }

    public DocumentCharacter getDocumentCharacterOfIndex(int index) {
        return this.content.get(index);
    }

    // get adjacent DocumentCharacters (x2) (maybe)

    
}