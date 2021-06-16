package prog2.knobel.overover.control.command;

import java.util.List;

public class ComputeCommand extends Command {
    private final String staticType;
    private final String dynamicType;
    private final String methodName;
    private final List<String> methodArgs;

    public ComputeCommand(final String st, final String dt, final String mname, final List<String> args) {
        this.staticType = st;
        this.dynamicType = dt;
        this.methodName = mname;
        this.methodArgs = args;
    }

    public String getStaticType() {
        return staticType;
    }

    public String getDynamicType() {
        return dynamicType;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getMethodArgs() {
        return methodArgs;
    }

    @Override
    public String toString() {
        return "ComputeCommand{" +
               "staticType='" + staticType + '\'' +
               ", dynamiscType='" + dynamicType + '\'' +
               ", methodName='" + methodName + '\'' +
               ", methodArgs=" + methodArgs +
               '}';
    }

    @Override
    public void accept(Visitor v) {
        v.handle(this);
    }
}
