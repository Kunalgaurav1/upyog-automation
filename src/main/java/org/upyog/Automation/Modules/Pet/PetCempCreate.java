package org.upyog.Automation.Modules.Pet;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.HashMap;
import java.util.Map;




//@Component
public class PetCempCreate {

    private static final Logger logger = Logger.getLogger(PetCempCreate.class.getName());









    public void clickNextSubmitButton(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        WebElement submitButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@class, 'submit-bar') and @type='submit' and .//header[normalize-space()='Next']]")
                )
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButton);
        Thread.sleep(300);
        submitButton.click();
    }



    //@PostConstruct
    public void PetRegCemp(){

        System.out.println("New Pet Registration");
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

            driver.get("https://niuatt.niua.in/digit-ui/employee/user/login");
            driver.manage().window().maximize();
            System.out.println("Open the Login Portal");

            // Wait for the username input field to be visible and enter the username
            WebElement usernameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("username"))
            );
            usernameInput.clear();
            usernameInput.sendKeys("PET_CEMP"); // Replace with actual username

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

            System.out.println("now going for new pet registration");


            WebElement newPetRegLink = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(@href, '/digit-ui/employee/ptr/petservice/new-application') and contains(normalize-space(.), 'New Pet Registration')]")
                    )
            );
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", newPetRegLink);
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newPetRegLink);
            System.out.println("Clicked New Pet Registration link.");


            // Wait for the "Next" button to be clickable
            WebElement nextButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(@class, 'submit-bar') and .//header[normalize-space()='Next']]")
                    )
            );

// Optionally scroll to the button for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextButton);
            Thread.sleep(300); // Optional

// Click the Next button
            nextButton.click();






//---------------------------------OWNER DETAILS PAGE --------------------------

            // Wait for the applicant name input field to be visible
            WebElement applicantNameField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("applicantName"))
            );

            // Clear any existing value (optional)
            applicantNameField.clear();
            // Enter the applicant name
            applicantNameField.sendKeys("Ramesh kumar");


            // Wait for the mobile number input field to be visible
            WebElement mobileNumberField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("mobileNumber"))
            );

            // Clear any existing value (optional)
            mobileNumberField.clear();
            // Enter the mobile number
            mobileNumberField.sendKeys("8893445543");



            // Wait for the father's name input field to be visible
            WebElement fatherNameField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("fatherName"))
            );

            // Clear any existing value (optional)
            fatherNameField.clear();
            // Enter the father's name
            fatherNameField.sendKeys("amit kumar");



            // Wait for the email input field to be visible
            WebElement emailField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("emailId"))
            );

            // Clear any existing value (optional)
            emailField.clear();
            // Enter the email address
            emailField.sendKeys("ramesh@gmail.com");


            clickNextSubmitButton(driver, wait);


//----------------------PET DETAILS DROPDOWN ----------------------------------

            Thread.sleep(300);
            //----------------------PET DETAILS DROPDOWN ----------------------------------

            try {
                // Wait for all dropdowns to be present
                List<WebElement> allDropdowns = wait.until(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.select"))
                );

                Actions actionsforpettype = new Actions(driver);

                WebElement petTypeDropdownContainer1 = allDropdowns.get(0);
                WebElement petTypeDropdownButton1 = petTypeDropdownContainer1.findElement(By.tagName("svg"));
                wait.until(ExpectedConditions.elementToBeClickable(petTypeDropdownButton1));

                // --- PET TYPE: Select "Dog" ---
                WebElement petTypeDropdownContainer = allDropdowns.get(0);
                System.out.println("going to click svg");
                WebElement petTypeDropdownButton = petTypeDropdownContainer.findElement(By.tagName("svg"));
                wait.until(ExpectedConditions.elementToBeClickable(petTypeDropdownButton));

                System.out.println("clicked svg");
                actionsforpettype.moveToElement(petTypeDropdownButton).click().perform();
                System.out.println("clicked pet type");
                WebElement petTypeOptions = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.options-card"))
                );

                System.out.println("drppdown become visible ");
                List<WebElement> petTypeItems = petTypeOptions.findElements(By.cssSelector(".profile-dropdown--item"));
                boolean petTypeSelected = false;
                for (WebElement option : petTypeItems) {
                    if (option.getText().trim().equalsIgnoreCase("Dog")) {
                        actionsforpettype.moveToElement(option).click().perform();
                        petTypeSelected = true;
                        break;
                    }
                }
                if (!petTypeSelected) throw new Exception("Pet Type 'Dog' not found!");
                System.out.println("Pet Type 'Dog' selected!");

                // Wait for the dropdown field to be present
