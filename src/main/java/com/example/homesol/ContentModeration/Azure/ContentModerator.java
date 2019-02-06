package com.example.homesol.ContentModeration.Azure;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.example.homesol.Helper.Helper;
import com.example.homesol.Interface.IContentModerator;
import com.example.homesol.exception.ServerErrorException;
import com.example.homesol.model.ModerateResult;
import com.google.gson.Gson;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorClient;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.TextModerations;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.*;


public class ContentModerator implements IContentModerator  {

	TextModerations moderator;
	
	@Value("${Azure.Password}")
		String Key; 
	@Value("${Azure.Username}")
	String UserName; 
	@Value("${Azure.Region}")
	String Region; 
	@Value("${Azure.BaseUrl}")
	String BaseUrl; 
	
	@Value("${Application.IsPIIRequired}")
	Boolean IsPIIRequired; 
	@Value("${Application.IsClassficationRequired}")
	Boolean IsClassificationRequired; 
	
	@Override
	public String ValidateText(String data) {
		try {
			return CreateReview(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new ServerErrorException("Something Went Wrong");
		}
	}
	public String CreateReview(String data)throws InterruptedException,ServerErrorException
	{
		Secrets client=new Secrets(Region, BaseUrl, UserName, Key);
		ContentModeratorClient clnt= Secrets.NewClient();
		if(clnt==null)
		{
			throw new ServerErrorException("Unable to Create Azure clinet");
		}
		 
		Screen res=clnt.textModerations().screenText("text/plain",data.getBytes(StandardCharsets.UTF_8), new ScreenTextOptionalParameter()
				.withPII(true)
				.withClassify(true));
		
		ModerateResult finalresult= Helper.ConvertToModerateResult(res);
		Gson gson = new Gson();
		
		return gson.toJson(finalresult);
	}

}
