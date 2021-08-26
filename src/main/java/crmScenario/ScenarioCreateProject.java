package crmScenario;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class ScenarioCreateProject {
    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        //scenarioWithExtention();

        driver = new ChromeDriver();
        //runJsScriptExample();

        driver.manage().window().maximize();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        loginToCrm();

        Actions actions = new Actions(driver);
        WebElement projectMenuElement = driver.findElement(By.xpath("//a/span[text()='Проекты']"));
        actions.moveToElement(projectMenuElement).perform();

        driver.findElement(By.xpath("//li[@data-route='crm_project_index']/a")).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Создать проект']")));
        driver.findElement(By.xpath("//a[text()='Создать проект']")).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.name("crm_project[name]")));
        driver.findElement(By.name("crm_project[name]")).sendKeys("crm_project[name]");

        driver.findElement(By.xpath("//span[text()='Укажите организацию']")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("test");

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='select2-result-label']")));
        List<WebElement> organizationVars = driver.findElements(By.xpath("//div[@class='select2-result-label']"));
        organizationVars.get(0).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@id, 's2id_crm_project_contactMain-uid')]/a")));
        webDriverWait.until(ExpectedConditions.textToBePresentInElement(
                driver.findElement(By.xpath("//div[contains(@id, 's2id_crm_project_company')]/a")), "123test"));
        Thread.sleep(1000);//TODO: подебажить это ожидание
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='select2-container select2']")));
        driver.findElement(By.xpath("//div[contains(@id, 's2id_crm_project_contactMain-uid')]/a")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//input")));
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("1111");
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys(Keys.ENTER);

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'crm_project_planning-uid')]")));
        driver.findElement(By.xpath("//body")).sendKeys("testtest");
        driver.findElement(By.xpath("//body")).sendKeys(Keys.TAB);
        driver.switchTo().defaultContent();

        //treb
        {
            String xPath = "//iframe[contains(@id, 'crm_project_requirementsManagement')]";
            String textDannye = "treb11";

            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
            driver.switchTo().frame(driver.findElement(By.xpath(xPath)));
            WebElement bodyElem = driver.findElement(By.xpath("//body"));
            bodyElem.sendKeys(textDannye);
            bodyElem.sendKeys(Keys.TAB);
            driver.switchTo().defaultContent();
        }

        //test
        {
            String xPath = "//iframe[contains(@id, 'crm_project_testing')]";
            String textDannye = "test33";

            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
            driver.switchTo().frame(driver.findElement(By.xpath(xPath)));
            WebElement bodyElem = driver.findElement(By.xpath("//body"));
            bodyElem.sendKeys(textDannye);
            bodyElem.sendKeys(Keys.TAB);
            driver.switchTo().defaultContent();
        }
        //raz
        {
            String xPath = "//iframe[contains(@id, 'crm_project_development')]";
            String textDannye = "raz22";

            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
            driver.switchTo().frame(driver.findElement(By.xpath(xPath)));
            WebElement bodyElem = driver.findElement(By.xpath("//body"));
            bodyElem.sendKeys(textDannye);
            bodyElem.sendKeys(Keys.TAB);
            driver.switchTo().defaultContent();
        }

        //Управление конфигурацией
        {
            String xPath = "//input[contains(@id, 'crm_project_configManagement')]";
            String textDannye = "test33";

            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
            driver.findElement(By.xpath(xPath)).sendKeys(textDannye);
        }

        //Этапы
        {
            String xPath = "//input[contains(@id, 'crm_project_skipSpeedChecks')]";
            String textDannye = "test33";

            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
            driver.findElement(By.xpath(xPath)).sendKeys(Keys.SPACE);
        }
        //
        {
            String xpathSelect = "//select[contains(@id, 'crm_project_businessUnit')]";
            Select dropdown = new Select(driver.findElement(By.xpath(xpathSelect)));
            dropdown.selectByIndex(1);
        }

        {
            String xpathSelect = "//select[contains(@id, 'crm_project_curator')]";
            Select dropdown = new Select(driver.findElement(By.xpath(xpathSelect)));
            dropdown.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");
        }

        //рук проекта
        {
            String xpathSelect = "//select[contains(@id, 'crm_project_rp')]";
            Select dropdown = new Select(driver.findElement(By.xpath(xpathSelect)));
            dropdown.selectByIndex(1);
        }

        //адм проекта
        {
            String xpathSelect = "//select[contains(@id, 'crm_project_administrator')]";
            Select dropdown = new Select(driver.findElement(By.xpath(xpathSelect)));
            dropdown.selectByIndex(1);
        }

        //менедж
        {
            String xpathSelect = "//select[contains(@id, 'crm_project_manager')]";
            Select dropdown = new Select(driver.findElement(By.xpath(xpathSelect)));
            dropdown.selectByIndex(1);
        }

        //сохранить
        driver.findElement(By.xpath("//button[contains(@type, 'submit')and contains(@class, 'btn-primary')]")).click();

        Thread.sleep(5000);

        driver.switchTo().parentFrame();
    }

    public static void loginToCrm() {
        driver.get("https://crm.geekbrains.space/user/login");
        driver.findElement(By.id("prependedInput")).sendKeys("Applanatest1");
        driver.findElement(By.id("prependedInput2")).sendKeys("Student2020!");
        driver.findElement(By.xpath("//button")).click();
    }

    public static void scenarioWithExtention() throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-data-dir=src/main/resources/chrome_profile");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://afisha.ru");
        Thread.sleep(10000);
    }

    public static void runJsScriptExample() throws InterruptedException {
        driver.get("https://afisha.ru");
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        javascriptExecutor.executeScript("function getElementByXpath(path) {\n" +
                "  return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n" +
                "}\n" +
                "\n" +
                "getElementByXpath(\"//div[@data-test='HONEY-AD AD-ad_1']\").remove();");
        Thread.sleep(10000);
    }
}
