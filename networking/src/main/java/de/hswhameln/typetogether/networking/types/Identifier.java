package de.hswhameln.typetogether.networking.types;

public class Identifier {
    private int digit;
    private int userId;

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
}
