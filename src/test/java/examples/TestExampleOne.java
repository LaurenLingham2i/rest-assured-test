package examples;

import com.tngtech.java.junit.dataprovider.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(DataProviderRunner.class)
public class TestExampleOne {

    @DataProvider
    public static Object[][] postCodesAndPlaces() {
        return new Object[][] {
                { "gb", "EH54", "Livingston Village" },
                { "gb", "AB12", "Aberdeen" },
                { "ca", "B2R", "Waverley"}
        };
    }

    @Test
    @UseDataProvider("postCodesAndPlaces")
    public void requestPostCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String postCode, String expectedPlaceName) {

        given().
                pathParam("countryCode", countryCode).pathParam("postCode", postCode).
                when().
                get("http://zippopotam.us/{countryCode}/{postCode}").
                then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }

    @Test
    public void requestUkPostCodeEH54_checkPlaceNameInResponseBody_expectLivingstonVillage() {

        given().
                when().
                get("http://zippopotam.us/gb/EH54").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Livingston Village"));
    }

    @Test
    public void requestUkPostCodeAB12_checkPlaceNameInResponseBody_expectAberdeen() {

        given().
                when().
                get("http://zippopotam.us/gb/AB12").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Aberdeen"));
    }

    @Test
    public void requestCaZipCodeB2R_checkPlaceNameInResponseBody_expectWaverley() {

        given().
                when().
                get("http://zippopotam.us/ca/B2R").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Waverley"));
    }
}