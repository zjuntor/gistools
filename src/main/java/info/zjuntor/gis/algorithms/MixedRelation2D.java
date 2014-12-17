/*
 *Copyright (c) 2014 Juntao ZH<zjuntor@gmail.com>

 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:

 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.

 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 */
package info.zjuntor.gis.algorithms;

import java.lang.Math;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineSegment;



/**
 * @author juntao
 *
 */
public class MixedRelation2D {
	
	
	public static class CircleAndLine{
		public static boolean isCrossing(Coordinate center,double radius, LineSegment line){
			if(line.distance(center) > radius)
				return false;
			if(center.distance(line.p0) < radius && center.distance(line.p1) < radius)
				return false;
			return true;
		}
		
		
		/*
		 * x = x1 + (x2 - x1)t
		 * y = y1 + (y2 - y1)t
		 * using  parametric form line function to calculate crossing point, put x,y into the circle fuction (x - xp)^2  + (y - yp)^2 = r^2 to solve it.
		 */
		public static Coordinate[] getCrossingPoint(Coordinate center,double radius,  LineSegment line){
			double x1 = line.p0.x;
			double y1 = line.p0.y;
			double x2 = line.p1.x;
			double y2 = line.p1.y;
			double xp = center.x;
			double yp = center.y;
			double a = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
			double b = 2*(x1*(x2-x1) + y1*(y2-y1) - xp*(x2-x1) - yp*(y2-y1));
			double c = x1*x1 + y1*y1 + xp*xp + yp*yp - 2*(x1*xp + y1*yp) - radius * radius;
			double delta = Math.sqrt(b*b - 4*a*c);
			double t1 = (-b + delta)/(2*a);
			double t2 = (-b - delta)/(2*a);
			if(delta==0){
				Coordinate[] res = {new Coordinate(x1 + t1*(x2-x1),y1 + t1*(y2-y1))};
				if(line.distance(res[0])<=Precision.PRECISION)
					return res;
				else 
					return null;
			}else{
				Coordinate coord1 = new Coordinate(x1 + t1*(x2-x1),y1 + t1*(y2-y1));
				Coordinate coord2 = new Coordinate(x1 + t2*(x2-x1),y1 + t2*(y2-y1));
				if(line.distance(coord1)<=Precision.PRECISION && line.distance(coord2) <= Precision.PRECISION){
					Coordinate[] res = {coord1,coord2};
					return res;
				}else{
					if(line.distance(coord1)<=Precision.PRECISION){
						Coordinate[] res = {coord1};
						return res;
					}else if(line.distance(coord2)<=Precision.PRECISION){ 
						Coordinate[] res = {coord2};
						return res;
					}else{
						return null;
					}
				}
				
			}
			
		}
		
	}
}
