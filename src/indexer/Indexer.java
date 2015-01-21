package indexer;

public class Indexer {
	public String[] IndexString(String s) {
		String[] parts = s.split("[^a-zA-Z\\d\\s]");
		return parts;
	}
}
