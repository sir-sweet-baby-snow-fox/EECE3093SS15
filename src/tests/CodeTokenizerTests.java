package tests;

import static org.junit.Assert.*;
import indexer.CodeTokenizer;

import org.junit.Test;

public class CodeTokenizerTests {
	@Test
	public void testTokenizeEOLComment() {
		String code = "String test = \"A string\"; // This is a test comment";
		
		CodeTokenizer ct = new CodeTokenizer();
		
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "String test = \"A string\";  // This is a test comment \n ");
	}
	
	@Test
	public void testTokenizeEOLCommentMultipleLines() {
		String code = "String test = \"A string\";\n String test2 = \"A string 2\" // This is a test comment";
		
		CodeTokenizer ct = new CodeTokenizer();
		
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "String test = \"A string\"; \n  String test2 = \"A string 2\"  // This is a test comment \n ");
	}
	
	@Test
	public void testTokenizeEOLCommentSyntaxError() {
		String code = "int a = 0 // This is a test comment";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "int a = 0  // This is a test comment \n ");
	}
	
	@Test
	public void testTokenizeSlashStartCommentSingleLine() {
		String code = "int a = 0; /* Test Comment */";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "int a = 0;  /* Test Comment */ \n ");
	}
	
	@Test
	public void testTokenizeSlashStartCommentSingleLineCodeAfter() {
		String code = "int a = 0; /* Test Comment */ int b = 0;";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "int a = 0;  /* Test Comment */  int b = 0; \n ");
	}
	
	@Test
	public void testTokenizeMultiComment() {
		String code = "int a = 0; /*\n This is\n a test\n*/";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "int a = 0;  /* \n  This is \n  a test \n */ \n ");
	}
	
	@Test
	public void testTokenizeMultiCommentCodeAfter() {
		String code = "int a = 0; /* start\nof a comment */ int b = 0;";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "int a = 0;  /* start \n of a comment */  int b = 0; \n ");
	}
	
	@Test
	public void testOnlyMultiLineComment() {
		String code = "int a = 4; /* comment dude /* nested comment dude // */";
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code, "test");
		
		assertEquals(retVal, "int a = 4;  /* comment dude /* nested comment dude // */ \n ");
		
	}
}



