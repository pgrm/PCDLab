/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session2.after;

import java.util.Objects;
import session2.SleepyClass;
import session2.Tuple;

/**
 *
 * @author Peter
 */
public abstract class NamedSleepyClass extends SleepyClass {
    public final String name;

    public NamedSleepyClass(String name, long maxMsSleepTime) {
        super(maxMsSleepTime);
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof NamedSleepyClass) {
            return equals((NamedSleepyClass) obj);
        } else {
            return false;
        }
    }

    public boolean equals(NamedSleepyClass other) {
        return this == other ||
                Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }

    protected void reportProgress(String progressDescription) {
        System.out.println("[" + name + "]: " + progressDescription);
    }
}
