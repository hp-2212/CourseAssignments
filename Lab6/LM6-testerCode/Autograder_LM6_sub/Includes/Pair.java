package Includes;

public class Pair<K, V> {
    public K first;
    public V second;
    public Pair(K _first, V _second) {
        first = _first;
        second = _second;
    }
    public K getKey() {
        return first;
    }
    public V getValue() {
        return second;
    }
}
