package com.briant.utssmt2;

public class UploadUser {
        public String username,password;
        public UploadUser() {
        }

        public UploadUser(String username,String password){
            this.username = username;
            this.password = password;
        }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
