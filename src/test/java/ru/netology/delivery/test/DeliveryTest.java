package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class DeliveryTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldDeliveryRequest() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var firstDate = DataGenerator.generateDate(0);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(firstDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно запланирована на " + firstDate));
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[role=button] .button__content").click();
        $("[data-test-id=replan-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("У вас уже запланирована встреча на другую дату. Перепланировать?\n" +
                "\n" +
                "Перепланировать"));
        $("[data-test-id=replan-notification] .button__text").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));

    }
}







