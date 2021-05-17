package vending_machine;

public class ModelResponse {

    public boolean success;
    public String reason = null;
    public Object data = null;

    public ModelResponse(boolean success) {
        this.success = success;
    }

    public ModelResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public ModelResponse(boolean success, String reason, Object data) {
        this.success = success;
        this.reason = reason;
        this.data = data;
    }
    
}