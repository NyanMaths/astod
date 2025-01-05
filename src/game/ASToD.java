package game;

import java.util.Scanner;
import libraries.StdDraw;


/**
 * This is a tower defense game
 */
public class ASToD
{
/**
 * Everything starts here, there goes the game
 * @param args launch params, ignored
 * @throws Exception funny aah exceptions
 */
public static void main (String[] args) throws Exception
{
    String gameID;
    try (Scanner reader = new Scanner(System.in))
	{
        System.out.println("Which game ? ");
        gameID = reader.nextLine();
    }
	if (gameID.isEmpty())
	{
		gameID = "1";
	}

	StdDraw.setCanvasSize(1024, 720);
    StdDraw.setXscale(-12, 1012);
    StdDraw.setYscale(-10, 710);
	StdDraw.enableDoubleBuffering();


	Game game = new Game(gameID);
	game.start();
}
}
