package ru.ifmo.web.client;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class Client {
    public static void main(String... args) throws SQLException_Exception, IOException {
        URL url = new URL("http://localhost:8080/users?wsdl");
        Users usersService = new Users(url);
        UsersService userPort = usersService.getUsersServicePort();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int currentState = 0;

        while (true) {
            switch (currentState) {
                case 0:
                    System.out.println("\nВыберите один из пунктов:");
                    System.out.println("1. Вывести всех пользователей");
                    System.out.println("2. Применить фильтры");
                    System.out.println("3. Выйти");
                    currentState = readState(currentState, reader);
                    break;
                case 1:
                    System.out.println("Найдено:");
                    userPort.findAll().stream().map(Client::userToString).forEach(System.out::println);
                    currentState = 0;
                    break;
                case 2:
                    System.out.println("\nЧтобы не применять фильтр, оставьте значение пустым");
                    System.out.println("id:");
                    Long id = readLong(reader);
                    System.out.println("login:");
                    String login = readString(reader);
                    System.out.println("password:");
                    String password = readString(reader);
                    System.out.println("email:");
                    String email = readString(reader);
                    System.out.println("gender:");
                    Boolean gender = readBoolean(reader);
                    System.out.println("registerDate(yyyy-mm-dd):");
                    XMLGregorianCalendar registerDate = readDate(reader);
                    System.out.println("Найдено:");
                    userPort.findWithFilters(id, login, password, email, gender, registerDate)
                            .stream()
                            .map(Client::userToString)
                            .forEach(System.out::println);
                    currentState = 0;
                    break;
                case 3:
                    return;
                default:
                    currentState = 0;
                    break;
            }
        }
    }

    private static String readString(BufferedReader reader) throws IOException {
        String trim = reader.readLine().trim();
        if (trim.isEmpty()) {
            return null;
        }
        return trim;
    }

    private static XMLGregorianCalendar readDate(BufferedReader reader) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date rd = sdf.parse(reader.readLine());

            GregorianCalendar c = new GregorianCalendar();

            if (rd != null) {
                    c.setTime(rd);
                XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                xmlGregorianCalendar.setTimezone(0);
                return xmlGregorianCalendar;
            } else {
                return null;
            }
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private static Long readLong(BufferedReader reader) {
        try {
            return Long.parseLong(reader.readLine());
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private static Boolean readBoolean(BufferedReader reader) {
        try {
            String s = reader.readLine();
            if (s.equals("")) {
                return null;
            }
            return Boolean.parseBoolean(s);
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private static int readState(int current, BufferedReader reader) {
        try {
            return Integer.parseInt(reader.readLine());
        } catch (java.lang.Exception e) {
            return current;
        }
    }

    private static String userToString(User user) {
        return "User{" +
                "id=" + user.getId() +
                ", login='" + user.getLogin() + '\'' +
                ", email='" + user.getEmail() + '\'' +
                ", gender=" + user.isGender() +
                ", registerDate=" + user.getRegisterDate() +
                '}';
    }

}
