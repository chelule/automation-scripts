import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.openqa.selenium.WebElement as WebElement
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
//import com.kms.katalon.core.moåel.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.FileInputStream as FileInputStream
import java.io.FileNotFoundException as FileNotFoundException
import java.io.IOException as IOException
import java.util.Date as Date
import org.apache.poi.xssf.usermodel.XSSFCell as XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow as XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet as XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import java.lang.String as String

WebUI.openBrowser('')

WebUI.navigateToUrl('https://lg-innovation-ke.app.medicmobile.org/medic/login?redirect=%2Fmedic%2F_design%2Fmedic%2F_rewrite%2F')

WebUI.setText(findTestObject('Object Repository/Page_Medic Mobile/input_User name_user'), 'annny')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Medic Mobile/input_Password_password'), 'geb7Z7Jt5mpqmWkc5uq76A==')

WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/button_Login'))

WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/span_Messages'))

WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/div_Tasks'))

WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/span-bars'))

WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/a_Log out'))

WebUI.click(findTestObject('Object Repository/Page_Medic Mobile/a_Yes'))

void testLoginSuccess() {
    WebUI.openBrowser('')

    WebDriver driver = DriverFactory.getWebDriver()

    WebUI.navigateToUrl('https://lg-innovation-ke.app.medicmobile.org')

    System.out.println(driver.currentUrl)

    FileInputStream file = new FileInputStream(new File('//Users//fredrickonyango//Katalon Studio///Users//fredrickonyango//Katalon Studio//Ogembo_Cohort2_Script//Data Files.xlsx'))

    XSSFWorkbook workbook = new XSSFWorkbook(file)

    XSSFSheet sheet = workbook.getSheetAt(0)

    int rows = sheet.getPhysicalNumberOfRows()

    for (int x = 1; x < rows; x++) {
        try {
            String field = sheet.getRow(x).getCell(1).getStringCellValue()

            String type = sheet.getRow(x).getCell(0).getStringCellValue()

            String data = sheet.getRow(x).getCell(2).getStringCellValue()

            switch (type) {
                case 'radio':
                    List<WebElement> moduleOptions = driver.findElements(By.name(field))

                    for (int i = 0; x < moduleOptions.size(); i++) {
                        if (moduleOptions.get(i).getAttribute('value').equals(data)) {
                            moduleOptions.get(i).click()
                        }
                    }
                    
                    break
                default:
                    moduleOptions.get(i).sendKeys(data)}
        }
        catch (Exception e) {
            System.out.print(e)
        } 
    }
    
    moduleOptions = driver.findElements(By.cssSelector('button'))

    for (int i = 0; i < moduleOptions.size(); i++) {
        moduleOptions.get(i).click()
    }
}

//test validation ..
//WebUI.closeBrowser()