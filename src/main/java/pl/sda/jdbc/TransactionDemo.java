package pl.sda.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TransactionDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection con = ConnectionDemo.getConnection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz liczbę od 1 do 5");
        // obnizka punktow w pierwszym pytaniu
        // podwyzka w drugim
        int points = scanner.nextInt();
        PreparedStatement update = con.prepareStatement("update question set points = points - ? where id =1");
        update.setInt(1, points);
        update.executeUpdate();

        scanner.nextLine();
        scanner.nextLine();
        // dodajemy punkty
        update = con.prepareStatement("update question set points = points + ? where id =2");
        update.setInt(1, points);
        update.executeUpdate();
        con.close();
    }
}
