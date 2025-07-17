package org.upyog.Automation.Modules.TradeLicense;


import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;




//@Component
public class TradeLicenseCreate {



    public void uploadDocumentAndClickNext(WebDriver driver, WebDriverWait wait, String filePath) throws InterruptedException {
        // Wait for the file input to be present
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("tl-doc"))
        );
        System.out.println("Uploading document: " + filePath);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", fileInput);
        Thread.sleep(200);
        fileInput.sendKeys(filePath);
        System.out.println("Document uploaded.");

        // Wait for and click the Next button
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='submit-bar ' and @type='submit'][.//header[text()='Next']]")
        ));
        System.out.println("Clicking Next...");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton);
        Thread.sleep(200);
        nextButton.click();
        System.out.println("Next button clicked.");
    }


    public void clickButtonByHeader(WebDriver driver, WebDriverWait wait, String headerText) {
        try {
            System.out.println("[DEBUG] Waiting for button with header: " + headerText);
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'submit-bar') and .//header[contains(text(),'" + headerText + "')]]")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            System.out.println("[SUCCESS] Clicked button with header: " + headerText);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("[ERROR] Exception occurred while clicking button: " + headerText + " - " + e.getMessage());
            throw new RuntimeException("Failed to click button: " + headerText, e);
        }
    }




    public void selectRadioButtonByLabel(WebDriver driver, String labelText) {
        WebElement radio = driver.findElement(By.xpath("//label[text()='"+labelText+"']/preceding-sibling::span/input"));
        if(!radio.isSelected()) {
            radio.click();
        }
    }






   // @PostConstruct
    public void TradeLicenseReg(){
        System.out.println("New Trade License Registration");

        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");


        options.addArguments("--disable-autofill");
        options.addArguments("--disable-autofill-keyboard-accessory-view");
        options.addArguments("--disable-full-form-autofill-ios");
        options.addArguments("--disable-save-password-bubble");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("autofill.address_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try{

            // Step 1: Navigate and login
            driver.get("https://niuatt.niua.in/digit-ui/citizen/login");

            WebElement mobileNumberInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("mobileNumber")
            ));
            mobileNumberInput.clear();
            mobileNumberInput.sendKeys("9999999999");

            //checking the check box
            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='checkbox'].form-field")
            ));
            if (!checkbox.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                Thread.sleep(1000);
            }

            //clicking continue
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='submit']//header[text()='Next']/..")
            ));
            nextButton.click();

            //filling otp for login
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.input-otp-wrap")
            ));
            List<WebElement> otpInputs = driver.findElements(By.cssSelector("input.input-otp"));
            String otp = "123456";
            for (int i = 0; i < otp.length() && i < otpInputs.size(); i++) {
                otpInputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
            }

            //continue with login button
            WebElement nextAfterOtpButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='submit']//header[text()='Next']/..")
            ));
            nextAfterOtpButton.click();




            //select the city
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.radio-wrap.reverse-radio-selection-wrapper")
            ));

            boolean citySelected = false;
            List<WebElement> cityOptions = driver.findElements(
                    By.cssSelector("div.radio-wrap.reverse-radio-selection-wrapper div")
            );

            for (WebElement option : cityOptions) {
                WebElement label = option.findElement(By.tagName("label"));
                if (label.getText().trim().equals("City A")) {
                    WebElement radioInput = option.findElement(By.cssSelector("input[type='radio']"));
                    if (!radioInput.isSelected()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioInput);
                        Thread.sleep(1000);
                    }
                    citySelected = true;
                    break;
                }
            }

            if (!citySelected) {
                throw new RuntimeException("Failed to select City A");
            }
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'submit-bar') and contains(., 'Continue')]")
            ));

            // continue to home page

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueBtn);
            new Actions(driver).moveToElement(continueBtn).click().perform();


            //after login into the system
            // clicking the Pet Registration

            WebElement TlSidebarLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[@href='/digit-ui/citizen/tl-home']")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", TlSidebarLink);


            //Register Trade button
            //it opens up the required document page

            WebElement registerTlLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'CitizenHomeCard')]//a[text()='Apply for Trade License']")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerTlLink);

            // clicking Next Button
            WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span/button[contains(@class,'submit-bar')]//header[normalize-space()='Next']/..")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextBtn);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);

            //Business name
            // Wait for the "Institute" input field to be visible and enabled
            WebElement instituteInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("TradeName"))
            );

            // Clear any existing value (optional but recommended)
            instituteInput.clear();

            // Fill the input using sendKeys (standard and usually works)
            instituteInput.sendKeys(" Institute ");

            clickButtonByHeader(driver, wait, "Next");

            //Radio button 1

            selectRadioButtonByLabel(driver, "Yes");



            // clicking Next Button
            // Wait for the "Next" button to be clickable
            WebElement nextButtonforsubcate = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Next']]")
                    )
            );
            // Optionally, scroll into view for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButtonforsubcate);
            Thread.sleep(300); // Optional, helps with dynamic UIs
            // Click the button
            nextButtonforsubcate.click();

            System.out.println("page sourcess");
            //System.out.println(driver.getPageSource());





            Thread.sleep(3000);
            selectRadioButtonByLabel(driver, "Kutcha");



            WebDriverWait waitForCity = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextButton2 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Next']]")
            ));
            // Scroll into view (optional, for reliability)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton2);
            // Click the button
            nextButton2.click();




            // Wait for the date input to be visible
            WebDriverWait waitfordate = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dateInput = waitfordate.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input.employee-card-input[type='date']"))
            );
            // Set the date (20th May 2025)
            dateInput.clear(); // Optional: clear existing value
            dateInput.sendKeys("20-05-2025");

            //clicking next buttonn


            WebElement nextButton3 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Next']]")
            ));
            // Scroll into view (optional, for reliability)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton3);
            // Click the button
            nextButton3.click();


            System.out.println("selecting the trade units");
            Thread.sleep(3000);

            selectRadioButtonByLabel(driver, "Goods");




            Thread.sleep(5000);
            // Wait for the dropdown SVG to be clickable and click it to open the dropdown
            WebDriverWait waitfordropdown1 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdownSvg = waitfordropdown1.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("div.select svg.cp"))
            );
            dropdownSvg.click();
            // Wait for the dropdown options to be visible
            WebElement optionsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.options-card"))
            );
            // Find all options inside the dropdown
            List<WebElement> optionsfordropdown1 = optionsContainer.findElements(By.cssSelector("div.profile-dropdown--item"));
            // Click the first option (index 0)
            if (!optionsfordropdown1.isEmpty()) {
                optionsfordropdown1.get(0).click();
            }

            //2nd dropdown

            // Wait for all dropdown SVGs to be present
            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> dropdownSvgs = wait3.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div.select svg.cp"))
            );
            // Click the SVG of the second dropdown (index 1)
            dropdownSvgs.get(1).click();
            // Wait for the dropdown options to be visible
            WebElement optionsContainer2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.options-card"))
            );
            // Find all options inside the dropdown
            List<WebElement> options33 = optionsContainer2.findElements(By.cssSelector("div.profile-dropdown--item"));
            // Click the first option (index 0)
            if (!options33.isEmpty()) {
                options33.get(0).click();
            }


            // Wait for the input to be visible
            WebDriverWait waitforUom = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement uomInput = waitforUom.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("UomValue"))
            );
            // Clear any existing value (optional but recommended)
            uomInput.clear();
            // Fill the input with "6"
            uomInput.sendKeys("6");





            WebElement nextButton4 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Next']]")
            ));
            // Scroll into view (optional, for reliability)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton4);
            // Click the button
            nextButton4.click();


            Thread.sleep(3000);

            selectRadioButtonByLabel(driver, "No");




            WebElement nextButton5 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Next']]")
            ));
            // Scroll into view (optional, for reliability)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton5);
            // Click the button
            nextButton5.click();



