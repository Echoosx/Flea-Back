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
 * Servlet implementation class GetGoodsOrder
 */
@WebServlet("/getGoodsOrder.action")
public class GetGoodsOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGoodsOrder() {
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
        try {
        	Connection con=Database.db_properties();
			String sql ="select u.name as uname,o.openid as openid,num,g.unit as unit,oid,(UNIX_TIMESTAMP(sysdate()) - UNIX_TIMESTAMP(orddate)) ago from orderlist o,goodsinfo g,userdata u where o.gid=? and accept is null and o.gid=g.gid and o.openid=u.openid order by orddate desc";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("gid"));
//			处理数据库的返回结果(使用ResultSet类)
	        ResultSet rs=pstmt.executeQuery();
	        while(rs.next()) {
	        	jsonObject.put("oid", rs.getString("oid"));
	        	jsonObject.put("gid", request.getParameter("gid"));
	        	jsonObject.put("openid", rs.getString("openid"));
	        	jsonObject.put("uname", rs.getString("uname"));
	        	jsonObject.put("num", rs.getString("num"));
	        	jsonObject.put("unit", rs.getString("unit"));
	        	int ago=Integer.parseUnsignedInt(rs.getString("ago"));
	        	if(ago<60) {
	        		jsonObject.put("ago", ago);
	        		jsonObject.put("agounit", "秒");
	        	} else if(ago<3600) {
	        		jsonObject.put("ago", ago/60);
	        		jsonObject.put("agounit", "分钟");
	        	}else if(ago<86400) {
	        		jsonObject.put("ago", ago/3600);
	        		jsonObject.put("agounit", "小时");
	        	}else if(ago<2592000) {
	        		jsonObject.put("ago", ago/86400);
	        		jsonObject.put("agounit", "天");
	        	}else if(ago<31536000) {
	        		jsonObject.put("ago", ago/2592000);
	        		jsonObject.put("agounit", "月");
	        	}else {
	        		jsonObject.put("ago", ago/31536000);
	        		jsonObject.put("agounit", "年");
	        	}
	        	jsonArray.add(jsonObject);
	        }
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
