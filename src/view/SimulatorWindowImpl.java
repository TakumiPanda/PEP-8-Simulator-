package view;

import model.MemoryDumpImpl;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Observable;

public class SimulatorWindowImpl extends Observable implements SimulatorWindow{

	private static int PANEL_WIDTH = 400;
	private static int PANEL_HEIGHT = 200;

	/* Main component. */
	private JPanel mainPanel = new JPanel();
	private MemoryDumpImpl memoryDump;

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
	private JLabel nLabel = new JLabel("                          N");
	private JTextField nField = new JTextField();
	private JLabel zLabel = new JLabel("Z");
	private JTextField zField = new JTextField();
	private JLabel vLabel = new JLabel("V");
	private JTextField vField = new JTextField();
	private JLabel cLabel = new JLabel("C");
	private JTextField cField = new JTextField();
	private JLabel accumalatorLabel = new JLabel("Accumulator");
	private JTextField accumalatorField = new JTextField(); 
	private JLabel indexRegisterLabel = new JLabel("Index Register");
	private JTextField indexRegisterField = new JTextField();
	private JLabel stackPointerLabel = new JLabel("Stack Pointer");
	private JTextField stackPointerField = new JTextField();
	private JLabel programCounterLabel = new JLabel("Program Counter");
	private JTextField programCounterField = new JTextField();;
	private JLabel instructionSpecifierLabel = new JLabel("Instruction Specifier");
	private JTextField instructionSpecifierField = new JTextField();
	private JLabel operandSpecifierLabel = new JLabel("Operand Specifier");
	private JTextField operandSpecifierField = new JTextField();
	private JLabel operandLabel = new JLabel("(Operand)");
	private JTextField operandField = new JTextField();
	private JButton singleStepButton = new JButton("Single Step");
	private JButton resumeButton = new JButton("Resume");

	/* Terminal window components. */
	private JTextArea terminalArea = new JTextArea();
	private JLabel terminalTextField = new JLabel("Input/Output");
	private JPanel terminalPanel = new JPanel();

	/* Memory Dump window components. */
	private JTextArea memoryArea = new JTextArea();
	private JLabel memoryTextField = new JLabel("Memory Dump");
	private JPanel memoryPanel = new JPanel();
	private JScrollPane scroll = new JScrollPane(memoryArea);



