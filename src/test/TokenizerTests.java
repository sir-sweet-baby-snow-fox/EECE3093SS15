package test;

import static org.junit.Assert.*;
import indexer.Tokenizer;

import org.junit.Test;

public class TokenizerTests {

	@Test
	public void testTokenizeAllSpaces() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The quick brown fox jumped over the lazy dog";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
	
	@Test
	public void testTokenizeAllPeriods() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The.quick.brown.fox.jumped.over.the.lazy.dog.";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
	
	@Test
	public void testTokenizeAllColons() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The:quick:brown:fox:jumped:over:the:lazy:dog:";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
	
	@Test
	public void testTokenizeAllQuestionMarks() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The?quick?brown?fox?jumped?over?the?lazy?dog?";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
	
	@Test
	public void testTokenizeAllCommas() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The,quick,brown,fox,jumped,over,the,lazy,dog,";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
	
	@Test
	public void testTokenizeAllNewLines() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The\nquick\nbrown\nfox\njumped\nover\nthe\nlazy\ndog\n";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
	
	@Test
	public void testTokenizeMultipleNewLines() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The\n\n\n\n\n\n quick\n\n brown\n\n fox\n\n jumped\n\n over\n\n the\n\n lazy\n\n dog\n\n";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
	
	@Test
	public void testTokenizeMultipleSpaces() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The      quick   brown fox   jumped  over                    the lazy    dog   ";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
	
	@Test
	public void testTokenizeMultipleSpacesAndNewLines() {
		Tokenizer t = new Tokenizer();
		String stringToTokenize = "The  \n\n\n    quick \n  brown \nfox \n\n  jumped  \nover \n\n               \n    the \n\nlazy  \n  dog   ";
		String[] tokens = t.TokenizeString(stringToTokenize);
		
		assertEquals(tokens.length, 9);
		assertEquals(tokens[0], "The");
		assertEquals(tokens[1], "quick");
		assertEquals(tokens[2], "brown");
		assertEquals(tokens[3], "fox");
		assertEquals(tokens[4], "jumped");
		assertEquals(tokens[5], "over");
		assertEquals(tokens[6], "the");
		assertEquals(tokens[7], "lazy");
		assertEquals(tokens[8], "dog");
	}
}
