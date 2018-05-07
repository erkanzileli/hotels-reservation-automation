package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import entity.Account;
import entity.Company;
import entity.GeneralManager;
import entity.Hotel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.CompanyTableModel;
import utility.EntityManagerUtility;

public class SystemManagerMainController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private TableView<CompanyTableModel> tableCompany;

	@FXML
	private TableColumn<?, ?> columnCompanyName;

	@FXML
	private TableColumn<?, ?> columnGeneralManagerName;

	@FXML
	private TableColumn<?, ?> columnGeneralManagerSurname;

	@FXML
	private TableColumn<?, ?> columnHotelCount;

	@FXML
	private TableColumn<?, ?> columnEmployeeCount;

	private EntityManager entityManager;

	private List<CompanyTableModel> data;

	public static JFXDialog newCompanyDialog;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entityManager = EntityManagerUtility.createEntityManager();
		columnCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
		columnGeneralManagerName.setCellValueFactory(new PropertyValueFactory<>("managerName"));
		columnGeneralManagerSurname.setCellValueFactory(new PropertyValueFactory<>("managerSurname"));
		columnHotelCount.setCellValueFactory(new PropertyValueFactory<>("hotelCount"));
		columnEmployeeCount.setCellValueFactory(new PropertyValueFactory<>("employeeCount"));

		fillTable();

		tableCompany.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				ButtonType cancelButton = new ButtonType("Ýptal", ButtonData.CANCEL_CLOSE);
				ButtonType okButton = new ButtonType("Onayla", ButtonData.OK_DONE);
				Alert alert = new Alert(AlertType.CONFIRMATION, "Bu þirkete ait tüm verileri silmek istiyor musunuz?",
						cancelButton, okButton);
				alert.headerTextProperty().set(null);
				alert.setTitle("Þirket siliniyor!");
				Optional<ButtonType> result = alert.showAndWait();
				result.ifPresent(buttonData -> {
					if (buttonData.getButtonData() == ButtonData.OK_DONE) {
						CompanyTableModel selected = tableCompany.getSelectionModel().getSelectedItem();
						Company c = entityManager.find(Company.class, selected.getIdCompany());
						GeneralManager gm = entityManager.find(GeneralManager.class, selected.getTcManager());
						Account account = entityManager.find(Account.class, gm.getIdAccount());
						entityManager.getTransaction().begin();
						entityManager.remove(c);
						entityManager.remove(account);
						entityManager.remove(gm);
						entityManager.getTransaction().commit();
						fillTable();
					}
				});
			}
		});

	}

	private void fillTable() {
		data = new ArrayList<>();
		getData();
		tableCompany.getItems().setAll(data);
	}

	private void getData() {
		// Þirketlerin çekilmesi
		Query query = entityManager.createNativeQuery("select * from company", Company.class);
		List<Company> result = query.getResultList();
		if (!result.isEmpty()) {
			for (Company c : result) {
				Query query1 = entityManager.createNativeQuery("select * from generalmanager where idCompany=?1",
						GeneralManager.class);
				query1.setParameter(1, c.getIdCompany());
				GeneralManager gm = null;
				Account gmAccount = null;
				if (!query1.getResultList().isEmpty()) {
					gm = (GeneralManager) query1.getResultList().get(0);
					Query query2 = entityManager.createNativeQuery("select * from account where idAccount=?1",
							Account.class);
					query2.setParameter(1, gm.getIdAccount());
					gmAccount = (Account) query2.getResultList().get(0);
				}

				Query query3 = entityManager.createNativeQuery("select * from hotel where idCompany=?1", Hotel.class);
				query3.setParameter(1, c.getIdCompany());
				List<Hotel> hotels = query3.getResultList();

				Query query4 = entityManager.createNativeQuery("select * from employee where idCompany=?1");
				query4.setParameter(1, c.getIdCompany());
				List employees = query4.getResultList();
				if (gm != null) {
					data.add(new CompanyTableModel(c.getIdCompany(), c.getName(), gm.getTc(), gmAccount.getFirstname(),
							gmAccount.getLastname(), hotels.size(), employees.size(), "Genel Müdür"));

				} else {
					data.add(new CompanyTableModel(c.getIdCompany(), c.getName(), 0, "", "", hotels.size(),
							employees.size(), "Genel Müdür"));
				}
			}
		}

	}

	@FXML
	void newCompany() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CreateCompany.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.TOP);
		dialog.show();
		newCompanyDialog = dialog;

		newCompanyDialog.setOnDialogClosed(e -> {
			if (!e.isConsumed()) {
				fillTable();
			}
		});

	}

	@FXML
	void profile() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/Profile.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.RIGHT);
		dialog.show();
	}

}
