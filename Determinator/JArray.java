public class JArray
{
	public static void printArray(int[] array)
	{
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + "\t");
	}
	public static void printArray(int[][] array)
	{
		for(int row = 0; row < array.length; row++)
		{
			for(int column = 0; column < array[0].length; column++)
				System.out.print(array[column][row] + "\t");
			System.out.println();
		}
	}
}