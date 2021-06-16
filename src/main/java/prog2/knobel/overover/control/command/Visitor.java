package prog2.knobel.overover.control.command;

public interface Visitor {

    void handle(AddClassCommand cmd);

    void handle(AddMethodCommand cmd);

    void handle(ChangeSuperclassCommand cmd);

    void handle(ComputeCommand cmd);

}
