package fr.excilys.db.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.dto.ComputerMapper;
import fr.excilys.db.dto.DtoConnection;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.impl.ComputerServiceImpl;

/**
 * Servlet implementation class FirsTServlet
 */
@WebServlet("/computerServlet")
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerServlet() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DtoConnection.getConnection();
		
		String pageSize=request.getParameter("size");
		//int numPage=Integer.parseInt(request.getParameter("numPage"));
		int beginPage=Integer.parseInt(request.getParameter("beginPage"));
		int endPage=Integer.parseInt(request.getParameter("endPage"));
		int numPage=beginPage;
		int pageSizeInt= Integer.parseInt(pageSize);
		int numberOfPages=ComputerDaoImpl.getInstance().getNumberOfPages(conn, pageSizeInt);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("numPage", numPage);
		request.setAttribute("size", pageSizeInt);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		List<Computer> computers=ComputerServiceImpl.getInstance().getComputersByPage(numPage, conn, pageSizeInt);
		List<fr.excilys.db.dto.Computer> computersDTO=ComputerMapper.fromListObjecToListString(computers);
		request.setAttribute("computers", computersDTO);
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
