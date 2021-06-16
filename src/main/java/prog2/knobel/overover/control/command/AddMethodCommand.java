package prog2.knobel.overover.control.command;

import java.util.List;

public class AddMethodCommand extends Command {

    private final String className;
    private final String methodName;
    private final List<String> argClasses;

    public AddMethodCommand(final String className, final String methodName, final List<String> argClasses) {
        this.className = className;
        this.methodName = methodName;
        this.argClasses = argClasses;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getArgClasses() {
        return argClasses;
    }

    @Override
    public String toString() {
        return "AddMethodCommand{" +
               "className='" + className + '\'' +
               ", methodName='" + methodName + '\'' +
               ", argClasses=" + argClasses +
               '}';
    }


    @Override
    public void accept(Visitor v) {
        v.handle(this);
    }
}
