package indexer;

import java.io.File;
import java.io.IOException;
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
	
	private String requirementsIndiciesViewID = "tracing.views.RequirementsIndicesView";

	/**
	 * 
	 * @param resourceDirectoryPath
	 * @param tokenize
	 * @param stem
	 * @param acronymFilePath
	 * @param stopWordsFilePath
	 * @throws IOException 
	 */
	Indexer(String resourceDirectoryPath, boolean doTokenize, boolean doStem, String acronymFilePath, String stopWordsFilePath)  {
		//Start timing
		
		indices = new ArrayList<Index>();
		
		//Set up acronymConvert (if requested)
		if(acronymFilePath != null) {
			File acronymFile = new File(acronymFilePath);
			acronymConverter = new AcronymConverter(acronymFile);
		}
		
		//Set up stopWordRemover (if requested)
		if(stopWordsFilePath != null) {
			stopWordRemover = new StopWordRemover(stopWordsFilePath);
		}
		
		//Set up stemmer and tokenizer
		tokenizer = new Tokenizer();
		stemmer = new Stemmer();
		
		//Check if users want to process use case files
		if(doTokenize || doStem || acronymFilePath != null || stopWordsFilePath != null) {
			//Index each file
			File resourceFolder = new File(resourceDirectoryPath);
			File[] resourceFiles = resourceFolder.listFiles();

			for (int i = 0; i < resourceFiles.length; i++) {
				if (resourceFiles[i].isFile()) {
					//Tokenize for other features
					String[] tokens = tokenizer.TokenizeString(resourceFiles[i].toString());
					
					if(stopWordRemover != null) { 
						tokens = stopWordRemover.RemoveStopWords(tokens);
					}
					
					if(acronymConverter != null) {
						tokens = acronymConverter.restoreAcronyms(tokens);
					}
					
					if(doStem) {
						tokens = stemmer.stem(tokens);
					}
					
					Index index = new Index(tokens);
					indices.add(index);
					
				}
			}
		}

		
		
		//End timing
	}

	private RequirementsIndicesView getRequirementsIndiciesView(String id) {
		RequirementsIndicesView riv = (RequirementsIndicesView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(id);
		return riv;
	}
}
