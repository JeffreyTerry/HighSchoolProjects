import java.awt.Color;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
public class FileSaver
{
	private String fileName;
	private ArrayList<ShapeObject> shapeObjects=new ArrayList<ShapeObject>();
	public FileSaver(String fn)
	{
		fileName="./SavedPaintings/"+fn+".sav";
	}
	public boolean save(DrawingComponent d)
	{
		try
		{
			FileOutputStream saveFile=new FileOutputStream(fileName);
			
			ObjectOutputStream save=new ObjectOutputStream(saveFile);
			
			save.writeObject(new Integer(d.getShapesToDraw().size()));
			for(ShapeObject shape: d.getShapesToDraw())
			{
				save.writeObject(shape.getX());
				save.writeObject(shape.getY());
				save.writeObject(shape.getSize());
				save.writeObject(shape.getColor());
				save.writeObject(shape.getClassID());
			}
			
			save.close();
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"Path error!  Check FileSaver.java (Line 12) for folder path","", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean load(DrawingComponent d)
	{
		String currentClassID;
		int currentX;
		int currentY;
		int currentSize;
		Color currentColor;
		try
		{
			FileInputStream pictureFile=new FileInputStream(fileName);
			
			ObjectInputStream load=new ObjectInputStream(pictureFile);
			
			int objectsStored=(Integer)load.readObject();
			
			for(int i=0;i<objectsStored;i++)
			{
				currentX=(Integer)load.readObject();
				currentY=(Integer)load.readObject();
				currentSize=(Integer)load.readObject();
				currentColor=(Color)load.readObject();
				currentClassID=(String)load.readObject();
				if(currentClassID.equals("NathanObject"))
				{
					shapeObjects.add(new NathanObject(currentX,currentY,currentSize));
				}
				else if(currentClassID.equals("CircleObject"))
				{
					shapeObjects.add(new CircleObject(currentX,currentY,currentSize,currentColor));
				}
			}
			d.setShapesToDraw(shapeObjects);
			
			load.close();
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"File Not Found!","", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
}
