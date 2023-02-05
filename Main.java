import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        List<Student> listaStudentiItaliani= new ArrayList<>();
        List<Student> listaStudentiTedeschi= new ArrayList<>();

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","password");
            Statement createStudent = connection.createStatement();
            Statement alterStudent = connection.createStatement();
            Statement createView = connection.createStatement();
            Statement select = connection.createStatement();
            Statement insert = connection.createStatement()){
            ResultSet rs;
            createStudent.execute("CREATE TABLE student(" +
                    "student_id INT NOT NULL AUTO_INCREMENT," +
                    "last_name  VARCHAR(30) NOT NULL," +
                    "first_name VARCHAR(30) NOT NULL," +
                    "PRIMARY KEY (student_id));");
            alterStudent.execute("ALTER TABLE student ADD COLUMN country VARCHAR (30)");
            createView.execute("CREATE VIEW studenti_italiani AS " +
                    "SELECT last_name,first_name FROM student " +
                    "WHERE country = \"Italia\";");
            createView.execute("CREATE VIEW studenti_tedeschi AS " +
                    "SELECT last_name,first_name FROM student " +
                    "WHERE country = \"Germania\";");

            insert.execute("INSERT INTO student VALUES (1,\"Sepe\",\"Francesco\",\"Italia\");");
            insert.execute("INSERT INTO student VALUES (2,\"Rossi\",\"Mario\",\"Germania\");");

            rs = select.executeQuery("SELECT * FROM studenti_italiani;");
            while (rs.next()){
                listaStudentiItaliani.add(new Student(rs.getString("first_name"),rs.getString("last_name")));
            }
            rs = select.executeQuery("SELECT * FROM studenti_tedeschi;");
            while (rs.next()){
                listaStudentiTedeschi.add(new Student(rs.getString("first_name"),rs.getString("last_name")));
            }
        rs.close();
        }
        System.out.println("Stampa degli studenti italiani");
        listaStudentiItaliani.forEach(student ->
                System.out.println(student.toString()));
        System.out.println("Stampa degli studenti Tedeschi");
        listaStudentiTedeschi.forEach(student ->
                System.out.println(student.toString()));


    }

}
