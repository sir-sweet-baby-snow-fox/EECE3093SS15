package indexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * @param acronymListFile	The file containing the list of acronyms and their meanings.
	 */
	public AcronymConverter(File acronymListFile) {
		setupHashtable(acronymListFile);
	}
	
	/**
	 * Returns the restore acronym form of the inputAcronym, or the inputAcronym
	 * restored value can be found in the list. If hash table was not set up correctly,
	 * just returns the input.
	 * 
	 * @param inputAcronym	Acronym to be restored
	 * @param acronymMap 	The mapping to be used to restore the acronym
	 * @return				The restored version of the acronym, or the input if no mapping is found.
	 */
	public String fromAcronym(String inputAcronym) {
		if(acronymHashtable != null && acronymHashtable.containsKey(inputAcronym)) {
			return acronymHashtable.get(inputAcronym);
		} else {
			return inputAcronym;
		}
	}
	
	public String[] restoreAcronyms(String[] tokens) {
		// initialize empty array list
		ArrayList<String> cleanedParts = new ArrayList<String>();
		
		// run a binary search for each token to see if it should be removed
		for (String token : tokens){
			cleanedParts.add(fromAcronym(token));
		}
		
		String[] retArray = new String[cleanedParts.size()];
		return cleanedParts.toArray(retArray);
		
	}
	
	/**
	 * Set up the hash table given an input file. See constructor details for
	 * file scheme.
	 * 
	 * @param acronymListFile	The file containing the list of acronyms and their meanings.
	 */
	public void setupHashtable(File acronymListFile) {
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
				} else {
					//Make sure users can't use the fromAcronym() with incorrectly set up table
					acronymHashtable = null; 
					throw new Throwable("Badly formatted acronym list file");
				}
			}
		} catch (IOException e1) {
			System.out.println("Error parsing file: " + e1.getMessage());
		} catch (Throwable s) {
			System.out.println(s.getMessage());
		}
	}
	
}