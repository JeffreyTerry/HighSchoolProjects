import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
public class Car extends JComponent
{
	private int x,y;
	private int size;
	public boolean shot=false;
	private Color WindowColor=Color.WHITE;
	private Color BodyColor=Color.WHITE;
	private Color WheelColor=Color.WHITE;
	public Car()
	{
		x=0;
		y=0;
		size=10;
	}
	public Car(int s)
	{
		x=0;
		y=0;
		size=s;
	}
	public Car(int xcorner,int ycorner)
	{
		x=xcorner;
		y=ycorner;
		size=10;
	}
	public Car(int xcorner,int ycorner,int s)
	{
		x=xcorner;
		y=ycorner;
		size=s;
	}
	public void paintComponent(Graphics g)
	{
		if(!shot)
		{
			Graphics2D g2=(Graphics2D) g;
			int[] WindowX={x+1*size,x+2*size,x+4*size,x+5*size};
			int[] WindowY={y+1*size,y,y,y+1*size};
			Polygon Windows=new Polygon(WindowX,WindowY,WindowX.length);
			Rectangle Body=new Rectangle(x,y+size*1,size*6,size*1);
			Ellipse2D.Double BackWheel=new Ellipse2D.Double(x+1*size,y+2*size,size,size);
			Ellipse2D.Double FrontWheel=new Ellipse2D.Double(x+4*size,y+2*size,size,size);
			g2.setColor(WindowColor);
			g2.fill(Windows);
			g2.setColor(BodyColor);
			g2.fill(Body);
			g2.setColor(WheelColor);
			g2.fill(BackWheel);
			g2.fill(FrontWheel);
			g2.setColor(Color.BLACK);
			g2.draw(Windows);
			g2.draw(Body);
			g2.draw(BackWheel);
			g2.draw(FrontWheel);
		}
	}
	public void paintCar(Color c)
	{
		WindowColor=c;
		BodyColor=c;
		WheelColor=c;
	}
	public void paintWindow(Color c)
	{
		WindowColor=c;
	}
	public void paintBody(Color c)
	{
		BodyColor=c;
	}
	public void paintWheels(Color c)
	{
		WheelColor=c;
	}
	public void moveCar(int speed,int distance) //Speeds should be low - possibly single digit - values
	{
		int makemove=3200000;
		int xfinal=x+distance;
		if(x<xfinal)
		{
			for(int i=0;x<xfinal;i+=speed)
			{
				if(i>=makemove)
				{
					x+=1;
					repaint();
					makemove+=3200000;
				}
			}
		}
		else if(x>xfinal)
		{
			for(int i=0;x>xfinal;i+=speed)
			{
				if(i>=makemove)
				{
					x-=1;
					repaint();
					makemove+=3200000;
				}
			}
		}
		System.out.println("xfinal: "+xfinal);
		System.out.println("x: "+x);
	}
	public void control()
	{
		int previousx=x;
		while(GUI.mouse)
		{
			if(GUI.X-size*3>=10 && GUI.X-size*3<=516)
				x=GUI.X-size*3;
			else if(GUI.X-size*3<10)
				x=10;
			else if(GUI.X-size*3>516)
				x=516;
			if(x!=previousx)
			{
				repaint();
				previousx=x;
			}
		}
	}
	public void changex(int newx)
	{
		x=newx;
	}
	public void changey(int newy)
	{
		y=newy;
	}
	public int getx()
	{
		return x;
	}
	public int gety()
	{
		return y;
	}
	public int getsize()
	{
		return size;
	}
	public Color getWindowColor()
	{
		return WindowColor;
	}
	public Color getBodyColor()
	{
		return BodyColor;
	}
	public Color getWheelColor()
	{
		return WheelColor;
	}
}