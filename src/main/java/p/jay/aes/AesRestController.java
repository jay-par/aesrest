package p.jay.aes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AesRestController {
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public String getDatetime() {
    	DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEEEEE yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
        return "It is " + dateFormat.format(date) + " now.";
    }
    
    @RequestMapping(value = "/encrypt", method = RequestMethod.GET)
    public String encrypt(@RequestParam(value="key", required=true) String key,
    		@RequestParam(value="message", required=true) String msg) {
    	
		try {
			return Encrypter.encrypt(key, msg);
		} catch (Exception e) {
			// TODO Exception handling ControllerAdvice
			e.printStackTrace();
		}
		return "Issues happened";
    }
    
    @RequestMapping(value = "/decrypt", method = RequestMethod.GET)
    public String decrypt(@RequestParam(value="key", required=true) String key,
    		@RequestParam(value="iv", required=true) String iv,
    		@RequestParam(value="message", required=true) String msg) {
    	
		try {
			return Encrypter.decrypt(key, iv, msg);
		} catch (Exception e) {
			// TODO Exception handling. ControllerAdvice
			e.printStackTrace();
		}
		return "Issues happened";
    }
}
