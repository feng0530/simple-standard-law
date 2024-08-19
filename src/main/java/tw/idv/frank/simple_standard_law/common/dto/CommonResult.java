package tw.idv.frank.simple_standard_law.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;

@Data
@AllArgsConstructor
public class CommonResult<T> {

    private Integer code;

    private String msg;

    private T result;

    public CommonResult() {
        this.code = CommonCode.SUCCESS.getCode();
        this.msg = CommonCode.SUCCESS.getMes();
    }

    public CommonResult(T result) {
        this();
        this.result = result;
    }

    public CommonResult(CommonCode commonCode) {
        this.code = commonCode.getCode();
        this.msg = commonCode.getMes();
    }

    public CommonResult(CommonCode commonCode, T result) {
        this(commonCode);
        this.result = result;
    }

    public CommonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
