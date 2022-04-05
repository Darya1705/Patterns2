package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;


import java.util.Locale;

import static io.restassured.RestAssured.given;

public class AuthTest {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost").setPort(9999).setAccept(ContentType.JSON).setContentType(ContentType.JSON).log(LogDetail.ALL).build();

    static void sendRequest(RegistrationDto user) {

        given().spec(requestSpec).body(user).when().post("/api/system/users").then().statusCode(200);
    }

    public static String randomLogin() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username();
        return login;
    }

    public static String randomPass() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        return password;
    }

    public static RegistrationDto getNewUser(String status) {
        RegistrationDto user = new RegistrationDto(randomLogin(), randomPass(), status);
        return user;
    }

    public static RegistrationDto getRegisteredUser(String status) {
        RegistrationDto registeredUser = getNewUser(status);
        sendRequest(registeredUser);
        return registeredUser;
    }


    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}