package prog2.knobel.overover.control.data;

import java.util.List;

public class MethodRep {

    private final ClassRep definedIn;
    private final String name;
    private final List<ClassRep> arguments;

    public MethodRep(final ClassRep definedIn, final String name, final List<ClassRep> arguments) {
        this.definedIn = definedIn;
        this.name = name;
        this.arguments = arguments;
    }
}
