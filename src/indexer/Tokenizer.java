package indexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tokenizer {
	public String[] TokenizeString(String s) {
		String[] parts = s.split("[^a-zA-Z\\d]");
		for(int i=0; i < parts.length; i++) {
			  parts[i] = parts[i].toLowerCase();
			}
		return parts;
	}
	
	public String[] RemoveStopWords(String fileLocation, String[] s) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (String line : Files.readAllLines(Paths.get(fileLocation))) {
			// append to the StringBuilder
			sb.append(line);
		}
		String[] stopWords = sb.toString().split(",");
		
		// convert both arrays to sets
		Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopWords));
		Set<String> tokenSet = new HashSet<String>(Arrays.asList(s));
		
		// subtract the stopWordSet from the tokenSet to find the elements that are only in tokenSet
		tokenSet.removeAll(stopWordSet);
		
		// convert back to string array and return
		return tokenSet.toArray(new String[tokenSet.size()]);
	}
	
	
}
