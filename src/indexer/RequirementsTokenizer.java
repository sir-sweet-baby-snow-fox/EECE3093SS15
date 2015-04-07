package indexer;

import java.util.ArrayList;

import interfaces.Tokenizer;

/**
 * RequirementsTokenizer.java
 * 
 * RequirementsTokenizer that implements the Tokenizer interface to properly tokenize the requirements text file.
 * @author Brian Adams
 *
 */
public class RequirementsTokenizer implements Tokenizer {
	ArrayList<Token> tokens;
	
	/**
	 * Default constructor
	 * @return
	 */
	public RequirementsTokenizer() {
		tokens = new ArrayList<Token>();
	}
	
	/**
	 * Takes a string and returns a list of tokens from that string
	 * @param s string of text to tokenize
	 */
	public ArrayList<Token> tokenize(String s) {
		String[] parts = s.split("[^a-zA-Z\\d]+");
		
		for (String stringPart : parts) {
			// add the token to the list
			tokens.add(new Token(TokenType.REQUIREMENT, stringPart));
		}
		
		return tokens;
	}
	
	/**
	 * Returns a list of the tokens
	 */
	public ArrayList<Token> getTokens() {
		return this.tokens;
	}
	
	/**
	 * Updates the list of tokens 
	 */
	public void updateTokens(ArrayList<Token> t) {
		this.tokens = t;
	}
		
}
