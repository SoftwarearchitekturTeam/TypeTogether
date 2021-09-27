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
    public void doCharectertestUseCase1InsertCharacter(){
        
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
    public void doCharectertestUseCase2(){
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        befor.add(new Identifier(1, 2));
        after.add(new Identifier(1, 3));
        list.add(new DocumentCharacter('s', new Identifier(1, 1)));
        list.add(new DocumentCharacter('a', new Identifier(1, 2)));
        list.add(new DocumentCharacter('g', new Identifier(1, 3)));
        list.add(new DocumentCharacter('e', befor, after, 4));
        Collections.sort(list);
        String erg = "";

        for(DocumentCharacter character : list) {
            erg = erg + character.getValue();
        }
        assertEquals("saeg", erg);
    }

    //Refers to Issue #1
    @Disabled
    @Test
    public void doCharectertestUseCase2InsertBeforeFirst(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        after.add(new Identifier(1, 1));
        list.add(new DocumentCharacter('s', new Identifier(1, 1)));
        list.add(new DocumentCharacter('a', new Identifier(1, 2)));
        list.add(new DocumentCharacter('g', new Identifier(1, 3)));
        list.add(new DocumentCharacter('i', befor, after, 4));
        Collections.sort(list);
        String erg = "";

        for(DocumentCharacter character : list) {
            erg = erg + character.getValue();
        }
        assertEquals("isag", erg);
    }

    @Disabled
    @Test
    public void doCharectertestUseCase2SmalerUserId(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        befor.add(new Identifier(1, 3));
        after.add(new Identifier(1, 2));
        list.add(new DocumentCharacter('s', new Identifier(1, 1)));
        list.add(new DocumentCharacter('a', new Identifier(1, 3)));
        list.add(new DocumentCharacter('g', new Identifier(1, 2)));
        list.add(new DocumentCharacter('e', befor, after, 4));
        Collections.sort(list);
        String erg = "";

        for(DocumentCharacter character : list) {
            erg = erg + character.getValue();
        }
        assertEquals("saeg", erg);
    }

    @Disabled
    @Test
    public void doCharectertestUseCase3(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> before = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        
        list.add(new DocumentCharacter('s', new Identifier(1, 1)));
        list.add(new DocumentCharacter('n', new Identifier(2, 1)));

        before.add(new Identifier(1, 1));
        after.add(new Identifier(2, 1));
        list.add(new DocumentCharacter('a', before, after, 2));

        before.clear();
        after.clear();
        before.add(new Identifier(1, 1));
        before.add(new Identifier(1, 2));
        after.add(new Identifier(2, 1));
        list.add(new DocumentCharacter('e', before, after, 2));

        before.clear();
        after.clear();
        before.add(new Identifier(1, 1));
        before.add(new Identifier(1, 2));
        after.add(new Identifier(1, 1));
        after.add(new Identifier(2, 2));
        list.add(new DocumentCharacter('g', before, after, 3));
        Collections.sort(list);

        String erg = "";
        for(DocumentCharacter character : list) {
            erg = erg + character.getValue();
        }
        assertEquals("sagen", erg);
    }

    @Test
    public void testGetStringRepresentation() {
        var documentCharacter = new DocumentCharacter('x', List.of(new Identifier(4711, 42), new Identifier(11, 22)));
        assertEquals("<x-(4711|42),(11|22)>", documentCharacter.getStringRepresentation());
    }

    @Test
    public void testParse() {
        String representation = "<x-(4711|42),(11|22)>";
        var expected = new DocumentCharacter('x', List.of(new Identifier(4711, 42), new Identifier(11, 22)));
        assertEquals(0, expected.compareTo(DocumentCharacter.parse(representation)));
    }
}
