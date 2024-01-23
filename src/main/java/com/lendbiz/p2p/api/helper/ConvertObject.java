package com.lendbiz.p2p.api.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertObject {

//    ObjectMapper objectMapper = new ObjectMapper();
//
//    private JsonNode createJsonTree(String jsonString) {
//        return new ObjectMapper().valueToTree(jsonString);
//    }
//
//    private void convertStringsToTree(JsonNode node) {
//        if (node.isObject()) {
//            node.fields().forEachRemaining(entry -> {
//                if (entry.getValue().isTextual()) {
//                    // Convert String to JsonNode tree
//                    entry.setValue(createJsonTree(entry.getValue().asText()));
//                } else {
//                    // Recursively traverse the structure
//                    convertStringsToTree(entry.getValue());
//                }
//            });
//        } else if (node.isArray()) {
//            node.elements().forEachRemaining(JsonStringToTreeConverter::convertStringsToTree);
//        }
//    }
}
