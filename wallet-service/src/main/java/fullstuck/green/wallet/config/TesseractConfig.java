package fullstuck.green.wallet.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {
    @Bean
    Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/wallet-service/src/main/resources/tessdata");
        // Files of the example : https://github.com/tesseract-ocr/tessdata
        return tesseract;
    }
}
