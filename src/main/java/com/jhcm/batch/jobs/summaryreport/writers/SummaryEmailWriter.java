package com.jhcm.batch.jobs.summaryreport.writers;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.jhcm.batch.jobs.summaryreport.model.SummaryMediaPlaceHolder;

import freemarker.template.Configuration;

@Service
public class SummaryEmailWriter implements ItemWriter<SummaryMediaPlaceHolder>
{
    private final static Logger LOGGER = LoggerFactory.getLogger( SummaryEmailWriter.class );

    private final static String TEMPLATE_NAME = "summary_media_email.html.ftl";

    @Resource
    private Configuration configuration;

    @Override
    public void write( List<? extends SummaryMediaPlaceHolder> items ) throws Exception
    {
        for ( SummaryMediaPlaceHolder item : items ) {
            LOGGER.debug( "************* WRITER **************" );
            LOGGER.debug( item.getUsername() );
            FreeMarkerTemplateUtils.processTemplateIntoString( configuration.getTemplate( TEMPLATE_NAME ), item );
        }
    }
}
