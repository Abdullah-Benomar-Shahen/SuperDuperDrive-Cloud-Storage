package com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages;

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

    public HomePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void navigateToNotes(){
        this.navNoteTab.click();
    }

    public void navigateToCredentials(){
        this.navCredentialsTab.click();
    }

    public void createNewNote(){
        this.createNewCredentialButton.click();
    }

    public void clickLogout(){
        this.logoutButton.click();
    }
}
