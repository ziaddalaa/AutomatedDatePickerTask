import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CalenderTests {

    private WebDriver driver;
    private CalenderPage calenderPage;
    private String URL = "https://www.bestcase.com/date-calculator/";

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
        calenderPage = new CalenderPage(driver);
    }

    @Test
    public void testPickYear() throws InterruptedException {
        int day = 12; int month = 3; int year = 2001;
        calenderPage.pickDate(day,month,year);
        System.out.println(calenderPage.getDateValue());

        // %02d means that this fields is a two digit values 2 --> 02
        Assert.assertEquals(calenderPage.getDateValue(), String.format("%02d/%02d/%d",month,day,year));
    }
}
