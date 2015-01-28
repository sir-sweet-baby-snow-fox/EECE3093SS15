package indexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;

public class AcronymConverter {
	
	private Hashtable<String, String> acronymHashtable;
	
	/**
	 * Constructor function. Takes an input which will be a simple File containing the list
	 * of acronyms and the meaning of the acronym. A pairing of acronym to expanded form is 
	 * generated. Each pair should be on a single line with the following form:
	 * 
	 * LHCP: Liscensed Health Care Professional.
	 * OB/GYN: Obstetrics and Gynaecology.
	 * 
	 * 
	 * @param acronymListFile	The file containing the list of acronyms and their meaniings.
	 */
	public AcronymConverter(File acronymListFile) {
		acronymHashtable = new Hashtable<String, String>();
		
		//User has selected a use case associated with a file name.
		try {
			for (String line : Files.readAllLines(acronymListFile.toPath())) {
				//Remove ending period, if it exists, and split the line on the ": "
				if(line.endsWith(".")) {
					line = line.substring(0, line.length() - 1);
				}
				String[] splitLine = line.split(":");
				
				//Insert acronym and its expanded form into hash table
				if(splitLine.length == 2) {
					//Correctly formattd line
					String acronym = splitLine[0].trim();
					String expandedAcronym = splitLine[1].trim();
					acronymHashtable.put(acronym, expandedAcronym);
				}
			}
		} catch (IOException e1) {
			System.out.println("Error parsing file: " + e1.getMessage());
		}
	}
	
	/**
	 * Returns the restore acronym form of the inputAcronym, or the inputAcronym
	 * restored value can be found in the list.
	 * @param inputAcronym	Acronym to be restored
	 * @param acronymMap 	The mapping to be used to restore the acronym
	 * @return				The restored version of the acronym, or the input if no mapping is found.
	 */
	public String fromAcronym(String inputAcronym) {
		if(acronymHashtable.containsKey(inputAcronym)) {
			return acronymHashtable.get(inputAcronym);
		} else {
			return inputAcronym;
		}
	}
	
}
