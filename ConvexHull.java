import java.util.*;

class ConvexHull {
  /**Calculates which points are part of the convex hull
   * Returns the points in counter-clockwise order
   * Requires: Point, Vector (Geometry2D)
   * O(n log n)
   * @param points The set of points
   * @return The points that are part of the convex hull*/
  static ArrayList<Point> convexHull(List<Point> points) {
    Collections.sort(points, new Point.Compare());
    
    Deque<Point> lower = halfConvexHull(points);
    Collections.reverse(points);
    Deque<Point> upper = halfConvexHull(points);
    lower.removeLast();
    upper.removeLast();
    
    ArrayList<Point> hull = new ArrayList();
    hull.addAll(lower);
    hull.addAll(upper);
    
    return hull;
  }
  
  /**Calculates either the upper of lower part of the convex hull
   * @param points The ordered set of points
   * @return The set of points belonging to the same vertical part of the convex hull as the first point*/
  private static Deque<Point> halfConvexHull(Collection<Point> points) {
    Deque<Point> hull = new ArrayDeque();
    for(Point p : points) {
      Point last = hull.pollLast();
      while(hull.size() >= 2 && new Vector(last, hull.getLast()).cross(new Vector(hull.getLast(), p)) >= 0)
        last = hull.pollLast();
      if(last != null)
        hull.add(last);
      hull.add(p);
    }
    return hull;
  }
}
