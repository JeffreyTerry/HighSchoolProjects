import java.awt.Color;

public class NathanObject extends ShapeObject
{
	private int x,y,size;
	public NathanObject(int x1,int y1,int size1)
	{
		x=x1;
		y=y1;
		size=size1;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getSize()
	{
		return size;
	}
	public Color getColor()
	{
		return Color.BLACK;
	}
	public String getClassID()
	{
		return "NathanObject";
	}
	public String toString()
	{
		return "I am a nathan";
	}
}