package cn.servlet.personal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.util.db.Database;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class showUserdata
 */
@WebServlet("/showUserdata.action")
public class ShowUserdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUserdata() {
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
			String sql ="select mail,name,phonenumber,school,sex,userName,telNumber from userData where openid=?";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("openid"));		
//			处理数据库的返回结果(使用ResultSet类)
	        ResultSet rs=pstmt.executeQuery();
	        boolean hasResultSet=rs.next();
	        if(hasResultSet) {
				jsonObject.put("mail", rs.getString("mail"));
				jsonObject.put("name", rs.getString("name"));
				jsonObject.put("phonenumber", rs.getString("phonenumber"));
				jsonObject.put("school", rs.getString("school"));
				jsonObject.put("sex", rs.getString("sex"));
				jsonObject.put("userName", rs.getString("userName"));
				jsonObject.put("telNumber", rs.getString("telNumber"));
			}
			else {
				System.out.println("no user!");
				request.getRequestDispatcher("/addUser.action").forward(request,response);
			}
			
//	        关闭资源
	        if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}   
	    out.println(jsonObject);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
