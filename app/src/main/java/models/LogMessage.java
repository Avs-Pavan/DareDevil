package models;

public class LogMessage {
    private String type;
    private String jsonData;

    public LogMessage(String type, String jsonData) {
        this.type = type;
        this.jsonData = jsonData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
