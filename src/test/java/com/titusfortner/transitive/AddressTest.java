package com.titusfortner.transitive;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class AddressTest extends BaseTest {
    String emailValue = faker.internet().emailAddress();
    String passwordValue = faker.internet().password();

    Map<String, String> data = Map.ofEntries(
            entry("firstName", faker.name().firstName()),
            entry("lastName", faker.name().lastName()),
            entry("streetAddress", faker.address().streetAddress()),
            entry("secondaryAddress", faker.address().secondaryAddress()),
            entry("city", faker.address().city()),
            entry("state", faker.address().stateAbbr()),
            entry("zipCode", faker.address().zipCode()),
            entry("country", "us"),
            entry("website", "http://" + faker.internet().url()),
            entry("phone", faker.phoneNumber().cellPhone()),
            entry("note", faker.lorem().paragraph()),
            entry("age", String.valueOf(faker.number().numberBetween(20, 60)))
    );

    @BeforeEach
    public void signUp() {
        driver.get(BASE_URL + "/sign_up");
        WebElement email = driver.findElement(By.id("user_email"));
        email.sendKeys(emailValue);
        WebElement password = driver.findElement(By.id("user_password"));
        password.sendKeys(passwordValue);
        WebElement submit = driver.findElement(By.cssSelector("[data-test=submit]"));
        submit.click();
    }

    @Test
    public void createAddress() {
        driver.get(BASE_URL + "/addresses/new");

        WebElement firstNameField = driver.findElement(By.id("address_first_name"));
        firstNameField.sendKeys(data.get("firstName"));

        WebElement lastNameField = driver.findElement(By.id("address_last_name"));
        lastNameField.sendKeys(data.get("lastName"));

        WebElement streetAddressField = driver.findElement(By.id("address_street_address"));
        streetAddressField.sendKeys(data.get("streetAddress"));

        WebElement secondaryAddressField = driver.findElement(By.id("address_secondary_address"));
        secondaryAddressField.sendKeys(data.get("secondaryAddress"));

        WebElement cityField = driver.findElement(By.id("address_city"));
        cityField.sendKeys(data.get("city"));

        WebElement stateField = driver.findElement(By.id("address_state"));
        new Select(stateField).selectByValue(data.get("state"));

        WebElement zipCodeField = driver.findElement(By.id("address_zip_code"));
        zipCodeField.sendKeys(data.get("zipCode"));

        WebElement countryField = driver.findElement(By.id("address_country_" + data.get("country")));
        countryField.click();

        WebElement ageField = driver.findElement(By.id("address_age"));
        ageField.sendKeys(data.get("age"));

        WebElement websiteField = driver.findElement(By.id("address_website"));
        websiteField.sendKeys(data.get("website"));

        WebElement phoneField = driver.findElement(By.id("address_phone"));
        phoneField.sendKeys(data.get("phone"));

        WebElement noteField = driver.findElement(By.id("address_note"));
        noteField.sendKeys(data.get("note"));

        WebElement submitButton = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitButton.click();

        WebElement notice = driver.findElement(By.cssSelector("[data-test=notice]"));
        Assertions.assertEquals("Address was successfully created.", notice.getText());

        String url = driver.getCurrentUrl();
        String id = url.substring(url.lastIndexOf('/') + 1);
        Assertions.assertTrue(NumberUtils.isParsable(id));

        WebElement firstNameContent = driver.findElement(By.cssSelector("[data-test=first_name]"));
        Assertions.assertEquals(data.get("firstName"), firstNameContent.getText());

        WebElement lastNameContent = driver.findElement(By.cssSelector("[data-test=last_name]"));
        Assertions.assertEquals(data.get("lastName"), lastNameContent.getText());

        WebElement streetAddressContent = driver.findElement(By.cssSelector("[data-test=street_address]"));
        Assertions.assertEquals(data.get("streetAddress"), streetAddressContent.getText());

        WebElement secondaryAddressContent = driver.findElement(By.cssSelector("[data-test=secondary_address]"));
        Assertions.assertEquals(data.get("secondaryAddress"), secondaryAddressContent.getText());

        WebElement cityContent = driver.findElement(By.cssSelector("[data-test=city]"));
        Assertions.assertEquals(data.get("city"), cityContent.getText());

        WebElement stateContent = driver.findElement(By.cssSelector("[data-test=state]"));
        Assertions.assertEquals(data.get("state"), stateContent.getText());

        WebElement zipCodeContent = driver.findElement(By.cssSelector("[data-test=zip_code]"));
        Assertions.assertEquals(data.get("zipCode"), zipCodeContent.getText());

        WebElement countryContent = driver.findElement(By.cssSelector("[data-test=country]"));
        Assertions.assertEquals(data.get("country"), countryContent.getText());

        WebElement ageContent = driver.findElement(By.cssSelector("[data-test=age]"));
        Assertions.assertEquals(data.get("age"), ageContent.getText());

        WebElement websiteContent = driver.findElement(By.cssSelector("[data-test=website]"));
        Assertions.assertEquals(data.get("website"), websiteContent.getText());

        WebElement phoneContent = driver.findElement(By.cssSelector("[data-test=phone]"));
        Assertions.assertEquals(data.get("phone"), phoneContent.getText());

        WebElement noteContent = driver.findElement(By.cssSelector("[data-test=note]"));
        Assertions.assertEquals(data.get("note"), noteContent.getText());
    }

    @Test
    public void editAddress() {
        driver.get(BASE_URL + "/addresses/new");

        WebElement firstNameField = driver.findElement(By.id("address_first_name"));
        firstNameField.sendKeys(data.get("firstName"));

        WebElement lastNameField = driver.findElement(By.id("address_last_name"));
        lastNameField.sendKeys(data.get("lastName"));

        WebElement streetAddressField = driver.findElement(By.id("address_street_address"));
        streetAddressField.sendKeys(data.get("streetAddress"));

        WebElement secondaryAddressField = driver.findElement(By.id("address_secondary_address"));
        secondaryAddressField.sendKeys(data.get("secondaryAddress"));

        WebElement cityField = driver.findElement(By.id("address_city"));
        cityField.sendKeys(data.get("city"));

        WebElement stateField = driver.findElement(By.id("address_state"));
        new Select(stateField).selectByValue(data.get("state"));

        WebElement zipCodeField = driver.findElement(By.id("address_zip_code"));
        zipCodeField.sendKeys(data.get("zipCode"));

        WebElement countryField = driver.findElement(By.id("address_country_" + data.get("country")));
        countryField.click();

        WebElement ageField = driver.findElement(By.id("address_age"));
        ageField.sendKeys(data.get("age"));

        WebElement websiteField = driver.findElement(By.id("address_website"));
        websiteField.sendKeys(data.get("website"));

        WebElement phoneField = driver.findElement(By.id("address_phone"));
        phoneField.sendKeys(data.get("phone"));

        WebElement noteField = driver.findElement(By.id("address_note"));
        noteField.sendKeys(data.get("note"));

        WebElement submitButton = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitButton.click();

        String url = driver.getCurrentUrl();
        String id = url.substring(url.lastIndexOf('/') + 1);

        driver.get(BASE_URL + "/addresses/" + id + "/edit");

        String newName = "Jack";
        WebElement editFirstNameField = driver.findElement(By.id("address_first_name"));
        editFirstNameField.clear();
        editFirstNameField.sendKeys(newName);
        WebElement submitButtonEdit = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitButtonEdit.click();

        WebElement noticeEdit = driver.findElement(By.cssSelector("[data-test=notice]"));
        Assertions.assertEquals("Address was successfully updated.", noticeEdit.getText());

        // Valid Page
        Assertions.assertEquals(url, driver.getCurrentUrl());

        // Correct Content
        WebElement firstNameEdited = driver.findElement(By.cssSelector("[data-test=first_name]"));
        Assertions.assertEquals(newName, firstNameEdited.getText());
    }

    @Test
    public void deleteAddress() {
        driver.get(BASE_URL + "/addresses/new");

        WebElement firstNameField = driver.findElement(By.id("address_first_name"));
        firstNameField.sendKeys(data.get("firstName"));

        WebElement lastNameField = driver.findElement(By.id("address_last_name"));
        lastNameField.sendKeys(data.get("lastName"));

        WebElement streetAddressField = driver.findElement(By.id("address_street_address"));
        streetAddressField.sendKeys(data.get("streetAddress"));

        WebElement secondaryAddressField = driver.findElement(By.id("address_secondary_address"));
        secondaryAddressField.sendKeys(data.get("secondaryAddress"));

        WebElement cityField = driver.findElement(By.id("address_city"));
        cityField.sendKeys(data.get("city"));

        WebElement stateField = driver.findElement(By.id("address_state"));
        new Select(stateField).selectByValue(data.get("state"));

        WebElement zipCodeField = driver.findElement(By.id("address_zip_code"));
        zipCodeField.sendKeys(data.get("zipCode"));

        WebElement countryField = driver.findElement(By.id("address_country_" + data.get("country")));
        countryField.click();

        WebElement ageField = driver.findElement(By.id("address_age"));
        ageField.sendKeys(data.get("age"));

        WebElement websiteField = driver.findElement(By.id("address_website"));
        websiteField.sendKeys(data.get("website"));

        WebElement phoneField = driver.findElement(By.id("address_phone"));
        phoneField.sendKeys(data.get("phone"));

        WebElement noteField = driver.findElement(By.id("address_note"));
        noteField.sendKeys(data.get("note"));

        WebElement submitButton = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitButton.click();

        String url = driver.getCurrentUrl();
        String id = url.substring(url.lastIndexOf('/') + 1);

        driver.get(BASE_URL + "/addresses");

        WebElement destroyLink = driver.findElement(By.cssSelector("[data-test=destroy-" + id + "]"));
        destroyLink.click();
        driver.switchTo().alert().accept();

        List<WebElement> destroyedLinks = driver.findElements(By.cssSelector("[data-test^=destroy]"));
        Assertions.assertEquals(0, destroyedLinks.size());
    }
}
