package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXTextField;

import entity.Account;
import entity.Adress;
import entity.Employee;
import entity.LocalManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.PaneModel;
import utility.EntityManagerUtility;

public class HotelDetailsController implements Initializable {

	@FXML
	private JFXTextField txtProvince;

	@FXML
	private JFXTextField txtDistrict;

	@FXML
	private JFXTextField txtName;

	@FXML
	private JFXTextField txtPoint;

	@FXML
	private JFXTextField txtRoomCount;

	@FXML
	private JFXTextField txtBedCount;

	@FXML
	private JFXTextField txtEmployeeCount;

	@FXML
	private JFXTextField txtReceptionistCount;

	@FXML
	private JFXTextField txtCleanerCount;

	@FXML
	private JFXTextField txtManagerName;

	@FXML
	private JFXTextField txtManagerMemberName;

	@FXML
	private JFXTextField txtManagerTC;

	@FXML
	private JFXTextField txtManagerCount;

	public static PaneModel selectedHotel;

	private EntityManager entityManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entityManager = EntityManagerUtility.createEntityManager();
		// adres bilgilerinin cekilmesi
		Query query = entityManager.createNativeQuery("SELECT * FROM Adress WHERE idHotel=?1", Adress.class);
		query.setParameter(1, selectedHotel.getIdHotel());
		Adress hotelAdress = (Adress) query.getSingleResult();
		// mudur bilgilerinin cekilmesi
		query = entityManager.createNativeQuery("SELECT * FROM LocalManager WHERE idHotel=?1", LocalManager.class);
		query.setParameter(1, selectedHotel.getIdHotel());
		LocalManager hotelLocalManager = (LocalManager) query.getSingleResult();

		query = entityManager.createNativeQuery("SELECT * FROM Account WHERE idAccount=?1", Account.class);
		query.setParameter(1, hotelLocalManager.getIdAccount());
		Account localManagerAccount = (Account) query.getSingleResult();
		//oda sayilarinin hesaplanmasi
		query=entityManager.createNativeQuery("SELECT COUNT(*) FROM Room WHERE idHotel=?1");
		query.setParameter(1, selectedHotel.getIdHotel());
		String roomCount = (String) query.getSingleResult();
		// yatak sayisinin hesaplanmasi
		query = entityManager.createNativeQuery("SELECT COUNT(*) FROM Bed WHERE idHotel=?1");
		//String bedCount = (String) query.getSingleResult();
		// calisan saylarinin hesaplanmasi
		query = entityManager.createNativeQuery("SELECT * FROM Employee WHERE idHotel=?1", Employee.class);
		query.setParameter(1, selectedHotel.getIdHotel());
		List<Employee> employees = query.getResultList();
		int receptionistCount = 0;
		int employeeCount = 0;
		int cleanerCount = 0;
		int totalEmployeeCount;
		if (!employees.isEmpty()) {
			for (Employee employee : employees) {
				if (employee.getType() == "RECEPTIONIST")
					receptionistCount++;
				if (employee.getType() == "CLEANER")
					cleanerCount++;
				if (employee.getType() == "EMPLOYEE")
					employeeCount++;
			}
			totalEmployeeCount = employees.size();
		}
		// textfield'lara yazma
		
	}

	@FXML
	void changeManager() {

	}

}
