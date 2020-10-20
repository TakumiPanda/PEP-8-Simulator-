package view;

import model.MemoryDump;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;

public class SimulatorWindow extends Observable {

	/* Main component. */
	private JPanel mainPanel = new JPanel();
	private MemoryDump memoryDump;

	/* Components for the top container for the buttons. */
	private JPanel buttonsPanel = new JPanel();
	private JButton loaderButton = new JButton();
	private JButton executeButton = new JButton();

	/* Window components for the source code window. */
	private JLabel sourceCodeText = new JLabel("Source Code");
	private JTextArea sourceCodeArea = new JTextArea();
	private JPanel sourceCodePanel = new JPanel();

	/* Window components for the object code window. */
	private JLabel objectCodeText = new JLabel("Object Code");
	private JTextArea objectCodeArea = new JTextArea();
	private JPanel objectCodePanel = new JPanel();

	/* CPU window components. */
	private JTextArea cpuArea = new JTextArea();
	private JPanel cpuPanel = new JPanel();
	private JLabel cpuTextField = new JLabel("CPU");

	/* Terminal window components. */
	private JTextArea terminalArea = new JTextArea();
	private JLabel terminalTextField = new JLabel("Input/Output");
	private JPanel terminalPanel = new JPanel();

	/* Memory Dump window components. */
	private JTextArea memoryArea = new JTextArea();
	private JLabel memoryTextField = new JLabel("Memory Dump");
	private JPanel memoryPanel = new JPanel();
	private JScrollPane scroll = new JScrollPane(memoryArea);

	public SimulatorWindow(MemoryDump memory) throws IOException {
		this.memoryDump = memory;
		mainPanel.setLayout(new FlowLayout());
		mainPanel.setBackground(Color.BLACK);

		Image img = ImageIO.read(new FileInputStream("resources/play_button.png"));
		executeButton.setIcon(new ImageIcon(img));

		Image img2 = ImageIO.read(new FileInputStream("resources/loader_resize.png"));
		loaderButton.setIcon(new ImageIcon(img2));

		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setChanged();
				notifyObservers();
			}
		});

		terminalArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER && terminalArea.getText().length() == 1) {
					setChanged();
					notifyObservers(terminalArea.getText());
				}
			}
		});

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buildButtonPanel(), buildSourceCodePanel());
		JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, buildObjectCodePanel());
		JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buildCpuWindow(), buildTerminalWindow());
		JSplitPane splitPane4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane2, splitPane3);
		JSplitPane splitPane5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane4, buildMemoryDumpWindow());
		mainPanel.add(splitPane5);

	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	private JPanel buildButtonPanel() {
		buttonsPanel.setLayout(new GridLayout(1, 2));
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
		objectCodePanel.setPreferredSize(new Dimension(300, 200));
		objectCodePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		objectCodeText.setBorder(BorderFactory.createLineBorder(Color.black));
		objectCodeArea.setLineWrap(true);
		objectCodePanel.add(objectCodeText, BorderLayout.NORTH);
		objectCodePanel.add(objectCodeArea, BorderLayout.CENTER);
		return objectCodePanel;
	}

	private JPanel buildCpuWindow() {
		cpuPanel.setLayout(new BorderLayout());
		cpuPanel.setPreferredSize(new Dimension(300, 200));
		cpuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cpuArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		cpuPanel.add(cpuTextField, BorderLayout.NORTH);

		cpuPanel.add(new JLabel("Program Counter: "), BorderLayout.CENTER);
		cpuPanel.add(new JTextArea(), BorderLayout.CENTER);
		// cpuPanel.add(cpuArea, BorderLayout.CENTER);

		return cpuPanel;
	}

	private JPanel buildTerminalWindow() {
		terminalPanel.setLayout(new BorderLayout());
		terminalPanel.setPreferredSize(new Dimension(300, 200));
		terminalPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		terminalArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		terminalPanel.add(terminalTextField, BorderLayout.NORTH);
		terminalPanel.add(terminalArea, BorderLayout.CENTER);
		return terminalPanel;
	}

	public JTextArea getObjectCodeArea() {
		return objectCodeArea;
	}

	public JTextArea getSourceCodeArea() {
		return sourceCodeArea;
	}

	public JTextArea getMemoryArea() {
		return memoryArea;
	}

	public void setMemoryDump(MemoryDump updatedMemory) {
		this.memoryDump = updatedMemory;
	}

	public void setTerminalArea(String output) {
		terminalArea.setText(output);
	}

	public String getTerminalArea() {
		return terminalArea.getText();
	}

	private JPanel buildMemoryDumpWindow() {
		memoryArea.setText(memoryDump.toString());
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		memoryPanel.setLayout(new BorderLayout());
		memoryPanel.setPreferredSize(new Dimension(300, 400));
		memoryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		memoryArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		memoryArea.setEditable(false);

		memoryPanel.add(memoryTextField, BorderLayout.NORTH);
		memoryPanel.add(scroll, BorderLayout.CENTER);
		memoryPanel.setVisible(true);
		return memoryPanel;
	}
}
