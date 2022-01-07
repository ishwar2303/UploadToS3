package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.database.DBConDao;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

@WebServlet(
	name = "FetchAdmin",
	description = "Fetch all admins information from AWS RDS",
	urlPatterns = {
			"/admins",
			"/registered-admins",
			"/fetch-admins"
	}
)
public class FetchAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FetchAdmin() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<JSONObject> admins = new ArrayList();
		try {
			admins = fetchAdmin();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Integer count = admins.size();
		JSONObject json = new JSONObject();
		json.put("count", count);
		json.put("data", admins);
		
		// accessiblity
		PrintWriter out = response.getWriter();
		out.println(json.toString());
	}

	private ArrayList<JSONObject> fetchAdmin() throws ClassNotFoundException, SQLException {
		DBConDao dao = new DBConDao();
		Connection con = dao.connection();
		
		String sql = "SELECT * FROM `admin`";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		ArrayList<JSONObject> admins = new ArrayList<JSONObject>();
		while(rs.next()) {
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			JSONObject json = new JSONObject(); 
			for (int j=1; j<=rsmd.getColumnCount(); j++) {
				json.put(rs.getMetaData().getColumnLabel(j), rs.getString(j));
			}
			admins.add(json);
			
		}
		
		st.close();
		con.close();
		
		return admins;
		
	}
}
