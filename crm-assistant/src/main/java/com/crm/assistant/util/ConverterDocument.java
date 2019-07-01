package com.crm.assistant.util;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.sun.star.awt.Size;
 
public class ConverterDocument extends StreamOpenOfficeDocumentConverter  {
    public ConverterDocument(OpenOfficeConnection connection) {
        super(connection);
    }
 
    public final static Size A5, A4, A3;
    public final static Size B4, B5, B6;
    public final static Size KaoqinReport;
 
    static {
        A5 = new Size(14800, 21000);
        A4 = new Size(21000, 29700);
        A3 = new Size(29700, 42000);
 
        B4 = new Size(25000, 35300);
        B5 = new Size(17600, 25000);
        B6 = new Size(12500, 17600);
 
        KaoqinReport = new Size(29700, 27940);  //最大限度  宽 1600000
    }
}