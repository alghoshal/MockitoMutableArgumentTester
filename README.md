# MockitoMutableArgumentTester
Showcases Mutable Arguments capture in Mockito

Captures Arguments to the Service.serve() method: 
 - Multiple calls to serve() happen from the same caller 
 - Along with reuse of MutableInt argument objects by the caller
 - Argument value is copied to a new MutableInt object & that's captured
