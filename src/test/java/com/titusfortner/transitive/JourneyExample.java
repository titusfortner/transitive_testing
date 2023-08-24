package com.titusfortner.transitive;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class JourneyExample extends BaseTest {
    @Test
    public void userJourney() throws InterruptedException {
//        Go to Demo Site
        driver.get(BASE_URL);

//        Navigate to Sign In
        WebElement signIn = driver.findElement(By.id("sign-in"));
        signIn.click();

//        Navigate to Sign Up
        WebElement signUp = driver.findElement(By.cssSelector("[data-test=sign-up]"));
        signUp.click();

//        Submit Sign Up form
        String emailValue = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@example.com";
        WebElement email = driver.findElement(By.id("user_email"));
        email.sendKeys(emailValue);
        WebElement password = driver.findElement(By.id("user_password"));
        password.sendKeys("password");
        WebElement submit = driver.findElement(By.cssSelector("[data-test=submit]"));
        submit.click();

//        Verify Sign Up
        WebElement currentUser = driver.findElement(By.cssSelector("[data-test=current-user]"));
        String actualEmail = currentUser.getText();
        Assertions.assertEquals(emailValue, actualEmail);

//        Sign Out
        WebElement signOut = driver.findElement(By.cssSelector("[data-test=sign-out]"));
        signOut.click();

//        Verify Sign Out
        List<WebElement> emailElements = driver.findElements(By.cssSelector("[data-test=current-user]"));
        Assertions.assertEquals(0, emailElements.size());

//        Navigate to Sign In
        WebElement signInAgain = driver.findElement(By.id("sign-in"));
        signInAgain.click();
        Thread.sleep(100);

//        Submit Sign In Form
        WebElement sessionEmail = driver.findElement(By.id("session_email"));
        sessionEmail.sendKeys(emailValue);
        WebElement sessionPassword = driver.findElement(By.id("session_password"));
        sessionPassword.sendKeys("password");
        WebElement submitSignIn = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitSignIn.click();

//        Verify Sign In
        WebElement currentUserSignIn = driver.findElement(By.cssSelector("[data-test=current-user]"));
        String actualEmailSignIn = currentUserSignIn.getText();
        Assertions.assertEquals(emailValue, actualEmailSignIn);

//        Navigate to Address List
        WebElement addressesLink = driver.findElement(By.cssSelector("[data-test=addresses]"));
        addressesLink.click();

//        Navigate to New Address
        WebElement createAddress = driver.findElement(By.cssSelector("[data-test=create]"));
        createAddress.click();

//        Submit New Address Form
        String firstName = "Roger";
        WebElement firstNameField = driver.findElement(By.id("address_first_name"));
        firstNameField.sendKeys(firstName);

        String lastName = "Ramjet";
        WebElement lastNameField = driver.findElement(By.id("address_last_name"));
        lastNameField.sendKeys(lastName);

        String streetAddress = "1234 Main St";
        WebElement streetAddressField = driver.findElement(By.id("address_street_address"));
        streetAddressField.sendKeys(streetAddress);

        String secondaryAddress = "Apt B";
        WebElement secondaryAddressField = driver.findElement(By.id("address_secondary_address"));
        secondaryAddressField.sendKeys(secondaryAddress);

        String city = "Fairview";
        WebElement cityField =   driver.findElement(By.id("address_city"));
        cityField.sendKeys(city);

        String state = "TX";
        WebElement stateField = driver.findElement(By.id("address_state"));
        new Select(stateField).selectByValue(state);

        String zipCode = "74000";
        WebElement zipCodeField = driver.findElement(By.id("address_zip_code"));
        zipCodeField.sendKeys(zipCode);

        String country = "us";
        WebElement countryField = driver.findElement(By.id("address_country_"+ country));
        countryField.click();

        String birthday = "11/12/1980";
        WebElement birthdayField =  driver.findElement(By.id("address_birthday"));
        birthdayField.sendKeys(birthday);

        String age = "38";
        WebElement ageField = driver.findElement(By.id("address_age"));
        ageField.sendKeys(age);

        String website = "http://example.com";
        WebElement websiteField = driver.findElement(By.id("address_website"));
        websiteField.sendKeys(website);

        String phone = "5128675309";
        WebElement phoneField = driver.findElement(By.id("address_phone"));
        phoneField.sendKeys(phone);

        WebElement interestDanceField = driver.findElement(By.id("address_interest_dance"));
        interestDanceField.click();

        String note = "This person is nice.";
        WebElement noteField = driver.findElement(By.id("address_note"));
        noteField.sendKeys(note);

        WebElement submitButton = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitButton.click();

//        Verify new Address
        // Success
        WebElement notice = driver.findElement(By.cssSelector("[data-test=notice]"));
        Assertions.assertEquals("Address was successfully created.", notice.getText());

        // Valid Page
        String url = driver.getCurrentUrl();
        String id = url.substring(url.lastIndexOf('/') + 1);
        Assertions.assertTrue(NumberUtils.isParsable("22"));

        // Correct Content
        WebElement firstNameContent = driver.findElement(By.cssSelector("[data-test=first_name]"));
        Assertions.assertEquals(firstName, firstNameContent.getText());

        WebElement lastNameContent = driver.findElement(By.cssSelector("[data-test=last_name]"));
        Assertions.assertEquals(lastName, lastNameContent.getText());

        WebElement streetAddressContent = driver.findElement(By.cssSelector("[data-test=street_address]"));
        Assertions.assertEquals(streetAddress, streetAddressContent.getText());

        WebElement secondaryAddressContent = driver.findElement(By.cssSelector("[data-test=secondary_address]"));
        Assertions.assertEquals(secondaryAddress, secondaryAddressContent.getText());

        WebElement cityContent = driver.findElement(By.cssSelector("[data-test=city]"));
        Assertions.assertEquals(city, cityContent.getText());

        WebElement stateContent = driver.findElement(By.cssSelector("[data-test=state]"));
        Assertions.assertEquals(state, stateContent.getText());

        WebElement zipCodeContent = driver.findElement(By.cssSelector("[data-test=zip_code]"));
        Assertions.assertEquals(zipCode, zipCodeContent.getText());

        WebElement countryContent = driver.findElement(By.cssSelector("[data-test=country]"));
        Assertions.assertEquals(country, countryContent.getText());

        WebElement birthdayContent = driver.findElement(By.cssSelector("[data-test=birthday]"));
        Assertions.assertEquals(birthday, birthdayContent.getText());

        WebElement ageContent = driver.findElement(By.cssSelector("[data-test=age]"));
        Assertions.assertEquals(age, ageContent.getText());

        WebElement websiteContent = driver.findElement(By.cssSelector("[data-test=website]"));
        Assertions.assertEquals(website, websiteContent.getText());

        WebElement phoneContent = driver.findElement(By.cssSelector("[data-test=phone]"));
        Assertions.assertEquals(phone, phoneContent.getText().replace("-", ""));

        WebElement interestContent = driver.findElement(By.cssSelector("[data-test=interest_dance]"));
        Assertions.assertEquals("Yes", interestContent.getText());

        WebElement noteContent = driver.findElement(By.cssSelector("[data-test=note]"));
        Assertions.assertEquals(note, noteContent.getText());

//        Navigate to Edit Address
        driver.findElement(By.cssSelector("[data-test=edit]")).click();

//        Submit Edit Address Form
        String newName = "Jack";
        WebElement editFirstNameField = driver.findElement(By.id("address_first_name"));
        editFirstNameField.clear();
        editFirstNameField.sendKeys(newName);
        WebElement submitButtonEdit = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitButtonEdit.click();

//        Verify Edit Address
        // Success
        WebElement noticeEdit = driver.findElement(By.cssSelector("[data-test=notice]"));
        Assertions.assertEquals("Address was successfully updated.", noticeEdit.getText());

        // Valid Page
        Assertions.assertEquals(url, driver.getCurrentUrl());

        // Correct Content
        WebElement firstNameEdited = driver.findElement(By.cssSelector("[data-test=first_name]"));
        Assertions.assertEquals(newName, firstNameEdited.getText());

//        Navigate to Address List
        WebElement addressListLink = driver.findElement(By.cssSelector("[data-test=list]"));
        addressListLink.click();

//        Delete Address
        WebElement destroyLink = driver.findElement(By.cssSelector("[data-test=destroy-" + id + "]"));
        destroyLink.click();
        driver.switchTo().alert().accept();

//        Verify Address Deleted
        List<WebElement> destroyedLinks = driver.findElements(By.cssSelector("[data-test^=destroy]"));
        Assertions.assertEquals(0, destroyedLinks.size());
    }
}
