package com.titusfortner.transitive;

import com.titusfortner.transitive.api.Address;
import com.titusfortner.transitive.api.Authentication;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class AddressTest extends BaseTest {
    String emailValue = faker.internet().emailAddress();
    String passwordValue = faker.internet().password();

    Map<String, String> data;
    Authentication authAPI;
    Address addressAPI;

    @BeforeEach
    public void signUp() {
        data = Map.ofEntries(
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
        authAPI = new Authentication();
        addressAPI = new Address();
        driver.get(BASE_URL);
        authAPI.createUser(emailValue, passwordValue);
        Cookie authCookie = new Cookie("remember_token", authAPI.rememberToken);
        driver.manage().addCookie(authCookie);
    }

    @Test
    public void createAddressUI() {
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

        // UI Assertions

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
    public void createAddressAPI() {
        Map<String, String> address = addressAPI.createAddress(data, authAPI.rememberToken);
        String id = String.valueOf(address.get("id"));

        // UI Assertions
        driver.get(BASE_URL + "/addresses/" + id);

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

        // API Assertions
        Map<String, String> addressResult = addressAPI.getAddress(id, authAPI.rememberToken);
        for (String name : data.keySet())
            Assertions.assertEquals(data.get(name), addressResult.get(name));
    }

    @Test
    public void editAddress() {
        Map<String, String> address = addressAPI.createAddress(data, authAPI.rememberToken);
        String id = String.valueOf(address.get("id"));

        driver.get(BASE_URL + "/addresses/" + id + "/edit");

        HashMap<String, String> editedData = new HashMap<>(data);
        editedData.put("firstName", "Jack");

        WebElement firstName = driver.findElement(By.id("address_first_name"));
        firstName.clear();
        firstName.sendKeys(editedData.get("firstName"));
        WebElement submitButtonEdit = driver.findElement(By.cssSelector("[data-test=submit]"));
        submitButtonEdit.click();

        WebElement noticeEdit = driver.findElement(By.cssSelector("[data-test=notice]"));
        Assertions.assertEquals("Address was successfully updated.", noticeEdit.getText());

        Map<String, String> addressResult = addressAPI.getAddress(id, authAPI.rememberToken);
        for (String name : editedData.keySet())
            Assertions.assertEquals(editedData.get(name), addressResult.get(name));
    }

    @Test
    public void deleteAddress() throws InterruptedException {
        Map<String, String> address = addressAPI.createAddress(data, authAPI.rememberToken);
        String id = String.valueOf(address.get("id"));

        driver.get(BASE_URL + "/addresses");

        WebElement destroyLink = driver.findElement(By.cssSelector("[data-test=destroy-" + id + "]"));
        destroyLink.click();
        driver.switchTo().alert().accept();

        Thread.sleep(500);
        Assertions.assertNull(addressAPI.getAddress(id, authAPI.rememberToken));
    }
}
