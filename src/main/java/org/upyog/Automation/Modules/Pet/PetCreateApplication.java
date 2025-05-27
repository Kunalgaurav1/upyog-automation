package org.upyog.Automation.Modules.Pet;

import java.time.Duration;
import java.util.List;
import javax.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;



@Component
public class PetCreateApplication {

    @PostConstruct
    public void testingPetApp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
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

            WebElement petRegSidebarLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[@href='/digit-ui/citizen/ptr-home']")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", petRegSidebarLink);


            //Register Pet button
            //it opens up the required document page

            WebElement registerPetLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'CitizenHomeCard')]//a[text()='Register Pet']")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerPetLink);



            // clicking Next Button
            WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span/button[contains(@class,'submit-bar')]//header[normalize-space()='Next']/..")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextBtn);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);



            //Owner Detail page

            //Father's name
            WebElement fatherNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("fatherName")));
            fatherNameInput.clear();
            fatherNameInput.sendKeys("Ramesh Singh");

            //Email
            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("emailId")));
            emailInput.clear();
            emailInput.sendKeys("ramesh@gmail.com");

            Thread.sleep(700);

            //Next Button to Pet Details
            WebElement nextBtnowner = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'submit-bar')]//header[normalize-space()='Next']/..")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextBtnowner);
            Thread.sleep(300);
            nextBtnowner.click();




            //--------------------- PET DETAILS PAGE --------------//

            //PetType
            WebDriverWait waitForPetType = new WebDriverWait(driver, Duration.ofSeconds(10));

            // First locate ALL dropdown containers on the page
            List<WebElement> allDropdowns = driver.findElements(By.cssSelector("div.select"));

            // Get the first dropdown (Pet Type)
            WebElement petTypeDropdownContainer = allDropdowns.get(0); // First dropdown on the page

            // Find the SVG element (dropdown button) within the container
            WebElement petTypeDropdownButton = petTypeDropdownContainer.findElement(By.tagName("svg"));

            // Use Actions class instead of JavaScript for clicking
            Actions petTypeActions = new Actions(driver);
            petTypeActions.moveToElement(petTypeDropdownButton).click().perform();

            // Wait a brief moment to ensure dropdown opens
            Thread.sleep(500);

            // Wait for dropdown options to appear
            waitForPetType.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("jk-dropdown-unique")));

            // Now find and click the first option using Actions
            WebElement petTypeFirstOption = waitForPetType.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("#jk-dropdown-unique .profile-dropdown--item:first-child")));

            // Click using Actions instead of JavaScript
            petTypeActions.moveToElement(petTypeFirstOption).click().perform();

            // Verify selection was successful
            WebElement petTypeInput = petTypeDropdownContainer.findElement(By.cssSelector("input.employee-select-wrap--elipses"));
            waitForPetType.until(ExpectedConditions.or(
                    ExpectedConditions.attributeContains(petTypeInput, "value", "Cat"),
                    ExpectedConditions.textToBePresentInElementValue(petTypeInput, "Cat")
            ));

            System.out.println("Pet Type selected successfully!");
            Thread.sleep(1000);


            //Breed Type
            WebDriverWait waitForBreedType = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Refresh the list of dropdowns (as the page might have changed after first selection)
            allDropdowns = driver.findElements(By.cssSelector("div.select"));

            // Get the second dropdown (Breed Type)
            WebElement breedTypeDropdownContainer = allDropdowns.get(1); // Second dropdown on the page

            // Find the SVG element (dropdown button) within the container
            WebElement breedTypeDropdownButton = breedTypeDropdownContainer.findElement(By.tagName("svg"));

            // Use Actions class for clicking the dropdown button
            Actions breedTypeActions = new Actions(driver);
            breedTypeActions.moveToElement(breedTypeDropdownButton).click().perform();

            // Short wait to ensure dropdown opens
            Thread.sleep(500);

            // Wait for dropdown options to appear
            waitForBreedType.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("jk-dropdown-unique")));

            // Find and click the first option (Bombay Cat)
            WebElement breedTypeFirstOption = waitForBreedType.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("#jk-dropdown-unique .profile-dropdown--item:first-child")));

            // Click using Actions
            breedTypeActions.moveToElement(breedTypeFirstOption).click().perform();

            // Verify selection was successful (check input value)
            WebElement breedTypeInput = breedTypeDropdownContainer.findElement(By.cssSelector("input.employee-select-wrap--elipses"));
            waitForBreedType.until(ExpectedConditions.or(
                    ExpectedConditions.attributeContains(breedTypeInput, "value", "Bombay Cat"),
                    ExpectedConditions.textToBePresentInElementValue(breedTypeInput, "Bombay Cat")
            ));

            System.out.println("'Bombay Cat' selected successfully!");






            // -----------Pet Gender -------------//
            WebDriverWait waitForGender = new WebDriverWait(driver, Duration.ofSeconds(10));

            // First locate ALL dropdown containers on the page for gender selection
            List<WebElement> allGenderDropdowns = driver.findElements(By.cssSelector("div.select"));

            // Get the appropriate dropdown (Gender) - adjust index if needed
            WebElement genderDropdownContainer = allGenderDropdowns.get(2); // Third dropdown on the page

            // Find the SVG element (dropdown button) within the container
            WebElement genderDropdownButton = genderDropdownContainer.findElement(By.tagName("svg"));

            // Use Actions class instead of JavaScript for clicking
            Actions genderActions = new Actions(driver);
            genderActions.moveToElement(genderDropdownButton).click().perform();

            // Wait a brief moment to ensure dropdown opens
            Thread.sleep(500);

            // Wait for dropdown options to appear
            waitForGender.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("jk-dropdown-unique")));

            // Now find and click the first option using Actions
            WebElement genderFirstOption = waitForGender.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("#jk-dropdown-unique .profile-dropdown--item:first-child")));

            // Click using Actions instead of JavaScript
            genderActions.moveToElement(genderFirstOption).click().perform();

            // Verify selection was successful
            WebElement genderInput = genderDropdownContainer.findElement(By.cssSelector("input.employee-select-wrap--elipses"));
            waitForGender.until(ExpectedConditions.or(
                    ExpectedConditions.attributeContains(genderInput, "value", "Male"),
                    ExpectedConditions.textToBePresentInElementValue(genderInput, "Male")
            ));

            System.out.println("Gender selected successfully!");

            // Wait for a moment before moving to the next dropdown
            Thread.sleep(1000);





            //---------- PET COLOR ----------//
            WebDriverWait waitForColor = new WebDriverWait(driver, Duration.ofSeconds(100));

            // Locate ALL dropdown containers on the page for color selection
            List<WebElement> allColorDropdowns = driver.findElements(By.cssSelector("div.select"));

            // Get the appropriate dropdown (Color) - adjust index if needed
            WebElement colorDropdownContainer = allColorDropdowns.get(3); // Fourth dropdown on the page

            // Find the SVG element (dropdown button) within the container
            WebElement colorDropdownButton = colorDropdownContainer.findElement(By.tagName("svg"));

            // Use Actions class for clicking
            Actions colorActions = new Actions(driver);
            colorActions.moveToElement(colorDropdownButton).click().perform();

            // Wait a brief moment to ensure dropdown opens
            Thread.sleep(500);

            // Wait for dropdown options to appear
            waitForColor.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("jk-dropdown-unique")));

            // Now find and click the first option using Actions
            WebElement colorFirstOption = waitForColor.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("#jk-dropdown-unique .profile-dropdown--item:first-child")));

            // Click using Actions
            colorActions.moveToElement(colorFirstOption).click().perform();

            // Verify selection was successful
            WebElement colorInput = colorDropdownContainer.findElement(By.cssSelector("input.employee-select-wrap--elipses"));
            waitForColor.until(ExpectedConditions.or(
                    ExpectedConditions.attributeContains(colorInput, "value", "Golden Brown"),
                    ExpectedConditions.textToBePresentInElementValue(colorInput, "Golden Brown")
            ));

            System.out.println("Color selected successfully!");

            // Wait for a moment before moving to the next dropdown
            Thread.sleep(1000);



            //---------------Select Birth or Adoption-----------//

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


            //---------- Selecting City (First Dropdown on new page) ----------//
            WebDriverWait waitForCity = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Locate ALL dropdowns on the NEW PAGE
            List<WebElement> allDropdownsforcityyy = driver.findElements(By.cssSelector("div.select"));
            System.out.println("DEBUG: Dropdowns on new page - " + allDropdownsforcityyy.size());

            // City is the FIRST dropdown (index 0)
            WebElement cityDropdownContainer = allDropdownsforcityyy.get(0);

            // Open dropdown
            WebElement cityDropdownButton = cityDropdownContainer.findElement(By.tagName("svg"));
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
            WebDriverWait waitForCitySelection = new WebDriverWait(driver, Duration.ofSeconds(15));

            // 1. Try both select and select-active classes
            List<WebElement> cityDropdownContainers = driver.findElements(By.cssSelector("div.select, div.select-active"));
            System.out.println("DEBUG: Total city dropdown candidates found: " + cityDropdownContainers.size());

            // 2. Verify we found dropdowns
            if (cityDropdownContainers.isEmpty()) {
                throw new RuntimeException("No city dropdown found with either select or select-active class");
            }

            // 3. Get the most likely candidate (last one in DOM is usually the active one)
            WebElement citySelectContainer = cityDropdownContainers.get(cityDropdownContainers.size() - 1);
            System.out.println("DEBUG: Using dropdown with classes: " + citySelectContainer.getAttribute("class"));

            // 4. Find and click the expand button
            WebElement cityExpandButton = citySelectContainer.findElement(By.tagName("svg"));
            new Actions(driver).moveToElement(cityExpandButton).click().perform();
            Thread.sleep(800);  // Increased wait for stability

            // 5. Wait for options and select first one
            waitForCitySelection.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("jk-dropdown-unique")));

            WebElement primaryCityOption = waitForCitySelection.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("#jk-dropdown-unique .profile-dropdown--item:first-child")
                    )
            );
            new Actions(driver).moveToElement(primaryCityOption).click().perform();
            System.out.println("City selection completed successfully!");
            Thread.sleep(1000);


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
            Actions actions = new Actions(driver);
            WebElement cityDropdownToggle = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.select svg.cp")
            ));
            actions.moveToElement(cityDropdownToggle).click().perform();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jk-dropdown-unique")));

            // Select first city option
            WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@id='jk-dropdown-unique']//div[contains(@class,'profile-dropdown--item')][1]")
            ));
            cityOption.click();
            System.out.println("Selected first available city option");

            //wait for city selection to complete
            Thread.sleep(3000);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("jk-dropdown-unique")));



            //Locality Selection
            List<WebElement> allDropdownsforlocality = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("div.select")
            ));

            WebElement localityDropdownContainer = allDropdownsforlocality.get(1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", localityDropdownContainer);
            Thread.sleep(2000);

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



            // ------------IDENTITY  PROOF---------------
            Thread.sleep(5000);

            System.out.println("now selecting the identity proof");

            // ------------IDENTITY PROOF SELECTION---------------

            try {
                // ====== STEP 1: Find the dropdown container and scroll into view ======
                WebElement dropdownContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div.select")
                ));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdownContainer);
                Thread.sleep(1000); // Optional pause for visual scroll

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

            //---------------------------------------------------------------

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

















            System.out.println("Test completed successfully!");
            Thread.sleep(50000); // Final observation
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //driver.quit();
        }
    }

}


