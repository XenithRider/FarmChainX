package com.farmchain.farmchain.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders; // ✅ Correct import for Spring headers
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class AiService {

    // Initialize RestTemplate for making HTTP requests
    private final RestTemplate restTemplate = new RestTemplate();

    // URL of the local AI prediction service
    private final String url = "http://localhost:5000/predict";

    /**
     * Sends an image to the AI service and retrieves the predicted quality.
     *
     * @param imagePath Absolute path to the image file
     * @return Map containing prediction results from the AI service
     */
    public Map<String, Object> predictQuality(String imagePath) {
        try {
            // Wrap the image file as a resource for multipart upload
            FileSystemResource resource = new FileSystemResource(imagePath);

            // Prepare the multipart form data body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", resource); // 'image' is the expected key by the Flask API

            // Set headers to indicate multipart/form-data content
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Combine body and headers into a single HTTP entity
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

            // Send POST request to the AI service and receive response
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            // Return the parsed response body (prediction results)
            return response.getBody();

        } catch (Exception e) {
            // Wrap and rethrow any exceptions with a custom message
            throw new RuntimeException("AI Service call failed: " + e.getMessage(), e);
        }
    }
}