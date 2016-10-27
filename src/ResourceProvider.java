import com.sun.deploy.util.StringUtils;
import com.sun.deploy.xml.XMLParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.w3c.dom.*;

import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by giacomo on 13/10/16.
 */

public class ResourceProvider extends ResourceHttpRequestHandler{

    String resourceName = "actors.xml";

    public ResourceProvider(HttpServletRequest request )
    {
        String path = request.getSession().getServletContext().getRealPath("/resources/")+resourceName;
        //System.out.println("Path actors.xml "+ path);


        Resource r = new FileSystemResource(new File(path));
        if (! r.exists())
            System.out.println("Resource not found");


        List<Resource> resources = new ArrayList<Resource>();
        resources.add(r);

        setLocations(resources);
        /*
        try{
            Resource res = r.createRelative("/../../../conf/tomcat-users.xml");

            if(res.exists())
                System.out.println("FileName " + res.getFilename());
        }
        catch (IOException ex)
        {System.out.println("Create relative error");}

        System.out.println("Resources " + getLocations());
        System.out.println("Info resources "+r.toString());
        */
    }

    public List<String> getStuff(HttpServletRequest request, HttpServletResponse response )//, @RequestParam("file") String fileName)
    {
        Resource res = null;
        /*
        MockHttpServletRequest redirectPath = new MockHttpServletRequest();
        redirectPath.setServerName("http://localhost:8080");
        redirectPath.setRequestURI("/resources");
        redirectPath.setQueryString(request.getParameter("file"));
*/
        //String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        request.setAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, request.getParameter("file"));
        System.out.println("request.getAttribute(HandlerMapping ) "+request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        System.out.println("Query string == " + request.getQueryString());
        System.out.println("isInvalidPath "+ isInvalidPath(path));

        System.out.println("cleanPath " + org.springframework.util.StringUtils.cleanPath(path));
        System.out.println("cleanPath().startsWith(..) " + org.springframework.util.StringUtils.cleanPath(path).startsWith(".."));
        System.out.println(" (! hasText) "+ ! org.springframework.util.StringUtils.hasText(path));

        //!! Vulnerable string http://localhost:8080/index.htm?file=/../../../conf/tomcat-users.xml
        List<String> actorsAndFilms = new ArrayList<String>();

        System.out.println(this.getLocations().toString());
        try {

            res = getResource(request);




            if(res != null){

                if( ! res.getFilename().equals(resourceName))
                    System.out.println("*****HACKED!******");

                System.out.println("Is readable? "+ res.isReadable());
                File f = res.getFile();
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

                try{
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(f);
                    doc.getDocumentElement().normalize();
                    Element parent = doc.getDocumentElement();
                    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                    actorsAndFilms.add(doc.getDocumentElement().getNodeName());
                    NodeList childNodes = parent.getChildNodes();

                    for(int i=0; i< childNodes.getLength(); i++)
                    {
                        System.out.println(childNodes.item(i).getNodeName()) ;
                        actorsAndFilms.add(childNodes.item(i).getNodeName());

                        NamedNodeMap n = childNodes.item(i).getAttributes();
                        if(n != null)
                        {
                            for (int j=0; j< n.getLength(); j++ )
                            {
                                actorsAndFilms.add(n.item(j).toString()); //.replace("name=", "").replace("\"", ""));
                                System.out.println(n.item(j).toString());
                            }

                        }


                    }

                }
                catch (SAXException s)
                {}
                catch (ParserConfigurationException s){}

                System.out.println(res.getFilename());}
            }

        catch (IOException r) { return actorsAndFilms;}

        System.out.println("LIST DIMENSION "+ actorsAndFilms.size());

        return actorsAndFilms;

    }


}


