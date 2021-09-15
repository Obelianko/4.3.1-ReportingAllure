package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate dayPlus = LocalDate.now();
        return dayPlus.plusDays(3 + shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.address().city();
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().fullName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static User generateUser(String locale) {
            return new User(generateCity(locale), generateName(locale), generatePhone(locale));

        }
    }

    @Data
    @RequiredArgsConstructor
    public static class User {
        private final String city;
        private final String name;
        private final String phone;
    }
}

//    Faker faker = new Faker();
//
//    String name = faker.name().fullName(); // Miss Samanta Schmidt
//    String firstName = faker.name().firstName(); // Emory
//    String lastName = faker.name().lastName(); // Barton
//
//    String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449


