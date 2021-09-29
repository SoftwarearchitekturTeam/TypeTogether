package de.hswhameln.typetogether.networking.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.types.Identifier;

public class DocumentCharacterFactory {
    
    public static DocumentCharacter getDocumentCharacter(char value, List<Identifier> positionBefore, int userId) {
        return new DocumentCharacter(value, DocumentCharacterFactory.generatePositionAfter(positionBefore, userId));
    }

    public static DocumentCharacter getDocumentCharacter(char value, List<Identifier> positionBefore, List<Identifier> positionAfter, int userId) {
        return new DocumentCharacter(value, DocumentCharacterFactory.generatePositionBetween(positionBefore, positionAfter, userId));
    }

    private static List<Identifier> generatePositionAfter(List<Identifier> positionBefore, int userId) {
        System.out.println("Trying to generate a position for user "+ userId + " after " + positionBefore.stream().map(Identifier::getStringRepresentation).collect(Collectors.joining("/")));
        List<Identifier> returnIn = new ArrayList<>(positionBefore);
        if(returnIn.get(returnIn.size() - 1).getUserId() == userId) {
            Identifier identifier = returnIn.remove(returnIn.size() - 1);
            returnIn.add(new Identifier(identifier.getDigit() + 1, userId));
        } else {
            returnIn.add(new Identifier(1, userId));
        }
        System.out.println("The new position is " + returnIn.stream().map(Identifier::getStringRepresentation).collect(Collectors.joining("/")));

        return Collections.unmodifiableList(returnIn);
    }

    private static List<Identifier> generatePositionBetween(List<Identifier> p1, List<Identifier> p2, int userId) {
        System.out.println("Trying to generate a position for user "+ userId + " after " + p1.stream().map(Identifier::getStringRepresentation).collect(Collectors.joining("/")) + " and before " + p2.stream().map(Identifier::getStringRepresentation).collect(Collectors.joining("/")));
        Identifier head1;
        if(p1.size() > 0 && p2.get(0) != null) {
            head1 = p1.get(0);
        } else {
            head1 = new Identifier(0, userId);
        }
        Identifier head2;
        if(p2.size() > 0 && p2.get(0) != null) {
            head2 = p2.get(0);
        } else {
            head2 = new Identifier(10, userId);
        }

        if(head1.getDigit() != head2.getDigit()) {
            List<Integer> n1 = Decimal.fromIdentifierList(p1);
            List<Integer> n2 = Decimal.fromIdentifierList(p2);
            List<Integer> delta = Decimal.subtractGreaterThan(n1, n2);
            List<Integer> next = Decimal.increment(n1, delta);
            return Decimal.toIdentifierList(next, p1, p2, userId);
        } else {
            if(head1.getUserId() != head2.getUserId()) {
                return Collections.unmodifiableList(Decimal.cons(head1, generatePositionBetween(Decimal.rest(p1), Collections.emptyList(), userId)));
            } else if(head1.getUserId() == head2.getUserId()) {
                return Collections.unmodifiableList(Decimal.cons(head1, generatePositionBetween(Decimal.rest(p1), Decimal.rest(p2), userId)));
            } else {
                throw new IllegalArgumentException("Invalid UserId ordering");
            }
        }
    }
}
