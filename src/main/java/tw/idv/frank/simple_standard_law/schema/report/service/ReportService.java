package tw.idv.frank.simple_standard_law.schema.report.service;

import jakarta.servlet.http.HttpServletResponse;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;

public interface ReportService {
    void runIR01(HttpServletResponse response) throws BaseException;

    String getIR01() throws BaseException;
}
