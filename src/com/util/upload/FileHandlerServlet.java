package com.util.upload;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.List;
import java.util.Iterator;

/**
 * kannan1
 * jtrhttr
 * gjft
 * Servlet implementation class FileHandlerServlet
 */
@WebServlet("/FileHandlerServlet")
public class FileHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String FilePath = null;
    private boolean isMultipart;
    private int maxFileSize = 100*1024;
    private int maxMemSize = 1000*1024;
    private File file;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileHandlerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(){
    	//Getting the file location from web.xml
    	FilePath = getServletContext().getInitParameter("FilePath");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		try{
			isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				request.getRequestDispatcher("Fail.jsp").forward(request,response);
				
			}else{
				String FileName = (String)request.getParameter("one");
				System.out.println("FIle::"+file);
				String FileType = null;
				String newFile = null;
				File file = new File(FileName);
				FileReader fileReader = new FileReader(file);
				//for getting the file Type
				if(FileName.lastIndexOf("\\")!=-1){
					
					newFile = FileName.substring(FileName.lastIndexOf("\\")).trim();
				}
				
				FileWriter fileWriter = new FileWriter(FilePath+"MANUAL"+newFile);
				fileWriter.write(FilePath+"MANUAL"+newFile);
				request.getRequestDispatcher("Success.jsp").forward(request,response);
				
				//DiskFileItemFactory for disk level function
				DiskFileItemFactory diskFile = new DiskFileItemFactory();
				diskFile.setSizeThreshold(maxMemSize);
				System.out.println("Maximum memory size::"+maxMemSize);
				diskFile.setRepository(new File("F:\\FileUpload_using_common-io\temp"));
				
				//servletFileupload handler for fileupload
				ServletFileUpload upload = new ServletFileUpload(diskFile);
				upload.setSizeMax(maxFileSize);
				
				try{
					List FileItems = upload.parseRequest(request);
					Iterator listIterator = FileItems.iterator();
					while(listIterator.hasNext()){
						FileItem fileItem = (FileItem)listIterator.next();
						if(!fileItem.isFormField()){
							String fileName = fileItem.getName();
							String fieldName = fileItem.getFieldName();
							String getContent_Type = fileItem.getContentType();
							boolean isInmem = fileItem.isInMemory();
							if(fileName.lastIndexOf("\\")>0){
								file = new File(FilePath+fileName.substring(fileName.lastIndexOf("\\")));
								System.out.println("FILE UPLOADED TO::"+FilePath+fileName.substring(fileName.lastIndexOf("\\")));
							}
							else{
								file = new File(FilePath+fileName);
							System.out.println("FILE UPLOADED TO::"+FilePath+fileName);
							}
							fileItem.write(file);
							System.out.println("FILE WRITTEN SUCCEESSFULLY.");
							request.getRequestDispatcher("Success.jsp").forward(request,response);
						}
						
					}
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
				finally{
					if(fileReader!=null){
						fileReader.close();
					}
					if(fileWriter!=null){
						fileWriter.close();
					}
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
