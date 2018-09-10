## AxCrawler Library 
Hello to everyone! This is a library that allow the developers to include this functionality in the project .
### JAVA
The first version of this library is written in java, that born to be easy and fast in the integration of your projects. In this repository there are the source code and the .jar file.

#### Library Class

The main class is named **AxCrawler**.
This library allow you to call this methods:
- The **scan** method (below the usage), accept only one parameter, as **String**, that represent the url  link of the website, notice: this is only for http protocol.
```
myCrawler.scan("http://xxx.com");
```
- The **scanHttps** method is more similar to the `scan` method, but you can pass a string url that use https. It's called with the same signature of the above `scan` method.


- The **getWebLinks** method allow you to obtain the ArrayList of web links contained inside the website that you are requested after a **scan** operation. Typical use:
 ```
 ArrayList<String> webLinks = myCrawler.getWebLinks();
 ```
- The **getFileLinks** method is the same as **getWebLinks**. It returns an ArrayList of String that contain the found file links inside the scanned url.
  
- The **printWebLinks** method that print all of the found web links inside the standard out stream. Typical use:
 ```
  myCrawler.printWebLinks();
 ```
- The **printFileLinks** method is the same as **printWebLinks**. It shows in the standard output stream all file link found after the **scan**.