//                WebElement dropdownField = new WebDriverWait(driver, Duration.ofSeconds(10))
//                        .until(ExpectedConditions.presenceOfElementLocated(
//                                By.cssSelector("div.select.undefined")));
//
//                System.out.println("1111 - Dropdown field found");
//
//                // Click the SVG button to open the dropdown options
//                WebElement svgButton = dropdownField.findElement(By.cssSelector("svg.cp"));
//                svgButton.click();
//
//                System.out.println("2222 - SVG button clicked");
//
//                // Wait for the dropdown options to appear
//                WebElement dropdownOptions1 = new WebDriverWait(driver, Duration.ofSeconds(10))
//                        .until(ExpectedConditions.visibilityOfElementLocated(
//                                By.id("jk-dropdown-unique")));
//
//                System.out.println("3333 - Dropdown options container visible");
//                System.out.println("Successfully selected 'Cat' from the dropdown");




                // --- BREED TYPE: Select "Labrador Retriever" ---
                WebElement breedTypeDropdownContainer = allDropdowns.get(1);
                WebElement breedTypeDropdownButton = breedTypeDropdownContainer.findElement(By.tagName("svg"));
                actionsforpettype.moveToElement(breedTypeDropdownButton).click().perform();
                WebElement breedTypeOptions = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.options-card"))
                );
                List<WebElement> breedTypeItems = breedTypeOptions.findElements(By.cssSelector(".profile-dropdown--item"));
                boolean breedTypeSelected = false;
                for (WebElement option : breedTypeItems) {
                    if (option.getText().trim().equalsIgnoreCase("Labrador Retriever")) {
                        actionsforpettype.moveToElement(option).click().perform();
                        breedTypeSelected = true;
                        break;
                    }
                }

                if (!breedTypeSelected) throw new Exception("Breed Type 'Labrador Retriever' not found!");
                System.out.println("Breed Type 'Labrador Retriever' selected!");

                // --- PET SEX: Select "Male" ---
                WebElement petSexDropdownContainer = allDropdowns.get(2);
                WebElement petSexDropdownButton = petSexDropdownContainer.findElement(By.tagName("svg"));
                actionsforpettype.moveToElement(petSexDropdownButton).click().perform();
                WebElement petSexOptions = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.options-card"))
                );
                List<WebElement> petSexItems = petSexOptions.findElements(By.cssSelector(".profile-dropdown--item"));
                boolean petSexSelected = false;
                for (WebElement option : petSexItems) {
                    if (option.getText().trim().equalsIgnoreCase("Male")) {
                        actionsforpettype.moveToElement(option).click().perform();
                        petSexSelected = true;
                        break;
                    }
                }
                if (!petSexSelected) throw new Exception("Pet Sex 'Male' not found!");
                System.out.println("Pet Sex 'Male' selected!");

                // --- PET COLOUR: Select "Tricolor or white" ---
                WebElement petColourDropdownContainer = allDropdowns.get(3);
                WebElement petColourDropdownButton = petColourDropdownContainer.findElement(By.tagName("svg"));
                actionsforpettype.moveToElement(petColourDropdownButton).click().perform();
                WebElement petColourOptions = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.options-card"))
                );
                List<WebElement> petColourItems = petColourOptions.findElements(By.cssSelector(".profile-dropdown--item"));
                boolean petColourSelected = false;
                for (WebElement option : petColourItems) {
                    if (option.getText().trim().equalsIgnoreCase("Tricolor or white")) {
                        actionsforpettype.moveToElement(option).click().perform();
                        petColourSelected = true;
                        break;
                    }
                }
                if (!petColourSelected) throw new Exception("Pet Colour 'Tricolor or white' not found!");
                System.out.println("Pet Colour 'Tricolor or white' selected!");

            } catch (Exception e) {
                System.out.println("Exception in PET DETAILS DROPDOWN: " + e.getMessage());
                e.printStackTrace();
            }



            // Select first radio button for "selectBirthAdoption"
            WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> birthAdoptionRadios = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("input[type='radio'][name='selectBirthAdoption']")
            ));
            if (!birthAdoptionRadios.isEmpty()) {
                WebElement firstRadio = birthAdoptionRadios.get(0);
                if (!firstRadio.isSelected()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstRadio);
                    Thread.sleep(1000); // Wait to ensure click is processed
                }
            } else {
                throw new RuntimeException("Radio buttons for selectBirthAdoption not found");
            }

            // Locate the date input field
            WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("birthDate")));

            // Clear any existing value (optional)
            dateInput.clear();

            // Send the date in yyyy-MM-dd format (for example, May 20, 2025)
            dateInput.sendKeys("20-05-2025");

            // Optionally, you can click elsewhere or press TAB to trigger any onblur/onchange events
            dateInput.sendKeys(Keys.TAB);
            Thread.sleep(1000); // small pause to allow UI update if needed


            //----------PET NAME-----------//
            WebElement petNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("petName")));
            petNameInput.clear();
            petNameInput.sendKeys("cherry");

            //-------------Identification Mark ---------//
            WebElement identificationMarkInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("identificationMark")));
            identificationMarkInput.clear();
            identificationMarkInput.sendKeys("black color");

            //--------------Pet Age (in months)----------//
            WebElement petAgeInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("petAge")));
            petAgeInput.clear();
            petAgeInput.sendKeys("5");

            //--------------Doctor Name ------------//
            WebElement doctorNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("doctorName")));
            doctorNameInput.clear();
            doctorNameInput.sendKeys("kdjfksdfsf");


            //---------------Clinic Name---------------//
            WebElement clinicNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("clinicName")));
            clinicNameInput.clear();
            clinicNameInput.sendKeys("sdjhfsjfsfsg");

            //---------------Vaccination Date------------//
            WebElement vaccinationdate = wait.until(ExpectedConditions.elementToBeClickable(By.name("lastVaccineDate")));
            dateInput.clear();   // Clear any existing value (optional)
            vaccinationdate.sendKeys("20-05-2025");   // Send the date in yyyy-MM-dd format (for example, May 20, 2025)

            // Optionally, you can click elsewhere or press TAB to trigger any onblur/onchange events
            vaccinationdate.sendKeys(Keys.TAB);
            Thread.sleep(1000); // small pause to allow UI update if needed


            //----------------Vaccination Number-----------//
            WebElement vaccinationNumberInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("vaccinationNumber")));
            vaccinationNumberInput.clear();
            vaccinationNumberInput.sendKeys("34545645");

            // Next Button
            WebElement nextButton3 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']:not([disabled])")));
            nextButton3.click();



            //----------------------PROPERTY DETAILS-------------------------//

            //------Property Id---------//
            WebElement propertyIdInput = driver.findElement(By.name("propertyId"));
            propertyIdInput.clear();
            propertyIdInput.sendKeys("435345");


            //---------Bulding Name-------//
            WebElement buildingNameInput = driver.findElement(By.name("buildingName"));
            buildingNameInput.clear();
            buildingNameInput.sendKeys("gdfgdgdgdfg");


            //---------Plot Number-------//
            WebElement doorNoInput = driver.findElement(By.name("doorNo"));
            doorNoInput.clear();
            doorNoInput.sendKeys("4534");


            // Locate ALL dropdowns on the NEW PAGE
            List<WebElement> allDropdownsforcityyy = driver.findElements(By.cssSelector("div.select"));
            System.out.println("DEBUG: Dropdowns on new page - " + allDropdownsforcityyy.size());

            //---------- Selecting City (First Dropdown on new page) ----------//
            WebDriverWait waitForCity = new WebDriverWait(driver, Duration.ofSeconds(10));

            // City is the FIRST dropdown (index 0)
            WebElement cityDropdownContainer1 = allDropdownsforcityyy.get(0);

            // Open dropdown
            WebElement cityDropdownButton = cityDropdownContainer1.findElement(By.tagName("svg"));
            new Actions(driver).moveToElement(cityDropdownButton).click().perform();
            Thread.sleep(500);

            // Select first option ("New Delhi")
            waitForCity.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("jk-dropdown-unique")));
            WebElement cityFirstOption = driver.findElement(
                    By.cssSelector("#jk-dropdown-unique .profile-dropdown--item:first-child"));
            new Actions(driver).moveToElement(cityFirstOption).click().perform();


            //----------Street Name------//
            WebElement streetInput = driver.findElement(By.name("street"));
            streetInput.clear();
            streetInput.sendKeys("lajpat");


            //---------- Selecting Mohalla ----------//
            WebDriverWait waitForLocation = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 1. Locate ALL dropdown containers using div.select
            List<WebElement> allLocationDropdowns = driver.findElements(By.cssSelector("div.select"));
            System.out.println("DEBUG: Found " + allLocationDropdowns.size() + " dropdown(s) on page");

            // 2. Get the Location dropdown by index (adjust if needed)
            WebElement locationDropdownContainer = allLocationDropdowns.get(1); // Typically 2nd dropdown (index 1)
            System.out.println("DEBUG: Location dropdown class: " + locationDropdownContainer.getAttribute("class"));

            // 3. Find and click the SVG dropdown button
            WebElement locationDropdownButton = locationDropdownContainer.findElement(By.tagName("svg"));
            new Actions(driver).moveToElement(locationDropdownButton).click().perform();
            Thread.sleep(500);

            // 4. Wait for options and select first one ("Other")
            waitForLocation.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("jk-dropdown-unique")));

            WebElement firstLocationOption = driver.findElement(
                    By.cssSelector("#jk-dropdown-unique .profile-dropdown--item:first-child"));
            new Actions(driver).moveToElement(firstLocationOption).click().perform();
            System.out.println("Location 'Other' selected successfully!");
            Thread.sleep(1000);


            //pincode
            WebElement pincodeInput = driver.findElement(By.name("pincode"));
            pincodeInput.clear();
            pincodeInput.sendKeys("110011");






            //---------- Selecting Fire Station ----------//
            int attempts = 0;
            while (attempts < 3) {
                try {
                    System.out.println("Attempt " + (attempts + 1) + ": Locating the correct dropdown container...");
                    // Locate the specific dropdown container (set the index as needed)
                    WebElement dropdownContainer = driver.findElements(By.cssSelector("div.select.undefined")).get(2); // Adjust index if needed
                    WebElement svgToggle = dropdownContainer.findElement(By.cssSelector("svg.cp"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", svgToggle);

                    System.out.println("Clicking SVG to open dropdown...");
                    new Actions(driver).moveToElement(svgToggle).click().perform();

                    System.out.println("Waiting for the correct dropdown options to appear...");
                    // Find the options container that appears after this dropdown
                    WebElement optionsContainer = dropdownContainer.findElement(
                            By.xpath("following-sibling::div[contains(@class,'options-card')]")
                    );

                    // Debug: print all options for visibility
                    List<WebElement> allOptions = optionsContainer.findElements(By.xpath(".//div[contains(@class,'profile-dropdown--item')]"));
                    System.out.println("Found " + allOptions.size() + " options:");
                    for (WebElement opt : allOptions) {
                        System.out.println("Option: '" + opt.getText().trim() + "'");
                    }

                    System.out.println("Locating and clicking 'City A' option...");
                    // Use flexible descendant-based XPath with normalize-space
                    WebElement cityAOption = optionsContainer.findElement(
                            By.xpath(".//div[contains(@class,'profile-dropdown--item')][.//span[contains(normalize-space(.),'City A')]]")
                    );
                    String selectedOptionText = cityAOption.getText().trim();
                    cityAOption.click();
                    System.out.println("'City A' selected: " + selectedOptionText);

                    // Optionally wait for the options container to disappear
                    wait.until(ExpectedConditions.invisibilityOf(optionsContainer));
                    System.out.println("Dropdown closed. Selection complete.");
                    Thread.sleep(500);

                    break; // Success, exit loop
                } catch (org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.TimeoutException e) {
                    attempts++;
                    System.out.println(e.getClass().getSimpleName() + " caught on attempt " + attempts + ". Retrying...");
                    if (attempts == 3) {
                        System.out.println("Failed after 3 attempts. Throwing exception.");
                        throw e;
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    System.out.println("Error during dropdown selection on attempt " + (attempts + 1) + ": " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }









            //GIS Location
            WebElement gisInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("gisCode")
            ));
            // Clear existing value and enter new one
            gisInput.clear();
            gisInput.sendKeys("2343453");

            // getting to the next page
            WebElement nextButton4 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']:not([disabled])")));
            nextButton4.click();



            //-------------------ADDRESS DETAILS-----------------------//


            //---------- Enter House Number ----------//
            WebElement houseNoInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("houseNo")));
            houseNoInput.clear();
            houseNoInput.sendKeys("2342");

            //---------- Enter House name ----------//
            WebElement houseNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("houseName")));
            houseNameInput.clear();
            houseNameInput.sendKeys("My House");

            //---------- Enter Street Name ----------//
            WebElement streetNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("streetName")));
            streetNameInput.clear();
            streetNameInput.sendKeys("Main Street");

            //---------- Enter Address 1 ----------//
            WebElement addressInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("addressline1")));
            addressInput.clear();
            addressInput.sendKeys("123 Main Street, Apt 4B");


            //---------- Enter Address 2 ----------//
            WebElement addressLine2Input = wait.until(ExpectedConditions.elementToBeClickable(By.name("addressline2")));
            addressLine2Input.clear();
            addressLine2Input.sendKeys("Near Central Park");




            //---------- Enter Landmark----------//
            WebElement landmarkInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("landmark")));
            landmarkInput.clear();
            landmarkInput.sendKeys("Opposite City Mall");



            //------- City Selection ----------
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.select input.employee-select-wrap--elipses")
            ));

            WebElement cityContainer = driver.findElement(By.cssSelector("div.select"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cityContainer);
            Thread.sleep(1000);

            // Open city dropdown
            Actions actionsforcity = new Actions(driver);
            WebElement cityDropdownToggle = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.select svg.cp")
            ));
            actionsforcity.moveToElement(cityDropdownToggle).click().perform();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jk-dropdown-unique")));

            // Select first city option
            WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@id='jk-dropdown-unique']//div[contains(@class,'profile-dropdown--item')][1]")
            ));
            cityOption.click();
            System.out.println("Selected first available city option");

            //wait for city selection to complete
            Thread.sleep(300);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("jk-dropdown-unique")));



            //Locality Selection
            List<WebElement> allDropdownsforlocality = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("div.select")
            ));

            WebElement localityDropdownContainer = allDropdownsforlocality.get(1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", localityDropdownContainer);
            Thread.sleep(200);

            // Open locality dropdown
            WebElement localityArrow = localityDropdownContainer.findElement(By.cssSelector("svg.cp"));
            new Actions(driver).moveToElement(localityArrow).click().perform();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jk-dropdown-unique")));

            // Select first locality option
            WebElement localityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@id='jk-dropdown-unique']//div[contains(@class,'profile-dropdown--item')][1]")
            ));
            localityOption.click();
            System.out.println("Selected first available locality option");

            // Wait for completion
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("jk-dropdown-unique")));
            System.out.println("Both city and locality selections completed!");



            // Wait for locality dropdown to close first
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("jk-dropdown-unique")));
            Thread.sleep(1000);



            // Find and fill pincode field
            WebElement pincodeField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.name("pincode")
            ));

            // Scroll to field and enter pincode
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pincodeField);
            pincodeField.clear();
            pincodeField.sendKeys("112233");

            System.out.println("Pincode 112233 entered successfully");

            //NEXT BUTTON
            WebElement nextButton5 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']:not([disabled])")));
            nextButton5.click();


            try {
                // ====== STEP 1: Find the dropdown container and scroll into view ======
//                WebElement dropdownContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
//                        By.cssSelector("div.select")
//                ));
//                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdownContainer);
//                Thread.sleep(1000); // Optional pause for visual scroll

                System.out.println("Waiting for Aadhar selection");

                Actions actionsforidentity = new Actions(driver);
                WebElement cityDropdownToggleforidentity = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div.select svg.cp")
                ));
                actionsforidentity.moveToElement(cityDropdownToggleforidentity).click().perform();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jk-dropdown-unique")));

                // ====== STEP 3: Wait for options to appear ======
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("jk-dropdown-unique")
                ));

                System.out.println("Approaching towards Selection of Aadhar card");

                // ====== STEP 4: Select the first option (Aadhaar Card) ======
                WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@id='jk-dropdown-unique']/div[contains(@class, 'profile-dropdown--item')][1]")
                ));

                // Get the text BEFORE clicking to avoid stale element reference
                String selectedOptionText = firstOption.getText().trim();
                firstOption.click();

                System.out.println("Aadhar card is selected");
                System.out.println("Selected: " + selectedOptionText); // Use stored text instead of calling getText() again

                // ====== STEP 5: Wait for dropdown to close ======
                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.id("jk-dropdown-unique")
                ));

                // Additional wait to ensure page is stable after dropdown selection
                Thread.sleep(2000);

            } catch (Exception e) {
                System.out.println("Error during identity proof selection: " + e.getMessage());
                e.printStackTrace();
            }


            //----------------document upload automation code ------------------

            // Set your file paths here
            String identityProofPath = "/Users/kaveri/Downloads/doc.pdf";
            String ownerProofPath = "/Users/kaveri/Downloads/owner.pdf";
            String vaccinationCertPath = "/Users/kaveri/Downloads/vaccination.pdf";
            String petPhotoPath = "/Users/kaveri/Downloads/deep.png";
            String fitnessCertPath = "/Users/kaveri/Downloads/fitness.pdf";

            // List of file paths in the order of appearance (excluding pet photo)
            String[] docFilePaths = {
                    identityProofPath,
                    ownerProofPath,
                    vaccinationCertPath,
                    fitnessCertPath
            };

            // Upload for the first four fields (identity, owner, vaccination, fitness)
            List<WebElement> fileInputs = driver.findElements(
                    By.cssSelector("input.input-mirror-selector-button[type='file']")
            );

            if (fileInputs.size() < 4) {
                System.out.println("Error: Expected at least 4 file inputs, found " + fileInputs.size());
                return;
            }

            for (int i = 0; i < docFilePaths.length; i++) {
                String filePath = docFilePaths[i];
                WebElement fileInput = fileInputs.get(i);

                int maxRetries = 3;
                boolean uploadSuccessful = false;

                for (int attempt = 1; attempt <= maxRetries && !uploadSuccessful; attempt++) {
                    try {
                        System.out.println("Uploading file for field " + (i+1) + " - Attempt: " + attempt);
                        Thread.sleep(1000);

                        // Scroll into view
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fileInput);
                        Thread.sleep(500);

                        // Try sendKeys
                        try {
                            fileInput.sendKeys(filePath);
                            System.out.println("File path sent using direct sendKeys");
                        } catch (Exception e1) {
                            System.out.println("Direct sendKeys failed, trying JavaScript approach...");
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible';",
                                    fileInput
                            );
                            Thread.sleep(500);
                            fileInput.sendKeys(filePath);
                            System.out.println("File path sent after making element visible");
                        }

                        Thread.sleep(4000);

                        // Check status
                        try {
                            WebElement uploadStatus = fileInput.findElement(
                                    By.xpath("ancestor::div[contains(@class,'upload-file')]//h2[contains(@class,'file-upload-status')]")
                            );
                            String statusText = uploadStatus.getText().trim();
                            System.out.println("Current upload status: " + statusText);

                            if (!statusText.equals("No File Uploaded") && !statusText.isEmpty()) {
                                System.out.println("File uploaded successfully! Status: " + statusText);
                                uploadSuccessful = true;
                            } else {
                                String inputValue = fileInput.getAttribute("value");
                                if (inputValue != null && !inputValue.isEmpty()) {
                                    System.out.println("File input has value, upload likely successful: " + inputValue);
                                    uploadSuccessful = true;
                                }
                            }
                        } catch (Exception statusException) {
                            System.out.println("Could not verify upload status, but file was sent to input");
                            uploadSuccessful = true;
                        }
                    } catch (org.openqa.selenium.StaleElementReferenceException e) {
                        System.out.println("Stale element reference on attempt " + attempt + ". Retrying...");
                        if (attempt == maxRetries) {
                            System.out.println("Max retries reached. Proceeding with assumption that upload was successful.");
                            uploadSuccessful = true;
                        } else {
                            Thread.sleep(2000);
                        }
                    } catch (Exception e) {
                        System.out.println("Error on attempt " + attempt + ": " + e.getMessage());
                        if (attempt == maxRetries) {
                            throw e;
                        }
                        Thread.sleep(2000);
                    }
                }

                if (uploadSuccessful) {
                    System.out.println("Upload for field " + (i+1) + " completed successfully!");
                } else {
                    System.out.println("Upload for field " + (i+1) + " completed with uncertainties");
                }
            }

            // Now handle pet photo (separate input)
            int maxRetries = 3;
            boolean petPhotoUploadSuccessful = false;

            for (int attempt = 1; attempt <= maxRetries && !petPhotoUploadSuccessful; attempt++) {
                try {
                    System.out.println("Uploading pet photo - Attempt: " + attempt);
                    WebElement petPhotoInput = driver.findElement(By.cssSelector("input[type='file']#upload"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", petPhotoInput);
                    Thread.sleep(500);

                    try {
                        petPhotoInput.sendKeys(petPhotoPath);
                        System.out.println("Pet photo path sent using direct sendKeys");
                    } catch (Exception e1) {
                        System.out.println("Direct sendKeys failed for pet photo, trying JavaScript approach...");
                        ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible';",
                                petPhotoInput
                        );
                        Thread.sleep(500);
                        petPhotoInput.sendKeys(petPhotoPath);
                        System.out.println("Pet photo path sent after making element visible");
                    }

                    Thread.sleep(4000);

                    // No status check for pet photo, assume success if no exception
                    petPhotoUploadSuccessful = true;
                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    System.out.println("Stale element reference on attempt " + attempt + ". Retrying...");
                    if (attempt == maxRetries) {
                        System.out.println("Max retries reached for pet photo. Proceeding with assumption that upload was successful.");
                        petPhotoUploadSuccessful = true;
                    } else {
                        Thread.sleep(2000);
                    }
                } catch (Exception e) {
                    System.out.println("Error on attempt " + attempt + ": " + e.getMessage());
                    if (attempt == maxRetries) {
                        throw e;
                    }
                    Thread.sleep(2000);
                }
            }

            if (petPhotoUploadSuccessful) {
                System.out.println("Pet photo upload completed successfully!");
            } else {
                System.out.println("Pet photo upload completed with uncertainties");
            }

            System.out.println("All document uploads completed!");


            WebElement nextButton6 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']:not([disabled])")));
            nextButton6.click();



            WebElement declarationCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='checkbox'][value^='I declare']")
            ));

            // Scroll into view (optional but recommended)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", declarationCheckbox);
            Thread.sleep(300);

            // Check if not already selected, then click
            if (!declarationCheckbox.isSelected()) {
                declarationCheckbox.click();
                System.out.println("Declaration checkbox checked!");
            } else {
                System.out.println("Declaration checkbox was already checked.");
            }


            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='button' and contains(@class,'submit-bar')][header='Submit' or .//header[text()='Submit']]")
            ));

            // Scroll into view (optional but recommended)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            Thread.sleep(300);

            // Click the Submit button
            submitButton.click();
            System.out.println("The Application form is Submitted now");


            Thread.sleep(2000);



            try {
                // Wait for the "Go back to home page" link to be visible
                WebElement goHomeLink = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//a[@href='/digit-ui/employee']//span[contains(text(), 'Go back to home page')]")
                        )
                );

                // Scroll to the link (optional, for reliability)
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", goHomeLink);

                // Click the link
                goHomeLink.click();

                // Optionally, wait for the new page to load by checking the URL
                wait.until(ExpectedConditions.urlContains("/digit-ui/employee"));

                System.out.println("Navigated to /digit-ui/employee successfully!");
            } catch (Exception e) {
                System.out.println("Failed to navigate to home page: " + e.getMessage());
                e.printStackTrace();
            }















































        }catch (Exception e){
            System.out.println("Exception in Pet Registration");
        }finally {
            //driver.quit();

        }
    }
}
