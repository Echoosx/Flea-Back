package cn.servlet.revise;

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
 * Servlet implementation class UpdateOnsale
 */
@WebServlet("/updateOnsale.action")
public class UpdateOnsale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateOnsale() {
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
        String gid=request.getParameter("gid");
//      gid,openid,name,count,unit,classify,price,message,url
//	    数据库
	    try {
			Connection con=Database.db_properties();
			String sql ="update goodsinfo set name=?,count=?,unit=?,classify=?,price=?,message=?,url=? where openid=? and gid=?";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2,count);
			pstmt.setString(3,unit);
			pstmt.setString(4,classify);
			pstmt.setFloat(5, price);
			pstmt.setString(6,message);
			pstmt.setString(7,url);
			pstmt.setString(8,openid);
			pstmt.setString(9,gid);
			pstmt.execute();
			jsonObject.put("success", true);

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
