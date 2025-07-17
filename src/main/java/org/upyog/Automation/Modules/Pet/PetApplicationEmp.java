package org.upyog.Automation.Modules.Pet;
import org.upyog.Automation.Utils.DriverFactory;
import org.upyog.Automation.Utils.ConfigReader;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import java.time.Duration;

//@Component
public class PetApplicationEmp {

    public void handlePopupAndSubmit(WebDriver driver, WebDriverWait wait, String comment, String filePath) throws InterruptedException {
        // 1. Wait for the comment textarea to be visible and enter comment
        WebElement commentField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("comments"))
        );
        commentField.clear();
        commentField.sendKeys(comment);

        // 2. Wait for the file input to be present and upload the file
        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("workflow-doc"))
        );
        fileInput.sendKeys(filePath);
        System.out.println("document uploaded");

        // 3. Click the Verify or Approve button if present
        List<WebElement> verifyButtons = driver.findElements(By.xpath("//button[contains(@class, 'selector-button-primary') and .//h2[normalize-space()='Verify']]"));
        List<WebElement> approveButtons = driver.findElements(By.xpath("//button[contains(@class, 'selector-button-primary') and .//h2[normalize-space()='Approve']]"));

        WebElement actionButton = null;
        if (!verifyButtons.isEmpty()) {
            actionButton = verifyButtons.get(0);
            System.out.println("Verify button found, clicking Verify...");
        } else if (!approveButtons.isEmpty()) {
            actionButton = approveButtons.get(0);
            System.out.println("Approve button found, clicking Approve...");
        } else {
            throw new RuntimeException("Neither Verify nor Approve button found!");
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", actionButton);
        Thread.sleep(300);
        actionButton.click();
    }



    public void clickTakeActionButton(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        // Wait for the TAKE ACTION button to be clickable
        WebElement takeActionButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@class, 'submit-bar') and .//header[normalize-space()='TAKE ACTION']]")
                )
        );
        // Optionally scroll to the button
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", takeActionButton);
        Thread.sleep(300); // Optional small wait
        // Click the TAKE ACTION button
        takeActionButton.click();
        System.out.println("Clicked TAKE ACTION button");
    }



    public void handleTakeActionMenu(WebDriver driver, WebDriverWait wait) {
        try {
            // Wait for the menu-wrap to appear after clicking TAKE ACTION
            WebElement menuWrap = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.menu-wrap"))
            );
            // Get all action options inside the menu
            List<WebElement> actionOptions = menuWrap.findElements(By.tagName("p"));

            // Loop through options and click based on your workflow
            for (WebElement option : actionOptions) {
                String text = option.getText().trim().toUpperCase();
                if (text.equals("VERIFY")) {
                    option.click();
                    System.out.println("Clicked VERIFY");
                    handlePopupAndSubmit(driver, wait, "This is an automated verification comment.", "/Users/kaveri/Downloads/upyog-automation/doc.pdf");
                    break;
                } else if (text.equals("APPROVE")) {
                    System.out.println("approve button found");
                    option.click();
                    System.out.println("Clicked APPROVE");
                    handlePopupAndSubmit(driver, wait, "This is an automated approve comment.", "/Users/kaveri/Downloads/upyog-automation/doc.pdf");
                    break;
                } else if (text.equals("PAY")) {
                    option.click();
                    System.out.println("Clicked PAY");
                    break;
                } else if (text.equals("REJECT")) {
                    System.out.println("Application Rejected");
                }
            }
        } catch (Exception e) {
            System.out.println("Take Action Menu not found or no valid option present.");
            e.printStackTrace();
        }
    }


    //@PostConstruct
    public void petInbox() {
        WebDriver driver = DriverFactory.createChromeDriver();
        WebDriverWait wait = DriverFactory.createWebDriverWait(driver);


        // After logging in (or wherever you want to open a new tab)
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));


       // Continue with your next steps here...


        try {
            driver.get("https://upyog.niua.org/upyog-ui/employee/user/login");
            driver.manage().window().maximize();
            System.out.println("Open the Login Portal");

            // Read username and password from properties file
            String username = ConfigReader.get("app.login.username");
            String password = ConfigReader.get("app.login.password");
            // Wait for the username input field to be visible and enter the username
            WebElement usernameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("username"))
            );
            usernameInput.clear();
            usernameInput.sendKeys(username); // Use value from properties
            // Wait for the password input field to be visible and enter the password
            WebElement passwordInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("password"))
            );
            passwordInput.clear();
            passwordInput.sendKeys(password); // Use value from properties



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





//-------------------INBOX LINK---------------------------
            // Wait for the Inbox link to be present and clickable
            WebElement inboxLink = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[@href='/upyog-ui/employee/ptr/petservice/inbox' and contains(text(), 'Inbox')]")
                    )
            );
            // Optionally scroll to the link (improves reliability)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", inboxLink);

            // Click the Inbox link
            inboxLink.click();


            System.out.println("now opening the first application");


//------------------------SELECTING THE FIRST APPLICATION NUMBER------------------------
            System.out.println("Getting into the application");
            WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.table")));
            WebElement firstRow = table.findElement(By.cssSelector("tbody > tr:first-child"));
            WebElement firstApplicationLink = firstRow.findElement(By.cssSelector("td .link a"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", firstApplicationLink);
            Thread.sleep(300); // Optional
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstApplicationLink);





//-----------------------------TAKE ACTION BUTTON----------------------------

            clickTakeActionButton(driver, wait);

//--------------------TAKE ACTION MENU---------------------------------
            handleTakeActionMenu(driver, wait); //verify


//--------------------------POPUP WORKFLOW-----------------------


            System.out.println("again going to approve");
//--------------------------TAKE ACTION MENU --------------------------------
            clickTakeActionButton(driver, wait);
            handleTakeActionMenu(driver, wait); //approve


            clickTakeActionButton(driver, wait);

            handleTakeActionMenu(driver, wait); //pay





//---------------------- MOBILE NUMBER INPUT ON RECEIPT PAGE -------------


            // Wait for the mobile number input field to be visible
            WebElement mobileInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("payerMobile"))
            );
            // Clear any existing value (optional)
            mobileInput.clear();
            // Enter the mobile number
            mobileInput.sendKeys("9847584944");




//----------------------- COLLECT PAYMENT ----------------------------


            WebElement collectPaymentButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(@class, 'submit-bar') and .//header[normalize-space()='Collect Payment']]")
                    )
            );
            collectPaymentButton.click();




//-----------------------DOWNLOADING THE PAYMENT RECEIPTS ----------------------
            // Wait for all SVG button containers to be visible
            List<WebElement> svgButtons = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.cssSelector("div.primary-label-btn.d-grid")
                    )
            );
// Loop through each button container and click the SVG inside
            for (WebElement buttonContainer : svgButtons) {
                // Find the SVG element inside the container
                WebElement svg = buttonContainer.findElement(By.tagName("svg"));
                // Optionally scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", svg);
                Thread.sleep(300); // Optional for UI stability
                // Click the SVG button
                svg.click();
                // Optional: print which button was clicked
                System.out.println("Clicked button: " + buttonContainer.getText().trim());
                Thread.sleep(1000); // Optional: wait between clicks if needed
            }



































            // Add further steps as needed

            Thread.sleep(50000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //driver.quit(); // Ensure browser closes
        }
    }
}
