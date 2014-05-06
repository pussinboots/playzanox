The Angular JS App - minified zanox partner ui

This Application use the scala, play with AngularJS to build an minified 
zanox marketplace ui that is build on the zanox publisher api.

For the Authentification the zanox connect protocol is used.

This prototyp demonstrate the possibility to build an complete client side ui
with Angular JS that is build on top of an rest api (for that example the zanox Rest 
API is used http://wiki.zanox.com/en/Web_Services).

The json response will be fetch from http://api.zanox.com but at the moment 
there a little scala proxy is used to query the zanox api to solve the cross domain 
problem.

1. git clone
2. play run 
3. Open browser to http://localhost:9000


To see the live demo go here http://playzanox.herokuapp.com/assets/products.html .
Continous Integration with travis https://travis-ci.org/pussinboots/playzanox/ .

To use this live version you need a valid zanox publisher account.


To start development.

For Eclipse
     
sbt eclipse with-source=true
    
For Idea

sbt idea with-sources
