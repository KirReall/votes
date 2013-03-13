package votes.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class CheckCaptchaServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
		//String content = req.getParameter("content");
		//Integer rate = Integer.valueOf(req.getParameter("rate"));
		//Long id = Long.valueOf(req.getParameter("id"));
		
		String remoteAddr = req.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdzccMSAAAAACP6tSD0SUds2VMrqosuPWWA9srg");//6LdzccMSAAAAACP6tSD0SUds2VMrqosuPWWA9srg//6LcN0MISAAAAAI26uaZq26Ppxy9BNw74E-_5kDHO

        String challenge = req.getParameter("recaptcha_challenge_field");
        String uresponse = req.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (reCaptchaResponse.isValid()) {
        	resp.setStatus(HttpServletResponse.SC_OK);
        } else {
        	resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,"Answer is wrong");
        }
		//resp.sendRedirect(req.getHeader("Referer"));
	}
}
