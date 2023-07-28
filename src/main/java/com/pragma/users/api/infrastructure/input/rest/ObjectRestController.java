package com.pragma.users.api.infrastructure.input.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/object")
@RequiredArgsConstructor
public class ObjectRestController {


//    @Operation(summary = "Add a new object")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Object created", content = @Content),
//            @ApiResponse(responseCode = "409", description = "Object already exists", content = @Content)
//    })
//    @PostMapping("/")
//    public ResponseEntity<Void> saveObject(@RequestBody ObjectRequestDto objectRequestDto) {
//        objectHandler.saveObject(objectRequestDto);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @Operation(summary = "Get all objects")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "All objects returned",
//                    content = @Content(mediaType = "application/json",
//                            array = @ArraySchema(schema = @Schema(implementation = ObjectResponseDto.class)))),
//            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
//    })
//    @GetMapping("/")
//    public ResponseEntity<List<ObjectResponseDto>> getAllObjects() {
//        return ResponseEntity.ok(objectHandler.getAllObjects());
//    }

}