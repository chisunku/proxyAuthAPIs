package com.sjsu.proxyAuth;

import com.sjsu.proxyAuth.model.Location;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import java.util.List;

public class PolygonChecker {

    public static boolean isPointInsidePolygon(Location.Point point, List<Location.Point> boundaryPoints) {
        // Create a JTS Polygon from the list of boundary points
        Polygon polygon = createPolygon(boundaryPoints);

        // Check if the point is inside the polygon
        return checkPointInsidePolygon(point, polygon);
    }

    private static Polygon createPolygon(List<Location.Point> boundaryPoints) {
        // Create a JTS GeometryFactory
        GeometryFactory geometryFactory = new GeometryFactory();

        // Convert latitude and longitude points to JTS Coordinates
        Coordinate[] coordinates = new Coordinate[boundaryPoints.size() + 1];
        for (int i = 0; i < boundaryPoints.size(); i++) {
            Location.Point point = boundaryPoints.get(i);
            coordinates[i] = new Coordinate(point.getLongitude(), point.getLatitude());
        }

        // Ensure closure by adding the first point at the end
        coordinates[coordinates.length - 1] = coordinates[0];

        // Create a JTS Polygon
        return geometryFactory.createPolygon(coordinates);
    }

    private static boolean checkPointInsidePolygon(Location.Point point, Polygon polygon) {
        // Create a JTS GeometryFactory
        GeometryFactory geometryFactory = new GeometryFactory();

        // Create a JTS Point for the check point
        Coordinate checkPoint = new Coordinate(point.getLongitude(), point.getLatitude());
        org.locationtech.jts.geom.Point jtsPoint = geometryFactory.createPoint(checkPoint);

        System.out.println("polygon: "+ polygon);
        System.out.println("point: "+jtsPoint);

        // Check if the point is inside the polygon
        return polygon.contains(jtsPoint);
    }
}