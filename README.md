This the README file explaining what I did in the project, the design decisions and oop implementation.

# **Project Structure:**
I tried to keep the project as organized as possible, so I split it into different packages.
src Folder: it has the main class and this README file.
inside it there are 3 Packages:
DataBase Package: it has the DataBase loader class and all the .properties files that store all the data. (Login Database,Order History database,product database, promotion code database, shopping cart database)
I will later explain why I chose a .properties file as storage.
Entities Package: it contains all the classes of the entities I used in the project.(User,Product, Shopping Cart ect...)
Controllers Package: it has the general classes that deal with a certain functionality and the menus that the controllers that are common for both customer and admin.
Inside it there are two packages that contain specific controllers for Admin and Customer.

## **Why the .properties files?**
I chose the .properties files as it stores key pair values and provides constant time access to the wanted data by its ID(key).
It is similar to a hashmap when it comes to constant time access, but it is stored in a file to keep the data.
I found it is a proper way to simulate a database.
Example of storage: I stored the products by their barcode which is the ID of the products in the case (the key). The value of the key is a string that has all the attributes of the product seperated by comas.
To access the Database I just use the builtin getProperty method that returns the string value that I then split to get the wanted attributes of the product.

## *_Storage in a .properties file (database):_*
I stored the product in this format: "Barcode"(key)="Category(clothing or electronics),name,price,quantity,type,brand or size(depending on category)"
Stored login info in this format: "UserID"="password,role"
Promotion code in this format: "PromoCode"="percentage of reduction"
Order history in this format: "OrderID"="barcode,name,quantity"
Shopping cart info in this format: "Barcode"(key)="Category(clothing or electronics),name,price,quantity,type,brand or size(depending on category)"


## *_OOP implementation:_*
I tried to keep the project as modular as possible and to separate the different functions on different classes so that every class handles a different function.
I also tried to keep the menus seperated because every menu handles a different scenario.
I seperated the classes on different packages as admins and customers have different controllers and functionalities.
I used abstract classes,inheritance and interfaces to implement some functionalities when polymorphism was needed.
I did my some research on design patterns and I tried to implement some of them.
I used the Strategy design pattern when it comes to choosing the payment method of the customer.
I used the singleton design pattern when loading the database and when only a single instance of a class was needed.

Design Decisions:
User auth:
I made an authentication class that handles login,logout and interacts with the database
I made two roles: customer and admin.
Depending on the logged in credentials a welcome page shows up and gives access to certain functionalities.

Product Management: Functionalities handled by the ProductControllerCustomer and ProductControllerAdmin classes.
Abstract product class that has abstract getCategory and showProduct methods extended by electronics product and clothing product.
Admin can create,read,update delete items by interacting with the product database.
Customer can only read and select the items by interacting with the product database.

Shopping Cart: Functionalities handled by the ShoppingCartController class.
After user selects a product he is redirected to the selected product menu where he can view product details or add product to shopping cart.
When Adding an item to the shopping cart if the product already exists in the shopping cart its quantity will increase else the product will be added normally.
User can view his shopping cart. All the items in the shopping cart will be displayed and user can select them to delete it, update it or order it.
These operations are implemented by interacting with the shopping cart database(adding or deleting item from shopping cart)

Order Processing:
After the customer accesses the shopping cart and decides to order the item, he is redirected to order item menu.
Customer can choose to add promotion code(later explained) or to proceed to payment.
I maintained records of user transactions by storing the order in the Order database after order is validated.
There is an Order class that has a showOrderHistory method that outputs the order history of the client if he chooses to view it.

Inventory Management:
I included scenarios when customer wants to order more than available in the product inventory in this case he either chooses to cancel the order or to only order the available quantity.
If the order is passed and payment is done, the quantity ordered is subtracted from the quantity in the product inventory. This is done by modifying the database.
When the items in the inventory are out of stock they won't be displayed for the user.
When inventory is empty, no items will be displayed for customer.
If the item is out of stock are low on stock a notification shows up on the welcome page of the admin when he logs in, displaying the items that are low or out of stock. This is handled by the NotificationController class in the admin package.

Dynamic Product Search and filtering:
This is implemented in the SearchAndFiltering Class, the user can choose between three options:
Search by name(keyword), filter by category and filter by price range.
This is done by reading all items from database storing them in a productList and then outputting the resulting products according to the search option or filtering option.

Payment processing:
I implemented the strategy design pattern for this functionality.
Created a payment methods interface that has collectPaymentDetails and pay method.
This interface is implemented by the two payment options:
Payment by PayPal: the user types in email and username and pays
Payment by credit card: the user types in the credit card info and pays

Optional features:

First feature: Promotion code: This is handled by the PromotionCodeController class.
After selecting the item and ordering it the user can choose to add a promotion code.
The promotion code has to be available in the promotion code database for it to work.
After applying the promotion code successfully, the price per unit of the product he selected will be reduced by the reduction percentage of that promotion code.

Second feature: Shipping Options: This is handled by the abstract ShippingMethods class that is extended by ShippingStandard and ShippingExpress classes
After selecting the order and typing in the payment details, the user can choose between two options: Shipping Standard and Shipping express.
The difference between the two is the shipment delay.
Express is faster(2days) while standard takes 5 days. User has to type in his address and name.
After choosing the shipping option the order is then validated and customer is redirected to product section.
