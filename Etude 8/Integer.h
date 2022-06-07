#ifndef INTEGER_H_
#define INTEGER_H_

#include <vector>
#include <string>
#include <iostream>

/**
 * @brief This sets all the methods Integer.cpp file 
 * 
 */
namespace etude08 {

	class Integer {
	private:
		bool sign;
		std::vector<int> nums;

	private:
		Integer(bool sign, const std::vector<int> nums);
		Integer abs() const;
		void setData(std::string);
		std::vector<int> removeLeadingZero(const std::vector<int>& nums) const;
		std::vector<int> reverse(const std::vector<int>& nums) const;
		int compare(const std::vector<int>& a, const std::vector<int>& b) const;
		std::vector<int> add(const std::vector<int>& a, const std::vector<int>& b) const;
		std::vector<int> sub(const std::vector<int>& a, const std::vector<int>& b) const;
	public:
		Integer();

		Integer(int i);

		Integer(std::string);

		Integer(const Integer& integer);

		~Integer();

		Integer& operator=(const Integer& integer);

		Integer operator+() const;
		Integer operator-() const;

		Integer operator+(const Integer& integer) const;
		Integer operator-(const Integer& integer) const;
		Integer operator*(const Integer& integer) const;
		Integer operator/(const Integer& integer) const;
		Integer operator%(const Integer& integer) const;

		Integer operator+=(const Integer& integer);
		Integer operator-=(const Integer& integer);
		Integer operator*=(const Integer& integer);
		Integer operator/=(const Integer& integer);
		Integer operator%=(const Integer& integer);

		bool operator==(const Integer& integer) const;
		bool operator!=(const Integer& integer) const;
		bool operator<(const Integer& integer) const;
		bool operator<=(const Integer& integer) const;
		bool operator>(const Integer& integer) const;
		bool operator>=(const Integer& integer) const;

		friend std::ostream& operator<<(std::ostream& os, const Integer& integer); 	
		friend std::istream& operator>>(std::istream& is, Integer& integer);		

		Integer gcd(const Integer& integer1, const Integer& integer2) const;
	};
}
#endif
