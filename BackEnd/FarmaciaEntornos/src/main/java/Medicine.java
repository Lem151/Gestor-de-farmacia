import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Medicine {
    private int id;
    private String name;
    private float Tmax;
    private float Tmin;

    public Medicine() {
        // Constructor vacío
    }

    public Medicine(int id, String name, float Tmax, float Tmin) {
        this.id = id;
        this.name = name;
        this.Tmax = Tmax;
        this.Tmin = Tmin;
    }

    public void load(int id) {
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
        String query = "Select * From medicine Where id = '" + id + "';";
        ResultSet rs;


        try {
            rs = st.executeQuery(query);
            while (rs.next()) {
            	int Mid = rs.getInt("id");
                String name = rs.getString("name");
                float Tmax = rs.getFloat("Tmax");
                float Tmin = rs.getFloat("Tmin");
         
            	this.setId(Mid);
            	this.setName(name);
            	this.setTmax(Tmax);
            	this.setTmin(Tmin);
            	

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getTmax() {
		return Tmax;
	}

	public void setTmax(float tmax) {
		Tmax = tmax;
	}

	public float getTmin() {
		return Tmin;
	}

	public void setTmin(float tmin) {
		Tmin = tmin;
	}
}
