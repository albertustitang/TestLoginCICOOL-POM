package com.nexsoft.cicool;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.FindBy;

public class CRUDBuilderPage {

	protected WebDriver driver;

//	@FindBy(xpath = "//a[@id='btn_add_new']")
//	private WebElement AddCrudNew;

	@FindBy(xpath = "//tbody[@id='tbody_crud']/tr")
	List<WebElement> lstTable;

	public CRUDBuilderPage(WebDriver driver) {

		this.driver = driver;

	}

	public void clickSeeButtonWithTitle(String title) {

		WebElement see = null;
		int counter = 1;

		for (WebElement webElement : lstTable) {

			see = webElement.findElement(By.xpath("//tbody[@id='tbody_crud']/tr[" + counter + "]/td/a[1]"));
			WebElement teks = webElement.findElement(By.xpath("//td[2]"));

			if (teks.getText().equalsIgnoreCase(title)) {
				see.click();
				break;
			}

		}
	}

}
