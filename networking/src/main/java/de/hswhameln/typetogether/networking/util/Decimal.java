package de.hswhameln.typetogether.networking.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hswhameln.typetogether.networking.types.Identifier;

public class Decimal {
    
    public static List<Integer> fromIdentifierList(List<Identifier> identifiers) {
        return identifiers.stream().map(ident -> ident.getDigit()).collect(Collectors.toList());
    }

    public static List<Integer> increment(List<Integer> n1, List<Integer> delta) {
        int firstNonzeroDigit = -1;
        List<Integer> inc = new ArrayList<>();
        for(int i = 0; i < delta.size(); i++) {
            if(delta.get(i) != 0 && firstNonzeroDigit != -1) {
                firstNonzeroDigit = i;
            }
        }
        inc = delta.subList(0, firstNonzeroDigit);
        inc.add(0);
        inc.add(1);

        List<Integer> v1 = new ArrayList<>();
        for(int i = 0; i < n1.size(); i++) {
            v1.set(i, n1.get(i));
        }
        for(Integer i : inc) {
            v1.add(i);
        }

        List<Integer> v2 = new ArrayList<>();
        if(v1.get(v1.size() -1 ) == 0) {
            for(Integer i : inc) {
                v2.add(i);
            }
        } else {
            v2 = v1;
        }
        return v2;
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

    public static List<Integer> subtractGreaterThan(List<Integer> n1, List<Integer> n2) {
        List<Integer> returnIn = new ArrayList<>();
        for(int i = 0; i < Math.max(n1.size(), n2.size()); i++) {
            if(n1.get(i) == n2.get(i)) {
                returnIn.add(0);
            } else if(n1.get(i) > n2.get(i)) {
                returnIn.add(n1.get(i) - n2.get(i));
            } else {
                returnIn.add(n2.get(i) - n1.get(i));
            }
        }
        return returnIn;
    }
}
