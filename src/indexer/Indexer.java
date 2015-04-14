package indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.ui.PlatformUI;

import tracing.views.RequirementsIndicesView;

/**
 * @date 4/7/2015
 * @author Ricky Rossi
 * @description
 * 	This class is used to index each requirement. It is used as a sort of utility class; it holds all
 * the classes necessary to properly process the requirements in the file that is passed. It will process
 * the requirements, and keep the indexed form in a list of Index classes.
 *
 */
public class Indexer {
	private AcronymConverter acronymConverter = null;
	private StopWordRemover stopWordRemover = null;
	private Stemmer stemmer = null;
	private ArrayList<Index> indices = null;
	private double indexDurationTime = 0;
	
	public static final double NANOSEC_SEC_CONVERT = 1000000000.0;

	/**
	 * @param info
	 * @throws IOException 
	 */
	public Indexer(IndexerInfo info)  {
		double indexStartTime = System.nanoTime();
		
		indices = new ArrayList<Index>();
		
		//Set up acronymConvert (if requested)
		if(!info.acronymFilePath.isEmpty()) {
			File acronymFile = new File(info.acronymFilePath);
			acronymConverter = new AcronymConverter(acronymFile);
		}
		
		//Set up stopWordRemover (if requested)
		if(!info.stopWordsFilePath.isEmpty()) {
			stopWordRemover = new StopWordRemover(info.stopWordsFilePath);
		}
		
		//Set up stemmer
		stemmer = new Stemmer();
	
		//Index each file
		File resourceFolder = new File(info.resourceDirectoryPath);
		File[] resourceFiles = resourceFolder.listFiles();

		for (int i = 0; i < resourceFiles.length; i++) {
			if (resourceFiles[i].isFile()) {	
				
				// Make sure the file is a .txt in order to index
				String filename = resourceFiles[i].getName();
				if (!filename.toString().endsWith(".txt"))
					continue; // go to the next file
				
				//Collect the file contents into a string for tokenizing.
				StringBuilder s = new StringBuilder();
				String useCaseFileName = resourceFiles[i].toString();

				try {
					for (String line : Files.readAllLines(Paths.get(useCaseFileName))) {
						s.append(line + "\n");
					}
				} catch(IOException io) {
					System.out.println(io.getMessage());
				}
				
				//Tokenize for other features
				RequirementsTokenizer tokenizer = new RequirementsTokenizer();
				ArrayList<Token> tokens = tokenizer.tokenize(s.toString());
			
				//Check if users want to process use case files
				if(info.doTokenize || info.doStem || !info.acronymFilePath.isEmpty() || !info.stopWordsFilePath.isEmpty()) {

					//Remove stop words (if requested)
					if(stopWordRemover != null) { 
						tokens = stopWordRemover.RemoveStopWords(tokens);
					}

					//Convert acronyms (if requested)
					if(acronymConverter != null) {
						tokens = acronymConverter.restoreAcronyms(tokens);
					}

					//Stem (if requetsed)
					if(info.doStem) {
						tokens = stemmer.stem(tokens);
					}

					tokenizer.updateTokens(tokens);
				}

				//Create an index. Add to list
				Index index = new Index(tokens);
				indices.add(index);
				
				//If the user wants to store the indices, write to file
				if (info.storeIndicesDir != "") {
					// remove the extension
					filename = filename.replaceFirst("[.][^.]+$", "");
					try {
						PrintWriter w = new PrintWriter(Paths.get(info.storeIndicesDir, filename +"_indices.txt").toString(), "UTF-8");
						w.println(index.getTokensAsString());
						w.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	
				
		//End timing
		indexDurationTime = (System.nanoTime() - indexStartTime) / NANOSEC_SEC_CONVERT;
	}
	
	/**
	 * Returns the index of the file at index.
	 * @param index	The position the file is in alphabetical order in the given
	 * 				directory.
	 * @return The index as a string
	 */
	public String getIndexFile(int index) {
		if(index < indices.size() && index >= 0) {
			return indices.get(index).getTokensAsString();
		} else {
			return new String("");
		}
	}
	
	public double GetIndexTime()
	{
		return indexDurationTime;
	}

}
