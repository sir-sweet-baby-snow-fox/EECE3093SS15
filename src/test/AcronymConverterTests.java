package test;

import static org.junit.Assert.*;
import java.io.File;
import indexer.AcronymConverter;
import org.junit.Test;

public class AcronymConverterTests {

	@Test
	public void testConstructor() {
		File acronymListFile = new File("C:/Users/Ricky/Desktop/Acronym_List.txt");
		AcronymConverter acronymConverter = new AcronymConverter(acronymListFile);	
	}
	
	@Test
	public void testConverter() {
		File acronymListFile = new File("C:/Users/Ricky/Desktop/Acronym_List.txt");
		AcronymConverter acronymConverter = new AcronymConverter(acronymListFile);
		assertEquals("Licensed Health Care Professional", acronymConverter.fromAcronym("LHCP"));
		assertEquals("Emergency Responder", acronymConverter.fromAcronym("ER"));
		assertEquals("Laboratory Technician", acronymConverter.fromAcronym("LT"));
		assertEquals("Public Health Agent", acronymConverter.fromAcronym("PHA"));
	}


}
