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

/**
 * @author juntao
 *
 */
public class Precision {
	public static double PRECISION=1e-6;
	
	
	public static boolean equal(double a,double b){
		return Math.abs(a-b) < PRECISION;
	}
	
	public static boolean bigThan(double a,double b){
		return a > b;
	}
	
	public static boolean littleThan(double a,double b){
		return a < b;
	}
	public static boolean bigEqual(double a,double b){
		return a > b || equal(a,b);
	}
	
	public static boolean littleEqual(double a,double b){
		return a < b || equal(a,b);
	}
	
}
