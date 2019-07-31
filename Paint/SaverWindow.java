import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SaverWindow extends JFrame
{
	private JLabel instructLabel=new JLabel("Enter a name for this painting: ");
	private JTextField fileTextField=new JTextField();
	private String fileName;
	private DrawingComponent drawer;
	public SaverWindow(DrawingComponent d)
	{
		//Sets drawer to d
		drawer=d;
		
		//Sets up the fileTextField
		fileTextField.setPreferredSize(new Dimension(200,20));
		
		//Sets up the window
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new FlowLayout());
		setTitle("Save File");
		add(instructLabel);
		add(fileTextField);
		pack();
		setLocation(500-getWidth()/2,75); //500 is half of the PaintWindow object's width, subject to change
		
		//Makes the fileTextField functional
		fileTextField.addActionListener(new FileTextListener());
	}
	public void display()
	{
		setVisible(true);
	}
	class FileTextListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			fileName=event.getActionCommand();
			FileSaver saver=new FileSaver(fileName);
			saver.save(drawer);
			fileTextField.setText("");
			setVisible(false);
		}
	}
}
