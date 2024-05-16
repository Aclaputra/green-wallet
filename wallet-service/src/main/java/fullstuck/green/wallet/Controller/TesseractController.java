package fullstuck.green.wallet.Controller;

import fullstuck.green.wallet.Service.TesseractOCRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TesseractController {

    @Autowired
    private TesseractOCRService tesseractOCRService;

    @PostMapping("/ocr")
    public String recognizeText(@RequestParam("file") MultipartFile file) throws IOException {
        return tesseractOCRService.recognizeText(file.getInputStream());
    }

}