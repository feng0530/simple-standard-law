package tw.idv.frank.simple_standard_law.schema.report.service;

import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.rabbit.dto.RabbitMsg;

public interface ReportService {
    void addTaskToReportQueue(RabbitMsg msg);

    String runIR01Test();

    String runIR01();

    String getIR01() throws BaseException;
}
