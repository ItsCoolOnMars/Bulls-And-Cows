import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class StrToArrTest {

	@Test
	public void test() {
		int[] i = {1,2,3,4,5,6,7,8,9};
		int[] a = {2,5,6,8,4};
		int[] b = {9,8,7,6,5,4,3,2,1};
		assertEquals("", Arrays.toString(i) , Arrays.toString(BullsAndCows.stringToArray("123456789")));
		assertEquals("", Arrays.toString(a) , Arrays.toString(BullsAndCows.stringToArray("25684")));
		assertEquals("", Arrays.toString(b) , Arrays.toString(BullsAndCows.stringToArray("987654321")));
	}

}
