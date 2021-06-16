package prog2.knobel.overover.control;

import prog2.knobel.overover.control.command.AddClassCommand;
import prog2.knobel.overover.control.command.AddMethodCommand;
import prog2.knobel.overover.control.command.ChangeSuperclassCommand;
import prog2.knobel.overover.control.command.Command;
import prog2.knobel.overover.control.command.ComputeCommand;
import prog2.knobel.overover.control.command.Visitor;
import prog2.knobel.overover.control.data.ClassRep;
import prog2.knobel.overover.control.data.MethodRep;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Control {

    private final Map<String, ClassRep> classes;

    public Control() {
        classes = new HashMap<>();
    }

    public void postMessage(Command command) {
        System.out.println("Got message " + command);
        command.accept(new Visitor() {

            @Override
            public void handle(AddClassCommand cmd) {
                classes.put(cmd.getNewClassName(), new ClassRep(cmd.getNewClassName()));
            }

            @Override
            public void handle(AddMethodCommand cmd) {
                ClassRep rep = classes.get(cmd.getClassName());
                if (rep == null) throw new IllegalArgumentException("Klasse nicht gefunden");
                var lst = cmd.getArgClasses().stream()
                             .map(classes::get)
                             .collect(Collectors.toList());
                System.out.println(lst);
                if (lst.size() > 0 && lst.stream().anyMatch(Objects::isNull)) {
                    throw new IllegalArgumentException("Klasse nicht gefunden!");
                }
                rep.addMethodDef(new MethodRep(rep, cmd.getMethodName(), lst));

            }

            @Override
            public void handle(ChangeSuperclassCommand cmd) {
                ClassRep toChange = classes.get(cmd.getClassName());
                String scName = cmd.getNewSuperClass();
                ClassRep newSuper;
                if (scName == null) newSuper = null;
                else {
                    newSuper = classes.get(scName);
                    if (newSuper == null)
                        throw new IllegalArgumentException("Klasse nicht gefunden!");
                }
                toChange.setParent(newSuper);

            }

            @Override
            public void handle(ComputeCommand cmd) {
                String sts = cmd.getStaticType();
                ClassRep rep = classes.get(sts);
                Set<MethodRep> methods = new HashSet<>();
                rep.addAllMethodsIncludingSuper(methods);
                methods.removeIf(m -> {
                    if (!m.getName().equals(cmd.getMethodName())) {
                        return true;
                    }
                    return m.getArguments().size() != cmd.getMethodArgs().size();
                });
                List<ClassRep> arguments = cmd.getMethodArgs().stream()
                                              .map(classes::get)
                                              .collect(Collectors.toList());
                methods.removeIf(m -> {
                    for (int i = 0; i < m.getArguments().size(); i++) {
                        ClassRep declared = m.getArguments().get(i);
                        ClassRep argument = arguments.get(i);
                        if (!declared.canAcceptAsArgument(argument)) {
                            return true;
                        }
                    }
                    return false;
                });
                Set<MethodRep> mostSpecific = new HashSet<>();
                for (MethodRep m : methods) {
                    boolean isMoreSpecific = true;
                    for (MethodRep m2 : methods) {
                        if (m.equals(m2)) continue;
                        if (!m.isMoreSpecificThan(m2)) isMoreSpecific = false;
                    }
                    if (isMoreSpecific) mostSpecific.add(m);
                }
                if (mostSpecific.size() == 1) {
                    MethodRep resolved = mostSpecific.stream().findAny().get();
                    ClassRep dynamic = classes.get(cmd.getDynamicType());

                    while (dynamic != null) {
                        if (dynamic.getMethods().contains(resolved)) {
                            System.out.println(resolved + " overwritten in " + dynamic);
                            return;
                        }
                        dynamic = dynamic.getParent();
                    }

                } else {
                    throw new IllegalArgumentException("Ambigous method call!");
                }
            }

        });
    }
}
