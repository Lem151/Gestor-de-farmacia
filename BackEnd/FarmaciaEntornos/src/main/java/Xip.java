import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Xip {
    private int id;
    private Medicine medicine;
    private Patient patient;
    private LocalDate date;

    public Xip() {
        // Constructor vacío
    }

    public Xip(int id, Medicine medicine, Patient patient, LocalDate date) {
        this.id = id;
        this.medicine = medicine;
        this.patient = patient;
        this.date = date;
    }

    public void load(int id) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            System.out.println("Hace load Xips");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/farmacia", "root", "");
            String query = "SELECT * FROM xip WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("Entra en la consulta");
                int Xid = rs.getInt("id");
                int medicineId = rs.getInt("id_medicine");
                String patientMail = rs.getString("id_patient");
                Date date = rs.getDate("date");
                Medicine medicine = new Medicine();
                Patient patient = new Patient();
                medicine.load(medicineId);
                patient.load(patientMail);

                this.setId(Xid);
                this.setDate(date.toLocalDate());
                this.setMedicine(medicine);
                this.setPatient(patient);
                System.out.println("Termina Xips");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
