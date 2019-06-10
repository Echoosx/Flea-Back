package cn.servlet.plus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.util.db.Database;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AddGoods
 */
@WebServlet("/addGoods.action")
public class AddGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGoods() {
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
//        获取参数
        String openid=request.getParameter("openid");
        String name=request.getParameter("name");
        int count=Integer.parseInt(request.getParameter("count"));
        String unit=request.getParameter("unit");
        String classify=request.getParameter("classify");
        float price=Float.parseFloat(request.getParameter("price"));
        String message=request.getParameter("message");
        String url=request.getParameter("url");
        String gid=UUID.randomUUID().toString();
//      gid,openid,name,count,unit,classify,price,message,url
//	    数据库
	    try {
			Connection con=Database.db_properties();
			String sql ="insert into goodsinfo(gid,openid,name,count,unit,classify,price,message,url,pubdate) values(?,?,?,?,?,?,?,?,?,sysdate());";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1,gid);	
			pstmt.setString(2,openid);
			pstmt.setString(3, name);
			pstmt.setInt(4,count);
			pstmt.setString(5,unit);
			pstmt.setString(6,classify);
			pstmt.setFloat(7, price);
			pstmt.setString(8,message);
			pstmt.setString(9,url);
			pstmt.execute();
			jsonObject.put("success", true);
			jsonObject.put("gid", gid);

//	        关闭资源
	        if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
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
