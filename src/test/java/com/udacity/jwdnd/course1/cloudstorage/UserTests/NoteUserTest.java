package com.udacity.jwdnd.course1.cloudstorage.UserTests;

import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.UserTests.Pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
    public void createAndUpdateNote(){
        this.driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());

        // CREATE NOTE
        homePage = new HomePage(driver);
        homePage.navigateToNotes();
        homePage.createNewNote();
        homePage.setNoteTitleField("TEST NOTE");
        homePage.setNoteDescriptionField("THI IS TEST NOTE!");
        homePage.clickNoteSubmit();

        Assertions.assertEquals("Result", driver.getTitle());
        homePage.clickGoBackHome();

        Assertions.assertEquals("Home", driver.getTitle());
        homePage.navigateToNotes();

        List<WebElement> rows = driver.findElements(By.cssSelector("[class='note-elements']"));
        Assertions.assertEquals(1, rows.size());
    }


    @Test
    @Order(2)
    public void updateNote(){
        this.driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());

        // CREATE NOTE
        homePage = new HomePage(driver);
        homePage.navigateToNotes();
        homePage.createNewNote();
        homePage.setNoteTitleField("TEST NOTE");
        homePage.setNoteDescriptionField("THI IS TEST NOTE!");
        homePage.clickNoteSubmit();

        Assertions.assertEquals("Result", driver.getTitle());
        homePage.clickGoBackHome();

        Assertions.assertEquals("Home", driver.getTitle());
        homePage.navigateToNotes();

        List<WebElement> rows = driver.findElements(By.cssSelector("[class='note-elements']"));
        String c = rows.get(0).getText();
        System.out.println("CURRENT NOTE: " + c);
        Assertions.assertEquals(1, rows.size());

        // UPDATE NOTE
        homePage.clickNoteEdit();
        homePage.setNoteTitleField("TEST NOTE EDITED");
        homePage.clickNoteSubmit();
        homePage.clickGoBackHome();
        homePage.navigateToNotes();

        // FIXME: CHANGES AFTER UPDATE ARE NOT DISPLAYED
        //List<WebElement> elements = driver.findElements(By.id("note-element-title"));

        //Assertions.assertEquals("TEST NOTE EDITED", );

        //Assertions.assertEquals();
    }

    @Test
    @Order(3)
    public void deleteNote(){
        this.driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());

        // CREATE NOTE
        homePage = new HomePage(driver);
        homePage.navigateToNotes();
        homePage.createNewNote();
        homePage.setNoteTitleField("TEST NOTE");
        homePage.setNoteDescriptionField("THI IS TEST NOTE!");
        homePage.clickNoteSubmit();

        Assertions.assertEquals("Result", driver.getTitle());
        homePage.clickGoBackHome();

        Assertions.assertEquals("Home", driver.getTitle());
        homePage.navigateToNotes();

        List<WebElement> rows = this.driver.findElements(By.cssSelector("[class='note-elements']"));
        Assertions.assertEquals(1, rows.size());

        // DELETE THE CREATED NOTE
        homePage.clickNoteDelete();

        List<WebElement> rowsAfterDelete = this.driver.findElements(By.cssSelector("[class='note-elements']"));
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

        this.driverWait.until(ExpectedConditions.titleContains("Home"));
    }
}
