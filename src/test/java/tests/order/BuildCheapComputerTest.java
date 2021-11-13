package tests.order;

import io.qameta.allure.Description;
import models.components.product.CheapComputerEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.UserData;
import testdata.url.URL;
import testflows.order.computer.BuyingComputerFlow;
import tests.BaseTest;
import utils.data.ComputerTestDataGenerator;

public class BuildCheapComputerTest extends BaseTest {

    @Test(dataProvider = "cheapCompsDataSet", description = "Buying a cheap computer")
    @Description(value = "Using a set of utils.data with different computer specs and check total price in cart")
    public void testBuildingCheapComputer(ComputerDataObject computerDataObject) {
        WebDriver driver = getDriver();
        BuyingComputerFlow<CheapComputerEssentialComponent> orderingComputerFlow = new BuyingComputerFlow<>(driver);

        // Go to cheap computer item page
        goTo(URL.CHEAP_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        orderingComputerFlow.buildComputer(computerDataObject);

        // Go to Shopping cart Page
        goTo(URL.CART);
        orderingComputerFlow.verifyComputerAdded(computerDataObject);

        //checkout
        UserData userData = new UserData();
        userData.setFirstName("Khanh");
        userData.setLastName("Nguyen");
        userData.setEmail("khanh@mail.com");
        userData.setCompany("ABC");
        userData.setAddress1("Phan Xich Long");
        userData.setCountry("Viet Nam");
        userData.setState("Other (Non US)");
        userData.setCity("Ho Chi Minh");
        userData.setZip("70000");
        userData.setPhone("0911111111");

        orderingComputerFlow.goToCheckOutOptions(userData, computerDataObject);

        orderingComputerFlow.completeCheckOut();

    }

    @DataProvider()
    public ComputerDataObject[] cheapCompsDataSet() {
        ComputerDataObject[] computerTestData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/CheapComputerDataList.json");
        return computerTestData;
    }

}
