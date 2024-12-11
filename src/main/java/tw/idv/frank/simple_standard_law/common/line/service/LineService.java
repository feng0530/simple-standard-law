package tw.idv.frank.simple_standard_law.common.line.service;

import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.line.dto.LineRes;

public interface LineService {

    String getOAuthUrl();

    LineRes getLineToken(String code) throws BaseException;

    LineRes sendPicture(String token) throws BaseException;
}
