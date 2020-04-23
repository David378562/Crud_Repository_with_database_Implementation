package am.basic.db.servlets;

import am.basic.db.models.User;
import dao.UserDao;
import dao.UserDaoImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@WebServlet("/users")
public class UserServletWithDao extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String DriverClassName = properties.getProperty("driverClassName");

            /*dataSource.setUrl(dbUrl);
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);
            dataSource.setDriverClassName(DriverClassName);*/

            dataSource.setUrl("jdbc:mysql://localhost:3306/jsp_db");
            dataSource.setUsername("root");
            dataSource.setPassword("");
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");


            userDao = new UserDaoImpl(dataSource);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDao.findAll();
        request.setAttribute("usersFromServer", users);
        request.getServletContext().getRequestDispatcher("/jsp/users.jsp").forward(request, response);
    }
}
