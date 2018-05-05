package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import entity.Account;
import entity.Address;
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
	private JFXTextField txtManagerSurname;

	@FXML
	private JFXPasswordField txtPassword;

	@FXML
	private JFXPasswordField txtPasswordAgain;

	public static PaneModel selectedHotel;

	private EntityManager entityManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entityManager = EntityManagerUtility.createEntityManager();
		Address address = getAddress(selectedHotel.getIdHotel());
		int roomCount = getRoomCount();
		int bedCount = getBedCount();
		int[] employees = getEmployeeCount();

		// kutucuklarin doldurulmasi
		txtName.setText(selectedHotel.getHotelName());
		txtProvince.setText(address.getProvince());
		txtDistrict.setText(address.getDistrict());
		txtRoomCount.setText(String.valueOf(roomCount));
		txtBedCount.setText(String.valueOf(bedCount));
		txtReceptionistCount.setText(String.valueOf(employees[0]));
		txtCleanerCount.setText(String.valueOf(employees[1]));
		txtEmployeeCount.setText(String.valueOf(employees[2]));
		// mudur var ise
		LocalManager localManager = getLocalManager();
		if (localManager != null) {
			Account localManagerAccount = getLocalManagerAccount(localManager.getIdAccount());
			txtManagerMemberName.setText(localManagerAccount.getMemberName());
			txtManagerName.setText(localManagerAccount.getFirstname());
			txtManagerSurname.setText(localManagerAccount.getLastname());
			txtManagerTC.setText(String.valueOf(localManager.getTc()));
		}

	}

	private int[] getEmployeeCount() {
		// calisan saylarinin hesaplanmasi
		int[] employees = new int[3];
		Query query = entityManager.createNativeQuery("SELECT * FROM Employee WHERE idHotel=?1", Employee.class);
		query.setParameter(1, selectedHotel.getIdHotel());
		List<Employee> result = query.getResultList();
		if (!result.isEmpty()) {
			for (Employee employee : result) {
				if (employee.getType() == "RECEPTIONIST")
					employees[0]++;
				if (employee.getType() == "CLEANER")
					employees[1]++;
			}
			employees[3] = employees[0] + employees[1];
		}
		return employees;
	}

	private int getBedCount() {
		// yatak sayisinin hesaplanmasi
		Query query = entityManager.createNativeQuery(
				"select count(idBed) as bedCount from bed INNER JOIN room r ON bed.idRoom = r.idRoom where idHotel=?1;");
		query.setParameter(1, selectedHotel.getIdHotel());
		return query.getFirstResult();
	}

	private int getRoomCount() {
		// oda sayilarinin hesaplanmasi
		Query query = entityManager.createNativeQuery("SELECT COUNT(idRoom) FROM Room WHERE idHotel=?1");
		query.setParameter(1, selectedHotel.getIdHotel());
		return query.getFirstResult();
	}

	private Account getLocalManagerAccount(int idAccount) {
		Query query = entityManager.createNativeQuery("SELECT * FROM Account WHERE idAccount=?1", Account.class);
		query.setParameter(1, idAccount);
		if (!query.getResultList().isEmpty())
			return (Account) query.getResultList().get(0);
		else
			return null;
	}

	private LocalManager getLocalManager() {
		// mudur bilgilerinin cekilmesi
		Query query = entityManager.createNativeQuery("SELECT * FROM LocalManager WHERE idHotel=?1",
				LocalManager.class);
		query.setParameter(1, selectedHotel.getIdHotel());
		if (!query.getResultList().isEmpty())
			return (LocalManager) query.getResultList().get(0);
		else
			return null;
	}

	private Address getAddress(int idHotel) {
		// adres bilgilerinin cekilmesi
		Query query = entityManager.createNativeQuery("SELECT * FROM Address WHERE idHotel=?1", Address.class);
		query.setParameter(1, idHotel);
		if (!query.getResultList().isEmpty())
			return (Address) query.getResultList().get(0);
		else
			return null;
	}

	@FXML
	void changeManager() {

	}

}
