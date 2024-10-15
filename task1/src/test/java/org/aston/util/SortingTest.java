package org.aston.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortingTest {

    private static final Random RANDOM = new Random();
    private ObjectList<String> testStringObjectList;
    private ObjectList<Integer> testIntegerObjectList;
    private ArrayList<String> testStringArrayList;

    @AfterEach
    void tearDown() {
        if (testStringObjectList != null) {
            testStringObjectList.clear();
        }
        if (testIntegerObjectList != null) {
            testIntegerObjectList.clear();
        }
        if (testStringArrayList != null) {
            testStringArrayList.clear();
        }
    }

    @Test
    void sortIntegerList_shouldSortElementsInAscendingOrder() {
        testIntegerObjectList = new ObjectList<>();
        int amountOfRandomNumbers = 10;
        int randomBound = 100;
        fillInIntegerList(testIntegerObjectList, amountOfRandomNumbers, randomBound);

        Sorting.sort(testIntegerObjectList);
        for (int i = 1; i < testIntegerObjectList.size(); i++) {
            assertTrue(testIntegerObjectList.get(i - 1) <= testIntegerObjectList.get(i));
        }
    }

    @Test
    void sortStringList_shouldSortElementsAlphabetically() {
        testStringArrayList = new ArrayList<>();
        fillInArrayStringList(testStringArrayList);
        Collections.sort(testStringArrayList);
        String arrayListSortedNames = testStringArrayList.toString();

        testStringObjectList = new ObjectList<>();
        fillInObjectStringList(testStringObjectList);
        Sorting.sort(testStringObjectList);
        String objectListSortedNames = testStringObjectList.toString();

        assertEquals(arrayListSortedNames, objectListSortedNames);
    }

    @Test
    void sortStringListWithComparator_shouldSortElementsCorrectly() {
        testStringArrayList = new ArrayList<>();
        fillInArrayStringList(testStringArrayList);
        testStringArrayList.sort(Comparator.comparingInt(String::length).thenComparing(o -> o));
        String arrayListSortedNames = testStringArrayList.toString();

        testStringObjectList = new ObjectList<>();
        fillInObjectStringList(testStringObjectList);
        Sorting.sort(testStringObjectList, Comparator.comparingInt(String::length).thenComparing(o -> o));
        String objectListSortedNames = testStringObjectList.toString();

        assertEquals(arrayListSortedNames, objectListSortedNames);
    }

    private void fillInIntegerList(ObjectList<Integer> integerList, int amountOfRandomNumbers, int randomBound) {
        for (int i = 0; i < amountOfRandomNumbers; i++) {
            integerList.add(RANDOM.nextInt(randomBound));
        }
    }

    private void fillInObjectStringList(ObjectList<String> stringObjectList) {
        stringObjectList.add("Nick");
        stringObjectList.add("Daniel");
        stringObjectList.add("Ilya");
        stringObjectList.add("Alex");
        stringObjectList.add("Andrew");
        stringObjectList.add("Michael");
        stringObjectList.add("Antony");
    }

    private void fillInArrayStringList(ArrayList<String> stringArrayList) {
        stringArrayList.add("Nick");
        stringArrayList.add("Daniel");
        stringArrayList.add("Ilya");
        stringArrayList.add("Alex");
        stringArrayList.add("Andrew");
        stringArrayList.add("Michael");
        stringArrayList.add("Antony");
    }
}