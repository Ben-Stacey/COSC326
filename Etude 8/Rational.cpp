#include "Rational.h"

namespace etude08 {
	/**
	 * @brief Construct a new Rational:: Rational object
	 * 
	 */
	Rational::Rational() {
		sign = 1;
		a = Integer("0");
		b = Integer("1");
	}

	/**
	 * @brief sets data for the rationals
	 * 
	 * @param data 
	 */
	void Rational::setData(std::string data) {
		sign = 1;

		int indexOfDot = -1;
		int indexOfSeperator = -1;

		for (size_t i = 0; i < data.size(); i++) {
			if (data[i] == '/') {
				indexOfSeperator = i;
			}

			if (data[i] == '.') {
				indexOfDot = i;
			}
		}

		if (indexOfDot == -1) {
			if (indexOfSeperator != -1) {
				a = Integer(data.substr(0, indexOfSeperator));
				b = Integer(data.substr(indexOfSeperator + 1, data.size() - indexOfSeperator - 1));
			} else {
				a = Integer(data); 
				b = Integer("1");
			}
		} else {
			Integer zero("0");
			Integer w(data.substr(0, indexOfDot));
			if(w < zero){
				w = -w;
				sign = -1;
			}
			
			a = Integer(data.substr(indexOfDot + 1, indexOfSeperator - indexOfDot));
			b = Integer(data.substr(indexOfSeperator + 1, data.size() - indexOfSeperator - 1));
			Integer i = a;

			while(i > Integer(0)){
				w = w * Integer(10);
				b = b * Integer(10);

				i /= Integer(10);
			}
			a = w + a;

			if(sign == -1){
				a = -a;
			}
		}

		this->normalize();
	}
	
	/**
	 * @brief Construct a new Rational:: Rational object
	 * 
	 * @param data 
	 */
	Rational::Rational(std::string data) {
		setData(data);
	}

	/**
	 * @brief 
	 * 
	 * @param os 
	 * @param r 
	 * @return std::ostream& 
	 */
	std::ostream& operator<<(std::ostream& os, const Rational& r) {
		
		if (r.a > r.b) {
			if (r.sign == -1) {
				os << "-";
			}

			Integer one("1");
			if (r.b == one) {
				os << r.a;
			} else {

				Integer whole = r.a / r.b;
				Integer mod = r.a % r.b;

				os << whole << "." << mod << "/" << r.b;
			}
		} else {
			if (r.sign == -1) {
				os << "-";
			}

			Integer one("1");
			if (r.b == one) {
				os << r.a;
			} else {
				os << r.a << "/" << r.b;
			}
		}
		return os;
	}

	/**
	 * @brief 
	 * 
	 * @param is 
	 * @param r 
	 * @return std::istream& 
	 */
	std::istream& operator>>(std::istream& is, Rational& r) {
		std::string data;
		is >> data;
		r.setData(data);

		return is;
	}
	
	/**
	 * @brief when there is a fraction is exceutes a reduction of a fraction
	 * 
	 */
	void Rational::normalize() {
		Integer zero; // 0

		sign = 1;
		if (a < zero) {
			sign *= -1;
			a = -a;
		}

		if (b < zero) {
			sign *= -1;
			b = -b;
		}
		
		Integer g = a.gcd(a, b);
		Integer one("1");
		if (g > one) {
			a /= g;
			b /= g;
		}
		if (a == zero) {
			sign = 1;
			b = Integer("1");
		}
	}
	
	/**
	 * @brief Construct a new Rational:: Rational object
	 * 
	 * @param r 
	 */
	Rational::Rational(const Rational& r) {
		sign = r.sign;
		a = r.a;
		b = r.b;
	}

	/**
	 * @brief Construct a new Rational:: Rational object
	 * 
	 * @param a 
	 */
	Rational::Rational(const Integer& a) {
		sign = 1;
		this->a = a;
		b = Integer("1");
		this->normalize();
	}

	/**
	 * @brief Construct a new Rational:: Rational object
	 * 
	 * @param a 
	 * @param b 
	 */
	Rational::Rational(const Integer& a, const Integer b) {
		sign = 1;
		this->a = a;
		this->b = b;
		this->normalize();
	}

	/**
	 * @brief Construct a new Rational:: Rational object
	 * 
	 * @param a 
	 * @param b 
	 * @param c 
	 */
	Rational::Rational(const Integer& a, const Integer b, const Integer c) {
		sign = 1;
		this->a = a * c + b;
		this->b = c;
		this->normalize();
	}

