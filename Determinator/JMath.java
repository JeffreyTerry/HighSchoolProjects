public class JMath
{
	//*****Note that this method takes a matrix represented by int[columns][rows], not in line with the conventional int[rows][columns]. Unfortunately,
	//when this program was written, I was unfamiliar with 2D arrays and unaware of that convention.
	public static int getDeterminant(int[][] matrix)  //This method calculates the determinant of a matrix, designated by the array matrix[columns][rows]
	{
		int answer = 0;
		
		//This ensures the matrix is a square matrix
		for(int i = 0; i < matrix.length; i++)
			if(matrix.length != matrix[i].length)
				return -1;
		
		//This does the calculation
		if(matrix.length > 1)
		{
			int[][] subMatrix = new int[matrix.length - 1][matrix.length - 1];
			for(int i = 0; i < matrix.length; i++)
			{
				//Create the subMatrix here
				int offset = 0;
				for(int k = 0; k < matrix.length; k++)  //For each column in the matrix
				{
					for(int j = 1; j < matrix.length; j++)  //For each row in the matrix (starting at the second row)
					{
						if(k != i)
							subMatrix[k - offset][j - 1] = matrix[k][j];
						else
							offset=1;
					}
				}
				
				//This does the individual calulations
				answer += (int)Math.pow(-1, i) * matrix[i][0] * getDeterminant(subMatrix);
			}
			return answer;
		}
		else if(matrix.length == 1)
			return matrix[0][0];
		else  //This takes care of empty arrays
			return -1;
	}
	
	public static int convertToInt(String s)
	{
		int returnNum=-1;
		try
		{
			returnNum=Integer.parseInt(s);
		}
		catch(NumberFormatException e){
			System.out.println("Invalid integer argument");
		}
		return returnNum;
	}
}
