package br.com.wel.apicnabmaneger.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CnabService {
    private final Path fileStoreLocation;
    private final Job job;
    private final JobLauncher jobLauncher;

    public CnabService(@Value("${file.upload-dir}") String fileUploadDir, Job job, @Qualifier("JobLauncherAsync") JobLauncher jobLauncher) {
        this.fileStoreLocation = Paths.get(fileUploadDir);
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    public void uploadCnabFile(MultipartFile file) throws IOException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        var fileName = StringUtils.cleanPath(file.getOriginalFilename());
        var targetLocation = fileStoreLocation.resolve(fileName);
        file.transferTo(targetLocation);

        var JobParameters = new JobParametersBuilder()
                .addJobParameter("cnab", file.getOriginalFilename(), String.class, true)
                .addJobParameter("cnabFile", "file:" + targetLocation.toString(), String.class)
                .toJobParameters();

        jobLauncher.run(job, JobParameters);
    }
}
