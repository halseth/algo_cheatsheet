class Point3D {
  double x, y, z;
  Point3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**@param p An other point
   * @return The distance between this point and p*/
  double dist(Point3D p) {
    return Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y) + (z-p.z)*(z-p.z));
  }

  /**@param l A bounded line
   * @return The distance between this point and l*/
  double dist(Line3D l) {
    return l.dist(this);
  }

  /**@param l A line
   * @param infiniteLine Whether l should be regarded as unbounded
   * @return The distance between this point and l*/
  double dist(Line3D l, boolean infiniteLine) {
    return l.dist(this, infiniteLine);
  }
}

class Line3D {
  static final double precision = 0.0000001;
  
  Point3D s;
  Point3D e;
  Line3D(int sx, int sy, int sz, int ex, int ey, int ez) {
    this.s = new Point3D(sx, sy, sz);
    this.e = new Point3D(ex, ey, ez);
  }

  /**@param p A point
   * @return The distance between this bounded line and p*/
  double dist(Point3D p) {
    return dist(p, false);
  }

  /**@param p A point
   * @param infiniteLine Whether this line should be regarded as unbounded
   * @return The distance between this line and p*/
  double dist(Point3D p, boolean infiniteLine) {
    Point3D v = new Point3D(e.x-s.x, e.y-s.y, e.z-s.z);
    Point3D w = new Point3D(p.x-s.x, p.y-s.y, p.z-s.z);
    double c1 = v.x*w.x + v.y*w.y + v.z*w.z;
    double c2 = v.x*v.x + v.y*v.y + v.z*v.z;
    double b = c1/c2;
    if(infiniteLine && c1 < 0) // s closest
      return p.dist(s);
    if(infiniteLine && c2 <= c1) // e closest
      return p.dist(e);
    Point3D n = new Point3D(s.x+v.x*b, s.y+v.y*b, s.z+v.z*b);
    return p.dist(n);
  }

  /**@param l An other bounded line
   * @return The distance between this bounded line and l*/
  double dist(Line3D l) {
    double lo = 0;
    double hi = 1;
    Point3D d = new Point3D(e.x-s.x, e.y-s.y, e.z-s.z);
    while(hi-lo > precision) {
      double m1 = lo+(hi-lo)/3;
      double m2 = hi-(hi-lo)/3;
      if(l.dist(new Point3D(s.x+d.x*m1, s.y+d.y*m1, s.z+d.z*m1)) < l.dist(new Point3D(s.x+d.x*m2, s.y+d.y*m2, s.z+d.z*m2)))
        hi = m2;
      else
        lo = m1;
    }
    return l.dist(new Point3D(s.x+d.x*(lo+hi)/2, s.y+d.y*(lo+hi)/2, s.z+d.z*(lo+hi)/2));
  }
}
