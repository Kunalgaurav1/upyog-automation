# UPYOG Selenium Test Automation

## Overview
This project contains automated test scripts for the UPYOG pet registration application using Selenium WebDriver with Java. The automation suite simulates user interactions with the web application to verify functionality of the pet registration workflow.

## Features
- Automated login with OTP verification
- City selection
- Navigation to pet registration module
- Form filling for pet owner details
- Pet type and breed selection

## Prerequisites
- Java JDK 8 or higher
- Maven
- Chrome browser
- ChromeDriver (matching your Chrome version)

## Setup
1. Ensure ChromeDriver is installed at `/usr/local/bin/chromedriver` or update the path in the code
2. Build the project using Maven:
   ```
   mvn clean install
   ```

## Project Structure
- `src/main/java/org/upyog/testing/Automation/PetApplication.java` - Main test script for pet registration workflow

## Test Flow
1. Login to the application using mobile number and OTP
2. Select city
3. Navigate to pet registration module
4. Fill owner details (father's name, email)
5. Select pet type and breed

## Usage
The test is configured as a Spring component with `@PostConstruct` annotation, which means it will run automatically when the Spring context is initialized.

## Notes
- The test uses a fixed mobile number (9999999999) and OTP (123456) for login
- Various Selenium techniques are implemented including:
  - Explicit waits
  - JavaScript execution
  - Actions API for complex interactions
  - Multiple locator strategies (CSS, XPath)