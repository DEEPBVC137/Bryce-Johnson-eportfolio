CS 499 Database Enhancement

Project Overview
This project enhances a previous database artifact by implementing a relational MySQL database using a real-world public ecommerce dataset and a Python-based menu application for querying and analyzing the data. The enhancement demonstrates database design, data ingestion, and programmatic interaction with a relational database system.

Dataset Source
This project uses the Olist Brazilian E-Commerce Public Dataset, which is publicly available on Kaggle:

Olist. (2018). Brazilian E-Commerce Public Dataset. Kaggle.
https://www.kaggle.com/datasets/olistbr/brazilian-ecommerce

The dataset includes information on customers, orders, products, and order items, which were used to populate the relational database.

Files Included
main.py
import_data.py
README.txt
data folder containing CSV files used for import

Database Features
Relational schema with the following tables:
- customers
- orders
- products
- order_items

Primary key and foreign key relationships between tables
Data import from CSV files into MySQL using Python
Interactive Python menu application for querying and analyzing data

How to Run
1. Ensure MySQL Server is installed and running.
2. Create the ecommerce database in MySQL.
3. Update database credentials in main.py and import_data.py.
4. Run python import_data.py to load the dataset into MySQL.
5. Run python main.py to launch the menu-driven application.

Query Features
View sample customers
View sample orders
View top products by sales count
View orders by state
View orders by status
View total customer count
View average product price

Summary
This enhancement transforms a basic database concept into a fully functional application that integrates real-world data, relational database design, and interactive querying through Python.