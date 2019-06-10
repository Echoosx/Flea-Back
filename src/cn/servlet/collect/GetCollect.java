
/*
 * ���ղ�ʱ��˳����ʾ�ղؼ���
 */
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
 * Servlet implementation class GetCollect
 */
@WebServlet("/getCollect.action")
public class GetCollect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int index=0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCollect() {
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
//	    ���ݿ�
	    try {
			Connection con=Database.db_properties();
			String sql ="select g.gid as gid,g.name as gname,classify,price,count,unit,message,url,u.openid as openid,u.name as uname from collection c,goodsinfo g,userdata u where c.openid=? and c.gid=g.gid and g.openid=u.openid order by c.coldate desc";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("openid"));
//			�������ݿ�ķ��ؽ��(ʹ��ResultSet��)
	        ResultSet rs=pstmt.executeQuery();
	        index=0;
	        while(rs.next()) {
	        	jsonObject.put("index", index);
	        	jsonObject.put("gid", rs.getString("gid"));
	        	jsonObject.put("gname", rs.getString("gname"));
	        	jsonObject.put("classify", rs.getString("classify"));
	        	jsonObject.put("price", rs.getString("price"));
	        	jsonObject.put("count", rs.getString("count"));
	        	jsonObject.put("unit", rs.getString("unit"));
	        	jsonObject.put("message", rs.getString("message"));
	        	jsonObject.put("url", rs.getString("url"));
	        	jsonObject.put("openid", rs.getString("openid"));
	        	jsonObject.put("uname", rs.getString("uname"));
	        	jsonObject.put("num", 1);

	        	jsonArray.add(jsonObject);
	        	index++;
	        }
			
//	        �ر���Դ
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
