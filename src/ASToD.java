import java.nio.file.Path;
import java.awt.Color;
import libraries.StdDraw;
public class ASToD
{
public static void main (String[] args) throws Exception
{
	Map carte = new Map("beta_test");
	StdDraw.setCanvasSize (1024 , 720);
	StdDraw.setXscale ( -12 , 1012);
	StdDraw.setYscale ( -10 , 710);
	StdDraw.setPenColor(Color.BLACK);
	StdDraw.filledSquare(0.5,0.5,350);
	StdDraw.enableDoubleBuffering();
}
}