package de.hswhameln.typetogether.networking.types;

import java.util.ArrayList;
import java.util.List;

import de.hswhameln.typetogether.networking.util.Decimal;

public class DocumentCharacter implements Comparable<DocumentCharacter> {
    private List<Identifier> position;
    private char value;

    public DocumentCharacter(char value, Identifier firstPosition) {
        this.position = new ArrayList<>();
        this.position.add(firstPosition);
        this.value = value;
    }

    public DocumentCharacter(char value, List<Identifier> positionBefore, List<Identifier> positionAfter, int userId) {
        this.position = this.generatePositionBetween(positionBefore, positionAfter, userId);
        this.value = value;
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

    private List<Identifier> generatePositionBetween(List<Identifier> p1, List<Identifier> p2, int userId) {
        Identifier head1;
        if(p1.get(0) != null) {
            head1 = p1.get(0);
        } else {
            head1 = new Identifier(0, userId);
        }
        Identifier head2;
        if(p2.get(0) != null) {
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
            if(head1.getUserId() < head2.getUserId()) {
                List<Identifier> returnIn = new ArrayList<>();
                returnIn.add(head1);
                p1.remove(0);
                returnIn.addAll(generatePositionBetween(p1, new ArrayList<Identifier>(), userId));
                return returnIn;
            } else if(head1.getUserId() == head2.getUserId()) {
                List<Identifier> returnIn = new ArrayList<>();
                returnIn.add(head1);
                p1.remove(0);
                p2.remove(0);
                returnIn.addAll(generatePositionBetween(p1, p2, userId));
                return returnIn;
            } else {
                throw new IllegalArgumentException("Invalid UserId ordering");
            }
        }
    }

    private List<Identifier> generateIdentifierList(int digit, int userId, List<Identifier> p1, List<Identifier> p2) {
        List<Identifier> returnIn = new ArrayList<>();
        for(int i = 0; i < Math.min(p1.size(), p2.size()); i++) {
            if(p1.get(i).equals(p2.get(i))) {
                returnIn.add(p1.get(i));
            } else if(p1.get(i) == null) {
                returnIn.add(p2.get(i));
            } else {
                returnIn.add(p1.get(i));
            }
        }
        returnIn.add(new Identifier(digit, userId));
        return returnIn;
    }
}
