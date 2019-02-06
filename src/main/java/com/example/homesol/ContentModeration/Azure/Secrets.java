package com.example.homesol.ContentModeration.Azure;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorClient;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorManager;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.AzureRegionBaseUrl;

//Wraps the creation and configuration of a Content Moderator client.
public class Secrets {
	
	// The region/location for your Content Moderator account, 
    private static  String AzureRegion ;
    private static String UserName="";
    // Your Content Moderator subscription key.
    private static String Key="";
    // The base URL fragment for Content Moderator calls.
    private static String AzureBaseURL ="https://{AzureRegion}.api.cognitive.microsoft.com";
 
    public Secrets(String azureregion,String azurebaseurl,String username,String password) {
    	
    	AzureRegion=azureregion;
    	AzureBaseURL=azurebaseurl;
    	UserName=username;
    	Key=password;
    	}
    
    // Returns a new Content Moderator client for your subscription.
    public static ContentModeratorClient NewClient()
    {
        // Create and initialize an instance of the Content Moderator API wrapper..
    	try
    	{	
    		AzureRegionBaseUrl arbu=AzureRegionBaseUrl.fromString(AzureRegion);
    		ContentModeratorClient client=ContentModeratorManager.authenticate(AzureBaseURL, Key).withBaseUrl(arbu);    		
    		return client;
    	}
    	catch(Exception e)
    	{
    		String exception=String.format("Exception:%s,StackTrace:%s", e.getMessage(),e.getStackTrace());
    		System.out.println(exception);
    	}
    	return null;
    }
}
