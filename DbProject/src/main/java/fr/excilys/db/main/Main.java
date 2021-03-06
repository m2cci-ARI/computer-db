package fr.excilys.db.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import fr.excilys.db.connection.ComputerDBConnection;
import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.exception.ComputerToDeleteNotFound;
import fr.excilys.db.exception.DatesNotValidException;
import fr.excilys.db.exception.NoCompanyFound;
import fr.excilys.db.exception.NoComputerFound;
import fr.excilys.db.exception.NotFoundCompanyException;
import fr.excilys.db.exception.PageNotFoundException;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
import fr.excilys.db.model.ComputerBuilder;
import fr.excilys.db.service.impl.*;
import fr.excilys.db.validators.LocalDateValidator;

public class Main {
	
	private static String url ="jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
	private static String username="admincdb";
	private static String password="qwerty1234";
	static Connection conn;
	static ComputerDaoImpl computerDAO = ComputerDaoImpl.getInstance();
	static ComputerServiceImpl computerServiceImpl = ComputerServiceImpl.getInstance();
	static int value;

	public static void main(String[] args) {

		showTheMenu();

		Label: while (true) {
			switch (value) {
			case 1:
				ComputerDBConnection cdbcnx1= new ComputerDBConnection(url, username, password);
				Connection conn=cdbcnx1.getConnection();
				List<Computer> computers = manageAllComputers(conn);
				computers.forEach((c) -> System.out.println(c));
				value = showTheMenu();
				break;
			case 2:
				ComputerDBConnection cdbcnx2= new ComputerDBConnection(url, username, password);
				conn=cdbcnx2.getConnection();
				List<Company> companies = manageAllCompanies(conn);
				companies.forEach((cp)->System.out.println(cp));
				value = showTheMenu();
				break;
			case 3:
				ComputerDBConnection cdbcnx3= new ComputerDBConnection(url, username, password);
				conn=cdbcnx3.getConnection();
				Computer computer = computerCreate(conn);
				manageCreate(computer,conn);
				value = showTheMenu();
				break;
			case 4:
				System.out.println("Your are about to get computer details :");
				int idComputer = getIdOfComputer();
				ComputerDBConnection cdbcnx4= new ComputerDBConnection(url, username, password);
				conn=cdbcnx4.getConnection();
				Computer myComputer = manageDetails(idComputer,conn);
				System.out.println(myComputer.getId()+" "+myComputer.getName()+" "+myComputer.getIntroducedDate()+" "+myComputer.getDiscountedDate()+" "+myComputer.getCompany().getId());
				value = showTheMenu();
				break;
			case 5:
				System.out.println("Your are in the update part :");
				ComputerDBConnection cdbcnx5= new ComputerDBConnection(url, username, password);
				conn=cdbcnx5.getConnection();
				Computer updatComputer = getComputerToUpdate(conn);
				manageUpdate(updatComputer,conn);
				value = showTheMenu();
				break;
			case 6:
				System.out.println("Your are in the delete part :");
				int idCom = getComputerToDelet();
				ComputerDBConnection cdbcnx6= new ComputerDBConnection(url, username, password);
				conn=cdbcnx6.getConnection();
				manageDelete(idCom,conn);
				value = showTheMenu();
				break;
			case 7:
				System.out.println("Pagination part :");
				ComputerDBConnection cdbcnx7= new ComputerDBConnection(url, username, password);
				conn=cdbcnx7.getConnection();
				List<Computer> myComputers = computersByPage(conn);
				myComputers.forEach((cp) -> System.out.println(cp.toString()));
				value = showTheMenu();
				break;
			case 8:
				System.out.print("Quit");
				System.out.flush();
				System.out.println("Thank you for using our Application.");
				break Label;
			}

		}
	}

	public static int getOperationNumber() {
		System.out.println("Choose a number :");
		Scanner sc = new Scanner(System.in);
		value = sc.nextInt();
		return value;
	}

	public static Computer computerCreate(Connection conn) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the name of PC :");
		String name = sc.nextLine();
		
		LocalDate localDateIntro=LocalDateValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted=LocalDateValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = sc.nextInt();
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Company company = computerDao.getCompanyById(idCompany,conn);
		Computer computer = ComputerBuilder.newInstance().setName(name).setIntroducedDate(localDateIntro)
				.setDiscountedDate(localDateDicounted).setCompany(company).build();
		return computer;
	}

	public static int getIdOfComputer() {
		System.out.println("Please give the id of the computer");
		Scanner sc = new Scanner(System.in);
		int idComputer = sc.nextInt();
		return idComputer;
	}

	public static int getComputerToDelet() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		int idComputer = sc.nextInt();
		return idComputer;
	}

	public static Computer getComputerToUpdate(Connection conn) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please give the id of the computer to update");
		Scanner scn = new Scanner(System.in);
		int idComputer = sc.nextInt();
		System.out.println("Please give the name of PC :");
		String name = scn.nextLine();
		LocalDate localDateIntro=LocalDateValidator.inputIsValidForIntroduction();
		LocalDate localDateDicounted=LocalDateValidator.inputIsValidForDiscontinued();
		System.out.println("Please give the id of company :");
		int idCompany = scn.nextInt();
		ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance();
		Company company = computerDao.getCompanyById(idCompany,conn);
		Computer computer = ComputerBuilder.newInstance().setId(idComputer).setName(name)
				.setIntroducedDate(localDateIntro).setDiscountedDate(localDateDicounted).setCompany(company).build();
		return computer;
		
	}

	public static List<Computer> computersByPage(Connection conn) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of page");
		int pageNumber = sc.nextInt();
		List<Computer> computers = null;
		try {
			computers = computerServiceImpl.getComputersByPage(pageNumber,conn);
		} catch (PageNotFoundException e) {
			System.out.println("The number of page is too high !");
		}catch(SQLException exc) {
			System.out.println(exc.getMessage());
		}
		return computers;
	}

	public static int showTheMenu() {
		System.out.println("=================================");
		System.out.println("      Excilys: CDB Application   ");
		System.out.println("=================================");
		System.out.println("1-Show the list of computers :");
		System.out.println("2-Show the list of campanies :");
		System.out.println("3-Add a computer to database :");
		System.out.println("4-Find a computer with a specific Id :");
		System.out.println("5-Update a given computer :");
		System.out.println("6-Delete a given computer :");
		System.out.println("7-Pagination functionalities :");
		System.out.println("8-Quit :");
		value = getOperationNumber();
		return value;
	}

	public static void manageCreate(Computer computer, Connection conn) {

		try {
			computerServiceImpl.createComputer(computer,conn);

		} catch (DatesNotValidException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundCompanyException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void manageUpdate(Computer computer,Connection conn) {
		try {
			computerServiceImpl.updateComputer(computer,conn);
		} catch (DatesNotValidException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundCompanyException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	

	public static List<Computer> manageAllComputers(Connection conn) {
		List<Computer> computers = null;
		computers = computerServiceImpl.getAllComputers(conn);
		return computers;

	}

	public static List<Company> manageAllCompanies(Connection conn) {
		List<Company> computers = null;
		try {
			computers = computerServiceImpl.getAllCompanies(conn);
		} catch (NoCompanyFound e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return computers;
	}

	public static Computer manageDetails(int id,Connection conn) {

		Computer computer = null;
			computer = computerDAO.getComputerDetails(id,conn);
		return computer;
	}

	public static void manageDelete(int idComputer, Connection conn) {

		try {
			computerServiceImpl.deleteComputer(idComputer,conn);
		} catch (ComputerToDeleteNotFound e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}