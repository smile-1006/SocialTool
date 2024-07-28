package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {

    // private static final List<String> SOCIAL_MEDIA_DOMAINS = Arrays.asList("linkedin.com", "facebook.com", "instagram.com");

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide the full URL as a command-line argument.");
            return;
        }

        String socialMediaUrl = args[0];

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        WebDriver driver = setupWebDriver();

        checkSocialMediaActivity(driver, socialMediaUrl);

        driver.quit();
    }

    private static WebDriver setupWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }

    private static void checkSocialMediaActivity(WebDriver driver, String url) {
        try {
            driver.get(url);
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);
            Elements posts = doc.select("time");

            for (Element post : posts) {
                String datetime = post.attr("datetime").substring(0, 10); // Extracting date portion only
                if (isWithinLastSixMonths(datetime)) {
                    System.out.println("Active page: " + url);
                    return;
                }
            }
            System.out.println("Inactive page: " + url);
        } catch (Exception e) {
            System.out.println("Error checking " + url + ": " + e.getMessage());
        }
    }

    public static boolean isWithinLastSixMonths(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        LocalDate now = LocalDate.now();
        return !date.isBefore(now.minus(6, ChronoUnit.MONTHS));
    }
}
