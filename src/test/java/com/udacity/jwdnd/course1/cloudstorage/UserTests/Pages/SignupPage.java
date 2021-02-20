package com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages;

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

    public SignupPage(WebDriver webDriver) {
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
        inputFirstName.clear();
        inputFirstName.sendKeys(firstName);
    }

    public void setInputLastName(String lastName){
        inputLastName.clear();
        inputLastName.sendKeys(lastName);
    }

    public void setInputUsername(String username){
        inputUsername.clear();
        inputUsername.sendKeys(username);
    }

    public void setInputPassword(String password){
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    public void submitForm(){
        signupBtn.click();
    }

    public void goToLogin(){
        toLoginPage.click();
    }

}
