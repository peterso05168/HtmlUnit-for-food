package net.sourceforge.htmlunit.htmlunit;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class HundredYear {
	public void getData() {
		final WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(20000);
		
		try {
			final HtmlPage page = webClient.getPage("http://bcel1985.blogspot.hk/");
			HtmlElement firstLink = page.querySelector("#Blog1 > div.blog-posts.hfeed > div:nth-child(1) > div > div > div > h3 > a");
			HtmlPage OneHundredYear = firstLink.click();
			HtmlTable FxTable = OneHundredYear.querySelector("#post-body-2209129596612489504 > table");
			List<HtmlTableRow> FxTableRow = FxTable.getRows();
			for (int i = 2; i < FxTableRow.size(); i++) {
				String[] requiredDataField = FxTableRow.get(i).asText().split("\\s+");
				for (int j = 0; j < 4; j++) {
					System.out.println(requiredDataField[j]);					
				}
			}			
			webClient.close();
		} catch (Exception e) {
			webClient.close();
			System.out.println("Fail to get HundredYear Fx Data");
		}
	}
}
