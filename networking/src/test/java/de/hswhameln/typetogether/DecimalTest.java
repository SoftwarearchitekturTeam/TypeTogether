package de.hswhameln.typetogether;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.hswhameln.typetogether.networking.util.Decimal;

public class DecimalTest {
    
    @Test
    public void subtractGreaterThanSameLength() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(2);
        list2.add(1);

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(0);
        expected.add(2);

        List<Integer> result = Decimal.subtractGreaterThan(list1, list2);
        assertIterableEquals(result, expected);
    }

    @Test
    public void subtractGreaterThanFirstLonger() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(2);
        list2.add(1);

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(0);
        expected.add(2);
        expected.add(4);

        List<Integer> result = Decimal.subtractGreaterThan(list1, list2);
        assertIterableEquals(result, expected);
    }

    @Test
    public void subtractGreaterThanSecondLonger() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(2);
        list2.add(1);
        list1.add(4);

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(0);
        expected.add(2);
        expected.add(4);

        List<Integer> result = Decimal.subtractGreaterThan(list1, list2);
        assertIterableEquals(result, expected);
    }
}
