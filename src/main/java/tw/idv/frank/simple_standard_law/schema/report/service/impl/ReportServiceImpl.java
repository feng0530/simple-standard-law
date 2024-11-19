package tw.idv.frank.simple_standard_law.schema.report.service.impl;


import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.mail.service.MailService;
import tw.idv.frank.simple_standard_law.common.rabbit.dto.RabbitMsg;
import tw.idv.frank.simple_standard_law.schema.report.service.ReportService;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.UsersMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailService mailService;

    @Override
    public void addTaskToReportQueue(RabbitMsg msg) {
        rabbitTemplate.convertAndSend(msg.getExchange(), msg.getRoutingKey(), msg);
    }

    @Override
    public String runIR01Test() {
        String msg = runIR01();
        mailService.sendSimpleEmail(new RabbitMsg());
        return msg;
    }

    @Override
    public String runIR01() {
        // 取得已經編譯好的jasper檔案
        ClassPathResource resource = new ClassPathResource("static/jasper/IR01/IR01.jasper");

        // 使用 try-with-resources 來自動管理資源
        try (
                FileInputStream fis = new FileInputStream(resource.getFile());
        ) {

//            List<UsersRes> usersList = usersMapper.findUsersList().stream()
//                    .map(users -> modelMapper.map(users, UsersRes.class))
//                    .collect(Collectors.toList());

            // 將報表中的parameter的名稱當作Map的key，value則放入欲傳遞的資料
            Map<String, Object> param = new HashMap<>();
            param.put("TWD-a", 1);
            param.put("TWD-b", 2);
            param.put("TWD-c", 3);
            param.put("USD-a", 4);
            param.put("USD-b", 5);
            param.put("USD-c", 6);

            // 設定要傳入報表中field的資料
//            JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(usersList);
            // 填充資料到報表內
//            JasperPrint jasperPrint = JasperFillManager.fillReport(fis, param, jrds);
            JasperPrint jasperPrint = JasperFillManager.fillReport(fis, param, new JREmptyDataSource());

            String projectPath = Paths.get("").toAbsolutePath().toString();
            String pdfStorageLocation = projectPath + "\\pdf\\IR01";
            Path pdfDir = Paths.get(pdfStorageLocation);
            if (!Files.exists(pdfDir)) {
                Files.createDirectories(pdfDir);
            }

            // 組合檔案儲存的完整路徑
            String filePath = pdfStorageLocation + "\\IR01.pdf";

            // 在指定路徑建立一個報表
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

            return CommonCode.SUCCESS.getMes();
        } catch (IOException | JRException e) {
            log.error(e.getMessage());
            return CommonCode.REPORT_ERROR.getMes();
        }
    }

    @Override
    public String getIR01() throws BaseException {
        String projectPath = Paths.get("").toAbsolutePath().toString().concat("\\pdf\\IR01\\IR01.pdf");

        try {
            File pdfFile = new File(projectPath);
            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
            return Base64.getEncoder().encodeToString(pdfBytes);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            throw new BaseException(CommonCode.ERROR_904); // 返回空的字節數組以表示錯誤
        }
    }
}
