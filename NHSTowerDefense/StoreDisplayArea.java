import java.awt.*;

import javax.swing.JComponent;

public class StoreDisplayArea extends JComponent
{
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	public static Image backgroundImage=tk.createImage("./Images/Store_Frame.png");
	
	public static int width=1000,height=632;
	
	public StoreDisplayArea()
	{
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(backgroundImage,0,0,width,height,null);
	}
	
}