# OnlineStore - Comprehensive E-commerce Project

OnlineStore is a complete e-commerce solution crafted with Spring Boot, MySQL, and React.js. It facilitates various server-side operations including shopping cart management and more. Security is ensured through JWT authentication and Spring Security.

## Technology Stack

- Backend Framework: Spring Boot
- Frontend Framework: React.js
- Database: Postgresql

## Requirements

To run this project locally, ensure the following software is installed:

- JDK 22
- Node.js
- Postgresql
- Git

## Features

- JWT-based user authentication and authorization
- Product browsing capabilities
- Efficient shopping cart handling
- Seamless order placement
- Integration with Stripe for payments
- Api docs using Swagger UI

## Getting Started

1. Clone the repository:

   ```shell
   git clone https://github.com/pyroblazer/project-java-springboot-reactjs.git
   cd project-java-springboot-reactjs
   ```

2. Set up the database:

   - Create a MySQL database and configure connection details in `Server/src/main/resources/application.properties`.

3. Configuring Application Properties

   - Open `Server/src/main/resources/application.properties`.

   - Update MySQL connection settings:

     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/onlinestore
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     spring.jpa.hibernate.ddl-auto=update
     server.port=9090
     spring.jpa.show-sql=true
     stripe.key.secret=your-stripe-secret-key
     springdoc.api-docs.path=/api-docs
     myapp.key=your-custom-online-store-app-key
     ```

     Replace `your-username` and `your-password` with your MySQL credentials.

     Replace `your-stripe-secret-key` with your Stripe secret key

     Replace `your-custom-online-store-app-key` with any key string

   - Modify the server port:

     ```
     server.port=9090
     ```

     Replace `9090` with your preferred port number.

   - Save `application.properties`.

4. Setting Up Backend:

   - Navigate to the `Server` directory:

     ```shell
     cd server
     ```

   - Build and run the Spring Boot application:

     ```shell
     ./mvnw spring-boot:run
     ```

     The backend server will run on `http://localhost:9090`.

5. Setting Up Frontend:

   - Navigate to the `Client` directory:

     ```shell
     cd Client
     ```

   - Install dependencies:

     ```shell
     npm install
     ```

   - Start the React development server:

     ```shell
     npm start
     ```

     The frontend server will run on `http://localhost:3000`.

6. Access the Application

   - Open your web browser and go to `http://localhost:3000` to use the OnlineStore application.

## Contributing

Contributions are encouraged! To contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Implement your changes and commit them.
4. Push changes to your forked repository.
5. Submit a pull request to the main repository.

## Contact

For questions or suggestions, please reach out to the project maintainers:

- Ignatius Timothy Manullang - ignatiustimothymanullang@gmail.com
