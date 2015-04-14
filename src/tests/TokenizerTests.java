package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import indexer.RequirementsTokenizer;
import indexer.Token;

import org.junit.Test;

public class TokenizerTests {

	@Test
	public void testTokenizeAllSpaces() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The quick brown fox jumped over the lazy dog";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
	
	@Test
	public void testTokenizeAllPeriods() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The.quick.brown.fox.jumped.over.the.lazy.dog.";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
	
	@Test
	public void testTokenizeAllColons() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The:quick:brown:fox:jumped:over:the:lazy:dog:";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
	
	@Test
	public void testTokenizeAllQuestionMarks() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The?quick?brown?fox?jumped?over?the?lazy?dog?";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
	
	@Test
	public void testTokenizeAllCommas() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The,quick,brown,fox,jumped,over,the,lazy,dog,";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
	
	@Test
	public void testTokenizeAllNewLines() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The\nquick\nbrown\nfox\njumped\nover\nthe\nlazy\ndog\n";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
	
	@Test
	public void testTokenizeMultipleNewLines() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The\n\n\n\n\n\n quick\n\n brown\n\n fox\n\n jumped\n\n over\n\n the\n\n lazy\n\n dog\n\n";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
	
	@Test
	public void testTokenizeMultipleSpaces() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The      quick   brown fox   jumped  over                    the lazy    dog   ";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
	
	@Test
	public void testTokenizeMultipleSpacesAndNewLines() {
		RequirementsTokenizer t = new RequirementsTokenizer();
		String stringToTokenize = "The  \n\n\n    quick \n  brown \nfox \n\n  jumped  \nover \n\n               \n    the \n\nlazy  \n  dog   ";
		ArrayList<Token> tokens = t.tokenize(stringToTokenize);
		
		assertEquals(tokens.size(), 9);
		assertEquals(tokens.get(0).getValue(), "The");
		assertEquals(tokens.get(1).getValue(), "quick");
		assertEquals(tokens.get(2).getValue(), "brown");
		assertEquals(tokens.get(3).getValue(), "fox");
		assertEquals(tokens.get(4).getValue(), "jumped");
		assertEquals(tokens.get(5).getValue(), "over");
		assertEquals(tokens.get(6).getValue(), "the");
		assertEquals(tokens.get(7).getValue(), "lazy");
		assertEquals(tokens.get(8).getValue(), "dog");
	}
}
