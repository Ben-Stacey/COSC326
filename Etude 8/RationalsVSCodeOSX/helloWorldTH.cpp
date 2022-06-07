#include <iostream>
#include "Integer.h"
#include "Rational.h"

using namespace cosc326;

int main(int argc, const char * argv[]) {
	// insert code here...
	int i = 5;
	std::cout << "Kia ora!\n";
	std::cout << argv[0] << std::endl;

 	int a = 10;
 	int b = -a;
	int c = +a; // b = -10

 	Integer myint;

	std::cout<<"a: "<<a<<", b: "<<b<<std::endl;
	return 0;
}
