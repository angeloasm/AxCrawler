
package AxCrawler;

import java.io.BufferedReader;
import java.io.IOException;


import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author AxC
 */
public class AxCrawler {
    
    private final ArrayList<String> webLinks = new ArrayList<>();;
    private final ArrayList<String> fileLinks = new ArrayList<>();;
    
    // GET FILE LINK
    private void getFileLink(String document){
        
        
        
        if(document.contains("src=")){
            String[] arr = document.split(" ");
            for (String string : arr) {
                if(string.startsWith("src=")){
                    
                    if(string.charAt(4) == '\''){
                        fileLinks.add(string.substring(5, string.lastIndexOf("'")));                       
                    }
                    
                    if(string.charAt(4) == '"'){
                        fileLinks.add(string.substring(5, string.lastIndexOf("\"")));
                    }
                    
                }
            }
        }
        
        
    }
    
    
    private String getFullString(String iElementTag){
        String link;
        if(iElementTag.charAt(5)== '\"'){

            link = iElementTag.substring(6, iElementTag.lastIndexOf("\""));

        }else{

            link = iElementTag.substring(6, iElementTag.lastIndexOf("\'"));
        }
        return link;
    }
    
    
    private void matchToLink(Matcher matchReg){
        
                
        while (matchReg.find()) {
            
            String tag = matchReg.group(1);
            
            if(tag.startsWith("a")){
                
                String[] splitElement = tag.split(" ");
                
                for(String iElementTag : splitElement){
                    
                    if(iElementTag.startsWith("href=")){
                        
                        String link;
                        
                        link = getFullString(iElementTag);
                        
                        webLinks.add(link);
                        break;
                    }
                }
            }
        }
    }
    
    // GET LINK TO ANOTHER PAGES
    private void getAnchorLink(String document){
        
        
        
        String lineOfDoc = null;
        if(!document.contains("href=")){
            return;
        }
        
        
            if(document.contains("<a")){
                
                lineOfDoc = document.trim();
            }else{
                return;
            }
        
        Pattern patReg = Pattern.compile(Pattern.quote("<") + "(.*?)" + Pattern.quote(">"));
        Matcher matchReg = patReg.matcher(lineOfDoc);
        
        matchToLink(matchReg);
        
        
    }
    
    
    // Scanning function from url
    public void scan(String URLLink) {
        
        try {
            
            URLConnection url = new URL(URLLink).openConnection();
            url.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    url.getInputStream()))) {
                String document = br.readLine();
                
                while(document!=null){
                    
                    getFileLink(document);
                    
                    getAnchorLink(document);
                    
                    document = br.readLine();
                }
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(AxCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AxCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    // Scanning function from HTTPS URL
    public void scanHttps(String HTTPSURLLink) {
        
        try {
            
            URL url = new URL(HTTPSURLLink);
            

            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()))) {
                String document = br.readLine();
                
                while(document!=null){
                    
                    getFileLink(document);
                    
                    getAnchorLink(document);
                    
                    document = br.readLine();
                }
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(AxCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AxCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    // Return WEBLinks ArrayList
    public ArrayList<String> getWebLinks(){
        return webLinks;
    }
    
    // Return FILELinks ArrayList
    public ArrayList<String> getFileLinks(){
        return fileLinks;
    }

    
    // Print all File links founded
    public void printFileLinks() {
        for(String fileLink : fileLinks){
            System.out.println("FILE LINK:"+fileLink);
        }
    }
    
    // Print all Web links founded
    public void printWebLinks() {
        for(String weblink : webLinks){
            System.out.println("WEB LINK:"+weblink);
        }
    }
    
    
}
