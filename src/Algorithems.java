import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

public class Algorithems {

	public List<ArrayList<String>> Data;

	public Algorithems()
	{
		WriteToKML test=new WriteToKML();
		this.Data=test.inputheCSVfile();
	}

	/**
	 * This function calculates the weighted point according to the first algorithm (using the MAC address)
	 * @param MAC the specific MAC address that you want to find
	 * @param Accuracy the accuracy level that you want to calculate by (between 3-10)
	 * @return the weighted point (the WIFI point location)
	 */
	public Point3D getWpoint(String MAC) // algoritem A
	{
		List<ArrayList<String>> Mlist=new ArrayList<ArrayList<String>>();
		List<S_I> signals=new ArrayList<S_I>();
		for(int i=0; i<Data.size(); i++)
		{
			for(int j=6; j<Data.get(i).size(); j+=4)
			{
				if(MAC.equals(Data.get(i).get(j)))
				{
					if(!Mlist.contains(Data.get(i)))
					{
						Mlist.add(Data.get(i));
						int signal;
						if(Data.get(i).get(j+2).length()==4)
						{
							String temp=Data.get(i).get(j+2);
							String finish=temp.substring(0,3);
							signal=Integer.parseInt(finish);
						}
						else
						{
							signal=Integer.parseInt(Data.get(i).get(j+2));
						}
						S_I temp=new S_I(signal,Mlist.size()-1);
						signals.add(temp);
					}
				}
			}
		}
		if(Mlist.isEmpty())
		{
			Point3D ans=new Point3D();
			return ans;
		}
		S_IbubbleSort(signals);
		double[] Warr=new double[signals.size()];
		Point3D[] Points=new Point3D[Mlist.size()];
		for(int i=0; i<signals.size(); i++)
		{
			Warr[i]=1.0/(Math.pow(signals.get(i).getSignal(), 2));
			double Lat=Double.parseDouble(Mlist.get(signals.get(i).getIndex()).get(0));
			double Lon=Double.parseDouble(Mlist.get(signals.get(i).getIndex()).get(1));
			double Alt=Double.parseDouble(Mlist.get(signals.get(i).getIndex()).get(2));
			Point3D point=new Point3D(Lat,Lon,Alt);
			Points[i]=point;
		}
		Point3D answer=CenterW(Points,Warr);
		return answer;
	}

	/**
	 * Calculates the center weight of all the points in the array
	 * @param points the points you want to calculate
	 * @param arr the array with the weights
	 * @return the weighted point
	 */
	public Point3D CenterW(Point3D[] points, double[] arr)
	{
		Point3D WCenter=new Point3D();
		double Latnominator=0;
		double Latdenominator=0;
		for(int i=0; i<arr.length; i++)
		{
			Latnominator+=(points[i].getLat())*(arr[i]);
			Latdenominator+=arr[i];
		}
		WCenter.Lat=Latnominator/Latdenominator;
		double Lonnominator=0;
		double Londenominator=0;
		for(int i=0; i<arr.length; i++)
		{
			Lonnominator+=(points[i].getLon())*(arr[i]);
			Londenominator+=arr[i];
		}
		WCenter.Lon=Lonnominator/Londenominator;
		double Altnominator=0;
		double Altdenominator=0;
		for(int i=0; i<arr.length; i++)
		{
			Altnominator+=(points[i].getAlt())*(arr[i]);
			Altdenominator+=arr[i];
		}
		WCenter.Alt=Altnominator/Altdenominator;
		return WCenter;
	}

