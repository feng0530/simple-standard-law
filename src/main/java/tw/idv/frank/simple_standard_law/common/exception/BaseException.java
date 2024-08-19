package tw.idv.frank.simple_standard_law.common.exception;


import lombok.Getter;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;

@Getter
public class BaseException extends Exception{

    Integer code;

    String mes;

    public BaseException(CommonCode code) {
        this.code = code.getCode();
        this.mes = code.getMes();
    }

    public BaseException(Integer code, String mes) {
        this.code = code;
        this.mes = mes;
    }
}
