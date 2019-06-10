package cn.servlet.goods;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.util.db.Database;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AddOrder
 */
@WebServlet("/addOrder.action")
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOrder() {
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
			String sql="insert into orderlist(oid,openid,gid,num,orddate) values(?,?,?,?,sysdate())";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, request.getParameter("openid"));
			pstmt.setString(3, request.getParameter("gid"));
			pstmt.setInt(4, Integer.parseInt(request.getParameter("num")));
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
