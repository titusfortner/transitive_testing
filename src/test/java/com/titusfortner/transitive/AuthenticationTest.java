package com.titusfortner.transitive;

import com.titusfortner.transitive.api.Authentication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AuthenticationTest extends BaseTest {
    String emailValue = faker.internet().emailAddress();
    String passwordValue = faker.internet().password();
    Authentication authAPI;

    @BeforeEach
    public void setup() {
        authAPI = new Authentication();
    }

    @Test
    public void signUp() {
        driver.get(BASE_URL + "/sign_up");

        WebElement email = driver.findElement(By.id("user_email"));
        email.sendKeys(emailValue);
        WebElement password = driver.findElement(By.id("user_password"));
        password.sendKeys(passwordValue);
        WebElement submit = driver.findElement(By.cssSelector("[data-test=submit]"));
        submit.click();

        WebElement currentUser = driver.findElement(By.cssSelector("[data-test=current-user]"));
        String actualEmail = currentUser.getText();
        Assertions.assertEquals(emailValue, actualEmail);
    }

    @Test
    public void logOut() {
        driver.get(BASE_URL);
        authAPI.createUser(emailValue, passwordValue);

        Cookie authCookie = new Cookie("remember_token", authAPI.rememberToken);
        driver.manage().addCookie(authCookie);
        driver.navigate().refresh();

        WebElement signOut = driver.findElement(By.cssSelector("[data-test=sign-out]"));
        signOut.click();

        List<WebElement> emailElements = driver.findElements(By.cssSelector("[data-test=current-user]"));
        Assertions.assertEquals(0, emailElements.size());
    }

    @Test
    public void logIn() throws InterruptedException {
        driver.get(BASE_URL);
        authAPI.createUser(emailValue, passwordValue);

        driver.get(BASE_URL + "/sign_in");

        WebElement sessionEmail = driver.findElement(By.id("session_email"));
        sessionEmail.sendKeys(emailValue);
        WebElement sessionPassword = driver.findElement(By.id("session_password"));
        sessionPassword.sendKeys(passwordValue);
        WebElement submitSignIn = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitSignIn.click();

        Thread.sleep(100);
        WebElement currentUserSignIn = driver.findElement(By.cssSelector("[data-test=current-user]"));
        String actualEmailSignIn = currentUserSignIn.getText();
        Assertions.assertEquals(emailValue, actualEmailSignIn);
    }
}
