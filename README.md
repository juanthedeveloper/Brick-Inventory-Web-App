# Brick Inventory Web App
An inventory management system for a client.
This application consist of a web application hosted on AWS S3, an AWS RDS MySQL database for inventory storage, with an AWS Elastic Beanstalk instance to host the Java API.
The web application consist of client side Javascript code to display information about the current status of each product. One of the major requirements of this project was 
to be able to use the application with only a barcode scanner. The client wanted the ability to scan barcodes to bring up that item, then scan barcodes that added or removed a 
certain amount from the quantity and repeat with minimal keyboard and mouse interactions. The application also contains other features such as a cron email alert system for when inventory gets too low.<br><br>

Main screen

<img src=https://user-images.githubusercontent.com/86272014/209044677-5397c9c3-e255-477f-b8e0-c8d0590e7041.png width=800>

Individual item adjustment screen

<img src=https://user-images.githubusercontent.com/86272014/209044704-ffbfcd28-c90c-45cc-bbd0-1e5564a1a00c.png width=800px>
