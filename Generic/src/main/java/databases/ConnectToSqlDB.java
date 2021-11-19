package databases;

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//import static org.apache.commons.io.IOUtils.close;
//
//public class ConnectToSqlDB {
//
//    public static Connection connect =null;
//    public static Statement statement=null;
//    public static PreparedStatement ps=null;
//    public static String resultSet=null;
//
//    public static Properties loadProperties() throws IOException {
//        Properties prop=new Properties();
//        InputStream ism=new FileInputStream("../Generic/src/main/secret.properties");
//        prop.load(ism);
//        ism.close();
//        return prop;
//    }
//
//
//    public static Connection connectToSqlDatabase() throws IOException, ClassNotFoundException, SQLException {
//      Properties prop=loadProperties();
//      String driverClass=prop.getProperty("MYSQLJDBC.Driver");
//      String url=prop.getProperty("MYSQLJDBC.url");
//      String userName=prop.getProperty("MYSQLJDBC.userName");
//      String password=prop.getProperty("MYSQLJDBC.password");
//      Class.forName(driverClass);
//      connect = DriverManager.getConnection(url,userName,password);
//      System.out.println("Database is connected");
//      return connect;
//
//    }
//
//    public List<String> getResultSetData(String tableName,String columName) throws SQLException, IOException, ClassNotFoundException {
//        List<String> data = new ArrayList<>();
//        try {
//            connectToSqlDatabase();
//            statement = connect.createStatement();
//        ResultSet resultSet = statement.executeQuery("select * from " +tableName);
//            data = getResultSetData("resultSet", columName);
//
//        }catch (ClassNotFoundException e){
//            throw e;
//        }finally {
//            close();
//        }
//        return data;
//    }
//private void close() throws SQLException {
//        try {
//            if (resultSet !=null) {
//                resultSet.close();
//            }
//            if (statement !=null){
//                statement.close();
//            }
//            if(connect !=null){
//                connect.close();
//            }
//        }
//}



//}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToSqlDB {
    public static void main(String[] args) throws SQLException {

        String url="jdbc:mysql://localhost:3306/first_table?serverTimezone=UTC";
        String user="root";
        String password="Aairam1517";
        Connection connection=null;
        Statement statement=null;

        try{
            connection= DriverManager.getConnection(url,user,password);
            statement=connection.createStatement();
            String query="select * from students";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            statement.close();
            connection.close();
        }
    }

}