package de.hswhameln.typetogether.networking.types;

import java.util.*;
import java.util.stream.Collectors;

import de.hswhameln.typetogether.networking.util.StringEscaper;
import de.hswhameln.typetogether.networking.util.StringRepresentationSchema;

public class DocumentCharacter implements Comparable<DocumentCharacter> {
    private final List<Identifier> position;
    private final char value;

    private static final StringRepresentationSchema<DocumentCharacter> stringRepresentationSchema = new StringRepresentationSchema<>(
            DocumentCharacter.class,
            "<",
            ">",
            "->",
            2
    );
    private static final String POSTION_DELIMITER = ",";

    public DocumentCharacter(char value, List<Identifier> position) {
        this.value = value;
        this.position = position;
    }

    public char getValue() {
        return this.value;
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
        return Integer.compare(this.position.size(), character.position.size());
    }

    private int compareIdentifier(Identifier i1, Identifier i2) {
        if (i1.getDigit() == i2.getDigit()) {
            return Integer.compare(i1.getUserId(), i2.getUserId());
        }
        return Integer.compare(i1.getDigit(), i2.getDigit());
    }

    public String getStringRepresentation() {
        return stringRepresentationSchema.getStringRepresentation(
                StringEscaper.escape(Character.toString(this.value)),
                this.position.stream()
                        .map(Identifier::getStringRepresentation)
                        .collect(Collectors.joining(POSTION_DELIMITER))
               );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentCharacter character = (DocumentCharacter) o;
        return value == character.value && Objects.equals(position, character.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, value);
    }

    public static DocumentCharacter parse(String stringRepresentation) {
        return stringRepresentationSchema.parse(stringRepresentation, (elements) -> fromStringArray(stringRepresentation, elements));
    }

    private static DocumentCharacter fromStringArray(String stringRepresentation, String[] elements) {
        if (elements[0].length() > 2) {
            throw new ParseException(DocumentCharacter.class, stringRepresentation, "First element must be a single character.");
        }
        char value = StringEscaper.unescape(elements[0]).charAt(0);
        List<Identifier> position = Arrays.stream(elements[1].split(POSTION_DELIMITER))
                .map(Identifier::parse)
                .collect(Collectors.toList());
        return new DocumentCharacter(value, position);
    }
}