	/**
	 * @brief Destroy the Rational:: Rational object
	 * 
	 */
	Rational::~Rational() {
	}
		/**
		 * @brief checks for the = sign and then sets the values and looks for total
		 * 
		 * @param r 
		 * @return Rational& 
		 */
		Rational& Rational::operator =(const Rational& r) {
		sign = r.sign;
		a = r.a;
		b = r.b;

		return *this;
	}
    
	/**
	 * @brief checks for the + sign and then adds a and b
	 * 
	 * @return Rational 
	 */
	Rational Rational::operator +() const {
		return *this;
	}
    
	/**
	 * @brief checks for the - sign and then minuses a and b
	 * 
	 * @return Rational 
	 */
	Rational Rational::operator -() const {
		Rational r = *this;
		r.sign *= -1;
		return r;
	}    

	/**
	 * @brief checks for the + sign and then adds a and b
	 * 
	 * @param r 
	 * @return Rational 
	 */
	Rational Rational::operator +(const Rational& r) const {
		Integer down = b * r.b;
		Integer up = a * r.b + r.a * b;
		return Rational(up, down);
	}
    
	/**
	 * @brief checks for the - sign and then minuses a and b
	 * 
	 * @param r 
	 * @return Rational 
	 */
	Rational Rational::operator -(const Rational& r) const {
		Integer down = b * r.b;
		Integer up = a * r.b - r.a * b;
		return Rational(up, down);
	}

	/**
	 * @brief checks for the * sign and then mulitples the a and b
	 * 
	 * @param r 
	 * @return Rational 
	 */
	Rational Rational::operator *(const Rational& r) const {
		return Rational(a * r.a, b * r.b);
	}

	/**
	 * @brief checks for the / sign and then divides a and b
	 * 
	 * @param r 
	 * @return Rational 
	 */
	Rational Rational::operator /(const Rational& r) const {
		return Rational(a * r.b, b * r.a);
	}
    
	/**
	 * @brief checks for the += sign and then adds it to the total
	 * 
	 * @param r 
	 * @return Rational 
	 */
	Rational Rational::operator +=(const Rational& r) {
		*this = *this + r;
		return *this;
	}
    
	/**
	 * @brief checks for the -= sign and then minuses from the total
	 * 
	 * @param r 
	 * @return Rational 
	 */
	Rational Rational::operator -=(const Rational& r) {
		*this = *this - r;
		return *this;
	}
    
	/**
	 * @brief checks for the *= sign and then multiples of the total
	 * 
	 * @param r 
	 * @return Rational 
	 */
	Rational Rational::operator *=(const Rational& r) {
		*this = *this * r;
		return *this;
	}
    
	/**
	 * @brief checks for the /= sign and then looks for the remainder of the total
	 * 
	 * @param r 
	 * @return Rational 
	 */
	Rational Rational::operator /=(const Rational& r) {
		*this = *this / r;
		return *this;
	}
    
	/**
	 * @brief checks for the < sign and then compares the a and b
	 * 
	 * @param r 
	 * @return true 
	 * @return false 
	 */
	bool Rational::operator <(const Rational& r) const {
		return a * r.b < r.a * b;
	}
    
	/**
	 * @brief checks for the <= sign and then compares the a and b
	 * 
	 * @param r 
	 * @return true 
	 * @return false 
	 */
	bool Rational::operator <=(const Rational& r) const {
		return a * r.b <= r.a * b;
	}
    
	/**
	 * @brief checks for the >= sign and then compares the a and b
	 * 
	 * @param r 
	 * @return true 
	 * @return false 
	 */
	bool Rational::operator >=(const Rational& r) const {
		return a * r.b >= r.a * b;
	}

	/**
	 * @brief this checks for the > sign and then see if it is bigger than the integer
	 * 
	 * @param r 
	 * @return true 
	 * @return false 
	 */
	bool Rational::operator >(const Rational& r) const {
		return a * r.b > r.a * b;
	}

	/**
	 * @brief checks for == value and them calls the comapre method
	 * 
	 * @param r 
	 * @return true 
	 * @return false 
	 */
	bool Rational::operator ==(const Rational& r) const {
		return a * r.b == r.a * b;
	}
    
	/**
	 * @brief checks for != value and then it looks for not equale to the other
	 * 
	 * @param r 
	 * @return true 
	 * @return false 
	 */
    bool Rational::operator !=(const Rational& r) const {
		return a * r.b != r.a * b;
    }
}
