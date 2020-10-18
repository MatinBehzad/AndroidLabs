

package com.example.androidlabs;
import androidx.appcompat.app.AppCompatActivity;
public class Message extends AppCompatActivity {

    private String message;
    private boolean isSend;

    public Message(){

    }

    public Message(String message, boolean isSend){
        this.message=message;
        this.isSend=isSend;
    }
    public String getMessage(){
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSendMessage(){
        return isSend;
    }
}
