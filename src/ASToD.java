import java.util.Scanner;
import libraries.StdDraw;

public class ASToD
{
public static void main (String[] args) throws Exception
{
	Scanner reader = new Scanner(System.in);
	System.out.println("Which level ? ");
	String levelId = reader.nextLine();
	if (levelId.isEmpty())
	{
		levelId = "1";
	}

	StdDraw.setCanvasSize(1024, 720);
    StdDraw.setXscale(-12, 1012);
    StdDraw.setYscale(-10, 710);
	
	Level level = new Level(levelId);
	level.start();
}
}
