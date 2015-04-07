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
	 * Displays the list of tokens in a friendly way
	 * @return
	 */
	public String displayTokens() {
		StringBuilder sb = new StringBuilder();
		
		for (Token t: tokens) {
			// Add the value and a space to the string builder
			sb.append(t.getValue());
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a list of the tokens
	 */
	public ArrayList<Token> getTokens() {
		return this.tokens;
	}
		
}
