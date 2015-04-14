package indexer;

import java.util.ArrayList;

/**
 * @date 4/7/2015
 * @author Ricky
 * @description
 * 	This class holds the information about a requirement index. A list of strings (which are the processed tokens)
 * are given to the class to hold onto. This list together represents the index.
 */
public class Index {
	private ArrayList<Token> tokens;

	/**
	 * Create an index given an array of strings (tokens)
	 * @param processedTokens
	 */
	Index(ArrayList<Token> processedTokens) {
		this.tokens = processedTokens;
	}
	
	/**
	 * Returns all tokens in a string, separated by space.
	 * Useful for printing the index.
	 * @return
	 */
	public String getTokensAsString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < tokens.size(); i++) {
			s.append(tokens.get(i));
			if(i < tokens.size() - 1) {
				s.append(" ");
			}
		}
		return s.toString();
	}
}
