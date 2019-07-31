import java.awt.*;
import javax.swing.JComponent;

public class MenuDisplayArea extends JComponent
{
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	public static Image winImage=tk.createImage("./Images/Map_Win.png");
	public static Image loseImage=tk.createImage("./Images/Map_Lose.gif");
	
	public static int width=1000,height=500;
	
	public MenuDisplayArea()
	{
	}
	
	public void paintComponent(Graphics g)
	{
		if(TowerDefenseFrame.won)
			g.drawImage(winImage,0,0,width,height,null);
		else
			g.drawImage(loseImage,0,0,width,height,null);
	}
	
}