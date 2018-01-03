import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *   * source of the code1: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 * source of the code2: https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
	 * source of the code3: http://www.avajava.com/tutorials/lessons/how-do-i-use-a-filenamefilter-to-display-a-subset-of-files-in-a-directory.html;jsessionid=C8740187DF79764CE4DAD895FD5078F0
	 * @param args
 *     @author computer
 *
 */

public class WriteToCsv  {

	public File f;

	public WriteToCsv()
	{
		//here u can choose the address of the Directory
		this.f=new File("C:\\Users\\computer\\Desktop\\Input");
	}

	/**
	 * the function "createlistofdata" input a Directory with csv files and inserting
	 * the data to one big List of ArrayLists.
	 * 
	 * please note that you need to change the directory location according to
	 * your computer for it to read the directory successfully
	 */

	static ArrayList<String> IDlist=new ArrayList<String>();
	static ArrayList<Integer> IDsplit=new ArrayList<Integer>();

	/**
	 * Takes all the WiggleWifi's tables for a directory and sorts it 
	 * @param fcsv the directory location
	 * @return A list of arraylist that stores all the data from the WiggleWifi files
	 * @throws IOException
	 */
	public List<ArrayList<String>> createlistofdata() throws IOException
	{
		List<ArrayList<String>> inputCSV=new ArrayList<ArrayList<String>>();

		File f = this.f; // current directory

		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".csv")) { // check that the file is csv 
					return true;                      // wont work if not csv
				} else {
					return false;
				}
			}
		};

		File[] files = f.listFiles(textFilter);
		for (File file : files) {

			if (file.isDirectory()) {
				//System.out.print("directory:");
			} else {
				//System.out.print("file:");
			}
			//System.out.println(file.getCanonicalPath());



			String csvfile=file.getCanonicalPath();
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";

			try {

				int count=0;
				br = new BufferedReader(new FileReader(csvfile));
				while ((line = br.readLine()) != null)
				{
					// use comma as separator
					String[] Getline = line.split(cvsSplitBy);
					if(count==0)
					{
						/**
						 * here i save the ID's in the first line of each wiglewifi file
						 * in a String list and also its location in Integer list. it
						 * will help me later when building the big CSV file
						 */
						String ID=Getline[2];
						IDlist.add(ID);
						IDsplit.add(inputCSV.size());
						count++;
					}
					else if(count==1)
					{
						//skip titles line
						count++;
					}
					else
					{
						ArrayList<String> inner=new ArrayList<String>();
						for(int i=0; i<=10; i++)
						{
							inner.add(Getline[i]);
						}
						inputCSV.add(inner);
					}
				}
				// this is to catch Exception and wrong files
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


		}
		/**
		 * here i call my bubblesort function to sort my list by signals
		 */
		bubbleSort(inputCSV);
		return inputCSV;
	}

	/**
	 * in "createMap" function i insert all the data from the ArrayList to HashMap in order to 
	 * seperate location, time and ID from the wifi data. it will help me 
	 * write the data to a 46 columns format CSV table. i wrote 2 classes, one 
	 * for location and one for WIFI. the HashMap key is location and the values
	 * are the top 10 signals WIFIs.	
	 */
	/**
	 * Takes the data from the previous ArrayList and put it inside a HashMap in order to seperate 
	 * the location, time and ID from the data
	 * @param inputCSV the ArrayList from the previous function
	 * @return HashMap with the right format
	 */
	public HashMap<Location,ArrayList<WIFI>> createMap(List<ArrayList<String>> inputCSV)
	{
		HashMap<Location,ArrayList<WIFI>> m1=new HashMap();
		int idcounter=0;
		int maxsize=IDsplit.size();
		for(int i=0; i<inputCSV.size(); i++)
		{
			double Lat=Double.parseDouble(inputCSV.get(i).get(6));
			double Lon=Double.parseDouble(inputCSV.get(i).get(7));
			double Alt=Double.parseDouble(inputCSV.get(i).get(8));
			String ID=IDlist.get(idcounter);
			if(idcounter<maxsize-1)
			{
				if(i==IDsplit.get(idcounter+1) & i!=0)
				{
					/**
					 * by this "if" i can know when to switch ID to the next one
					 **/
					idcounter++;
					ID=IDlist.get(idcounter);
				}
			}
			String Time=inputCSV.get(i).get(3);
			Location L=new Location(Lat,Lon,Alt,ID,Time);
			if(m1.containsKey(L))
			{
				continue;
			}
			else
			{
				ArrayList<WIFI> WIFIA=new ArrayList<WIFI>();
				int countwifi=1;
				for(int j=0; j<inputCSV.size() & countwifi<=10; j++)
				{
					double Lat0=Double.parseDouble(inputCSV.get(j).get(6));
					double Lon0=Double.parseDouble(inputCSV.get(j).get(7));
					double Alt0=Double.parseDouble(inputCSV.get(j).get(8));
					String Time0=inputCSV.get(j).get(3);
					Location temp=new Location(Lat0,Lon0,Alt0,ID,Time0);
					if(L.equalocation(temp)==true & countwifi!=11 )
					{
						String SSID=inputCSV.get(j).get(1);
						String MAC=inputCSV.get(j).get(0);
						int Frequency=Integer.parseInt(inputCSV.get(j).get(4));
						int Signal=Integer.parseInt(inputCSV.get(j).get(5));
						WIFI W=new WIFI(SSID,MAC,Frequency,Signal);
						WIFIA.add(W);
						countwifi++;
					}
				}
				m1.put(L, WIFIA);
			}
		}
		return m1;
	}

	/**
	 * in "writethecsvtable" function i write the map into a big 46 columns CSV table
	 * the outputed file name is "finaltest460"
	 * note that you need to change the output Location regarding your computer!
	 */
	/**
	 * Takes the HashMap and write it into a CSV file
	 * @param m the HashMap from the previous function
	 * @throws IOException
	 */
	public void writethecsvtable(HashMap<Location,ArrayList<WIFI>> m) throws IOException
	{
		FileWriter writer = new FileWriter("C:\\Users\\computer\\Desktop\\OutPut\\CSVOutput.csv");
		List<String> titles= new ArrayList<>();
		titles.add("Latitude");
		titles.add("Longitude");
		titles.add("Altitude");
		titles.add("ID");
		titles.add("Time");
		for(int i=1; i<=10; i++)
		{
			titles.add("SSID"+i);
			titles.add("MAC"+i);
			titles.add("Frequency"+i);
			titles.add("Signal"+i);
		}
		String collectitles= titles.stream().collect(Collectors.joining(","));
		writer.write(collectitles);
		writer.write("\n");
		for(Entry<Location, ArrayList<WIFI>> entry : m.entrySet())
		{
			String collect=entry.getKey()+","+entry.getValue();
			writer.write(collect);
			writer.write("\n");
		}
		writer.close();

	}





	public static void main(String[] args) throws IOException {
		/**
		 * some tests to see that my functions work properly
		 */

		WriteToCsv test=new WriteToCsv();
		List<ArrayList<String>> testCSV=test.createlistofdata();
		HashMap<Location,ArrayList<WIFI>> m1=test.createMap(testCSV);
		test.writethecsvtable(m1);
		//System.out.println(IDlist);
		//System.out.println(IDsplit);
		//System.out.println(testCSV);
		//System.out.println(m1);
	}

	/**
	 * this is the bubble sort i made to sort my list by signals(RSSI)
	 */
	static void bubbleSort(List<ArrayList<String>> arr)
	{  
		int n = arr.size();  
		ArrayList<String> temp=new ArrayList<String>();  
		for(int i=0; i < n; i++){  
			for(int j=1; j < (n-i); j++){ 
				int n1=Integer.parseInt(arr.get(j-1).get(5));
				int n2=Integer.parseInt(arr.get(j).get(5));
				if(n1 < n2){  
					//swap elements  
					temp = arr.get(j-1); 
					arr.set(j-1, arr.get(j));  
					arr.set(j, temp);
				}  

			}  
		}  
	}

}
