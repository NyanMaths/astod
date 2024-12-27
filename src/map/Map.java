package map;

import graphics.Drawable;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import libraries.StdDraw;
import units.towers.Archer;
import units.towers.EarthCaster;
import units.towers.FireCaster;
import units.towers.WaterCaster;
import units.towers.WindCaster;


public final class Map implements Drawable
{
private Path name;
private List<List<Cell>> matrix;
private Point2D.Float playerPosition;
private Point2D.Float spawnerPosition;

public Map (String name) throws InvalidMapException, InvalidMapPathException
{
	this.load(name);
}
public Map () throws InvalidMapException, InvalidMapPathException
{
	// nothing here
}

public void load (String name) throws InvalidMapException, InvalidMapPathException
{
	this.name = Paths.get("assets/maps/" + name + ".mtp");

	File mapFile = new File("assets/maps/" + name + ".mtp");
	if (!mapFile.exists() || mapFile.isDirectory())
	{
		throw new InvalidMapPathException(this.name);
	}


	this.matrix = new ArrayList<>();

	try (BufferedReader reader = Files.newBufferedReader(this.name))
	{
		String currentLine = reader.readLine();

		Point2D.Float playerCoordinates = new Point2D.Float(0.0f, 0.0f);
		Point2D.Float spawnerCoordinates = new Point2D.Float(0.0f, 0.0f);

		int i = 0;
		while (currentLine != null)
		{
			this.matrix.addLast(new ArrayList<>());
			int j = 0;
			for (Character cellType : currentLine.toCharArray())
			{
				if (cellType == 'B')
				{
					playerCoordinates = new Point2D.Float(i, j);
				}
				if (cellType == 'S')
				{
					spawnerCoordinates = new Point2D.Float(i, j);
				}

				this.matrix.getLast().addLast(new Cell(cellType, new Point2D.Float(i, j)));
				j++;
			}
			
			currentLine = reader.readLine();
			i++;
		}

		Cell.setSize(Math.min(1024.0f, 720.0f) / (float)Math.max(this.getRowsCount(), this.getColumnsCount()));

		this.playerPosition = new Point2D.Float(Cell.getSize()*playerCoordinates.x + 0.5f*Cell.getSize(), Cell.getSize()*playerCoordinates.y + 0.5f*Cell.getSize());
		this.spawnerPosition = new Point2D.Float(Cell.getSize()*spawnerCoordinates.x + 0.5f*Cell.getSize(), Cell.getSize()*spawnerCoordinates.y + 0.5f*Cell.getSize());
    }
	catch (IOException eee)
	{
		throw new InvalidMapException(this.name);
	}
}


public Point2D.Float getPlayerPosition ()
{
	return this.playerPosition;
}

public Point2D.Float getSpawnerPosition ()
{
	return this.spawnerPosition;
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
	StdDraw.text(810, 641, "Coins"); //J'arrive pas avec Player.getCoins... normal, where player ?
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

	float x1 = 795;
	float y1 = 545;
	float halfWidth = 72;
	float halfHeight = (float) 60.5;
	float x2 = x1+2*halfWidth;
	float y2 = y1-2*halfHeight;
	float y3 = y1-4*halfHeight;
	StdDraw.rectangle(x1, y1, halfWidth, halfHeight);
	Point2D.Float spawnPosition = new Float(x1-55,y1);
	Archer archer = new Archer(spawnPosition);
	archer.draw();
	StdDraw.rectangle(x2, y1, halfWidth, halfHeight);
	spawnPosition.setLocation(x2-55,y1);
	EarthCaster earth = new EarthCaster(spawnPosition);
	earth.draw();
	StdDraw.rectangle(x1,y2,halfWidth,halfHeight);
	spawnPosition.setLocation(x1-55,y2);
	FireCaster fire = new FireCaster(spawnPosition);
	fire.draw();
	StdDraw.rectangle(x2,y2,halfWidth,halfHeight);
	spawnPosition.setLocation(x2-55,y2);
	WaterCaster water = new WaterCaster(spawnPosition);
	water.draw();
	StdDraw.rectangle(x1,y3,halfWidth,halfHeight);
	spawnPosition.setLocation(x1-55,y3);
	WindCaster wind = new WindCaster(spawnPosition);
	wind.draw();
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

public boolean isShopClicked()
{
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > 876-144 && StdDraw.mouseX() < 867+144 && StdDraw.mouseY() > 303-303 && StdDraw.mouseY() < 303+303)
	{
		StdDraw.pause(100);
		return true;
	}
	return false;
}

public boolean isMapClicked()
{
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > 350-350 && StdDraw.mouseX() < 350+350 && StdDraw.mouseY() > 350-350 && StdDraw.mouseY() < 350+350)
	{
		StdDraw.pause(100);
		return true;
	}
	return false;
}

public boolean isCellBuildable(double x, double y)
{
	x = StdDraw.mouseX();
	y = StdDraw.mouseY();
	//Cell cell = new Cell(null, new Point2D.Float((float)x, (float)y));
	//return cell.getType() == CellType.Buildable;
	return true;
}
}
