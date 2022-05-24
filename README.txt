The following are the curl commands used to test the backend system

Running:
mvnw clean package
mvnw spring-boot:run

All mapping returns a responseEntity that informs if the operation was successful or not
On returning data, data is not formatted to be easily viewed as this would be done on the front end
So instead as well as returning the data, I will also print it to the command line to get a better view of all data
All commands have error detection
This application is to be used alongside the SQL database provided

Product and users used in following tests 
{ProductID:2, Name:SandPaper, Description:Used for sanding, Price:5, Discount:0}
{UserID:432, Firstname:Emma, Secondname:Sleith, AccessLevel:Admin}

Methods

 - Get a list of all the users 
	curl -v -w\\n http://localhost:8080/toolShop/getAllUsers

 - Get a specific user
	curl -v -w\\n http://localhost:8080/toolShop/getUser/432

 - Get list of all the products
	curl -v -w\\n http://localhost:8080/toolShop/getAllProducts

 - Get a specific product 
	curl -v -w\\n http://localhost:8080/toolShop/getProduct/2

 - Add a product to cart
	curl -X PUT http://localhost:8080/toolShop/addCart/432/2

 - View the users cart
	curl -v -w\\n http://localhost:8080/toolShop/viewCart/432

 - Get the price of items in the cart
	curl -v -w\\n http://localhost:8080/toolShop/cartPrice/432

 - Clear the cart
	curl -X DELETE http://localhost:8080/toolShop/clearCart/432

 - Remove specific product from cart
	curl -X DELETE http://localhost:8080/toolShop/removeProductCart/432/2

 - Purchase the cart
	curl -X POST http://localhost:8080/toolShop/purchase/432

 - Get orders
	curl -v -w\\n http://localhost:8080/toolShop/getOrders/432
	curl -v -w\\n http://localhost:8080/toolShop/getOrders/33

 - get news
	curl -v -w\\n http://localhost:8080/toolShop/getNews

 - Update news
	curl -X PUT http://localhost:8080/toolShop/updateNews/432 -H "Content-Type: application/json" -d """New news banner"""


 - add Product (could not figure correct command to pass this object, and so an a different object is created when this is ran)
	curl -X POST http://localhost:8080/toolShop/addProduct/432 -H "Content-Type: application/json" -d "{""productID"":23, ""name"":"""testTool""", ""description"":""Testing posting new product"", ""price"": 100, ""discount"":25}"


 - update a product (same issue as before)
	curl -X PUT http://localhost:8080/toolShop/addProduct/432 -H "Content-Type: application/json" -d "{""productID"":23, ""name"":"""testTool""", ""description"":""Testing posting new product"", ""price"": 100, ""discount"":25}"


 - delete a product from the database
	curl -X DELETE http://localhost:8080/toolShop/deleteProduct/432/2 

BONUS FUNCTIONALITY

 - Filter products by ascending price
	curl -v -w\\n http://localhost:8080/toolShop/filterAsc

 - Filter products by descending price
	curl -v -w\\n http://localhost:8080/toolShop/filterDesc

 - Find product by name
	curl -v -w\\n http://localhost:8080/toolShop/getProductByName/Hammer