	/**
	 * Calculates the weighted point according to the second algorithm
	 * @param arr an array that contains M_S objects
	 * @return weighted point
	 */
	public Point3D GetWlocation(M_S[] arr) // algoritem B
	{
		List<ArrayList<M_S>> list=new ArrayList<ArrayList<M_S>>();
		for(int k=0; k<arr.length; k++)
		{
			ArrayList<M_S> inner=new ArrayList<M_S>();
			for(int i=0; i<this.Data.size(); i++)
			{
				for(int j=6; j<this.Data.get(i).size(); j+=4)
				{
					if(Data.get(i).get(j).equals(arr[k].getMac()))
					{
						String mac=Data.get(i).get(j);
						int signal;
						if(Data.get(i).get(j+2).length()==4)
						{
							String temp=Data.get(i).get(j+2);
							String finish=temp.substring(0,3);
							signal=Integer.parseInt(finish);
						}
						else
						{
							signal=Integer.parseInt(Data.get(i).get(j+2));
						}
						M_S temp=new M_S(mac,signal);
						inner.add(temp);
					}

				}
			}
			if(inner.isEmpty())
			{
				M_S temp0=new M_S(arr[k].getMac());
				inner.add(temp0);
			}
			list.add(inner);
		}
		List<Integer> indexes=new ArrayList<Integer>();
		for(int i=0; i<list.size(); i++)
		{
		    int index=indexofclosest(arr[i].getSignal(),list.get(i));
		    indexes.add(index);
		}
		String[] Macs=new String[arr.length];
		double[] WSignals=new double[arr.length];
		for(int i=0; i<list.size(); i++)
		{
			Macs[i]=list.get(i).get(indexes.get(i)).getMac();
			WSignals[i]=1.0/(Math.pow(list.get(i).get(indexes.get(i)).getSignal(), 2));
		}
		Point3D[] WPoints=new Point3D[arr.length];
		for(int i=0; i<WPoints.length; i++)
		{
			WPoints[i]=getWpoint(Macs[i]);
		}
		Point3D answer=CenterW(WPoints,WSignals);
		return answer;
	}
	
	public void MakeKml(Point3D[] arr) throws FileNotFoundException
	{
		final Kml kml=new Kml();
		Document doc=kml.createAndSetDocument().withName("my points");
		for(int i=0; i<arr.length; i++)
		{
			String Location=arr[i].getLon()+","+arr[i].getLat();
			Placemark p=KmlFactory.createPlacemark();
			doc.createAndAddPlacemark().withName("point"+i).withOpen(Boolean.TRUE).withTimePrimitive(p.getTimePrimitive())
			.createAndSetPoint().addToCoordinates(Location);
		}
		kml.marshal(new File("C:\\Users\\computer\\Desktop\\OutPut\\KMLalgo.kml"));
	}







	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
	//	Algorithems A1=new Algorithems();
		//Point3D MacLocation=A1.getWpoint("0a:8d:cb:6e:71:6d",3);
		//System.out.println(MacLocation);	
	//	M_S[] arr=new M_S[3];
	//	arr[0]=new M_S("4c:60:de:d1:da:80",-70);
	//	arr[1]=new M_S("6c:b0:ce:e8:2a:b0",-75);
	//	arr[2]=new M_S("c4:12:f5:83:aa:0c",-80);
	//	Point3D test=A1.GetWlocation(arr);
	//	System.out.println(test);
	//	Point3D[] arr0= {test};
	//	A1.MakeKml(arr0);  
		
		/**
		 * boaz test for algo1
		 */
		Algorithems B1=new Algorithems();
		Point3D line1=B1.getWpoint("b2:6c:ac:9f:f1:c5");
		//System.out.println(line1);
		M_S[] arr1=new M_S[3];
		arr1[0]=new M_S("3c:52:82:ef:a4:8b",-60);
		arr1[1]=new M_S("24:79:2a:2b:07:bc",-63);
		arr1[2]=new M_S("24:79:2a:ab:07:b7",-67);
		Point3D ans=B1.GetWlocation(arr1);
		System.out.println(ans);
		
		// localtime and localdate very important!
	}

	public static void S_IbubbleSort(List<S_I> arr)
	{  
		int n = arr.size(); 
		S_I temp;
		for(int i=0; i < n; i++)
		{  
			for(int j=1; j < (n-i); j++)
			{ 
				int n1=(arr.get(j-1).Signal);
				int n2=(arr.get(j).Signal);
				if(n1 < n2){  
					//swap elements  
					temp = arr.get(j-1); 
					arr.set(j-1, arr.get(j));  
					arr.set(j, temp);
				}  

			}  
		}  
	}

	/**
	 * this function gets an ArrayList with all of the M_S and returns the closest M_S object according to the signal 
	 * @param signal the signal we want
	 * @param list the list with all of the M_S
	 * @return the closets M_S object
	 */
	public static int indexofclosest(int signal,ArrayList<M_S> list)
	{
		int index=-1;
		int min=Integer.MAX_VALUE;
		int maxmin=Integer.MAX_VALUE;
		for(int i=0; i<list.size(); i++)
		{
			min=Math.abs(list.get(i).getSignal()-signal);
			if(min<maxmin)
			{
				maxmin=min;
				index=i;
			}
		}
		return index;
	}

	



}
