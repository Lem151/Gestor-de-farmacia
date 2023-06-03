import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import org.json.JSONArray;


/**
 * Servlet implementation class Release
 */
public class Release extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Release() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Doctor doc = new Doctor();
		JSONArray JSON = new JSONArray();
		String mail = request.getParameter("mail");
		String session = request.getParameter("session");
		int id = Integer.parseInt(request.getParameter("id"));
		String pacient = request.getParameter("paciente");
		int medicine = Integer.parseInt(request.getParameter("medicamento"));
		LocalDate fecha = LocalDate.parse(request.getParameter("fechalimite"));

		if(doc.isLogged(mail,session)) {
		Connection con = null;

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3308/farmacia", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Statement st = null;

		try {
			st = con.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query1 = "INSERT INTO xip (id, doctor_mail, id_medicine, id_patient, date) VALUES ('" + id + "','" + mail + "','" + medicine + "','" + pacient + "','" + fecha + "')";

		try {
			st.executeUpdate(query1);
			}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.getWriter().append(JSON.toString());
		

		}else {
			response.setContentType("text/plain");
			response.getWriter().write("null");
		}
	}

}
