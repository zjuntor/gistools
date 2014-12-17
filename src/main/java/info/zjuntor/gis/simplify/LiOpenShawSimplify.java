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
package info.zjuntor.gis.simplify;

import info.zjuntor.gis.algorithms.MixedRelation2D;

import java.util.ArrayList;
import java.util.List;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LineString;

/**
 * @author Juntao ZH
 *
 */
public class LiOpenShawSimplify {
	
	private double radius;
	private Coordinate[] line;
	
	public LiOpenShawSimplify(double scaleSrc,double scaleDist,LineString line){
		this.radius = scaleDist * 1 * (1 - scaleSrc/scaleDist);
		this.line = line.getCoordinates();
	}
	
	
	public static LineString simplify(double scaleSrc,double scaleDist,LineString line){
		LiOpenShawSimplify simp = new LiOpenShawSimplify(scaleSrc,scaleDist,line);
		return simp.simplify();
	}
	

	
	public LineString simplify(){
		return simplifySegment(0,line.length-1);
	}
	
	
	public static LineString simplifySegment(double scaleSrc,double scaleDist,LineString line,int lineStart,int lineEnd){
		LiOpenShawSimplify simp = new LiOpenShawSimplify(scaleSrc,scaleDist,line);
		return simp.simplifySegment(lineStart,lineEnd);
	}

	public LineString simplifySegment(int lineStart,int lineEnd){
		List<Coordinate> l = new ArrayList<Coordinate>();
		
		int startIndex = lineStart;
		Coordinate prePoint = new Coordinate();
		Coordinate startPoint = new Coordinate();
		startPoint.x = line[startIndex].x;
		startPoint.y = line[startIndex].y;
		
		l.add(line[startIndex]);
		
		
		Coordinate nextPoint;
		
		while((nextPoint=getNextStart(lineEnd,startIndex,prePoint,startPoint))!=null){
			Coordinate choosedPoint = new Coordinate((startPoint.x+nextPoint.x)/2,(startPoint.y+nextPoint.y)/2);
			l.add(choosedPoint);
			prePoint.x = startPoint.x;
			prePoint.y = startPoint.y;
			startPoint.x = nextPoint.x;
			startPoint.y = nextPoint.y;
			startIndex = (int) nextPoint.z;
		}
		
		
		
		l.add(line[lineEnd]);
		GeometryFactory factory = JTSFactoryFinder.getGeometryFactory();
		return factory.createLineString(l.toArray(new Coordinate[l.size()]));
	}
	
	
	
	private  Coordinate getNextStart(int lineEnd,int startIndex,Coordinate prePoint,Coordinate startPoint){		
		for(int i=startIndex;i<lineEnd-1;i++){
			Coordinate[] point = MixedRelation2D.CircleAndLine.getCrossingPoint(startPoint, this.radius, new LineSegment(line[i],line[i+1]));
			
			if(point!=null){
				
				if(point.length==1){
					/*
					 * when goes back.
					 */
					if(point[0].distance(line[i+1])>startPoint.distance(line[i+1])){
						continue;
					}
					point[0].z = i;
					return point[0];
			}else{	
					
					if(point[0].distance(prePoint) < point[1].distance( prePoint)){
						point[1].z = i;
						return point[1];
					}else{
						point[0].z = i;
						return point[0];
					}
				}
			}
		}
		
		return null;
	}
}
