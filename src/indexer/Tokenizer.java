package indexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tokenizer {
	
	public String[] TokenizeString(String s) {
		String[] parts = s.split("[^a-zA-Z\\d]+");
		return parts;
	}
		
}
