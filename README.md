
# gildedroseexpand

Gilded Rose Expansion Application
------------------------------------------------------------------------------------------------------

The Application Supports following REST ENDPOINTS
------------------------------------------------------------------------------------------------------
Method   URL                                       Description
                                         
------------------------------------------------------------------------------------------------------
GET:      /public/items                           :Provides the list of items in the inventory.
                                                   Does not require user authentication.

GET:      /public/items/{itemname}                :Provides the details of an item with the itemname
                                                   given in the path variable of the url if it
                                                   exists in in the inventory.
                                                   Does not require user authentication.

GET:      /item/buy                               :Provides a form that can be used to submit a
                                                   request to buy an item.
                                                   Requires user authentication.

POST:     /item/buy                               :Handles the request posted by buy form.
                                                   Requires user authentication.

GET:      /login                                  :Provides login form that can be used
                                                   to request for authentication.

POST:     /login                                  :Handles authentication request posted by
                                                   by login form.

POST:     /logout                                 :Handles logout request.

----------------------------------------------------------------------------------------------------

Prerequisite to Build and Run the Application
----------------------------------------------------------------------------------------------------
1.  Java Runtime. The code is developed in Java 8 and in Eclipse J2EE IDE
2.  Maven build tools. The code has been tested with version 3.5.4
3.  Tomcat container. The code has been tested with version 9.0.10
4.  Database named 'gildedroseexpansion' and a role named 'gildedroseexpansion' in PostgreSQL.
    PostgreSQL version 10.4 has been tested on its default port 5432.
    Password 'password' has been used for 'gildedroseexpansion' role.
    RDBMS, Port, Database, Role, and Password can be changed by changing corresponding
    values in Spring application.properties file before the deployment.
    Database schema will be automatically created by the application.
5.  Application has been developed using Spring Boot and Hibernate. Maven build
    tool will automatically manage these dependencies.

---------------------------------------------------------------------------------------------------
Instruction to Build and Run the Application
---------------------------------------------------------------------------------------------------
1.  Open the command prompt.
2.  Use change directory command and go to the Application directory.
3.  Type 'mvn clean install' in the command prompt to build the Application.
4.  Type 'mvn spring-boot:run' in the commnd prompt to run the Application.

---------------------------------------------------------------------------------------------------
Instruction on how to use the Application
---------------------------------------------------------------------------------------------------
1.  Open a browser.
2.  Type 'http://localhost:8080/public/items' at the address bar of your browser.
    You will get a json array with all the items in the inventory. The Application
    will provide you the item list without asking for any authentication.
3.  Type 'http://localhost:8080/public/items/NotebookComputer' at the address bar of your
    browser. The Application will provide the details of this item including its price.
4.  Type 'http://localhost:8080/item/buy' at the address bar of your browser. It will ask
    for your credentials (username and password) to authenticate. Type 'user' for the
    username and 'passw0rd' for the password. The Application will authenticate you
    and provide the buy form. Type itemname 'NotebookComputer' in the form and click on
    'Buy'. You will get 'Purchased Successfully' message.
5.  Type 'http://localhost:8080/item/buy' at the address bar of your browser again.
    The Application will provide you the buy form since you are already authenticated.
    Type itemname 'NotebookComputers' in the form and click on 'Buy'.
    You will get 'Purchase Failed' message since the item is not available in the inventory.
    You will also get 'Purchase Failed' message if item's stock is empty in the inventory.

-------------------------------------------------------------------------------------------------
Unit and Integration Tests
-------------------------------------------------------------------------------------------------
1.  The Application has unit tests for every method that has implemented some sort of business
    logic or has some sort of computation.
    Trivial methods however do not have unit test as it is not necessary and not worthy.
2.  The Application has enough integration tests to cover all the aspects of its business logics.
3.  All the unit and integration tests will run and pass during build. This has been ensured
    during the development. If any test fails during the build, the Application will not run
    properly.


------------------------------------------------------------------------------------------------
Surge Price Modeling
------------------------------------------------------------------------------------------------
PriceService class in the Application is a Spring Service which has the implementation of Surge
pricing business logic. It maintains a map object in the memory to keep track of item views.
In the map object items are inserted as the key and its view count as the value against the key.
When an item details is viewed using single item view rest end-point, the map is checked whether
this item has been viewed before or not. If the item was viewed before, the map will have an entry
with the item as the key and the number of views in the past as the value. In this case, the
view count is incremented and put back into the map. If the view count of the item exceeds the
view threshold of surge price, item price is surged using surge percentage. If the view count is
less than the threshold, item price remains unchanged. This price change only happens in the
memory, i.e., not persisted. The item with price surged is then returned to the client in the
response. If the item is missing in the map, it is inserted into the map with the visit count
value 1. The map entries are refreshed or cleared at the beginning of each surge interval, which
is 1 hour as per project requirement. This ensures that the visit counts of the items are the
accumulation of the counts of the last hour. Spring @Scheduled annotation is used to schedule
this task.  


------------------------------------------------------------------------------------------------
Request and Response Data Format
------------------------------------------------------------------------------------------------
1.  Rest end-point '/public/items' does not have any data or parameter for the request.
    It's response data format is a json array that contains all the items in the inventory
    as the Json object. Json data format has been chosen since it is simpler than xml and it
    is easy to load and process in JavaScript.
2.  Rest end-point '/public/items/{itemname}' uses path variable instead of request parameter.
    Path variable is preferred over request parameter when it identifies particular resource
    in the application. In this case, {itemname} path variable identifies a specific item
    whose details are sought by using the end-point. Its response uses Json data format.
3.  All the post requests use request parameter since the request parameters are easy to submit
    through forms.


------------------------------------------------------------------------------------------------
Authentication Mechanism
------------------------------------------------------------------------------------------------
Login authentication mechanism has been used in the application. Whenever a user authentication
is required the application prompt the user to type username and password. The user is
authenticated successfully if the submitted username and password are matched with the username
and password stored in the application. User's passwords are encoded before storing in the
application to avoid password theft from the application. Login authentication mechanism is
comparatively easy and less resource incentive to implement and strong enough to give a certain
level of security to the application as well as to the users. For these reasons, I choose to use
it in this application.    
