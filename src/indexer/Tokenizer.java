package indexer;

public class Tokenizer {
	public String[] TokenizeString(String s) {
		String[] parts = s.split("[^a-zA-Z\\d\\s\n]");
		return parts;
	}
}
