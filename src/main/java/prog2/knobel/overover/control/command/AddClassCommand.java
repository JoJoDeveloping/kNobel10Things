package prog2.knobel.overover.control.command;

public class AddClassCommand extends Command{

    public AddClassCommand(final String newClassName) {
        this.newClassName = newClassName;
    }

    private final String newClassName;

    public String getNewClassName() {
        return newClassName;
    }

    @Override
    public String toString() {
        return "AddClassCommand{" +
               "newClassName='" + newClassName + '\'' +
               '}';
    }
}
