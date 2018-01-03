import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class WriteToKMLTest {
	/**
	 * To test the files we use the files in the OutPut directory
	 * we added another library to help us compare the kml files
	 * https://stackoverflow.com/questions/27379059/determine-if-two-files-store-the-same-content
	 */
	
	List<ArrayList<String>> inputCSV;//for inputheCSVfile maybe use the actual file?
	List<ArrayList<String>> FilterListTest;//for filterthelist
	File KMLOutputTest; //for writethekmlfileTest
	List<ArrayList<String>> FilteredByTest;//for filterbyTest
	WriteToKML input;
	
	
	@Before
	public void init() {
		input=new WriteToKML();
		inputCSV=input.inputheCSVfile();
		FilterListTest=input.filterthelist(inputCSV, "Lenovo", 3);
		KMLOutputTest = new File("C:\\Users\\computer\\Desktop\\OutPut\\þþKMLoutput.kml");
		
	}

	@Test
	void inputheCSVfileTest() {
		init();
		List<ArrayList<String>> inputest=input.inputheCSVfile();
		assertEquals(inputest, inputCSV);	
	}
	
	@Test
	void filterthelistTest() {
		init();
		List<ArrayList<String>> inputest=input.inputheCSVfile();
		List<ArrayList<String>> filtertest=input.filterthelist(inputest, "Lenovo", 3);
		assertEquals(filtertest,FilterListTest);
	}
	
	@Test
	void writethekmlfileTest() throws IOException {
		init();
		List<ArrayList<String>> inputest=input.inputheCSVfile();
		List<ArrayList<String>> filtertest=input.filterthelist(inputest, "Lenovo", 3);
		input.writethekmlfile(filtertest);
		File Tested = new File("C:\\Users\\computer\\Desktop\\OutPut\\KMLoutputID.kml");
		assertThat(FileUtils.sizeOf(Tested),is(FileUtils.sizeOf(KMLOutputTest)));
	}
	 
}
