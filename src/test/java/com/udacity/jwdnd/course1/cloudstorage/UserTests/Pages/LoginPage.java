package com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages;

import org.openqa.selenium.JavascriptExecutor;
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

    private final WebDriver driver;


    public LoginPage(WebDriver webDriver){
        this.driver = webDriver;
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
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", usernameField);
    }

    public void setPasswordField(String password){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", passwordField);
    }

    public void clickLoginButton(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
    }

    public void clickSignupLink(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signupLink);
    }



}
