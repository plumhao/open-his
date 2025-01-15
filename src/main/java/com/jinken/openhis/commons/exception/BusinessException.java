package com.jinken.openhis.commons.exception;

import com.jinken.openhis.commons.enums.ErrorCodeAndMessageEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {


    private static final long serialVersionUID = -4743819198578737692L;


    //增加一个特定的状态码，以区分是发生哪种错误
    private Integer code;//如果出现错误抛出的错误码属性

    /**
     * 有参数构造函数，程序业务逻辑一旦出现业务异常，就创建业务异常对象，并抛出这个异常
     * @param code
     * @param message
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(ErrorCodeAndMessageEnum errorCodeAndMessageEnum) {
        super(errorCodeAndMessageEnum.getMessage());
        this.code = errorCodeAndMessageEnum.getCode();
    }

}
