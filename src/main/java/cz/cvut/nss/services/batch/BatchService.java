package cz.cvut.nss.services.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
@Service
public class BatchService {

    @Autowired
    protected JobLauncher jobLauncher;

    @Autowired
    protected Job simpleJob1;

    public void runJob() throws Exception {
        jobLauncher.run(simpleJob1, new JobParameters());
    }

}