	public SimulatorWindowImpl(MemoryDumpImpl memory) throws IOException {
		this.memoryDump = memory;
		mainPanel.setLayout(new FlowLayout());
		mainPanel.setBackground(Color.BLACK);

		Image img = ImageIO.read(new FileInputStream("resources/play_button.png"));
		executeButton.setIcon(new ImageIcon(img));

		Image img2 = ImageIO.read(new FileInputStream("resources/loader_resize.png"));
		loaderButton.setIcon(new ImageIcon(img2));

		executeButton.addActionListener(e -> {
			setChanged();
			notifyObservers();
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

	@Override
	public void reset() {
		objectCodeArea.setText("");
		terminalArea.setText("");
	}
	@Override
	public JPanel getMainPanel() {
		return mainPanel;
	}
	@Override
	public JTextArea getObjectCodeArea() {
		return objectCodeArea;
	}
	@Override
	public JTextArea getSourceCodeArea() {
		return sourceCodeArea;
	}
	@Override
	public JTextArea getMemoryArea() {
		return memoryArea;
	}
	@Override
	public void setMemoryDump(MemoryDumpImpl updatedMemory) {
		this.memoryDump = updatedMemory;
	}
	@Override
	public void setTerminalArea(String output) {
		terminalArea.setText(output);
	}
	@Override
	public String getTerminalArea() {
		return terminalArea.getText();
	}
	@Override 
	public Map<String, JTextField> getCPUComponents() {
		return Map.ofEntries(
			entry("N", nField),
			entry("Z", zField),
			entry("V", vField),
			entry("C", cField),
			entry("Accumulator", accumalatorField),
			entry("Index Register", indexRegisterField),
			entry("Stack Pointer", stackPointerField),
			entry("Program Counter", programCounterField),
			entry("Instruction Specifier", instructionSpecifierField),
			entry("Operand Specifier", operandSpecifierField),
			entry("Operand", operandField)
		);
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
		sourceCodePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		sourceCodePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		sourceCodeText.setBorder(BorderFactory.createLineBorder(Color.black));
		sourceCodePanel.add(sourceCodeText, BorderLayout.NORTH);
		sourceCodePanel.add(sourceCodeArea, BorderLayout.CENTER);
		return sourceCodePanel;
	}

	private JPanel buildObjectCodePanel() {
		objectCodePanel.setLayout(new BorderLayout());
		objectCodePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		objectCodePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		objectCodeText.setBorder(BorderFactory.createLineBorder(Color.black));
		objectCodeArea.setLineWrap(true);
		objectCodePanel.add(objectCodeText, BorderLayout.NORTH);
		objectCodePanel.add(objectCodeArea, BorderLayout.CENTER);
		return objectCodePanel;
	}

	private JPanel buildCpuWindow() {
		cpuPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		cpuPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		cpuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cpuArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		nField.setPreferredSize(new Dimension(PANEL_WIDTH/8, PANEL_HEIGHT/10)); nField.setEditable(false);
		zField.setPreferredSize(new Dimension(PANEL_WIDTH/8, PANEL_HEIGHT/10)); zField.setEditable(false);
		vField.setPreferredSize(new Dimension(PANEL_WIDTH/8, PANEL_HEIGHT/10)); vField.setEditable(false);
		cField.setPreferredSize(new Dimension(PANEL_WIDTH/8, PANEL_HEIGHT/10)); cField.setEditable(false);
		accumalatorField.setPreferredSize(new Dimension(PANEL_WIDTH/2, PANEL_HEIGHT/10)); accumalatorField.setEditable(false);
		indexRegisterField.setPreferredSize(new Dimension(PANEL_WIDTH/2, PANEL_HEIGHT/10)); indexRegisterField.setEditable(false);
		stackPointerField.setPreferredSize(new Dimension(PANEL_WIDTH/2, PANEL_HEIGHT/10)); stackPointerField.setEditable(false);
		programCounterField.setPreferredSize(new Dimension(PANEL_WIDTH/2, PANEL_HEIGHT/10)); programCounterField.setEditable(false);
		instructionSpecifierField.setPreferredSize(new Dimension(PANEL_WIDTH/2, PANEL_HEIGHT/10)); instructionSpecifierField.setEditable(false);
		operandSpecifierField.setPreferredSize(new Dimension(PANEL_WIDTH/2, PANEL_HEIGHT/10)); operandField.setEditable(false);
		operandField.setPreferredSize(new Dimension(PANEL_WIDTH/2, PANEL_HEIGHT/10)); operandSpecifierField.setEditable(false);
		singleStepButton.setPreferredSize(new Dimension(PANEL_WIDTH/4, PANEL_HEIGHT/10)); 
		resumeButton.setPreferredSize(new Dimension(PANEL_WIDTH/4, PANEL_HEIGHT/10)); 
		singleStepButton.setFont(new Font("Arial", Font.PLAIN, 9));
		resumeButton.setFont(new Font("Arial", Font.PLAIN, 9));
		constraints.gridx = 2; constraints.gridy = 0;
		cpuPanel.add(cpuTextField, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		cpuPanel.add(nLabel, constraints);
		constraints.gridx = 1;
		cpuPanel.add(nField, constraints);
		constraints.gridx = 2;
		cpuPanel.add(zLabel, constraints);
		constraints.gridx = 3;
		cpuPanel.add(zField, constraints);
		constraints.gridx = 4;
		cpuPanel.add(vLabel, constraints);
		constraints.gridx = 5;
		cpuPanel.add(vField, constraints);
		constraints.gridx = 6;
		cpuPanel.add(cLabel, constraints);
		constraints.gridx = 7;
		cpuPanel.add(cField, constraints);
		constraints.gridx = 0; constraints.gridy = 2;
		constraints.gridwidth = 1;
		cpuPanel.add(accumalatorLabel, constraints);
		constraints.gridx++;
		constraints.gridwidth = 7;
		cpuPanel.add(accumalatorField, constraints);
		constraints.gridy++; constraints.gridx = 0;
		constraints.gridwidth = 1;
		cpuPanel.add(indexRegisterLabel, constraints);
		constraints.gridx++; 
		constraints.gridwidth = 7;
		cpuPanel.add(indexRegisterField, constraints);

		constraints.gridy++; constraints.gridx = 0;
		constraints.gridwidth = 1;
		cpuPanel.add(stackPointerLabel, constraints);
		constraints.gridx++; 
		constraints.gridwidth = 7;
		cpuPanel.add(stackPointerField, constraints);

		constraints.gridy++; constraints.gridx = 0;
		constraints.gridwidth = 1;
		cpuPanel.add(programCounterLabel, constraints);
		constraints.gridx++; 
		constraints.gridwidth = 7;
		cpuPanel.add(programCounterField, constraints);

		constraints.gridy++; constraints.gridx = 0;
		constraints.gridwidth = 1;
		cpuPanel.add(instructionSpecifierLabel, constraints);
		constraints.gridx++; 
		constraints.gridwidth = 7;
		cpuPanel.add(instructionSpecifierField, constraints);

		constraints.gridy++; constraints.gridx = 0;
		constraints.gridwidth = 1;
		cpuPanel.add(operandSpecifierLabel, constraints);
		constraints.gridx++; 
		constraints.gridwidth = 7;
		cpuPanel.add(operandSpecifierField, constraints);

		constraints.gridy++; constraints.gridx = 0;
		constraints.gridwidth = 1;
		cpuPanel.add(operandLabel, constraints);
		constraints.gridx++; 
		constraints.gridwidth = 7;
		cpuPanel.add(operandField, constraints);

		
		constraints.gridy++; constraints.gridx = 0;
		constraints.gridwidth = 4;
		cpuPanel.add(singleStepButton, constraints);
		constraints.gridx++;
		cpuPanel.add(resumeButton, constraints); 
	
	
		return cpuPanel;
	}

	private JPanel buildTerminalWindow() {
		terminalPanel.setLayout(new BorderLayout());
		terminalPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		terminalPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		terminalArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		terminalPanel.add(terminalTextField, BorderLayout.NORTH);
		terminalPanel.add(terminalArea, BorderLayout.CENTER);
		return terminalPanel;
	}
	private JPanel buildMemoryDumpWindow() {
		memoryArea.setText(memoryDump.toString());
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		memoryPanel.setLayout(new BorderLayout());
		memoryPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		memoryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		memoryArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		memoryArea.setEditable(false);

		memoryPanel.add(memoryTextField, BorderLayout.NORTH);
		memoryPanel.add(scroll, BorderLayout.CENTER);
		memoryPanel.setVisible(true);
		return memoryPanel;
	}
}
