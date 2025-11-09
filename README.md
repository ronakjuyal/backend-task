# 🏎️ HotWheels Backend Task — Dealer & Vehicle Management API
},
"message": "Vehicle created successfully",
"timestamp": 1699999999999
}
```


**Error**
```json
{
"success": false,
"data": null,
"message": "Dealer not found",
"timestamp": 1699999999999
}
```


---


## 🧪 Testing
You can use:
- **Swagger UI** for live testing
- **Postman** (import endpoints manually)
- Or `curl`:
```bash
curl -X POST http://localhost:8080/api/dealers \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <token>" \
-d '{"name":"SuperCars","email":"scars@test.com","subscriptionType":"PREMIUM"}'
```


---


## 🧱 Build for production
```bash
mvn clean package -DskipTests
java -jar target/backendtask-0.0.1-SNAPSHOT.jar
```


---


## 🐳 Docker (Optional)
```bash
docker build -t hotwheels-backend .
docker run -p 8080:8080 hotwheels-backend
```


---


## 👨‍💻 Default Credentials
| Username | Password | Role |
|-----------|-----------|------|
| admin | password | ADMIN |


---


## 📄 License
This project is for demonstration and educational purposes.