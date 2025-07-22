package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;
import utils.ExcelUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class LoginTest extends BaseTest {

    private final String dataFilePath = "src/test/resources/data/LoginTestData.xlsx";

    @Test(dataProvider = "loginData")
    public void testLoginFromExcel(String testCaseID, String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        String actualResult;
        if (expectedResult.equalsIgnoreCase("Success")) {
            ProductPage productPage = new ProductPage(driver);
            boolean visible = productPage.isProductListVisible();
            actualResult = visible ? "Pass" : "Fail";
            Assert.assertTrue(visible, "Expected successful login but failed.");
        } else {
            boolean failed = driver.getCurrentUrl().contains("saucedemo.com");
            actualResult = failed ? "Pass" : "Fail";
            Assert.assertTrue(failed, "Expected failure but login passed.");
        }

        // Ghi kết quả thực vào file Excel
        ExcelUtils.writeTestResult(dataFilePath, "LoginTestData", testCaseID, actualResult);
    }

    @DataProvider(name = "loginData")
    public Iterator<Object[]> loginData() {
        List<String[]> rawData = ExcelUtils.readTestData("LoginTestData.xlsx", "LoginTestData");
        List<Object[]> data = new java.util.ArrayList<>();
        for (String[] row : rawData) {
            data.add(new Object[]{row[0], row[1], row[2], row[3]});
        }
        return data.iterator();
    }
}
