**REST API RESPONSE ASSERTIONS**

1. _`Verify a field in response`_
****
{
   "guid": "2c2db3b4-a576-4c19-8249-77fe3ac4338a",
   "version":10,
    "data": {
        "message": "Accepted"
    }
}

response.then().statusCode(200);

response.then().body("guid", startsWith("2c2db3b4"));

response.then().body("data.message", equalTo("Accepted"));  
OR  response.then().body("message", is("Unauthorized token"));
 
 
2. _`Check for presence of key or if the key is not null`_
 

response.then().body("guid", is(notNullValue()));

response.then().body("version",  is(greaterThan(9))));

response.then().body("data.any { it.key == 'guid'}", is(true));
    
    
    
3. _`Dealing with arrays`_    
    
    
    
    {
        "totalCount": 17,
        "resultCount": 17,
        "offset": 0,    
        "data": {
               "brands": [
                   {
                       "name": ".mobi",
                       "id": 1
                   },
                   {
                       "name": "Acer",
                       "id": 2
                   },
                   {
                       "name": "Ahong",
                       "id": 3
                   },
                   {
                       "name": "Airness",
                       "id": 4
            
            
            
            
            
            
response.then().body("data.brands.size", is(resultcount)); 

response.then().body("data.brands.flatten().any {it.containsKey('id') }", is(true));
            
            
4. _`Separate assertions`_
            
capturedVenueId = response.then().extract().path("data.guid").toString();
Assert.assertEquals(capturedVenueId, name, "does not match "); 

similarly assertnotequals, assertNotNull etc
