package myApp.CT_Accesorios;

/**
 * The MyBusinessException wraps all checked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages and to link to our online documentation.
 * 
 * @author TJanssen
 */
public class MyMtsReposException extends Exception {
    private static final long serialVersionUID = 7718828512143293558L;
    private final ErrorCode code;
    public MyMtsReposException(ErrorCode code) {
        super();
        this.code = code;
    }
    public MyMtsReposException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }
    public MyMtsReposException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
    public MyMtsReposException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
    }
    public ErrorCode getCode() {
        return this.code;
    }
}