package indexer;

/**
 * @author Ricky
 *
 *	This class holds all the information that is to be used by the Indexer class.
 *	The purpose of this class is to encapsulate the parameters sent to indexer in 
 *	a single structure.
 *
 *	Holds strings for:
 *		-resource directory path
 *		-acronym file path
 *		-stop words file path
 *		-directory for stored indices
 *
 *	Holds booleans for:
 *		-Whether or not to tokenize resources
 *		-Whether or not to stem resources
 */
public class IndexerInfo {
	public String resourceDirectoryPath = "";
	public String acronymFilePath = "";
	public String stopWordsFilePath = "";
	public String storeIndicesDir = "";
	
	public boolean doTokenize = false;
	public boolean doStem = false;
};