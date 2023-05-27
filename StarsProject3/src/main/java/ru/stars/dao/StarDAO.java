package ru.stars.dao;

import org.springframework.stereotype.Component;
import ru.stars.config.DataBase;
import ru.stars.models.Star;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class StarDAO {
    //Подключаем базу данных


    //Создание объекта звезды со всеми звездами из БД (при визуализации будем обращаться к некоторым полям элементов листа)
    public List<Star> indexStars() {
        List<Star> stars = new ArrayList<>();

        try {
            Statement statement = DataBase.getConnection().createStatement();
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
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return stars;
    }

    // Создание объекта звезды с определенным id (при визуализации будем обращаться к полям этого объекта)
    public Star show(int id) {
        Star star = null;

        try {
            PreparedStatement preparedStatement =
                    DataBase.getConnection().prepareStatement("SELECT * FROM stars WHERE id=?");

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

    // Добавление в базу данных звезды
    public void save(Star star) {
        try {
            PreparedStatement preparedStatement =
                    DataBase.getConnection().prepareStatement("SELECT * FROM stars ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int lastId = resultSet.getInt("id");
            System.out.println("lastId = " + lastId);
            preparedStatement = DataBase.getConnection().prepareStatement("INSERT INTO stars VALUES(?, ?, ?, ?, ?, ?, ?)");

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

    // Обновление полей данных какой-то звезды
    public void update(int id, Star updatedStar) {
        try {
            PreparedStatement preparedStatement =
                    DataBase.getConnection().prepareStatement("UPDATE stars SET name=?, constellation=?, temperature=?, mass=?, radius=?, luminosity=? WHERE id=?");

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

    //Удаление какой-то звезды
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DataBase.getConnection().prepareStatement("DELETE FROM stars WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}