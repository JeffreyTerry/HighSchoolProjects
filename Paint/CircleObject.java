import java.awt.Color;

public class CircleObject extends ShapeObject
{
	private int x,y,size;
	private Color color;
	public CircleObject(int x1,int y1,int size1, Color color1)
	{
		x=x1;
		y=y1;
		size=size1;
		color=color1;
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
		return color;
	}
	public String getClassID()
	{
		return "CircleObject";
	}
}