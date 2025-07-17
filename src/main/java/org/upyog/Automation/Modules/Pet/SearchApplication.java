package org.upyog.Automation.Modules.Pet;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

//@Component
public class SearchApplication {




    //@PostConstruct
    public void searchApplication() {
        System.out.println("Search Application");


        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // After logging in (or wherever you want to open a new tab)
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));



        try {


            driver.get("https://upyog.niua.org/upyog-ui/employee/user/login");
            driver.manage().window().maximize();
            System.out.println("Open the Login Portal");

            // Wait for the username input field to be visible and enter the username
            WebElement usernameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("username"))
            );
            usernameInput.clear();
            usernameInput.sendKeys("PTRCEMP"); // Replace with actual username

            // Wait for the password input field to be visible and enter the password
            WebElement passwordInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("password"))
            );
            passwordInput.clear();
            passwordInput.sendKeys("eGov@123"); // Replace with actual password

            System.out.println("filled username and password ");

            // 1. Wait for the city dropdown input to be clickable
            WebElement cityDropdownInput = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("div.select input.employee-select-wrap--elipses"))
            );

            // 2. Click the SVG (dropdown arrow) to open the dropdown
            WebElement cityDropdownContainer = driver.findElement(By.cssSelector("div.select"));
            WebElement cityDropdownArrow = cityDropdownContainer.findElement(By.tagName("svg"));
            cityDropdownArrow.click(); // Or use Actions for reliability

            // Alternatively, using Actions for more robust clicking:
            Actions actions = new Actions(driver);
            actions.moveToElement(cityDropdownArrow).click().perform();

            // 3. Wait for the dropdown options to appear
            WebElement dropdownOptions = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.options-card"))
            );

            // 4. Select the first option (City A)
            WebElement firstCityOption = dropdownOptions.findElement(By.cssSelector(".profile-dropdown--item:first-child"));
            actions.moveToElement(firstCityOption).click().perform();


            // Wait for the Continue button to be clickable
            WebElement continueButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(@class, 'submit-bar') and .//header[text()='Continue']]")
                    )
            );

            // Scroll to the button (optional, improves reliability)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);

            // Click the button
            continueButton.click();


            //Search Application button
            // Wait for the "Search Application" link to be clickable
            WebElement searchAppLink = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[@href='/upyog-ui/employee/ptr/petservice/my-applications' and contains(normalize-space(),'Search Application')]")
                    )
            );

// Optionally scroll to the link for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchAppLink);
            Thread.sleep(300); // Optional for UI stability

// Click the link
            searchAppLink.click();


            System.out.println("Going to fill mobile number ");






//-------------mobile number input-------------------
            String mobile = "9999999999";
            int attempts = 0;
            while (attempts < 3) {
                try {
                    System.out.println("Attempt " + (attempts + 1) + ": Waiting for mobile number input to be clickable...");
                    WebElement mobileInput = wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.cssSelector("input.employee-card-input[name='mobileNumber']")
                            )
                    );
                    System.out.println("Input found. Clearing any pre-filled value...");
                    mobileInput.clear();

                    System.out.println("Entering mobile number digit by digit...");
                    for (char c : mobile.toCharArray()) {
                        mobileInput.sendKeys(Character.toString(c));
                        System.out.println("Entered digit: " + c);
                        Thread.sleep(100); // Small delay for UI stability
                    }
                    System.out.println("Mobile number entry successful.");
                    break; // Success, exit loop
                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    attempts++;
                    System.out.println("StaleElementReferenceException caught on attempt " + attempts + ". Retrying...");
                    if (attempts == 3) {
                        System.out.println("Failed after 3 attempts. Throwing exception.");
                        throw e; // Only rethrow after 3 failed attempts
                    }
                    Thread.sleep(500); // Wait a bit before retrying
                }
            }




//----------------submit button---------------
            WebElement searchButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Search']]")
                    )
            );

// Optionally, scroll into view for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchButton);
            Thread.sleep(300); // Optional for UI stability

// Click the button
            searchButton.click();




















            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //driver.quit(); // Ensure browser closes
        }
        }


    }



