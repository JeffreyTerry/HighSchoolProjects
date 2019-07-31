import java.util.ArrayList;
import java.util.Random;

public class Waves
{
	Random randy=new Random();
	public ArrayList<Wave> waves=new ArrayList<Wave>();
	
	public Waves()
	{
		createWave(10,500,"Nathan");
		createWave(100,400,"StrongJellyBean","JellyBean");
		addEnemyToCurrentWave(50,"Ray");
		createWave(100,600,"StrongJellyBean","Zombie");
		createWave(150,300,"JellyBean","Nathan","Nathan");
		addEnemyToCurrentWave(100,"Ray");
	}
	
	public void addEnemyToCurrentWave(int index,String type)  //Adds a single enemy to the wave of type "type" at the given index
	{
		if(waves.size()!=0)  //This makes sure there is a wave to add the enemy to
			waves.get(waves.size()-1).addEnemy(index,type);
	}
	
	public void createWave(int length,int delay,String...types)
	{
		String enems[]=new String[length];
		int numberOfTypes=types.length;  //Not filtering out repeats allows the types to be weighted
		int seed;
		for(int i=0;i<enems.length;i++)
		{
			seed=randy.nextInt(numberOfTypes);
			enems[i]=types[seed];
		}
		waves.add(new Wave(delay,enems));
	}
	
	public int getNumberOfTypes(String types[])  //Calculates the number of unique Strings in an array of Strings
	{
		int numberOfTypes=0;
		boolean seen=false;
		ArrayList<String> typesSeen=new ArrayList<String>();
		for(String type: types)
		{
			for(int i=0;i<typesSeen.size();i++)
			{
				if(type.equals(typesSeen.get(i)))
				{
					seen=true;
				}
			}
			if(!seen)
			{
				typesSeen.add(type);
				numberOfTypes++;
			}
		}
		
		return numberOfTypes;
	}
}
