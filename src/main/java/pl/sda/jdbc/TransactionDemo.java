package pl.sda.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class TransactionDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection con = ConnectionDemo.getConnection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz liczbę od 1 do 5");
        // obnizka punktow w pierwszym pytaniu
        // podwyzka w drugim
        int points = scanner.nextInt();

        // transakcja

        con.setAutoCommit(false);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        Savepoint start = con.setSavepoint("start");
        PreparedStatement update = con.prepareStatement("update question set points = points - ? where id =1");
        update.setInt(1, points);
        update.executeUpdate();

        scanner.nextLine();
        scanner.nextLine();
        // dodajemy punkty
        update = con.prepareStatement("update question set points = points + ? where id =2");
        update.setInt(1, points);
        update.executeUpdate();

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select points from question where id = 1");
        resultSet.next();
        int pointsFromBase = resultSet.getInt("points");
        System.out.printf("Punkty w pytaniu 1 : "+pointsFromBase);
        if (pointsFromBase <= 0) {
            con.rollback(start);
        }
        con.commit();
        con.close();
    }
}
