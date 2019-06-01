package com.px.share.service;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import com.px.share.entity.Param;
import com.px.share.util.HibernateUtil;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.Query;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Peach
 * Example from : http://krams915.blogspot.com/2011/02/spring-3-dynamicjasper-hibernate.html
 */
public class DynamicJasperReportService {
    private static final Logger LOG = Logger.getLogger(DynamicJasperReportService.class.getName());
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public DynamicJasperReportService() {
        sessionFactory = HibernateUtil.getSESSION_FACTORY();
    }
    
    public void exportToXls(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        // Create a JRXlsExporter instance
        JRXlsExporter exporter = new JRXlsExporter();

        // Here we assign the parameters jp and baos to the exporter
        //exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        //exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.setExporterInput(new SimpleExporterInput(jp));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

        // Excel specific parameters
        // Check the Jasper (not DynamicJasper) docs for a description of these settings. Most are 
        // self-documenting
        //exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        //exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        //exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setWhitePageBackground(false);
        //configuration.setDetectCellType(true);
        //configuration.setCollapseRowSpan(false);
        exporter.setConfiguration(configuration);

        // Retrieve the exported report in XLS format
        exporter.exportReport();
    }
    
    public void exportToXlsx(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        // Create a JRXlsExporter instance
        JRXlsxExporter exporter = new JRXlsxExporter();

        // Here we assign the parameters jp and baos to the exporter
        //exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        //exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.setExporterInput(new SimpleExporterInput(jp));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

        // Excel specific parameters
        // Check the Jasper (not DynamicJasper) docs for a description of these settings. Most are 
        // self-documenting
        //exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        //exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        //exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setWhitePageBackground(false);
        //configuration.setDetectCellType(true);
        //configuration.setCollapseRowSpan(false);
        exporter.setConfiguration(configuration);

        // Retrieve the exported report in XLS format
        exporter.exportReport();
    }

    public void exportToPdf(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        JasperExportManager.exportReportToPdfStream(jp, baos);
    }
    
    public void exportToDocx(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        JRDocxExporter exporter = new JRDocxExporter();
        
        exporter.setExporterInput(new SimpleExporterInput(jp));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        
        SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
        
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
    
    
    
    //-----------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public ByteArrayOutputStream example(String exportType) throws ColumnBuilderException, ClassNotFoundException, JRException {
        // 1. Build the report layout
        DynamicReport dr = buildReportLayout();

        // 2. Retrieve the datasource
        JRDataSource ds = getDatasource();

        // 3. Compile the report layout
        JasperReport jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), null);

        // 4. Generate the JasperPrint object which also fills the report with data
        JasperPrint jp = JasperFillManager.fillReport(jr, null, ds);

        // We can also combine compilation (3) and generation (4) in a single line
        //JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), params);

        // Create the output byte stream where the data will be written
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 5. Export to XLS format
        switch (exportType) {
            case "XLS":
                exportToXls(jp, baos);
                break;
            case "XLSX":
                exportToXlsx(jp, baos);
                break;
            case "DOCX":
                exportToDocx(jp, baos);
                break;
            default:
                exportToPdf(jp, baos);
                break;
        }

        return baos;
    }
    
    
    /**
    * Retrieves a JRDataSource from a Hibernate HQL query. 
    * This datasource is a Java List wrapper.
    * @return
    */
    @SuppressWarnings("unchecked")
    private JRDataSource getDatasource() {

        // Retrieve session
        Session session = sessionFactory.getCurrentSession();
        if (session.getTransaction() == null || !session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        // Create query for retrieving products
        Query query = session.createQuery("FROM Param");
        // Execute query
        List<Param> result = query.getResultList();
        
        // Get from service
        ParamService paramService = new ParamService();
        result = paramService.listAll("", "");

        // Wrap the collection in a JRBeanCollectionDataSource
        // This is one of the collections that Jasper understands
        JRDataSource ds = new JRBeanCollectionDataSource(result);
        
        session.close();
        // Return the datasource
        return ds;
   }
    
    public static DynamicReport buildReportLayout() throws ColumnBuilderException, ClassNotFoundException {
        // Create an instance of FastReportBuilder
        FastReportBuilder drb = new FastReportBuilder();

        // Create columns
        // The column fields must match the name and type of the properties in your datasource
        drb.addColumn("Id", "id",  Integer.class.getName(), 50)
              .addColumn("Param Name", "paramName", String.class.getName(), 100)
              .addColumn("Param Value", "paramValue" , String.class.getName(), 100)
              .setPrintColumnNames(true)

               // Disables pagination
              .setIgnorePagination(true)

              // Experiment with this numbers to see the effect
              .setMargins(0, 0, 0, 0) 

              // Set the title shown inside the Excel file
              .setTitle("Param Report") 

              // Set the subtitle shown inside the Excel file
              .setSubtitle("This report was generated at " + new Date()) 

        // Set to true if you want to constrain your report within the page boundaries
        // For longer reports, set it to false
              .setUseFullPageWidth(true);

        // Set the name of the file
              //drb.setReportName("Sales Report");

        // Build the report layout. It doesn't have any data yet!
        DynamicReport dr = drb.build();

        // Return the layout
        return dr;
   }
}
