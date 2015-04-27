import java.util.*;

class Point {
  double x, y;
  
  /**Create a point at the specified coordinates
   * @param x The x coordinate
   * @param y The y coordinate*/
  Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  /**Create a point at the same place as p
   * @param p A point*/
  Point(Point p) {
    this.x = p.x;
    this.y = p.y;
  }
  
  /**@param v A vector
   * @return This point moved by the v*/
  Point move(Vector v) {
    return new Point(x+v.x, y+v.y);
  }
  
  /**@param p An other point
   * @return The distance between this point and p*/
  double dist(Point p) {
    return Math.hypot(x-p.x, y-p.y);
  }
  
  /**@param l An unbounded line
   * @return The distance between this point and l*/
  double dist(Line l) {
    return l.dist(this);
  }
  
  /**@param l A line segment
   * @return The distance between this point and l*/
  double dist(Segment l) {
    return l.dist(this);
  }
  
  /**@param polygon A set of line segments defining a polygon
   * @return The distance between this point and the polygon, 0 if this point is inside the polygon*/
  double dist(Collection<Segment> polygon) {
    double dist = Math.sqrt(Math.sqrt(Double.MAX_VALUE)); // Maximum distance we can have to point
    Segment line = new Segment(x, y, dist, dist);
    int cross = 0;
    for(Segment l : polygon) {
      if(l.intersects(line))
        ++cross;
      dist = Math.min(dist, dist(l));
    }
    if(cross%2 == 0)
      return dist;
    return 0;
  }
  
  /**@param p Another point
   * @return Whether this point equals p*/
  boolean equals(Point p) {
    return x == p.x && y == p.y;
  }
  
  /**@return A string representation of the point*/
  @Override
  public String toString() {
    return "("+x+", "+y+")";
  }
  
  /**@param p1 A point
   * @param p2 A point
   * @param p3 A point
   * @return The area of the triangle defined by the 3 points*/
  static double area(Point p1, Point p2, Point p3) {
    return Math.abs(new Vector(p1, p2).cross(new Vector(p1, p3)))/2;
  }
  
  /**A Point Comparator comparing first on x coordinate, and then on y coordinate if the x coordinates are equal*/
  static class Compare implements Comparator<Point> {
    @Override
    public int compare(Point o1, Point o2) {
      if(o1.x == o2.x)
        return Double.compare(o1.y, o2.y);
      return Double.compare(o1.x, o2.x);
    }
  }
}

class Vector {
  static double precision = 0.0000001;
  double x, y;
  
  /**Create a vector going from origo to the specified coordinates
   * @param x The x coordinate
   * @param y The y coordinate*/
  Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }
  /**Create a vector going from origo to p
   * @param p A point*/
  Vector(Point p) {
    x = p.x;
    y = p.y;
  }
  /**Create a vector going from one point to another
   * @param from A point
   * @param to A point*/
  Vector(Point from, Point to) {
    x = to.x-from.x;
    y = to.y-from.y;
  }
  
  /**@return The length of this vector*/
  double length() {
    return Math.hypot(x, y);
  }

  /**@param v Another vector
   * @return The cross product. Positive if this points to the right of v*/
  double cross(Vector v) {
    return x*v.y - y*v.x;
  }
  
  /**@param v Another vector
   * @return The dot product*/
  double dot(Vector v) {
    return x*v.x + y*v.y;
  }
  
  /**@return The vector added to v component-wise*/
  Vector add(Vector v) {
    return new Vector(x+v.x, y+v.y);
  }
  
  /**@return The vector multiplied by c component-wise*/
  Vector mult(double c) {
    return new Vector(x*c, y*c);
  }
  
  /**@param v Another vector
   * @return Whether this vector is parallel to v*/
  boolean isParallel(Vector v) {
    return Math.abs(cross(v)) < precision;
  }
  
  /**@return The angle between this vector and the x-axis in the range [0..2pi)*/
  double angle() {
    double a = Math.atan2(y, x);
    if(a < 0)
      a += 2*Math.PI;
    return a;
  }
  
  /**@param v Another vector
   * @return The angle between this vector and v in the range [0..2pi)*/
  double angle(Vector v) {
    double a = v.angle()-angle();
    if(a < 0)
      a += 2*Math.PI;
    return a;
  }
  
  /**@return A normal vector to this vector*/
  Vector normal() {
    return new Vector(-y, x);
  }
  
  /**@return A normalized version of this vector (a vector of length 1)*/
  Vector normalize() {
    double l = length();
    return new Vector(x/l, y/l);
  }
  
  /**@param v Another vector
   * @return Whether this vector equals v*/
  boolean equals(Vector v) {
    return x == v.x && y == v.y;
  }
  
  /**@return A string representation of the vector*/
  @Override
  public String toString() {
    return "<"+x+", "+y+">";
  }
}

/**Class representing a line segment*/
class Segment {
  static double precision = 0.0000001;
  Point s, e;
  Vector v;
  
  /**Create a line segment going through the specified coordinates
   * @param sx The first x coordinate
   * @param sy The first y coordinate
   * @param ex The second x coordinate
   * @param ey The second y coordinate*/
  Segment(double sx, double sy, double ex, double ey) {
    this.s = new Point(sx, sy);
    this.e = new Point(ex, ey);
    this.v = new Vector(s, e);
  }
  /**Create a line segment going through the specified points
   * @param s The first point
   * @param e The second point*/
  Segment(Point s, Point e) {
    this.s = new Point(s);
    this.e = new Point(e);
    this.v = new Vector(s, e);
  }
  
  /**@return The length of this line segment*/
  double length() {
    return s.dist(e);
  }
  
