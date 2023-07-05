package test.java.sql;

import java.beans.Transient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.java.sql.FacturaDAO;

public class FacturaDAOTest {
    Connection connection;

    private Connection connectDatabase(){
        /**
         * @precond se tiene instalado el driver de jdbc de postgres
         * @postcond devuelve una conexión válida de haberla, sino se muestra un mensaje de error
         */
        try{
            //Registra el driver de Postgres
            try{
                Class.forName("org.postgresql.Driver");
            }catch (ClassNotFoundException ex){
                System.out.println("Error al registrar el driver");
            }
            Connection connection = null;
            //Conectamos la BD
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", postgres, ademar17);
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "TEST OK":"TEST FAIL");
            return valid ? connection : null;
        } catch (SQLException sqle){
            System.out.println("Error: " + sqle);
        }
    }

    @BeforeEach
    public void openConnectionAndCleanup(){
        connection.close();
        connection = connectDatabase();
    }

    @Test
    public void probarTodo(){
        
    }
    
}
