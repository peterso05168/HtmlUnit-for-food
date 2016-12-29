package net.sourceforge.htmlunit.htmlunit;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class NgauKee {
	public void getData() {
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(20000);
		
		try {
			final HtmlPage page = webClient.getPage("http://www.nkcl.com.hk/change-ch.php");
			HtmlTable FxTable = page.querySelector("#maincontent-fullwidth > table");
			List<HtmlTableRow> FxTableRow = FxTable.getRows();
			for (int i = 1; i < FxTableRow.size(); i++) {
				System.out.println(FxTableRow.get(i).getFirstChild().getNextSibling().asText());
				System.out.println(FxTableRow.get(i).getFirstChild().getNextSibling().getNextSibling().asText());
				DomNodeList<DomNode> targetRateInString = FxTableRow.get(i).getFirstChild().getNextSibling().getNextSibling().getNextSibling().querySelectorAll("img");
				String result = "";
				for (int j = 0; j < targetRateInString.size(); j++) {
					String resultString = targetRateInString.get(j).asXml().replaceAll("<img src=\"images/", "").replaceAll(".png\"/>", "");
					if (resultString.trim().equals("vir")) {
						result += ".";
					}else {
						result += resultString.trim();
					}
				}
				System.out.println(result);
				
				targetRateInString = FxTableRow.get(i).getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().querySelectorAll("img");
				result = "";
				for (int j = 0; j < targetRateInString.size(); j++) {
					String resultString = targetRateInString.get(j).asXml().replaceAll("<img src=\"images/", "").replaceAll(".png\"/>", "");
					if (resultString.trim().equals("vir")) {
						result += ".";
					}else {
						result += resultString.trim();
					}
				}
				System.out.println(result);
			}			
			webClient.close();
		} catch (Exception e) {
			webClient.close();
			System.out.println("Fail to get NgauKee Fx Data");
		}
	}
}
