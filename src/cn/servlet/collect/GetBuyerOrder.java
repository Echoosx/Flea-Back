package cn.servlet.collect;

import java.io.FileNotFoundException;
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
 * Servlet implementation class GetOrder
 */
@WebServlet("/getBuyerOrder.action")
public class GetBuyerOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBuyerOrder() {
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
//	    数据库
	    try {
			Connection con=Database.db_properties();
			String sql ="select oid,g.gid as gid,g.name as gname,classify,price,message,count,unit,url,g.openid as openid,u.name as uname,num,accept,(UNIX_TIMESTAMP(sysdate()) - UNIX_TIMESTAMP(orddate)) ago from orderlist o,goodsinfo g,userdata u where o.openid=? and o.gid=g.gid and g.openid=u.openid order by o.orddate desc";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("openid"));
//			处理数据库的返回结果(使用ResultSet类)
	        ResultSet rs=pstmt.executeQuery();
	        while(rs.next()) {
	        	jsonObject.put("oid", rs.getString("oid"));
	        	jsonObject.put("gid", rs.getString("gid"));
	        	jsonObject.put("gname", rs.getString("gname"));
	        	jsonObject.put("classify", rs.getString("classify"));
	        	jsonObject.put("price", rs.getString("price"));
	        	jsonObject.put("message", rs.getString("message"));
	        	jsonObject.put("count", rs.getString("count"));
	        	jsonObject.put("unit", rs.getString("unit"));
	        	jsonObject.put("url", rs.getString("url"));
	        	jsonObject.put("openid", rs.getString("openid"));
	        	jsonObject.put("uname", rs.getString("uname"));
	        	jsonObject.put("num", rs.getString("num"));
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
	        	
	        	if(rs.getString("accept")==null) {
	        		jsonObject.put("accept", "null");
	        	}else if(Integer.parseInt(rs.getString("accept"))==1){
	        		jsonObject.put("accept", true);
	        	}else {
	        		jsonObject.put("accept", false);
	        	}
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
		}catch(FileNotFoundException e) {
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
