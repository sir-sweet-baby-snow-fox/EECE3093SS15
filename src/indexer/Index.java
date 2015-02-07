package indexer;

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
