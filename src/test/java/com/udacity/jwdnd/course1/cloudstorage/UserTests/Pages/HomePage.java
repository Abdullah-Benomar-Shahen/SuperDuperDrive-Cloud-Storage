package com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logoutBtn")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "note-creation-btn")
    private WebElement createNewNoteButton;

    @FindBy(id = "credential-creation-btn")
    private WebElement createNewCredentialButton;

    private final WebDriver driver;

    public HomePage(WebDriver webDriver){
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }
    public void navigateToNotes(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navNoteTab);
    }
    public void navigateToCredentials(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navCredentialsTab);
    }
    public void createNewNote(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createNewCredentialButton);
    }
    public void clickLogout(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutButton);
    }
}
