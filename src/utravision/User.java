package utravision;

public class User {

	private String un;
    private String pw;
    
    public User(String un, String pw){
        this.un = un;
        this.pw = pw;
    }

    public String getUn() {
        return un;
    }

    public String getPw() {
        return pw;
    }
}
