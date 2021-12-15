package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void getDoubleSignUp(){
		// Create a test account
		String firstName = "URL";
		String lastName = "Test";
		String  userName = "UT";
		String password = "123";
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		System.out.println("Page = " + driver.getCurrentUrl());
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		System.out.println("Page = " + driver.getPageSource().contains("Whitelabel Error Page"));
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}


	@Test
	public void testHomePage() {
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}
	@Test
	public void testHomePageAfterLogout(){
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		doLogout(webDriverWait);
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}


	@Test
	public void testAddNote(){
		addNewNote("New note", "Description");
		System.out.println("driver value" + driver.getPageSource());
		Assertions.assertTrue(driver.getPageSource().contains("New note"));
	}

	@Test
	public void testDeleteNote(){
		addNewNote("New note", "Description");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doLogout(webDriverWait);
		doLogIn("LFT", "123");
		//Select Notes tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();
		List<WebElement> elements = driver.findElements(By.id("btn-delete-notes-row"));
		elements.get(elements.size()-1).click();

		System.out.println("Todo listo");

		Assertions.assertFalse(driver.getPageSource().contains("New note"));
	}

	@Test
	public void testEditNote(){
		addNewNote("New note", "Description");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doLogout(webDriverWait);
		doLogIn("LFT", "123");
		//Select Notes tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();
		List<WebElement> elements = driver.findElements(By.id("btn-edit-notes-row"));
		elements.get(elements.size()-1).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys("Edited note");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.sendKeys("Description of edited note");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit")));
		WebElement btnSaveNote = driver.findElement(By.id("note-submit"));
		btnSaveNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		System.out.println("Todo listo");

		Assertions.assertTrue(driver.getPageSource().contains("Edited note"));
	}

	public void doLogout(WebDriverWait webDriverWait){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutBotton")));
		WebElement logoutBotton = driver.findElement(By.id("logoutBotton"));
		logoutBotton.click();
	}

	public void addNewNote(String titleNote,String descriptionNote){
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		//Select Notes tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-add-note")));
		WebElement btnAddNote = driver.findElement(By.id("btn-add-note"));
		btnAddNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys(titleNote);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.sendKeys(descriptionNote);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit")));
		WebElement btnSaveNote = driver.findElement(By.id("note-submit"));
		btnSaveNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();
	}

	@Test
	public void testDeleteCredential(){
		addCredential("www.google.com", "josera","1234");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doLogout(webDriverWait);
		doLogIn("LFT", "123");
		//Select Notes tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement navNotesTab = driver.findElement(By.id("nav-credentials-tab"));
		navNotesTab.click();
		List<WebElement> elements = driver.findElements(By.id("btn-delete-credential-row"));
		elements.get(elements.size()-1).click();

		System.out.println("Todo listo");

		Assertions.assertFalse(driver.getPageSource().contains("www.google.com"));
	}

	@Test
	public void testAddCredential(){
		addCredential("www.google.com", "josera", "1234");
		System.out.println("driver value" + driver.getPageSource());
		Assertions.assertTrue(driver.getPageSource().contains("www.google.com"));
	}

	@Test
	public void testEditCredential(){
		addCredential("www.google.com" ,"josera", "1234");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doLogout(webDriverWait);
		doLogIn("LFT", "123");
		//Select Notes tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement navCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialTab.click();
		List<WebElement> elements = driver.findElements(By.id("btn-edit-credential-row"));
		elements.get(elements.size()-1).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement noteTitle = driver.findElement(By.id("credential-url"));
		noteTitle.sendKeys("www.udacity.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("jramon");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("6547");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-submit")));
		WebElement btnSaveNote = driver.findElement(By.id("credential-submit"));
		btnSaveNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		navCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialTab.click();

		System.out.println("Todo listo");

		Assertions.assertTrue(driver.getPageSource().contains("www.udacity.com"));
	}

	public void addCredential(String url,String username,String password){
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		//Select Notes tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement navNotesTab = driver.findElement(By.id("nav-credentials-tab"));
		navNotesTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn_add_credential")));
		WebElement btnAddNote = driver.findElement(By.id("btn_add_credential"));
		btnAddNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys(url);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys(username);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-submit")));
		WebElement btnSaveCredential = driver.findElement(By.id("credential-submit"));
		btnSaveCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		navNotesTab = driver.findElement(By.id("nav-credentials-tab"));
		navNotesTab.click();
	}

}