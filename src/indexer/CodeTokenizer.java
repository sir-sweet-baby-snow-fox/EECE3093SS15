package indexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class CodeTokenizer {
	private ArrayList<String> tokens;
	
	public CodeTokenizer() { tokens = new ArrayList<String>(); }
	
	public String tokenizeCode(String code, String methodName){
		// get code and method name for tokenizing
		// index each line one at a time
		BufferedReader br = new BufferedReader(new StringReader(code));
		String line = null;
		try {
			while ( (line=br.readLine()) != null) {
				// tokenize the line
				tokenizeLine(line);
				
			}
		} catch (IOException e) {
			// TODO: Actually handle the error
			e.printStackTrace();
			return "";
		}
		
		
		String listString = "";

		for (String s : tokens)
		{
		    listString += s + " ";
		}

		return listString;
	}
	
	private void tokenizeLine(String line) {
		// check to see if there are any end of line comments
		int eolCommentIndex = line.indexOf("//");
		int slashStarCommentIndex = line.indexOf("/*");
		
		String commentEntity = null;
		
		// get the final line value
		if (eolCommentIndex > -1 && slashStarCommentIndex > -1) { // both comments found
			int minIndex = Math.min(eolCommentIndex, slashStarCommentIndex);
			commentEntity = line.substring(minIndex);
			line = line.substring(0,  minIndex);
		} else if (eolCommentIndex > -1) {
			commentEntity = line.substring(eolCommentIndex);
			line = line.substring(0, eolCommentIndex);
		} else if (slashStarCommentIndex > -1) {
			commentEntity = line.substring(slashStarCommentIndex);
			line = line.substring(0, slashStarCommentIndex);
		}
		
		tokens.add(line);
		
		if (commentEntity != null) 
			tokens.add(commentEntity);
	}
}
