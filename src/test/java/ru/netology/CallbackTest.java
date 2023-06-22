package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallbackTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldCallbackTest()  {
        // загрузка страницы
        driver.get("http://localhost:9999");
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Вася Пупкин");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79991234567");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button.button.button_view_extra.button_size_m.button_theme_alfa-on-white")).click();
        String text = driver.findElement((By.cssSelector("[data-test-id=order-success]"))).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

}
