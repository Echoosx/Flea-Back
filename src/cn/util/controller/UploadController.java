package cn.util.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UploadController
 */
@WebServlet("/upload.action")
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        JSONObject jsonObject=new JSONObject();
        response.setContentType("text/json;charset=UTF-8");
        
		ResourceBundle resource = ResourceBundle.getBundle("file");
		String path=resource.getString("PATH");
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        //��ô����ļ���Ŀ����
        DiskFileItemFactory factory = new DiskFileItemFactory();

        //���û�����������õĻ�,�ϴ�����ļ���ռ�úܶ��ڴ棬
        //������ʱ��ŵĴ洢��,����洢�ҿ��Ժ����մ洢�ļ���Ŀ¼��ͬ
        /**
         * ԭ��: �����ȴ浽��ʱ�洢�ң�Ȼ��������д����ӦĿ¼��Ӳ���ϣ�
         * ������˵���ϴ�һ���ļ�ʱ����ʵ���ϴ������ݣ���һ������ .tem ��ʽ��
         * Ȼ���ٽ�������д����ӦĿ¼��Ӳ����
         */
        factory.setRepository(dir);
        //���û���Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ���ʱ�洢��
        factory.setSizeThreshold(1024 * 1024);
        //��ˮƽ��API�ļ��ϴ�����
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> list = upload.parseRequest(request);
            FileItem picture = null;
            for (FileItem item : list) {
                //��ȡ������������
                String name = item.getFieldName();
                //�����ȡ�ı���Ϣ����ͨ�� �ı� ��Ϣ
                if (item.isFormField()) {
                    //��ȡ�û�����������ַ���
                    String value = item.getString();
                    request.setAttribute(name, value);
                } else {
                    picture = item;
                }
            }

            //�Զ����ϴ�ͼƬ������Ϊxxx.jpg
            String fileName = request.getAttribute("timestamp") + ".jpg";
            String destPath = path + fileName;

            //����д��������
            File file = new File(destPath);
            OutputStream out = new FileOutputStream(file);
            InputStream in = picture.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) ÿ�ζ��������ݴ����buf ������
            while ((length = in.read(buf)) != -1) {
                //��buf������ȡ������д�����������������
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
            jsonObject.put("url", "https://www.zamonia.cn/statics/upload_pic/"+fileName);
            
        } catch (FileUploadException e1) {
        	e1.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }

        printWriter.println(jsonObject);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
