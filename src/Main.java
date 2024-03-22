import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Vaše ID: ");
        int idZakaznik = sc.nextInt();
        System.out.println("Zboží: ");
        String zbozi = sc.next();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db1066",
                "db1066",
                "Uqu0uvi3ekaa8chiexai"

        )) {
            String sql = "SELECT id_zbozi FROM zbozi WHERE popis = ?";
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, zbozi);
            System.out.println(ps);

            PreparedStatement ps2 = conn.prepareStatement("Select pocet_kusu FROM sklad WHERE id_zbozi = ?");
            int idZbozi = dejCislo(ps);

            ps2.setInt(1, idZbozi);

            int pocetKusu = dejCislo(ps2);

            System.out.println(idZbozi + " " + pocetKusu);

            if (pocetKusu == -1) {
                System.out.println();
            } else if (pocetKusu > 1) {
                // UPDATE sklad SET pocet_kusu=pocet_kusu-1
            } else {
                // DELETE frim sklad where idzbozi = ?
            }

        } catch (SQLException e) {
            System.out.println("chyba " + e);
        }
    }

    private static int dejCislo(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }
}