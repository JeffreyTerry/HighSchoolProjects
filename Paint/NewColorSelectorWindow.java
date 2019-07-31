import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class NewColorSelectorWindow extends JFrame
{
	//This may be used by other programs to determine whether or not to redraw the window
	public boolean colorAdded=false;
	
	private int red=127,green=127,blue=127;
	
	private JLabel redLabel=new JLabel("Red   (127)");
	private JSlider redSlider=new JSlider(0,255,127);

	private JLabel greenLabel=new JLabel("Green (127)");
	private JSlider greenSlider=new JSlider(0,255,127);
	
	private JLabel blueLabel=new JLabel("Blue  (127)");
	private JSlider blueSlider=new JSlider(0,255,127);
	
	private ColorDisplayer displayer=new ColorDisplayer(new Color(127,127,127));
	
	private JButton createColorButton=new JButton("Create Color");
	
	public NewColorSelectorWindow()
	{
		//Sets up the window
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setTitle("Color Creator");
		
		//Sets up the sliders
		redSlider.setPreferredSize(new Dimension(500,20));
		greenSlider.setPreferredSize(new Dimension(500,20));
		blueSlider.setPreferredSize(new Dimension(500,20));
		redSlider.setBackground(Color.RED);
		greenSlider.setBackground(Color.GREEN);
		blueSlider.setBackground(Color.BLUE);
		
		//Sets up the redPanel
		redLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14)); //Set up the label

		//Sets up the greenPanel
		greenLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14)); //Set up the label

		//Sets up the bluePanel
		blueLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14)); //Set up the label

		//Sets up the displayer
		displayer.setPreferredSize(new Dimension(316,25));
		
		//Prepares the constraints for the labels
		GridBagConstraints c=new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(0,10,0,0);
		c.weightx=1;
		c.weighty=1;

		//Prepares the constraints for the sliders
		GridBagConstraints b=new GridBagConstraints();
		b.fill=GridBagConstraints.BOTH;
		b.insets=new Insets(0,0,0,10);
		b.weightx=2;
		b.weighty=1;
		
		//Prepares the constraints for the createColorButton and the displayer
		GridBagConstraints a=new GridBagConstraints();
		a.fill=GridBagConstraints.BOTH;
		a.gridwidth=2;
		a.weighty=1;
				
		//Adds the redLabel to the frame
		c.gridx=0;
		c.gridy=0;
		add(redLabel,c);

		//Adds the redSlider to the frame
		b.gridx=1;
		b.gridy=0;
		add(redSlider,b);
		
		//Adds the greenLabel to the frame
		c.gridx=0;
		c.gridy=1;
		add(greenLabel,c);

		//Adds the greenSlider to the frame
		b.gridx=1;
		b.gridy=1;
		add(greenSlider,b);
		
		//Adds the blueLabel to the frame
		c.gridx=0;
		c.gridy=2;
		add(blueLabel,c);
		
		//Adds the blueSlider to the frame
		b.gridx=1;
		b.gridy=2;
		add(blueSlider,b);

		//Adds the ColorDisplayer to the frame
		a.gridx=0;
		a.gridy=3;
		add(displayer,a);
		
		//Adds the createColorButton to the frame
		a.gridx=0;
		a.gridy=4;
		add(createColorButton,a);
		
		//Adds functionality to the sliders
		redSlider.addChangeListener(new ColorSliderListener());
		greenSlider.addChangeListener(new ColorSliderListener());
		blueSlider.addChangeListener(new ColorSliderListener());
		
		//Adds functionality to the createColorButton
		createColorButton.addActionListener(new CreateColorButtonListener());
		
		
		//Finish setting up the frame
		
		//Pack all of the components into the window
		pack();
		//Set the location to the upper middle of the Paint Window
		setLocation(500-getWidth()/2,75); //500 is half of the PaintWindow object's width, subject to change
	}
	
	public void display()
	{
		redSlider.setValue(127);
		greenSlider.setValue(127);
		blueSlider.setValue(127);
		setVisible(true);
	}
	
	public Color getColor()
	{
		return new Color(red,green,blue);
	}
	
	public void exit()
	{
		colorAdded=true;
		setVisible(false);
	}
	
	class ColorSliderListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent event)
		{
			if(event.getSource()==redSlider)
			{
				red=redSlider.getValue();
				if(redSlider.getValue()>=100)
					redLabel.setText("Red   ("+redSlider.getValue()+")");
				else if(redSlider.getValue()>=10)
					redLabel.setText("Red   ("+redSlider.getValue()+") ");
				else
					redLabel.setText("Red   ("+redSlider.getValue()+")  ");
			}
			else if(event.getSource()==greenSlider)
			{
				green=greenSlider.getValue();
				if(greenSlider.getValue()>=100)
					greenLabel.setText("Green ("+greenSlider.getValue()+")");
				else if(greenSlider.getValue()>=10)
					greenLabel.setText("Green ("+greenSlider.getValue()+") ");
				else
					greenLabel.setText("Green ("+greenSlider.getValue()+")  ");
			}
			else if(event.getSource()==blueSlider)
			{
				blue=blueSlider.getValue();
				if(blueSlider.getValue()>=100)
					blueLabel.setText("Blue  ("+blueSlider.getValue()+")");
				else if(blueSlider.getValue()>=10)
					blueLabel.setText("Blue  ("+blueSlider.getValue()+") ");
				else
					blueLabel.setText("Blue  ("+blueSlider.getValue()+")  ");
			}
			displayer.setColor(new Color(red,green,blue));
			setVisible(true);
		}
	}
	
	 class CreateColorButtonListener implements ActionListener
	 {
		 public void actionPerformed(ActionEvent event)
		 {
			 exit();
		 }
	 }
}
