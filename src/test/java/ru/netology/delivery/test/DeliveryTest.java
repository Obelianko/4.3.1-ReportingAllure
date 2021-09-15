package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class DeliveryTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
    }

    @Test
    @DisplayName("Should Delivery Request")
    void shouldDeliveryRequest() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var plusDaysFirstMeeting = 4;
        var firstDate = DataGenerator.generateDate(plusDaysFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").setValue(firstDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(visible).
                shouldHave(exactText("Успешно!\n" + "Встреча успешно запланирована на " + firstDate));
    }
}



