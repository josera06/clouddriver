
package com.udacity.jwdnd.course1.cloudstorage;



public class ApplicationTestMain {

    public static void main(String[] args) throws InterruptedException, Exception {
        String user1 = "josera";
        String user2 = "juan";
        ApplicationTest test = new ApplicationTest("firefox");
        test.createUser(user1);
        test.userLoginLogout(user1);
        test.closeDriver();
    }
}
