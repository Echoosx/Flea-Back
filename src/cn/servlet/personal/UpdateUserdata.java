package cn.servlet.personal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.util.db.Database;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class UpdateUserdata
 */
@WebServlet("/updateUserdata.action")
public class UpdateUserdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserdata() {
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
	    String openid=request.getParameter("openid");
	    String key=request.getParameter("key");
	    String value=request.getParameter("value"); 
//	    数据库
	    try {
			Connection con=Database.db_properties();
	        java.sql.Statement stmt=con.createStatement();
	        
			String sql ="update userdata set %s=\"%s\" where openid=\"%s\"";
			sql=String.format(sql, key,value,openid);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("update", stmt.executeUpdate(sql));
			out.println(jsonObject);
//	        关闭资源
	        if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
