package tests;

import static org.junit.Assert.*;
import indexer.Stemmer;
import org.junit.Test;

public class StemmerTests {

	@Test
	public void test() {
		Stemmer stemmer = new Stemmer();
		assertEquals("stem", stemmer.stem("stemming"));
		assertEquals("argu", stemmer.stem("argue"));
	}

}
