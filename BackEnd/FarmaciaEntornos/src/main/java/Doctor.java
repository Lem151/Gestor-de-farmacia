import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ArrayList;

public class Doctor extends Person {
    private String pass;
    private LocalDate lastLog;
    private String session;
    private ArrayList<Xip> releaseList;

    public Doctor() {
        super();
        releaseList = new ArrayList<>();
    }

    public Doctor(String name, String mail, String pass) {
        super(name, mail);
        this.pass = pass;
    }

    public void login(String mail, String pass) {
        System.out.println("Entra en el login");
        Connection conn = null;
        PreparedStatement st = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/farmacia", "root", "");
            String query1 = "SELECT * FROM doctor WHERE mail = ? AND pass = ?";
            st = conn.prepareStatement(query1);
            st.setString(1, mail);
            st.setString(2, pass);
            ResultSet rs = st.executeQuery();
            System.out.println("Ha preparado la consulta");
            if (rs.next()) {
                /*this.load(mail);*/
                System.out.println("Sesión iniciada");
                Random random = new Random();
                StringBuilder sessionNum = new StringBuilder();
                for (int i = 0; i < 9; i++) {
                    int digit = random.nextInt(10);
                    sessionNum.append(digit);
                }

                String session = String.valueOf(sessionNum);
                setSession(session);
                LocalDate ahora = LocalDate.now();
                System.out.println("pilla last log");
                Date lastLog = Date.valueOf(ahora);
                System.out.println("hace el update");
                String query2 = "UPDATE doctor SET session = ?, last_log = ? WHERE mail = ?";
                PreparedStatement st2 = conn.prepareStatement(query2);
                st2.setString(1, session);
                st2.setDate(2, lastLog);
                st2.setString(3, mail);
                int rowsUpdated = st2.executeUpdate();

                if (rowsUpdated > 0) {
                    setLastLog(ahora);
                } else {
                    System.out.println("No se ha podido hacer la actualización.");
                }
            } else {
                setSession("null");
                System.out.println("No se ha iniciado sesión.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isLogged(String mail, String session) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/farmacia", "root", "");
            String query = "SELECT * FROM doctor WHERE mail = ? AND session = ?";
            st = conn.prepareStatement(query);
            st.setString(1, mail);
            st.setString(2, session);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                load(mail);
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void load(String id) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/farmacia", "root", "");
            String query = "SELECT * FROM doctor WHERE mail = ?";
            st = conn.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            System.out.println("Hace la consulta");
            if (rs.next()) {
                String Dpass = rs.getString("pass");
                Date lastlog = rs.getDate("last_log");
                int sessionBBDD = rs.getInt("session");
                String Dmail = rs.getString("mail");
                String name = rs.getString("name");
                String session = String.valueOf(sessionBBDD);
                this.setPass(Dpass);
                this.setLastLog(lastlog.toLocalDate());
                this.setSession(session);
                this.setMail(Dmail);
                this.setName(name);
                System.out.println("Termina el load");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Explota");
        } finally {
          	System.out.println("que es esto");
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadReleaseList() {
        System.out.println("Entra en el otro load");
        Connection conn = null;
        PreparedStatement st = null;

        try {
            System.out.println("Entra");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/farmacia", "root", "");
            String query = "SELECT id FROM xip WHERE doctor_mail = ?";
            st = conn.prepareStatement(query);
            st.setString(1, this.mail);
            ResultSet rs = st.executeQuery();
            System.out.println("Hace la consulta");
            while (rs.next()) {
                System.out.println("Entra");
                Xip P = new Xip();
                int id = rs.getInt("id");
                P.load(id);
                System.out.println("Sale del load");
                releaseList.add(P);
                System.out.println("¿Esto funciona?");
            }
           
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTable() {
        String tablahtml = "<table id='table'>\n" +
                "<tr>\n" +
                "<th>ID</th>\n" +
                "<th>Id del medicamento</th>\n" +
                "<th>Mail del paciente</th>\n" +
                "<th>Fecha de finalización</th>\n" +
                "</tr>\n";

        for (Xip x : releaseList) {
        	System.out.println("Entra en el bucle");
            if (x.getDate().isAfter(LocalDate.now())) {
                tablahtml += "<tr>\n" +
                        "<td>" + x.getId() + "</td>\n" +
                        "<td>" + x.getMedicine().getId() + "</td>\n" +
                        "<td>" + x.getPatient().getMail() + "</td>\n" +
                        "<td>" + x.getDate() + "</td>\n" +
                        "</tr>\n";
                
            }else {
            	System.out.println("falla el if");
            }
        }
        System.out.println("Sale");

        tablahtml += "</table>";

        return tablahtml;
    }

    // Getters y Setters

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public LocalDate getLastLog() {
        return lastLog;
    }

    public void setLastLog(LocalDate lastLog) {
        this.lastLog = lastLog;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
