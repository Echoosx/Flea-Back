package cn.servlet.collect;

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
 * Servlet implementation class UpdateOrder
 */
@WebServlet("/updateOrder.action")
public class UpdateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateOrder() {
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
        try {
        	Connection con=Database.db_properties();
			String sql ="update orderlist set num=? where openid=? and oid=?";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(request.getParameter("num")));
			pstmt.setString(2, request.getParameter("openid"));
			pstmt.setString(3, request.getParameter("oid"));
			pstmt.execute();
			jsonObject.put("success", true);
        }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonObject.put("success", false);
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
