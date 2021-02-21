package com.lambdaschool.librarymanager.models;

public class Signup
{
    public String token;

    public long userid;

    public Signup()
    {
    }

    public Signup(
        String token,
        long userid)
    {
        this.token = token;
        this.userid = userid;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }
}
