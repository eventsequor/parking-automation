package com.parking.pageobjects.reservation;

import com.beust.ah.A;
import com.parking.drivermanager.WebDriverManager;
import com.parking.models.reservations.viewmodel.Reservation;
import com.parking.pageobjects.BasePageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ReservationPageObject extends BasePageObject {

    private final static String xpathReservationButton = "//button[@class='btn btn-primary ripple-surface']";

    @FindBy(xpath = "//a[@class='router-link-active router-link-exact-active nav-link']")
    private WebElement reservationButton;
    @FindBy(xpath = "//ul[@class='navbar-nav me-auto mb-2 mb-lg-0']/li[2]/a")
    private WebElement profileButton;

    @FindBy(xpath = "//ul[@class='navbar-nav me-auto mb-2 mb-lg-0']/li[3]/a")
    private WebElement helpButton;

    @FindBy(xpath = "//ul[@class='navbar-nav me-auto mb-2 mb-lg-0']/li[4]/a")
    private WebElement closeSession;

    @FindBy(xpath = "//div//h5")
    private WebElement containerTitle;

    @FindBy(xpath = xpathReservationButton)
    private WebElement newReservationButton;

    @FindBy(xpath = "//div[@class='table-responsive']//tbody/tr")
    private List<WebElement> rowsReservation;

    @FindBy(xpath = "//div[@class='modal-dialog modal-lg']")
    private WebElement modelDialog;

    @FindBy(id = "form_plat_number")
    private WebElement plateInputText;

    @FindBy(id = "form_schedule_day")
    private WebElement dateReservationInput;

    @FindBy(id = "form_schedule")
    private WebElement schedule;

    @FindBy(id = "form_location_id")
    private WebElement locationInputText;

    @FindBy(xpath = "//div[@class='modal-dialog modal-lg']//button[@class='btn btn-primary ripple-surface']")
    private WebElement saveButton;

    public ReservationPageObject(WebDriverManager webDriverManager) {
        super(webDriverManager);
    }

    public String getTextReservationButton() {
        return getText(reservationButton);
    }

    public String getTextProfileButton() {
        return getText(profileButton);
    }

    public String getTextHelpButton() {
        return getText(helpButton);
    }

    public String getTextCloseSession() {
        return getText(closeSession);
    }

    public String getTextContainerTitle() {
        return getText(containerTitle);
    }

    public String getTextNewReservationButton() {
        return getText(newReservationButton);
    }

    public boolean isPresentNewReservationButton() {
        return isPresent(By.xpath(xpathReservationButton));
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        for (int i = 0; i < 5; i++) {
            System.out.println(atomicInteger.getAndAdd(1));
        }
    }

    public List<Reservation> getListReservation() {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        return rowsReservation.stream()
                .map(webElement -> {
                    List<WebElement> webElementList = getElementsBy(By.xpath(String.format("//div[@class='table-responsive']//tbody/tr[%s]/td", atomicInteger.getAndAdd(1))));
                    return Reservation.builder()
                            .plate(webElementList.size() > 0 ? getText(webElementList.get(0)) : "undefined")
                            .scheduleDay(webElementList.size() > 1 ? getText(webElementList.get(1)) : "undefined")
                            .schedule(webElementList.size() > 2 ? translate(getText(webElementList.get(2))) : "undefined")
                            .location(webElementList.size() > 3 ? getText(webElementList.get(3)) : "undefined")
                            .build();
                })
                .sorted(Comparator.comparing(Reservation::getPlate))
                .collect(Collectors.toList());
    }

    protected String translate(String value) {
        switch (value.toLowerCase()) {
            case "mañana":
                return "morning";
            case "tarde":
                return "afternoon";
            case "día completo":
                return "day";
            case "afternoon":
                return "tarde";
            case "morning":
                return "mañana";
            case "day":
                return "día completo";
            default:
                return "undefined";
        }
    }

    @Step("Click on reservation button")
    public void clickNewReservation() {
        waitSeconds(5);
        try {
            click(newReservationButton);
            if (!isPresentModalDialog()) {
                click(newReservationButton);
            }
        } catch (NoSuchElementException e) {
            waitSeconds(4);
            click(newReservationButton);
        }
    }

    @Step("Is present modal dialog")
    public boolean isPresentModalDialog() {
        return isDisplayed(modelDialog);
    }

    @Step("Send keys {0} into plate input text")
    public void sendKeysPlateInput(String value) {
        sendKeys(plateInputText, value);
    }

    @Step("Set date {0} into date reservation")
    public void sendKeysInputDate(String value) {
        sendKeys(dateReservationInput, value);
    }

    @Step("Select schedule {0}")
    public void selectDate(String value) {
        Select select = new Select(schedule);
        select.selectByVisibleText(value);
    }

    @Step("Select location {0}")
    public void selectLocation(String value) {
        Select select = new Select(locationInputText);
        select.selectByVisibleText(value);
    }

    @Step("Click on save reservation")
    public void clickSaveReservationButton() {
        click(saveButton);
    }

    @Step("Get alert Message")
    public String getAlertMessage() {
        return getAlertMessageFromWindow();
    }
}
