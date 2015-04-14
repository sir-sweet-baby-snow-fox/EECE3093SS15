package tests;

import static org.junit.Assert.*;
import indexer.Stemmer;
import indexer.RequirementsTokenizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class StemmerTests {

	@Test
	public void test() {
		Stemmer stemmer = new Stemmer();
		assertEquals("stem", stemmer.stem("stemming"));
		assertEquals("argu", stemmer.stem("argue"));
	}

}
