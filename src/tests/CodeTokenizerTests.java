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
		
		assertEquals(retVal, "String test = \"A string\";  // This is a test comment ");
	}
	
	@Test
	public void testTokenizeEOLCommentMultipleLines() {
		String code = "String test = \"A string\";\n String test2 = \"A string 2\" // This is a test comment";
		
		CodeTokenizer ct = new CodeTokenizer();
		
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "String test = \"A string\";  String test2 = \"A string 2\"  // This is a test comment ");
	}
	
	@Test
	public void testTokenizeEOLCommentSyntaxError() {
		String code = "int a = 0 // This is a test comment";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code, "Test");
		
		assertEquals(retVal, "int a = 0  // This is a test comment ");
	}
	
	@Test
	public void testTokenizeMultiComment() {
		String code = "int a = 0; /*\n This is\n a test\n*/";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code, "Test");
	}
}
