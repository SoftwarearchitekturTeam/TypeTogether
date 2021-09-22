package de.hswhameln.typetogether.networking.types;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DocumentCharacterTest {
   
    @Test
    public void doTest() {
        System.out.println("Test");
    }

    @Test
    public void doCharectertestfUseCase1() {
        List<DocumentCharacter> list = new ArrayList<>();
        list.add(new DocumentCharacter('s', new Identifier(1, 1)));
        list.add(new DocumentCharacter('e', new Identifier(3, 1)));
        list.add(new DocumentCharacter('a', new Identifier(2, 1)));
        list.add(new DocumentCharacter('g', new Identifier(4, 1)));
        list.add(new DocumentCharacter('n', new Identifier(5, 1)));
        Collections.sort(list);
        String erg = "";
        
        for(DocumentCharacter character : list) {
            erg = erg + character.getValue();
        }
        assertEquals("saegn", erg);
    }

    @Test
    public void doCharectertestUseCase2(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        befor.add(new Identifier(2, 1));
        after.add(new Identifier(3, 1));
        list.add(new DocumentCharacter('s', new Identifier(1, 1)));
        list.add(new DocumentCharacter('a', new Identifier(2, 1)));
        list.add(new DocumentCharacter('g', new Identifier(3, 1)));
        list.add(new DocumentCharacter('e', befor, after, 2));
        Collections.sort(list);
        String erg = "";

        for(DocumentCharacter character : list) {
            erg = erg + character.getValue();
        }
        assertEquals("saeg", erg);
    }

    @Test
    public void doCharectertestcomplexUseCase3(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        
        list.add(new DocumentCharacter('s', new Identifier(1, 1)));
        list.add(new DocumentCharacter('a', new Identifier(2, 1)));
        list.add(new DocumentCharacter('g', new Identifier(3, 1)));
        befor.add(new Identifier(2, 1));
        after.add(new Identifier(3, 1));
        list.add(new DocumentCharacter('r', befor, after, 2));
        befor.add(new Identifier(1, 2));
        after.add(new Identifier(3, 1));
        list.add(new DocumentCharacter('k', befor, after, 2));
       after.clear();
        after.add(new Identifier(2, 1));
        after.add(new Identifier(2,2));
        list.add(new DocumentCharacter('e', befor, after, 3));
        Collections.sort(list);

        String erg = "";
        for(DocumentCharacter character : list) {
            erg = erg + character.getValue();
        }
        assertEquals("sarekg", erg);
    }
}
