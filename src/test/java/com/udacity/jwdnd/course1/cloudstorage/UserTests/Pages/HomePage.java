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

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(id = "note-delete-btn")
    private WebElement noteDeleteButton;

    @FindBy(id = "note-edit-btn")
    private WebElement noteEditButton;

    @FindBy(id = "credential-delete-btn")
    private WebElement credentialDeleteButton;

    @FindBy(id = "credential-edit-btn")
    private WebElement credentialEditButton;

    @FindBy(id = "note-element-title")
    private WebElement noteTitleText;

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
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createNewNoteButton);
    }

    public void createNewCredential(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createNewCredentialButton);
    }

    public void clickLogout(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutButton);
    }

    public void clickNoteSubmit(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteSubmitButton);
    }

    public void clickCredentialSubmit(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialSubmitButton);
    }

    public void clickNoteEdit(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteEditButton);
    }

    public void clickNoteDelete(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteDeleteButton);
    }

    public void clickCredentialEdit(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialEditButton);
    }

    public void clickCredentialDelete(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialDeleteButton);
    }

    public void setNoteTitleField(String title){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", noteTitleField);
    }

    public void setNoteDescriptionField(String description){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", noteDescriptionField);
    }

    public void setCredentialUrlField(String url){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", credentialUrlField);
    }

    public void setCredentialUsernameField(String username){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", credentialUsernameField);
    }

    public void setCredentialPasswordField(String pass){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + pass + "';", credentialPasswordField);
    }
}
