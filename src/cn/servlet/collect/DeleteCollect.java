/*
 * 从收藏夹中移除某商品
 */
package cn.servlet.collect;

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
 * Servlet implementation class DeleteCollect
 */
@WebServlet("/deleteCollect.action")
public class DeleteCollect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCollect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out=response.getWriter();
		JSONObject jsonObject=new JSONObject();
		try {
			Connection con=Database.db_properties();
			java.sql.Statement statement=con.createStatement();
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");
			String sql="delete from collection where openid=? and gid=?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("openid"));
			pstmt.setString(2, request.getParameter("gid"));
			pstmt.execute();
			statement.execute("SET FOREIGN_KEY_CHECKS = 1");
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
