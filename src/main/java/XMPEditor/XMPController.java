/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMPEditor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.apache.commons.imaging.formats.jpeg.xmp.JpegXmpRewriter;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author C. Levallois
 */
public class XMPController {

    public static void main(String args[]) throws ImageReadException, IOException, ImageWriteException, URISyntaxException {
//        readXMP("https://ckannet-storage.commondatastorage.googleapis.com/2013-01-16T133458/newfile.jpg");
    }

    public Map<String, String> readXMP(String urlString) throws ImageReadException, IOException, ImageWriteException, URISyntaxException {

        System.out.println("urlString: " + urlString);
        URL url = new URL(urlString);

        InputStream in = url.openStream();
//        File file = new File("D:\\Docs Pro Clement\\NESSHI\\Project ICONOGRAPHY\\Guardian pdfs\\Docs Dutch Team\\NRC\\1987\\test.JPG");
//        File file = new File("C:\\Users\\C. Levallois\\Desktop\\newfile.jpg");
//        byte[] bytes = IOUtils.toByteArray(new FileReader(file));
        byte[] bytes = IOUtils.toByteArray(in);
        System.out.println("bytes: " + bytes);
        ByteArrayOutputStream updatedBaos = new ByteArrayOutputStream();

        IImageMetadata metadata = Imaging.getMetadata(bytes, null);
        System.out.println("metadata: " + metadata);
        String xmpXml = Imaging.getXmpXml(bytes, null);
        System.out.println("xmpXml from the orig pic: " + xmpXml);

        if (xmpXml == null) {
            xmpXml = new XMPCreator().create();
            xmpXml = new VRAWriter().write(xmpXml);
            xmpXml = new XMPCloser().close(xmpXml);
        } else {
//            xmpXml = new XMPCreator().create(); // to be deleted after debugging
            xmpXml = new VRAWriter().write(xmpXml);
            xmpXml = new XMPCloser().close(xmpXml);
        }
        System.out.println("xmpXml with VRA fields" + xmpXml);

        XMPParser1 xmlParser = new XMPParser1(xmpXml);
        Map<String, String> mapProperties = xmlParser.parse();

        JpegXmpRewriter xmpWriter = new JpegXmpRewriter();
        //File newfile = new File("./img/newfile.jpg"); //output file
        //xmpWriter.updateXmpXml(new ByteSourceFile(file), new BufferedOutputStream(new FileOutputStream(newfile)), xmpXml);    

        //**************
        //the following lines are commented out but functional and VERY important!!
        //**************

        //the only thing is that I don't need to write to a baos at the moment, just testing the reading
//        xmpWriter.updateXmpXml(new ByteSourceFile(file), updatedBaos, xmpXml);
//
//        InputStream decodedInput = new ByteArrayInputStream(((ByteArrayOutputStream) updatedBaos).toByteArray());
//        try {
//
//            // write the inputStream to a FileOutputStream
//            OutputStream out = new FileOutputStream(new File("C:\\Users\\C. Levallois\\Desktop\\newfile.jpg"));
//
//            int read = 0;
//            bytes = new byte[1024];
//
//            while ((read = decodedInput.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//
//            decodedInput.close();
//            out.flush();
//            out.close();
//
//            System.out.println("New file created!");
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        return mapProperties;

    }
}
