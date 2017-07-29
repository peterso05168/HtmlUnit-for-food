package net.sourceforge.htmlunit.htmlunit;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class FoodDataCrawler {
	public void getData() {
		final WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(20000);
		
		try {
			final HtmlPage page = webClient.getPage("http://www.boohee.com/food/");
			HtmlTextInput inputBox = page.querySelector("#keyword");
			inputBox.setText("橙子");
			HtmlElement submitBtn = page.querySelector("#search_form > button");
			HtmlPage pageAfterSubmitClicked = submitBtn.click();
			
			HtmlElement firstFoodOption = pageAfterSubmitClicked.querySelector("#main > div > div.widget-food-list.pull-left > ul > li:nth-child(1) > div.text-box.pull-left > h4 > a");
			HtmlPage foodDetail = firstFoodOption.click();
			List<DomNode> foodNutritionLst = foodDetail.querySelectorAll("#main > div > div.widget-food-detail.pull-left > div.nutr-tag.margin10 > div > dl");
			List<DomNode> filteredNutritionLst = new ArrayList<DomNode>();
			for (int i = 1; i < foodNutritionLst.size(); i++) {
				DomNode foodNutrition = foodNutritionLst.get(i);
				if (foodNutrition.getChildNodes().get(1) != null) {
					filteredNutritionLst.add(foodNutrition.getChildNodes().get(1));
				}
				
				if (foodNutrition.getChildNodes().get(3) != null) {
					filteredNutritionLst.add(foodNutrition.getChildNodes().get(3));
				}
			}
			
			for (DomNode filteredNutrition: filteredNutritionLst) {
				System.out.println(filteredNutrition.getChildNodes().get(0).asText());
				System.out.println(filteredNutrition.getChildNodes().get(1).asText());
			}
			

			webClient.close();
		} catch (Exception e) {
			webClient.close();
			e.printStackTrace();
		}
	}
}
