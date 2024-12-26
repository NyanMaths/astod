import game.Level;
import java.util.Scanner;
import libraries.StdDraw;

public class ASToD
{
public static void main (String[] args) throws Exception
{
    String levelId;
    try (Scanner reader = new Scanner(System.in))
	{
        System.out.println("Which level ? ");
        levelId = reader.nextLine();
    }
	if (levelId.isEmpty())
	{
		levelId = "1";
	}

	StdDraw.setCanvasSize(1024, 720);
    StdDraw.setXscale(-12, 1012);
    StdDraw.setYscale(-10, 710);
	StdDraw.enableDoubleBuffering();
	
	Level level = new Level(levelId);
	level.start();
}
}
