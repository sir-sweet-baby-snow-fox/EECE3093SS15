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

public class Indexer {
	private AcronymConverter acronymConverter = null;
	private Tokenizer tokenizer = null;
	private StopWordRemover stopWordRemover = null;
	private Stemmer stemmer = null;
	private ArrayList<Index> indices = null;
	private double IndexDurationTime = 0;

	/**
	 * 
	 * @param resourceDirectoryPath
	 * @param tokenize
	 * @param stem
	 * @param acronymFilePath
	 * @param stopWordsFilePath
	 * @throws IOException 
	 */
	public Indexer(String resourceDirectoryPath, boolean doTokenize, boolean doStem, String acronymFilePath, String stopWordsFilePath, String storeIndicesDir)  {
		double indexStartTime = System.nanoTime();
		
		indices = new ArrayList<Index>();
		
		//Set up acronymConvert (if requested)
		if(!acronymFilePath.isEmpty()) {
			File acronymFile = new File(acronymFilePath);
			acronymConverter = new AcronymConverter(acronymFile);
		}
		
		//Set up stopWordRemover (if requested)
		if(!stopWordsFilePath.isEmpty()) {
			stopWordRemover = new StopWordRemover(stopWordsFilePath);
		}
		
		//Set up stemmer and tokenizer
		tokenizer = new Tokenizer();
		stemmer = new Stemmer();
	
		//Index each file
		File resourceFolder = new File(resourceDirectoryPath);
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
				String[] tokens = tokenizer.TokenizeString(s.toString());
			
				//Check if users want to process use case files
				if(doTokenize || doStem || !acronymFilePath.isEmpty() || !stopWordsFilePath.isEmpty()) {

					//Remove stop words (if requested)
					if(stopWordRemover != null) { 
						tokens = stopWordRemover.RemoveStopWords(tokens);
					}

					//Convert acronyms (if requested)
					if(acronymConverter != null) {
						tokens = acronymConverter.restoreAcronyms(tokens);
					}

					//Stem (if requetsed)
					if(doStem) {
						tokens = stemmer.stem(tokens);
					}


				}

				//Create an index. Add to list
				Index index = new Index(tokens);
				indices.add(index);
				
				//If the user wants to store the indices, write to file
				if (storeIndicesDir != "") {
					// remove the extension
					filename = filename.replaceFirst("[.][^.]+$", "");
					try {
						PrintWriter w = new PrintWriter(Paths.get(storeIndicesDir, filename +"_indices.txt").toString(), "UTF-8");
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
		IndexDurationTime = (System.nanoTime() - indexStartTime) / 1000000000.0;
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
		return IndexDurationTime;
	}

}
