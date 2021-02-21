package com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    @FindBy(id = "logoutBtn")
    private WebElement logoutButton;

    @FindBy(id = "back-to-home-from-success")
    private WebElement backHomeFromResult;

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

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "note-delete-btn")
    private WebElement noteDeleteButton;

    @FindBy(id = "note-edit-btn")
    private WebElement noteEditButton;

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

    public void clickGoBackHome(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backHomeFromResult);
    }

    public void clickNoteSubmit(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteSubmitButton);
    }

    public void clickNoteEdit(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteEditButton);
    }

    public void clickNoteDelete(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteDeleteButton);
    }

    public void setNoteTitleField(String title){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", noteTitleField);
    }

    public void setNoteDescriptionField(String description){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", noteDescriptionField);
    }
}
