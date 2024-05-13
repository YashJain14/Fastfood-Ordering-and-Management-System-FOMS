
package users;
/**
Represents a staff member of the fast-food restaurant.
Each staff member has attributes such as staff ID, name, role (Admin, Manager, or Staff), gender, age, branch ID, and password. The class also provides methods for password management and accessing staff details.
*/
public class Staff extends User{
	private String password = "password"; 
	private String staffId;
	private int branchId; 
	private int age; 
	private String name; 
	private char gender;
	private char role; 
	

	public Staff(String staffId, String name, char role, char gender, int age, int branchId){
		this.name = name;
		this.staffId = staffId;
		this.role = role;
		this.gender = gender;
		this.age = age;
		this.branchId = branchId;

	}

    public boolean checkPassword(String passwordInput) {
        return passwordInput.equals(password); //equals returns boolean values
    }

	public void setPassword(String newPassword) {
		this.password = newPassword; 
	}

	public String getStaffId() {
		return this.staffId;
	}
	public int getBranchId() {
		return this.branchId;
	}

	public void setBranchId(int newId){
		this.branchId = newId;
	}

	public String getPassword(){
		return this.password;
	}

	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public char getGender() {
		return this.gender;
	}
	
	public void setGender(char gender) {
		this.gender = gender;
	}
	
	public char getRole() {
		return this.role;
	}
	
	public void setRole(char role) {
		this.role = role;
	}

	public void setId(String newId){
		this.staffId = newId;
	}
	
}
