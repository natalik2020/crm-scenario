package crmScenario;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class AppTestPageObjectTest {

    public static final String createProjectLink_XPATH = "//a[text()='Создать проект']";
    public static final String DIV_ID_SELECT_2_DROP_INPUT = "//div[@id='select2-drop']//input";
    public static final String DIV_CLASS_SELECT_2_CONTAINER_SELECT_2 = "//div[@class='select2-container select2']";
    public static final String IFRAME_CONTAINS_ID_CRM_PROJECT_REQUIREMENTS_MANAGEMENT = "//iframe[contains(@id, 'crm_project_requirementsManagement')]";
    public static final String PROJECT_TESTING = "//iframe[contains(@id, 'crm_project_testing')]";
    public static final String PROJECT_DEVELOPMENT = "//iframe[contains(@id, 'crm_project_development')]";
    public static final String PROJECT_CONFIG_MANAGEMENT = "//input[contains(@id, 'crm_project_configManagement')]";
    public static final String X_PATH_ETAPY = "//input[contains(@id, 'crm_project_skipSpeedChecks')]";
    public static final String SUBMIT_AND_CONTAINS_TEXT_ХРАНИТЬ = "//button[contains(@type, 'submit') and contains(text(),'хранить')]";
    public static final String XPATH_NOVAYA_ZAYAVKA = "//a[text()='Новая']";
    public static final String ID_CRM_OWNERSHIPTYPE_NAME = "//input[contains(@id, 'crm_ownershiptype_name')]";

    private static WebDriver driver;

    private WebDriverWait webDriverWait;

    @FindBy(xpath = "//a/span[text()='Проекты']")
    WebElement projectMenuElement;
    @FindBy(xpath = "//li[@data-route='crm_project_index']/a")
    WebElement createLink;
    @FindBy(xpath = createProjectLink_XPATH)
    WebElement createProjectLink;
    @FindBy(name = "crm_project[name]")
    WebElement projectNameInput;
    @FindBy(xpath = "//span[text()='Укажите организацию']")
    WebElement ukazOrganizationSpan;
    @FindBy(xpath = "//div[@id='select2-drop']//input")
    WebElement ukazOrganizationInput;
    @FindBy(xpath = "//div[contains(@id, 's2id_crm_project_contactMain-uid')]/a")
    WebElement contactnoyeLicoLink;
    @FindBy(xpath = "//div[@id='select2-drop']//input")
    WebElement contactnouyLicoInput;
    @FindBy(xpath = "//iframe[contains(@id, 'crm_project_planning-uid')]")
    WebElement framePlanirovaniye;
    @FindBy(xpath = "//body")
    WebElement planirovaniyeBody;
    @FindBy(xpath = IFRAME_CONTAINS_ID_CRM_PROJECT_REQUIREMENTS_MANAGEMENT)
    WebElement trebovaniyaBody;
    @FindBy(xpath = "//body")
    WebElement bodyElem;
    @FindBy(xpath = PROJECT_TESTING)
    WebElement projectTestingElement;
    @FindBy(xpath = "//body")
    WebElement testKachestvaBody;
    @FindBy(xpath = PROJECT_DEVELOPMENT)
    WebElement razrabotka;
    @FindBy(xpath = "//body")
    WebElement razrabotkaBody;
    @FindBy(xpath = PROJECT_CONFIG_MANAGEMENT)
    WebElement upravleniyeConfiguraciej;
    @FindBy(xpath = X_PATH_ETAPY)
    WebElement etapyElement;
    @FindBy(xpath = "//select[contains(@id, 'crm_project_businessUnit')]")
    WebElement podrazdeleniyeSelect;
    @FindBy(xpath = "//select[contains(@id, 'crm_project_curator')]")
    WebElement curatorProjectSelect;
    @FindBy(xpath = "//select[contains(@id, 'crm_project_rp')]")
    WebElement rukovodProjectSelect;
    @FindBy(xpath = "//select[contains(@id, 'crm_project_administrator')]")
    WebElement admProjectSelect;
    @FindBy(xpath = "//select[contains(@id, 'crm_project_manager')]")
    WebElement managerSelect;
    @FindBy(xpath = SUBMIT_AND_CONTAINS_TEXT_ХРАНИТЬ)
    WebElement submitSaveElement;

    @FindBy(xpath = "//a/span[text()='Справочники']")
    WebElement spravochnikProjectMenuElement;
    @FindBy(xpath = "//li[@data-route='crm_ownershiptype_index']/a")
    WebElement spravochnicLink;
    @FindBy(xpath = XPATH_NOVAYA_ZAYAVKA)
    WebElement novayaZayavka;
    @FindBy(xpath = ID_CRM_OWNERSHIPTYPE_NAME)
    WebElement naimenSpravochnicInput;
    @FindBy(id = "prependedInput")
    WebElement prependedInput;
    @FindBy(xpath = "//button")
    WebElement buttonElementLodin;
    @FindBy(id = "prependedInput2")
    WebElement prependedInput2;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        PageFactory.initElements(driver, this);

        driver.manage().window().maximize();
        webDriverWait = new WebDriverWait(driver, 5);

        loginToCrm();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @Description("Тест на создание заявки")
    public void test1() throws InterruptedException {
        step1();
        createProject();
        vybratOrganization();
        contaktnoyeLico();
        planirovaniye();
        trebovaniya();
        testKachestva();
        razrabotka();
        upravKonfiguraciej();
        etapy();
        podrazdeleniye();
        kuratorProject();
        rucovoditelProject();
        admProject();
        manager();

        saveProject();

        //Проверка
        {
            Thread.sleep(4000);
            String xpathExpression = "//div[@class='message' and contains(text(),'охран')]";
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
            String text = driver.findElement(By.xpath(xpathExpression)).getText();
            Assertions.assertTrue(text.contains("Проект сохранен"));
        }
    }

    @Step("Сохранить проект")
    private void saveProject() {
        {
            Actions actions1 = new Actions(driver);
            actions1.moveToElement(submitSaveElement).perform();
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(SUBMIT_AND_CONTAINS_TEXT_ХРАНИТЬ)));
            submitSaveElement.click();
        }
    }

    @Step("Выбрать менеджера")
    private void manager() throws InterruptedException {
        {
            Select dropdown = new Select(managerSelect);
            dropdown.selectByIndex(1);
            managerSelect.sendKeys(Keys.ESCAPE);
            Thread.sleep(1000);
        }
    }

    @Step("Выбрать администратора проекта")
    private void admProject() {
        {
            Select dropdown = new Select(admProjectSelect);
            dropdown.selectByIndex(1);
        }
    }

    @Step("Выбрать руководителя проекта")
    private void rucovoditelProject() {
        {
            Select dropdown = new Select(rukovodProjectSelect);
            dropdown.selectByIndex(1);
        }
    }

    @Step("Выбрать куратора проекта")
    private void kuratorProject() {
        Select dropdown = new Select(curatorProjectSelect);
        dropdown.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");
    }

    @Step("Выбрать подразделение")
    private void podrazdeleniye() {
        Select dropdown = new Select(podrazdeleniyeSelect);
        dropdown.selectByIndex(1);
    }

    @Step("Заполнить поле этапы")
    private void etapy() {
        {
            String textDannye = "test33";
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(X_PATH_ETAPY)));
            etapyElement.sendKeys(Keys.SPACE);
        }
    }

    @Step("Заполнить поле разработка")
    private void razrabotka() {
        {
            String textDannye = "raz22";
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(PROJECT_DEVELOPMENT)));
            driver.switchTo().frame(razrabotka);
            razrabotkaBody.sendKeys(textDannye);
            razrabotkaBody.sendKeys(Keys.TAB);
            driver.switchTo().defaultContent();
        }
    }

    @Step("Заполнить поле управление конфигурацией")
    private void upravKonfiguraciej() {
        {
            String textDannye = "test33";
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(PROJECT_CONFIG_MANAGEMENT)));
            upravleniyeConfiguraciej.sendKeys(textDannye);
        }
    }

    @Step("Заполнить поле тест качества")
    private void testKachestva() {
        {
            String textDannye = "test33";
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(PROJECT_TESTING)));
            driver.switchTo().frame(projectTestingElement);
            testKachestvaBody.sendKeys(textDannye);
            testKachestvaBody.sendKeys(Keys.TAB);
            driver.switchTo().defaultContent();
        }
    }

    @Step("Заполнить поле планирование")
    private void planirovaniye() {
        driver.switchTo().frame(framePlanirovaniye);
        planirovaniyeBody.sendKeys("testtest");
        planirovaniyeBody.sendKeys(Keys.TAB);
        driver.switchTo().defaultContent();
    }

    @Step("Выбрать из выпадающего списка контактное лицо")
    private void contaktnoyeLico() {
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(DIV_CLASS_SELECT_2_CONTAINER_SELECT_2)));
        contactnoyeLicoLink.click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(DIV_ID_SELECT_2_DROP_INPUT)));
        contactnouyLicoInput.sendKeys("1111");
        contactnouyLicoInput.sendKeys(Keys.ENTER);
    }

    @Step("Выбрать из выпадающего списка организацию")
    private void vybratOrganization() throws InterruptedException {
        ukazOrganizationSpan.click();
        ukazOrganizationInput.sendKeys("test");

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='select2-result-label']")));
        List<WebElement> organizationVars = driver.findElements(By.xpath("//div[@class='select2-result-label']"));
        organizationVars.get(0).click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@id, 's2id_crm_project_contactMain-uid')]/a")));
        webDriverWait.until(ExpectedConditions.textToBePresentInElement(
                driver.findElement(By.xpath("//div[contains(@id, 's2id_crm_project_company')]/a")), "123test"));

        Thread.sleep(1000);
    }

    @Step("Нажать кнопку: создать проект")
    private void createProject() {
        Actions actions = new Actions(driver);
        actions.moveToElement(projectMenuElement).perform();
        createLink.click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(createProjectLink_XPATH)));
        createProjectLink.click();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.name("crm_project[name]")));
        String projectName = "NNN-project_" + UUID.randomUUID();
        projectNameInput.sendKeys(projectName);
    }

    @Step("Заполнить поле требования")
    private void trebovaniya() {
        String textDannye = "treb11";
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(IFRAME_CONTAINS_ID_CRM_PROJECT_REQUIREMENTS_MANAGEMENT)));
        driver.switchTo().frame(trebovaniyaBody);
        bodyElem.sendKeys(textDannye);
        bodyElem.sendKeys(Keys.TAB);
        driver.switchTo().defaultContent();
    }

    private void step1() throws InterruptedException {
        Thread.sleep(3000);
    }

    @Test
    @Description("Тест на создание справочника")
    public void test2() throws InterruptedException {
        sozdatSpravochnic();
        naimenovanieSpravochnica();
        saveProject();

        //Проверка
        {
            Thread.sleep(4000);
            String xpathExpression = "//div[@class='message' and contains(text(),'охран')]";
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
            String text = driver.findElement(By.xpath(xpathExpression)).getText();
            Assertions.assertTrue(text.contains("Форма собственности сохранена"));
        }
    }

    @Step("Заполнить поле Наименование")
    private void naimenovanieSpravochnica() throws InterruptedException {
        {
            Thread.sleep(2000);
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(ID_CRM_OWNERSHIPTYPE_NAME)));
            naimenSpravochnicInput.sendKeys("Наименование" + UUID.randomUUID());
        }
    }

    @Step("Нажать кнопку: создать справочник")
    private void sozdatSpravochnic() {
        Actions actions = new Actions(driver);
        actions.moveToElement(spravochnikProjectMenuElement).perform();
        spravochnicLink.click();
        sozdatNovuyZayavky();
    }

    @Step("Нажать кнопку: создать новую заявку")
    private void sozdatNovuyZayavky() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_NOVAYA_ZAYAVKA)));
        novayaZayavka.click();
    }

    @Step("Залогиниться")
    private void loginToCrm() {
        driver.get("https://crm.geekbrains.space/user/login");
        prependedInput.sendKeys("Applanatest1");
        prependedInput2.sendKeys("Student2020!");
        buttonElementLodin.click();
    }
}
