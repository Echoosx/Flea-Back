/*
 * 设置收货地址
 */
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
 * Servlet implementation class SetAddress
 */
@WebServlet("/setAddress.action")
public class SetAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAddress() {
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
			
//			String sql ="insert into userdata(userName,postalCode,provinceName,cityName,countyName,detailInfo,nationalCode,telNumber) values(?,?,?,?,?,?,?,?)";
			String sql="update userdata set userName=?,postalCode=?,provinceName=?,cityName=?,countyName=?,detailInfo=?,nationalCode=?,telNumber=? where openid=?";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("userName"));
			pstmt.setString(2, request.getParameter("postalCode"));
			pstmt.setString(3, request.getParameter("provinceName"));
			pstmt.setString(4, request.getParameter("cityName"));
			pstmt.setString(5, request.getParameter("countyName"));
			pstmt.setString(6, request.getParameter("detailInfo"));
			pstmt.setString(7, request.getParameter("nationalCode"));
			pstmt.setString(8, request.getParameter("telNumber"));
			pstmt.setString(9, request.getParameter("openid"));
			pstmt.execute();
			
			jsonObject.put("success", true);
//	        关闭资源
	        if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
