package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LongMapImplTest {
    private LongMap<String> map;

    @Before
    public void setUp() {
        map = new LongMapImpl<>();
    }

    @Test
    public void testPutAndGet_PutNewKeyShouldReturnCorrectValue() {
        map.put(1L, "Value1");
        String value = map.get(1L);
        Assert.assertEquals("Value1", value);
    }

    @Test
    public void testPutAndGet_PutMultipleKeysShouldReturnCorrectValues() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");
        map.put(3L, "Value3");

        Assert.assertEquals("Value1", map.get(1L));
        Assert.assertEquals("Value2", map.get(2L));
        Assert.assertEquals("Value3", map.get(3L));
    }

    @Test
    public void testPutAndGet_PutNullValueShouldReturnNull() {
        map.put(1L, null);
        Assert.assertNull(map.get(1L));
    }

    @Test
    public void testRemove_RemoveNonExistingKeyShouldReturnNull() {
        map.put(1L, "Value1");
        String removedValue = map.remove(2L);
        Assert.assertNull(removedValue);
    }

    @Test
    public void testRemove_RemoveMultipleKeysShouldReturnCorrectValues() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");
        map.put(3L, "Value3");

        String removedValue1 = map.remove(1L);
        String removedValue2 = map.remove(2L);
        String removedValue3 = map.remove(3L);

        Assert.assertEquals("Value1", removedValue1);
        Assert.assertEquals("Value2", removedValue2);
        Assert.assertEquals("Value3", removedValue3);
        Assert.assertNull(map.get(1L));
        Assert.assertNull(map.get(2L));
        Assert.assertNull(map.get(3L));
    }

    @Test
    public void testIsEmpty_EmptyMapShouldReturnTrue() {
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void testContainsKey_KeyDoesNotExistShouldReturnFalse() {
        Assert.assertFalse(map.containsKey(1L));
    }

    @Test
    public void testContainsKey_KeyExistsShouldReturnTrue() {
        map.put(1L, "Value1");
        Assert.assertTrue(map.containsKey(1L));
    }

    @Test
    public void testContainsValue_ValueDoesNotExistShouldReturnFalse() {
        Assert.assertFalse(map.containsValue("Value1"));
    }

    @Test
    public void testContainsValue_ValueExistsShouldReturnTrue() {
        map.put(1L, "Value1");
        Assert.assertTrue(map.containsValue("Value1"));
    }

    @Test
    public void testKeys_EmptyMapShouldReturnEmptyArray() {
        long[] keys = map.keys();
        Assert.assertEquals(0, keys.length);
    }

    @Test
    public void testValues_EmptyMapShouldReturnEmptyArray() {
        List<String> values = map.values();
        Assert.assertEquals(0,values.size());
    }

    @Test
    public void testSize_AfterPutOperationsShouldReturnCorrectSize() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");
        map.put(3L, "Value3");

        Assert.assertEquals(3, map.size());
    }

    @Test
    public void testClear_AfterClearingMapShouldBeEmpty() {
        map.put(1L, "Value1");
        map.put(2L, "Value2");
        map.put(3L, "Value3");

        map.clear();

        Assert.assertTrue(map.isEmpty());
        Assert.assertEquals(0, map.size());
    }
}
