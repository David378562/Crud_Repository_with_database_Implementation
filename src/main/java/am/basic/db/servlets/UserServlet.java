package am.basic.db.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

@WebServlet("/addUsers")
public class UserServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String DriverClassName = properties.getProperty("driverClassName");

            //Class.forName(DriverClassName);
            //connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jsp_db", "root", "");
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/jsp/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");

        try {
            //Bad SQL grammar, maybe SQl injection
            /*Statement statement = connection.createStatement();
            String sqlInsert = "SELECT INTO my_user_db(first_name, last_name) VALUES ('" + firstName + "','" + lastName + "');";
            System.out.println(sqlInsert);
            statement.execute(sqlInsert);*/

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO my_user_db(first_name, last_name) VALUES (?, ?)");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.execute();
            doGet(request, response);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
