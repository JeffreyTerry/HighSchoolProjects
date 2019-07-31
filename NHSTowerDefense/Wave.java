import java.util.ArrayList;

public class Wave implements Runnable
{
	public ArrayList<Enemy> enemies=new ArrayList<Enemy>();
	public long delay;
	int i=0;  //This variable stores the number of enemies that have entered the map so far
	
	public Wave()
	{
	}
	
	public Wave(long d,String...types)
	{
		for(int i=0;i<types.length;i++)
		{
			enemies.add(new Enemy(types[i],Map.path));
		}
		delay=d;
	}
	
	public void addEnemy(int index,String type)
	{
		enemies.add(index,new Enemy(type,Map.path));
	}
	
	public void run()
	{
		while(i<enemies.size() && !TowerDefenseFrame.paused)
		{
			try
			{
				Thread.sleep(delay);
			}
			catch(Exception e){}
			
			//This waits until the map is done painting
			while(TowerDefenseFrame.map.isPainting)
			{
				try
				{
					Thread.sleep(1);
				}
				catch(Exception e){}
			}
			
			TowerDefenseFrame.map.addEnemy(enemies.get(i).type);
			
			//This code starts Ray's superpower if he enters the game and no other Ray's are already active
			if(enemies.get(i).type.equals("Ray") && !Enemy.rayIsActive)
			{
				Enemy.startRaySuperpower();
			}
			
			i++;
		}
		
		while(TowerDefenseFrame.paused)
		{
			try
			{
				Thread.sleep(1);
			}
			catch(Exception e){}
		}
		if(i<enemies.size())
			run();
	}
}