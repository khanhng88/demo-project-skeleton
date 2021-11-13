package tests.order;

import io.qameta.allure.Description;
import models.components.product.StandardEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.UserData;
import testdata.url.URL;
import testflows.order.computer.BuyingComputerFlow;
import tests.BaseTest;
import utils.data.ComputerTestDataGenerator;

public class BuildStandardComputerTest extends BaseTest {

    @Test(dataProvider = "standardCompsDataSet", description = "Buying a standard computer")
    @Description(value = "Using a set of utils.data with different computer specs and check total price in cart")
    public void testBuildingStandardComputer(ComputerDataObject computerDataObject) {
        WebDriver driver = getDriver();
        BuyingComputerFlow<StandardEssentialComponent> orderingComputerFlow = new BuyingComputerFlow<>(driver);

        // Go to cheap computer item page
        goTo(URL.STANDARD_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(StandardEssentialComponent.class);
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
    public ComputerDataObject[] standardCompsDataSet() {
        ComputerDataObject[] computerTestData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/StandardComputerDataList.json");
        return computerTestData;
    }

}
