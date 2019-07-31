import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Enemy
{
	public static Toolkit tk=Toolkit.getDefaultToolkit();  //This toolkit is used to create images throughout the program
	
	public static Thread raySuperpowerThread;
	
	public static boolean rayIsActive=false;
	
	//These images are only created once. They are static. Each object's enemyImages ArrayList will refer to one of these
	public static ArrayList<Image> enemyJellyBeanImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyStrongJellyBeanImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyZombieImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyNathanImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyRayImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyJellyBeanDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyStrongJellyBeanDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyZombieDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyNathanDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> enemyRayDeathImages=new ArrayList<Image>();

	ArrayList<Image> enemyImages=new ArrayList<Image>();  //The images for the current object

	public int numberOfImages=1;
	public double imageCounter=0;
	public double imageCounterDelay=0.1;

	ArrayList<Image> enemyDeathImages=new ArrayList<Image>();

	public int numberOfDeathImages=1;
	public double deathImageCounter=0;
	public double deathImageCounterDelay=0.1;
	
	public static int JellyBeanWidth=50,JellyBeanHeight=50,StrongJellyBeanWidth=60,StrongJellyBeanHeight=60,ZombieWidth=90,ZombieHeight=90,NathanWidth=80,NathanHeight=80,RayWidth=200,RayHeight=300;
	
	public int width,height;

	public Path path;
	
	public int indexInPath;
	
	public int x,y;

	public int xCenter,yCenter;
	public int xOffset,yOffset;
	
	public String type;
	
	public int health,cashValue;
	
	public boolean dead;
	
	public int futureDamage=0;
	
	public int refreshesPerMovement=1; //Is used to change how fast the enemy moves across the screen
	public int refreshes=1;

	public Enemy(Enemy e)
	{
		type=e.type;
		path=e.path;
		indexInPath=0;
		x=e.x;
		y=e.y;
		numberOfImages=e.numberOfImages;
		imageCounterDelay=e.imageCounterDelay;
		for(Image image: e.enemyImages)
		{
			enemyImages.add(image);
		}
		for(Image image: e.enemyDeathImages)
		{
			enemyDeathImages.add(image);
		}
		refreshesPerMovement=e.refreshesPerMovement;
		health=e.health;
		cashValue=e.cashValue;
		xOffset=e.xOffset;
		yOffset=e.yOffset;
	}
	
	public Enemy(String type,Path p)
	{
		this.type=type;
		path=p;
		indexInPath=0;
		x=path.pointsInPath.get(0).x;
		y=path.pointsInPath.get(0).y;
		
		//These if statements set the values for each enemy type. They are very influential
		if(type.equals("JellyBean"))
		{
			refreshesPerMovement=1;
			health=4000;
			cashValue=4;
			width=JellyBeanWidth;
			height=JellyBeanHeight;
			xOffset=-15;
			yOffset=-10;
			numberOfImages=11;
			imageCounterDelay=0.1;
			numberOfDeathImages=11;
			deathImageCounterDelay=0.1;
			enemyImages=enemyJellyBeanImages;
			enemyDeathImages=enemyJellyBeanDeathImages;
		}
		else if(type.equals("StrongJellyBean"))
		{
			refreshesPerMovement=2;
			health=12000;
			cashValue=8;
			width=StrongJellyBeanWidth;
			height=StrongJellyBeanHeight;
			xOffset=-15;
			yOffset=-10;
			numberOfImages=11;
			imageCounterDelay=0.1;
			numberOfDeathImages=11;
			deathImageCounterDelay=0.1;
			enemyImages=enemyStrongJellyBeanImages;
			enemyDeathImages=enemyStrongJellyBeanDeathImages;
		}
		else if(type.equals("Zombie"))
		{
			refreshesPerMovement=5;
			health=30000;
			cashValue=10;
			width=ZombieWidth;
			height=ZombieHeight;
			xOffset=-15;
			yOffset=-10;
			numberOfImages=11;
			imageCounterDelay=0.08;
			numberOfDeathImages=11;
			deathImageCounterDelay=0.1;
			enemyImages=enemyZombieImages;
			enemyDeathImages=enemyZombieDeathImages;
		}
		else if(type.equals("Nathan"))
		{
			refreshesPerMovement=1;
			health=20000;
			cashValue=10;
			width=NathanWidth;
			height=NathanHeight;
			xOffset=-15;
			yOffset=-10;
			numberOfImages=11;
			imageCounterDelay=0.08;
			numberOfDeathImages=7;
			deathImageCounterDelay=0.08;
			enemyImages=enemyNathanImages;
			enemyDeathImages=enemyNathanDeathImages;
		}
		else if(type.equals("Ray"))
		{
			refreshesPerMovement=6;
			health=1200000;
			cashValue=4000;
			width=RayWidth;
			height=RayHeight;
			xOffset=-15;
			yOffset=-10;
			numberOfImages=8;
			imageCounterDelay=0.06;
			numberOfDeathImages=8;
			deathImageCounterDelay=0.1;
			enemyImages=enemyRayImages;
			enemyDeathImages=enemyRayDeathImages;
			raySuperpowerThread=new Thread(new RaySuperpowerRunner());
		}
		
	}
	
	public void moveAlongPath()
	{
		if(refreshes>=refreshesPerMovement && indexInPath<path.pointsInPath.size())
		{
			x=path.pointsInPath.get(indexInPath).x;
			y=path.pointsInPath.get(indexInPath).y;
			indexInPath++;
			refreshes=1;
		}
		else if(indexInPath<path.pointsInPath.size())
		{
			refreshes++;
		}
		else
		{
			if(TowerDefenseFrame.livesLeft<=1)  //If there is only one life left and this enemy escapes, there will be zero lives left, which should result in a game over
			{
				TowerDefenseFrame.livesLeft--;
				TowerDefenseFrame.livesLabel.setText("Lives: "+TowerDefenseFrame.livesLeft);
				TowerDefenseFrame.lose();
			}
			else
			{
				TowerDefenseFrame.livesLeft--;
				TowerDefenseFrame.livesLabel.setText("Lives: "+TowerDefenseFrame.livesLeft);
				if(type.equals("Ray"))
				{
					boolean otherRayExists=false;
					for(int i=0;i<TowerDefenseFrame.map.enemies.size();i++)
					{
						if(TowerDefenseFrame.map.enemies.get(i).type.equals("Ray") && TowerDefenseFrame.map.enemies.get(i)!=this)
						{
							otherRayExists=true;
						}
					}
					if(!otherRayExists) //If there are no other rays alive, stop his superpower
						stopRaySuperpower();
				}
			}
			try
			{
				TowerDefenseFrame.map.enemies.remove(this);
			}
			catch(ConcurrentModificationException e)
			{
				System.out.println("Caught");
			}
		}
	}
	
	public void takeHealth(int damage)
	{
		health-=damage;
		if(health<=0)
		{
			dead=true;
			refreshesPerMovement+=2;
			TowerDefenseFrame.cashMoneyFlow+=cashValue;
			TowerDefenseFrame.cashLabel.setText("Cash: "+TowerDefenseFrame.cashMoneyFlow);
		}
	}
	
	public void draw(Graphics g)
	{
		if(!dead && imageCounter<numberOfImages)
		{
			g.drawImage(enemyImages.get((int)imageCounter),x-width/2,y-height/2,width,height,null);
			imageCounter+=imageCounterDelay;
		}
		else if(!dead)
		{
			imageCounter=0;
			g.drawImage(enemyImages.get((int)imageCounter),x-width/2,y-height/2,width,height,null);
		}
		if(dead && deathImageCounter<numberOfDeathImages)
		{
			if(type.equals("Ray") && raySuperpowerThread.isAlive())  //This makes sure that if rayStopIt is true when Ray dies, it makes it false
			{
				stopRaySuperpower();  //Stops Ray's superpower
				
				//This sleep is for animation effect
				try
				{
					Thread.sleep(200);
				}
				catch(Exception e){}
			}
			
			g.drawImage(enemyDeathImages.get((int)deathImageCounter),x-width/2,y-height/2,width,height,null);
			deathImageCounter+=deathImageCounterDelay;
		}
		else if(dead)
		{
			TowerDefenseFrame.map.enemies.remove(this);
		}
	}
	
	//Starts Ray's Superpower
	public static void startRaySuperpower()
	{
		rayIsActive=true;
		raySuperpowerThread.start();
	}
	
	//Stops Ray's Superpower
	public static void stopRaySuperpower()
	{
		Enemy.rayIsActive=false;
		
		while(raySuperpowerThread.isAlive())  //Waits until the RaySuperpowerRunner stops executing
		{
			try
			{
				Thread.sleep(1);
			}
			catch(Exception e){}
		}
		TowerDefenseFrame.rayStopIt=false;  //Makes absolutely sure rayStopIt isn't true
	}

	//Should be called once and only once by the map object using it
	public static void createEnemyImages()
	{
		if(enemyNathanImages.size()==0) //This makes sure the method hasn't already been called
		{
			int numberOfImages=11;  //These are local variables, not to be confused with the class variables
			int numberOfDeathImages=11;
			for(int i=1;i<=numberOfImages;i++)
			{
				enemyJellyBeanImages.add(tk.createImage("./Images/DelayedMobs/Mob_JellyBean"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				enemyJellyBeanDeathImages.add(tk.createImage("./Images/DelayedMobs/Mob_JellyBean_Death"+i+".png"));
			}
			for(int i=1;i<=numberOfImages;i++)
			{
				enemyStrongJellyBeanImages.add(tk.createImage("./Images/DelayedMobs/Mob_StrongJellyBean"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				enemyStrongJellyBeanDeathImages.add(tk.createImage("./Images/DelayedMobs/Mob_StrongJellyBean_Death"+i+".png"));
			}
			for(int i=1;i<=numberOfImages;i++)
			{
				enemyZombieImages.add(tk.createImage("./Images/DelayedMobs/Mob_Zombie"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				enemyZombieDeathImages.add(tk.createImage("./Images/DelayedMobs/Mob_Zombie_Death"+i+".png"));
			}
			for(int i=1;i<=numberOfImages;i++)
			{
				enemyNathanImages.add(tk.createImage("./Images/DelayedMobs/Mob_Nathan"+i+".png"));
			}
			numberOfDeathImages=7;
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				enemyNathanDeathImages.add(tk.createImage("./Images/DelayedMobs/Mob_Nathan_Death"+i+".png"));
			}
			numberOfImages=8;
			for(int i=1;i<=numberOfImages;i++)
			{
				enemyRayImages.add(tk.createImage("./Images/DelayedMobs/Mob_Ray"+i+".png"));
			}
			numberOfDeathImages=8;
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				enemyRayDeathImages.add(tk.createImage("./Images/DelayedMobs/Mob_RayDeath"+i+".png"));
			}
		}
	}
	
	//This class runs Ray's superpower
	public class RaySuperpowerRunner implements Runnable
	{
		public int counter=0;
		public final int TIME_TO_HIDE_STOP_IT=5000;
		public final int TIME_TO_SHOW_STOP_IT=6000;
		public void run()
		{
			TowerDefenseFrame.rayStopIt=false;
			while(Enemy.rayIsActive && counter<TIME_TO_HIDE_STOP_IT)
			{
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e){}
				counter+=10;
			}
			counter=0;
			TowerDefenseFrame.rayStopIt=true;
			while(Enemy.rayIsActive && counter<TIME_TO_SHOW_STOP_IT)
			{
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e){}
				counter+=10;
			}
			counter=0;
			if(Enemy.rayIsActive)
				run();
			TowerDefenseFrame.rayStopIt=false;
		}
	}
}
