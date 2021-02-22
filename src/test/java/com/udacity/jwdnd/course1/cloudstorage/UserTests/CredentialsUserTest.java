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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CredentialsUserTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait driverWait;

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
    public void createCredential(){
        this.driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());

        HomePage homePage = new HomePage(driver);
        homePage.navigateToCredentials();
        homePage.createNewCredential();
        homePage.setCredentialUrlField("http://localhost");
        homePage.setCredentialUsernameField("TEST_USER");
        homePage.setCredentialPasswordField("Pa$$w0rd");
        homePage.clickCredentialSubmit();

        Assertions.assertEquals("Result", driver.getTitle());
        homePage.clickGoBackHome();

        Assertions.assertEquals("Home", driver.getTitle());
        homePage.navigateToCredentials();

        List<WebElement> rows = driver.findElements(By.id("credential-elements"));
        Assertions.assertEquals(1, rows.size());
    }

    @Test
    @Order(2)
    public void updateCredential(){
        this.driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());

        HomePage homePage = new HomePage(driver);
        homePage.navigateToCredentials();
        homePage.createNewCredential();
        homePage.setCredentialUrlField("http://localhost");
        homePage.setCredentialUsernameField("TEST_USER");
        homePage.setCredentialPasswordField("Pa$$w0rd");
        homePage.clickCredentialSubmit();

        Assertions.assertEquals("Result", driver.getTitle());
        homePage.clickGoBackHome();

        Assertions.assertEquals("Home", driver.getTitle());
        homePage.navigateToCredentials();

        List<WebElement> rows = driver.findElements(By.id("credential-elements"));
        Assertions.assertEquals(1, rows.size());

        homePage.clickCredentialEdit();
        this.driverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        this.driver.findElement(By.id("credential-url")).clear();
        homePage.setCredentialUrlField("https://localhost");
        homePage.setCredentialPasswordField("Pa$$w0rd");
        homePage.clickCredentialSubmit();
        this.driverWait.until(ExpectedConditions.elementToBeClickable(By.id("back-to-home-from-success")));
        homePage.clickGoBackHome();
        homePage.navigateToCredentials();


        WebElement updatedCredentialUrl= this.driver.findElement(By.id("credentialUrl"));
        Assertions.assertEquals("https://localhost", updatedCredentialUrl.getAttribute("innerText"));
    }

    @Test
    @Order(3)
    public void deleteCredential(){
        this.driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());

        HomePage homePage = new HomePage(driver);
        homePage.navigateToCredentials();
        homePage.createNewCredential();
        homePage.setCredentialUrlField("http://localhost");
        homePage.setCredentialUsernameField("TEST_USER");
        homePage.setCredentialPasswordField("Pa$$w0rd");
        homePage.clickCredentialSubmit();

        Assertions.assertEquals("Result", driver.getTitle());
        homePage.clickGoBackHome();

        Assertions.assertEquals("Home", driver.getTitle());
        homePage.navigateToCredentials();

        List<WebElement> rows = driver.findElements(By.id("credential-elements"));
        Assertions.assertEquals(1, rows.size());

        homePage.clickCredentialDelete();

        List<WebElement> rowsAfterDelete = this.driver.findElements(By.id("credential-elements"));
        Assertions.assertEquals(0, rowsAfterDelete.size());
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

        SignupPage signupPage = new SignupPage(driver);
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

        LoginPage loginPage = new LoginPage(driver);
        Assertions.assertDoesNotThrow(() ->{
            loginPage.setUsernameField(username);
            loginPage.setPasswordField(password);
            loginPage.clickLoginButton();
        });

        this.driverWait.until(ExpectedConditions.titleContains("Home"));
    }
}
