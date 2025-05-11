# API-Automation-by-REST-Assured-TestNg
## 📌 Project Overview

This project is focused on testing and automating the RESTful API endpoints of the [**Daily Finance**](https://dailyfinance.roadtocareer.net/) web application.

The automation is implemented using **Rest Assured** in Java, following the **Page Object Model (POM)** architecture. This ensures separation of concerns and enhances maintainability and scalability of the test framework.

---

## ✅ Features Covered in Postman Collection

Using browser Developer Tools → Network tab, the following API interactions were captured and added to the Postman collection: [Link here](https://www.postman.com/satellite-operator-81623342/workspace/my-practice-workspace/collection/39262502-713d7334-ab63-4072-b543-61f3508a63a5?action=share&creator=39262502)

1. **User Registration**
2. **Admin Login**
3. **Fetch User List**
4. **Search User by ID**
5. **Edit User Info** (e.g., first name, phone number)
6. **User Login**
7. **Fetch Item List**
8. **Add Item**
9. **Edit Item Name**
10. **Delete Item**

---

## 🧪 Test Reports

All tests are integrated with **Allure Reporting**.

📸 **Allure Report Screenshot:**


---

## 🔗 Important Links

- 📘 **Postman Collection Documentation:**  
  [View Collection](https://www.postman.com/satellite-operator-81623342/workspace/my-practice-workspace/collection/39262502-713d7334-ab63-4072-b543-61f3508a63a5?action=share&creator=39262502)

- 🧾 **Test Case Documentation:**  
  [Test Cases](https://docs.google.com/spreadsheets/d/1iT5hX-jTAE9xrogx9-cdLNqrqxwT9azWQbVk0egwAqw/edit?usp=sharing)

---
The captured APIs were automated using **Rest Assured** in Java. The project includes:

- Positive and negative test cases for each API endpoint
- Request/response validation
- Status code and payload assertions
- Data-driven approach where applicable
- Utility methods and reusable components
- --

## 🛠 Technologies Used

- Java
- Rest Assured
- TestNG / JUnit
- Maven
- Allure Reports
- Postman
- Git & GitHub

---
## ▶️ How to Run This Project

### 🛠 Requirements
- Java 8+
- Maven
- Git
- Allure CLI
- Internet access

---

### 🧪 Steps to Run

```bash
# 1. Clone the repository
git clone https://github.com/your-username/daily-finance-api-automation.git
cd daily-finance-api-automation

# 2. Build the project
mvn clean install

# 3. (Optional) Update config.properties if needed (e.g., base URL, credentials)

# 4. Run all tests
mvn test

# To run a specific test class
mvn -Dtest=ClassName test

# 5. Generate and view Allure report
allure serve target/allure-results
```
## 👨‍💻 Author

**Faisal Kabir**  
- **LinkedIn:** [Faisal Kabir](https://www.linkedin.com/in/faisal-kabir1/)
- **GitHub:** [faisalkabir1](https://github.com/faisalkabir1)
