package se.yrgo.deb.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class OutputHandler {
    private static final String JSON = "application/json";
    private static final String XML = "application/xml";
    private static final String TEXT_XML = "text/xml";

    private static final List<String> ACCEPTABLE_MIMES = List.of(JSON, XML, TEXT_XML);

    private OutputHandler() {}

    public static void writeFormatted(Object o, HttpServletRequest req, HttpServletResponse res) throws IOException, JAXBException {
        Optional<String> maybeMime = getPreferredMime(req);
        if (maybeMime.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
        else if (maybeMime.get().contains(JSON)) {
            outputJSON(o, res);
        }
        else {
            outputXML(o, res);
        }
    }

    private static void outputXML(Object o, HttpServletResponse res) throws JAXBException, IOException {
        res.setContentType(XML);
        try (OutputStream os = res.getOutputStream()) {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(o, os);
        }
    }

    private static void outputJSON(Object o, HttpServletResponse res) throws IOException {
        res.setContentType(JSON);
        try (OutputStream os = res.getOutputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(mapper.getTypeFactory());
            mapper.setAnnotationIntrospector(introspector);
            String result = mapper.writeValueAsString(o);
            os.write(result.getBytes());
        }
    }

    private static Optional<String> getPreferredMime(HttpServletRequest req) {
        String acceptValue = req.getHeader("Accept");
        List<String> types = MimeTypeParser.parse(acceptValue);
        return types.stream().filter(ACCEPTABLE_MIMES::contains).findFirst();
    }
}
