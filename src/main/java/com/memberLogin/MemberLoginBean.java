package com.memberLogin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "memberLoginBean")
@SessionScoped
public class MemberLoginBean {
    private String username;
    private String password;
    private Member member = null;;
    
    private List<Member> memberList = new ArrayList<>();
    
    
    
    
    public MemberLoginBean() {
    	
    	//dummies members
    	memberList.add( new Member("TestMember", "member@email.com", "MemberPass123!"));
    	memberList.add( new Member("John", "john@email.com", "JohnPass123@"));
    	memberList.add( new Member("Jack", "jack@email.com", "JackPass@123"));
    	
    }
    
   

    public String getUsername() {
    	return username; 
    	}
    public void setUsername(String username) {
    	this.username = username; 
    	}

    public String getPassword() {
    	return password;
    	}
    public void setPassword(String password) { 
    	this.password = password; 
    	}
   
    public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	
		
//login method validation
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        
     
        //Empty fielsd check
        if (username == null || username.trim().isEmpty() ||
        	    password == null || password.trim().isEmpty()) {
        		context.addMessage("loginForm:username", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in all required fields", null));
        	    return null;
        	}

        //Silent email format check (no error message shown)
        if(!username.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
        	//just return null silently
        	return null;
        }
        
        //Credential check
        for (Member m : memberList) {
            if (m.getEmail().equals(username) && m.getPassword().equals(password)) {
                this.member = m;
                context.addMessage("loginForm:username", new FacesMessage("Login successful"));
                return "memberDashboard.xhtml?faces-redirect=true";
            }
        }

        context.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", null));
        return null;
    }
}