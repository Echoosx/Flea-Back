package cn.servlet.home;

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
 * Servlet implementation class SearchGoods
 */
@WebServlet("/searchGoods.action")
public class SearchGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int selectnum=0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchGoods() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    selectnum=0;
	    String keyword="%"+request.getParameter("keyword")+"%";
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
//	    数据库
	    try {
			Connection con=Database.db_properties();
			String sql ="select * from goodsinfo where name like ? or message like ? order by pubdate desc;";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
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
	        	
	        	jsonArray.add(jsonObject);
	        	selectnum++;
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
	    JSONObject result=new JSONObject();
	    result.put("select", selectnum);
	    result.put("list", jsonArray);
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
