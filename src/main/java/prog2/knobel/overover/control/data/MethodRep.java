package prog2.knobel.overover.control.data;

import java.util.List;
import java.util.Objects;

public class MethodRep {

    private final ClassRep definedIn;
    private final String name;
    private final List<ClassRep> arguments;

    public MethodRep(final ClassRep definedIn, final String name, final List<ClassRep> arguments) {
        this.definedIn = definedIn;
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<ClassRep> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Method " + name + " Args: " + arguments + " declared in " + definedIn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arguments);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodRep) {
            MethodRep other = (MethodRep) obj;
            if (!other.name.equals(name))
                return false;
            return other.arguments.equals(arguments);
        } else return false;
    }

    /**
     * Check if this method has a more specific signature than the other method
     *
     * @param rep
     * @return
     */
    public boolean isMoreSpecificThan(MethodRep rep) {
        if (rep.arguments.size() != arguments.size())
            return false;

        for (int i = 0; i < rep.arguments.size(); i++) {
            ClassRep thisOnes = arguments.get(i);
            ClassRep otherOnes = rep.arguments.get(i);

            if (!otherOnes.canAcceptAsArgument(thisOnes)) return false;
        }
        return true;

    }
}
