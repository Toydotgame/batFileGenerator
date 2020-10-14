package batFileGenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener {
	static String versionRing = "Release";
	static String version = "1.0";
	
	private static JLabel instructionsText;
	private static JTextField commandField;
	private static JButton createButton;
	private static JButton runButton;
	private static JLabel successText;
	
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		JFrame frame = new JFrame("Command Prompt Batch Creator - Version " + versionRing + " " + version);
		JPanel panel = new JPanel();
		
		frame.setSize(350, 235);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		panel.setLayout(null);
		
		instructionsText = new JLabel("Please type the CMD Command you want to run:");
		instructionsText.setBounds(10, 20, 300, 25);
		panel.add(instructionsText);
		
		commandField = new JTextField();
		commandField.setBounds(10, 45, 300, 25);
		panel.add(commandField);
		
		createButton = new JButton("Create .bat");
		createButton.setBounds(110, 80, 100, 35);
		createButton.addActionListener(new GUI());
		panel.add(createButton);
		
		runButton = new JButton(new AbstractAction("Run Now") {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = commandField.getText();
				System.out.println("Grabbed command input");
				
				createTempBatch();
				addCommandToTempBatch();
				
				try {
					Runtime.getRuntime().exec("cmd /c start \"\" run.bat");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		runButton.setBounds(110, 120, 100, 35);
		panel.add(runButton);
		
		successText = new JLabel("");
		successText.setBounds(10, 165, 300, 25);
		panel.add(successText);
		
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = commandField.getText();
		System.out.println("Grabbed command input");
		
		createBatch();
		addCommandToBatch();
	}
	
	private void createBatch() {
		String command = commandField.getText();
		
		try {
			File outputFile = new File("output.bat");
			if(outputFile.createNewFile()) {
				System.out.println("File created as \"output.bat\"");
				successText.setText("File successfully created.");
			} else {
				System.out.println("Unable to create file.");
				successText.setText("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("Java could not create the file. It may already exist or the jarfile does not have sufficient priveleges.");
			successText.setText("Java could not create the file");
			e.printStackTrace();
		}
	}
	
	private static void createTempBatch() {		
		try {
			File outputFile = new File("run.bat");
			if(outputFile.createNewFile()) {
				System.out.println("File created as \"run.bat\"");
				successText.setText("File successfully created.");
			} else {
				System.out.println("Unable to create file.");
				successText.setText("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("Java could not create the file. It may already exist or the jarfile does not have sufficient priveleges.");
			successText.setText("Java could not create the file");
			e.printStackTrace();
		}
	}
	
	private void addCommandToBatch() {
		String command = commandField.getText();
		String outputBatchName = "output.bat";
		System.out.println("Successfully accessed \"output.bat\"");
		
		try {
			FileWriter myWriter = new FileWriter(outputBatchName);
			
			myWriter.write(command + "\n");
			myWriter.write("pause");
			
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Could not write to access file. Check that the file exists and that the jarfile has sufficient priveleges.");
			successText.setText("Could not write to output file.");
			e.printStackTrace();
		}
	}
	
	private static void addCommandToTempBatch() {
		String command = commandField.getText();
		String outputBatchName = "run.bat";
		System.out.println("Successfully accessed \"run.bat\"");
		
		try {
			FileWriter myWriter = new FileWriter(outputBatchName);
			
			myWriter.write(command + "\n");
			// myWriter.write("pause");
			myWriter.write("(goto) 2>nul & del \"%~f0\"");
			
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Could not write to access file. Check that the file exists and that the jarfile has sufficient priveleges.");
			successText.setText("Could not write to output file.");
			e.printStackTrace();
		}
	}
}
