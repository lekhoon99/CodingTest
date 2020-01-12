# Summary

**Time taken: 4 hours +

**Assumptions: 
The input string equation only consists of these parameters, i.e. numbers and operators (+ - * /).
Each parameter is separated by single space.
The input string equation are in valid equation format.

**Solution Approach:
1. The main method loops through a list of test equations to output the calculated values.
2. The solution first attempts to resolve equations in brackets. The bracket equations are extracted using regular expression matching. The extracted equations are calculated, and the bracket equations are then replaced with the calculated value.
3. After resolving the equations in bracket, the solution attempts to resolve the remaining equations. Mutiply and divide calculations are prioritized, followed by add and subtract. 
4. Multiply and divide equations are extracted using regular expression matching, The extracted equation are calculated and replaced with the calculated value.
5. Lastly, the remaining equations for Add and subtract are calculated.


