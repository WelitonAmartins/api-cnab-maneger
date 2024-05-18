package br.com.wel.apicnabmaneger.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CnabService {
    private final Path fileStoreLocation;

    public CnabService(@Value("${file.upload-dir}") String fileUploadDir) {
        this.fileStoreLocation = Paths.get(fileUploadDir);
    }

    public void uploadCnabFile(MultipartFile file) throws IOException {
        var fileName = StringUtils.cleanPath(file.getOriginalFilename());
        var targetLocation = fileStoreLocation.resolve(fileName);
        file.transferTo(targetLocation);
    }
}
