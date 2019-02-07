package com.example.homesol.Helper;
import com.example.homesol.model.Classification;
import com.example.homesol.model.ModerateResult;
import com.example.homesol.model.TextDetail;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.Screen;
import java.util.List;
import java.util.ArrayList;
public class Helper {

	public static ModerateResult ConvertToModerateResult(Screen result) {
		
		ModerateResult finalresult=null;
		if(result!=null)
		{
			finalresult=new ModerateResult();
			finalresult.ProfaneText=GetProfoneWords(result);
			
			if(result.pII()!=null)
			finalresult.PersonalInfo=GetPersonalInfo(result);
			
			if(result.classification()!=null)
			finalresult.Category=GetCategory(result);
			}
		return finalresult;		
	}
	
	public static List<TextDetail> GetProfoneWords(Screen result)
	{
		List<TextDetail> profonewords=null;
		if(result!=null)
		{
			profonewords=new ArrayList<TextDetail>();
			if(result.terms()!=null){
				
				for (int i = 0; i < result.terms().size(); i++) {
					
					TextDetail temp=new TextDetail();
					temp.Position=result.terms().get(i).originalIndex();
					temp.Text=result.terms().get(i).term();
					profonewords.add(temp);						
				}
			}
		}
		return profonewords;		
	}	
	public static List<TextDetail> GetPersonalInfo(Screen result)
	{
		List<TextDetail> personalinfo=null;
		if(result!=null)
		{
			personalinfo=new ArrayList<TextDetail>();
			if(result.pII().email()!=null)
			{
				for (int i = 0; i < result.pII().email().size(); i++) {
					
					TextDetail temp=new TextDetail();
					temp.Position=result.pII().email().get(i).index();
					temp.Text=result.pII().email().get(i).detected();
					personalinfo.add(temp);						
				}
			}
			if(result.pII().phone()!=null)
			{
				for (int i = 0; i < result.pII().phone().size(); i++) {
					
					TextDetail temp=new TextDetail();
					temp.Position=result.pII().phone().get(i).index();
					temp.Text=result.pII().phone().get(i).text();
					personalinfo.add(temp);						
				}
			}
			if(result.pII().address()!=null)
			{
				for (int i = 0; i < result.pII().address().size(); i++) {
					
					TextDetail temp=new TextDetail();
					temp.Position=result.pII().phone().get(i).index();
					temp.Text=result.pII().phone().get(i).text();
					personalinfo.add(temp);						
				}
			}
		}
		return personalinfo;		
	}
	
	public static Classification GetCategory(Screen result)
	{
		Classification category=null;	
		if(result.classification()!=null)
		{
			category=new Classification();
			category.ReviewRecommended=result.classification().reviewRecommended();
			category.Category1=result.classification().category1().score();
			category.Category2=result.classification().category2().score();
			category.Category3=result.classification().category3().score();
		}
		
		return category;
	}
		
}
