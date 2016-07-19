package Handlers;

import android.app.AlertDialog;
import android.content.Intent;

import com.example.chamod.backupalbumfinal.LoggedAccountActivity;
import com.example.chamod.backupalbumfinal.MainLogin;
import com.example.chamod.backupalbumfinal.NewAccountActivity;

import Databases.AccountDB;
import Model.User;
import Support.Utility;


/**
 * Created by Chamod on 7/5/2016.
 */
public class AccountHandler
{
    private static AccountHandler accountHandler;

    public static AccountHandler getInstance()
    {
        if(accountHandler==null)
            accountHandler=new AccountHandler();
        return accountHandler;
    }

    private NewAccountActivity newAccountActivity;
    private MainLogin mainLogin;

    private AccountDB accountDB;

    private User loggedUser;

    private AccountHandler() {


    }

    public void setMainLogin(MainLogin mainLogin) {
        this.mainLogin = mainLogin;

        accountDB=AccountDB.getInstance(mainLogin);
        accountDB.setMainLogin(mainLogin);
    }

    public void setNewAccountActivity(NewAccountActivity newAccountActivity) {
        this.newAccountActivity = newAccountActivity;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
        if(loggedUser!=null) {
            loggedUser.setLogged(true);

            //save in DB
            //accountDB.setUserLogged(loggedUser, true);
        }

    }

    public void createAccount(String name,String email,String userName,String password,String confirmPassword)
    {
        if(validate(name,email,userName,password,confirmPassword))
        {
            //save to db
            User user=new User(name,email,userName,password);
            user.setId(accountDB.addUser(user));
            logOut();
            setLoggedUser(user);

            Utility.showAlertOk(newAccountActivity,"Alert","New account was created...!");

            newAccountActivity.startActivity(new Intent(newAccountActivity,LoggedAccountActivity.class));
        }
    }

    public boolean validate(String name,String email,String userName,String password,String confirmPassword)
    {
        String msg="";
        if(name.trim().equals(""))
            msg="Please enter the name...!";
        else if(email.trim().equals(""))
            msg="Please enter the email...!";
        else if(userName.trim().equals(""))
            msg="Please enter the username...!";
        else if(accountDB.isUsernameUsed(userName.trim()))
            msg="Username is already in use, Please select another..!";
        else if(password.length()<4)
            msg="Password is too short, Please enter at least 4 characters...!";
        else if(!password.equals(confirmPassword))
            msg="Passwords don't match...!";
        if(!msg.equals(""))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(newAccountActivity);
            builder.setMessage(msg);
            builder.setTitle("Alert");
            builder.create();
            builder.show();
            return false;
        }
        return true;
    }

    public void login(String username , String password)
    {
        boolean success=false;
        if(username.length()!=0 && password.length()!=0) {
            User tempUser=accountDB.login(username,password);
            if(tempUser!=null) {
                success=true;
                logOut();
                setLoggedUser(tempUser);
                Intent newAccountIntent = new Intent(mainLogin, LoggedAccountActivity.class);
                mainLogin.startActivity(newAccountIntent);
            }
        }
        if(!success)Utility.showAlertOk(mainLogin,"Alert","Login Failed...!");

    }

    public  boolean logOut()
    {
        if(loggedUser!=null) {
            accountDB.logOut(loggedUser);
            setLoggedUser(null);
        }
        return true;
    }

    public boolean checkForLoggedUser()
    {
        setLoggedUser(accountDB.getLoggedUser());
        if(getLoggedUser()==null)return false;
        return true;
    }


}
