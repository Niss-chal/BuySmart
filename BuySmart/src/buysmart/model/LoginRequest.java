/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buysmart.model;

/**
 *
 * @author batas
 */
public class LoginRequest {
   private String email;
   private String password;
   
   public LoginRequest(String email,String password){
       this.email=email;
       this.password=password;
   }
    //getter
   public String getemail(){
       return email;
   }
    public String getpassword(){
        return password;
    }
    //setter
    public void setemail(String email){
        this.email=email;
    }
    public void setpassword(String password){
        this.password=password;
    }
}
