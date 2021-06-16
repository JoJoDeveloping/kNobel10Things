package prog2.knobel.overover.control.data;

import java.util.Set;

public class ClassRep {

    private final String name;
    private ClassRep parent;

    private Set<MethodRep> thisMethods;

    public ClassRep(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ClassRep getParent() {
        return parent;
    }

    public void setParent(final ClassRep parent) {
        this.parent = parent;
    }

    public void addMethodDef(final MethodRep methodRep) {
        thisMethods.add(methodRep);
    }
}
