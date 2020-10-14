package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SimulatorWindow extends JPanel{

    /*Components for the top container for the buttons.*/
    private JPanel buttonsPanel = new JPanel();
    private JButton loaderButton = new JButton();
    private JButton executeButton = new JButton();

    /*Window components for the source code window. */
    private JLabel sourceCodeText = new JLabel("Source Code");
    private JTextArea sourceCodeArea = new JTextArea();
    private JPanel sourceCodePanel = new JPanel();

    /*Window components for the object code window. */
    private JLabel objectCodeText = new JLabel("Object Code");
    private JTextArea objectCodeArea = new JTextArea();
    private JPanel objectCodePanel = new JPanel();

    /*CPU window components. */
    private JTextArea cpuArea = new JTextArea();
    private JPanel cpuPanel = new JPanel();
    private JLabel cpuTextField = new JLabel("CPU");

    /*Terminal window components. */
    private JTextArea terminalArea = new JTextArea();
    private JLabel terminalTextField = new JLabel("Input/Output");
    private JPanel terminalPanel = new JPanel();

    /*Memory Dump window components. */
    private JTextArea memoryArea = new JTextArea();
    private JLabel memoryTextField = new JLabel("Memory Dump");
    private JPanel memoryPanel = new JPanel();

    public SimulatorWindow() throws IOException {
        setLayout(new FlowLayout());
        setBackground(Color.BLACK);

        Image img = ImageIO.read(getClass().getResource("./play_button.png"));
        executeButton.setIcon(new ImageIcon(img));

        Image img2 = ImageIO.read(getClass().getResource("./loader_resize.png"));
        loaderButton.setIcon(new ImageIcon(img2));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buildButtonPanel(), buildSourceCodePanel());
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, buildObjectCodePanel());
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buildCpuWindow(), buildTerminalWindow());
        JSplitPane splitPane4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane2, splitPane3);
        JSplitPane splitPane5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane4, buildMemoryDumpWindow());
        add(splitPane5);

    }

    private JPanel buildButtonPanel() {
        buttonsPanel.setLayout(new GridLayout(1,2));
        buttonsPanel.add(loaderButton);
        buttonsPanel.add(executeButton);
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return buttonsPanel;
    }

    private JPanel buildSourceCodePanel() {
        sourceCodePanel.setLayout(new BorderLayout());
        sourceCodePanel.setPreferredSize(new Dimension(300, 200));
        sourceCodePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        sourceCodeText.setBorder(BorderFactory.createLineBorder(Color.black));
        sourceCodePanel.add(sourceCodeText, BorderLayout.NORTH);
        sourceCodePanel.add(sourceCodeArea, BorderLayout.CENTER);
        return sourceCodePanel;
    }

    private JPanel buildObjectCodePanel() {
        objectCodePanel.setLayout(new BorderLayout());
        objectCodePanel.setPreferredSize(new Dimension(300,200));
        objectCodePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        objectCodeText.setBorder(BorderFactory.createLineBorder(Color.black));
        objectCodePanel.add(objectCodeText, BorderLayout.NORTH);
        objectCodePanel.add(objectCodeArea, BorderLayout.CENTER);
        return objectCodePanel;
    }

    private JPanel buildCpuWindow() {
        cpuPanel.setLayout(new BorderLayout());
        cpuPanel.setPreferredSize(new Dimension(300,200));
        cpuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        cpuArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        cpuPanel.add(cpuTextField, BorderLayout.NORTH);
        cpuPanel.add(cpuArea, BorderLayout.CENTER);
        return cpuPanel;
    }

    private JPanel buildTerminalWindow() {
        terminalPanel.setLayout(new BorderLayout());
        terminalPanel.setPreferredSize(new Dimension(300,200));
        terminalPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        terminalArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        terminalPanel.add(terminalTextField, BorderLayout.NORTH);
        terminalPanel.add(terminalArea, BorderLayout.CENTER);
        return terminalPanel;
    }

    private JPanel buildMemoryDumpWindow() {
        memoryPanel.setLayout(new BorderLayout());
        memoryPanel.setPreferredSize(new Dimension(300,400));
        memoryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        memoryArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        memoryArea.setEnabled(false);
        memoryPanel.add(memoryTextField, BorderLayout.NORTH);
        memoryPanel.add(memoryArea, BorderLayout.CENTER);
        return memoryPanel;
    }


    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.setBackground(Color.BLACK);
        SimulatorWindow window = new SimulatorWindow();
        frame.add(window);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
}
