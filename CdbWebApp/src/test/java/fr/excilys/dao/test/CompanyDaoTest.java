package fr.excilys.dao.test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.excilys.db.daoImp.ComputerDaoImpl;
import fr.excilys.db.model.Company;
import fr.excilys.db.servlet.SpringConfiguration;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
public class CompanyDaoTest {
	@Autowired
	ComputerDaoImpl computerDaoImpl;
	@Before
	public void beforeTestAllComputers() {
		
	}
	@After
	public void afterAllComputer() {
		computerDaoImpl=null;
	}
	@Test
	public void testAllCompanies() {
		List<Company> companies=computerDaoImpl.getAllyCompanies();
		assertEquals(9,companies.size());
	}

}
