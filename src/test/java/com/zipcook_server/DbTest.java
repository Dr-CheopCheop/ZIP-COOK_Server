package com.zipcook_server;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbTest{

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/zipcook";

    private static final String USER = "cuk";
    private static final String PW = "1234";

    @Test
    public void testConnection() throws Exception{
        Class.forName(DRIVER);

        try(Connection conn = DriverManager.getConnection(URL, USER, PW)){

            System.out.println(conn); // 콘솔창에서 연결정보를 출력하여 확인한다.

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

