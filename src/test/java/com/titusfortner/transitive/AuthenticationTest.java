package com.titusfortner.transitive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AuthenticationTest extends BaseTest {
    String emailValue = faker.internet().emailAddress();
    String passwordValue = faker.internet().password();

    @BeforeEach
    public void navigateToSignUp() {
        driver.get(BASE_URL + "/sign_up");
    }

    @Test
    public void signUp() {
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
        WebElement email = driver.findElement(By.id("user_email"));
        email.sendKeys(emailValue);
        WebElement password = driver.findElement(By.id("user_password"));
        password.sendKeys(passwordValue);
        WebElement submit = driver.findElement(By.cssSelector("[data-test=submit]"));
        submit.click();

        WebElement signOut = driver.findElement(By.cssSelector("[data-test=sign-out]"));
        signOut.click();

        List<WebElement> emailElements = driver.findElements(By.cssSelector("[data-test=current-user]"));
        Assertions.assertEquals(0, emailElements.size());
    }

    @Test
    public void logIn() throws InterruptedException {
        WebElement email = driver.findElement(By.id("user_email"));
        email.sendKeys(emailValue);
        WebElement password = driver.findElement(By.id("user_password"));
        password.sendKeys(passwordValue);
        WebElement submit = driver.findElement(By.cssSelector("[data-test=submit]"));
        submit.click();

        WebElement signOut = driver.findElement(By.cssSelector("[data-test=sign-out]"));
        signOut.click();

        WebElement signInAgain = driver.findElement(By.id("sign-in"));
        signInAgain.click();
        Thread.sleep(200);

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
