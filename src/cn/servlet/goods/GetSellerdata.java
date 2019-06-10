package cn.servlet.goods;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.util.db.*;

/**
 * Servlet implementation class GetSellerdata
 */
@WebServlet("/getSellerdata.action")
public class GetSellerdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSellerdata() {
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
		JSONObject result=new JSONObject();
		try {
			Connection con=Database.db_properties();
			String sql="select name,phonenumber,school,mail from userdata where openid=?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("openid"));
			ResultSet rs=pstmt.executeQuery();
			boolean success=rs.next();
			result.put("success", success);
	        if(success) {
	        	String mail=rs.getString("mail");
	        	String name=rs.getString("name");
	        	String phonenumber=rs.getString("phonenumber");
	        	String school=rs.getString("school");
				if(Integer.parseInt(request.getParameter("key"))==0) {
	        		if(mail==null||name==null||phonenumber==null||school==null) {
	        			out.println(false);
	        		}
	        		else {
	        			out.println(true);
	        		}
	        		return;
	        	}else {
					jsonObject.put("mail", mail);
					jsonObject.put("name", name);
					jsonObject.put("phonenumber", phonenumber);
					jsonObject.put("school", school);
					result.put("sellerdata", jsonObject);
	        	}
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			result.put("success",false);
			e.printStackTrace();
		}
		out.print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
