package prog2.knobel.overover.control.data;

import java.util.HashSet;
import java.util.Set;


public class ClassRep {

    private final String name;
    private ClassRep parent;

    private final Set<MethodRep> thisMethods;

    public ClassRep(final String name) {
        this.name = name;
        this.thisMethods = new HashSet<>();
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

    public Set<MethodRep> getMethods() {
        return thisMethods;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean canAcceptAsArgument(ClassRep rep) {
        while (rep != null) {
            if (rep == this) return true;
            rep = rep.getParent();
        }
        return false;
    }

    public void addAllMethodsIncludingSuper(Set<MethodRep> methods) {
        if (parent != null) parent.addAllMethodsIncludingSuper(methods);
        for (MethodRep m : thisMethods) {
            methods.add(m);
        }
    }

}
