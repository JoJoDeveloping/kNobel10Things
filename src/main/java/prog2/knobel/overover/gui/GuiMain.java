package prog2.knobel.overover.gui;

import prog2.knobel.overover.control.Control;
import prog2.knobel.overover.control.command.AddClassCommand;
import prog2.knobel.overover.control.command.Command;
import prog2.knobel.overover.control.command.ComputeCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GuiMain {

    private JTabbedPane classPanel;
    private JButton addClassButton, computeButton;
    private JFrame frame;
    private Control control;

    private Set<String> classes;

    public GuiMain() {
        control = new Control();
        classPanel = new JTabbedPane();
        addClassButton = new JButton("Klasse hinzufügen");
        computeButton = new JButton("Aufruf ausrechnen");
        classes = new HashSet<>();
        frame = new JFrame("Überladung Überschreibung");
        GridBagLayout manager = new GridBagLayout();
        frame.setLayout(manager);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.9;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = c.gridy = 0;
        frame.add(classPanel, c);
        c.gridwidth = 1;
        c.weighty = 0.5;
        c.weighty = 0.1;
        c.gridheight = 1;
        c.gridy = 1;
        frame.add(addClassButton, c);
        c.gridx = 1;
        frame.add(computeButton, c);
        frame.setPreferredSize(new Dimension(100, 100));
        frame.pack();
        frame.setVisible(true);

        addClassButton.addActionListener(this::addClass);
        computeButton.addActionListener(this::compute);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void compute(final ActionEvent actionEvent) {
        String st = JOptionPane.showInputDialog("Statischer Typ?").trim();
        String dt = JOptionPane.showInputDialog("Dynamischer Typ?").trim();
        String ms = JOptionPane.showInputDialog("Methodensignatur?").trim();

        int i = ms.indexOf('(');
        String mname = ms.substring(0, i).trim();
        ms = ms.substring(i + 1, ms.length() - 1);
        var args = Arrays.stream(ms.split(","))
                         .map(String::trim)
                         .collect(Collectors.toList());

        postMessage(new ComputeCommand(st, dt, mname, args));
    }

    private void addClass(final ActionEvent actionEvent) {
        String name = JOptionPane.showInputDialog("Wie heißt die Klasse");
        if (classes.add(name)) {
            control.postMessage(new AddClassCommand(name));
            ClassTab newOne = new ClassTab(name, this);
            classPanel.addTab(name, newOne);
            for (int i = 0; i < classPanel.getTabCount(); i++) {
                ClassTab oldOne = (ClassTab) classPanel.getComponentAt(i);
                oldOne.addClassToList(name);

                newOne.addClassToList(oldOne.getClassName());
            }
        } else {
            showErrorDialog("Already exists: " + name);
        }
    }

    public void postMessage(Command command) {
        control.postMessage(command);
    }

    // from http://www.java2s.com/example/java-utility-method/joptionpane-error/showerrordialog-jframe-parent-exception-ex-0b557.html
    public void showErrorDialog(String s) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Sans-Serif", Font.PLAIN, 10));
        textArea.setEditable(false);
        textArea.setText(s);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));
        JOptionPane.showMessageDialog(frame, scrollPane, "An Error Has Occurred", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new GuiMain();
    }

}
