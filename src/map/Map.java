package map;

import graphics.Drawable;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import libraries.StdDraw;


public final class Map implements Drawable
{
private Path name;
private List<List<Cell>> matrix;

public Map (String name) throws InvalidMapException
{
	this.loadFromFile(name);
}

public void loadFromFile (String name) throws InvalidMapException
{
	this.name = Paths.get("assets/maps/" + name + ".mtp");
	this.matrix = new ArrayList<>();

	try (BufferedReader reader = Files.newBufferedReader(this.name))
	{
		String currentLine = reader.readLine();

		int i = 0;
		while (currentLine != null)
		{
			this.matrix.addLast(new ArrayList<>());
			int j = 0;
			for (Character cellType : currentLine.toCharArray())
			{
				this.matrix.getLast().addLast(new Cell(cellType, new Point2D.Float(i, j)));
				j++;
			}
			
			currentLine = reader.readLine();
			i++;
		}

		Cell.setSize(Math.min(1024.0f, 720.0f) / (float)Math.max(this.getRowsCount(), this.getColumnsCount()));
    }
	catch (IOException eee)
	{
		System.err.println(eee);
	}
}


public Path getLocation ()
{
	return this.name;
}

public int getRowsCount ()
{
	return matrix.size();
}

public int getColumnsCount ()
{
	return matrix.get(0).size();
}

public Cell getCell (int row, int col)
{
	return this.matrix.get(row).get(col);
}

public void drawCoin()
{
	StdDraw.setPenColor (new Color(212,175,55));
	StdDraw.filledCircle (760, 641, 20);
	StdDraw.text(810, 641, "Coins"); //J'arrive pas avec Player.getCoins...
	StdDraw.setPenColor (new Color(192,192,192));
	StdDraw.filledCircle (760, 641, 15);

}

public void drawHeart()
{
	int centerX = 930;
	int centerY = 641;
	int halfHeight = 20;
	StdDraw . setPenColor ( new Color (223 , 75 , 95) );
	double [] listX = new double []
	{
		centerX,
		centerX-halfHeight,
		centerX-halfHeight,
		centerX-0.66*halfHeight,
		centerX-0.33*halfHeight,
		centerX,
		centerX+0.33*halfHeight,
		centerX+0.66*halfHeight,
		centerX+halfHeight,
		centerX+halfHeight,
	};
	double [] listY = new double []
	{
		centerY-halfHeight,
		centerY,
		centerY+0.5*halfHeight,
		centerY+halfHeight,
		centerY+halfHeight,
		centerY+0.5*halfHeight,
		centerY+halfHeight,
		centerY+halfHeight,
		centerY+0.5*halfHeight,
		centerY,
		};
		StdDraw.filledPolygon ( listX , listY );
		StdDraw.text(980,641,"Health"); //J'arrive pas avec Player.getHealth...
}

public void drawLevelInfo() //il va prendre le niveau et la vague actuelle
{
	StdDraw.text(760,688,"LVL:X/X");
	StdDraw.text(930,688,"WAVE:X/X");
	//J'aimerais test de changer le font, mais je sais pas si c'est une très bonne idée, car faudra le changer entre les fonctions. 
	//Genre pour la vie et la thune, c'est un style différent de celui pour la vague et le niveau en terme d'écriture.
}

public void drawShop()
{
	double x1 = 795;
	double y1 = 545;
	double halfWidth = 72;
	double halfHeight = 60.5;
	double x2 = x1+2*halfWidth;
	double y2 = y1-2*halfHeight;
	double y3 = y1-4*halfHeight;
	StdDraw.rectangle(x1, y1, halfWidth, halfHeight);
	StdDraw.text(x1, y1, "Tower1");
	StdDraw.rectangle(x2, y1, halfWidth, halfHeight);
	StdDraw.text(x2, y1, "Tower2");
	StdDraw.rectangle(x1,y2,halfWidth,halfHeight);
	StdDraw.text(x1, y2, "Tower3");
	StdDraw.rectangle(x2,y2,halfWidth,halfHeight);
	StdDraw.text(x2, y2, "Tower4");
	StdDraw.rectangle(x1,y3,halfWidth,halfHeight);
	StdDraw.text(x1, y3, "Tower5");
}

@Override
public void draw ()
{
    int rowsCount = this.getRowsCount();
    int columnsCount = this.getColumnsCount();

	for (int i = 0 ; i<rowsCount ; i++)
	{
		for (int j = 0 ; j<columnsCount ; j++)
		{
			this.getCell(i, j).draw();
		}
	}
	
	StdDraw.setPenColor(Color.BLACK);
	StdDraw.rectangle(867,688,144,12);
	StdDraw.rectangle(867,641,144,25);
	StdDraw.rectangle(867, 303, 144,303);
	this.drawLevelInfo();
	this.drawShop();
	this.drawCoin();
	this.drawHeart();
}
}
