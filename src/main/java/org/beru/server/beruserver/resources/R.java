package org.beru.server.beruserver.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.beru.server.beruserver.Main;
import org.beru.server.beruserver.model.login.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class R {
    private final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    private final DocumentBuilder dBuilder;
    private ObjectMapper mapper = new ObjectMapper();
    private Document doc;
    private XPath xpath;
    public enum Strings{
        invalid_username,
        invalid_password
    }
    public R(){
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(Main.class.getResourceAsStream("values/strings.xml"));
            doc.getDocumentElement().normalize();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
    protected String getNodeString(String name){
        try {
            NodeList nodes = doc.getElementsByTagName("string");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Element element = (Element) node;
                if(element.getAttribute("name").equals(name)){
                    return element.getTextContent();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    protected String[] getNodeArray(String name){
        try {
            NodeList nodes = doc.getElementsByTagName("string-array");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Element element = (Element) node;
                if(element.getAttribute("name").equals(name)){
                    NodeList child = element.getElementsByTagName("item");
                    String[] values = new String[child.getLength()];
                    for (int j = 0; j <child.getLength(); j++) {
                        Node n = child.item(j);
                        values[j] = n.getTextContent();
                    }
                    return values;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    protected User getUserInCache(String name){
        File file = new File(Files.CACHE);
        try {
            TypeFactory typeFactory = mapper.getTypeFactory();
            List<User> users = mapper.readValue(file, typeFactory.constructCollectionType(List.class, User.class));
            for (User user:users){
                if(user.getName().equals(name))
                    return user;
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
    protected String getProperty(String name){
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("values/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(name);
    }
    public static class string{
        public static String invalid_username = Active.r.getNodeString("invalid_username");
        public static String invalid_password = Active.r.getNodeString("invalid_password");
        public static String application_name = Active.r.getNodeString("application_name");
        public static String invalid_host = Active.r.getNodeString("invalid_host");
        public static String invalid_port = Active.r.getNodeString("invalid_port");
        public static String login_failed = Active.r.getNodeString("login_failed");
    }
    public static class array{
        public static String[] connection_type = Active.r.getNodeArray("connection_type");
        public static String[] databases = Active.r.getNodeArray("databases");
    }
    public static class files{
        public static User user_in_cache(String name){return Active.r.getUserInCache(name);}
    }
}
