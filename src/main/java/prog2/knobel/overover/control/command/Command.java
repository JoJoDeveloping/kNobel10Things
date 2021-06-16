package prog2.knobel.overover.control.command;

public abstract class Command {
    public abstract void accept(Visitor v);
}
