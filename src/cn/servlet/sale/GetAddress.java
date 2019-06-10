package cn.servlet.sale;

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
 * Servlet implementation class GetAddress
 */
@WebServlet("/getAddress.action")
public class GetAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAddress() {
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
        	String sql="select userName,postalCode,provinceName,cityName,countyName,detailInfo,telNumber from userdata where openid=?";
        	PreparedStatement pstmt=con.prepareStatement(sql);
        	pstmt.setString(1, request.getParameter("openid"));
        	java.sql.ResultSet rs=pstmt.executeQuery();
        	rs.next();
        	
        	String UN=rs.getString("userName");
        	String PC=rs.getString("postalCode");
        	String PN=rs.getString("provinceName");
        	String CIN=rs.getString("cityName");
        	String CON=rs.getString("countyName");
        	String DI=rs.getString("detailInfo");
        	String TN=rs.getString("telNumber");
        	
        	if(Integer.parseInt(request.getParameter("key"))==0) {
        		if(UN==null) {
        			out.println(false);
        		}
        		else {
        			out.println(true);
        		}
        		return;
        	}else {
            	jsonObject.put("userName", UN);
            	jsonObject.put("postalCode", PC);
            	jsonObject.put("provinceName", PN);
            	jsonObject.put("cityName", CIN);
            	jsonObject.put("countyName", CON);
            	jsonObject.put("detailInfo", DI);
            	jsonObject.put("telNumber", TN);
            	
            	out.println(jsonObject);
        	}

        	
		} catch (SQLException e) {
			// TODO: handle exception
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
