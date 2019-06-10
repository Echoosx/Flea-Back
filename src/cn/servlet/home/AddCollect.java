/*
 * 商品加入收藏夹
 */
package cn.servlet.home;

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

import net.sf.json.JSONObject;
import cn.util.db.*;

/**
 * Servlet implementation class AddCollect
 */
@WebServlet("/addCollect.action")
public class AddCollect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCollect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out=response.getWriter();
		JSONObject jsonObject=new JSONObject();
		try {
			Connection con=Database.db_properties();
			String sql="insert into collection values(?,?,sysdate())";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("openid"));
			pstmt.setString(2, request.getParameter("gid"));
			pstmt.execute();
			jsonObject.put("success", true);
			
		} catch (SQLException e) {
			// TODO: handle exception
			jsonObject.put("success", false);
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
