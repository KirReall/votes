package votes.shared;

import java.io.Serializable;

public class UserAccountInfo implements Serializable{
	private Long id;
    private Integer service;
    private String uid;
    private Boolean user;
    private String firstName;
	private String lastName;
	private String photo;
    
    public static Integer UNAUTHORIZED = 0;
    public static Integer VKUSER = 1;
    public static Integer GPLUSUSER = 2;
    public static Integer FBUSER = 3;
    
    public UserAccountInfo(){
    	this.service = UNAUTHORIZED;
    	this.user = false;
    }

    public UserAccountInfo(String uid, Integer service) {
        this.uid = uid;
        this.service = service;
        this.user = false;
    }

    public Long getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

	public Integer getService() {
		return service;
	}
	
	public void setId(Long id) {
        this.id = id;
        this.user = true;
    }
	
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setService(Integer service) {
		this.service = service;
	}

	public void setUser(Boolean user) {
		this.user = user;
	}

	public Boolean isUser() {
		return user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	

}
