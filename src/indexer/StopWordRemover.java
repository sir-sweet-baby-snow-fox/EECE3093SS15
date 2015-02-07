package indexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class StopWordRemover {
	
	String[] stopWordList;
	
	/**
	 * Path to a text file that will set up the list of stop words.
	 * File should have a list of comma delimited stop words.
	 * @param stopWordFilePath
	 */
	public StopWordRemover(String stopWordFilePath) {
		StringBuilder sb = new StringBuilder();
		try {
			for (String line : Files.readAllLines(Paths.get(stopWordFilePath))) {
				// append to the StringBuilder
				sb.append(line);
			}
		} catch(IOException io) {
			System.out.println("Error setting up stop word remover\n");
			System.out.println(io.getMessage());
		}

		stopWordList = sb.toString().split(",");
		
		// sort the array to prepare it for binary search
		Arrays.sort(stopWordList);
	}
	
	/**
	 * Remove the stop words that exist in the stop words list from the strings in s.
	 * @param s
	 * @return
	 */
	public String[] RemoveStopWords(String[] s){
		
		// initialize empty array list
		ArrayList<String> cleanedParts = new ArrayList<String>();
		
		// run a binary search for each token to see if it should be removed
		for (String part : s){
			int index = Arrays.binarySearch(stopWordList, part.toLowerCase());
			index = index>=0 ? index : -1;
			if (index == -1) {
				cleanedParts.add(part);
			}
		}
		
		String[] retArray = new String[cleanedParts.size()];
		return cleanedParts.toArray(retArray);
	}
	
}
