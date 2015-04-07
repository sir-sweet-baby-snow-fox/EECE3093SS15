package indexer;

enum TokenType {
	CODE, COMMENT, NEWLINE, REQUIREMENT
}

public class Token {
	private TokenType type;
	private String value;
	
	public Token(TokenType t, String v) {
		this.type = t;
		this.value = v;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public TokenType getType() {
		return this.type;
	}
	
	public String getValueAsLowercase() {
		return this.value.toLowerCase();
	}
}