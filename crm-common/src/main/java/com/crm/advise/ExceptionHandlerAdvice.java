package com.crm.advise;


import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    public static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    /**
     * 拦截所有的Exception
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public RestfulResponse<Void> exception(Exception exception, WebRequest request) {
        exception.printStackTrace();
        log.error("系统出错：" + exception.getMessage(), exception);
        return RestfulResponse.error(ECode.SYSTEM_UNKNOWN_EXCEPTION, exception.getMessage());
    }


    /**
     * 拦截所有的MessageException
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = MessageException.class)
    public RestfulResponse<Void> messageException(MessageException exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        return RestfulResponse.error(exception.getCode(), exception.getMessage());
    }

    /**
     * 所有注解了@RequestMapping的方法可获得此键值对
     *
     * @param model
     */
    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("msg", ""); // 这里可添加额外信息
    }

    /**
     * 定制WebDataBinder
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields(""); // 这里可指定忽略request中的参数
    }
}