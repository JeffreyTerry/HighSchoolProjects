import java.awt.*;
import javax.swing.*;
public class UFO extends JComponent
{
	private int x,y;
	private int size;
	private int shotheight=0;
	private boolean shooting=false;
	private Color WindowColor=Color.WHITE;
	private Color BodyColor=Color.WHITE;
	public UFO()
	{
		x=0;
		y=0;
		size=10;
	}
	public UFO(int s)
	{
		x=0;
		y=0;
		size=s;
	}
	public UFO(int xcorner,int ycorner)
	{
		x=xcorner;
		y=ycorner;
		size=10;
	}
	public UFO(int xcorner,int ycorner,int s)
	{
		x=xcorner;
		y=ycorner;
		size=s;
	}
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D) g;
		int[] WindowX={x+1*size,x+2*size,x+4*size,x+5*size};
		int[] WindowY={y+1*size,y,y,y+1*size};
		Polygon Windows=new Polygon(WindowX,WindowY,WindowX.length);
		Rectangle Body=new Rectangle(x,y+size*1,size*6,size*1); //x,y,width,height
		Rectangle Laser=new Rectangle(x+(int)(size*2.5),y+size*2,size,0);
		g2.setColor(WindowColor);
		g2.fill(Windows);
		g2.setColor(BodyColor);
		g2.fill(Body);
		g2.setColor(Color.BLACK);
		g2.draw(Windows);
		g2.draw(Body);
		if(shooting)
		{
			if(shotheight>=0)
			{
				g2.setColor(Color.RED);
				Laser=new Rectangle(x+(int)(size*2.5),y+size*2,size,(int)(2*shotheight));
				g2.fill(Laser);
				g2.draw(Laser);
			}
			else if(-(int)(2*shotheight)>size*2)
			{
				g2.setColor(Color.ORANGE);
				Laser=new Rectangle(x+(int)(size*2.5),y+size*2+(int)(2*shotheight),size,-(int)(2*shotheight)-size*2);
				g2.fill(Laser);
				g2.draw(Laser);
			}
		}
	}
	public void paintCar(Color c)
	{
		WindowColor=c;
		BodyColor=c;
	}
	public void paintWindow(Color c)
	{
		WindowColor=c;
	}
	public void paintBody(Color c)
	{
		BodyColor=c;
	}
	public void moveUFO(int speed,int distance) //Speeds should be low - possibly single digit - values
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
	public void shoot()
	{
		shoot(150);
	}
	public void shoot(int sh)
	{
		shooting=true;
		shotheight=sh;
		repaint();
	}
	public void stopShooting()
	{
		shooting=false;
		repaint();
	}
	public void control()
	{
		int previousx=x;
		while(GUI.mouse)
		{
			if(GUI.X-size*3>=10 && GUI.X-size*3<=756)
				x=GUI.X-size*3;
			else if(GUI.X-size*3<10)
				x=10;
			else if(GUI.X-size*3>756)
				x=756;
			if(x!=previousx)
			{
				repaint();
				previousx=x;
			}
			if(GUI.Y<=100 && shooting)
				stopShooting();
			else if(GUI.Y>100 && !shooting)
				shoot();
		}
	}
	public void attack(Car obj)
	{
		int previousx=x;
		boolean magnet=false;
		Color temp;
		while(GUI.mouse)
		{
			if(GUI.X-size*3>=10 && GUI.X-size*3<=746)
				x=GUI.X-size*3;
			else if(GUI.X-size*3<10)
				x=10;
			else if(GUI.X-size*3>756)
				x=756;
			if(x!=previousx)
			{
				repaint();
				previousx=x;
			}
			if(!GUI.clicking)
			{
				magnet=false;
				stopShooting();
			}
			else
				shoot(GUI.Y-12);
			if(shooting && x+(int)(size*3.5)-obj.getx()>0 && x+(int)(size*2.5)-(obj.getx()+obj.getsize()*6)<0 && y+size*2+(shotheight)*2>=obj.gety() || magnet)
			{
				magnet=true;
				temp=obj.getWindowColor();
				obj.paintWindow(obj.getBodyColor());
				obj.paintBody(temp);
				obj.changex(x+(int)(size*2.5)-obj.getsize()*2);
				obj.changey((y+size*2)+shotheight*2);
				obj.repaint();
			}
		}
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
}