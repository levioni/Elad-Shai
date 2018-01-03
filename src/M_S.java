
public class M_S {
	
	public String Mac;
	public int Signal;
	/**
	 * creates a new M_S object that contains mac address and the signal for that address
	 * @param Mac the mac address
	 * @param Signal the signal for that address
	 */
	public M_S(String Mac,int Signal)
	{
		this.Mac=Mac;
		this.Signal=Signal;
	}
	/**
	 * creates a new M_S object with very weak signal (in case you have not found one in the algorithem)
	 * @param Mac the mac address with -120 signal
	 */
	public M_S(String Mac)
	{
		this.Mac=Mac;
		this.Signal=-12000;
	}
	public String getMac() {
		return Mac;
	}
	public void setMac(String mac) {
		Mac = mac;
	}
	public int getSignal() {
		return Signal;
	}
	public void setSignal(int signal) {
		Signal = signal;
	}
	@Override
	public String toString() {
		return "M_S [Mac=" + Mac + ", Signal=" + Signal + "]";
	}
	

}
