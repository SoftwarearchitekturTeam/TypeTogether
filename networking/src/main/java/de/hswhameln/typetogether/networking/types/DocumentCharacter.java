package de.hswhameln.typetogether.networking.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.hswhameln.typetogether.networking.util.Decimal;
import de.hswhameln.typetogether.networking.util.StringRepresentationSchema;

public class DocumentCharacter implements Comparable<DocumentCharacter> {
    private List<Identifier> position;
    private char value;

    private static final StringRepresentationSchema<DocumentCharacter> stringRepresentationSchema = new StringRepresentationSchema<>(
            DocumentCharacter.class,
            "<",
            ">",
            "-",
            2
    );
    private static final String POSTION_DELIMITER = ",";

    public DocumentCharacter(char value, Identifier firstPosition) {
        this.position = new ArrayList<>();
        this.position.add(firstPosition);
        this.value = value;
    }

    public DocumentCharacter(char value, List<Identifier> positionBefore, List<Identifier> positionAfter, int userId) {
        this.position = this.generatePositionBetween(positionBefore, positionAfter, userId);
        this.value = value;
    }

    public DocumentCharacter(char value, List<Identifier> position) {
        this.value = value;
        this.position = position;
    }

    public DocumentCharacter(char value, List<Identifier> positionBefore, int userId) {
        this.value = value;
        this.position = generatePositionAfter(positionBefore, userId);
    }

    public char getValue() {
        return this.value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public List<Identifier> getPosition() {
        return this.position;
    }

    @Override
    public int compareTo(DocumentCharacter character) {
        for(int i = 0; i < Math.min(this.position.size(), character.position.size()); i++) {
            int comp = this.compareIdentifier(this.position.get(i), character.position.get(i));
            if(comp != 0) {
                return comp;
            }
        }
        if(this.position.size() < character.position.size()) {
            return -1;
        } else if(this.position.size() > character.position.size()) {
            return 1;
        } else {
            return 0;
        }
    }

    private int compareIdentifier(Identifier i1, Identifier i2) {
        if(i1.getDigit() < i2.getDigit()) {
            return -1;
        } else if(i1.getDigit() > i2.getDigit()) {
            return 1;
        } else {
            if(i1.getUserId() < i2.getUserId()) {
                return -1;
            } else if(i1.getUserId() > i2.getUserId()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private List<Identifier> generatePositionAfter(List<Identifier> positionBefore, int userId) {
        List<Identifier> returnIn = new ArrayList<>();
        positionBefore.forEach(i -> returnIn.add(i));
        if(returnIn.get(returnIn.size() - 1).getUserId() == userId) {
            returnIn.get(returnIn.size() - 1).incrementDigit();
        } else {
            returnIn.add(new Identifier(1, userId));
        }
        return returnIn;
    }

    private List<Identifier> generatePositionBetween(List<Identifier> p1, List<Identifier> p2, int userId) {
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
                return Decimal.cons(head1, generatePositionBetween(Decimal.rest(p1), Collections.emptyList(), userId));
            } else if(head1.getUserId() == head2.getUserId()) {
                return Decimal.cons(head1, generatePositionBetween(Decimal.rest(p1), Decimal.rest(p2), userId));
            } else {
                throw new IllegalArgumentException("Invalid UserId ordering");
            }
        }
    }

    public String getStringRepresentation() {
        return stringRepresentationSchema.getStringRepresentation(
                Character.toString(this.value),
                this.position.stream()
                        .map(Identifier::getStringRepresentation)
                        .collect(Collectors.joining(POSTION_DELIMITER))
               );
    }

    public static DocumentCharacter parse(String stringRepresentation) {
        return stringRepresentationSchema.parse(stringRepresentation, (elements) -> fromStringArray(stringRepresentation, elements));
    }

    private static DocumentCharacter fromStringArray(String stringRepresentation, String[] elements) {
        if (elements[0].length() != 1) {
            throw new ParseException(DocumentCharacter.class, stringRepresentation, "First element must be a single character.");
        }
        char value = elements[0].charAt(0);
        List<Identifier> position = Arrays.stream(elements[1].split(POSTION_DELIMITER))
                .map(Identifier::parse)
                .collect(Collectors.toList());
        return new DocumentCharacter(value, position);
    }
}