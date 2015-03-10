package tests;

import static org.junit.Assert.*;
import indexer.CodeTokenizer;

import org.junit.Test;

public class CodeTokenizerTests {
	@Test
	public void testTokenizeEOLComment() {
		String code = "String test = \"A string\"; // This is a test comment";
		
		CodeTokenizer ct = new CodeTokenizer();
		
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "String test A string // This is a test comment \n");
	}
	
	@Test
	public void testTokenizeEOLCommentMultipleLines() {
		String code = "String test = \"A string\";\nString test2 = \"A string 2\" // This is a test comment";
		
		CodeTokenizer ct = new CodeTokenizer();
		
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "String test A string \nString test2 A string 2 // This is a test comment \n");
	}
	
	@Test
	public void testTokenizeEOLCommentSyntaxError() {
		String code = "int a = 0 // This is a test comment";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int a 0 // This is a test comment \n");
	}
	
	@Test
	public void testTokenizeSlashStartCommentSingleLine() {
		String code = "int a = 0; /* Test Comment */";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int a 0 /* Test Comment */ \n");
	}
	
	@Test
	public void testTokenizeSlashStartCommentSingleLineCodeAfter() {
		String code = "int a = 0; /* Test Comment */ int b = 0;";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int a 0 /* Test Comment */  int b 0 \n");
	}
	
	@Test
	public void testTokenizeMultiComment() {
		String code = "int a = 0; /*\nThis is\na test\n*/";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int a 0 /* \nThis is \na test \n*/ \n");
	}
	
	@Test
	public void testTokenizeMultiCommentCodeAfter() {
		String code = "int a = 0; /* start\nof a comment */ int b = 0;";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);

		
		assertEquals(retVal, "int a 0 /* start \nof a comment */  int b 0 \n");
	}
	
	@Test
	public void testOnlyMultiLineComment() {
		String code = "int a = 4; /* comment dude /* nested comment dude // */";
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int a 4 /* comment dude /* nested comment dude // */ \n");
		
	}
	
	@Test
	public void testCommentWithinStringFail() {
		String code = "int a = 4; String brian = \"not a comment /* a comment */\"";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int a 4  String brian not a comment /* a comment */  \n");
	}
	
	@Test
	public void testCamelCaseSplit() {
		String code = "int testCase = 0;";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int test Case 0 \n");
	}
	
	@Test
	public void  testCamelCaseSplitComment() {
		String code = "int testCase = 0; // This isA test ofCamelCaseString";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int test Case 0 // This isA test ofCamelCaseString \n");
	}
	
	@Test
	public void testDoubleCase() {
		String code = "IHTTPConnection testCase = 0;";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "IHTTP Connection test Case 0 \n");
	}
	
	@Test
	public void testDoubleCaseTwo() {
		String code = "ITest testCase = 0;";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "I Test test Case 0 \n");
	}
	
	@Test
	public void testCamelCaseDoubleLineComment() {
		String code = "int testCase = 0; /* thisIs\naNew commentLine */";
		
		CodeTokenizer ct = new CodeTokenizer();
		String retVal = ct.tokenizeCode(code);
		
		assertEquals(retVal, "int test Case 0 /* thisIs \naNew commentLine */ \n");
	}
}



