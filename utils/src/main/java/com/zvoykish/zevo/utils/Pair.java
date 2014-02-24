package com.zvoykish.zevo.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 07/05/2010
 * Time: 16:46:13
 */
public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair pair = (Pair) o;

        return !(first != null ? !first.equals(pair.first) : pair.first != null) &&
                !(second != null ? !second.equals(pair.second) : pair.second != null);
    }

    @Override
    public int hashCode() {
        String a = first != null ? first.toString() : "";
        String b = second != null ? second.toString() : "";
        return (a + b).hashCode();
    }

    @Override
    public String toString() {
        return "<" + first + ',' + second + '>';
    }
}
