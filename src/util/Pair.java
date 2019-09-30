package util;

public class Pair<K, V> {
    K myKey;
    V myValue;

    public Pair(K key, V value) {
        myKey = key;
        myValue = value;
    }

    public K getKey() {
        return myKey;
    }

    public V getValue() {
        return myValue;
    }

    @Override
    public int hashCode() {
        return myKey.hashCode() +  myValue.hashCode();
    }
}
