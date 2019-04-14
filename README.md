Alt-Building the Project
========================
Database: Already built, no other actions required
Online Application: simple call to the main method without parameter
CheckRegister: simple call to the main method with one parameter, store_id. In our project, we have 4 stores so
enter 1-4.
Admin Application: simple call to the main method without parameter
Vendor Application: simple call to the main method without parameter


## Basic Applications Operations
================================
#### Online Application
+ Create an account: new users can be prompted with questions about their basic personal information and shipping address. The information includes the official name, telephone number, email, and password.
+ Log in: the user is required to enter the email and password they registered with. If the credentials match the data in the database, he/she can enter the store. If not, the user is given the option to try again or create a new account.
+ Buy food: the user can browse through different categories and view the list of products. After selecting an item, a confirmation message appears with the name and the cost of the product. If the user decides to purchase it, he/she will receive a confirmation message.
+ View orders: view previous orders made by the customer. If there is none, a blank screen is displayed.
+ View credit: display the amount of money the user has on his/her account.
+ Delete account: allows the user to delete their account from the database.
+ Sign out: logs out the user and ends the application.

#### CheckRegister Application
+ Create an account: new users can be prompted with questions about their basic personal information and shipping address. The information includes the official name, telephone number, email, and password.
+ Log in: the user is required to enter the email and password they registered with. If the credentials match the data in the database, he/she can enter the store. If not, the user is given the option to try again or create a new account.
+ Check out: the user enters the UPC into the application. If a match is found indicating that the store has the product in stock, a confirmation message will appear.
+ Sign out: logs out the user and ends the application.

#### Admin Application
+ Log in: the user is required to enter a specific password for database managers.
+ Enter SQL command: the user can enter any SQL commands and directly modify the database.
+ Quit: logs out and ends the application.

#### Vendor Application
+ Log in: the user is required to enter the vendor ID and its password.
+ Handle reorder requests: vendor enters what reorder request they want to handle by entering the reorder_id. Then, they enter shipment and delivery_date which would be null before they handle it.
+ View all reorders: display all recent reorders to the vendor from customers.
+ Quit: logs out and ends the application.


