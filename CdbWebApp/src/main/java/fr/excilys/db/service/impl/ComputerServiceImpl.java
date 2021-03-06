package fr.excilys.db.service.impl;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.service.ComputeService;
@Service
public class ComputerServiceImpl implements ComputeService  {
	
	@Autowired
	private  ComputerDaoImpl computerDaoImpl;
	
	private  ComputerServiceImpl() {
	}
//	public static ComputerServiceImpl getInstance() {
//		if (computerServiceImpl == null) {
//			computerServiceImpl = new ComputerServiceImpl();
//		}
//		return computerServiceImpl;
//	}
	
	@Override
	public List<Computer> getAllComputers()  {
		List<Computer> computers = computerDaoImpl.getAllComputers();
		return computers;
	}
	@Override
	public List<Company> getAllCompanies() {
		List<Company> companies = computerDaoImpl.getAllyCompanies();
		return companies;
	}
	@Override
	public int updateComputer(Computer computer) {
			int value = computerDaoImpl.updateComputer(computer);
				return value;
	}
	@Override
	public int createComputer(Computer computer) {
			int k = computerDaoImpl.createComputer(computer);
			return k;
	}
	@Override
	public List<Computer> getComputersByPage(int numPage,int pageSize)  {
		List<Computer> computers = computerDaoImpl.getComputersByPageNumber(numPage,pageSize);
		return computers;
	}
	@Override
	public int deleteComputer(int idComputer) {
		int k = computerDaoImpl.deleteComputer(idComputer);
		return k;
	}
	@Override
	public boolean datesExisted(LocalDate d1, LocalDate d2) {
		return ((d1 != null) && (d2 != null));
	}
	@Override
	public List<Computer> getComputersByName(String name ,int limite, int offset) {
		return computerDaoImpl.getComputersByName(name,limite,offset);
	}
	@Override
	public int getPagesNumberByName(int pageSize, String name) {
		int pageNumbers=computerDaoImpl.getPagesNumberByName(pageSize, name);
		return pageNumbers;
	}
	@Override
	public int deleteCompany(int id) {
		return computerDaoImpl.deleteCompany(id);
	}
	@Override
	public int getNumberOfPages(int pageSize) {
		return computerDaoImpl.getNumberOfPages(pageSize);
	}
	@Override
	public List<Computer> getComputersByOrder(String order, int sizePage, int numPage) {
		
		return computerDaoImpl.getComputersByOrder(order, sizePage, numPage);
	}

	@Override
	public Computer getComputerDetails(int id) {
		
		return computerDaoImpl.getComputerDetails(id);
	}

	@Override
	public Company getCompanyById(int idCompany) {
		
		return computerDaoImpl.getCompanyById(idCompany);
	}
}