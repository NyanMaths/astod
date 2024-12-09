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
	carte.loadFromFile("beta_test");
	int nbRaw = carte.getRaw();
	int nbColumn = carte.getColumn();
	System.out.println("Nombre lignes = " + nbRaw + "\n Nombre colonnes = " + nbColumn);
	double caseSize = 350.0/Math.max(nbRaw, nbColumn);
	for(int i = 0; i<nbRaw;i++)
	{
		for(int j = 0; j<nbColumn; j++)
		{
			Cell case = (carte.getCell(i, j)); //on récup la cellule
			Color couleur = Cell.getColor(case); // on determine sa couleur
			StdDraw.setPenColor(couleur); // on set la couleur

			double x =  //calcul de la taille des cases
			double y = //calcul de la taille des cases
			StdDraw.filledSquare(x,y,caseSize) // dessin de la case
		}
	}
	StdDraw.enableDoubleBuffering();
}
}