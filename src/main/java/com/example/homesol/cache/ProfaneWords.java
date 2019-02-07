package com.example.homesol.cache;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public class ProfaneWords {

	@Value("${Application.ProfaneWordList}")
	String profaneWords="C:\\WorkRelated\\Java\\Google-profanity-words-master\\Google-profanity-words-master\\list.txt";
	
	private static ProfaneWords instance;	
	HashMap<Integer, List<String>> ProfaneHashTable = new HashMap<Integer, List<String>>();
	
	private ProfaneWords()
	{
		GenerateHashMap();
	}
	public HashMap<Integer, List<String>> getProfaneHashTable() {
		return ProfaneHashTable;
	}
	public static ProfaneWords GetInstance()
	{
		if(instance==null)
			instance=new ProfaneWords();
		return instance;
	}
	private List<String> readFileInList(String fileName) 
	  { 	  
	    List<String> lines = Collections.emptyList(); 
	    try
	    { 
	      lines = 
	       Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8); 
	    } 	  
	    catch (IOException e) 
	    { 
	      // do something 
	      e.printStackTrace(); 
	    } 
	    return lines; 
	  }
	
	private void GenerateHashMap()
	{	
		List<String> profanelist=readFileInList(profaneWords);
		for (int i = 0; i < profanelist.size(); i++) {
			
			String value=profanelist.get(i).trim();
			int key=value.hashCode();
			if(ProfaneHashTable.containsKey(key))
			{
				ProfaneHashTable.get(key).add(value);
			}
			else
			{
				List<String> temp=new ArrayList<String>();
				temp.add(value);
				ProfaneHashTable.put(key, temp);			
			}
		}
	}
	
}
