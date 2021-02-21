package com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;
    @FindBy(id = "inputLastName")
    private WebElement inputLastName;
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;
    @FindBy(id = "inputPassword")
    private WebElement inputPassword;
    @FindBy(id = "signupBtn")
    private WebElement signupBtn;
    @FindBy(id = "toLoginPage")
    private WebElement toLoginPage;
    @FindBy(id = "successMsg")
    private WebElement successMsg;
    @FindBy(id = "errorMsg")
    private WebElement errorMsg;

    private final WebDriver driver;

    public SignupPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getFirstName(){
        return inputFirstName.getText();
    }

    public String getLastName(){
        return inputLastName.getText();
    }

    public String getUserName(){
        return inputUsername.getText();
    }

    public String getPassword(){
        return inputPassword.getText();
    }

    public String getSuccessMsg(){
        return successMsg.getText();
    }

    public String getErrorMsg(){
        return errorMsg.getText();
    }

    public WebElement getLoginLink(){
        return this.toLoginPage;
    }

    public void setInputFirstName(String firstName){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + firstName + "';", inputFirstName);
    }
    public void setInputLastName(String lastName){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + lastName + "';", inputLastName);
    }
    public void setInputUsername(String username){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", inputUsername);
    }
    public void setInputPassword(String password){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", inputPassword);
    }
    public void submitForm(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signupBtn);
    }
    public void goToLogin(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toLoginPage);
    }

}
