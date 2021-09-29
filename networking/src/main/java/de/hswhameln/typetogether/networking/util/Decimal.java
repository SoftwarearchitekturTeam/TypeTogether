package de.hswhameln.typetogether.networking.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hswhameln.typetogether.networking.types.Identifier;

public class Decimal {
    
    public static List<Integer> fromIdentifierList(List<Identifier> identifiers) {
        return identifiers.stream().map(ident -> ident.getDigit()).collect(Collectors.toList());
    }

    public static List<Identifier> cons(Identifier identifier, List<Identifier> position) {
        List<Identifier> returnIn = new ArrayList<>();
        returnIn.add(identifier);
        returnIn.addAll(position);
        return returnIn;
    }

    public static <T> List<T> rest(List<T> position) {
        List<T> returnIn = new ArrayList<>();
        for(int i = 1; i < position.size(); i++) {
            returnIn.add(position.get(i));
        }
        return returnIn;
    }

    public static List<Integer> increment(List<Integer> n1, List<Integer> delta) {
        int firstNonzeroDigit = 0;
        for(int i = 0; i < delta.size() && delta.get(i) <= 0; i++) {
            firstNonzeroDigit = i;
        }

        List<Integer> inc = new ArrayList<>();
        inc = delta.subList(0, firstNonzeroDigit);
        
        //TODO critical lines. Needed for Usecase2 but not Usecase 1
        inc.add(0);
        inc.add(1); //TODO position for possible addition of programm wide counter

        List<Integer> v1 = Decimal.add(n1, inc);
        return v1.get(v1.size() - 1) == 0 ? Decimal.add(v1, inc) : v1;
    }

    public static List<Identifier> toIdentifierList(List<Integer> n, List<Identifier> before, List<Identifier> after, int userId) {
        List<Identifier> returnIn = new ArrayList<>();
        for(int i = 0; i < n.size(); i++) {
            if(i == n.size() - 1) {
                returnIn.add(new Identifier(n.get(i), userId));

            } else if(i < before.size() && n.get(i) == before.get(i).getDigit()) {
                returnIn.add(new Identifier(n.get(i), before.get(i).getUserId()));

            } else if(i < after.size() && n.get(i) == after.get(i).getDigit()) {
                returnIn.add(new Identifier(n.get(i), after.get(i).getUserId()));
            } else {
                returnIn.add(new Identifier(n.get(i), userId));
            }
        }
        return returnIn;
    }

    public static List<Integer> add(List<Integer> n1, List<Integer> n2) {
        List<Integer> returnIn = new ArrayList<>();
        for(int i = 0; i < Math.max(n1.size(), n2.size()); i++) {
            if(i >= n1.size()) {
                returnIn.add(n2.get(i));
            } else if(i >= n2.size()) {
                returnIn.add(n1.get(i));
            } else {
                returnIn.add(n1.get(i) + n2.get(i));
            }
        }
        return returnIn;
    }

    public static List<Integer> subtractGreaterThan(List<Integer> n1, List<Integer> n2) {
        List<Integer> returnIn = new ArrayList<>();
        int j = 0;
        for(int i = 0; i < Math.min(n1.size(), n2.size()); i++) {
            if(n1.get(i) == n2.get(i)) {
                returnIn.add(0);
            } else if(n1.get(i) > n2.get(i)) {
                returnIn.add(n1.get(i) - n2.get(i));
            } else {
                returnIn.add(n2.get(i) - n1.get(i));
            }
            j = i;
        }

        j++;
        if(n1.size() > j) {
            for(int i = j; i < n1.size(); i++) {
                returnIn.add(n1.get(i));
            }
        } else if(n2.size() > j) {
            for(int i = j; i < n2.size(); i++) {
                returnIn.add(n2.get(i));
            }
        }

        return returnIn;
    }
}