//9876788
            WebDriverWait waitforgst = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement gstInput = waitforgst.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("TradeGSTNumber"))
            );
            // Clear any existing value (recommended)
            gstInput.clear();
            // Fill the input with the GST number
            gstInput.sendKeys("10SEIJI3622O0Z4");



            // Wait for the input to be visible
            WebDriverWait waitforoparea = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement areaInput = waitforoparea.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("OperationalSqFtArea"))
            );
            // Clear any existing value (recommended)
            areaInput.clear();
            // Fill the input with 454
            areaInput.sendKeys("454");



            // Wait for the input to be visible
            WebDriverWait waitforemp = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement employeesInput = waitforemp.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("NumberOfEmployees"))
            );
            // Clear any existing value (recommended)
            employeesInput.clear();
            // Fill the input with 10
            employeesInput.sendKeys("10");



            WebElement nextButton6 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Next']]")
            ));
            // Scroll into view (optional, for reliability)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton6);
            // Click the button
            nextButton6.click();


            //radio button implementation

            selectRadioButtonByLabel(driver, "No");



            WebElement nextButton7 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Next']]")
            ));

            // Scroll into view (optional, for reliability)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton7);

            // Click the button
            nextButton7.click();





            //create property page

           // Wait for the dropdown SVG to be clickable and click it to open the dropdown
            WebDriverWait waitforproperty = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdownSvgforproperty = waitforproperty.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("div.select svg.cp"))
            );
            dropdownSvgforproperty.click();
            // Wait for the dropdown options to be visible
            WebElement optionsContainerproperty = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.options-card"))
            );
            // Find all options inside the dropdown
            List<WebElement> optionsproperty = optionsContainerproperty.findElements(By.cssSelector("div.profile-dropdown--item"));
            // Click the first option (index 0)
            if (!optionsproperty.isEmpty()) {
                optionsproperty.get(0).click();
            }



            // Wait for the input to be visible
            WebDriverWait waitforarea = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement landAreaInput = waitforarea.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("totLandArea"))
            );
            // Clear any existing value (recommended)
            landAreaInput.clear();
            // Fill the input with 345
            landAreaInput.sendKeys("345");


            // Wait for the input to be visible
            WebDriverWait waitusedarea = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement constructionAreaInput = waitusedarea.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("totConstructionArea"))
            );
            // Clear any existing value (recommended)
            constructionAreaInput.clear();
            // Fill the input with 87
            constructionAreaInput.sendKeys("87");





            Thread.sleep(1000);


            System.out.println("going in uses dropdown");

            // Wait for all dropdown SVGs to be present
            WebDriverWait waitforconsarea = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> dropdownSvgsarea = waitforconsarea.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div.select svg.cp"))
            );
            // Click the second dropdown's SVG (index 1)
            dropdownSvgsarea.get(1).click();
            // Wait for the dropdown options to be visible
            WebElement optionsContainerarea = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.options-card"))
            );
            // Find all options inside the dropdown
            List<WebElement> optionsforarea = optionsContainerarea.findElements(By.cssSelector("div.profile-dropdown--item"));
            // Click the first option ("Others")
            if (!optionsforarea.isEmpty()) {
                optionsforarea.get(0).click();
            }


            System.out.println("going for city");

            try {
                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));

                // Wait for all dropdown SVGs to be visible
                List<WebElement> dropdownSvgs1 = wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("div.select svg.cp"))
                );
                System.out.println("[DEBUG] Number of dropdown SVGs found: " + dropdownSvgs1.size());

                // Ensure you have at least 3 dropdowns (index 2 for the third one)
                if (dropdownSvgs1.size() < 3) {
                    throw new Exception("Less than 3 dropdown SVGs found on the page!");
                }

                // Scroll the third dropdown's SVG (index 2) into view and click it
                WebElement cityDropdownSvg = dropdownSvgs1.get(2);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", cityDropdownSvg);
                Thread.sleep(300); // Ensures scroll finishes
                cityDropdownSvg.click();
                Thread.sleep(500); // Wait for dropdown animation/overlay to finish

                // Wait for the dropdown options to be visible
                WebElement optionsContainer1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.options-card"))
                );

                // Find all options inside the dropdown
                List<WebElement> options1 = optionsContainer1.findElements(By.cssSelector("div.profile-dropdown--item"));
                //System.out.println("[DEBUG] Number of dropdown options found: " + options.size());

                // Click the option whose text is "City A" using JavaScript to avoid click interception
                boolean found = false;
                for (WebElement option : options1) {
                    String optionText = option.getText().trim();
                    if (optionText.equals("City A")) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                        Thread.sleep(200); // Let scroll finish
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                        found = true;
                        System.out.println("[SUCCESS] Selected City A from the dropdown using JS click.");
                        break;
                    }
                }
                if (!found) {
                    System.out.println("[ERROR] City A not found in the dropdown options!");
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Exception occurred while selecting City A: " + e.getMessage());
                throw e;
            }


            System.out.println("going for mohalla");


            // Wait for all dropdown SVGs to be present
            WebDriverWait waitforlocality = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> dropdownSvgsforlocality = waitforlocality.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div.select svg.cp"))
            );
            // Click the fourth dropdown's SVG (index 3)
            dropdownSvgsforlocality.get(3).click();
            // Wait for the dropdown options to be visible
            WebElement optionsContainerforlocality = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.options-card"))
            );
            // Find all options inside the dropdown
            List<WebElement> optionsforlocality = optionsContainerforlocality.findElements(By.cssSelector("div.profile-dropdown--item"));
            // Click the first option ("Ajit Nagar - Area1")
            if (!optionsforlocality.isEmpty()) {
                optionsforlocality.get(0).click();
            }


            System.out.println("Door no.");
            // Wait for the input to be visible
            WebDriverWait waitfordoorno = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement houseDoorNoInput = waitfordoorno.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("houseDoorNo"))
            );
            // Clear any existing value (recommended)
            houseDoorNoInput.clear();
            // Fill the input with 445
            houseDoorNoInput.sendKeys("445");


            System.out.println("street name");

            // Wait for the input to be visible
            WebDriverWait waitforstreet = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement colonyNameInput = waitforstreet.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("buildingColonyName"))
            );
            // Clear any existing value (recommended)
            colonyNameInput.clear();
            // Fill the input with "chruch street"
            colonyNameInput.sendKeys("chruch street");


            System.out.println("landmark");

            // Wait for the input to be visible
            WebDriverWait waitforlandmark = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement landmarkInput = waitforlandmark.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("landmarkName"))
            );
            // Clear any existing value (recommended)
            landmarkInput.clear();
            // Fill the input with "cirle point"
            landmarkInput.sendKeys("cirle point");








            // Wait for all dropdown containers
            // Wait for all dropdown SVGs to be present
            WebDriverWait waitforowner = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> dropdownSvgsforwoner = waitforowner.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div.select svg.cp"))
            );
            // Scroll and click the SVG with Actions
            WebElement ownerDropdownSvg = dropdownSvgsforwoner.get(4);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", ownerDropdownSvg);
            Thread.sleep(300);

            Actions actions = new Actions(driver);
            actions.moveToElement(ownerDropdownSvg).click().perform();
            // Wait for the dropdown options to be present and visible
            WebDriverWait waitforownership = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement optionsContainerforowner = waitforownership.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.options-card"))
            );
            // Find and click the first option
            List<WebElement> optionsforowner = optionsContainerforowner.findElements(By.cssSelector("div.profile-dropdown--item"));
            if (!optionsforowner.isEmpty()) {
                optionsforowner.get(2).click();
            }


            Thread.sleep(1000);

            System.out.println("mobile number");
            // Wait for the input to be visible
            WebDriverWait waitforphno = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement mobileInput = waitforphno.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("mobileNumber0"))
            );
            // Clear any existing value (recommended)
            mobileInput.clear();
            // Fill the input with "9494848897"
            mobileInput.sendKeys("9494848897");








            // Wait for the input to be visible
            WebDriverWait waitforownername = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nameInput = waitforownername.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("name0"))
            );
            // Clear any existing value (recommended)
            nameInput.clear();
            // Fill the input with "atul"
            nameInput.sendKeys("atul");


            // Wait for the radio buttons to be present
            WebDriverWait waitforgender = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> radioDivs = waitforgender.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".radio-wrap > div"))
            );
            // Loop through each radio option and select "Male"
            for (WebElement radioDiv : radioDivs) {
                WebElement label = radioDiv.findElement(By.tagName("label"));
                if (label.getText().trim().equalsIgnoreCase("Male")) {
                    WebElement radioInput = radioDiv.findElement(By.cssSelector("input[type='radio']"));
                    // Scroll the radio input into view
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", radioInput);
                    Thread.sleep(200); // Optional: ensure scroll finishes
                    // Click using JavaScript
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioInput);
                    break;
                }
            }



            // Wait for the input to be visible
            WebDriverWait waitforfather = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement fatherInput = waitforfather.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("fatherOrHusbandName0"))
            );
            // Clear any existing value (recommended)
            fatherInput.clear();
            // Fill the input with "father"
            fatherInput.sendKeys("father");


            // Wait for the radio buttons to be present
            WebDriverWait waitforrelationship = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> radioDivsforrelationship = waitforrelationship.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".radio-wrap > div"))
            );
            // Loop through each radio option and select "Husband"
            for (WebElement radioDiv : radioDivsforrelationship) {
                WebElement label = radioDiv.findElement(By.tagName("label"));
                if (label.getText().trim().equalsIgnoreCase("Husband")) {
                    WebElement radioInput = radioDiv.findElement(By.cssSelector("input[type='radio']"));
                    // Scroll the radio input into view (optional but robust)
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", radioInput);
                    Thread.sleep(200); // Optional: ensure scroll finishes
                    // Click using JavaScript
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioInput);
                    break;
                }
            }


            // 1. Wait for all dropdown containers to be visible
            WebDriverWait waitforlast = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> dropdownDivs = waitforlast.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div.select"))
            );
            // 2. Scroll the 6th dropdown (index 5) into view
            WebElement sixthDropdownDiv = dropdownDivs.get(5);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", sixthDropdownDiv);
            Thread.sleep(300); // Optional: ensures scroll finishes
            // 3. Click the SVG icon inside the 6th dropdown using Actions
            WebElement svgIcon = sixthDropdownDiv.findElement(By.cssSelector("svg.cp"));
            Actions actionsforlast = new Actions(driver);
            actionsforlast.moveToElement(svgIcon).click().perform();
            // 4. Wait for the dropdown options to appear
            WebElement optionsContainerforlast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.options-card"))
            );
            // 5. Find all options
            List<WebElement> optionsforlast = optionsContainerforlast.findElements(By.cssSelector("div.profile-dropdown--item"));
            // 6. Select the desired option by text (e.g., "BPL")
            String desiredOption = "BPL";
            for (WebElement option : optionsforlast) {
                if (option.getText().trim().equalsIgnoreCase(desiredOption)) {
                    option.click();
                    break;
                }
            }






            // Wait for the textarea to be visible
            WebDriverWait waitfortextarea = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement addressTextarea = waitfortextarea.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("address0"))
            );
            // Clear any existing value (recommended)
            addressTextarea.clear();
            // Fill the textarea with "nsdkfnsdfsf"
            addressTextarea.sendKeys("nsdkfnsdfsf");


            // Wait for the SUBMIT button to be visible
            WebDriverWait waitforsubmit = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement submitButton = waitforsubmit.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("button.submit-bar[type='submit']"))
            );
            // Scroll into view (optional for robustness)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButton);
            Thread.sleep(200); // Optional: ensures scroll finishes
            // Click the SUBMIT button
            submitButton.click();


            // Wait for the Proceed button to be visible
            WebDriverWait waitforproceed = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement proceedButton = waitforproceed.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("button.submit-bar[type='button']"))
            );
            // Scroll into view (optional for robustness)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", proceedButton);
            Thread.sleep(200); // Optional: ensures scroll finishes
            // Click the Proceed button
            proceedButton.click();



            // Wait for the Next button to be visible
            WebDriverWait waitfornextt = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextButton8 = waitfornextt.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[@class='submit-bar ' and @type='button' and header='Next'] | //button[@class='submit-bar ' and @type='button'][.//header[text()='Next']]")
            ));
            // Scroll into view (optional for robustness)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton8);
            Thread.sleep(200); // Optional: ensures scroll finishes
            // Click the Next button
            nextButton8.click();





            // Wait for the radio options to be visible
            WebDriverWait waitforowns = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> radioDivsforowns = waitforowns.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".radio-wrap > div"))
            );
            // Loop through each radio option and select "Institutional - Government"
            for (WebElement radioDiv : radioDivsforowns) {
                WebElement label = radioDiv.findElement(By.tagName("label"));
                if (label.getText().trim().equalsIgnoreCase("Institutional - Government")) {
                    WebElement radioInput = radioDiv.findElement(By.cssSelector("input[type='radio']"));
                    // Scroll the radio input into view (optional but robust)
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", radioInput);
                    Thread.sleep(200); // Optional: ensure scroll finishes
                    // Click using JavaScript (robust for custom radios)
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioInput);
                    break;
                }
            }


            // Wait for the Next submit button to be visible
            WebDriverWait waitfir = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextSubmitButton = waitfir.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[@class='submit-bar ' and @type='submit'][.//header[text()='Next']]")
            ));
            // Scroll into view (optional for robustness)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextSubmitButton);
            Thread.sleep(200); // Optional: ensures scroll finishes
            // Click the Next submit button
            nextSubmitButton.click();



            // Wait for the input to be visible
            WebDriverWait waitforname = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement institutionInput = waitforname.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("institutionName"))
            );
            // Clear any existing value (recommended)
            institutionInput.clear();
            // Fill the input with "kunal gaurav"
            institutionInput.sendKeys("kunal gaurav");



            // 1. Wait for all dropdown containers to be visible
            WebDriverWait waitforisn = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> dropdownDivstt = waitforisn.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div.select"))
            );
            // 2. Scroll the first dropdown (index 0) into view
            WebElement firstDropdownDiv = dropdownDivstt.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", firstDropdownDiv);
            Thread.sleep(300); // Optional: ensures scroll finishes
            // 3. Click the SVG icon inside the first dropdown using Actions
            WebElement svgIcontt = firstDropdownDiv.findElement(By.cssSelector("svg.cp"));
            Actions actionstt = new Actions(driver);
            actionstt.moveToElement(svgIcontt).click().perform();
            // 4. Wait for the dropdown options to appear
            WebElement optionsContainertt = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.options-card"))
            );
            // 5. Find all dropdown options
            List<WebElement> optionsttt = optionsContainertt.findElements(By.cssSelector("div.profile-dropdown--item"));
            // 6. Select the desired option by text (e.g., "Central Government")
            String desiredOptiontt = "Central Government"; // <-- Change this as needed
            for (WebElement option : optionsttt) {
                if (option.getText().trim().equalsIgnoreCase(desiredOptiontt)) {
                    option.click();
                    break;
                }
            }



            // Wait for the input to be visible
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nameInput1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("name"))
            );
            // Clear any existing value (recommended)
            nameInput1.clear();
            // Fill the input with "sourabh"
            nameInput1.sendKeys("sourabh");


            // Wait for the input to be visible
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement designationInput = wait2.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("designation"))
            );
            // Clear any existing value (recommended)
            designationInput.clear();
            // Fill the input with "officer"
            designationInput.sendKeys("officer");


            // Wait for the mobile number input to be visible
            WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement mobileInput1 = wait4.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("mobilenumber"))
            );
            // Clear any existing value (recommended)
            mobileInput1.clear();
            // Fill the input with "8474857362"
            mobileInput1.sendKeys("8474857362");



            // Wait for the Next submit button to be visible
            WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextSubmitButton6 = wait5.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[@class='submit-bar ' and @type='submit'][.//header[text()='Next']]")
            ));
            // Scroll the button into view (centered)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextSubmitButton6);
            Thread.sleep(200); // Optional: ensures scroll finishes
            // Click the Next submit button
            nextSubmitButton6.click();


            Thread.sleep(3000);

            By checkboxLocator = By.cssSelector("input[type='checkbox'][value=\"Same as Trade's Address\"]");
            WebElement checkbox2 = driver.findElement(checkboxLocator);

            if (!checkbox2.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox2);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox2);
                System.out.println("Checkbox clicked using JavaScript.");
            } else {
                System.out.println("Checkbox already selected.");
            }







            WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextSubmitButton8 = wait6.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[@class='submit-bar ' and @type='submit'][.//header[text()='Next']]")
            ));
            // Scroll the button into view (centered)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextSubmitButton8);
            Thread.sleep(200); // Optional: ensures scroll finishes
            // Click the Next submit button
            nextSubmitButton8.click();


            // Initialize wait
            WebDriverWait waitfordoc = new WebDriverWait(driver, Duration.ofSeconds(10));
            String filePath = "/Users/kaveri/Downloads/doc.pdf";
            // Upload first document and go to next page
            uploadDocumentAndClickNext(driver, waitfordoc, filePath);
            // Upload second document and go to next page
            uploadDocumentAndClickNext(driver, waitfordoc, filePath);
            // Upload third document (no next click if it's the last step)
            WebElement fileInput = waitfordoc.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("tl-doc"))
            );
            System.out.println("Uploading third document: " + filePath);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", fileInput);
            Thread.sleep(200);
            fileInput.sendKeys(filePath);
            System.out.println("Third document uploaded.");



            WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextSubmitButton9 = wait7.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[@class='submit-bar ' and @type='submit'][.//header[text()='Next']]")
            ));
            // Scroll the button into view (centered)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextSubmitButton9);
            Thread.sleep(200); // Optional: ensures scroll finishes
            // Click the Next submit button
            nextSubmitButton9.click();


            // Wait for the Submit Application button to be present
            WebDriverWait waitlast = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement submitButtonlast = waitlast.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[@class='submit-bar ' and @type='button'][.//header[text()='Submit Application']]")
            ));
            // Scroll to the bottom of the page (ensures button is rendered and visible)
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(300); // Optional: wait for scroll to finish
            // Scroll the button into view (extra robustness)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButtonlast);
            Thread.sleep(200);
            // Click the Submit Application button
            submitButtonlast.click();

            System.out.println("Submit Application button clicked.");



            System.out.println("Test completed successfully!");
            Thread.sleep(50000); // Final observation
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //driver.quit();
        }
    }

}
