package de.hswhameln.typetogether.networking.types;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.hswhameln.typetogether.networking.util.DocumentCharacterFactory;

public class DocumentCharacterTest {
   
    @Test
    public void doTest() {
        System.out.println("Test");
    }

    @Test
    public void doCharectertestfUseCase1() {
        List<DocumentCharacter> list = new ArrayList<>();
        list.add(new DocumentCharacter('s', List.of(new Identifier(1, 1))));
        list.add(new DocumentCharacter('e', List.of(new Identifier(3, 1))));
        list.add(new DocumentCharacter('a', List.of(new Identifier(2, 1))));
        list.add(new DocumentCharacter('g', List.of(new Identifier(4, 1))));
        list.add(new DocumentCharacter('n', List.of(new Identifier(5, 1))));
        Collections.sort(list);
        StringBuilder erg = new StringBuilder();
        
        for(DocumentCharacter character : list) {
            erg.append(character.getValue());
        }
        assertEquals("saegn", erg.toString());
    }

    @Test
    public void doCharectertestUseCase1InsertCharacter(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        befor.add(new Identifier(2, 1));
        after.add(new Identifier(3, 1));
        list.add(new DocumentCharacter('s', List.of(new Identifier(1, 1))));
        list.add(new DocumentCharacter('a', List.of(new Identifier(2, 1))));
        list.add(new DocumentCharacter('g', List.of(new Identifier(3, 1))));
        list.add(DocumentCharacterFactory.getDocumentCharacter('e', befor, after, 2));
        Collections.sort(list);
        StringBuilder erg = new StringBuilder();

        for(DocumentCharacter character : list) {
            erg.append(character.getValue());
        }
        assertEquals("saeg", erg.toString());
    }

    @Test
    public void doCharectertestUseCase2(){
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        befor.add(new Identifier(1, 2));
        after.add(new Identifier(1, 3));
        list.add(new DocumentCharacter('s', List.of(new Identifier(1, 1))));
        list.add(new DocumentCharacter('a', List.of(new Identifier(1, 2))));
        list.add(new DocumentCharacter('g', List.of(new Identifier(1, 3))));
        list.add(DocumentCharacterFactory.getDocumentCharacter('e', befor, after, 4));
        Collections.sort(list);
        StringBuilder erg = new StringBuilder();

        for(DocumentCharacter character : list) {
            erg.append(character.getValue());
        }
        assertEquals("saeg", erg.toString());
    }

    //Refers to Issue #1
    @Disabled
    @Test
    public void doCharectertestUseCase2InsertBeforeFirst(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        after.add(new Identifier(1, 1));
        list.add(new DocumentCharacter('s', List.of(new Identifier(1, 1))));
        list.add(new DocumentCharacter('a', List.of(new Identifier(1, 2))));
        list.add(new DocumentCharacter('g', List.of(new Identifier(1, 3))));
        list.add(DocumentCharacterFactory.getDocumentCharacter('i', befor, after, 4));
        Collections.sort(list);
        StringBuilder erg = new StringBuilder();

        for(DocumentCharacter character : list) {
            erg.append(character.getValue());
        }
        assertEquals("isag", erg.toString());
    }

    @Disabled
    @Test
    public void doCharectertestUseCase2SmalerUserId(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> befor = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        befor.add(new Identifier(1, 3));
        after.add(new Identifier(1, 2));
        list.add(new DocumentCharacter('s', List.of(new Identifier(1, 1))));
        list.add(new DocumentCharacter('a', List.of(new Identifier(1, 3))));
        list.add(new DocumentCharacter('g', List.of(new Identifier(1, 2))));
        list.add(DocumentCharacterFactory.getDocumentCharacter('e', befor, after, 4));
        Collections.sort(list);
        StringBuilder erg = new StringBuilder();

        for(DocumentCharacter character : list) {
            erg.append(character.getValue());
        }
        assertEquals("saeg", erg.toString());
    }

    @Test
    public void doCharectertestUseCase3(){
        
        List<DocumentCharacter> list = new ArrayList<>();
        List<Identifier> before = new ArrayList<>();
        List<Identifier> after = new ArrayList<>();
        
        list.add(new DocumentCharacter('s', List.of(new Identifier(1, 1))));
        list.add(new DocumentCharacter('n', List.of(new Identifier(2, 1))));

        before.add(new Identifier(1, 1));
        after.add(new Identifier(2, 1));
        list.add(DocumentCharacterFactory.getDocumentCharacter('a', before, after, 2));

        before.clear();
        after.clear();
        before.add(new Identifier(1, 1));
        before.add(new Identifier(1, 2));
        after.add(new Identifier(2, 1));
        list.add(DocumentCharacterFactory.getDocumentCharacter('e', before, after, 2));

        before.clear();
        after.clear();
        before.add(new Identifier(1, 1));
        before.add(new Identifier(1, 2));
        after.add(new Identifier(1, 1));
        after.add(new Identifier(2, 2));
        list.add(DocumentCharacterFactory.getDocumentCharacter('g', before, after, 3));
        Collections.sort(list);

        StringBuilder erg = new StringBuilder();
        for(DocumentCharacter character : list) {
            erg.append(character.getValue());
        }
        assertEquals("sagen", erg.toString());
    }

    @Test
    public void testInsertBetween() {
        var b = new DocumentCharacter('B', List.of(new Identifier(0, 0), new Identifier(2, 4711)));

        var c = new DocumentCharacter('C', List.of(new Identifier(0, 0), new Identifier(1, 4711), new Identifier(1, 4711)));

        DocumentCharacter d = DocumentCharacterFactory.getDocumentCharacter('D', c.getPosition(), b.getPosition(), 4711);

        assertEquals(new Identifier(0, 0), d.getPosition().get(0));
        assertEquals(new Identifier(1, 4711), d.getPosition().get(1));
        assertEquals(new Identifier(2, 4711), d.getPosition().get(2));
        assertEquals(3, d.getPosition().size());

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
