package com.example.homesol.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.homesol.Interface.IContentModerator;

@RestController
@RequestMapping("/TextContent/Validate")
public class TextModerator {

	@Autowired
	IContentModerator contentmoderator ;		

	@RequestMapping(method = RequestMethod.GET)
	public String Gettext() {
		return "Get Not Supported";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)	
	public String Validatecomments(@RequestBody  String content)
	{
		//Preconditions.checkNotNull(content);
		if(content!=null)
		{
			return contentmoderator.ValidateText(content);
		}
		return null;
	}
}