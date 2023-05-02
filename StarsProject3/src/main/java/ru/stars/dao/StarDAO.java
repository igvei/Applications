package ru.stars.dao;

import org.springframework.stereotype.Component;
import ru.stars.models.Star;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class StarDAO {
    private static final String URL = "jdbc:mysql://localhost:3307/mydbtest";
    private static final String USERNAME = "root1";
    private static final String PASSWORD = "root1";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Star> index() {
        List<Star> stars = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM stars";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Star star = new Star();

                star.setId(resultSet.getInt("id"));
                star.setName(resultSet.getString("name"));
                star.setConstellation(resultSet.getString("constellation"));
                star.setTemperature(resultSet.getInt("temperature"));
                star.setMass(resultSet.getDouble("mass"));
                star.setRadius(resultSet.getDouble("radius"));
                star.setLuminosity(resultSet.getDouble("luminosity"));
                stars.add(star);
                System.out.println("id = " + resultSet.getInt("id"));
                System.out.println("name = " + resultSet.getString("name"));
                System.out.println("constellation = " + resultSet.getString("constellation"));
                System.out.println("temperature = " + resultSet.getInt("temperature"));
                System.out.println("mass = " + resultSet.getDouble("mass"));
                System.out.println("radius = " + resultSet.getDouble("radius"));
                System.out.println("luminosity = " + resultSet.getDouble("luminosity"));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return stars;
    }

    public Star show(int id) {
        Star star = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM stars WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            star = new Star();

            star.setId(resultSet.getInt("id"));
            star.setName(resultSet.getString("name"));
            star.setConstellation(resultSet.getString("constellation"));
            star.setTemperature(resultSet.getInt("temperature"));
            star.setMass(resultSet.getDouble("mass"));
            star.setRadius(resultSet.getDouble("radius"));
            star.setLuminosity(resultSet.getDouble("luminosity"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return star;
    }

    public void save(Star star) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM stars ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int lastId = resultSet.getInt("id");
            System.out.println("lastId = " + lastId);
            preparedStatement = connection.prepareStatement("INSERT INTO stars VALUES(?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, lastId + 1);
            preparedStatement.setString(2, star.getName());
            preparedStatement.setString(3, star.getConstellation());
            preparedStatement.setInt(4, star.getTemperature());
            preparedStatement.setDouble(5, star.getMass());
            preparedStatement.setDouble(6, star.getRadius());
            preparedStatement.setDouble(7, star.getLuminosity());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Star updatedStar) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE stars SET name=?, constellation=?, temperature=?, mass=?, radius=?, luminosity=? WHERE id=?");

            preparedStatement.setString(1, updatedStar.getName());
            preparedStatement.setString(2, updatedStar.getConstellation());
            preparedStatement.setInt(3, updatedStar.getTemperature());
            preparedStatement.setDouble(4, updatedStar.getMass());
            preparedStatement.setDouble(5, updatedStar.getRadius());
            preparedStatement.setDouble(6, updatedStar.getLuminosity());
            preparedStatement.setInt(7, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM stars WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}