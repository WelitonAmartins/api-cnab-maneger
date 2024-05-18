package br.com.wel.apicnabmaneger.controller;

import br.com.wel.apicnabmaneger.service.CnabService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cnab")
public class CnabController {
    private final CnabService cnabService;

    public CnabController(CnabService cnabService) {
        this.cnabService = cnabService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file) throws IOException {
        cnabService.uploadCnabFile(file); 
        return "Processamento iniciado!";
    }
}
