package votes.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import votes.shared.UserAccountInfo;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserAccount {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent 
    private Integer service;
    
    @Persistent
    private String uid;
    
    @Persistent
    private String firstName;
    
    @Persistent
	private String lastName;
    
    @Persistent
	private String photo;

    public UserAccount(){
    	
    }

    public UserAccount(String uid, Integer service) {
        this.uid = uid;
        this.service = service;
    }
    
    public UserAccount(UserAccountInfo user) {
    	this.uid = user.getUid();
    	this.service = user.getService();
    	this.firstName = user.getFirstName();
    	this.lastName = user.getLastName();
    	this.photo = user.getPhoto();
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
	
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setService(Integer service) {
		this.service = service;
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

	public boolean correspondsTo(UserAccountInfo user) {
		if (user.getFirstName().equals(getFirstName())&&user.getLastName().equals(getLastName())&&user.getPhoto().equals(getPhoto())){
			return true;
		}
		return false;
	}

	public void correspond(UserAccountInfo user) {
		this.firstName = user.getFirstName();
    	this.lastName = user.getLastName();
    	this.photo = user.getPhoto();
		
	}
	
	public UserAccountInfo createUserAccountInfo(){
		UserAccountInfo user = new UserAccountInfo(getUid(), getService());
		user.setId(getId());
		user.setUser(true);
		user.setFirstName(getFirstName());
		user.setLastName(getLastName());
		user.setPhoto(getPhoto());
		return user;
	}

}
