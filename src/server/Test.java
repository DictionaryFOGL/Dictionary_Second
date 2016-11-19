package server;

import application.model.User;

public class Test {
	public static void change(Object obj) {
		User user=(User) obj;
		user.setGender('M');
	}
	public static void main(String[] args) {
		User newUser=new User("ahaha", "12345");
		newUser.addNewFriend(new User("admin", "23333"));
		System.out.println(newUser.getGender());
		change(newUser);
		System.out.println(newUser.getGender());
	}
}
