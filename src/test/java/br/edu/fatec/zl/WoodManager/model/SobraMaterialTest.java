package br.edu.fatec.zl.WoodManager.model;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SobraMaterialTest {

    // Método auxiliar para abrir conexão (elimina repetição)
    private Connection getConnection() throws Exception {
        String hostname = "localhost";
        String port = "1433";
        String database = "WoodManager";
        String user = "sa";
        String pass = "1008";

        String url = "jdbc:jtds:sqlserver://" + hostname + ":" + port
                + ";databaseName=" + database
                + ";user=" + user
                + ";password=" + pass;

        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        return DriverManager.getConnection(url);
    }

    @Test
    @DisplayName("Retorna sobras suficientes mesmo quando largura e espessura estão invertidas")
    void testSobrasComMedidasSuficientes() {
        try (Connection conn = getConnection()) {

            assertNotNull(conn, "Falha ao conectar ao SQL Server!");

            int larguraMin = 5;
            int comprimentoMin = 8;
            int espessuraMin = 2;

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM SobraMaterial " +
                "WHERE CAST(comprimento AS INT) >= " + comprimentoMin + 
                " AND ( " +
                "       (CAST(largura AS INT) >= " + larguraMin + " AND CAST(espessura AS INT) >= " + espessuraMin + ") " +
                "    OR (CAST(espessura AS INT) >= " + larguraMin + " AND CAST(largura AS INT) >= " + espessuraMin + ") " +
                " )"
            );

            int count = 0;
            System.out.println("\n📌 Sobras suficientes encontradas:\n");

            while (rs.next()) {
                count++;
                System.out.println(
                    rs.getString("estoque") + " | " +
                    rs.getString("largura") + "x" +
                    rs.getString("comprimento") + "x" +
                    rs.getString("espessura")
                );
            }

            assertTrue(count >= 1, "Nenhuma sobra atende às medidas mínimas (mesmo invertidas)!");

        } catch (Exception e) {
            fail("Erro ao executar consulta: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Não retorna sobras quando nenhuma atende às medidas mínimas, mesmo com inversão de largura/espessura")
    void testSobrasInsuficientes() {
        try (Connection conn = getConnection()) {

            assertNotNull(conn, "Falha ao conectar ao SQL Server!");

            int larguraMin = 100;
            int comprimentoMin = 200;
            int espessuraMin = 300;

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM SobraMaterial " +
                "WHERE CAST(comprimento AS INT) >= " + comprimentoMin + 
                " AND ( " +
                "       (CAST(largura AS INT) >= " + larguraMin + " AND CAST(espessura AS INT) >= " + espessuraMin + ") " +
                "    OR (CAST(espessura AS INT) >= " + larguraMin + " AND CAST(largura AS INT) >= " + espessuraMin + ") " +
                " )"
            );

            int count = 0;
            while (rs.next()) {
                count++;
            }

            assertEquals(0, count, "Deveria retornar zero sobras, mas retornou algumas!");

        } catch (Exception e) {
            fail("Erro ao executar consulta: " + e.getMessage());
        }
    }
    
}
