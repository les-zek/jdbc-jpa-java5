package jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CRUDApp {
    static Scanner scanner = new Scanner(System.in);
    public static int menu(){
        System.out.println("1. Utwórz tabelę");
        System.out.println("2. Dodaj rekord");
        System.out.println("3. Wyświetl tabelę");
        System.out.println("4. Wyszukaj wg imienia i punktów");
        System.out.println("0. Wyjście");
        while(!scanner.hasNextInt()){
            scanner.nextLine();
        }
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }
    public static void insertRecord() throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        System.out.println("Podaj imię:");
        String name = scanner.nextLine();
        System.out.println("Podaj punkty:");
        int points = scanner.nextInt();
        Connection connection = ConnectionDemo.getConnection();
        PreparedStatement insert = connection.prepareStatement(
                "insert into demo(name, points) values(? ,?)"
        );
        insert.setString(1, name);
        insert.setInt(2, points);
        insert.executeUpdate();
        connection.close();
    }

    public static void findByNameOrPoints() throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        System.out.println("Podaj imię lub szablon:");
        String templateName = scanner.nextLine();
        System.out.println("Podaj limit punktów:");
        int limit = scanner.nextInt();
        Connection connection = ConnectionDemo.getConnection();
        PreparedStatement search = connection.prepareStatement(
                "select * from demo where name like ? or points >= ?"
        );
        search.setString(1, templateName);
        search.setInt(2, limit);
        ResultSet result = search.executeQuery();
        SelectDemo.printDemoTable(result);
    }
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        while(true){
            switch (menu()){
                case 1:
                    //TODO zdefiniować metodę tworzenie własnej tabeli
                    CreateTableStatementDemo.createTableDemo();
                    break;
                case 2:
                    insertRecord();
                    break;
                case 3:
                    //wyświetl tabelę
                    break;
                case 4:
                    findByNameOrPoints();
                    break;
                case 0:
                    System.exit(0);
                    //TODO dodać modyfikację, usuwanie i wyszukiwanie rekordów
            }
        }
    }
}
