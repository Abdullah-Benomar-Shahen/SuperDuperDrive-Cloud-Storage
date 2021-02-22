package com.udacity.jwdnd.course1.cloudstorage.UserTests;

import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.SignupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AccessUserTest {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait driverWait;
    private LoginPage loginPage;
    private SignupPage signupPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.driverWait = new WebDriverWait (driver, 1000);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }


    @Test
    @Order(1)
    public void unauthorizedUserCanAccessLogin(){
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }
    @Test
    @Order(2)
    public void unauthorizedUserCanAccessSignup(){
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }
    @Test
    @Order(3)
    public void unauthorizedUserCanNotAccessOtherPages(){
        driver.get("http://localhost:" + this.port + "/Home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(4)
    public void signupWithSameUsernameTest(){
        String firstName = "TestFirstName";
        String lastName = "TestLastName";
        String password = "Pa$$w0rd";

        driver.get("http://localhost:" + this.port + "/signup");

        // User 1
        signupPage = new SignupPage(driver);
        signupPage.setInputFirstName(firstName);
        signupPage.setInputLastName(lastName);
        signupPage.setInputUsername("TEST_USER");
        signupPage.setInputPassword(password);
        signupPage.submitForm();


        driver.get("http://localhost:" + this.port + "/signup");

        // User2 with same Username as User 1
        signupPage = new SignupPage(driver);
        signupPage.setInputFirstName(firstName);
        signupPage.setInputLastName(lastName);
        signupPage.setInputUsername("TEST_USER");
        signupPage.setInputPassword(password);
        signupPage.submitForm();

        Assertions.assertEquals("Sign Up", driver.getTitle());
        Assertions.assertEquals("user or username already exists. Please try again.", signupPage.getErrorMsg());
    }

    @Test
    @Order(5)
    public void loginWithInvalidCredentialsTest(){
        driver.get("http://localhost:" + this.port + "/login");

        loginPage = new LoginPage(driver);
        loginPage.setUsernameField("TEST_USER_INVALID");
        loginPage.setPasswordField("Pa$$w0rd");
        loginPage.clickLoginButton();

        Assertions.assertEquals("Login", driver.getTitle());
        Assertions.assertEquals("Invalid username or password", loginPage.getErrorMsg());
    }

    @Test
    @Order(6)
    public void signupAndLoginTest() {
        this.signupProcess();
        this.loginProcess();

        this.driverWait.until(ExpectedConditions.titleContains("Home"));
        HomePage homePage = new HomePage(driver);
        Assertions.assertEquals("Home", driver.getTitle());
        this.driverWait.until(ExpectedConditions.elementToBeClickable(By.id("logoutBtn")));
        homePage.clickLogout();
        this.driverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("Login", driver.getTitle());
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
        signupPage.goToLogin();

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

        this.driverWait.until(ExpectedConditions.titleContains("Home"));
    }









}