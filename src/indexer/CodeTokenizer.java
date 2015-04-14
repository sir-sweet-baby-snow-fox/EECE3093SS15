package indexer;

import interfaces.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * CodeTokenizer.java
 * 
 * Code tokenizer, implements the Tokenizer interface and tokenizes the various IMethods passed into it
 * @author Brian Adams
 *
 */
public class CodeTokenizer implements Tokenizer {
	private ArrayList<Token> tokens;
	private boolean inComment = false;
	
	/**
	 * Default constructor
	 */
	public CodeTokenizer() { tokens = new ArrayList<Token>(); }
	
	/**
	 * Tokenizes the string of code that is passed into the method
	 * @param code String of source code to tokenize
	 */
	public ArrayList<Token> tokenize(String code){
		// get code and method name for tokenizing
		// index each line one at a time
		BufferedReader br = new BufferedReader(new StringReader(code));
		String line = null;
		try {
			while ( (line=br.readLine()) != null) {
				// tokenize the line
				tokens.addAll(tokenizeLine(line));
				tokens.add(new Token(TokenType.NEWLINE, "\n"));
				
			}
		} catch (IOException e) {
			// TODO: Actually handle the error
			e.printStackTrace();
		}
		
		ArrayList <Token> finalList = new ArrayList<Token>();
		
		for(Token c : tokens) {
			if(c.getType() == TokenType.CODE){
				finalList.add(indexCode(c));
			}
			else {
				finalList.add(c);
			}
		}
		
		// overwrite this.tokens with the final list of tokens
		tokens = finalList;
		
		return tokens;
	}
	
	/**
	 * Splits the code based on the desired functionality as specified in the lab document.
	 * @param ct Token of a piece of code
	 * @return new Token with the code split on the proper patterns.
	 */
	private Token indexCode(Token ct) {
		String[] newVal = ct.getValue().split("(?=[A-Z][a-z])+|[^A-Za-z\\d]+");
		
		Token retCt = new Token(ct.getType(), convertArrayToString(newVal));
		return retCt;
	}
	
	/**
	 * Converts an array of strings to a single string
	 * @param val String array of code
	 * @return String, composed of each string in the array
	 */
	private String convertArrayToString(String[] val) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < val.length; i++) {
			sb.append(val[i] + " ");
		}
		
		return sb.toString();
	}
	
	/**
	 * Procedure to tokenize a given line of the source code
	 * @param line String line of the source code
	 * @return
	 */
	private ArrayList<Token> tokenizeLine(String line) {
		ArrayList<Token> newTokens = new ArrayList<Token>();
		
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
			
			newTokens.add(new Token(TokenType.CODE, line));
			newTokens.add(new Token(TokenType.COMMENT, commentEntity));
		} else if (eolCommentIndex > -1) {
			commentEntity = line.substring(eolCommentIndex);
			line = line.substring(0, eolCommentIndex);
			
			newTokens.add(new Token(TokenType.CODE, line));
			newTokens.add(new Token(TokenType.COMMENT, commentEntity));
		} else if (slashStarCommentIndex > -1) {
			newTokens.addAll(handleMultiLineComment(line));
		} else {
			// no comments found
			newTokens.add(new Token(TokenType.CODE, line));
		}
		
		return newTokens;
	}
	
	private ArrayList<Token> handleMultiLineComment(String line) {
		ArrayList<Token> newTokens = new ArrayList<Token>();
		
		int slashStarCommentIndex = line.indexOf("/*");
		
		
		if (slashStarCommentIndex == -1 &&  !inComment) {
			if (line.trim().length() > 0)
				newTokens.add(new Token(TokenType.CODE, line));
			return newTokens;
		}
		
		// save the code before the comment as a separate string 
		// save the rest as a new string to be sent recursively
		String newLine = "";
		if (slashStarCommentIndex > -1) {
			newLine = line.substring(slashStarCommentIndex);
			line = line.substring(0, slashStarCommentIndex);
		} else {
			newLine = line;
			line = "";
		}
		
		if (line != "")
			if (inComment)
				newTokens.add(new Token(TokenType.COMMENT, line));
			else
				newTokens.add(new Token(TokenType.CODE, line));
		
		inComment = true;
		
		int starSlashEnd = newLine.indexOf("*/");
		if (starSlashEnd > -1) { /* End of slash star comment was found on this line so any tokens after it
									need to be added */
			inComment = false;
			String newComment = newLine.substring(0, starSlashEnd+2);
			newLine = newLine.substring(starSlashEnd+2);
			newTokens.add(new Token(TokenType.COMMENT, newComment));
		} else {
			newTokens.add(new Token(TokenType.COMMENT, newLine));
			newLine = "";
		}
		
		if (newLine != "")
			newTokens.addAll(handleMultiLineComment(newLine));
		
		return newTokens;
		
	}
	
	public ArrayList<Token> getTokens() {
		return this.tokens;
	}
	
	public String getTokensAsString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < this.tokens.size(); i++) {
			String postfix = " ";
			Token t = this.tokens.get(i);
			if (i == this.tokens.size() -1 || t.getValue() == "\n");
				postfix = "";
			sb.append(this.tokens.get(i).getValue() + postfix);
		}
		return sb.toString();
	}
}
