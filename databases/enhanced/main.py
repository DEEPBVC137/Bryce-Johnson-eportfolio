import mysql.connector

DB_HOST = "localhost"
DB_USER = "root"
DB_PASSWORD = "MySQLTest!"
DB_NAME = "ecommerce"


def connect_db():
    return mysql.connector.connect(
        host=DB_HOST,
        user=DB_USER,
        password=DB_PASSWORD,
        database=DB_NAME
    )


def pause():
    input("\nPress Enter to return to the menu...")


def print_header(title):
    print("\n" + "=" * 90)
    print(title)
    print("=" * 90)


def show_customers(limit=10):
    conn = connect_db()
    cursor = conn.cursor()

    query = """
    SELECT customer_id, customer_city, customer_state
    FROM customers
    LIMIT %s
    """
    cursor.execute(query, (limit,))
    rows = cursor.fetchall()

    print_header("Sample Customers")
    for customer_id, city, state in rows:
        print(f"ID: {customer_id} | City: {city} | State: {state}")

    cursor.close()
    conn.close()
    pause()


def show_orders(limit=10):
    conn = connect_db()
    cursor = conn.cursor()

    query = """
    SELECT order_id, customer_id, order_status, order_purchase_timestamp
    FROM orders
    LIMIT %s
    """
    cursor.execute(query, (limit,))
    rows = cursor.fetchall()

    print_header("Sample Orders")
    for order_id, customer_id, status, purchase_time in rows:
        print(
            f"Order ID: {order_id} | Customer ID: {customer_id} | "
            f"Status: {status} | Purchased: {purchase_time}"
        )

    cursor.close()
    conn.close()
    pause()


def top_products(limit=10):
    conn = connect_db()
    cursor = conn.cursor()

    query = """
    SELECT p.product_id, p.product_category_name, COUNT(*) AS total_sales
    FROM order_items oi
    JOIN products p ON oi.product_id = p.product_id
    GROUP BY p.product_id, p.product_category_name
    ORDER BY total_sales DESC
    LIMIT %s
    """
    cursor.execute(query, (limit,))
    rows = cursor.fetchall()

    print_header("Top Products by Sales Count")
    for product_id, category, total_sales in rows:
        category_display = category if category else "Unknown"
        print(
            f"Product ID: {product_id} | Category: {category_display} | "
            f"Sales Count: {total_sales}"
        )

    cursor.close()
    conn.close()
    pause()


def orders_by_state():
    conn = connect_db()
    cursor = conn.cursor()

    query = """
    SELECT c.customer_state, COUNT(o.order_id) AS total_orders
    FROM orders o
    JOIN customers c ON o.customer_id = c.customer_id
    GROUP BY c.customer_state
    ORDER BY total_orders DESC
    """
    cursor.execute(query)
    rows = cursor.fetchall()

    print_header("Orders by State")
    for state, total_orders in rows:
        print(f"State: {state} | Total Orders: {total_orders}")

    cursor.close()
    conn.close()
    pause()


def orders_by_status():
    conn = connect_db()
    cursor = conn.cursor()

    query = """
    SELECT order_status, COUNT(*) AS total_orders
    FROM orders
    GROUP BY order_status
    ORDER BY total_orders DESC
    """
    cursor.execute(query)
    rows = cursor.fetchall()

    print_header("Orders by Status")
    for status, total_orders in rows:
        print(f"Status: {status} | Total Orders: {total_orders}")

    cursor.close()
    conn.close()
    pause()


def total_customer_count():
    conn = connect_db()
    cursor = conn.cursor()

    query = """
    SELECT COUNT(*) AS total_customers
    FROM customers
    """
    cursor.execute(query)
    total_customers = cursor.fetchone()[0]

    print_header("Total Customer Count")
    print(f"Total Customers: {total_customers}")

    cursor.close()
    conn.close()
    pause()


def average_product_price():
    conn = connect_db()
    cursor = conn.cursor()

    query = """
    SELECT AVG(price) AS average_price
    FROM order_items
    """
    cursor.execute(query)
    average_price = cursor.fetchone()[0]

    print_header("Average Product Price")
    if average_price is not None:
        print(f"Average Price: ${average_price:.2f}")
    else:
        print("No price data available.")

    cursor.close()
    conn.close()
    pause()


def main_menu():
    while True:
        print("\n" + "=" * 90)
        print("Ecommerce Database Menu")
        print("=" * 90)
        print("1. View sample customers")
        print("2. View sample orders")
        print("3. View top products by sales count")
        print("4. View orders by state")
        print("5. View orders by status")
        print("6. View total customer count")
        print("7. View average product price")
        print("8. Exit")

        choice = input("Enter your choice: ").strip()

        if choice == "1":
            show_customers()
        elif choice == "2":
            show_orders()
        elif choice == "3":
            top_products()
        elif choice == "4":
            orders_by_state()
        elif choice == "5":
            orders_by_status()
        elif choice == "6":
            total_customer_count()
        elif choice == "7":
            average_product_price()
        elif choice == "8":
            print("Exiting program.")
            break
        else:
            print("Invalid choice. Please enter a number from 1 to 8.")


if __name__ == "__main__":
    main_menu()