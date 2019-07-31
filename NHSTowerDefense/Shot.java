import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

public class Shot
{
	//These ArrayLists hold the shotImageUpdater and shotDeathImageUpdater objects for each shot
	public ShotImageUpdater shotImageUpdater=new ShotImageUpdater();
	public ShotDeathImageUpdater shotDeathImageUpdater=new ShotDeathImageUpdater();
	
	//These ArrayLists hold the threads used to run the shotImageUpdater and shotDeathImageUpdater objects for each shot
	public Thread shotImageThread=new Thread(shotImageUpdater);
	public Thread shotDeathImageThread=new Thread(shotDeathImageUpdater);

	public int imageCounter=0;
	public int deathImageCounter=0;
	
	public Enemy enemy;
	public Tower tower;
	public Point enemyPoint;
	public Point towerPoint;
	public int xDistance,yDistance,distance;
	public int counter=0;
	public Shot(Point e,Point t,Enemy en,Tower to)
	{
		enemy=en;
		tower=to;
		enemyPoint=e;
		towerPoint=t;
		xDistance=enemyPoint.x-towerPoint.x;
		yDistance=enemyPoint.y-towerPoint.y;
		distance=(int)Math.sqrt(Math.pow(xDistance,2)+Math.pow(yDistance,2));
		
		//These start the threads that animate the shot images and shot death images
		shotImageThread.start();
		shotDeathImageThread.start();
	}
	
	public static boolean isEnemyGoingToDie(Enemy e,ArrayList<ArrayList<Shot>> allShots)
	{
		for(int j=0;j<allShots.size();j++)
		{
			for(int i=0;i<allShots.get(j).size();i++)  //For every index in shots
			{
				if(allShots.get(j).get(i).enemy==e)  //If there is a shot headed towards the enemy
				{
					e.futureDamage+=allShots.get(j).get(i).tower.towerPower;  //Add the appropriate future damage to the enemy being shot at
				}
			}
		}
		if(e.futureDamage>=e.health)  //If the total future damage on an enemy is greater than or equal to the enemy's health
		{
			return true;
		}
		e.futureDamage=0;
		return false;
	}

	public Image getCurrentDeathImage()
	{
		if(deathImageCounter<tower.shotDeathImages.size())
			return tower.shotDeathImages.get(deathImageCounter);
		else
			return null;
	}

	public Image getCurrentImage()
	{
		if(imageCounter<tower.shotImages.size())
			return tower.shotImages.get(imageCounter);
		else
			return null;
	}
	
	//These two classes animate the shot images
	public class ShotImageUpdater implements Runnable
	{
		public void run()
		{
			while(true)
			{
				imageCounter=(imageCounter+1)%tower.numberOfImages;  //Adds one to the imageCounter until it is equal to the numberOfImages, at which point it sets it back to zero
				try
				{
					Thread.sleep(tower.imageCounterDelay);
				}
				catch(Exception e){}
			}
		}
	}
	public class ShotDeathImageUpdater implements Runnable
	{
		public void run()
		{
			while(true)
			{
				deathImageCounter=(deathImageCounter+1)%tower.numberOfDeathImages;  //Adds one to the deathImageCounter until it is equal to the numberOfDeathImages, at which point it sets it back to zero
				try
				{
					Thread.sleep(tower.deathImageCounterDelay);
				}
				catch(Exception e){}
			}
		}
	}
}