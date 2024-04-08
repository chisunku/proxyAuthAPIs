package com.sjsu.proxyAuth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FaceModel")
public class FaceModel {
    @Getter@Setter
    String name;
    @Getter@Setter
    float[] faceVector;
}
