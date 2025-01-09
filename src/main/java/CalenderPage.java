import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CalenderPage {

    private WebDriver driver;
    private Actions action;
    private WebDriverWait wait;

    private By inputDateField = By.id("txtStartDate");

    //  style is empty to choose only the active prev and next buttons
    private By prevButton = By.cssSelector("div[style=''] th[class='prev']");
    private By nextButton = By.cssSelector("div[style=''] th[class='next']");

    // stlye is empty to select the active switch button
    private By switchButton = By.cssSelector("div[style=''] th[class='datepicker-switch']");

    // we used the contains method to choose all the displayed years as a list
    private By yearsList = By.xpath("//span[contains(@class,'year')]");

    // %d means that this will be replaced with a specific value
    private String specificYear = "//span[contains(@class,'year') and text() = '%d']";

    // we used the contains method to choose all the displayed months as a list
    private By monthList = By.xpath("//span[contains(@class,'month')]");
    private String specificDay = "//td[@class='day'][text() = '%d']";

    private By calculateButton = By.id("btnCalculate");

    public CalenderPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        // we made an object from Actions class to be able to scroll down in our page
        this.action = new Actions(driver);
        // deltaX is for right and left, deltaY is for up and down, we can know the values by try and erro
        action.scrollByAmount(0,500).pause(300).perform();
    }

    private void selectYear(int year){
        driver.findElement(inputDateField).click();

        // we can use for loop instead of duplicating the same line of code
        driver.findElement(switchButton).click();
        driver.findElement(switchButton).click();

        // while(true) means it will keep repeated until I break it
        while (true) {
            // This block of code should be repeated until the year is clicked
            List<WebElement> yearElementsList = driver.findElements(yearsList);

            // we have parsed the extracted first and the last year from the list into integer to be able to compare them or using them at the IF condition
            int firstYear = Integer.parseInt(yearElementsList.getFirst().getText());
            int lastYear = Integer.parseInt(yearElementsList.getLast().getText());

            if (year >= firstYear && year <= lastYear) {
                // we used String.format method to replace the %d in our string to the specific year we want to use in our xpath
                String updatedMonthsXpath = String.format(specificYear, year);
                driver.findElement(By.xpath(updatedMonthsXpath)).click();
                break;
            } else if (year < firstYear) {
                driver.findElement(prevButton).click();
            } else {
                driver.findElement(nextButton).click();
            }
        }
    }

    private void selectMonth(int monthIndex){
        wait.until(ExpectedConditions.visibilityOfElementLocated(monthList));
        List<WebElement> monthElementsList = driver.findElements(monthList);
        // we have subtracted 1 from the monthIndex because the list index begins from 0
        monthElementsList.get(monthIndex-1).click();
    }

    private void selectDay(int day){
        String updatedDaysXpath = String.format(specificDay, day);
        driver.findElement(By.xpath(updatedDaysXpath)).click();
    }

    public void pickDate(int day, int month, int year){
        selectYear(year);
        selectMonth(month);
        selectDay(day);
        action.scrollByAmount(0,200).pause(100).perform();
        driver.findElement(calculateButton).click();
    }

    public String getDateValue(){
        return driver.findElement(inputDateField).getDomAttribute("value");
    }




}
