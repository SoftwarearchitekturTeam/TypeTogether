package de.hswhameln.typetogether.networking.types;

import de.hswhameln.typetogether.networking.util.StringRepresentationSchema;

public class Identifier {
    private int digit;
    private int userId;

    private static final StringRepresentationSchema<Identifier> stringRepresentationSchema = new StringRepresentationSchema<>(
            Identifier.class,
            "(",
            ")",
            "|",
            2
    );

    public Identifier(int digit, int userId) {
        this.digit = digit;
        this.userId = userId;
    }

    public int getDigit() {
        return this.digit;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o instanceof Identifier) {
            Identifier i = (Identifier) o;
            return this.digit == i.digit && this.userId == i.userId;
        }
        return false;
    }

    public String getStringRepresentation() {
        return stringRepresentationSchema.getStringRepresentation(Integer.toString(this.digit), Integer.toString(this.userId));
    }

    public static Identifier parse(String stringRepresentation) {
        return stringRepresentationSchema.parse(stringRepresentation, (elements) -> fromStringArray(stringRepresentation, elements));
    }

    private static Identifier fromStringArray(String stringRepresentation, String[] elements) {
        try {
            int digit = Integer.parseInt(elements[0]);
            int userId = Integer.parseInt(elements[1]);
            return new Identifier(digit, userId);
        } catch (NumberFormatException e) {
            throw new ParseException(Identifier.class, stringRepresentation, e);
        }
    }
}
