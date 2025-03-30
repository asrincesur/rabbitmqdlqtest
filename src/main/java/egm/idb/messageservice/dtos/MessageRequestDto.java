package egm.idb.messageservice.dtos;


import java.io.Serializable;

public class MessageRequestDto implements Serializable {

    String phoneNum;
    String message;

    public MessageRequestDto(String telNo, String message) {
        this.phoneNum = telNo;
        this.message = message;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageRequestDto() {
    }

    @Override
    public String toString() {
        return "MessageRequestDto{" +
                "phoneNum='" + phoneNum + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
