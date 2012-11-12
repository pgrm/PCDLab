/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session2;

import java.util.Objects;

/**
 *
 * @param <First>
 * @param <Second>
 * @author Peter
 */
public class Tuple<First, Second> {

    public final First first;
    public final Second second;

    public Tuple(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Tuple<?, ?>) {
            return equals((Tuple<?, ?>) obj);
        } else {
            return false;
        }
    }

    public boolean equals(Tuple<?, ?> other) {
        return this == other ||
                (Objects.equals(first, other.first) &&
                Objects.equals(second, other.second));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.first);
        hash = 97 * hash + Objects.hashCode(this.second);
        return hash;
    }
}
