
public class Point3D {
	
	public double Lat;
	public double Lon;
	public double Alt;
	
	public Point3D()
	{
		this.Lat=-1.0;
		this.Lon=-1.0;
		this.Alt=-1.0;
	}
	/**
	 * creates a new Point object that uses the coordinates from the CSV table
	 * @param Lat the latitude of the point
	 * @param Lon the longitude of the point
	 * @param Alt the altitude of the point
	 */
	public Point3D(double Lat, double Lon, double Alt)
	{
		this.Lat=Lat;
		this.Lon=Lon;
		this.Alt=Alt;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public double getLon() {
		return Lon;
	}

	public void setLon(double lon) {
		Lon = lon;
	}

	public double getAlt() {
		return Alt;
	}

	public void setAlt(double alt) {
		Alt = alt;
	}

	@Override
	public String toString() {
		return "Point3D [Lat=" + Lat + ", Lon=" + Lon + ", Alt=" + Alt + "]";
	}
	
	

}
