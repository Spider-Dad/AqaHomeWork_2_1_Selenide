package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class CallbackTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void correctValueTest() {
        driver.get("http://localhost:9999");
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Николай Римский-Корсаков");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79991234567");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement((By.cssSelector("[data-test-id=order-success]"))).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void notCorrectLatinNameTest()  {
        driver.get("http://localhost:9999");
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Нiколай Римский-Корсаков");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79991234567");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        Assertions.assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void notCorrectNumberNameTest() {
        driver.get("http://localhost:9999");
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Ник0лай Римский-Корсаков");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79991234567");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        Assertions.assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void notCorrectSymbolNameTest() {
        driver.get("http://localhost:9999");
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Никол@й Римский-Корсаков");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79991234567");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        Assertions.assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void notCorrectPhoneNotPlusTest() {
        driver.get("http://localhost:9999");
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Николай Римский-Корсаков");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("799912345677");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        Assertions.assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void notCorrectPhoneMore11Test() {
        driver.get("http://localhost:9999");
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Николай Римский-Корсаков");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+799912345677");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        Assertions.assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void notPushAgreementTest() {
        driver.get("http://localhost:9999");
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Николай Римский-Корсаков");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+799912345677");
        WebElement agreementCheckbox = driver.findElement((By.cssSelector("[data-test-id=agreement]")));
        boolean isChecked = agreementCheckbox.isSelected();
        Assertions.assertFalse(isChecked);
        driver.findElement(By.cssSelector("button")).click();

    }

}
