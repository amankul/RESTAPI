package com.org.module.tests;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Customer {

    private static final Logger LOG = LogManager.getLogger();

    @BeforeClass()
    @Parameters({"env","auth"})
    public void preTestSteps(String env,String auth) {

        if ("PROD".equalsIgnoreCase(env)) {
            SERVICE_END_POINT = API_Constants.SERVICE_END_POINT_PROD;
        this.serviceEndPoint = serviceEndPoint;                            -- global so that they can be used for all test methods
        this.auth = auth;

        LOG.debug("REQUEST-URL: GET [{}]", requestUrl);
}

@Test
public void verifyGetAllBrands() {

            // Make request and extract response

            Response response =
                    given().header("Authorization", auth).request().get(requestUrl).then().extract().response();

            int size = response.then().extract().jsonPath().getList("data.brands").size();

            // MySQL DB data capture

            String sqlQuery_name = "select count(*) from brand";
            dbResult = MySQL.query_Post_connection_To_MySQL_Via_JumpServer(sqlQuery_name, serviceEndPoint);
            LOG.debug("DB Result-" + dbResult);
            dbcount = Integer.valueOf(dbResult.get(0));

            // JSON payload and DB match validations

            response.then().statusCode(200);
            response.then().body("data.brands.flatten().any {it.containsKey('name') }", equalTo(true));
            response.then().body("data.brands.flatten().any {it.containsKey('id') }", equalTo(true));
            response.then().body("status", equalTo("success"));
            Assert.assertEquals(dbcount, size, "DB Result does not match size");
        }






        @Test(dataProvider = "usesParameter", priority = 1)
        public void verify_Create_Venue(String name, String CreateVenueRequestBodyPath)
      throws IOException {
            // Request Details
            String requestBody = fileUtils.getJsonTextFromFile(CreateVenueRequestBodyPath);
            JSONObject requestBodyJSONObject = new JSONObject(requestBody);
            requestBodyJSONObject.put("name", name + " " + HelperMethods.getDateAsString());

            log.info("CREATE VENUE");

            // Extracting response after status code validation
            Response response =
                    given()
                            .header("Content-Type", "application/json")
                            .auth()
                            .oauth2(jwt)
                            .body(requestBodyJSONObject.toString())
                            .post(venueURL)
                            .then()
                            .statusCode(200)
                            .extract()
                            .response();

            // printing response
            log.info("RESPONSE:" + response.asString());
            response.then().body("data.any { it.key == 'guid'}", is(true));
            capturedVenueId = response.then().extract().path("data.guid").toString();
            // We are making sure the venue created for update and delete operation overwrites this variable
            // so that it can be reused in future
            Assert.assertNotNull(capturedVenueId);
            venueGuid.add(capturedVenueId);
            log.info("VENUE MAP " + venueGuid);
        }



        @DataProvider(name = "usesParameter")
        public Object[][] getData(ITestContext context) {
            return new Object[][] {
                    {"QA VENUE", context.getCurrentXmlTest().getParameter("CreateVenueRequestBodyPath")},
                    {
                            "QA VENUE FOR UPDATE & DELETE",
                            context.getCurrentXmlTest().getParameter("CreateVenueRequestBodyPath")
                    }
            };
