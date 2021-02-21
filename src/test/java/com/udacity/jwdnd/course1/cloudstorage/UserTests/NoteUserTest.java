package com.udacity.jwdnd.course1.cloudstorage.UserTests;

import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteUserTest {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait driverWait;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private HomePage homePage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.driverWait = new WebDriverWait (driver, 1000);
        this.signupAndLogin();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void createNewNote(){
        this.driver.get("http://localhost:" + this.port + "/home");

        homePage = new HomePage(driver);
        homePage.navigateToNotes();
    }

    @Test
    @Order(2)
    public void updateNote(){

    }

    @Test
    @Order(1)
    public void deleteNote(){

    }

    private void signupAndLogin(){
        this.signupProcess();
        this.loginProcess();
    }
    private void signupProcess(){
        String username = "TEST_USER";
        String password = "Pa$$w0rd";

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        signupPage = new SignupPage(driver);
        signupPage.setInputFirstName("TestFirstName");
        signupPage.setInputLastName("TestLastName");
        signupPage.setInputUsername(username);
        signupPage.setInputPassword(password);
        signupPage.submitForm();
        this.driverWait.until(ExpectedConditions.elementToBeClickable(signupPage.getLoginLink()));
        Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signupPage.getSuccessMsg());
        signupPage.goToLogin(); //TODO: Works on time and it does not ten other times??? FIX IT

        this.driverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("Login", driver.getTitle());
    }
    private void loginProcess(){
        String username = "TEST_USER";
        String password = "Pa$$w0rd";

        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        loginPage = new LoginPage(driver);
        Assertions.assertDoesNotThrow(() ->{
            loginPage.setUsernameField(username);
            loginPage.setPasswordField(password);
            loginPage.clickLoginButton();
        });

        this.driverWait.until(ExpectedConditions.titleContains("Login"));
    }
}
