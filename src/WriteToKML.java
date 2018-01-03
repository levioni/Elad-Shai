import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

/**
 * https://labs.micromata.de/projects/jak/quickstart.html
 * https://stackoverflow.com/questions/12701364/how-to-mark-multiple-coordinates-in-kml-using-java
 */

public class WriteToKML {

	public String address;

	public WriteToKML()
	{
		//the address of the 46 columns csv table we made in the previous class
		this.address="C:\\Users\\computer\\Desktop\\boazinput\\BM3_myversion.csv";
	}

	/**
	 * in "inputtheCSVfile" function i input the big 46 columns CSV file
	 * that we made in WriteToCSV class
	 * and insert the data to a collection (arraylist of arraylists)
	 * 
	 * please note that you need to change the location of the file according to
	 * your computer for it to read the file successfully
	 */
	/**
	 * Takes the output CSV file and insert it into a collection
	 * @param s the CSV file
	 * @return an Arraylist of Arraylists
	 */
	public List<ArrayList<String>> inputheCSVfile()
	{
		List<ArrayList<String>> mycsv=new ArrayList<ArrayList<String>>();
		String csvFile =this.address;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] input = line.split(cvsSplitBy);
				ArrayList<String> inner=new ArrayList<String>();
				for(int i=0; i<input.length; i++)
				{
					inner.add(input[i]);
				}
				mycsv.add(inner);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mycsv;
	}

	/**
	 * in "filterthelist" funtion i filter my list by Time, ID or location
	 * and insert the filtered list to a new list.
	 * please note the following instruction on how to filter succesfully:
	 * /**
	 * to filter by ID, enter the ID name by String and number 3!
	 * to filter by Time, enter the Time by String and number 4!
	 * to filter by Location, enter String like this: "Lat,Lon" and choose other number than 3 or 4!
	 * 
	 * 
	 * this instructions also written in the main
	 */
	/**
	 * Filters the Arraylist from the previous function by use input:
	 * 3 for ID
	 * 4 for time
	 * insert a point (latitude, longitude and radius) to filter by points
	 * @param list the list from the previous function
	 * @param filterby the string to filter by (ID,time or location)
	 * @param choose defines the filter
	 * @return a filtered ArrayList
	 */
	public List<ArrayList<String>> filterthelist(List<ArrayList<String>> list, String filterby, int choose )
	{
		Predicate<ArrayList<String>> condition1;
		if(choose==3)// filter by ID
		{
			condition1=s -> s.get(3).contains(filterby);
		}
		else if(choose==4)//filter by Time
		{
			condition1=s -> s.get(4).contains(filterby);
		}
		else // filter by Location
		{
			condition1=s -> s.get(0).contains(filterby) & s.get(1).contains(filterby);
		}
		List<ArrayList<String>> filteredStrings=filterby(list ,condition1);
		return filteredStrings;
	}
	/**
	 * Filters the Arraylist from the previous function by use input:
	 * 3 for ID
	 * 4 for time
	 * insert a point (latitude, longitude and radius) to filter by points
	 * @param list the list itself
	 * @param lat the latitude you want
	 * @param lon the logitude you want
	 * @param radius the radius you want around the point
	 * @return a filterd list with all the points surrounding the point in a certain radius
	 */
	public List<ArrayList<String>> filterthelist(List<ArrayList<String>> list, double lat, double lon, double radius)
	{
		List<ArrayList<String>> Rfilter=new ArrayList<ArrayList<String>>();
		for(int i=1; i<list.size(); i++)
		{
			double Lat0=Double.parseDouble(list.get(i).get(0));
			double Lon0=Double.parseDouble(list.get(i).get(1));
			if(radius>=Distance(lat,lon,Lat0,Lon0))
			{
				Rfilter.add(list.get(i));
			}
		}
		return Rfilter;
	}
	



	/**
	 * in "writethekmlfile" function i am writing my filtered list into a kml file 
	 * and then output it to my computer.
	 * file name is "KMLoutputAPI".
	 * we are using JAK library to create the kml file
	 */
	/**
	 * Takes the filtered ArrayList and writes a KML file
	 * @param filteredlist the filtered Arraylist from the previous function
	 * @throws FileNotFoundException
	 */
	public void writethekmlfile(List<ArrayList<String>> filteredlist) throws FileNotFoundException
	{
		final Kml kml=new Kml();
		Document doc=kml.createAndSetDocument().withName("my points");
		for(int i=0; i<filteredlist.size(); i++)
		{
			String Location=filteredlist.get(i).get(1)+","+filteredlist.get(i).get(0);
			String time=filteredlist.get(i).get(4);
			Timestamp ts=Timestamp.valueOf(time);
			Placemark p=KmlFactory.createPlacemark();
			p.createAndSetTimeStamp().addToTimeStampSimpleExtension(ts);
			doc.createAndAddPlacemark().withName("point"+i).withOpen(Boolean.TRUE).withTimePrimitive(p.getTimePrimitive())
			.createAndSetPoint().addToCoordinates(Location);
		}
		kml.marshal(new File("C:\\Users\\computer\\Desktop\\OutPut\\KMLoutput2.kml"));
	}



	public static void main(String[] args) throws FileNotFoundException {

		/**
		 * some tests to see that my functions work properly
		 */

		WriteToKML test=new WriteToKML();
		List<ArrayList<String>> check=test.inputheCSVfile();
		//List<ArrayList<String>> afterfilter=test.filterthelist(check, "Lenovo",3);
		List<ArrayList<String>> afterfilter2=test.filterthelist(check,32.1624034177512,34.8076996207237,0.005);
		/**
		 * to filter by ID, enter the ID name String and number 3!
		 * to filter by Time, enter the Time by String and number 4!
		 * to filter by Location, enter String like this: "Lat,Lon" and choose other number than 3 or 4!
		 */
		test.writethekmlfile(afterfilter2);
		//System.out.println(afterfilter.size());
		//System.out.println(check.size());

	}

	/**
	 * this function help me to filter the list by interface
	 */
	/**
	 * The filter
	 * @param strings the filter
	 * @param condition the condition to filter by
	 * @return the filtered list
	 */
	public static List<ArrayList<String>> filterby(List<ArrayList<String>> strings, Predicate<ArrayList<String>> condition)
	{
		List<ArrayList<String>> output=new ArrayList<ArrayList<String>>(); //initalize empty list
		for(int i=0; i<strings.size(); i++)
		{
			if(condition.test(strings.get(i)))
			{
				output.add(strings.get(i));
			}
		}
		return output;
	}
	/**
	 * Calculates the distance between two points
	 * @param Lat1 latitude of the first point
	 * @param Lon1 longitude of the first point
	 * @param Lat2 latitude of the second point
	 * @param Lon2 longitude of the second point
	 * @return the distance between the points
	 */
	public static double Distance(double Lat1, double Lon1, double Lat2, double Lon2)
	{
		double Dlat=Math.pow((Lat1-Lat2), 2);
		double Dlon=Math.pow((Lon1-Lon2), 2);
		return Math.sqrt(Dlat+Dlon);
	}

}
