package test;

import static org.junit.Assert.*;
import indexer.Stemmer;
import indexer.Tokenizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class StemmerTests {

	@Test
	public void test() {
		/** Test program for demonstrating the Stemmer.  It reads text from a
		 * a list of files, stems each word, and writes the result to standard
		 * output. Note that the word stemmed is expected to be in lower case:
		 * forcing lower case must be done outside the Stemmer class.
		 * Usage: Stemmer file-name file-name ...
		 */
		Stemmer stemmer = new Stemmer();
		Tokenizer tokenizer = new Tokenizer();
		//User has selected a use case associated with a file name.
		try {
			for (String line : Files.readAllLines(Paths.get("C:/Users/Ricky/Desktop/Acronym_List.txt"))) {
				String[] tokens = tokenizer.TokenizeString(line);
				for(int i = 0; i < tokens.length; i++) {
					System.out.println(stemmer.stem(tokens[i]));
				}
				
			}
		} catch (IOException e1) {
			System.out.println("Error parsing file: " + e1.getMessage());
		}
	}

}
