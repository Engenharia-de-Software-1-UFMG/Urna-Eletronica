/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urnaeletronica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Data Access Object genérico.
 *
 * @see https://pt.wikipedia.org/wiki/Objeto_de_acesso_a_dados
 *
 * @author Bernardo
 */
public abstract class GenericDAO {

    protected Connection getConnection() {
        return ConnectionDatabase.getConnection();
    }

    protected int save(String insertSql, Object... parametros) {
        int id = -1;
        try {
            Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(insertSql,
                Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i + 1, parametros[i]);
            }

            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()){ 
                id = generatedKeys.getInt(1);
            }

            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return id;
    }

    protected void update(String updateSql, Object id, Object... parametros) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(updateSql);

        for (int i = 0; i < parametros.length; i++) {
            pstmt.setObject(i + 1, parametros[i]);
        }
        pstmt.setObject(parametros.length + 1, id);
        pstmt.execute();
        pstmt.close();
        connection.close();
    }

    protected void delete(String deleteSql, Object... parametros) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(deleteSql);

        for (int i = 0; i < parametros.length; i++) {
            pstmt.setObject(i + 1, parametros[i]);
        }

        pstmt.execute();
        pstmt.close();
        connection.close();
    }
}
