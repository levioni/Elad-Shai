import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BoazTests {

	public String[] kelet;
	public final int size=6;


	public BoazTests()
	{
		this.kelet=new String[this.size];
		this.kelet[0]="C:\\Users\\computer\\Desktop\\testing\\testing\\output\\Algo1_BM2_4.csv";
		this.kelet[1]="C:\\Users\\computer\\Desktop\\testing\\testing\\output\\Algo1_BM3_4.csv";
		this.kelet[2]="C:\\Users\\computer\\Desktop\\testing\\testing\\output\\Algo2_BM2_TS1_4.csv";
		this.kelet[3]="C:\\Users\\computer\\Desktop\\testing\\testing\\output\\Algo2_BM2_TS2_4.csv";
		this.kelet[4]="C:\\Users\\computer\\Desktop\\testing\\testing\\output\\Algo2_BM3_TS1_4.csv";
		this.kelet[5]="C:\\Users\\computer\\Desktop\\testing\\testing\\output\\Algo2_BM3_TS2_4.csv";
	}

	public ArrayList<String> MakeMacalgo1(String address){

		ArrayList<String> Maclist=new ArrayList<String>();

		String csvfile=address;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {


			br = new BufferedReader(new FileReader(csvfile));
			while ((line = br.readLine()) != null)
			{
				// use comma as separator
				String[] Getline = line.split(cvsSplitBy);
				Maclist.add(Getline[1]);
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

		return Maclist;
	}

	public void algo1output(ArrayList<String> MacList) throws IOException
	{
		FileWriter writer = new FileWriter("C:\\Users\\computer\\Desktop\\matala2outputs\\algo1test2.csv");
		List<String> titles= new ArrayList<>();
		titles.add("Latitude");
		titles.add("Longitude");
		titles.add("Altitude");
		titles.add("Mac");
		Algorithems algo1=new Algorithems();
		String collectitles= titles.stream().collect(Collectors.joining(","));
		writer.write(collectitles);
		writer.write("\n");
		for(int i=0; i<MacList.size(); i++)
		{
			Point3D point=algo1.getWpoint(MacList.get(i));
			List<String> points=new ArrayList<String>();
			points.add(String.valueOf(point.getLat()));
			points.add(String.valueOf(point.getLon()));
			points.add(String.valueOf(point.getAlt()));
			points.add(MacList.get(i));
			String collections=points.stream().collect(Collectors.joining(","));
			writer.write(collections);
			writer.write("\n");
		}
		writer.close();

	}


	public List<ArrayList<M_S>> createMS(String address) throws IOException{

		List<ArrayList<M_S>> MacSignal = new ArrayList<ArrayList<M_S>>();
		String csvfile=address;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {


			br = new BufferedReader(new FileReader(csvfile));
			while ((line = br.readLine()) != null)
			{
				// use comma as separator
				String[] Getline = line.split(cvsSplitBy);
				ArrayList<M_S> ms = new ArrayList<>();
				for(int i=6;i<Getline.length;i+=4) {
					String mac = Getline[i];
					double signal = Double.parseDouble(Getline[i+3]);
					int signaltrue=(int)signal;
					M_S temp = new M_S(mac,signaltrue);
					ms.add(temp);
				}
				MacSignal.add(ms);
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

		return MacSignal;
	}


	public void algo2output(List<ArrayList<M_S>> MacSignal) throws IOException
	{
		FileWriter writer = new FileWriter("C:\\Users\\computer\\Desktop\\matala2outputs\\algo2test4.csv");
		List<String> titles= new ArrayList<>();
		titles.add("Latitude");
		titles.add("Longitude");
		titles.add("Altitude");
		for(int i=1;i<=10;i++) {
			titles.add("Mac"+i);
			titles.add("Signal"+i);
		}
		Algorithems algo2=new Algorithems();
		String collectitles= titles.stream().collect(Collectors.joining(","));
		writer.write(collectitles);
		writer.write("\n");
		for(int i=0; i<MacSignal.size(); i++)
		{
			M_S[] arrMS = new M_S[MacSignal.get(i).size()];
			for(int j=0; j<MacSignal.get(i).size();j++) {
				String mac=MacSignal.get(i).get(j).getMac();
				int signal = MacSignal.get(i).get(j).getSignal();
				arrMS[j] = new M_S(mac,signal);
			}
			Point3D point=algo2.GetWlocation(arrMS);
			List<String> points=new ArrayList<String>();
			points.add(String.valueOf(point.getLat()));
			points.add(String.valueOf(point.getLon()));
			points.add(String.valueOf(point.getAlt()));
			for(int k=0;k<arrMS.length;k++) {
				points.add(arrMS[k].getMac());
				points.add(String.valueOf(arrMS[k].getSignal()));
			}
			String collections=points.stream().collect(Collectors.joining(","));
			writer.write(collections);
			writer.write("\n");
		}
		writer.close();

	}



	public static void main(String[] args) throws IOException 
	{
		BoazTests B1=new BoazTests();
		ArrayList<String> test=B1.MakeMacalgo1(B1.kelet[1]);
		//B1.algo1output(test);
		List<ArrayList<M_S>> test2 = B1.createMS(B1.kelet[5]);
		B1.algo2output(test2);

	}

}
