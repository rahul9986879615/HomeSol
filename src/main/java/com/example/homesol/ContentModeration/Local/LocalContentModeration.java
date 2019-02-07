package com.example.homesol.ContentModeration.Local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.example.homesol.Interface.IContentModerator;
import com.example.homesol.cache.ProfaneWords;
import com.example.homesol.model.ModerateResult;
import com.example.homesol.model.TextDetail;
import com.google.gson.Gson;

import opennlp.tools.tokenize.SimpleTokenizer;

public class LocalContentModeration implements IContentModerator{

	HashMap<Integer, List<String>> ProfaneHashTable=null;
	@Override
	public String ValidateText(String data) {
		// TODO Auto-generated method stub
		ProfaneHashTable=ProfaneWords.GetInstance().getProfaneHashTable();
		return ParseAndValidate(data);
	} 
	
	private String ParseAndValidate(String data)
	{
		ModerateResult finalresult=new ModerateResult();
		
		try {			
			finalresult.ProfaneText=new ArrayList<TextDetail>();
			finalresult.PersonalInfo=new ArrayList<TextDetail>();
			GetProfaneWords(data, finalresult);
			GetEmails(data, finalresult);
			GetPhoneNumbers(data, finalresult);
			GetWebUrls(data, finalresult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();		
		return gson.toJson(finalresult);
	}
	
	private String[] TokenizeText(String data) 
			  throws Exception {
		
				String[] tokens=null;
			    SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
			    tokens = tokenizer.tokenize(data);
			   return tokens;
					   
			  }
	private void GetProfaneWords(String data,ModerateResult finalresult)
	{
		String[] words;
		try {
			words = TokenizeText(data);
			int pos=0;
			for (int i = 0; i < words.length; i++) {				
				String temp=words[i].toLowerCase();
				if(ProfaneHashTable.containsKey(temp.hashCode()))
				{
					TextDetail detail=new TextDetail();
					detail.Position=pos+i;
					detail.Text=words[i];
					finalresult.ProfaneText.add(detail);
				}
				pos=pos+words[i].length();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void GetEmails(String line,ModerateResult finalresult){

		HashSet<String> container=new HashSet<String>();
		Pattern p = Pattern.compile("([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})");
        Matcher m = p.matcher(line);

        while(m.find()) {
            container.add(m.group(1));
        }
        Object[] matcheditems=container.toArray();
        
		for (int i = 0; i < matcheditems.length; i++) {
			
			TextDetail detail=new TextDetail();
			detail.Position=line.indexOf(matcheditems[i].toString());
			detail.Text=matcheditems[i].toString();
			finalresult.PersonalInfo.add(detail);
		}
    }
	public void GetPhoneNumbers(String line,ModerateResult finalresult){

		HashSet<String> container=new HashSet<String>();
		Pattern p = Pattern.compile("(?:(?:\\+?([1-9]|[0-9][0-9]|[0-9][0-9][0-9])\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([0-9][1-9]|[0-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?");
        Matcher m = p.matcher(line);

        while(m.find()) {
            container.add(m.group(1));
        }
        Object[] matcheditems=container.toArray();
        for (int i = 0; i < matcheditems.length; i++) {
			
			TextDetail detail=new TextDetail();
			if(matcheditems[i]!=null)
			{
				detail.Position=line.indexOf(matcheditems[i].toString());
				detail.Text=matcheditems[i].toString();
			}
			finalresult.PersonalInfo.add(detail);
		}

    }
	public void GetWebUrls(String line,ModerateResult finalresult){

		HashSet<String> container=new HashSet<String>();
		Pattern p = Pattern.compile("(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))");
        Matcher m = p.matcher(line);

        while(m.find()) {
            container.add(m.group(1));
        }
        Object[] matcheditems=container.toArray();
        for (int i = 0; i < matcheditems.length; i++) {
			
			TextDetail detail=new TextDetail();
			if(matcheditems[i]!=null)
			{
				detail.Position=line.indexOf(matcheditems[i].toString());
				detail.Text=matcheditems[i].toString();
			}
			finalresult.PersonalInfo.add(detail);
		}

    }
	
}
