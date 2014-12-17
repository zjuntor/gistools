gistools
========

gistools is a gis toolset and it exsit for some gis tools that not included in popular open source toolset, such as geotools, JTS. Currently, it only contains the simplifier for graphics(LineString).


packages
========

algorithms : base algorithms used in this toolset
simplify : simplifiers used to simplify graphics


usage
========
LiOpenShawSimplify

Using Li-Openshaw algorithms to simplify graphics

//scaleSrc is the source map scale
//scaleDist is the destination map scale
//lineString is the LineString object to simplify
LiOpenShawSimplify simp = new LiOpenShawSimplify(scaleSrc,scaleDist,lineString);
LineString res = simp.simplify()




