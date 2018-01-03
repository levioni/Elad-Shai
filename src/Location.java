

/**
 * this class define Location by Lat,Lon,Alt,ID and time
 * @author computer
 *
 */
public class Location {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(Alt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Double.doubleToLongBits(Alt) != Double.doubleToLongBits(other.Alt))
			return false;
		if (Double.doubleToLongBits(Lat) != Double.doubleToLongBits(other.Lat))
			return false;
		if (Double.doubleToLongBits(Lon) != Double.doubleToLongBits(other.Lon))
			return false;
		return true;
	}
	public double Lat;
	public double Lon;
	public double Alt;
	public String ID; 
	public String Time;
	/**
	 * Creates a new Location object
	 * @param Lat for the point's Latitude
	 * @param Lon for the point's Longitude
	 * @param Alt for the point's Altitude
	 * @param ID for the device ID
	 * @param time for the measured time
	 */
	public Location(double Lat, double Lon, double Alt,String ID, String time)
	{
		this.Lat=Lat;
		this.Lon=Lon;
		this.Alt=Alt;
		this.ID=ID;
		this.Time=time;
	}
	/**
	 * Gets a Location object and checks if this Location is the same as the one recieved
	 * @param L the Location object you want to check with this object
	 * @return true if it has the same parameters, false if it has any different parameter
	 */
	public boolean equalocation(Location L)
	{
		if(this.Lat==L.Lat & this.Lon==L.Lon & this.Alt==L.Alt)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Calculates the distance between two Location objects
	 * @param L the other location you want to measure the distance too
	 * @return the distance between two locations
	 */
	public double Distance(Location L)
	{
		double Dlat=Math.pow((this.Lat-L.Lat), 2);
		double Dlon=Math.pow((this.Lon-L.Lon), 2);
		return Math.sqrt(Dlat+Dlon);
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
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	@Override
	public String toString() {
		return "" + Lat + "," + Lon + "," + Alt + "," + ID + "," + Time + "";
	}


}
