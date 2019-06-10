package cn.servlet.personal;

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

import cn.util.db.Database;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/addUser.action")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    JSONObject jsonObject=new JSONObject();
//	    数据库
	    try {
			Connection con=Database.db_properties();
			
	        java.sql.Statement stmt=con.createStatement();
			String sql ="insert into userdata(openid) values(?)";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("openid"));
			pstmt.execute();
			jsonObject.put("success", true);
//	        关闭资源
	        if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			jsonObject.put("success", false);
			e.printStackTrace();
		}
	    out.print(jsonObject);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
