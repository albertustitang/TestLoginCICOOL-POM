package com.nexsoft.cicool;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Dashboard {

	protected WebDriver driver;

	@FindBy(xpath = "//span[normalize-space()='CRUD Builder']")
	private WebElement crudBuilder;

	@FindBy(xpath = "//span[@class='hidden-xs']")
	private WebElement username;

	public Dashboard(WebDriver driver) {

		this.driver = driver;

	}

	public CRUDBuilderPage clickCRUD() {
		crudBuilder.click();

		CRUDBuilderPage crudBuilderPage = PageFactory.initElements(driver, CRUDBuilderPage.class);

		return crudBuilderPage;
	}

	public String getUsername() {

		return username.getText();
	}

}
