package tw.idv.frank.simple_standard_law.schema.report.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.schema.report.service.ReportService;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.UsersMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void runIR01(HttpServletResponse response) throws BaseException {
        // 取得已經編譯好的jasper檔案
        ClassPathResource resource = new ClassPathResource("static/report/IR01/IR01.jasper");

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

//            // 設定回應的內容類型為PDF
//            response.setContentType("application/pdf");
//            // 設定回應的標頭，提示用戶端進行下載
//            response.setHeader("Content-Disposition", "inline; filename=IR01.pdf");
            // 在指定路徑建立一個報表
            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/static/report/IR01/IR01.pdf");

        } catch (IOException | JRException e) {
            // 處理異常，例如記錄日誌或回傳錯誤訊息
            e.printStackTrace();
            throw new BaseException(CommonCode.REPORT_ERROR);
        }
    }

    @Override
    public String getIR01() throws BaseException {
        String filePath = "src/main/resources/static/report/IR01/IR01.pdf";
        File pdfFile = new File(filePath);

        try {
            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
            return Base64.getEncoder().encodeToString(pdfBytes);
        } catch (IOException e) {
            e.printStackTrace(); // 你可以根據需要處理異常
            throw new BaseException(CommonCode.ERROR_904); // 返回空的字節數組以表示錯誤
        }
    }
}
