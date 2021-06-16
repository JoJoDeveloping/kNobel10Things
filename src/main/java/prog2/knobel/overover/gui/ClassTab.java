package prog2.knobel.overover.gui;

import prog2.knobel.overover.control.command.AddMethodCommand;
import prog2.knobel.overover.control.command.ChangeSuperclassCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassTab extends JPanel {

    private final DefaultComboBoxModel<String> model;
    private final DefaultListModel<String> listModel;
    private final GuiMain gui;
    private final String classname;
    private final JLabel name;
    private final JComboBox<String> superclass;
    private final JList<String> body;
    private final JButton addMethodButton;
    private final boolean broadcastActions = true;


    public ClassTab(String nn, final GuiMain guiMain) {
        this.gui = guiMain;
        this.classname = nn;
        name = new JLabel("class " + nn);
        model = new DefaultComboBoxModel<String>();
        model.addAll(List.of(""));
        superclass = new JComboBox<>(model);
        listModel = new DefaultListModel<>();
        listModel.addAll(List.of());
        body = new JList<>(listModel);
        body.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setAnchorSelectionIndex(final int anchorIndex) {
            }

            @Override
            public void setLeadAnchorNotificationEnabled(final boolean flag) {
            }

            @Override
            public void setLeadSelectionIndex(final int leadIndex) {
            }

            @Override
            public void setSelectionInterval(final int index0, final int index1) {
            }
        });
        addMethodButton = new JButton("Funktion hinzufügen");
        GridBagLayout l = new GridBagLayout();
        setLayout(l);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.05;
        c.weightx = 0.5;
        c.gridy = 1;
        c.gridx = 1;
        add(name, c);
        c.gridx = 2;
        add(superclass, c);
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weighty = 0.9;
        add(body, c);
        c.weighty = 0.05;
        c.gridy = 3;
        add(addMethodButton, c);
        addMethodButton.addActionListener(this::handleAddMethod);
        superclass.addActionListener(this::handleChangeSuperclass);
    }

    private void handleChangeSuperclass(final ActionEvent actionEvent) {
        String s = (String) superclass.getSelectedItem();
        if (s != null) {
            if (s.startsWith("extends ")) {
                s = s.substring("extends ".length());
            } else {
                s = null;
            }
            gui.postMessage(new ChangeSuperclassCommand(classname, s));
        }
    }

    private void handleAddMethod(final ActionEvent actionEvent) {
        String name = JOptionPane.showInputDialog("Gebe Methodensignatur ein (zB foo(A, B))").trim();
        int i = name.indexOf('(');
        String mname = name.substring(0, i).trim();
        name = name.substring(i + 1, name.length() - 1).trim();
        var args = Arrays.stream(name.split(","))
                         .map(String::trim)
                         .collect(Collectors.toList());
        if (name.isBlank())
            args = new ArrayList<>();
        name = mname + args.stream().collect(Collectors.joining(", ", "(", ")"));
        gui.postMessage(new AddMethodCommand(classname, mname, args));
        if (this.listModel.contains(name)) {
            gui.showErrorDialog("Method already exists");
        } else {
            this.listModel.addElement(name);
        }
    }

    public void addClassToList(String clazz) {
        if (!clazz.equals(classname))
            model.addElement("extends " + clazz);
    }

    public String getClassName() {
        return classname;
    }
}
