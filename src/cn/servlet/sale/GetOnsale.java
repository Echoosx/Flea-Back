package cn.servlet.sale;

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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetOnsale
 */
@WebServlet("/getOnsale.action")
public class GetOnsale extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int index=0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOnsale() {
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
        JSONArray jsonArray=new JSONArray();
        this.index=0;
//	    数据库
	    try {
			Connection con=Database.db_properties();
			String sql ="select * from goodsinfo where openid=? order by pubdate desc";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("openid"));
//			处理数据库的返回结果(使用ResultSet类)
	        ResultSet rs=pstmt.executeQuery();
	        while(rs.next()) {
	        	jsonObject.put("gid", rs.getString("gid"));
	        	jsonObject.put("name", rs.getString("name"));
	        	jsonObject.put("classify", rs.getString("classify"));
	        	jsonObject.put("price", rs.getString("price"));
	        	jsonObject.put("count", rs.getString("count"));
	        	jsonObject.put("unit", rs.getString("unit"));
	        	jsonObject.put("message", rs.getString("message"));
	        	jsonObject.put("url", rs.getString("url"));
	        	jsonObject.put("index", this.index);
	        	jsonObject.put("show", false);
	        	this.index++;
	        	jsonArray.add(jsonObject);
	        }
			
//	        关闭资源
	        if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    out.println(jsonArray);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
