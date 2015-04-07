package interfaces;

import indexer.Token;

import java.util.ArrayList;

/**
 * Tokenizer.java
 * Interface class that outlines methods for the different types of tokenizer
 * 
 * @author Brian Adams
 * @since 03-31-2015
 */

public interface Tokenizer {
	
	// method to tokenize a given string
	public ArrayList<Token> tokenize(String s);
	
	// method to properly display the tokens
	public String displayTokens();
	
	// method to get the tokens
	public ArrayList<Token> getTokens();
}
