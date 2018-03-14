
public class Pixel {
	
	int color;
	
	//Pixel pixel;
	
	Location p;
	
	
	public Pixel(Location p, int color)
	{
		this.color = color;
		
		this.p = p;
	}
	
	//returns location which is p
	public Location getLocation()
	{	
		return this.p;
	}
	
	public int getColor()
	{
		return this.color;
	}

}
