package indexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class CodeTokenizer {
	private ArrayList<String> tokens;
	private boolean inComment = false;
	
	public CodeTokenizer() { tokens = new ArrayList<String>(); }
	
	public String tokenizeCode(String code, String methodName){
		// get code and method name for tokenizing
		// index each line one at a time
		BufferedReader br = new BufferedReader(new StringReader(code));
		String line = null;
		try {
			while ( (line=br.readLine()) != null) {
				// tokenize the line
				tokens.addAll(tokenizeLine(line));
				tokens.add("\n");
				
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
	
	private ArrayList<String> tokenizeLine(String line) {
		ArrayList<String> newTokens = new ArrayList<String>();
		
		// check to see if there are any end of line comments
		int eolCommentIndex = line.indexOf("//");
		int slashStarCommentIndex = line.indexOf("/*");
		
		String commentEntity = null;
		
		// get the final line value
		if (inComment) {
			newTokens.addAll(handleMultiLineComment(line));
		}
		else if (eolCommentIndex > -1 && slashStarCommentIndex > -1) { // both comments found
			int minIndex = Math.min(eolCommentIndex, slashStarCommentIndex);
			commentEntity = line.substring(minIndex);
			line = line.substring(0,  minIndex);
			
			newTokens.add(line);
			newTokens.add(commentEntity);
		} else if (eolCommentIndex > -1) {
			commentEntity = line.substring(eolCommentIndex);
			line = line.substring(0, eolCommentIndex);
			
			newTokens.add(line);
			newTokens.add(commentEntity);
		} else if (slashStarCommentIndex > -1) {
			newTokens.addAll(handleMultiLineComment(line));
		} else {
			// no comments found
			newTokens.add(line);
		}
		
		return newTokens;
	}
	
	private ArrayList<String> handleMultiLineComment(String line) {
		ArrayList<String> newTokens = new ArrayList<String>();
		
		int slashStarCommentIndex = line.indexOf("/*");
		
		
		if (slashStarCommentIndex == -1) {
			inComment = false;
			if (line.trim().length() > 0)
				newTokens.add(line);
			return newTokens;
		}
		
		inComment = true;
		
		// save the code before the comment as a separate string 
		// save the rest as a new string to be sent recursively
		String newLine = line.substring(slashStarCommentIndex);
		line = line.substring(0, slashStarCommentIndex);
		
		if (line != "")
			newTokens.add(line);
		
		int starSlashEnd = newLine.indexOf("*/");
		if (starSlashEnd > -1) { /* End of slash star comment was found on this line so any tokens after it
									need to be added */
			inComment = false;
			String newComment = newLine.substring(0, starSlashEnd+2);
			newLine = newLine.substring(starSlashEnd+2);
			newTokens.add(newComment);
		} else {
			newTokens.add(newLine);
			newLine = "";
		}
		
		if (newLine != "")
			newTokens.addAll(handleMultiLineComment(newLine));
		
		return newTokens;
		
	}
}
