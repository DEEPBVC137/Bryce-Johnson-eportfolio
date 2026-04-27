import csv
import mysql.connector

conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="MySQLTest!",
    database="ecommerce"
)

cursor = conn.cursor()

def import_customers():
    with open("data/olist_customers_dataset.csv", "r", encoding="utf-8") as file:
        reader = csv.DictReader(file)
        for row in reader:
            cursor.execute(
                """
                INSERT IGNORE INTO customers (customer_id, customer_city, customer_state)
                VALUES (%s, %s, %s)
                """,
                (
                    row["customer_id"],
                    row["customer_city"],
                    row["customer_state"]
                )
            )
    conn.commit()
    print("Customers imported")

def import_orders():
    with open("data/olist_orders_dataset.csv", "r", encoding="utf-8") as file:
        reader = csv.DictReader(file)
        for row in reader:
            cursor.execute(
                """
                INSERT IGNORE INTO orders (order_id, customer_id, order_status, order_purchase_timestamp)
                VALUES (%s, %s, %s, %s)
                """,
                (
                    row["order_id"],
                    row["customer_id"],
                    row["order_status"],
                    row["order_purchase_timestamp"]
                )
            )
    conn.commit()
    print("Orders imported")

def import_products():
    with open("data/olist_products_dataset.csv", "r", encoding="utf-8") as file:
        reader = csv.DictReader(file)
        for row in reader:
            cursor.execute(
                """
                INSERT IGNORE INTO products (product_id, product_category_name)
                VALUES (%s, %s)
                """,
                (
                    row["product_id"],
                    row["product_category_name"]
                )
            )
    conn.commit()
    print("Products imported")

def import_order_items():
    with open("data/olist_order_items_dataset.csv", "r", encoding="utf-8") as file:
        reader = csv.DictReader(file)
        for row in reader:
            cursor.execute(
                """
                INSERT IGNORE INTO order_items (order_id, product_id, price)
                VALUES (%s, %s, %s)
                """,
                (
                    row["order_id"],
                    row["product_id"],
                    row["price"]
                )
            )
    conn.commit()
    print("Order items imported")

try:
    import_customers()
    import_orders()
    import_products()
    import_order_items()
    print("All data imported successfully")
except Exception as e:
    print("Error during import")
    print(e)
finally:
    cursor.close()
    conn.close()