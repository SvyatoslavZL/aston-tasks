package org.aston.kovalinsky;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectListTest {

    private static final String TEST_STRING = "TestString";
    private static final int TEST_INT = 37458;
    private static final Object TEST_OBJECT = new StringBuilder();

    private int defaultCapacity;
    private int capacityMultiplier;
    private ObjectList<Object> testObjectList;

    @BeforeEach
    void setUp() {
        testObjectList = new ObjectList<>();
        defaultCapacity = testObjectList.capacity();
        capacityMultiplier = 2;
    }

    @AfterEach
    void tearDown() {
        testObjectList.clear();
    }

    @Test
    void add_shouldAddCorrectObjectToTheList() {
        testObjectList.add(TEST_OBJECT);
        Object actualObject = testObjectList.get(0);
        assertEquals(TEST_OBJECT, actualObject);
    }

    @Test
    void addByIndex_shouldAddCorrectObjectToTheListAtTheCorrectIndex() {
        testObjectList.add(0, TEST_OBJECT);
        assertEquals(TEST_OBJECT, testObjectList.get(0));
    }

    @Test
    void addByIndex_shouldShiftElements() {
        addDifferentObjects();
        assertEquals(TEST_STRING, testObjectList.get(0));
        testObjectList.add(0, TEST_INT);
        assertEquals(TEST_STRING, testObjectList.get(1));
    }

    @Test
    void set_shouldSetCorrectObjectToTheList() {
        addDifferentObjects();
        testObjectList.set(1, TEST_STRING);
        assertEquals(TEST_STRING, testObjectList.get(1));
    }

    @Test
    void set_returnCorrectOldObjectByIndex() {
        addDifferentObjects();
        Object expectedOldObject = testObjectList.get(1);
        Object actualOldObject = testObjectList.set(1, TEST_STRING);
        assertEquals(expectedOldObject, actualOldObject);
    }

    @Test
    void get_returnCorrectObject() {
        addDifferentObjects();
        Object actualObject = testObjectList.get(0);
        assertEquals(TEST_STRING, actualObject);
    }

    @Test
    void getByIncorrectIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> testObjectList.get(TEST_INT));
    }

    @Test
    void removeByIndex_removeCorrectObject() {
        addDifferentObjects();
        int sizeBeforeDeletion = testObjectList.size();
        assertEquals(TEST_STRING, testObjectList.get(0));
        testObjectList.remove(0);
        int sizeAfterDeletion = testObjectList.size();
        assertEquals(sizeBeforeDeletion - 1, sizeAfterDeletion);
    }

    @Test
    void remove_shouldShiftElements() {
        addDifferentObjects();
        assertEquals(TEST_INT, testObjectList.get(1));
        testObjectList.remove(0);
        assertEquals(TEST_INT, testObjectList.get(0));
    }

    @Test
    void isEmpty_returnTrueIfListIsEmpty() {
        assertTrue(testObjectList.isEmpty());
    }

    @Test
    void isEmpty_returnFalseIfListInNotEmpty() {
        addDifferentObjects();
        assertFalse(testObjectList.isEmpty());
    }

    @Test
    void getSize_returnCorrectSize() {
        testObjectList.add(TEST_STRING);
        testObjectList.add(TEST_INT);
        int actualSize = testObjectList.size();
        assertEquals(2, actualSize);
    }

    @Test
    void toArray_returnCorrectArray() {
        addDifferentObjects();
        Object[] actualArray = testObjectList.toArray();
        assertEquals(testObjectList.get(0), actualArray[0]);
        assertEquals(testObjectList.get(1), actualArray[1]);
        assertEquals(testObjectList.get(2), actualArray[2]);
    }

    @Test
    void getCapacity_returnCorrectCapacity() {
        int actual = testObjectList.capacity();
        assertEquals(defaultCapacity, actual);
    }

    @Test
    void getCapacity_returnCorrectCapacityAfterEnlarging() {
        addAsManyObjectsAsTheDefaultCapacity();
        int expected = defaultCapacity * capacityMultiplier;
        int actual = testObjectList.capacity();
        assertEquals(expected, actual);
    }

    @Test
    void increaseCapacity_shouldIncreaseCapacityByMultiplier() {
        int beforeIncrease = testObjectList.capacity();
        assertEquals(defaultCapacity, beforeIncrease);
        addAsManyObjectsAsTheDefaultCapacity();
        int actualAfterIncrease = testObjectList.capacity();
        int expectedAfterIncrease = beforeIncrease * capacityMultiplier;
        assertEquals(actualAfterIncrease, expectedAfterIncrease);
    }

    @Test
    void increaseCapacity_increaseCapacityIfSizeMoreOrEqualsDefaultCapacity() {
        addAsManyObjectsAsTheDefaultCapacity();
        assertEquals(defaultCapacity, testObjectList.size());
        assertTrue(testObjectList.capacity() > defaultCapacity);
    }

    private void addDifferentObjects() {
        testObjectList.add(TEST_STRING);
        testObjectList.add(TEST_INT);
        testObjectList.add(TEST_OBJECT);
    }

    private void addAsManyObjectsAsTheDefaultCapacity() {
        for (int i = 0; i < defaultCapacity; i++) {
            testObjectList.add(TEST_STRING);
        }
    }
}