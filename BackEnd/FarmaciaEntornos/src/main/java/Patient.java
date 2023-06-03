import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Patient extends Person {
    public Patient() {
        super();
    }

    public Patient(String name, String mail) {
        super(name, mail);
    }

    public void load(String mail) {
    	Connection con = null;

        try {con = DriverManager.getConnection("jdbc:mysql://localhost:3308/farmacia","root","");
    } catch (SQLException e) {
        e.printStackTrace();
    }
        Statement st = null;

        try{ st = con.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "Select * From patient Where mail = '" + mail + "';";
        ResultSet rs;


        try {
            rs = st.executeQuery(query);
            while (rs.next()) {
            	String Pmail = rs.getString("mail");
            	String name = rs.getString("name");
            	this.setMail(Pmail);
            	this.setName(name);
            	System.out.println(name);
            	

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        try {
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    
}
