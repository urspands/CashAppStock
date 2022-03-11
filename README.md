# CashAppStock
Listing Stocks from an APi

* This app is implemented with MVVM pattern and Hilt DI. Currently this 
is the best design pattern for android.

* I took the liberty to display only four values in the list item ticker, name, price and quantity. 
As quantity is nullable, I display "-" if its null else will display the value

* I used third party libraries like retrofit to make server api calls and Mockito for unit testing. 

* In DataRepositoryImpl.getPortfolioStocks() function, the api call for malformed json and empty response 
are commented. User testing this app can comment and uncomment corresponding apis to test the app.

* I used below reference for unit testing viewModel
https://blog.mindorks.com/unit-testing-viewmodel-with-kotlin-coroutines-and-livedata