from faker import Faker
import random
import mysql.connector

fake = Faker()

# Connect to MySQL
conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="root",   # change if your password is different
    database="salary_management_system"
)
cur = conn.cursor()

for i in range(10000):
    # Generate employee data
    employee_code = f"EMP{i+1:05d}"  # e.g., EMP00001
    first_name = fake.first_name()
    last_name = fake.last_name()
    email = f"{first_name.lower()}.{last_name.lower()}{i}@example.com"
    department = random.choice(["HR", "Engineering", "Sales", "Finance"])
    designation = random.choice(["Manager", "Developer", "Analyst", "Executive"])
    country = random.choice(["India", "USA", "UK", "Germany"])
    joining_date = fake.date_between(start_date="-10y", end_date="today")
    status = "ACTIVE"

    # Insert employee
    cur.execute("""
        INSERT INTO employee (employee_code, first_name, last_name, email, department, designation, country, joining_date, status, created_at)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, NOW())
    """, (employee_code, first_name, last_name, email, department, designation, country, joining_date, status))

    emp_id = cur.lastrowid

    # Generate salary data
    base_salary = round(random.uniform(30000, 200000), 2)
    bonus = round(random.uniform(0, 20000), 2)
    currency = random.choice(["INR", "USD", "EUR"])
    effective_from = fake.date_between(start_date="-5y", end_date="today")

    # Insert salary
    cur.execute("""
        INSERT INTO salary (employee_id, base_salary, bonus, currency, effective_from, created_at, created_by)
        VALUES (%s, %s, %s, %s, %s, NOW(), 'SYSTEM')
    """, (emp_id, base_salary, bonus, currency, effective_from))

# Commit changes
conn.commit()
cur.close()
conn.close()

print("✅ Successfully inserted 10,000 employees with salaries")
