/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Q2.Client;
import org.jpos.iso.ISOException;                
import org.jpos.iso.ISOMsg;                      
import org.jpos.iso.ISOUtil;                     
import org.jpos.iso.channel.BASE24Channel;       
import org.jpos.iso.channel.BASE24TCPChannel;    
import org.jpos.iso.packager.GenericPackager;    
import org.jpos.util.LogSource;                  
import org.jpos.util.Logger;                     
import org.jpos.util.SimpleLogListener;          
                                                 
import java.time.LocalDateTime;                  
import java.time.format.DateTimeFormatter;       
import java.util.Random;                         
import java.util.concurrent.ThreadLocalRandom;   

public class App {
    public String getGreeting() {
        int port = 8000;
        String host = "localhost";
        GenericPackager packager;
        
        try{
            ISOMsg m = new ISOMsg();
            m.setMTI("0200");
            m.set("4", "210000");
            packager = new GenericPackager("src/main/resources/base24.xml");
            BASE24Channel channel = new BASE24Channel(host, port, packager);
            channel.setHeader("ISO025000005".getBytes());
            channel.connect();
            channel.send(m);
            ISOMsg r = channel.receive();
            System.out.println(r.getString("37"));
            System.out.println(r.getString("36")+" "+r.getString("34"));
            channel.disconnect();
            return "Mensaje Exitoso";
        } catch (Exception e) {
            System.out.println("Receive Error IO ====> " + e.getMessage());
            e.printStackTrace();
        }
        return "Mensaje Fallido";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}