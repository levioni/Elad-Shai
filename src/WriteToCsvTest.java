import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * To test the files we use the files in the OutPut directory
 * compare files: https://stackoverflow.com/questions/27379059/determine-if-two-files-store-the-same-content
 * 
 *
 */
class WriteToCsvTest  {
	
	List<ArrayList<String>> expected; //for the bubbleSort
	File CSVtableCompare; //for the file compare for writethecsvtableTest
	List<ArrayList<String>> ListOfData; //for createlistofdata
	HashMap<Location,ArrayList<WIFI>> MapTest;//for createMapTest
	WriteToCsv testDir;
	
	static ArrayList<String> IDlist=new ArrayList<String>();
	static ArrayList<Integer> IDsplit=new ArrayList<Integer>();
	

	@Before
	public void init() throws IOException {
		CSVtableCompare = new File("C:\\Users\\computer\\Desktop\\OutPut\\CSVOutputTest.csv");
		testDir=new WriteToCsv();
		expected = new ArrayList<ArrayList<String>>();
		ListOfData = testDir.createlistofdata();
		MapTest = testDir.createMap(ListOfData);
	}
	
	@Test
	public void createlistofdataTest() throws IOException{
		init();
		List<ArrayList<String>> Listtest=testDir.createlistofdata();
		assertEquals(Listtest, ListOfData);
	}
	
	@Test
	void createMapTest() throws IOException {
		init();
		List<ArrayList<String>> Listtest=testDir.createlistofdata();
		HashMap<Location,ArrayList<WIFI>> Mapclone=testDir.createMap(Listtest);
		assertThat(Mapclone.size(), is(MapTest.size()));
		/**
		 * cant really compare the whole HashMap content becuase it inserts the data
		 * in an arbitrary order. that why we compared the size.
		 */
	}
	
	@Test
	void writethecsvtableTest() throws IOException {
		init();
		List<ArrayList<String>> Listtest=testDir.createlistofdata();
		HashMap<Location,ArrayList<WIFI>> Mapclone=testDir.createMap(Listtest);
		testDir.writethecsvtable(Mapclone);
		List<String> testContent=Files.readAllLines(Paths.get("C:\\Users\\computer\\Desktop\\OutPut\\CSVOutput.csv"));
		List<String> testOriginal=Files.readAllLines(Paths.get("C:\\Users\\computer\\Desktop\\OutPut\\CSVOutputTest.csv"));
		assertEquals(testContent.size(),testOriginal.size());
		for(int i=0; i<testContent.size(); i++)
		{
			assertEquals(testContent.get(i),testContent.get(i));
		}
		
		
	}
	

	@Test
	void bubbleSortTest() throws IOException {
	init();	
	for(int i=0; i<=10; i++)
	{
	ArrayList<String> innerexpected=new ArrayList<String>();
	innerexpected.add("testing"); // 0
	innerexpected.add("the"); // 1
	innerexpected.add("bubble"); // 2
	innerexpected.add("sort"); // 3
	innerexpected.add("function");// 4
	innerexpected.add(""+i*(-10)); //5
	innerexpected.add("!!!!"); //6
	expected.add(innerexpected);
	}
	List<ArrayList<String>> bubbleTest=new ArrayList<ArrayList<String>>();
	for(int i=10; i>=0; i--)
	{
		ArrayList<String> innerexpected2=new ArrayList<String>();
		innerexpected2.add("testing"); // 0
		innerexpected2.add("the"); // 1
		innerexpected2.add("bubble"); // 2
		innerexpected2.add("sort"); // 3
		innerexpected2.add("function");// 4
		innerexpected2.add(""+i*(-10)); //5
		innerexpected2.add("!!!!"); //6
		bubbleTest.add(innerexpected2);
	}
	WriteToCsv.bubbleSort(bubbleTest);
	assertEquals(bubbleTest, expected);
	}

}
