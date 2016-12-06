

public class UseGameOfLife {
	static GameOfLife a;
	public static void main(String args[])
	{
		try
		{
		new GameOfLife(Integer.valueOf(args[0]));
		}catch(Exception e)
		{
			System.out.print("Enviar delay!!");
		}
	}
	
}
