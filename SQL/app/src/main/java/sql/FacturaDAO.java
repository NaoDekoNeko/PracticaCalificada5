package main.java.sql;

import java.sql.*;

public class FacturaDAO {
    public Connection connectDatabase(){
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

    public Object todo(Connection connection){
        /**
         * @params connection es una conección a la base de datos
         * @return devuelve la tabla Factura que tenemos en nuestra BD
         * @precond la conexión es válida hacia la base de datos
         * @postcond devolverá todos los datos de la tabla factura
         */
        String sql = "SELECT * FROM factura;";
        //devuelve null porque aún no se ha implementado el código
        return null;
    }

    public void guardar(Connection connection, String ... datosFactura){
        /**
         * @params connection es una conección a la base de datos
         * @params datosFactura son las entradas que se agregaran de la nueva factura
         * @precond la conexión es válida hacia la base de datos, los datos a ingresar están en el orden
         * que necesita la tabla, si hay un espacio en blanco, se específica para que no ocurran errores
         * @postcond agrega la factura a nuestra base de datos
         */
    }
}