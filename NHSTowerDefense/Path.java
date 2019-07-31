import java.awt.*;
import java.util.ArrayList;

public class Path
{
	public ArrayList<Subpath> subpaths=new ArrayList<Subpath>();
	
	public Point startLocation;
	
	public ArrayList<Point> pointsInPath=new ArrayList<Point>();
	
	public Path()
	{
		startLocation=new Point(0,0);
	}
	
	public Path(Point p)
	{
		startLocation=p;
	}
	
	public void addSubpath(int dis,String dir)
	{
		subpaths.add(new Subpath(dis,dir));
	}
	
	public void createPointsInPath()
	{
		pointsInPath.clear();
		Point currentEndpoint=startLocation;
		for(Subpath subpath: subpaths)
		{
			if(subpath.direction.equals("Right"))
			{
				for(int i=1;i<=subpath.distance;i++)
				{
					pointsInPath.add(new Point(currentEndpoint.x+i,currentEndpoint.y));
				}
			}
			else if(subpath.direction.equals("Left"))
			{
				for(int i=1;i<=subpath.distance;i++)
				{
					pointsInPath.add(new Point(currentEndpoint.x-i,currentEndpoint.y));
				}
			}
			else if(subpath.direction.equals("Up"))
			{
				for(int i=1;i<=subpath.distance;i++)
				{
					pointsInPath.add(new Point(currentEndpoint.x,currentEndpoint.y-i));
				}
			}
			else if(subpath.direction.equals("Down"))
			{
				for(int i=1;i<=subpath.distance;i++)
				{
					pointsInPath.add(new Point(currentEndpoint.x,currentEndpoint.y+i));
				}
			}
			currentEndpoint=pointsInPath.get(pointsInPath.size()-1);
		}
	}
	
	private class Subpath
	{
		public int distance;
		public String direction; //Direction can be "Right" "Left" "Up" or "Down"
		
		public Subpath(int dis,String dir)
		{
			distance=dis;
			direction=dir;
		}
	}
}