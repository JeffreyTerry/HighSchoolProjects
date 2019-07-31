import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Determinator extends JFrame
{
	public static Determinator determinator = new Determinator();

	public GridBagConstraints c = new GridBagConstraints();

	public JButton calculateDeterminantButton = new JButton("Calculate Determinant");
	public JButton clearMatrixButton = new JButton("Clear Matrix");
	
	public ArrayList<JTextField> boxes = new ArrayList<JTextField>();
	
	public int matrixSize = 9;
	
	public static void main(String args[])
	{
		determinator.open();
	}
	
	public Determinator()
	{
		init();
	}
	
	public void init()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Determinator");
		
		setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;

		calculateDeterminantButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		clearMatrixButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = matrixSize;
		c.weighty = matrixSize;
		c.gridwidth = matrixSize;
		c.gridheight = matrixSize;
		add(calculateDeterminantButton, c);

		c.gridx = 0;
		c.gridy = matrixSize;
		c.weightx = matrixSize;
		c.weighty = matrixSize;
		c.gridwidth = matrixSize;
		c.gridheight = matrixSize;
		add(clearMatrixButton, c);
		
		createBoxes();
		addBoxesToWindow();

		calculateDeterminantButton.addActionListener(new ButtonListener());
		clearMatrixButton.addActionListener(new ButtonListener());
		
		calculateDeterminantButton.setToolTipText("Enter a square matrix at the top left corner then press me");
	}
	
	public void open()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void createBoxes()
	{
		int numberOfBoxesToAdd = matrixSize * matrixSize;
		
		for(int j = 0; j < numberOfBoxesToAdd; j++)
		{
			boxes.add(new JTextField(""));
			boxes.get(j).setHorizontalAlignment(JTextField.CENTER);
			boxes.get(j).setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
			boxes.get(j).addActionListener(new TextFieldListener());
		}
	}
	
	public void addBoxesToWindow()
	{
		int rows = (int)Math.sqrt(boxes.size());

		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		
		for(int i = 0 ; i < boxes.size(); i++)
		{
			c.gridx = i % rows;
			c.gridy = i / rows + matrixSize * 3;  //+matrixSize*3 is for the top stuff
			add(boxes.get(i), c);
		}
	}
	
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == clearMatrixButton)
			{
				for(int i = 0 ; i < boxes.size(); i++)
					boxes.get(i).setText("");
			}
			else if(event.getSource() == calculateDeterminantButton)
			{
				//This calculates the number of rows in the matrix entered
				int rows = 0;
				while(!boxes.get(rows).getText().equals("") && rows < matrixSize)  //matrixSize is the maximum number of rows
				{
					rows++;
				}
				
				//This creates the matrix for which the determinant is to be calculated
				int[][] matrix = new int[rows][rows];
				for(int i = 0; i < rows; i++)  //For each row
				{
					for(int j = 0; j < rows; j++)  //For each column
					{
						matrix[j][i] = JMath.convertToInt(boxes.get(i * matrixSize + j).getText());
					}
				}
				
				//This does the calculation and displays it
				JArray.printArray(matrix);
				JOptionPane.showMessageDialog(null, "Determinant: " + JMath.getDeterminant(matrix), "The Determinator Has Spoken", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	public class TextFieldListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			int indexOfAction = 0;
			for(int i = 0; i < boxes.size(); i++)
			{
				if(event.getSource().equals(boxes.get(i)))
				{
					indexOfAction = i;
				}
			}
			if(indexOfAction + 1 < boxes.size())
				boxes.get(indexOfAction + 1).requestFocusInWindow();
		}
	}
}