public class ASToD
{
public static void main (String[] args) throws Exception
{
	System.out.println ("Hello, World!");
	Map carte = new Map("5-8");

	carte.readFile(carte.getLocation());
}
}