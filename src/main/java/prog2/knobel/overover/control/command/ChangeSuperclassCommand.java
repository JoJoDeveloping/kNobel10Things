package prog2.knobel.overover.control.command;

public class ChangeSuperclassCommand extends Command {

    private final String className;
    private final String newSuperClass;

    public ChangeSuperclassCommand(final String className, final String newSuperClass) {
        this.className = className;
        this.newSuperClass = newSuperClass;
    }

    public String getClassName() {
        return className;
    }

    public String getNewSuperClass() {
        return newSuperClass;
    }

    @Override
    public String toString() {
        return "ChangeSuperclassCommand{" +
               "className='" + className + '\'' +
               ", newSuperClass='" + newSuperClass + '\'' +
               '}';
    }

    @Override
    public void accept(Visitor v) {
        v.handle(this);
    }
}
