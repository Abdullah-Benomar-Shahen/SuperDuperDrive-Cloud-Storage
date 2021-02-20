package com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id="inputUsername")
    private WebElement usernameField;

    @FindBy(id="inputPassword")
    private WebElement passwordField;

    @FindBy(id="loginBtn")
    private WebElement loginButton;

    @FindBy(id="goToSignup")
    private WebElement signupLink;

    @FindBy(id = "invalidMessage")
    private WebElement errorMsg;

    public LoginPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public String getUsername(){
        return usernameField.getText();
    }

    public String getPassword(){
        return passwordField.getText();
    }

    public String getErrorMsg(){
        return errorMsg.getText();
    }

    public void setUsernameField(String username){
        usernameField.sendKeys(username);
    }

    public void setPasswordField(String password){
        passwordField.sendKeys(password);
    }

    public void clickLoginButton(){
        loginButton.click();
    }

    public void clickSignupLink(){
        signupLink.click();
    }



}
