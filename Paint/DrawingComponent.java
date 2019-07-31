import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class DrawingComponent extends JComponent
{
	private Graphics2D page;

	//Creates a toolkit that will be used to create images
	private Toolkit tk=Toolkit.getDefaultToolkit();
	
	//Creates an image of nathan
	Image geographyWizardImage=tk.createImage("./NathanCursorImage.png");
	
	//Stores the current tool in use
	private String tool="marker";

	//Records the marker's current x and y positions
	private int markerXPosition,markerYPosition;
	
	//Determines whether or not the marker outline is shown in the paintComponent method
	private boolean markerOutlineIsVisible=true;

	//Records the eraser's current x and y positions
	private int eraserXPosition,eraserYPosition;
	
	//Determines whether or not the eraser outline is shown in the paintComponent method
	private boolean eraserOutlineIsVisible=false;

	//Records the eraser's current x and y positions
	private int nathanXPosition,nathanYPosition;
	
	//Determines whether or not the eraser outline is shown in the paintComponent method
	private boolean nathanOutlineIsVisible=false;
		
	//A variable that is used by the undo method to determine if the DrawingComponent object was just cleared
	private boolean wasCleared=false;

	//A variable that is used by the drawPoint method to determine if something was just undone or redone
	private boolean undone=false,redone=false;
	
	//A variable that records how many times the user has undone actions consecutively
	private int actionsUndone=0;
	
	//A variable that stores how many points are now permanent due to an action taken after an undo
	private int permanentPoints=0;
	
	//Stores the length of each action taken (an action is defined as a click and drag)
	private ArrayList<Integer> actionsTaken=new ArrayList<Integer>();
	
	//The current drawing color
	private Color color=Color.BLACK;
	
	//The size of the drawing tool
	private int toolSize=23;

	//The ArrayList in which all the points ever drawn are contained
	private ArrayList<ShapeObject> shapesToDraw=new ArrayList<ShapeObject>();
	
	//The ArrayList in which all the points ever drawn are contained
	private ArrayList<ShapeObject> shapesRemoved=new ArrayList<ShapeObject>();
	
	//The paintComponent method paints all CircleObjects contained within shapesToDraw onto the screen
	public void paintComponent(Graphics g)
	{
		page=(Graphics2D)g;
		
		//Draws a white rectangle as the background
		page.setColor(Color.WHITE);
		page.fillRect(0,0,984,492); //The width and height are flush with the edge of the window
		
		//Draws the CircleObjects to the screen
		for(int i=0;i<shapesToDraw.size();i++)
		{
			if(shapesToDraw.get(i).getClassID()=="CircleObject")
			{
				page.setColor(shapesToDraw.get(i).getColor());
				page.fillOval(shapesToDraw.get(i).getX()-shapesToDraw.get(i).getSize()/2,shapesToDraw.get(i).getY()-shapesToDraw.get(i).getSize()/2,shapesToDraw.get(i).getSize(),shapesToDraw.get(i).getSize());
			}
			else if(shapesToDraw.get(i).getClassID()=="NathanObject")
			{
				page.drawImage(geographyWizardImage,shapesToDraw.get(i).getX(),shapesToDraw.get(i).getY(),shapesToDraw.get(i).getSize(),shapesToDraw.get(i).getSize(),null);
			}
		}
		
		//Draws the tool outline to the screen
		if(eraserOutlineIsVisible)
		{
			page.setColor(Color.BLACK);
			page.fillOval(eraserXPosition-toolSize/2,eraserYPosition-toolSize/2,toolSize,toolSize);
			page.setColor(new Color(238,238,238));
			page.fillOval(eraserXPosition-toolSize/2+1,eraserYPosition-toolSize/2+1,toolSize-2,toolSize-2);
		}
		else if(markerOutlineIsVisible)
		{
			if(toolSize>=8) //Makes sure not to draw a circle of negative or zero radius
			{
				//Draws an outline around the circle
				page.setColor(Color.BLACK);
				page.fillOval(markerXPosition-toolSize/2,markerYPosition-toolSize/2,toolSize,toolSize);
				page.setColor(Color.LIGHT_GRAY);
				page.fillOval(markerXPosition-toolSize/2+1,markerYPosition-toolSize/2+1,toolSize-2,toolSize-2);
				
				//Draws the circle (in the proper color)
				page.setColor(color);
				page.fillOval(markerXPosition-toolSize/2+2,markerYPosition-toolSize/2+2,toolSize-4,toolSize-4);
			}
			else if(toolSize>3)
			{
				//Draws an outline around the circle
				page.setColor(Color.GRAY);
				page.fillOval(markerXPosition-toolSize/2,markerYPosition-toolSize/2,toolSize,toolSize);
				
				//Draws the circle (in the proper color)
				page.setColor(color);
				page.fillOval(markerXPosition-toolSize/2+1,markerYPosition-toolSize/2+1,toolSize-2,toolSize-2);
			}
			else
			{
				//Draws an outline around the circle
				page.setColor(Color.GRAY);
				page.fillOval(markerXPosition-toolSize/2,markerYPosition-toolSize/2,toolSize,toolSize);
				
				//Draws the circle (in the proper color)
				page.setColor(color);
				page.drawLine(markerXPosition,markerYPosition,markerXPosition,markerYPosition);
			}
		}
		else if(nathanOutlineIsVisible)
		{
			page.drawImage(geographyWizardImage,nathanXPosition-toolSize,nathanYPosition-toolSize,toolSize*2,toolSize*2,null);
		}
		
	}
	
	//Draws a CircleObject onto the screen at (x,y) with the current toolSize and color
	public void drawPoint(int x,int y)
	{
		//Checks to see if something was just undone, redone, or cleared
		if(undone || redone || wasCleared)
		{
			shapesRemoved.clear(); //Clears the shapesRemoved ArrayList so as not to confuse the program
			actionsTaken.clear(); //Clears the actionsTaken ArrayList so as not to confuse the program
			addAction(); //Adds an action because it just cleared all of the actions, including the current one
			permanentPoints=shapesToDraw.size(); //Tells the undo method how many points are permanently drawn on the screen
			actionsUndone=0; //Sets actionsUndone to zero
			
			//Resets these variables
			undone=false;
			redone=false;
			wasCleared=false;
		}

		//Checks to see if the eraser is being used
		if(tool=="eraser")
		{
			erasePoint(x,y);
		}
		else if(tool=="marker")
		{
			//Drawing Code
			boolean drawn=false;
			for(int i=0;i<shapesToDraw.size();i++)
			{
				if(shapesToDraw.get(i).getX()==x && shapesToDraw.get(i).getY()==y && shapesToDraw.get(i).getSize()==toolSize && shapesToDraw.get(i).getColor()==color)
				{
					drawn=true;
				}
			}
			if(!drawn)
			{
				shapesToDraw.add(new CircleObject(x,y,toolSize,color));
				addToCurrentAction();
				repaint();
			}
		}
		else if(tool=="nathan")
		{
			shapesToDraw.add(new NathanObject(x-toolSize,y-toolSize,toolSize*2));
			addToCurrentAction();
			repaint();
		}
	}
	
	//Erases all CircleObjects that are touching the current tool (touching is defined as the circle projected by the CircleObject being in contact with the circle projected by the tool)
	public void erasePoint(int x,int y)
	{
		for(int i=0;i<shapesToDraw.size();i++)
		{
			if(shapesToDraw.get(i).getClassID()!="NathanObject")
			{
				if(Math.sqrt(Math.pow(shapesToDraw.get(i).getX()-x,2)+Math.pow(shapesToDraw.get(i).getY()-y,2))<(shapesToDraw.get(i).getSize()/2+toolSize/2))
				{
					shapesToDraw.remove(i);
				}
			}
			else
			{
				if(Math.sqrt(Math.pow(shapesToDraw.get(i).getX()+shapesToDraw.get(i).getSize()/2-x,2)+Math.pow(shapesToDraw.get(i).getY()+shapesToDraw.get(i).getSize()/2-y,2))<(shapesToDraw.get(i).getSize()/2+toolSize/2-shapesToDraw.get(i).getSize()/6)) //The shapesToDraw.get(i).getSize()/6 at the end is for blank space in the picture
				{
					shapesToDraw.remove(i);
				}
			}
		}
		permanentPoints=shapesToDraw.size(); //Tells the undo method how many points are permanently drawn on the screen
		repaint();
	}

	//Shows the outline of the marker as a filled circle centered at (x,y)
	public void showMarkerOutline(int x,int y)
	{
		markerOutlineIsVisible=true;
		markerXPosition=x;
		markerYPosition=y;
		repaint();
	}
	
	//Shows the outline of the eraser as an empty circle centered at (x,y)
	public void showEraserOutline(int x,int y)
	{
		eraserOutlineIsVisible=true;
		eraserXPosition=x;
		eraserYPosition=y;
		repaint();
	}
	
	public void showNathanOutline(int x,int y)
	{
		nathanOutlineIsVisible=true;
		nathanXPosition=x;
		nathanYPosition=y;
		repaint();
	}
	
	//Hides all tool outlines
	public void hideToolOutlines()
	{
		markerOutlineIsVisible=false;
		eraserOutlineIsVisible=false;
	}
	
	//Adds an action to the list of actionsTaken
	public void addAction()
	{
		actionsTaken.add(0);
	}
	
	//Adds one to the current action
	public void addToCurrentAction()
	{
		actionsTaken.set(actionsTaken.size()-1,actionsTaken.get(actionsTaken.size()-1)+1);
	}
	
	//Undoes the last action done in the current DrawingComponent object
	public void undo()
	{
		if(wasCleared)
		{
			actionsUndone++;
			if(shapesRemoved.size()>0)
			{
				for(int i=actionsTaken.get(actionsTaken.size()-actionsUndone);i>0;i--)
				{
					shapesToDraw.add(shapesRemoved.get(shapesRemoved.size()-i));
					shapesRemoved.remove(shapesRemoved.size()-i);
				}
			}
			wasCleared=false;
			undone=true;
			repaint();
		}
		else if(shapesToDraw.size()>permanentPoints)
		{
			for(int i=0;i<actionsTaken.get(actionsTaken.size()-1-actionsUndone);i++)
			{
				shapesRemoved.add(shapesToDraw.get(shapesToDraw.size()-1));
				shapesToDraw.remove(shapesToDraw.size()-1);
			}
			actionsUndone++; //Adds one to actionsUndone
			undone=true;
			repaint();
		}
	}
	
	//Redoes the last action undone
	public void redo()
	{
		if(shapesRemoved.size()>0 && !wasCleared)
		{
			for(int i=actionsTaken.get(actionsTaken.size()-actionsUndone);i>0;i--)
			{
				shapesToDraw.add(shapesRemoved.get(shapesRemoved.size()-i));
				shapesRemoved.remove(shapesRemoved.size()-i);
			}
			actionsUndone--; //Subtracts one from actionsUndone
			redone=true;
			repaint();
		}
	}
	
	//Clears  the current DrawingComponent object
	public void clear()
	{
		if(!wasCleared) //A safeguard to make sure the DrawingComponent object is not cleared twice in a row
		{
			shapesRemoved.clear(); //Clears the shapesRemoved ArrayList
			for(int i=1;i<=actionsUndone;i++)
			{
				actionsTaken.remove(actionsTaken.size()-1); //Clears the actionsTaken ArrayList of all actions that were undone before the clear method was called
			}
			addAction(); //Adds an action that records the action taken by this method
			actionsUndone=0; //Set actionsUndone to zero
			for(int i=0;i<shapesToDraw.size();i++)
			{
				shapesRemoved.add(shapesToDraw.get(i));
				addToCurrentAction();
			}
			shapesToDraw.clear();
			wasCleared=true;
		}
	}
	
	public void setColor(Color c)
	{
		color=c;
	}
	public void setTool(String t)
	{
		tool=t;

		//Make sure the eraser outline is not visible for non-eraser tools
		if(tool!="eraser")
		{
			eraserOutlineIsVisible=false;
			repaint();
		}
		//Make sure the marker outline is not visible for non-marker tools
		if(tool!="marker")
		{
			markerOutlineIsVisible=false;
			repaint();
		}
		//Make sure the marker outline is not visible for non-marker tools
		if(tool!="nathan")
		{
			nathanOutlineIsVisible=false;
			repaint();
		}
	}
	public void setToolSize(int s)
	{
		toolSize=s;
		repaint();
	}
	public void setShapesToDraw(ArrayList<ShapeObject> s)
	{
		shapesRemoved.clear(); //Clears the shapesRemoved ArrayList so as not to confuse the program
		actionsTaken.clear(); //Clears the actionsTaken ArrayList so as not to confuse the program
		actionsUndone=0; //Sets actionsUndone to zero
		
		//Resets these variables
		undone=false;
		redone=false;
		wasCleared=false;
		
		shapesToDraw=s; //Sets shapesToDraw equal to the new shapes
		permanentPoints=shapesToDraw.size(); //Tells the undo method how many points are permanently drawn on the screen
		repaint();
	}
	public String getTool()
	{
		return tool;
	}
	public ArrayList<ShapeObject> getShapesToDraw()
	{
		return shapesToDraw;
	}
}