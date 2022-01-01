package tests.order;

import io.qameta.allure.Description;
import models.components.product.CheapComputerEssentialComponent;
import models.components.product.StandardEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.url.URL;
import testdata.user.UserData;
import testflows.order.computer.BuyingComputerFlow;
import tests.BaseTest;
import utils.data.CommonData;
import utils.data.ComputerTestDataGenerator;

import java.security.SecureRandom;
import java.util.Random;

public class BuildMultipleTypeComputerTest extends BaseTest {


    @Test(description = "Buying a standard and cheap computer")
    @Description(value = "build 2 computers with different types")
    public void testBuildingMultipleTypeComputer() {

        //get randomly 2 types of computer data
        ComputerDataObject[] standardComputerTestData = ComputerTestDataGenerator
                .getTestDataFrom("src/test/java/testdata/purchasing/StandardComputerDataList.json");
        ComputerDataObject standardComp = standardComputerTestData[new SecureRandom().nextInt(standardComputerTestData.length)];

        ComputerDataObject[] cheapComputerTestData = ComputerTestDataGenerator
                .getTestDataFrom("src/test/java/testdata/purchasing/CheapComputerDataList.json");
        ComputerDataObject cheapComp = cheapComputerTestData[new SecureRandom().nextInt(standardComputerTestData.length)];


        WebDriver driver = getDriver();
        BuyingComputerFlow orderingComputerFlow = new BuyingComputerFlow(driver);

        // Go to cheap computer item page
        goTo(URL.CHEAP_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        orderingComputerFlow.buildComputer(cheapComp);

        //go to standard computer item page
        goTo(URL.STANDARD_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(StandardEssentialComponent.class);
        orderingComputerFlow.buildComputer(standardComp);

        // Go to Shopping cart Page
        goTo(URL.CART);

        orderingComputerFlow.verifyComputerAdded(standardComp);
//
//        //checkout
//        UserData userData = CommonData
//                .buildUserData("src/test/java/testdata/user/DefaultCheckOutUserData.json");
//        orderingComputerFlow.goToCheckOutOptions(userData, computerDataObject);
//
//        orderingComputerFlow.completeCheckOut();
    }
}
