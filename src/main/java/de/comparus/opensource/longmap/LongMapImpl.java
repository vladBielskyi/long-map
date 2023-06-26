package de.comparus.opensource.longmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LongMapImpl<V> implements LongMap<V> {
    private static final int INITIAL_CAPACITY = 16;

    private List<Node<V>>[] table;
    private int size;

    public LongMapImpl() {
        this.table = new List[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public V put(long key, V value) {
        if (size == table.length) {
            resizeTable();
        }
        int index = getIndex(key, table.length);
        List<Node<V>> chain = table[index];
        if (chain == null) {
            chain = new LinkedList<>();
            table[index] = chain;
        }
        for (Node<V> node : chain) {
            if (node.key == key) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        chain.add(new Node<>(key, value));
        size++;
        return null;
    }

    @Override
    public V get(long key) {
        int index = getIndex(key, table.length);
        List<Node<V>> chain = table[index];
        if (chain != null) {
            for (Node<V> node : chain) {
                if (node.key == key) {
                    return node.value;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(long key) {
        int index = getIndex(key, table.length);
        List<Node<V>> chain = table[index];
        if (chain != null) {
            Iterator<Node<V>> iterator = chain.iterator();
            while (iterator.hasNext()) {
                Node<V> node = iterator.next();
                if (node.key == key) {
                    iterator.remove();
                    size--;
                    return node.value;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(long key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (List<Node<V>> chain : table) {
            if (chain != null) {
                for (Node<V> node : chain) {
                    if (Objects.equals(node.value, value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public long[] keys() {
        long[] keys = new long[size];
        int index = 0;
        for (List<Node<V>> chain : table) {
            if (chain != null) {
                for (Node<V> node : chain) {
                    keys[index++] = node.key;
                }
            }
        }
        return keys;
    }

    @Override
    public List<V> values() {
        List<V> valueList = new ArrayList<>(size);
        for (List<Node<V>> chain : table) {
            if (chain != null) {
                for (Node<V> node : chain) {
                    valueList.add(node.value);
                }
            }
        }
        return valueList;
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    private int getIndex(long key, int length) {
        return (int) ((key * 23424234L) % length);
    }

    private void resizeTable() {
        int newCapacity = table.length * 2;
        List<Node<V>>[] newTable = new List[newCapacity];

        for (List<Node<V>> chain : table) {
            if (chain != null) {
                for (Node<V> node : chain) {
                    int newIndex = getIndex(node.key, newCapacity);
                    List<Node<V>> newChain = newTable[newIndex];
                    if (newChain == null) {
                        newChain = new LinkedList<>();
                        newTable[newIndex] = newChain;
                    }
                    newChain.add(node);
                }
            }
        }

        table = newTable;
    }

    private static class Node<V> {
        private final long key;
        private V value;

        public Node(long key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return key == node.key && Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
