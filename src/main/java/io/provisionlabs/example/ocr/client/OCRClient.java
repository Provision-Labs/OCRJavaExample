package io.provisionlabs.example.ocr.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class OCRClient {
    static Logger log = LoggerFactory.getLogger( OCRClient.class );
    public static final String USER_AGENT_DEFAULT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36";

    private String processUrlTemplate;
    RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion( JsonInclude.Include.NON_NULL);

    public OCRClient( String processUrlTemplate ) {
        this.restTemplate = new RestTemplate();
        this.processUrlTemplate = processUrlTemplate;
    }

    public OCRClient( RestTemplate restTemplate,  String processUrlTemplate ) {
        this.restTemplate = restTemplate;
    }

    public boolean isHealthy() {
        String healtUrl = this.processUrlTemplate+"/health";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(USER_AGENT, USER_AGENT_DEFAULT);

        log.info("Request GET to " + healtUrl);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange( healtUrl, HttpMethod.GET, entity, String.class );

        String ret = response.getBody();
        try {
            Map<String, Object> obj = objectMapper.readValue( ret, Map.class );
            String status = (String)obj.get("status");
            if ( "ok".equals(status) ) {
                return true;
            }
            else {
                log.error("Status error: " + status);
                return false;
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
            log.error( "",e );
            return false;
        }
    }

    public String process( byte[] fileBytes, String templateDoc ) throws IOException {
        String url = processUrlTemplate.replace("{template}", templateDoc);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setAcceptCharset( Collections.singletonList( StandardCharsets.UTF_8 ) );
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36");

        HttpEntity<byte[]> entity = new HttpEntity<>(fileBytes, headers);
        log.info("Request to " + url);

        ResponseEntity<String> response = restTemplate.postForEntity( url, entity, String.class );
        String json = response.getBody();

        return json;
    }

}