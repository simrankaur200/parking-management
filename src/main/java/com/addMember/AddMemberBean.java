package com.addMember;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.memberLogin.Member;



@ManagedBean(name = "addMemberBean")
@ApplicationScoped
public class AddMemberBean {
	private List<Member> memberList = new ArrayList<>();
    private String companyName;
    private String email;
    private String address;
    private String password;

    private String message;
    private boolean formSubmitted = false;
    
    //Feield level error flags
    private boolean companyNameError;
    private boolean emailError;
    private boolean addressError;
    private boolean passwordError;
    

    
    public List<Member> getMemberList(){
    	return memberList;
    }

//    public void setMemberList(ArrayList<AddMemberBean> memberList) {
//    	this.memberList =memberList;
//    }
    
    
    // Getters and Setters
    public String getCompanyName() {
    	return companyName;
    	}
    public void setCompanyName(String companyName) { 
    	this.companyName = companyName;
    	}

    public String getEmail() {
    	return email; 
    	}
    public void setEmail(String email) { 
    	this.email = email; 
    	}



    public String getAddress() { 
    	return address;
    	}
    public void setAddress(String address) { 
    	this.address = address; 
    	}

   

    public String getPassword() {
    	return password;
    	}
    public void setPassword(String password) { 
    	this.password = password;
    	}

   
    public String getMessage() {
    	return message;
    	}
    public void setMessage(String message) {
    	this.message = message;
    	}
    
    public boolean isFormSubmitted() {
        return formSubmitted;
    }
  
    
   

    public boolean isCompanyNameError() {
		return companyNameError;
	}

	public void setCompanyNameError(boolean companyNameError) {
		this.companyNameError = companyNameError;
	}

	public boolean isEmailError() {
		return emailError;
	}

	public void setEmailError(boolean emailError) {
		this.emailError = emailError;
	}

	public boolean isAddressError() {
		return addressError;
	}

	public void setAddressError(boolean addressError) {
		this.addressError = addressError;
	}

	public boolean isPasswordError() {
		return passwordError;
	}

	public void setPasswordError(boolean passwordError) {
		this.passwordError = passwordError;
	}

	// Action method
    public String addMember() {
    	formSubmitted = true;
    	
    	FacesContext context = FacesContext.getCurrentInstance();
    	
    	//reset errors
    	companyNameError = false;
    	emailError = false;
    	addressError =false;
    	passwordError = false;
    	message = null;
    	
    	boolean allEmpty = isEmpty(companyName) && isEmpty(email) && isEmpty(address) && isEmpty(password);

    	if (allEmpty) {
            message = "All fields are required";
            return null;
        }

        if (isEmpty(companyName)) companyNameError = true;
        if (isEmpty(email)) emailError = true;
        if (isEmpty(address)) addressError = true;
        if (isEmpty(password)) passwordError = true;

        if (companyNameError || emailError || addressError || passwordError) {
            return null;
        }

        Member newMember = new Member();
        newMember.setCompanyName(companyName);
        newMember.setEmail(email);
        newMember.setAddress(address);
        newMember.setPassword(password);
        newMember.setRegistrationDate(LocalDate.now());

        memberList.add(newMember);

        message = "Company added successfully";
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", message));
        clearForm();
        return null;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void clearForm() {
        companyName = "";
        email = "";
        address = "";
        password = "";
    }
}
//    	//basic validatrion
//        if (companyName == null ||companyName.trim().isEmpty() || email == null ||email.trim().isEmpty()|| address == null ||address.trim().isEmpty()||  password == null ||password.trim().isEmpty()) {
//        	
//        		  message = "All fields are required";//failure message
//            return null; // stay on the same page
//        }
//        
//        // create and add new member
//        Member newMember = new Member();
//        newMember.setCompanyName(companyName);
//        newMember.setEmail(email);
//        newMember.setAddress(address);
//        newMember.setPassword(password);
//        newMember.setRegistrationDate(LocalDate.now());
//
//        	memberList.add(newMember);
//
//        //  success message
//        message = "Company added successfully";
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Company added successfully"));
//       
//       clearForm(); // resets the form fields
//       // stay on the same page 
//   return null;
//        
//    }
//
//	private void clearForm() {
//		companyName ="";
//		email ="";
//		address = "";
//		password = "";
//		
//	}
//    
//    
//}
