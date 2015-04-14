package indexer;

import java.util.ArrayList;

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
