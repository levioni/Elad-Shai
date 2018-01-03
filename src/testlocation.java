import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

class testlocation {
	
	Location m;
	@Before
	public void init() {
		m=new Location(34.0,32.05,50,"shlomo","20:10");
	}
	

	@Test
	void testequallocation() {
		init();
	//Location m=new Location(34.0,32.05,50,"shlomo","20:10");
	assertThat(m.getLat(), is(34.0));
	}
	
	@Test
	void testtoString() {
		init();
		String expected = "" + "34.0" + "," + "32.05" + "," + "50.0" + "," + "shlomo" + "," + "20:10" + "";
		assertThat(expected, is(m.toString()));
	}

}
