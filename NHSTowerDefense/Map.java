import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Map extends JComponent
{
	private static Toolkit tk=Toolkit.getDefaultToolkit();  //This toolkit is used to create images throughout the program
	
	public Image mapImage=tk.createImage("./Images/Map_Background.png");  //This image is painted to the screen as the map

	public Image stopItImage=tk.createImage("./Images/StopIt.png");  //This image is painted to the screen when ray uses his superpower
	
	public Image cursorImage=tk.createImage("NonexistentFile.png");  //This image is painted to the screen at the mouse's location. It is invisible when not placing towers
	public Image rangeImage=tk.createImage("./Images/Help_TowerRange.png");  //This image is displayed when a tower is being placed, if the tower is in a placeable location
	public Image rangeRedImage=tk.createImage("./Images/Help_TowerRangeRed.png");  //This image is displayed when a tower is being placed, if the tower is not in a placeable location
	public String cursorTowerType="None";  //This variable stores the type of tower currently being placed

	public int cursorX=0,cursorY=0;  //These variables store the location of the cursor within the map. The TowerDefenseFrame class updates this

	public ArrayList<Tower> towers=new ArrayList<Tower>();  //This ArrayList stores every tower that has been placed

	public ArrayList<Enemy> enemies=new ArrayList<Enemy>();

	public ArrayList<ArrayList<Shot>> allShots=new ArrayList<ArrayList<Shot>>();
	
	public static Path path=new Path(new Point(0,220));  //Maybe don't make this static eventually, but it's a lot of work so not right now

	public static int width=1000,height=500;
	
	//These two variables are used to display the tower when it is selected
	public Tower towerSelected;
	public boolean towerIsSelected=false;

	//These variables are used for the animation of the towerUpgrader buttons
	public int numberOfSleepValues=80;
	public long sleepValues[]=new long[numberOfSleepValues];
	public int towerUpgraderCounter=0;

	public int towerUpgraderWidth=80;
	public int towerUpgraderHeight=80;
	
	public int towerUpgraderOption1Y=70;
	public int towerUpgraderOption2Y=towerUpgraderOption1Y+towerUpgraderHeight+10;
	public int towerUpgraderOption3Y=towerUpgraderOption1Y+towerUpgraderHeight*2+20;
	public int towerUpgraderOption4Y=towerUpgraderOption1Y+towerUpgraderHeight*3+30;
	
	//This variable is used to tell if the paintComponent method is being executed for the first time
	public boolean firstTime=true;
	
	//This variable is used to determine when the map finishes painting everything
	public boolean isPainting=false;
	
	//The constructor creates the images for the enemies and the towers and then creates the path
	public Map()
	{
		Enemy.createEnemyImages();
		Tower.createShotImages();
		createPath();
		
		//This creates an increasing, sinusoidally decelerating array of values
		for(int i=0;i<sleepValues.length;i++)
		{
			sleepValues[i]=(long)(Math.sin((i*(Math.PI/2))/numberOfSleepValues)*2);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		isPainting=true;
		
		//This code stops the animations from blinking the first time they enter
		if(firstTime)
		{
			initializeImages(g);
			firstTime=false;
		}
		
		g.drawImage(mapImage, 0, 0, width, height, null);
		paintTowers(g);
		paintEnemies(g);
		paintShots(g);
		paintCursor(g);
		
		if(towerIsSelected)
		{
			drawSelectedTower(g);
		}
		
		//This code should always be painted last
		if(TowerDefenseFrame.rayStopIt)
		{
			g.drawImage(stopItImage, 0, 0, width, height, null);
		}
		
		isPainting=false;
	}

	//This method draws the current shots and shoots enemies for every tower
	public void paintShots(Graphics g)
	{
		for(int i=0;i<towers.size();i++)
		{
			towers.get(i).drawShots(g);
			
			if(!TowerDefenseFrame.rayStopIt)
			{
				towers.get(i).shootEnemy();
			}
		}
	}
	
	public void paintEnemies(Graphics g)
	{
		for(int i=0;i<enemies.size();i++)
		{
			enemies.get(i).draw(g);
			if(i<enemies.size())
				enemies.get(i).moveAlongPath();
		}
	}

	public void paintTowers(Graphics g)
	{
		for(int i=0;i<towers.size();i++)
		{
			towers.get(i).draw(g);
		}
	}
	
	public void paintCursor(Graphics g)
	{
		if(!cursorTowerType.equals("None"))
		{
			if(!isTowerAtLocation(cursorX,cursorY) && !isPathAtLocation(cursorX,cursorY))
			{
				g.drawImage(cursorImage,cursorX-Tower.width/2,cursorY-Tower.height/2,Tower.width,Tower.height,null);
				g.drawImage(rangeImage,cursorX-Tower.getRange(cursorTowerType),cursorY-Tower.getRange(cursorTowerType),Tower.getRange(cursorTowerType)*2,Tower.getRange(cursorTowerType)*2,null);
			}
			else
			{
				g.drawImage(cursorImage,cursorX-Tower.width/2,cursorY-Tower.height/2,Tower.width,Tower.height,null);
				g.drawImage(rangeRedImage,cursorX-Tower.getRange(cursorTowerType),cursorY-Tower.getRange(cursorTowerType),Tower.getRange(cursorTowerType)*2,Tower.getRange(cursorTowerType)*2,null);
			}
		}
	}
	
	public void drawSelectedTower(Graphics g)
	{
		g.drawImage(towerSelected.towerImage,towerSelected.x-Tower.width/2,towerSelected.y-Tower.height/2,Tower.width,Tower.height,null);
		g.drawImage(rangeImage,towerSelected.x-towerSelected.range,towerSelected.y-towerSelected.range,towerSelected.range*2,towerSelected.range*2,null);
		showTowerUpgradeOptions(g);
	}

	public void showTowerUpgradeOptions(Graphics g)
	{
		if(towerUpgraderCounter<sleepValues.length)
		{
			drawTowerUpgradeOptions(g);
			try
			{
				Thread.sleep(sleepValues[towerUpgraderCounter]);
			}
			catch(Exception e){}
			towerUpgraderCounter++;
		}
		else
		{
			drawTowerUpgradeOptions(g);
		}
	}
	
	public void drawTowerUpgradeOptions(Graphics g)
	{
		if(towerSelected.option1UpgradesLeft>0)
		{
			g.drawImage(Tower.towerUpgradeSpeedButtonImage,Map.width-towerUpgraderCounter,towerUpgraderOption1Y,towerUpgraderWidth,towerUpgraderHeight,null);
		}
		else
		{
			g.drawImage(Tower.noFurtherUpgradesAvailableImage,Map.width-towerUpgraderCounter,towerUpgraderOption1Y,towerUpgraderWidth,towerUpgraderHeight,null);
		}
		if(towerSelected.option2UpgradesLeft>0)
		{
			g.drawImage(Tower.towerUpgradeRangeButtonImage,Map.width-towerUpgraderCounter,towerUpgraderOption2Y,towerUpgraderWidth,towerUpgraderHeight,null);
		}
		else
		{
			g.drawImage(Tower.noFurtherUpgradesAvailableImage,Map.width-towerUpgraderCounter,towerUpgraderOption2Y,towerUpgraderWidth,towerUpgraderHeight,null);
		}
		if(towerSelected.option3UpgradesLeft>0)
		{
			g.drawImage(Tower.towerUpgradePowerButtonImage,Map.width-towerUpgraderCounter,towerUpgraderOption3Y,towerUpgraderWidth,towerUpgraderHeight,null);
		}
		else
		{
			g.drawImage(Tower.noFurtherUpgradesAvailableImage,Map.width-towerUpgraderCounter,towerUpgraderOption3Y,towerUpgraderWidth,towerUpgraderHeight,null);
		}
		if(towerSelected.option4UpgradesLeft>0)
		{
			g.drawImage(Tower.towerUpgradeBallsButtonImage,Map.width-towerUpgraderCounter,towerUpgraderOption4Y,towerUpgraderWidth,towerUpgraderHeight,null);
		}
		else
		{
			g.drawImage(Tower.noFurtherUpgradesAvailableImage,Map.width-towerUpgraderCounter,towerUpgraderOption4Y,towerUpgraderWidth,towerUpgraderHeight,null);
		}
	}
	
	public String getMouseClickingButton(int x,int y)
	{
		if(x>Map.width-towerUpgraderCounter && y>towerUpgraderOption1Y && y<towerUpgraderOption1Y+towerUpgraderHeight) //Checks the option1 button
			return "Option1";
		if(x>Map.width-towerUpgraderCounter && y>towerUpgraderOption2Y && y<towerUpgraderOption2Y+towerUpgraderHeight) //Checks the option2 button
			return "Option2";
		if(x>Map.width-towerUpgraderCounter && y>towerUpgraderOption3Y && y<towerUpgraderOption3Y+towerUpgraderHeight) //Checks the option3 button
			return "Option3";
		if(x>Map.width-towerUpgraderCounter && y>towerUpgraderOption4Y && y<towerUpgraderOption4Y+towerUpgraderHeight) //Checks the option4 button
			return "Option4";
		
		return "None";
	}
	
	public void setCursor(String type)
	{
		cursorImage=tk.createImage("./Images/"+type+".png");
		cursorTowerType=type;
	}

	public void addEnemy(String type)
	{
		enemies.add(new Enemy(type,path));
	}

	public void addTower(int x,int y,String t)
	{
		towers.add(new Tower(x,y,t));  //Adds a new tower to the towers ArrayList
		allShots.add(towers.get(towers.size()-1).shots);  //Adds the shots of the new tower to the ArrayList containing all shots from all towers
	}
	
	public boolean isTowerAtLocation(int x,int y)
	{
		int radius=0;
		int distanceBetween=0;
		for(Tower t: towers)
		{
			radius=Tower.width/2-8;
			distanceBetween=(int)Math.sqrt(Math.pow(Math.abs(t.x-x),2)+Math.pow(Math.abs(t.y-y),2));
			if(distanceBetween<radius*2)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isPathAtLocation(int x,int y)
	{
		int radius=0;
		int distanceBetween=0;
		for(Point p: path.pointsInPath)
		{
			radius=Tower.width/2-8;
			distanceBetween=(int)Math.sqrt(Math.pow(Math.abs(p.x-x),2)+Math.pow(Math.abs(p.y-y),2));
			if(distanceBetween<radius*2)
			{
				return true;
			}
		}
		return false;
	}
	
	public Tower getTowerAtLocation(int x,int y)
	{
		int radius=0;
		int distanceBetween=0;
		for(Tower t: towers)
		{
			radius=Tower.width/2-8;
			distanceBetween=(int)Math.sqrt(Math.pow(Math.abs(t.x-x),2)+Math.pow(Math.abs(t.y-y),2));
			if(distanceBetween<radius)
			{
				return t;
			}
		}
		return null;
	}
	
	//This method is used to create the path that the enemies will follow
	public void createPath()
	{
		path.addSubpath(415,"Right");
		path.addSubpath(150,"Up");
		path.addSubpath(305,"Right");
		path.addSubpath(355,"Down");
		path.addSubpath(120,"Right");
		path.createPointsInPath();
	}
	
	//This method paints every image once so that when they are used for the first time, they don't blink
	public void initializeImages(Graphics g)
	{
		for(Image image: Enemy.enemyJellyBeanImages)
			g.drawImage(image,0,0,Enemy.JellyBeanWidth,Enemy.JellyBeanHeight,null);
		for(Image image: Enemy.enemyJellyBeanDeathImages)
			g.drawImage(image,0,0,Enemy.JellyBeanWidth,Enemy.JellyBeanHeight,null);

		for(Image image: Enemy.enemyStrongJellyBeanImages)
			g.drawImage(image,0,0,Enemy.StrongJellyBeanWidth,Enemy.StrongJellyBeanHeight,null);
		for(Image image: Enemy.enemyStrongJellyBeanDeathImages)
			g.drawImage(image,0,0,Enemy.StrongJellyBeanWidth,Enemy.StrongJellyBeanHeight,null);

		for(Image image: Enemy.enemyZombieImages)
			g.drawImage(image,0,0,Enemy.ZombieWidth,Enemy.ZombieHeight,null);
		for(Image image: Enemy.enemyZombieDeathImages)
			g.drawImage(image,0,0,Enemy.ZombieWidth,Enemy.ZombieHeight,null);

		for(Image image: Enemy.enemyNathanImages)
			g.drawImage(image,0,0,Enemy.NathanWidth,Enemy.NathanHeight,null);
		for(Image image: Enemy.enemyNathanDeathImages)
			g.drawImage(image,0,0,Enemy.NathanWidth,Enemy.NathanHeight,null);

		for(Image image: Enemy.enemyRayImages)
			g.drawImage(image,0,0,Enemy.RayWidth,Enemy.RayHeight,null);
		for(Image image: Enemy.enemyRayDeathImages)
			g.drawImage(image,0,0,Enemy.RayWidth,Enemy.RayHeight,null);
	}
}