package indexer;

/**
 * @date 4/7/2015
 * @author Ricky
 * @description
 * 	This class holds the information about a requirement index. A list of strings (which are the processed tokens)
 * are given to the class to hold onto. This list together represents the index.
 */
public class Index {
	private String[] tokens;

	/**
	 * Create an index given an array of strings (tokens)
	 * @param processedTokens
	 */
	Index(String[] processedTokens) {
		this.tokens = processedTokens;
	}
	
	/**
	 * Returns all tokens in a string, separated by space.
	 * Useful for printing the index.
	 * @return
	 */
	public String getTokensAsString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < tokens.length; i++) {
			s.append(tokens[i]);
			if(i < tokens.length - 1) {
				s.append(" ");
			}
		}
		return s.toString();
	}
}
