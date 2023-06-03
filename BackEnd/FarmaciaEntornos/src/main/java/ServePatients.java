import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class ServePatients
 */
public class ServePatients extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServePatients() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Miguel es el putisimo amo");
		Doctor doc = new Doctor();
		JSONArray ja = new JSONArray();
		String mail = request.getParameter("mail");
		String session = request.getParameter("session");
		
		System.out.println(session);
		System.out.println(mail);
		if(doc.isLogged(mail,session)) {
			Connection con = null;
			System.out.println("epicamente el login funciona");
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
			String query1 = "Select * From patient";
			ResultSet rs;
			try {
				rs = st.executeQuery(query1);
				while (rs.next()) {
				
					String qmail = rs.getString("mail");
					String name = rs.getString("name");
					JSONObject json = new JSONObject();
					System.out.println(name);
					json.put("name",name);
					json.put("id", qmail);
					ja.put(json);
				}
				System.out.println("Sale del buccle");
					con.close();

					response.setHeader("Access-Control-Allow-Origin", "*");
					response.setContentType("text/json");
					System.out.println(ja.toString());
					response.getWriter().append(ja.toString());
			} catch (Exception e) {
				e.printStackTrace();

			}
		}else {
			System.out.println("Not is logged");
			response.getWriter().write("null");
		}
	}
		
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
