package prog2.knobel.overover.control.command;

public class AddClassCommand extends Command {

    private final String newClassName;

    public AddClassCommand(final String newClassName) {
        this.newClassName = newClassName;
    }

    public String getNewClassName() {
        return newClassName;
    }

    @Override
    public String toString() {
        return "AddClassCommand{" +
               "newClassName='" + newClassName + '\'' +
               '}';
    }

    @Override
    public void accept(Visitor v) {
        v.handle(this);
    }
}
