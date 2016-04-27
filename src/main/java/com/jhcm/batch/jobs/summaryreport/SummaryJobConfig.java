package com.jhcm.batch.jobs.summaryreport;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.annotation.Scheduled;

import com.jhcm.batch.jobs.shared.ProcessListener;
import com.jhcm.batch.jobs.summaryreport.model.SummaryMediaPlaceHolder;
import com.jhcm.batch.jobs.summaryreport.writers.SummaryEmailWriter;

@Configuration
@EnableBatchProcessing
public class SummaryJobConfig
{
    private final static Logger LOGGER = LoggerFactory.getLogger( SummaryJobConfig.class );

    @Resource
    private JobBuilderFactory jobBuilders;
    @Resource
    private StepBuilderFactory stepBuilders;
    @Resource(name = "apiDataSource")
    private DataSource apiDataSource;
    @Resource
    private SummaryEmailWriter summaryEmailWriter;
    @Resource
    private ProcessListener processListener;

    @Resource
    private JobLauncher jobLauncher;

    @Resource(name = "sqlProps")
    private Properties sqlProps;

    @Scheduled(cron = "0 * * * * MON-FRI")
    private void run() throws Exception
    {
        Date day = Date.valueOf( LocalDate.now());

        try {

            jobLauncher.run( weeklySummaryJob(), new JobParametersBuilder()
                .addDate( "date", day  ).toJobParameters() );

        } catch ( JobInstanceAlreadyCompleteException ex ) {
            LOGGER.info( "Job already completed for day [{}]", day );
        } catch ( DuplicateKeyException ex ) {
            LOGGER.info( "Job already started for day [{}]", day );
        }
    }

    public Job weeklySummaryJob()
    {
        return jobBuilders.get( "weeklySummaryJob" )
            .incrementer( new RunIdIncrementer() )
            .listener( processListener )
            .flow( weeklySummaryStep() )
            .end()
            .build();
    }

    @Bean
    public Step weeklySummaryStep()
    {
        return stepBuilders.get( "summaryEmailStep" )
            .<SummaryMediaPlaceHolder, SummaryMediaPlaceHolder> chunk( 1 )
            .reader( sumaryReader() )
            // .processor()
            .writer( summaryEmailWriter )
            .faultTolerant()
            .build();
    }

    @Bean
    public ItemReader<SummaryMediaPlaceHolder> sumaryReader()
    {
        JdbcCursorItemReader<SummaryMediaPlaceHolder> reader = new JdbcCursorItemReader<>();
        reader.setDataSource( apiDataSource );
        String sql = sqlProps.getProperty( "weeklyUserMedia" );

        reader.setSql( sql );
        reader.setDataSource( apiDataSource );
        reader.setRowMapper( new BeanPropertyRowMapper<SummaryMediaPlaceHolder>( SummaryMediaPlaceHolder.class ) );

        return reader;
    }

}
