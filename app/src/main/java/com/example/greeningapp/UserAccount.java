package com.example.greeningapp;

/** 사용자 계정 정보 클래스
 *
 */
public class UserAccount {
    private String idToken;  //Firebase Uid(고유 토큰정보)
    private String emailId;  //이메일 아이디
    private String password; //비밀번호
    private String regdate; //날짜

    public UserAccount() { }

    public String getIdToken() { return idToken; }

    public void setIdToken(String idToken)
    {
        this.idToken = idToken;
    }

    public String getEmailId ()
    {
        return  emailId;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRegdate() { return regdate; }
    public void setRegdate(String regdate) { this.regdate = regdate; }
}
