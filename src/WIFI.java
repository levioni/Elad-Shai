
/**
 * this class define WIFI by SSID,MAC,Frequency and Signal
 * @author computer
 *
 */
public class WIFI {
	
	public String SSID;
	public String MAC;
	public int Frequency;
	public int Signal;
	/**
	 * Construct a new WIFI object
	 * @param SSID for the WIFI's name
	 * @param MAC for the WIFI's mac address
	 * @param F for the WIFI's frequency
	 * @param Signal for the Wifi's signal strength
	 */
	public WIFI(String SSID, String MAC, int F, int Signal)
	{
		this.SSID=SSID;
		this.MAC=MAC;
		this.Frequency=F;
		this.Signal=Signal;
	}
	

	@Override
	public String toString() {
		return "" + SSID + "," + MAC + "," + Frequency + "," + Signal + "";
	}


}
