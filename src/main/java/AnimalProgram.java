import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

interface Commandable {
    void listCommands();

    void teachCommand(String command);
}

abstract class Animal {
    private String name;
    private Date birthday;

    public Animal(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public abstract String getTableName();
}

abstract class HomeAnimal extends Animal implements Commandable {
    private List<String> commands;

    public HomeAnimal(String name, Date birthday) {
        super(name, birthday);
        this.commands = new ArrayList<>();
    }

    public List<String> getCommands() {
        return commands;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    @Override
    public void listCommands() {
        System.out.println("Commands for " + getName() + ":");
        for (String command : getCommands()) {
            System.out.println("- " + command);
        }
    }

    @Override
    public void teachCommand(String command) {
        addCommand(command);
        System.out.println(getName() + " has learned a new command: " + command);
    }
}

abstract class PackAnimal extends Animal {
    public PackAnimal(String name, Date birthday) {
        super(name, birthday);
    }

}

class Cat extends HomeAnimal {
    public Cat(String name, Date birthday) {
        super(name, birthday);
    }

    @Override
    public String getTableName() {
        return "cats";
    }
}

class Dog extends HomeAnimal {
    public Dog(String name, Date birthday) {
        super(name, birthday);
    }

    @Override
    public String getTableName() {
        return "dogs";
    }
}

class Hamster extends HomeAnimal {
    public Hamster(String name, Date birthday) {
        super(name, birthday);
    }

    @Override
    public String getTableName() {
        return "hamsters";
    }
}

class Horse extends PackAnimal {
    public Horse(String name, Date birthday) {
        super(name, birthday);
    }

    @Override
    public String getTableName() {
        return "horses";
    }
}

class Camel extends PackAnimal {
    public Camel(String name, Date birthday) {
        super(name, birthday);
    }

    @Override
    public String getTableName() {
        return "camels";
    }
}

class Donkey extends PackAnimal {
    public Donkey(String name, Date birthday) {
        super(name, birthday);
    }

    @Override
    public String getTableName() {
        return "donkeys";
    }
}

class AnimalRegistry {
    private Connection connection;

    public AnimalRegistry(Connection connection) {
        this.connection = connection;
    }

