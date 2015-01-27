package indexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tokenizer {
	
	public String[] TokenizeString(String s) {
		String[] parts = s.split("[^a-zA-Z\\d]");
		return parts;
	}
	
	public String[] RemoveStopWords(String fileLocation, String[] s) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (String line : Files.readAllLines(Paths.get(fileLocation))) {
			// append to the StringBuilder
			sb.append(line);
		}
		String[] stopWordList = sb.toString().split(",");
		
		// sort the array to prepare it for binary search
		Arrays.sort(stopWordList);
		
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
