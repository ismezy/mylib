package com.zy.mylib.base.exception;

public class BusException extends RuntimeException {
	public final static String ERROR_CODE = "300";
	public final static String SUCCESS_CODE = "0000";
	private String code = SUCCESS_CODE;
    private int httpStatus = 500;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5058689238804568643L;
	
	public BusException() {}
	public BusException(String message){
		super(message);
		this.setCode(ERROR_CODE);
	}
	public BusException(Throwable cause){
		super(cause);
		this.setCode(ERROR_CODE);
	}
	public BusException(String message, Throwable cause){
		super(message, cause);
		this.setCode(ERROR_CODE);
	}
	public BusException(String message, String code){
		super(message);
		this.setCode(code);
	}
	public BusException(String message, String code, Throwable cause){
		super(message, cause);
		this.setCode(code);
	}
    public BusException(String message, String code, Integer httpStatus){
        super(message);
        this.setCode(code);
        this.setHttpStatus(httpStatus);
    }
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

    /**
     * Sets new httpStatus.
     *
     * @param httpStatus New value of httpStatus.
     */
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * Gets httpStatus.
     *
     * @return Value of httpStatus.
     */
    public int getHttpStatus() {
        return httpStatus;
    }
}
