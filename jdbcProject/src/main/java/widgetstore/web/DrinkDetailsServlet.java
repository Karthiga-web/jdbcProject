package widgetstore.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import desserts.DrinkDAO;
import desserts.DrinkDAOImpl;
import desserts.DrinkDTO;
import desserts.GenericDAO;

/**
 * Servlet implementation class DrinkDetailsServlet
 */
public class DrinkDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GenericDAO<DrinkDTO> drinkDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DrinkDetailsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<form action='' method='POST'>");
		out.println("<label>Enter Product (Drink) ID: <input type='text' name='drink-id'></input></label><br></br>");
		out.println("<label> To Get Details of the Product: Hit Submit!</label><br></br>");
		out.println("<input type='submit'></input>");
		out.println("</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String drinkId = request.getParameter("drink-id");
		PrintWriter out = response.getWriter();
		DrinkDAO d = new DrinkDAOImpl();
		try {
			DrinkDTO check = (d.productInfo((Integer.parseInt(drinkId))));
			if (check != null) {
				out.println("The Details of the Product are:");
				out.println("Product ID:" + d.productInfo((Integer.parseInt(drinkId))).getId());
				out.println("Product Name:" + d.productInfo((Integer.parseInt(drinkId))).getName());
				out.println("Product Cost in Dollars:" + d.productInfo((Integer.parseInt(drinkId))).getCost());
				method(d, response);
			} else {
				out.println("Product with the given Id is not found!");
				this.doGet(request, response);
			}
		} catch (NumberFormatException e) {
			out.println("Incorrect id is given!");
			this.doGet(request, response);
		}
	}

	public void method(DrinkDAO d, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println();
		out.println();
		out.println();
		out.println("List of products with details are:");
		List<DrinkDTO> drinks = d.getAll();
		for (int i = 0; i < drinks.size(); i++) {
			out.println((i + 1) + ":");
			out.println("Product ID:" + drinks.get(i).getId());
			out.println("Product Name:" + drinks.get(i).getName());
			out.println("Product Cost in Dollars:" + drinks.get(i).getCost());
		}
	}
}