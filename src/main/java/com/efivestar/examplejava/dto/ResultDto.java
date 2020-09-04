package com.efivestar.examplejava.dto;

import com.efivestar.examplejava.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private Boolean success;
    private String message;
    private String code;
    private Object data;

    public static ResultDto success() {
        return ResultDto.builder()
                .success(true)
                .code(ResultCode.SUCCESS.getCode())
                .message("")
                .build();
    }
    public static ResultDto success(Object data) {
        return ResultDto.builder()
                .data(data)
                .success(true)
                .code(ResultCode.SUCCESS.getCode())
                .message("")
                .build();
    }

    public static ResultDto error(String message){
        return ResultDto.builder()
                .success(false)
                .code(ResultCode.SERVER_ERROR.getCode())
                .message(message)
                .build();
    }

    public static ResultDto error(ResultCode code, String message){
        return ResultDto.builder()
                .success(false)
                .code(code.getCode())
                .message(message)
                .build();
    }
}