import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.openqa.selenium.WebElement as WebElement
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.By.ByClassName

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.FileInputStream as FileInputStream
import java.io.FileNotFoundException as FileNotFoundException
import java.io.IOException as IOException
import java.util.Date as Date

import java.text.SimpleDateFormat
import org.apache.poi.xssf.usermodel.XSSFCell as XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow as XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet as XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import java.lang.String as String
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.FluentWait;
import java.util.function.Function;
import java.util.concurrent.TimeUnit;




class Global{
	
 static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
 static def date= dateFormat.format(new Date());
  static  writer = new FileWriter(System.getProperty("user.home")+ "/"+ date+"chvTasks.csv");
  
}


 

testLoginSuccess( //@Field 	WebDriver driver = DriverFactory.getWebDriver()
    ) 

void writeCsvLine(String[] record) {
	
	String r = record.join(',');
	//String r = record.join(',');
	Global.writer.append(r);
	Global.writer.append('\n');
	Global.writer.flush();
}

/**
 * Entry point for execution of script
 */
void testLoginSuccess() {
	
	// Write Output CSV Header
	Global.writer.append('\n');
	String[] line = ["username", "tasks", "task action date", "current date", "title", "description", "recipient"];
	writeCsvLine(line);
	
    WebUI.openBrowser('')
	WebUI.maximizeWindow()

	// Get input data from excel file
    FileInputStream file = new FileInputStream(new File('//Users//fredrickonyango//Katalon Studio//Ogembo_Cohort2_Script//Data Files//TestData.xlsx'))
    XSSFWorkbook workbook = new XSSFWorkbook(file)
    XSSFSheet sheet = workbook.getSheetAt(0)

    Integer rowSize = sheet.getLastRowNum()
	  
    for (int x = 1; x <= rowSize; x++) {
	
		XSSFRow row = sheet.getRow(x);
		String username = row.getCell(0).getStringCellValue();
		String password = row.getCell(1).getStringCellValue();
		
		startNewSession(username, password);
    }
	
	Global.writer.close();
}

/**
 * Start a new session
 * 1. Login user
 * 2. Get Users tasks
 * 3. Write task record (date, title, description, recipient) to CSV file
 * @param username
 * @param pass
 */
void startNewSession(String username, String pass) {
	
	WebUI.navigateToUrl('https://lg-innovation-ke.app.medicmobile.org')
	
	boolean isLoggedIn = login(username, pass);
	
	if (isLoggedIn) {
		
		processTasks(username);
		logout();
	}
	// def tasks = countTasks()
	
	
	
	// logout()
	//closeBrowser()

}

/**
 * Get Users tasks
 * Write task record (date, title, description, recipient) to CSV file
 */
void processTasks(String username) {
	WebDriver driver = DriverFactory.getWebDriver();
	WebElement taskListContainer = driver.findElement(By.id("tasks-list"));
	List<WebElement> taskList = taskListContainer.findElements(By.tagName("ul"));
	
	int taskCount = 0;
	if (taskList.size() > 0) {
		List<WebElement> taskListItems = taskList.get(0).findElements(By.tagName("li"))
		taskCount = taskListItems.size();
		
		for (WebElement li: taskListItems) {
			WebElement contentContainer = li.findElement(By.className("content"));
			
			WebElement heading = contentContainer.findElement(By.className("heading"));
			String title = heading.findElement(By.tagName("h4")).findElement(By.tagName("span")).getText();
			String date = heading.findElement(By.className("relative-date")).getAttribute("title").replace(',', ' ');
			String today = Global.date.toString();
			WebElement summary = contentContainer.findElement(By.className("summary"));
			String description = summary.findElement(By.tagName("p")).getText();
			//String description =  "";
			String recipient = ""
			
			String[] line = [username, String.valueOf(taskCount), date, today, title, description, recipient];
			writeCsvLine(line);
		}
	} else {
	
		// Do nothing
		String[] line = [username, String.valueOf(taskCount), "", Global.date.toString(), "", "", ""];
		writeCsvLine(line);
	}

}

/**
 * Login user
 * @param username
 * @param password
 */
boolean login(def username, def password) {
    WebDriver driver = DriverFactory.getWebDriver()

    List<WebElement> moduleOptions = driver.findElements(By.cssSelector('input'))

    for (int i = 0; i < moduleOptions.size(); i++) {
        

        try {
            String name = moduleOptions.get(i).getAttribute('id')

            switch (name) {
                case 'user':
                    moduleOptions.get(i).sendKeys(username)
					System.out.println(username)
					//FileWriter writer = new FileWriter("user.csv");
					//writer.append(username);
					//writer.append(',');
					//writer.append(i); //whatever String this is
					//writer.flush();
					//writer.close();
					
                    break
                case 'password':
                    moduleOptions.get(i).sendKeys(password)
					

                    break
            }
        }
        catch (Exception e) {
            System.out.print(e)
        } 
    }
    
    moduleOptions = driver.findElements(By.cssSelector('button'))

    for (int i = 0; i < moduleOptions.size(); i++) {
        moduleOptions.get(i).click()
		System.out.println(i)
    }
	
	//WebUI.waitForElementPresent(findTestObject('summary'), 10)
    //WebUI.waitForElementPresent(findTestObject('Tasks'), 100)
	//WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Medic Mobile/div_Tasks'),0)

	
	// Waiting 30 seconds for an element to be present on the page, checking
	// for its presence once every 5 seconds.
	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(1000, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
 
	WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
	  public WebElement apply(WebDriver d) {
		return d.findElement(By.cssSelector('a[href^="#tasks"]'));
	  }
	});


	
   WebUI.delay(1)
	return true;
}

int countTasks() {
	
	
	
    WebDriver driver = DriverFactory.getWebDriver()
	//WebUI.waitForElementPresent(By.cssSelector('a[href^="#tasks"]'), 50)
    //WebUI.delay(130)
	//WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id('a[href^="#tasks"]')));

    List<WebElement> moduleOptions = driver.findElements(By.cssSelector('a[href^="#tasks"]'))

   return moduleOptions.size()
	
}


//List<String> getTaskDates() {
//	List<WebElement> dates = driver.find... 
//	
//	return dates.map(element => element.getHtmlContent())	
//}
//
//List<String> getTaskTitles() {
//	List<WebElement> dates = driver.find...
//	
//	return dates.map(element => element.getHtmlContent())
//}

void logout(){
	
	WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/span-bars1'))
	WebUI.delay(1)
	WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/a_Log out'))
	WebUI.delay(1)
	WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/a_Yes'))
	WebUI.delay(1)
	
}

void closeBrowser(){
	
WebUI.closeBrowser()
}

