import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Task3 {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:exe\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String baseURL = "https://www.dodax.ca/en-ca/";
        driver.get(baseURL);
        driver.manage().window().maximize();
        Actions a = new Actions(driver);

        driver.findElement(By.cssSelector("div[class=\"mt-3 mt-md-0 ml-md-2\"]>:first-child")).click();
        boolean isCookieDispplayed = driver.findElement(By.cssSelector("div[class=\"mt-3 mt-md-0 ml-md-2\"]>:first-child")).isDisplayed();
        if (isCookieDispplayed)  {
        System.out.println("Coockies NOT accepted");}
        else  {System.out.println("Cookies accepted");}
/// Finding and printing the total number of links in the footer
        List<WebElement> footer = driver.findElements(By.xpath("//nav[@class='row']//a"));
        int totalFooter = footer.size();
        System.out.println("The number of links in footer is : " + totalFooter);

        //finding and printing the number of links in the first column in the footer
        List<WebElement> firstLinksFooter = driver.findElements(By.xpath("//div[@class='ft-footer__column col-12 col-sm-6'][1]//a"));
        int linkCount = firstLinksFooter.size();
        System.out.println("The number of links in first collumn in the footer is " + linkCount);
        //scroll to the footer> accept cookies > than click
        // iterating through every link in the first collumn in the footer, and verifying the right link is open
        // the verifyng process goes through comparing the URL of the new opened window and the href for the clicked link
        String[] links = new String[linkCount];
        //Thread.sleep(1000);
        WebElement footerElem = driver.findElement(By.xpath("//footer"));
        a.moveToElement(footerElem).build().perform();
       firstLinksFooter.get(0).click();
//navigate to element thank click

       for (int i = 0; linkCount > i; i++) {
            links[i] = firstLinksFooter.get(i).getAttribute("href");
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", firstLinksFooter.get(i));
            String urlToCheck = driver.getCurrentUrl();
            Assert.assertEquals(urlToCheck, links[i]);
            driver.navigate().back();
           firstLinksFooter = driver.findElements(By.xpath("//div[@class='ft-footer__column col-12 col-sm-6'][1]//a"));
        }

       List<WebElement> secondLinksFooter = driver.findElements(By.xpath("//div[@class='ft-footer__column col-12 col-sm-6'][2]//a"));
        int secondLinkCount = secondLinksFooter.size();
        String[] secondLinks= new String[secondLinkCount];
        for (int i = 0; secondLinkCount > i; i++){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement secondColumn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ft-footer__column col-12 col-sm-6'][2]")));
            secondLinks[i]= secondLinksFooter.get(i).getAttribute("href");
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", secondLinksFooter.get(i));
           // String parentId = driver.getWindowHandle();
            Set<String> windows = driver.getWindowHandles();
            int numberOfWindows = windows.size();
            if (numberOfWindows == 2) {
                //Set<String> windows = driver.getWindowHandles();
                Iterator<String> it = windows.iterator();
                String parentId = it.next();
                String childId = it.next();
                driver.switchTo().window(childId);
                String urlToCheck = driver.getCurrentUrl();
                Assert.assertEquals(urlToCheck, secondLinks[i]);
                driver.close();
                driver.switchTo().window(parentId);
                secondLinksFooter = driver.findElements(By.xpath("//div[@class='ft-footer__column col-12 col-sm-6'][2]//a"));
            } else {
                String urlToCheck = driver.getCurrentUrl();
                Assert.assertEquals(urlToCheck, secondLinks[i]);
                driver.navigate().back();
                secondLinksFooter = driver.findElements(By.xpath("//div[@class='ft-footer__column col-12 col-sm-6'][2]//a"));
            }

        }
       driver.quit();

    }
}


