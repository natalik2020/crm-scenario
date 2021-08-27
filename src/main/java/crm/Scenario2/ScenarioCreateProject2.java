package crm.Scenario2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.UUID;

public class ScenarioCreateProject2 {
    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        loginToCrm();

        Actions actions = new Actions(driver);
        WebElement projectMenuElement = driver.findElement(By.xpath("//a/span[text()='Справочники']"));
        actions.moveToElement(projectMenuElement).perform();

        driver.findElement(By.xpath("//li[@data-route='crm_ownershiptype_index']/a")).click();

        {
            String xpathExpression = "//a[text()='Новая']";
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
            driver.findElement(By.xpath(xpathExpression)).click();
        }

        // Наимен
        {
            Thread.sleep(2000);
            String xpath = "//input[contains(@id, 'crm_ownershiptype_name')]";
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            WebElement element = driver.findElement(By.xpath(xpath));
            element.sendKeys("Наименование" + UUID.randomUUID());
            element.sendKeys(Keys.ENTER);
        }

        //сохранить
        {
            String xpathExpression = "//button[contains(@type, 'submit')and contains(@class, 'btn-primary')]";

            Actions actions1 = new Actions(driver);
            WebElement projectMenuElement1 = driver.findElement(By.xpath(xpathExpression));
            actions1.moveToElement(projectMenuElement1).perform();

            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
            driver.findElement(By.xpath(xpathExpression)).click();
        }

        Thread.sleep(5000);
        driver.switchTo().parentFrame();
    }

    public static void loginToCrm() {
        driver.get("https://crm.geekbrains.space/user/login");
        driver.findElement(By.id("prependedInput")).sendKeys("Applanatest1");
        driver.findElement(By.id("prependedInput2")).sendKeys("Student2020!");
        driver.findElement(By.xpath("//button")).click();
    }


}
