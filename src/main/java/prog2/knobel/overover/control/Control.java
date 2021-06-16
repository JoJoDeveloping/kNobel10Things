package prog2.knobel.overover.control;

import prog2.knobel.overover.control.command.AddClassCommand;
import prog2.knobel.overover.control.command.AddMethodCommand;
import prog2.knobel.overover.control.command.ChangeSuperclassCommand;
import prog2.knobel.overover.control.command.Command;
import prog2.knobel.overover.control.command.ComputeCommand;
import prog2.knobel.overover.control.data.ClassRep;
import prog2.knobel.overover.control.data.MethodRep;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Control {

    private final Map<String, ClassRep> classes;

    public Control() {
        classes = new HashMap<>();
    }

    public void postMessage(Command command) {
        System.out.println("Got message " + command);
        if (command instanceof AddClassCommand) {
            classes.put(((AddClassCommand) command).getNewClassName(), new ClassRep(((AddClassCommand) command).getNewClassName()));
        } else if (command instanceof AddMethodCommand) {
            ClassRep rep = classes.get(((AddMethodCommand) command).getClassName());
            if (rep == null) throw new IllegalArgumentException("Klasse nicht gefunden");
            var lst = ((AddMethodCommand) command).getArgClasses().stream()
                                                  .map(classes::get)
                                                  .collect(Collectors.toList());
            if (lst.stream().anyMatch(Objects::isNull)) {
                throw new IllegalArgumentException("Klasse nicht gefunden!");
            }
            rep.addMethodDef(new MethodRep(rep, ((AddMethodCommand) command).getMethodName(), lst));
        } else if (command instanceof ChangeSuperclassCommand) {
            ClassRep toChange = classes.get(((ChangeSuperclassCommand) command).getClassName());
            String scName = ((ChangeSuperclassCommand) command).getNewSuperClass();
            ClassRep newSuper;
            if (scName == null) newSuper = null;
            else {
                newSuper = classes.get(scName);
                if (newSuper == null)
                    throw new IllegalArgumentException("Klasse nicht gefunden!");
            }
            toChange.setParent(newSuper);
        } else if (command instanceof ComputeCommand) {
            //TODO
        } else {
            throw new IllegalArgumentException("Unknown command");
        }
    }
}
