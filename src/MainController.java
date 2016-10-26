
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.core.io.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;


/**
 * Created by giacomo on 21/10/16.
 */
@org.springframework.stereotype.Controller
public class MainController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        //System.out.println("HttpServletRequest request == "+ request.getQueryString());
        System.out.println("Called handleRequest - returning the view");

        ResourceProvider resourceProvider = new ResourceProvider(request);


        Resource r= resourceProvider.getStuff(request,response);

        if (r != null)
        {
            System.out.println(r.getURL().getPath());
            // TODO: CREATE A VIEW  ---> return new ModelAndView(r.getURL().getPath());
            return new ModelAndView("hacked.jsp");
        }

        else
            return new ModelAndView("hello.jsp");
    }

    /*
    @RequestMapping(value="/{file}", method=RequestMethod.GET)
    public ModelAndView resources(@PathVariable String file, HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("Called resouces! for file "+ file);
        return new ModelAndView("res.jsp");
    }
    */


}
