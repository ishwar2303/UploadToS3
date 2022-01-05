package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.database.DBConDao;

@WebServlet("/admin-register")
public class RegisterAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterAdmin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/admin/register.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		boolean result = false;
		try {
			result = addAdmin(firstName, lastName, email, contact, password);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		String success = result ? "Registration successful" : "";
		String error = !result ? "Something went wrong" : "";
		json.put("success", success);
		json.put("error", error);
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		
	}

	private boolean addAdmin(String firstName, String lastName, String email, String contact, String password) throws SQLException, ClassNotFoundException {
		
		DBConDao dao = new DBConDao();
		Connection con = dao.connection();
		
		String sql = "INSERT INTO `admin` VALUES(NULL, ?, ?, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, firstName);
		st.setString(2, lastName);
		st.setString(3, email);
		st.setInt(4, Integer.parseInt(contact));
		st.setString(5, password);
		
		Integer count = st.executeUpdate();
		
		st.close();
		con.close();
		return count > 0 ? true : false;
	}
}
