package view;

import model.MemoryDump;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

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
	private JLabel binCodeText = new JLabel("Binary Code");
	private JTextArea binCodeArea = new JTextArea();
	private JPanel binCodePanel = new JPanel();

	/* Window components for the object code window. */
	private JLabel objectCodeText = new JLabel("Object Code");
	private JTextArea objectCodeArea = new JTextArea();
	private JPanel objectCodePanel = new JPanel();

	/* CPU window components. */
	private JTextArea cpuArea = new JTextArea();
	private JPanel cpuPanel = new JPanel();
	private JLabel cpuTextField = new JLabel("CPU");

	/* Terminal window components. */
	private JTextArea outArea = new JTextArea();
	private JLabel outTextField = new JLabel("Output");
	private JPanel outPanel = new JPanel();
	private JTextArea inArea = new JTextArea();
	private JLabel inTextField = new JLabel("Input");
	private JPanel inPanel = new JPanel();

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

		inArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER && inArea.getText().length() == 1) {
					setChanged();
					notifyObservers(inArea.getText());
				}
			}
		});

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buildButtonPanel(), buildSourceCodePanel());
		JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, buildObjectCodePanel());
		JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buildInWindow(), buildOutWindow());
		JSplitPane splitPane4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buildCpuWindow(), splitPane3);
		JSplitPane splitPane5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane2, splitPane4);
		JSplitPane splitPane6 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane5, buildMemoryDumpWindow());
		mainPanel.add(splitPane6);

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
		binCodePanel.setLayout(new BorderLayout());
		binCodePanel.setPreferredSize(new Dimension(300, 200));
		binCodePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		binCodeText.setBorder(BorderFactory.createLineBorder(Color.black));
		binCodePanel.add(binCodeText, BorderLayout.NORTH);
		binCodePanel.add(binCodeArea, BorderLayout.CENTER);
		return binCodePanel;
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
		Border blackline = BorderFactory.createLineBorder(Color.black);
		cpuTextField.setBorder(blackline);
		cpuPanel.add(cpuTextField, BorderLayout.NORTH);
		
		JPanel cpuInfoPanel = new JPanel();
		cpuInfoPanel.setLayout(new GridLayout(5,2));
		
		JLabel pc = new JLabel("Program Counter (PC): ");
		pc.setBackground(Color.WHITE);
		pc.setOpaque(true);
		pc.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel ar = new JLabel("Accumulator (AR): ");
		ar.setBackground(Color.WHITE);
		ar.setOpaque(true);
		ar.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel ir = new JLabel("Immediate Register (IR): ");
		ir.setBackground(Color.WHITE);
		ir.setOpaque(true);
		ir.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel is = new JLabel("Instruction Specifier: ");
		is.setBackground(Color.WHITE);
		is.setOpaque(true);
		is.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel os = new JLabel("Operand Specifier: ");
		os.setBackground(Color.WHITE);
		os.setOpaque(true);
		os.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JTextField pcText = new JTextField();
		pcText.setText("123\n123\n123\n123");
		pcText.setSize(1,1);
		pcText.setEditable(false);
		JTextField arText = new JTextField();
		arText.setText("123");
		arText.setEditable(false);
		JTextField irText = new JTextField();
		irText.setEditable(false);
		JTextField isText = new JTextField();
		isText.setEditable(false);
		JTextField osText = new JTextField();
		osText.setEditable(false);
		
		cpuInfoPanel.add(pc);
		cpuInfoPanel.add(pcText);
		cpuInfoPanel.add(ar);
		cpuInfoPanel.add(arText);
		cpuInfoPanel.add(ir);
		cpuInfoPanel.add(irText);
		cpuInfoPanel.add(is);
		cpuInfoPanel.add(isText);
		cpuInfoPanel.add(os);
		cpuInfoPanel.add(osText);

		cpuPanel.add(cpuInfoPanel, BorderLayout.CENTER);
		
		return cpuPanel;
	}

	private JPanel buildInWindow() {
		inPanel.setLayout(new BorderLayout());
		inPanel.setPreferredSize(new Dimension(300, 100));
		inPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		inArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		inPanel.add(inTextField, BorderLayout.NORTH);
		inPanel.add(inArea, BorderLayout.CENTER);
		return inPanel;
	}
	
	private JPanel buildOutWindow() {
		outPanel.setLayout(new BorderLayout());
		outPanel.setPreferredSize(new Dimension(300, 100));
		outPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		outArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		outPanel.add(outTextField, BorderLayout.NORTH);
		outPanel.add(outArea, BorderLayout.CENTER);
		return outPanel;
	}

	public JTextArea getObjectCodeArea() {
		return objectCodeArea;
	}
	
	public void setObjectCodeArea(String objCode) {
		this.objectCodeArea.setText(objCode);
	}

	public JTextArea getBinCodeArea() {
		return binCodeArea;
	}
	
	public void setBinCodeArea(String binCode) {
		this.binCodeArea.setText(binCode);
	}

	public JTextArea getMemoryArea() {
		return memoryArea;
	}

	public void setMemoryDump(MemoryDump updatedMemory) {
		this.memoryDump = updatedMemory;
	}

	public void setTerminalArea(String output) {
		outArea.setText(output);
	}

	public String getTerminalArea() {
		return inArea.getText();
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
