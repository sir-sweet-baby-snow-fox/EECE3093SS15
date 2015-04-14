package indexer;

public class Tokenizer {
	
	/**
	 * Split string into an array of strings.
	 * @param s
	 * @return
	 */
	public String[] TokenizeString(String s) {
		String[] parts = s.split("[^a-zA-Z\\d]+");
		return parts;
	}
		
}
