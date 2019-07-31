import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


public class ColorDisplayer extends JComponent
{
	private Color color;
	public ColorDisplayer(Color c)
	{
		color=c;
	}
	public void paintComponent(Graphics page)
	{
		page.setColor(color);
		page.fillRect(0,0,getWidth(),getHeight());
	}
	public void setColor(Color c)
	{
		color=c;
		repaint();
	}
}
