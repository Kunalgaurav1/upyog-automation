package org.upyog.Automation.Modules.TradeLicense;

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
import org.upyog.Automation.Utils.ConfigReader;

@Component
public class InboxEmpTl {

    // --- Helper methods ---

    public void clickTakeActionButton(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        WebElement takeActionButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@class, 'submit-bar') and .//header[normalize-space()='TAKE ACTION']]")
                )
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", takeActionButton);
        Thread.sleep(300);
        takeActionButton.click();
        System.out.println("Clicked TAKE ACTION button");
    }

    public void selectMenuOption(WebDriver driver, WebDriverWait wait, String optionText) throws InterruptedException {
        WebElement menuWrap = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.menu-wrap"))
        );
        List<WebElement> actionOptions = menuWrap.findElements(By.tagName("p"));
        boolean found = false;
        for (WebElement option : actionOptions) {
            if (option.getText().trim().equalsIgnoreCase(optionText)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                Thread.sleep(200);
                option.click();
                System.out.println("Clicked menu option: " + optionText);
                found = true;
                break;
            }
        }
        if (!found) throw new RuntimeException("Menu option '" + optionText + "' not found!");
    }

    public void fillComments(WebDriver driver, WebDriverWait wait, String comment) {
        WebElement textarea = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("comments"))
        );
        textarea.clear();
        textarea.sendKeys(comment);
        System.out.println("Filled comments: " + comment);
    }

    public void clickActionButton(WebDriver driver, WebDriverWait wait, String btnText) throws InterruptedException {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'selector-button-primary')][.//h2[normalize-space()='" + btnText + "']]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        Thread.sleep(200);
        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
        System.out.println("Clicked action button: " + btnText);
    }

    // --- Workflow step methods ---

    public void verifyAndForwardStep(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        clickTakeActionButton(driver, wait);
        selectMenuOption(driver, wait, "VERIFY AND FORWARD");
        fillComments(driver, wait, "verify");
        clickActionButton(driver, wait, "Verify and Forward");
    }

    public void approveStep(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        clickTakeActionButton(driver, wait);
        selectMenuOption(driver, wait, "APPROVE");
        fillComments(driver, wait, "approve");
        clickActionButton(driver, wait, "Approve");
    }

    public void payStep(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        clickTakeActionButton(driver, wait);
        selectMenuOption(driver, wait, "PAY");
        System.out.println("Clicked PAY in workflow.");
    }

    // --- Complete workflow method ---
    public void runWorkflow(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        verifyAndForwardStep(driver, wait); // 1st Verify and Forward
        verifyAndForwardStep(driver, wait); // 2nd Verify and Forward
        approveStep(driver, wait);          // Approve
        payStep(driver, wait);              // Pay
        System.out.println("[SUCCESS] Full workflow completed!");
    }

    @PostConstruct
    public void InboxEmpTl() {
        System.out.println("Inbox Emp TL");

        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        try {
            driver.get("https://niuatt.niua.in/digit-ui/employee/user/login");
            driver.manage().window().maximize();
            System.out.println("Open the Login Portal");

            WebElement usernameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("username"))
            );
            usernameInput.clear();
            usernameInput.sendKeys("TLEMP");

            WebElement passwordInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("password"))
            );
            passwordInput.clear();
            passwordInput.sendKeys("eGov@123");

            System.out.println("filled username and password ");

            WebElement cityDropdownInput = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("div.select input.employee-select-wrap--elipses"))
            );
            WebElement cityDropdownContainer = driver.findElement(By.cssSelector("div.select"));
            WebElement cityDropdownArrow = cityDropdownContainer.findElement(By.tagName("svg"));
            cityDropdownArrow.click();

            Actions actions = new Actions(driver);
            actions.moveToElement(cityDropdownArrow).click().perform();

            WebElement dropdownOptions = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.options-card"))
            );
            WebElement firstCityOption = dropdownOptions.findElement(By.cssSelector(".profile-dropdown--item:first-child"));
            actions.moveToElement(firstCityOption).click().perform();

            WebElement continueButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(@class, 'submit-bar') and .//header[text()='Continue']]")
                    )
            );
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
            continueButton.click();

            Thread.sleep(300);

            WebElement TlInbox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(@href, '/digit-ui/employee/tl/inbox') and contains(normalize-space(.), 'Inbox')]")
                    )
            );
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", TlInbox);
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", TlInbox);
            System.out.println("Clicked Tl Application Inbox .");

            Thread.sleep(1000);
            String appNumber = ConfigReader.get("employee.application.number");
            WebElement appNumberInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("employee-card-input"))
            );
            appNumberInput.clear();
            appNumberInput.sendKeys(appNumber);

            WebElement searchButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.className("submit-bar-search"))
            );
            searchButton.click();

            String appNumber2 = ConfigReader.get("employee.application.number");
            WebElement appLink = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.linkText(appNumber2))
            );
            appLink.click();

            // -------- WORKFLOW EXECUTION --------
            runWorkflow(driver, wait);



            //Generate Recipt page
            WebElement mobileInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("payerMobile"))
            );
            // Clear any existing value (optional)
            mobileInput.clear();
            // Enter the mobile number
            mobileInput.sendKeys("9847584944");


            //collect payment

            WebElement collectPaymentButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(@class, 'submit-bar') and .//header[normalize-space()='Collect Payment']]")
                    )
            );
            collectPaymentButton.click();

            Thread.sleep(50000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //driver.quit(); // Uncomment to close browser after run
        }
    }
}
