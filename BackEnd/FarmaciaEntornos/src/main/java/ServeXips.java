
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ServeXips
 */
public class ServeXips extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServeXips() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String respuesta = "null";
		String mail = request.getParameter("mail");
		String session = request.getParameter("session");
		Doctor doc = new Doctor();
		System.out.println(mail +"Session"+ session);

		if(doc.isLogged(mail,session)) {
			doc.load(mail);

			doc.loadReleaseList();
			System.out.println("termino el otro load");
			respuesta = doc.getTable();	
			System.out.println("Cogio la tabla");
			
		}
			

		response.getWriter().write(respuesta);
	}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
