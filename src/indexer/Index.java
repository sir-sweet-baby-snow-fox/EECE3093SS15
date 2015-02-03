package indexer;

public class Index {
	private String[] tokens;

	Index(String[] processedTokens) {
		this.tokens = processedTokens;
	}
	
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
