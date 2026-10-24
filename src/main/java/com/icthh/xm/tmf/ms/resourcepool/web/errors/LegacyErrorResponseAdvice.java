package com.icthh.xm.tmf.ms.resourcepool.web.errors;

import com.icthh.xm.commons.i18n.error.domain.vm.ErrorVM;
import com.icthh.xm.commons.i18n.error.domain.vm.FieldErrorVM;
import com.icthh.xm.commons.i18n.error.web.ExceptionTranslator;
import com.icthh.xm.commons.i18n.spring.service.LocalizationMessageService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Keeps error responses the service had before the migration (Spring 5, xm-commons 2), ahead of the xm-commons
 * {@link ExceptionTranslator}:
 * <ul>
 *     <li>an unmapped path: Spring 6.1+ throws {@link NoResourceFoundException} / {@link NoHandlerFoundException},
 *     which the xm-commons translator turned into 500 {@code error.internalServerError}; before, the servlet
 *     container answered 404</li>
 *     <li>a missing request parameter: the {@code fieldErrors[].message} keeps the Spring 5 text
 *     ("Required String parameter 'name' is not present")</li>
 *     <li>a 5xx from an upstream call (e.g. {@code lepContext.templates.rest} in a LEP): xm-commons 5 answers 500
 *     {@code error.internalServerError}, xm-commons 2 answered with the upstream status and {@code error.<status>}</li>
 * </ul>
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LegacyErrorResponseAdvice {

    private static final String ERROR_PREFIX = "error.";

    private final ExceptionTranslator exceptionTranslator;
    private final LocalizationMessageService localizationMessageService;

    public LegacyErrorResponseAdvice(ExceptionTranslator exceptionTranslator,
                                     LocalizationMessageService localizationMessageService) {
        this.exceptionTranslator = exceptionTranslator;
        this.localizationMessageService = localizationMessageService;
    }

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    public void processNoResourceFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<FieldErrorVM> processMissingParameter(MissingServletRequestParameterException ex) {
        MissingServletRequestParameterException legacy =
            new MissingServletRequestParameterException(ex.getParameterName(), ex.getParameterType()) {
                @Override
                public String getMessage() {
                    return "Required " + getParameterType() + " parameter '" + getParameterName() + "' is not present";
                }
            };
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exceptionTranslator.processMissingServletRequestParameterError(legacy));
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorVM> processHttpServerError(HttpServerErrorException ex) {
        int status = ex.getStatusCode().value();
        String code = ERROR_PREFIX + status;
        return ResponseEntity.status(status).body(new ErrorVM(code, localizationMessageService.getMessage(code)));
    }
}