    public void addAnimal(Animal animal) {
        try {
            String query = "INSERT INTO " + animal.getTableName() + " (Name, Birthday) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, animal.getName());
                preparedStatement.setDate(2, new java.sql.Date(animal.getBirthday().getTime()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listCommands(String animalName, String type) {
        try {
            String query = "SELECT Commands FROM " + getTableName(type) + " WHERE Name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, animalName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Commands for " + animalName + ": " + resultSet.getString("Commands"));
                    } else {
                        System.out.println("Animal not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void teachCommand(String animalName, String type, String newCommand) {
        try {
            String tableName = getTableName(type);
            if (tableName == null) {
                System.out.println("Invalid animal type.");
                return;
            }

            String getCurrentCommandsQuery = "SELECT Commands FROM " + tableName + " WHERE Name = ?";
            try (PreparedStatement getCurrentCommandsStatement = connection.prepareStatement(getCurrentCommandsQuery)) {
                getCurrentCommandsStatement.setString(1, animalName);
                try (ResultSet resultSet = getCurrentCommandsStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String existingCommands = resultSet.getString("Commands");
                        if (existingCommands != null && !existingCommands.isEmpty()) {
                            newCommand = existingCommands + ", " + newCommand;
                        }
                    }
                }
            }

            String updateCommandQuery = "UPDATE " + tableName + " SET Commands = ? WHERE Name = ?";
            try (PreparedStatement updateCommandStatement = connection.prepareStatement(updateCommandQuery)) {
                updateCommandStatement.setString(1, newCommand);
                updateCommandStatement.setString(2, animalName);
                int updatedRows = updateCommandStatement.executeUpdate();
                if (updatedRows > 0) {
                    System.out.println(animalName + " has learned a new command: " + newCommand);
                } else {
                    System.out.println("Animal not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String getTableName(String animalName) {
        if (animalName.toLowerCase().equals("cat")) {
            return "cats";
        } else if (animalName.toLowerCase().equals("dog")) {
            return "dogs";
        } else if (animalName.toLowerCase().equals("hamster")) {
            return "hamsters";
        } else if (animalName.toLowerCase().equals("horse")) {
            return "horses";
        } else if (animalName.toLowerCase().equals("camel")) {
            return "camels";
        } else if (animalName.toLowerCase().equals("donkey")) {
            return "donkeys";
        } else {
            throw new IllegalArgumentException("Invalid animal type: " + animalName);
        }
    }

    public void listAnimalsByBirthday() {
        try {
            String query = "SELECT Name, Birthday FROM cats UNION " +
                    "SELECT Name, Birthday FROM dogs UNION " +
                    "SELECT Name, Birthday FROM hamsters UNION " +
                    "SELECT Name, Birthday FROM horses UNION " +
                    "SELECT Name, Birthday FROM camels UNION " +
                    "SELECT Name, Birthday FROM donkeys " +
                    "ORDER BY Birthday";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                System.out.println("List of animals sorted by birthday:");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("Name") + " - " + resultSet.getDate("Birthday"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalAnimalCount() {
        int totalAnimalCount = 0;
        try {
            String query = "SELECT COUNT(*) AS TotalCount FROM cats UNION " +
                    "SELECT COUNT(*) AS TotalCount FROM dogs UNION " +
                    "SELECT COUNT(*) AS TotalCount FROM hamsters UNION " +
                    "SELECT COUNT(*) AS TotalCount FROM horses UNION " +
                    "SELECT COUNT(*) AS TotalCount FROM camels UNION " +
                    "SELECT COUNT(*) AS TotalCount FROM donkeys";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    totalAnimalCount += resultSet.getInt("TotalCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalAnimalCount;
    }
}

public class AnimalProgram {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/human_friends";
    private static final String JDBC_USER = "admin";
    private static final String JDBC_PASSWORD = "1234";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Установка соединения с базой данных
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            Scanner scanner = new Scanner(System.in);
            AnimalRegistry animalRegistry = new AnimalRegistry(connection);

            while (true) {
                System.out.println("\nAnimal Registry Menu:");
                System.out.println("1. Add New Animal");
                System.out.println("2. List Commands for an Animal");
                System.out.println("3. Teach New Command to an Animal");
                System.out.println("4. List Animals by Birthday");
                System.out.println("5. Total Animal Count");
                System.out.println("6. Exit");

                System.out.print("Enter your choice (1-6): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addNewAnimal(scanner, animalRegistry);
                        break;
                    case 2:
                        listCommandsForAnimal(scanner, animalRegistry);
                        break;
                    case 3:
                        teachNewCommand(scanner, animalRegistry);
                        break;
                    case 4:
                        animalRegistry.listAnimalsByBirthday();
                        break;
                    case 5:
                        System.out.println("Total Animal Count: " + animalRegistry.getTotalAnimalCount());
                        break;
                    case 6:
                        System.out.println("Exiting program. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addNewAnimal(Scanner scanner, AnimalRegistry animalRegistry) {
        System.out.print("Enter the animal type (Cat/Dog/Hamster/Horse/Camel/Donkey): ");
        String animalType = scanner.next();

        System.out.print("Enter the animal name: ");
        String name = scanner.next();

        System.out.print("Enter the animal's birthday (YYYY-MM-DD): ");
        String birthdayString = scanner.next();
        Date birthday;
        try {
            birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Animal not added.");
            return;
        }

        Animal animal;
        if (animalType.equalsIgnoreCase("Cat")) {
            animal = new Cat(name, birthday);
        } else if (animalType.equalsIgnoreCase("Dog")) {
            animal = new Dog(name, birthday);
        } else if (animalType.equalsIgnoreCase("Hamster")) {
            animal = new Hamster(name, birthday);
        } else if (animalType.equalsIgnoreCase("Horse")) {
            animal = new Horse(name, birthday);
        } else if (animalType.equalsIgnoreCase("Camel")) {
            animal = new Camel(name, birthday);
        } else if (animalType.equalsIgnoreCase("Donkey")) {
            animal = new Donkey(name, birthday);
        } else {
            System.out.println("Invalid animal type. Animal not added.");
            return;
        }

        animalRegistry.addAnimal(animal);
        System.out.println(name + " the " + animalType + " has been added to the registry.");
    }

    private static void listCommandsForAnimal(Scanner scanner, AnimalRegistry animalRegistry) {
        System.out.print("Enter the name of the animal: ");
        String name = scanner.next();
        System.out.print("Enter the type of the animal: ");
        String type = scanner.next();
        animalRegistry.listCommands(name,type);
    }

    private static void teachNewCommand(Scanner scanner, AnimalRegistry animalRegistry) {
        System.out.print("Enter the name of the animal: ");
        String name = scanner.next();
        System.out.print("Enter the type of the animal: ");
        String type = scanner.next();

        System.out.print("Enter the new command to teach: ");
        String newCommand = scanner.next();

        animalRegistry.teachCommand(name, type, newCommand);
    }
}
