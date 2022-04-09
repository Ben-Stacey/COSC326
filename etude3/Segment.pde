class Segment {
   PVector a;
   PVector b;
   
   //sets the vectors
   Segment(PVector a_, PVector b_) {
     a = a_.copy();
     b = b_.copy();
   }
   
   //generates the different orders
   Segment[] generate() {
     Segment[] children = new Segment[4];
     
     PVector v = PVector.sub(b,a);
     v.div(3);
     
     PVector b1 = PVector.add(a,v);
     children[0] = new Segment(a,b1);
     
     PVector a1 = PVector.sub(b,v);
     children[3] = new Segment(a1,b);
     
     v.rotate(-PI/3);
     PVector c = PVector.add(b1,v);

     children[1] = new Segment(b1, c);

     children[2] = new Segment(c, a1);
     return children;
   }
   
   void show() {
     stroke(255);
     line(a.x,a.y,b.x,b.y);
   }
}
