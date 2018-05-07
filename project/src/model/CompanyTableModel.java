package model;

public class CompanyTableModel {

	private int idCompany;
	private String companyName;
	private long tcManager;
	private String managerName;
	private String managerSurname;
	private String generalManager;
	private int hotelCount;
	private int employeeCount;

	public CompanyTableModel() {

	}

	public CompanyTableModel(int idCompany,String companyName, long tcManager,String gName, String gSurname, int hotelCount, int employeeCount,
			String generalManager) {
		this.setIdCompany(idCompany);
		this.setCompanyName(companyName);
		this.setTcManager(tcManager);
		this.setHotelCount(hotelCount);
		this.setEmployeeCount(employeeCount);
		this.setManagerName(gName);
		this.setManagerSurname(gSurname);
		this.setGeneralManager(generalManager);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getHotelCount() {
		return hotelCount;
	}

	public void setHotelCount(int hotelCount) {
		this.hotelCount = hotelCount;
	}

	public int getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(int employeeCount) {
		this.employeeCount = employeeCount;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerSurname() {
		return managerSurname;
	}

	public void setManagerSurname(String managerSurname) {
		this.managerSurname = managerSurname;
	}

	public String getGeneralManager() {
		return generalManager;
	}

	public void setGeneralManager(String generalManager) {
		this.generalManager = generalManager;
	}

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

	public long getTcManager() {
		return tcManager;
	}

	public void setTcManager(long tcManager) {
		this.tcManager = tcManager;
	}

}
