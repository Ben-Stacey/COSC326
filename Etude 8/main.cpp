#include <iostream>
using namespace std;

#include "Integer.h"
#include "Rational.h"

using namespace etude08;
/**
 * @brief This program completes mathematical computations of rational numbers
 * 
 * @return int 
 */
int main() {

	Integer a("-2");
	Integer b(8);
	cout << a + b << endl;
	cout << a - b << endl;
	cout << a * b << endl;
	cout << a / b << endl;
	cout << a % b << endl;
	cout <<a.gcd(a,b) << endl;

	cout << "-----------------" << endl;

	Rational r1("1");
	Rational r2("2/3");
	Rational r3("-3.1/2");
	Rational r4("0.1/3");
	Rational r5("4/3");
	Rational r6("-3.2/4");
	Rational r7("+15.32/2");

	cout << r1 << endl;
	cout << r2 << endl;
	cout << r3 << endl;
	cout << r4 << endl;
	cout << r5 << endl;
	cout << r6 << endl;
	cout << r7 << endl;

	cout << r2 + r5 << endl;

	etude08::Integer ai("2452345345234123123178");
	etude08::Integer bi("23459023850983290589042");
	etude08::Rational r8(ai,bi); //problem
	cout << r8 << endl;

	return 0;
}
