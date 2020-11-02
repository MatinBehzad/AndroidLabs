

package com.example.androidlabs;


public class Message  {

    private String message;
    private boolean isSend;
    protected long id;

    public Message(){

    }

    public Message(String message, boolean isSend, long i){
        this.message=message;
        this.isSend=isSend;
        this.id=i;
    }

    public Message(String n, boolean e) {
        this(n, e, 0);
    }


    public void update(String n, boolean e)
    {
        this.message= n;
        this.isSend = e;
    }

    public String getMessage(){
        return message;
    }

    public boolean isSendMessage(){
        return isSend;
    }

    public long getId() { return id; }

    public String setMessage(String message){
        return this.message=message;
    }

    public void isSendMessage(boolean isSend){
        this.isSend=isSend;
    }

    public void setId(long id) { this.id=id; }
}

