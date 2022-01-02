package tests.order;

import io.qameta.allure.Description;
import models.components.product.CheapComputerEssentialComponent;
import models.components.product.StandardEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import testdata.purchasing.computer.ComputerDataObject;
import testdata.purchasing.computer.ComputerType;
import testdata.url.URL;
import testdata.user.UserData;
import testflows.order.computer.BuyingComputerFlow;
import testflows.order.computer.BuyingComputerFlowEx;
import tests.BaseTest;
import utils.data.CommonData;
import utils.data.ComputerTestDataGenerator;

import java.security.SecureRandom;

public class BuildMultipleTypeComputerTest extends BaseTest {


    @Test(description = "Buying a standard and cheap computer")
    @Description(value = "build 2 computers with different types")
    public void testBuildingMultipleTypeComputer() {

        //get randomly 2 types of computer data
        ComputerDataObject[] standardComputerTestData = ComputerTestDataGenerator
                .getTestDataFrom("src/test/java/testdata/purchasing/computer/StandardComputerDataList.json");
        ComputerDataObject standardComp = standardComputerTestData[new SecureRandom().nextInt(standardComputerTestData.length)];

        ComputerDataObject[] cheapComputerTestData = ComputerTestDataGenerator
                .getTestDataFrom("src/test/java/testdata/purchasing/computer/CheapComputerDataList.json");
        ComputerDataObject cheapComp = cheapComputerTestData[new SecureRandom().nextInt(cheapComputerTestData.length)];


        WebDriver driver = getDriver();
        BuyingComputerFlowEx orderingComputerFlow = new BuyingComputerFlowEx(driver);

        // Go to cheap computer item page
        goTo(URL.CHEAP_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        int cheapCompQuantity = 2;
        orderingComputerFlow.buildComputer(ComputerType.CHEAP_COMPUTER, cheapComp, cheapCompQuantity);

        //go to standard computer item page
        goTo(URL.STANDARD_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(StandardEssentialComponent.class);
        int standardCompQuantity = 4;
        orderingComputerFlow.buildComputer(ComputerType.STANDARD_COMPUTER, standardComp, standardCompQuantity);

        // Go to Shopping cart Page
        goTo(URL.CART);
        orderingComputerFlow.verifyComputerAdded();
        UserData userData = CommonData.buildUserData("src/test/java/testdata/user/DefaultCheckOutUserData.json");

    }
}
