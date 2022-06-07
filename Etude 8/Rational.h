#ifndef RATIONAL_H_
#define RATIONAL_H_

#include "Integer.h"

/**
 * @brief This sets all the methods Rational.cpp file 
 * 
 */
namespace etude08 {
	class Rational {
	private:
		Integer a;
		Integer b;
		int sign;
	private:
		void normalize();
		void setData(std::string data);
	public:
		Rational();
		Rational(const Rational& r);
		Rational(const Integer& a);
		Rational(const Integer& a, const Integer b);
		Rational(const Integer& a, const Integer b, const Integer c);
        Rational(std::string data);
		~Rational();
		Rational& operator=(const Rational& r); // =r
		Rational operator+() const; // +r
		Rational operator-() const; // -r
		Rational operator+(const Rational& r) const; // binary +r
		Rational operator-(const Rational& r) const; // binary -r
		Rational operator*(const Rational& r) const; // binary *r
		Rational operator/(const Rational& r) const; // binary /r
		Rational operator+=(const Rational& r); // += r
		Rational operator-=(const Rational& r); // -= r
		Rational operator*=(const Rational& r); // *= r
		Rational operator/=(const Rational& r); // /= r

		bool operator==(const Rational& r) const; // == r
		bool operator!=(const Rational& r) const; // != r
		bool operator<(const Rational& r) const; // < r
		bool operator<=(const Rational& r) const; // <= r
		bool operator>(const Rational& r) const; // > r
		bool operator>=(const Rational& r) const; // >= r

		friend std::ostream& operator<<(std::ostream& os, const Rational& r); // std::cout << r << std::endl
		friend std::istream& operator>>(std::istream& is, Rational& r); // std::cin >> r

	};
}
#endif
