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
		Stemmer stemmer = new Stemmer();
		Tokenizer tokenizer = new Tokenizer();
		
		try {
			for (String line : Files.readAllLines(Paths.get("C:/Users/Ricky/Desktop/Acronym_List.txt"))) {
				//Read in the line, get tokens
				String[] tokens = tokenizer.TokenizeString(line);
				
				//Attempt to stem each token
				for(int i = 0; i < tokens.length; i++) {
					System.out.println(stemmer.stem(tokens[i]));
				}
				
			}
		} catch (IOException e1) {
			System.out.println("Error parsing file: " + e1.getMessage());
		}
	}

}