  /**@return An unbounded line that coincides with this line segment*/
  Line unbounded() {
    return new Line(s.x, s.y, e.x, e.y);
  }
  
  /**@param p A point
   * @return The distance between this line segment and p*/
  double dist(Point p) {
    return p.dist(closest(p));
  }
  
  /**@param l Another line segment
   * @return The distance between this line segment and l*/
  double dist(Segment l) {
    Point p = new Point(0, 0);
    int k = unbounded().intersection(l.unbounded(), p);
    if(k == 1 && dist(p) < precision && l.dist(p) < precision)
      return 0;
    return Math.min(Math.min(dist(l.s), dist(l.e)), Math.min(l.dist(s), l.dist(e)));
  }
  
  /**@param p A point
   * @return The point on this line segment closest to p*/
  Point closest(Point p) {
    if(hasPerpendicular(p))
      return unbounded().closest(p);
    if(p.dist(s) < p.dist(e))
      return new Point(s);
    return new Point(e);
  }
  
  /**@param l Another line segment
   * @return Whether this line segment intersects l*/
  boolean intersects(Segment l) {
    // l intersects with the unbounded line coinciding with this line segment
    if(v.cross(new Vector(s, l.s)) * v.cross(new Vector(s, l.e)) > precision)
      return false;
    Vector lv = new Vector(l.s, l.e);
    // this line segment intersects with the unbounded line coinciding with l
    if(lv.cross(new Vector(l.s, s)) * lv.cross(new Vector(l.s, e)) > precision)
      return false;
    return true;
  }
  
  /**@param l Another line segment
   * @return The point on the intersection between this line segment and l. null if there is no such point*/
  Point intersection(Segment l) {
    if(!intersects(l))
      return null;
    Point p1 = l.unbounded().closest(s);
    Point p2 = l.unbounded().closest(e);
    double d1 = s.dist(p1);
    double d2 = e.dist(p2);
    Vector v = new Vector(p1).mult(d2/(d1+d2)).add(new Vector(p2).mult(d1/(d1+d2)));
    return new Point(v.x, v.y);
  }
  
  /**@param l Another line segment
   * @return Whether this line segment is parallel to l*/
  boolean isParallel(Segment l) {
    return v.isParallel(l.v);
  }
  
  /**@param p A point
   * @return Whether there is a perpendicular line going from this line segment through p*/
  boolean hasPerpendicular(Point p) {
    return v.dot(new Vector(s, p)) >= 0 && new Vector(e, s).dot(new Vector(e, p)) >= 0;
  }
  
  /**@return A line segment perpendicular to this line segment going through the starting point of this line segment*/
  Segment perpendicularStart() {
    return new Segment(s, s.move(v.normal()));
  }
  
  /**@return A line segment perpendicular to this line segment going through the ending point of this line segment*/
  Segment perpendicularEnd() {
    return new Segment(e, e.move(v.normal()));
  }
  
  /**@param l Another line segment
   * @return Whether this line segment equals l*/
  boolean equals(Segment l) {
    return (s.equals(l.s) && e.equals(l.e)) || (s.equals(l.e) && e.equals(l.s));
  }
  
  /**@return A string representation of the line segment*/
  @Override
  public String toString() {
    return s.toString()+" - "+e.toString();
  }
}

/**Class representing an unbounded line*/
class Line {
  static double precision = 0.0000001;
  Point s, e;
  Vector v;
  
  /**Create an unbounded line going through the specified coordinates
   * @param sx The first x coordinate
   * @param sy The first y coordinate
   * @param ex The second x coordinate
   * @param ey The second y coordinate*/
  Line(double sx, double sy, double ex, double ey) {
    this.s = new Point(sx, sy);
    this.e = new Point(ex, ey);
    this.v = new Vector(s, e);
  }
  /**Create an unbounded line going through the specified points
   * @param s The first point
   * @param e The second point*/
  Line(Point s, Point e) {
    this.s = new Point(s);
    this.e = new Point(e);
    this.v = new Vector(s, e);
  }
  
  /**@param p A point
   * @return The distance between this unbounded line and p*/
  double dist(Point p) {
    return 2*Point.area(p, s, e)/s.dist(e);
  }
  
  /**@param p A point
   * @return The point on this unbounded line closest to p*/
  Point closest(Point p) {
    return s.move(v.mult(v.dot(new Vector(s, p))/v.dot(v)));
  }
  
  /**@param l Another unbounded line
   * @param p A point to be populated with the intersection if exactly one exists
   * @return 1 if there is one solution, 0 if the lines coincide (inf solutions), -1 if the lines are distinct and parallel (no solutions)*/
  int intersection(Line l, Point p) {
    double c = v.cross(l.v);
    double d = v.cross(new Vector(l.s, s));
    if(Math.abs(c) < precision) {
      if(Math.abs(d) < precision)
        return 0;
      return -1;
    }
    double a = new Vector(s).cross(new Vector(e));
    double b = new Vector(l.s).cross(new Vector(l.e));
    p.x = (b*v.x - a*l.v.x)/c;
    p.y = (b*v.y - a*l.v.y)/c;
    
    return 1;
  }
  
  /**@param l Another unbounded line
   * @return Whether this unbounded line is parallel to l*/
  boolean isParallel(Line l) {
    return v.isParallel(l.v);
  }
  
  /**@param p A point
   * @return -1 if p is to the left of this line, 1 if p is to the right of this line, 0 if p is on this line*/
  int sideOf(Point p) {
    double a = v.cross(new Vector(s, p));
    if(a > 0)
      return -1;
    if(a < 0)
      return 1;
    return 0;
  }
}
