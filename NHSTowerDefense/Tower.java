import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Tower
{
	public static Toolkit tk=Toolkit.getDefaultToolkit();  //This toolkit is used to create images throughout the program
	
	public Image towerImage;  //This is where the tower image is stored for each Tower object

	//These images are only created once. They are static. Each object's shotImages ArrayList will refer to one of these
	public static ArrayList<Image> shotDestroyerImages=new ArrayList<Image>();
	public static ArrayList<Image> shotGayRookImages=new ArrayList<Image>();
	public static ArrayList<Image> shotNathanTowerImages=new ArrayList<Image>();
	public static ArrayList<Image> shotLightningImages=new ArrayList<Image>();
	public static ArrayList<Image> shotDoughnutImages=new ArrayList<Image>();
	public static ArrayList<Image> shotCustomTowerImages=new ArrayList<Image>();
	public static ArrayList<Image> shotDestroyerDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> shotGayRookDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> shotNathanTowerDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> shotLightningDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> shotDoughnutDeathImages=new ArrayList<Image>();
	public static ArrayList<Image> shotCustomTowerDeathImages=new ArrayList<Image>();

	//These are the towerUpgradeButtonImages
	public static Image towerUpgradeSpeedButtonImage=tk.createImage("./Images/Icon_Destroyer.png");
	public static Image towerUpgradeRangeButtonImage=tk.createImage("./Images/Icon_GayRook.png");
	public static Image towerUpgradePowerButtonImage=tk.createImage("./Images/Icon_NathanTower.png");
	public static Image towerUpgradeBallsButtonImage=tk.createImage("./Images/Icon_Lightning.png");
	public static Image noFurtherUpgradesAvailableImage=tk.createImage("./Images/StopIt.png");
	
	//These are the variables in which the shot images and their necessary helper variables are stored for each Tower object
	ArrayList<Image> shotImages=new ArrayList<Image>();
	public int numberOfImages=1;
	public long imageCounterDelay=40;
	ArrayList<Image> shotDeathImages=new ArrayList<Image>();
	public int numberOfDeathImages=1;
	public long deathImageCounterDelay=40;
	
	public int enemyIndexInPath;  //This is used across methods to ensure the enemy's index doesn't change between different methods
	
	public static int width=60,height=60;  //Each tower is this width and height. These have to be static due to the way they are used in the isTowerAtLocation method in the Map class

	//These are the main property declarations for the towers, aside from those in the constructor. They are static
	public static int DestroyerCost=1000,GayRookCost=200,NathanTowerCost=1000,LightningCost=1000,HelpImTrappedInADoughnutCost=750;
	public static int DestroyerRange=150,GayRookRange=200,NathanTowerRange=200,LightningRange=500,HelpImTrappedInADoughnutRange=100;
	public static int DestroyerPower=5000,GayRookPower=300,NathanTowerPower=2000,LightningPower=100,HelpImTrappedInADoughnutPower=20000;
	
	//These are the properties of the customTower
	public static int CustomTowerCost=1000,CustomTowerRange=150,CustomTowerPower=1000;
	public static String CustomTowerShotType="Pellet";
	
	//These variables record how many times a tower can upgrade
	public int option1UpgradesLeft;
	public int option2UpgradesLeft;
	public int option3UpgradesLeft;
	public int option4UpgradesLeft;
	
	//These are the attributes of every Tower object. They are unique to each object and are not static
	public int towerPower,range,cost;
	public int x,y;
	public int shooterX,shooterY;
	public int projectileWidth,projectileDeathWidth;
	public int refreshesPerShot; //Is used to change how fast the tower shoots
	public int refreshes;
	public int pixelsPerRefresh; //Is used to change how fast the bullets shoot
	public String shotType;
	public String type;
	
	public ArrayList<Shot> shots=new ArrayList<Shot>();  //This stores all of the shots taken by each tower. Making this static messes up errthing, so don't do that
	
	public Tower(int x,int y,String type)  //Constructor
	{
		this.x=x;
		this.y=y;
		this.type=type;
		
		towerImage=tk.createImage("./Images/"+type+".png");  //This creates the tower image for whatever type of tower is created

		//These if statements set the values for each tower type. They are very influential
		if(type.equals("Tower_Destroyer"))
		{
			refreshesPerShot=40;
			pixelsPerRefresh=2;
			shotType="Pellet";
			projectileWidth=30;
			projectileDeathWidth=50;
			towerPower=DestroyerPower;
			range=DestroyerRange;
			cost=DestroyerCost;

			option1UpgradesLeft=1;
			option2UpgradesLeft=1;
			option3UpgradesLeft=1;
			option4UpgradesLeft=1;
			
			shooterX=x-15;
			shooterY=y-35;
			
			numberOfImages=7;
			imageCounterDelay=20;
			numberOfDeathImages=1;
			deathImageCounterDelay=40;
			shotImages=shotDestroyerImages;
			shotDeathImages=shotDestroyerDeathImages;
		}
		else if(type.equals("Tower_GayRook"))
		{
			refreshesPerShot=10;
			pixelsPerRefresh=2;
			shotType="Pellet";
			projectileWidth=30;
			projectileDeathWidth=30;
			towerPower=GayRookPower;
			range=GayRookRange;
			cost=GayRookCost;

			option1UpgradesLeft=1;
			option2UpgradesLeft=1;
			option3UpgradesLeft=1;
			option4UpgradesLeft=1;
			
			shooterX=x-15;
			shooterY=y-35;
			
			numberOfImages=1;  //Not really one image but it didn't work with three images for some reason #TODO YOLO
			imageCounterDelay=40;
			numberOfDeathImages=1;
			deathImageCounterDelay=40;
			shotImages=shotGayRookImages;
			shotDeathImages=shotGayRookDeathImages;
		}
		else if(type.equals("Tower_NathanTower"))
		{
			refreshesPerShot=20;
			pixelsPerRefresh=2;
			shotType="Pellet";
			projectileWidth=30;
			projectileDeathWidth=30;
			towerPower=NathanTowerPower;
			range=NathanTowerRange;
			cost=NathanTowerCost;

			option1UpgradesLeft=1;
			option2UpgradesLeft=1;
			option3UpgradesLeft=1;
			option4UpgradesLeft=1;
			
			shooterX=x-15;
			shooterY=y-25;
			
			numberOfImages=4;
			imageCounterDelay=100;
			numberOfDeathImages=4;
			deathImageCounterDelay=40;
			shotImages=shotNathanTowerImages;
			shotDeathImages=shotNathanTowerDeathImages;
		}
		else if(type.equals("Tower_Lightning"))
		{
			refreshesPerShot=2;
			pixelsPerRefresh=10;
			shotType="Laser";
			projectileWidth=30;
			projectileDeathWidth=30;
			towerPower=LightningPower;
			range=LightningRange;
			cost=LightningCost;

			option1UpgradesLeft=1;
			option2UpgradesLeft=1;
			option3UpgradesLeft=1;
			option4UpgradesLeft=1;
			
			shooterX=x-15;
			shooterY=y-35;
			
			numberOfImages=1;
			imageCounterDelay=40;
			numberOfDeathImages=1;
			deathImageCounterDelay=40;
			shotImages=shotLightningImages;
			shotDeathImages=shotLightningDeathImages;
		}
		else if(type.equals("Tower_HelpImTrappedInADoughnut"))
		{
			refreshesPerShot=150;
			pixelsPerRefresh=2;
			shotType="Pellet";
			projectileWidth=30;
			projectileDeathWidth=30;
			towerPower=HelpImTrappedInADoughnutPower;
			range=HelpImTrappedInADoughnutRange;
			cost=HelpImTrappedInADoughnutCost;

			option1UpgradesLeft=1;
			option2UpgradesLeft=1;
			option3UpgradesLeft=1;
			option4UpgradesLeft=1;
			
			shooterX=x-15;
			shooterY=y-25;
			
			numberOfImages=3;
			imageCounterDelay=40;
			numberOfDeathImages=1;
			deathImageCounterDelay=40;
			shotImages=shotDoughnutImages;
			shotDeathImages=shotDoughnutDeathImages;
		}
		else if(type.equals("Tower_CustomTower"))
		{
			refreshesPerShot=10;
			pixelsPerRefresh=4;
			shotType=CustomTowerShotType;
			projectileWidth=30;
			projectileDeathWidth=30;
			towerPower=CustomTowerPower;
			range=CustomTowerRange;
			cost=CustomTowerCost;

			option1UpgradesLeft=1;
			option2UpgradesLeft=1;
			option3UpgradesLeft=1;
			option4UpgradesLeft=1;
			
			shooterX=x-15;
			shooterY=y-25;
			
			numberOfImages=1;
			imageCounterDelay=40;
			numberOfDeathImages=1;
			deathImageCounterDelay=40;
			shotImages=shotCustomTowerImages;
			shotDeathImages=shotCustomTowerDeathImages;
		}
		else  //If the tower type isn't recognized, do this
		{
			JOptionPane.showMessageDialog(null,"This tower type is not recognized in Tower.java","Constructor Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void draw(Graphics g)  //This method draws the tower, shoots at the enemies, then draws all active shots
	{
		g.drawImage(towerImage,x-width/2,y-height/2,width,height,null);
	}

	public void drawShots(Graphics g)  //This method draws all active shots.
	{
		for(int i=0;i<shots.size();i++)  //This runs through every index in "shots"
		{
			if(shotType.equals("Pellet"))  //If the tower shoots pellet style
			{
				g.drawImage(shots.get(i).getCurrentImage(),shots.get(i).towerPoint.x+shots.get(i).xDistance*shots.get(i).counter/shots.get(i).distance,shots.get(i).towerPoint.y+shots.get(i).yDistance*shots.get(i).counter/shots.get(i).distance,shots.get(i).tower.projectileWidth,shots.get(i).tower.projectileWidth,null); //towerX+xDistance*counter/distance,towerY+yDistance*counter/distance,projectileWidth,projectileWidth
			}
			else if(shotType.equals("Laser"))  //If the tower shoots laser style
			{
				int j;
				for(j=0;j<=shots.get(i).distance;j++)
				{
					g.drawImage(shots.get(i).getCurrentImage(),shots.get(i).towerPoint.x+shots.get(i).xDistance*j/shots.get(i).distance,shots.get(i).towerPoint.y+shots.get(i).yDistance*j/shots.get(i).distance,shots.get(i).tower.projectileWidth,shots.get(i).tower.projectileWidth,null); //towerX+xDistance*j/distance,towerY+yDistance*j/distance,projectileWidth,projectileWidth
				}
				shots.get(i).counter=j;
			}
			if(shots.get(i).counter>=shots.get(i).distance)  //If the shot has reached its destination, this draws the death image, hurts the enemy, then removes the shot
			{
				g.drawImage(shots.get(i).getCurrentDeathImage(),shots.get(i).towerPoint.x+shots.get(i).xDistance*shots.get(i).counter/shots.get(i).distance,shots.get(i).towerPoint.y+shots.get(i).yDistance*shots.get(i).counter/shots.get(i).distance,shots.get(i).tower.projectileWidth,shots.get(i).tower.projectileWidth,null); //towerX+xDistance*counter/distance,towerY+yDistance*counter/distance,projectileDeathWidth,projectileDeathWidth
				shots.get(i).enemy.takeHealth(towerPower);  //Takes health from the enemy that was hit
				shots.remove(i);  //Removes the shot
			}
			else
			{
				shots.get(i).counter+=pixelsPerRefresh;  //Adds the number of pixels to jump the next time the projectile is drawn to the counter of the current Shot
			}
		}
	}
	
	public void shootEnemy()  //Creates a shot from the current tower to the enemy closest to the exit
	{
		if(refreshes>=refreshesPerShot)
		{
			Enemy nearestEnemy=getEnemyClosestToExit();
			if(nearestEnemy!=null)
			{
				if(shotType.equals("Laser")) //nearestEnemy.indexInPath<Map.path.pointsInPath.size()
				{
					if(nearestEnemy.indexInPath<Map.path.pointsInPath.size())
						shots.add(new Shot(new Point(Map.path.pointsInPath.get(nearestEnemy.indexInPath).x+nearestEnemy.xOffset,Map.path.pointsInPath.get(nearestEnemy.indexInPath).y+nearestEnemy.yOffset),new Point(shooterX,shooterY),nearestEnemy,this)); //new Point(x+xOffset,y+yOffset)
				}
				else if(shotType.equals("Pellet")) //nearestEnemy.indexInPath<Map.path.pointsInPath.size()
				{
					//This code refines the prediction made in the getEnemyClosestToExit method
					int pixelsBetween=(int)Math.sqrt(Math.pow(shooterX-nearestEnemy.x,2)+Math.pow(shooterY-nearestEnemy.y,2));
					int addIndexes=pixelsBetween/(nearestEnemy.refreshesPerMovement*pixelsPerRefresh);
					
					if(enemyIndexInPath+addIndexes<Map.path.pointsInPath.size()-1)
					{
						//This adds a shot
						shots.add(new Shot(new Point(Map.path.pointsInPath.get(enemyIndexInPath+addIndexes).x+nearestEnemy.xOffset,Map.path.pointsInPath.get(enemyIndexInPath+addIndexes).y+nearestEnemy.yOffset),new Point(shooterX,shooterY),nearestEnemy,this)); //new Point(x+xOffset,y+yOffset)
					}
				}
			}
			refreshes=0;
		}
		else
		{
			refreshes++;
		}
	}
	
	public Enemy getEnemyClosestToExit()
	{
		if(TowerDefenseFrame.map.enemies.size()==0) //Makes sure there are enemies to shoot
			return null;
		
		int distance=0;
		int indexInPath=-1;
		int indexOfEnemy=-1;
		
		for(int i=0;i<TowerDefenseFrame.map.enemies.size();i++)
		{
			distance=(int)Math.sqrt(Math.pow(x-TowerDefenseFrame.map.enemies.get(i).x,2)+Math.pow(y-TowerDefenseFrame.map.enemies.get(i).y,2));

			if(shotType.equals("Pellet"))  //If the current tower shoots pellet style
			{
				//This code predicts where the enemy will be when it is shot
				int pixelsBetween=(int)Math.sqrt(Math.pow(shooterX-TowerDefenseFrame.map.enemies.get(i).x,2)+Math.pow(shooterY-TowerDefenseFrame.map.enemies.get(i).y,2));  //Uses the pythagorean theorem to calculate the pixels between the tower and the enemy
				int addIndexes=pixelsBetween/(TowerDefenseFrame.map.enemies.get(i).refreshesPerMovement*pixelsPerRefresh);  //The number of indexes to add is simply x1=x2*(v1/v2)
				
				int indexInPathOfEnemy=TowerDefenseFrame.map.enemies.get(i).indexInPath;  //Records the index of the enemy in the path at this moment, to be used later
				
				if(distance<=range && indexInPathOfEnemy>indexInPath && indexInPathOfEnemy+addIndexes<Map.path.pointsInPath.size()-1 && !Shot.isEnemyGoingToDie(TowerDefenseFrame.map.enemies.get(i),TowerDefenseFrame.map.allShots))  //If the current enemy is within range, closer to the exit than the other enemies checked so far, able to be shot before it escapes, and not about to die due to previous shots, respectively
				{
					indexInPath=indexInPathOfEnemy;  //The index of the enemy closest to the end of the path. This variable is local to this method
					indexOfEnemy=i;  //The index of the current enemy in the TowerDefenseFrame.map.enemies ArrayList
					enemyIndexInPath=indexInPathOfEnemy;  //The index of the current enemy in the path. This variable is used by other methods in the class
				}
			}
			else if(shotType.equals("Laser"))  //If the current tower shoots laser style
			{
				if(distance<=range && TowerDefenseFrame.map.enemies.get(i).indexInPath>indexInPath && !Shot.isEnemyGoingToDie(TowerDefenseFrame.map.enemies.get(i),TowerDefenseFrame.map.allShots))  //If the  enemy is within range and closer to the exit than the other enemies checked so far
				{
					indexInPath=TowerDefenseFrame.map.enemies.get(i).indexInPath;  //The index of the enemy closest to the end of the path. This variable is local to this method
					indexOfEnemy=i;  //The index of the current enemy in the TowerDefenseFrame.map.enemies ArrayList
				}
			}
		}
		if(indexInPath!=-1)  //If an enemy was found within range of the tower, return that enemy, otherwise return null
			return TowerDefenseFrame.map.enemies.get(indexOfEnemy);
		else
			return null;
	}
	
	@Deprecated  //This is an old method that could be fixed to incorporate the improvements made in the getEnemyClosestToExit method, but as it is not currently used, I haven't taken the time to fix it
	public Enemy getNearestEnemy()
	{
		if(TowerDefenseFrame.map.enemies.size()==0) //Makes sure there are enemies to shoot
			return null;
		
		int closestDistance=100000; //This is set to an enormous value to ensure the closest enemy will be closer than this
		int distance=0;
		int index=0;
		
		for(int i=0;i<TowerDefenseFrame.map.enemies.size();i++)
		{
			distance=(int)Math.min(closestDistance,Math.sqrt(Math.pow(x-TowerDefenseFrame.map.enemies.get(i).x,2)+Math.pow(y-TowerDefenseFrame.map.enemies.get(i).y,2)));  //Finds the smaller of the two distances
			
			if(distance<closestDistance)
			{
				closestDistance=distance;
				index=i;
			}
		}
		if(closestDistance<=range)  //If there is a tower within the current tower's range
			return TowerDefenseFrame.map.enemies.get(index);
		else
			return null;
	}
	
	//This function upgrades the tower
	public void upgrade(String upgradeOption)
	{
		if(upgradeOption.equals("Option1"))
		{
			option1UpgradesLeft--;
		}
		if(upgradeOption.equals("Option2"))
		{
			option2UpgradesLeft--;
		}
		if(upgradeOption.equals("Option3"))
		{
			option3UpgradesLeft--;
		}
		if(upgradeOption.equals("Option4"))
		{
			option4UpgradesLeft--;
		}
	}
	
	public static int getCost(String type)  //This is a static method that returns the cost of the tower type entered as the parameter
	{
		if(type.equals("Tower_Destroyer"))
		{
			return DestroyerCost;
		}
		else if(type.equals("Tower_GayRook"))
		{
			return GayRookCost;
		}
		else if(type.equals("Tower_NathanTower"))
		{
			return NathanTowerCost;
		}
		else if(type.equals("Tower_Lightning"))
		{
			return LightningCost;
		}
		else if(type.equals("Tower_HelpImTrappedInADoughnut"))
		{
			return HelpImTrappedInADoughnutCost;
		}
		else if(type.equals("Tower_CustomTower"))
		{
			return CustomTowerCost;
		}
		else
		{
			return 1000000;
		}
	}
	
	public static int getRange(String type)  //This is a static method that returns the range of the tower type entered as the parameter
	{
		if(type.equals("Tower_Destroyer"))
		{
			return DestroyerRange;
		}
		else if(type.equals("Tower_GayRook"))
		{
			return GayRookRange;
		}
		else if(type.equals("Tower_NathanTower"))
		{
			return NathanTowerRange;
		}
		else if(type.equals("Tower_Lightning"))
		{
			return LightningRange;
		}
		else if(type.equals("Tower_HelpImTrappedInADoughnut"))
		{
			return HelpImTrappedInADoughnutRange;
		}
		else if(type.equals("Tower_CustomTower"))
		{
			return CustomTowerRange;
		}
		else
		{
			return 0;  //If the tower type isn't recognized, the method returns 0
		}
	}

	//Should be called once and only once by the map object using it
	public static void createShotImages()
	{
		if(shotDestroyerImages.size()==0) //This makes sure the method hasn't already been called
		{
			int numberOfImages=7;  //These are local variables, not to be confused with the class variables
			int numberOfDeathImages=0;
			for(int i=1;i<=numberOfImages;i++)
			{
				shotDestroyerImages.add(tk.createImage("./Images/DelayedMobs/Projectile_Destroyer"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				shotDestroyerDeathImages.add(tk.createImage("./Images/DelayedMobs/Projectile_Destroyer_Death"+i+".png"));
			}
			numberOfImages=1;
			for(int i=1;i<=numberOfImages;i++)
			{
				shotGayRookImages.add(tk.createImage("./Images/DelayedMobs/Projectile_GayRook"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				shotGayRookDeathImages.add(tk.createImage("./Images/DelayedMobs/Projectile_GayRook_Death"+i+".png"));
			}
			numberOfImages=7;
			for(int i=1;i<=numberOfImages;i++)
			{
				shotNathanTowerImages.add(tk.createImage("./Images/DelayedMobs/Projectile_NathanTower"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				shotNathanTowerDeathImages.add(tk.createImage("./Images/DelayedMobs/Projectile_NathanTower_Death"+i+".png"));
			}
			numberOfImages=1;
			for(int i=1;i<=numberOfImages;i++)
			{
				shotLightningImages.add(tk.createImage("./Images/DelayedMobs/Projectile_Lightning"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				shotLightningDeathImages.add(tk.createImage("./Images/DelayedMobs/Projectile_Lightning_Death"+i+".png"));
			}
			numberOfImages=3;
			for(int i=1;i<=numberOfImages;i++)
			{
				shotDoughnutImages.add(tk.createImage("./Images/DelayedMobs/Projectile_Doughnut"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				shotDoughnutDeathImages.add(tk.createImage("./Images/DelayedMobs/Projectile_Doughnut_Death"+i+".png"));
			}
			numberOfImages=1;
			for(int i=1;i<=numberOfImages;i++)
			{
				shotCustomTowerImages.add(tk.createImage("./Images/DelayedMobs/Projectile_CustomTower"+i+".png"));
			}
			for(int i=1;i<=numberOfDeathImages;i++)
			{
				shotCustomTowerDeathImages.add(tk.createImage("./Images/DelayedMobs/Projectile_CustomTower_Death"+i+".png"));  //TODO create projectiles for this
			}
		}
	}
	
}
