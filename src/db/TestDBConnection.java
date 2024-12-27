package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

public class TestDBConnection {
    public static void main(String[] args) {
        // Veritabanı bağlantısını test et
        if (DBConnection.getConnection() != null) {
            System.out.println("Bağlantı testi başarılı!");
        } else {
            System.out.println("Bağlantı testi başarısız!");
        }
       
          
        

        
    }
}
