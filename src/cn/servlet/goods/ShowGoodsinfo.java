package cn.servlet.goods;

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
 * Servlet implementation class showGoodsinfo
 */
@WebServlet("/showGoodsinfo.action")
public class ShowGoodsinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowGoodsinfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out=response.getWriter();
		String gid=request.getParameter("gid");
		JSONObject jsonObject=new JSONObject();
		JSONObject result=new JSONObject();
		try {
			Connection con=Database.db_properties();
			String sql ="select gid,g.name as gname,count,unit,classify,price,message,url,g.openid as openid,u.name as uname from goodsinfo g,userdata u where g.openid=u.openid and g.gid=?;";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, gid);
			
//			处理数据库的返回结果(使用ResultSet类)
	        ResultSet rs=pstmt.executeQuery();
	        boolean success=rs.next();
	        result.put("success", success);
        	jsonObject.put("gname", rs.getString("gname"));
        	jsonObject.put("count", rs.getString("count"));
        	jsonObject.put("price", rs.getString("price"));
        	jsonObject.put("unit", rs.getString("unit"));
        	jsonObject.put("classify", rs.getString("classify"));
        	jsonObject.put("message", rs.getString("message"));
        	jsonObject.put("url", rs.getString("url"));
        	jsonObject.put("openid", rs.getString("openid"));
        	jsonObject.put("uname", rs.getString("uname"));
        	result.put("result", jsonObject);
        	
//	        关闭资源
	        if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    out.println(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
