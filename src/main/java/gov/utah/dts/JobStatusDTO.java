package gov.utah.dts;

import org.springframework.batch.core.JobExecution;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jpjensen on 3/28/16.
 * Data transfer object for serializing JobStatuses to JSON
 */

public class JobStatusDTO {
    private String name;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
    private LocalDateTime lastUpdateTime;
    private boolean isRunning;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public JobStatusDTO() {
        this.name = "";
        this.isRunning = false;
        this.status = "N/A";
        Calendar calendar = getZeroCalendar();

        this.createTime = LocalDateTime.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());
        this.endTime = LocalDateTime.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());
        this.lastUpdateTime = LocalDateTime.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());
    }

    public JobStatusDTO (JobExecution jobExecution, String jobName) {
        this.name = jobName;
        this.status = jobExecution.getStatus().name();
        this.createTime = LocalDateTime.ofInstant(jobExecution.getCreateTime().toInstant(), ZoneId.systemDefault());
        Date tempTime;

        if(jobExecution.getEndTime() == null) {
            tempTime = getZeroCalendar().getTime();
        } else {
            tempTime = jobExecution.getEndTime();
        }

        this.endTime = LocalDateTime.ofInstant(tempTime.toInstant(), ZoneId.systemDefault());
        this.lastUpdateTime = LocalDateTime.ofInstant(jobExecution.getLastUpdated().toInstant(), ZoneId.systemDefault());
        this.isRunning = jobExecution.isRunning();

    }

    private Calendar getZeroCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);

        return calendar;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getCreateTime() {
        return createTime.format(formatter);
    }

    public String getEndTime() {
        return endTime.format(formatter);
    }

    public String getLastUpdateTime() {
        return lastUpdateTime.format(formatter);
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public String toString() {
        return "JobStatusDTO{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", isRunning=" + isRunning +
                '}';
    }
}
