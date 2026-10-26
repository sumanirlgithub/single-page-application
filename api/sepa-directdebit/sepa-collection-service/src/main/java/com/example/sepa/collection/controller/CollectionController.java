package com.example.sepa.collection.controller;

import com.example.sepa.collection.dto.request.CreateCollectionRequest;
import com.example.sepa.collection.dto.response.CollectionResponse;
import com.example.sepa.collection.service.CollectionService;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(
            CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping(value = "/submit")
    public ResponseEntity<CollectionResponse> createCollection(
            @Valid @RequestBody CreateCollectionRequest request) {

        CollectionResponse response =
                collectionService.createCollection(request);

        URI location = URI.create(
                "/api/v1/collections/submit" + response.collectionId()
        );

        return ResponseEntity
                .created(location)
                .body(response);
    }
}