import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class PaintWindow
{
	//Creates a toolkit that will be used to create cursors and images
	private Toolkit tk=Toolkit.getDefaultToolkit();
	
	//Creates the window that is used to create new colors
	NewColorSelectorWindow selectorWindow=new NewColorSelectorWindow();

	//Creates the invisible cursor that will be used by the showToolOutline method
	private Cursor invisibleCursor;
	
	//Variables used by the LineDrawer class that store the previous location the mouse was pressed or dragged in
	private	int lastX,lastY;
	
	//The main container
	private final JFrame frame=new JFrame();

	//The panel that will contain the ColorButtons
	private final JPanel colorPanel=new JPanel();
	
	//ColorButtons
	private final JButton redButton=new ColorButton(Color.RED);
	private final JButton orangeButton=new ColorButton(Color.ORANGE);
	private final JButton yellowButton=new ColorButton(Color.YELLOW);
	private final JButton greenButton=new ColorButton(Color.GREEN);
	private final JButton blueButton=new ColorButton(Color.BLUE);
	private final JButton brownButton=new ColorButton(new Color(150,75,0));
	private final JButton whiteButton=new ColorButton(Color.WHITE);
	private final JButton blackButton=new ColorButton(Color.BLACK);
	private final JButton addColorButton=new JButton();
	
	//The ArrayList that stores all new ColorButtons
	private final ArrayList<ColorButton> newColorButtons=new ArrayList<ColorButton>();

	//The panel that will contain the toolSizeSelector and its label
	private final JPanel toolSizeSelectorPanel=new JPanel();
	
	//The label for the toolSizeSelector
	private final JLabel toolSizeSelectorLabel=new JLabel("Tool Size");
	
	//The slider that is used to change the tool size
	private final JSlider toolSizeSelector=new JSlider(3,103,23);
	
	//The button that decreases the toolSize
	private final JButton toolSizeLessButton=new JButton();

	//The button that increases the toolSize
	private final JButton toolSizeMoreButton=new JButton();

	//The panel that will contain all of the tools
	private final JPanel toolSelectorPanel=new JPanel();

	//The label for the toolSelectorPanel
	private final JLabel toolSelectorLabel=new JLabel("Tools  ");
	
	//The marker tool button
	private final JButton markerToolButton=new JButton();

	//The marker tool button
	private final JButton eraserToolButton=new JButton();

	//The nathan tool button
	private final JButton nathanToolButton=new JButton();
	
	//The panel that will contain all of the other controls
	private final JPanel controlsPanel=new JPanel();

	//The save button
	private final JButton saveButton=new JButton("Save");

	//The load button
	private final JButton loadButton=new JButton("Load");
	
	//The undo button
	private final JButton undoButton=new JButton("Undo");
	
	//The redo button
	private final JButton redoButton=new JButton("Redo");
	
	//The button that clears the drawingBox
	private final JButton clearButton=new JButton("Clear");
	
	//The main DrawingComponent object
	private final DrawingComponent drawingBox=new DrawingComponent();

	//Creates the window that will be used to save paintings
	private SaverWindow saver=new SaverWindow(drawingBox);
	
	//Creates the window that will be used to load paintings
	private LoaderWindow loader=new LoaderWindow(drawingBox);
	
	//Constructor
	public PaintWindow()
	{
		initialize();
		open();
	}
	
	//Initializes the PaintWindow and sets up the window
	public void initialize()
	{
		//Creates the custom cursor that will be used by the showToolOutline method
		invisibleCursor=tk.createCustomCursor(tk.createImage("NonexistentFile.png"),new Point(0,0),"Invisible Cursor");
		
		//Set up the addColorButton
		addColorButton.setPreferredSize(new Dimension(58,26));
		addColorButton.setIcon(new ImageIcon("./AddNewColorImage.png"));
		addColorButton.setToolTipText("Press to add a new color");
		
		//Set up the colorPanel
		colorPanel.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		colorPanel.add(redButton,c);
		c.gridx=1;
		colorPanel.add(orangeButton,c);
		c.gridx=2;
		colorPanel.add(yellowButton,c);
		c.gridx=3;
		colorPanel.add(greenButton,c);
		c.gridx=4;
		colorPanel.add(blueButton,c);
		c.gridx=5;
		colorPanel.add(brownButton,c);
		c.gridx=6;
		colorPanel.add(whiteButton,c);
		c.gridx=7;
		colorPanel.add(blackButton,c);
		c.gridx=8;
		colorPanel.add(addColorButton,c);

		//Set up the toolSizeSelectorPanel
		toolSizeSelectorLabel.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14)); //Set up the label
		toolSizeSelector.setPreferredSize(new Dimension(320,18)); //Set up the selector
		toolSizeSelector.setToolTipText("Slide to adjust the tool size");
		toolSizeLessButton.setPreferredSize(new Dimension(20,20)); //Set up the toolSizeLessButton
		toolSizeLessButton.setIcon(new ImageIcon("./ToolSizeLessImage.png"));
		toolSizeLessButton.setToolTipText("Press to make the tool size smaller");
		toolSizeMoreButton.setPreferredSize(new Dimension(20,20)); //Set up the toolSizeMoreButton
		toolSizeMoreButton.setIcon(new ImageIcon("./ToolSizeMoreImage.png"));
		toolSizeMoreButton.setToolTipText("Press to make the tool size larger");
		toolSizeSelectorPanel.add(toolSizeSelectorLabel);
		toolSizeSelectorPanel.add(toolSizeSelector);
		toolSizeSelectorPanel.add(toolSizeLessButton);
		toolSizeSelectorPanel.add(toolSizeMoreButton);
		
		//Set up the toolSelectorPanel
		toolSelectorLabel.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14)); //Set up the label
		markerToolButton.setPreferredSize(new Dimension(28,28));
		markerToolButton.setIcon(new ImageIcon("./MarkerImageSelected.png"));
		eraserToolButton.setPreferredSize(new Dimension(28,28));
		eraserToolButton.setIcon(new ImageIcon("./EraserImage.png"));
		nathanToolButton.setPreferredSize(new Dimension(28,28));
		nathanToolButton.setIcon(new ImageIcon("./NathanImage.png"));
		toolSelectorPanel.add(toolSelectorLabel);
		toolSelectorPanel.add(markerToolButton);
		toolSelectorPanel.add(eraserToolButton);
		toolSelectorPanel.add(nathanToolButton);
		
		//Set up the controlsPanel
		saveButton.setFont(new Font(Font.SERIF,Font.BOLD,12)); //Set up the saveButton
		loadButton.setFont(new Font(Font.SERIF,Font.BOLD,12)); //Set up the loadButton
		undoButton.setFont(new Font(Font.SERIF,Font.BOLD,12)); //Set up the undoButton
		redoButton.setFont(new Font(Font.SERIF,Font.BOLD,12)); //Set up the redoButton
		clearButton.setFont(new Font(Font.SERIF,Font.BOLD,12)); //Set up the clearButton
		saveButton.setPreferredSize(new Dimension(64,27));
		loadButton.setPreferredSize(new Dimension(64,27));
		undoButton.setPreferredSize(new Dimension(64,27));
		redoButton.setPreferredSize(new Dimension(64,27));
		clearButton.setPreferredSize(new Dimension(64,27));
		controlsPanel.add(saveButton);
		controlsPanel.add(loadButton);
		controlsPanel.add(undoButton);
		controlsPanel.add(redoButton);
		controlsPanel.add(clearButton);
		
		//Set up the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Paint Special Edition XXX");
		frame.setSize(1000,700);
		frame.setLayout(new GridBagLayout());
		
		//Add the colorPanel to the frame, then add the drawingBox to the frame
		c=new GridBagConstraints();
		
		c.gridx=0; //Constraints for the colorPanel
		c.gridy=0;
		c.weightx=1;
		c.weighty=0.5;
		c.fill=GridBagConstraints.BOTH;
		frame.add(colorPanel,c);

		c.gridx=0; //Constraints for the toolSizeSelectorPanel
		c.gridy=1;
		c.weightx=1;
		c.weighty=0.05;
		c.fill=GridBagConstraints.VERTICAL;
		frame.add(toolSizeSelectorPanel,c);
		
		c.gridx=0; //Constraints for the toolSelectorPanel
		c.gridy=2;
		c.weightx=1;
		c.weighty=0.5;
		c.fill=GridBagConstraints.VERTICAL;
		frame.add(toolSelectorPanel,c);
		
		c.gridx=0; //Constraints for the controlsPanel
		c.gridy=3;
		c.weightx=1;
		c.weighty=0.5;
		c.fill=GridBagConstraints.BOTH;
		frame.add(controlsPanel,c);

		c.gridx=0; //Constraints for the drawingBox
		c.gridy=4;
		c.weightx=1;
		c.weighty=20;
		c.fill=GridBagConstraints.BOTH;
		frame.add(drawingBox,c);
		
		//Add ColorButtonListeners to all of the ColorButtons
		redButton.addActionListener(new ColorButtonListener());
		orangeButton.addActionListener(new ColorButtonListener());
		yellowButton.addActionListener(new ColorButtonListener());
		greenButton.addActionListener(new ColorButtonListener());
		blueButton.addActionListener(new ColorButtonListener());
		brownButton.addActionListener(new ColorButtonListener());
		whiteButton.addActionListener(new ColorButtonListener());
		blackButton.addActionListener(new ColorButtonListener());

		//Add functionality to the addColorButton
		addColorButton.addActionListener(new AddColorButtonListener());
		
		//Add functionality to the toolSizeSelector
		toolSizeSelector.addChangeListener(new ToolSizeChanger());

		//Set up the toolSizeLessButton and the toolSizeMoreButton
		toolSizeLessButton.addActionListener(new ToolSizeButtonListener());
		toolSizeMoreButton.addActionListener(new ToolSizeButtonListener());
		
		//Set up the markerToolButton
		markerToolButton.addActionListener(new ToolButtonListener());

		//Set up the eraserToolButtonButton
		eraserToolButton.addActionListener(new ToolButtonListener());
		
		//Set up the eraserToolButtonButton
		nathanToolButton.addActionListener(new ToolButtonListener());

		//Set up the saveButton
		saveButton.addActionListener(new SaveButtonListener());
		saveButton.addMouseListener(new WindowMouseListener()); //Add a WindowMouseListener to the undoButton to make sure the mouse is sensed there

		//Set up the loadButton
		loadButton.addActionListener(new LoadButtonListener());
		loadButton.addMouseListener(new WindowMouseListener()); //Add a WindowMouseListener to the undoButton to make sure the mouse is sensed there
		
		//Set up the undoButton
		undoButton.addActionListener(new UndoButtonListener());
		undoButton.addMouseListener(new WindowMouseListener()); //Add a WindowMouseListener to the undoButton to make sure the mouse is sensed there
		
		//Set up the redoButton
		redoButton.addActionListener(new RedoButtonListener());
		redoButton.addMouseListener(new WindowMouseListener()); //Add a WindowMouseListener to the redoButton to make sure the mouse is sensed there
		
		//Set up the clearButton
		clearButton.addActionListener(new ClearButtonListener());
		clearButton.addMouseListener(new WindowMouseListener()); //Add a WindowMouseListener to the clearButton to make sure the mouse is sensed there

		//Add the drawing capability to the drawingBox
		drawingBox.addMouseListener(new ShapeDrawer());
		drawingBox.addMouseMotionListener(new ShapeDrawer());
		
		//Add a MouseListener to the window
		frame.addMouseListener(new WindowMouseListener());
	}
	
	//Opens up the PaintWindow
	public void open()
	{
		frame.setVisible(true);
	}
	
	public void showToolOutline(int x,int y)
	{
		if(drawingBox.getTool()=="eraser")
		{
			drawingBox.showEraserOutline(x,y);
			frame.setCursor(invisibleCursor);
		}
		else if(drawingBox.getTool()=="marker")
		{
			drawingBox.showMarkerOutline(x,y);
			frame.setCursor(invisibleCursor);
		}
		else if(drawingBox.getTool()=="nathan")
		{
			drawingBox.showNathanOutline(x,y);
			frame.setCursor(invisibleCursor);
		}
	}
	
	//ColorButtonListener listens for ColorButton clicks
	class ColorButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			ColorButton buttonPressed=(ColorButton)event.getSource();
			Color c=buttonPressed.getColor();
			drawingBox.setColor(c);
			
			//Does the equivalent of what pressing the markerToolButton would do
			markerToolButton.setIcon(new ImageIcon("./MarkerImageSelected.png"));
			eraserToolButton.setIcon(new ImageIcon("./EraserImage.png"));
			nathanToolButton.setIcon(new ImageIcon("./NathanImage.png"));
			drawingBox.setTool("marker");
			drawingBox.showMarkerOutline(frame.getWidth()/2,100);
		}
	}
	
	//AddColorButtonListener listens for addColorButton clicks
	class AddColorButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			selectorWindow.display();
		}
	}

	//ToolSizeChanger
	class ToolSizeChanger implements ChangeListener
	{
		public void stateChanged(ChangeEvent event)
		{
			drawingBox.setToolSize(toolSizeSelector.getValue());
			if(drawingBox.getTool()=="eraser")
			{
				drawingBox.showEraserOutline(frame.getWidth()/2,100);
			}
			else if(drawingBox.getTool()=="marker")
			{
				drawingBox.showMarkerOutline(frame.getWidth()/2,100);
			}
			else if(drawingBox.getTool()=="nathan")
			{
				drawingBox.showNathanOutline(frame.getWidth()/2,100);
			}
			
		}
	}

	class ToolSizeButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==toolSizeLessButton)
			{
				toolSizeSelector.setValue(toolSizeSelector.getValue()-1);
			}
			else if(event.getSource()==toolSizeMoreButton)
			{
				toolSizeSelector.setValue(toolSizeSelector.getValue()+1);
			}
		}
	}
	
	//ToolButtonListener
	class ToolButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==markerToolButton)
			{
				markerToolButton.setIcon(new ImageIcon("./MarkerImageSelected.png"));
				eraserToolButton.setIcon(new ImageIcon("./EraserImage.png"));
				nathanToolButton.setIcon(new ImageIcon("./NathanImage.png"));
				drawingBox.setTool("marker");
				drawingBox.showMarkerOutline(frame.getWidth()/2,100);
			}
			else if(event.getSource()==eraserToolButton)
			{
				markerToolButton.setIcon(new ImageIcon("./MarkerImage.png"));
				eraserToolButton.setIcon(new ImageIcon("./EraserImageSelected.png"));
				nathanToolButton.setIcon(new ImageIcon("./NathanImage.png"));
				drawingBox.setTool("eraser");
				drawingBox.showEraserOutline(frame.getWidth()/2,100);
			}
			else if(event.getSource()==nathanToolButton)
			{
				markerToolButton.setIcon(new ImageIcon("./MarkerImage.png"));
				eraserToolButton.setIcon(new ImageIcon("./EraserImage.png"));
				nathanToolButton.setIcon(new ImageIcon("./NathanImageSelected.png"));
				drawingBox.setTool("nathan");
				drawingBox.showNathanOutline(frame.getWidth()/2,100);
			}
		}
	}

	//UndoButtonListener
	class SaveButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			saver.display();
		}
	}

	//UndoButtonListener
	class LoadButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			loader.display();
		}
	}

	//UndoButtonListener
	class UndoButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			drawingBox.undo();
		}
	}

	//RedoButtonListener
	class RedoButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			drawingBox.redo();
		}
	}
	
	//ClearButtonListener
	class ClearButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			drawingBox.clear();
			drawingBox.repaint();
		}
	}
	
	//LineDrawer
	class ShapeDrawer implements MouseListener,MouseMotionListener
	{
		public void mousePressed(MouseEvent event)
		{
			drawingBox.addAction();
			drawingBox.drawPoint(event.getX(),event.getY());
			lastX=event.getX();
			lastY=event.getY();
		}
		public void mouseClicked(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
		public void mouseEntered(MouseEvent event)
		{
			if(selectorWindow.colorAdded)
			{
				selectorWindow.colorAdded=false;
				newColorButtons.add(new ColorButton(selectorWindow.getColor(),true));
				redraw();
			}
		}
		public void mouseExited(MouseEvent event)
		{
			frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		public void mouseMoved(MouseEvent event)
		{
			showToolOutline(event.getX(),event.getY());
		}
		public void mouseDragged(MouseEvent event)
		{
			if(drawingBox.getTool()!="nathan")
			{
				showToolOutline(event.getX(),event.getY());
				
				//The following code adds the optimal number of circles between the previous dot and the current dot to ensure continuous drawing
				int numberOfIntermediateCircles=(int)Math.sqrt(Math.pow(event.getX()-lastX,2)+Math.pow(event.getY()-lastY,2));
				for(int i=0;i<=numberOfIntermediateCircles;i++)
				{
					drawingBox.drawPoint((i*event.getX()+(numberOfIntermediateCircles-i)*lastX)/numberOfIntermediateCircles,(i*event.getY()+(numberOfIntermediateCircles-i)*lastY)/numberOfIntermediateCircles);
				}
				
				//Record the coordinates of this event
				lastX=event.getX();
				lastY=event.getY();
			}
			else
			{
				drawingBox.showNathanOutline(event.getX(),event.getY());
			}
		}
	}
	
	//WindowMouseListener
	class WindowMouseListener implements MouseListener
	{
		public void mousePressed(MouseEvent event){}
		public void mouseClicked(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
		public void mouseEntered(MouseEvent event)
		{
			if(selectorWindow.colorAdded)
			{
				selectorWindow.colorAdded=false;
				newColorButtons.add(new ColorButton(selectorWindow.getColor(),true));
				redraw();
			}
		}
		public void mouseExited(MouseEvent event){}
		public void mouseMoved(MouseEvent event){}
	}
	
	
	
	//A method that redraws the frame
	public void redraw()
	{
		//Set up the addColorButton
		addColorButton.setPreferredSize(new Dimension(58,26));
		addColorButton.setIcon(new ImageIcon("./AddNewColorImage.png"));
		addColorButton.setToolTipText("Press to add a new color");
		
		//Set up the colorPanel
		colorPanel.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridy=0;
		c.gridx=0;
		colorPanel.add(redButton,c);
		c.gridx=1;
		colorPanel.add(orangeButton,c);
		c.gridx=2;
		colorPanel.add(yellowButton,c);
		c.gridx=3;
		colorPanel.add(greenButton,c);
		c.gridx=4;
		colorPanel.add(blueButton,c);
		c.gridx=5;
		colorPanel.add(brownButton,c);
		c.gridx=6;
		colorPanel.add(whiteButton,c);
		c.gridx=7;
		colorPanel.add(blackButton,c);
		c.gridx=8;
		colorPanel.add(addColorButton,c);
		
		int colorGridXCounter=0;
		int colorGridYCounter=1;
		for(ColorButton button: newColorButtons)
		{
			if(colorGridXCounter%9==0 && colorGridXCounter>=9)
				colorGridYCounter++;
			
			c.gridx=colorGridXCounter-(colorGridYCounter-1)*9;
			c.gridy=colorGridYCounter;
			colorPanel.add(button,c);
			button.addActionListener(new ColorButtonListener());
			colorGridXCounter++;
		}
	
		//Set up the toolSizeSelectorPanel
		toolSizeSelectorLabel.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14)); //Set up the label
		toolSizeSelector.setPreferredSize(new Dimension(320,18)); //Set up the selector
		toolSizeSelector.setToolTipText("Slide to adjust the tool size");
		toolSizeLessButton.setPreferredSize(new Dimension(20,20)); //Set up the toolSizeLessButton
		toolSizeLessButton.setIcon(new ImageIcon("./ToolSizeLessImage.png"));
		toolSizeLessButton.setToolTipText("Press to make the tool size smaller");
		toolSizeMoreButton.setPreferredSize(new Dimension(20,20)); //Set up the toolSizeMoreButton
		toolSizeMoreButton.setIcon(new ImageIcon("./ToolSizeMoreImage.png"));
		toolSizeMoreButton.setToolTipText("Press to make the tool size larger");
		toolSizeSelectorPanel.add(toolSizeSelectorLabel);
		toolSizeSelectorPanel.add(toolSizeSelector);
		toolSizeSelectorPanel.add(toolSizeLessButton);
		toolSizeSelectorPanel.add(toolSizeMoreButton);
		
		//Set up the toolSelectorPanel
		toolSelectorLabel.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14)); //Set up the label
		markerToolButton.setPreferredSize(new Dimension(28,28));
		markerToolButton.setIcon(new ImageIcon("./MarkerImageSelected.png"));
		eraserToolButton.setPreferredSize(new Dimension(28,28));
		eraserToolButton.setIcon(new ImageIcon("./EraserImage.png"));
		nathanToolButton.setPreferredSize(new Dimension(28,28));
		nathanToolButton.setIcon(new ImageIcon("./NathanImage.png"));
		toolSelectorPanel.add(toolSelectorLabel);
		toolSelectorPanel.add(markerToolButton);
		toolSelectorPanel.add(eraserToolButton);
		toolSelectorPanel.add(nathanToolButton);
		
		//Set up the controlsPanel
		undoButton.setFont(new Font(Font.SERIF,Font.BOLD,12)); //Set up the undoButton
		redoButton.setFont(new Font(Font.SERIF,Font.BOLD,12)); //Set up the redoButton
		clearButton.setFont(new Font(Font.SERIF,Font.BOLD,12)); //Set up the clearButton
		controlsPanel.add(undoButton);
		controlsPanel.add(redoButton);
		controlsPanel.add(clearButton);
		
		//Set up the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,700);
		frame.setLayout(new GridBagLayout());
		
		//Add the colorPanel to the frame, then add the drawingBox to the frame
		c=new GridBagConstraints();
		
		c.gridx=0; //Constraints for the colorPanel
		c.gridy=0;
		c.weightx=1;
		c.weighty=0.5;
		c.fill=GridBagConstraints.BOTH;
		frame.add(colorPanel,c);
		c.gridx=0; //Constraints for the toolSizeSelectorPanel
		c.gridy=1;
		c.weightx=1;
		c.weighty=0.05;
		c.fill=GridBagConstraints.VERTICAL;
		frame.add(toolSizeSelectorPanel,c);
		
		c.gridx=0; //Constraints for the toolSelectorPanel
		c.gridy=2;
		c.weightx=1;
		c.weighty=0.5;
		c.fill=GridBagConstraints.VERTICAL;
		frame.add(toolSelectorPanel,c);
		
		c.gridx=0; //Constraints for the controlsPanel
		c.gridy=3;
		c.weightx=1;
		c.weighty=0.5;
		c.fill=GridBagConstraints.BOTH;
		frame.add(controlsPanel,c);
		c.gridx=0; //Constraints for the drawingBox
		c.gridy=4;
		c.weightx=1;
		c.weighty=20;
		c.fill=GridBagConstraints.BOTH;
		frame.add(drawingBox,c);
	}
}
