package cn.servlet.revise;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.util.controller.DeleteFile;
import cn.util.db.Database;

/**
 * Servlet implementation class DeletePic
 */
@WebServlet("/deletePic.action")
public class DeletePic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePic() {
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
		try {
			Connection con=Database.db_properties();
			String sql ="select openid from goodsinfo where url=?";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, request.getParameter("url"));
			java.sql.ResultSet rs=pstmt.executeQuery();
			rs.next();
			if(rs.getString("openid")==request.getParameter("openid")) {
				ResourceBundle resource = ResourceBundle.getBundle("file");
				String PATH=resource.getString("PATH");
				String []url=request.getParameter("url").split("/");
				String fileName=url[url.length-1];
				String filePath=PATH+fileName;
//				out.println(filePath);
				if(DeleteFile.deleteFile(filePath)) {
					out.println(true);
				}else {
					out.println(false);
				}
			}else {
				out.println(false);
				return;
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
