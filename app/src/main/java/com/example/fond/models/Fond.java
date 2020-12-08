package com.example.fond.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Fond")
public class Fond extends ParseObject {
    public static final String KEY_USER = "userId";
    public static final String KEY_POST = "postId";

    public ParseUser getUser(){return getParseUser(KEY_USER);}
    public void setUser(ParseUser user) {put(KEY_USER, user);}

    public UserPost getPost(){return (UserPost) getParseObject(KEY_POST);}
    public void setPost(UserPost post) {put(KEY_POST, post);}
}