package com.sjsu.proxyAuth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Face")
public class Face {
    @Getter@Setter
    String name;
    @Getter@Setter
    float[] faceVector;
}
