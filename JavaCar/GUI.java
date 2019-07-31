import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class GUI extends JFrame
{
	public static boolean mouse=true;
	public static int X;
	public static int Y;
	public static boolean clicking=false;
	private JPanel mousepanel;
	//private JLabel statusbar;
	public GUI()
	{
		super("Car Controller");
		mousepanel=new JPanel();
		add(mousepanel,BorderLayout.CENTER);
		//statusbar=new JLabel("");
		//add(statusbar,BorderLayout.NORTH);
		HandlerClass handler=new HandlerClass();
		//mousepanel.addMouseListener(handler);
		mousepanel.addMouseMotionListener(handler);
	}
	private class HandlerClass implements /*MouseListener,*/MouseMotionListener
	{
		/*public void mouseClicked(MouseEvent event)
		{
			statusbar.setText("The Mouse Is Being Clicked");
		}
		public void mousePressed(MouseEvent event)
		{
			X=event.getX();
			Y=event.getY();
			statusbar.setText("The Mouse Is Being Pressed");
		}
		public void mouseReleased(MouseEvent event)
		{
			statusbar.setText("The Mouse Is Being Released");
		}
		public void mouseEntered(MouseEvent event)
		{
			mouse=true;
			statusbar.setText("The Mouse Is Active");
		}
		public void mouseExited(MouseEvent event)
		{
			mouse=false;
			statusbar.setText("The Mouse Is Inactive");
		}*/
		public void mouseDragged(MouseEvent event)
		{
			X=event.getX();
			Y=event.getY();
			clicking=true;
			//statusbar.setText("The Mouse Is Being Dragged");
		}
		public void mouseMoved(MouseEvent event)
		{
			X=event.getX();
			Y=event.getY();
			clicking=false;
			//statusbar.setText("The Mouse Is Not Being Dragged");
		}
	}
}
