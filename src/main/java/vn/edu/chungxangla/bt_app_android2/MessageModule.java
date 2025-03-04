package vn.edu.chungxangla.bt_app_android2;

public class MessageModule {
    private int id;
    private int userId;
    private String title;
    private String body;

    public MessageModule(int id, int uid, String td, String bd){
        this.id=id;
        this.userId=uid;
        this.title=td;
        this.body=bd;
    }
    public MessageModule(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
