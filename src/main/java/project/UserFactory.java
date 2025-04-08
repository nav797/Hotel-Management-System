package project;


	interface User {
		
		 String info();
		 public String getUsername();
			public String getFirstName();
			
			public String getLastName();
			
			public String getPassword();
			
			public String getRole();		}
	
	class Guest implements User {
		private String username;
		private String firstName;
		private String lastName;
		private String password;
		
		private String role = "Guest";
		
	 public Guest(String u, String f, String l, String p) {
			username = u;
			firstName = f;
			lastName = l;
			password = p;
		}
		
		public String getUsername() {
			return username;
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public String getPassword() {
			return password;
		}
		public String getRole() {
			return role;
		}
	
	 public String info() {
	 return role+" User"+firstName+" "+lastName+" created!";
	 }
	}
	
	
	
	class Housekeeping implements User {
		private String username;
		private String firstName;
		private String lastName;
		private String password;
		
		private String role = "Housekeeping";
		
	 public Housekeeping(String u, String f, String l, String p) {
			username = u;
			firstName = f;
			lastName = l;
			password = p;
		}
		
		public String getUsername() {
			return username;
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public String getPassword() {
			return password;
		}
		public String getRole() {
			return role;
		}
	
	 public String info() {
	 return role+" User"+firstName+" "+lastName+" created!";
	 }
	}
	
	
	class Receptionist implements User {
		private String username;
		private String firstName;
		private String lastName;
		private String password;
		
		private String role = "Receptionist";
		
	 public Receptionist(String u, String f, String l, String p) {
			username = u;
			firstName = f;
			lastName = l;
			password = p;
		}
		
		public String getUsername() {
			return username;
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public String getPassword() {
			return password;
		}
		public String getRole() {
			return role;
		}
	
	 public String info() {
	 return role+" User"+firstName+" "+lastName+" created!";
	 }
	}
	
	class Admin implements User {
		private String username;
		private String firstName;
		private String lastName;
		private String password;
		
		private String role = "Admin";
		
	 public Admin(String u, String f, String l, String p) {
			username = u;
			firstName = f;
			lastName = l;
			password = p;
		}
		
		public String getUsername() {
			return username;
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public String getPassword() {
			return password;
		}
		public String getRole() {
			return role;
		}
	
	 public String info() {
	 return role+" User"+firstName+" "+lastName+" created!";
	 }
	}
	
	
	public class UserFactory {
	 public static User createUser(String u, String f, String l, String p, String r) {
	 switch (r) {
	 case "Guest":
		 return new Guest(u,f,l,p);
	 case "Housekeeping":
		 return new Housekeeping(u,f,l,p);
	 case "Receptionist":
		 return new Receptionist(u,f,l,p);
	 case "Admin":
		 return new Admin(u,f,l,p);
	 default:
		 throw new IllegalArgumentException("Unknown role: " + r);
	 }
	 }
	}

