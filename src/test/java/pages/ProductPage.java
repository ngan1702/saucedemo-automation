package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {
    private WebDriver driver;
    private By inventoryList = By.className("inventory_list");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductListVisible() {
        return driver.findElement(inventoryList).isDisplayed();
    }
}
