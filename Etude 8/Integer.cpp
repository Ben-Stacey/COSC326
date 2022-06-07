#include "Integer.h"

namespace etude08 {
	Integer::Integer() {
		sign = true;
		nums.push_back(0);
	}

	/**
	 * @brief Construct a new Integer:: Integer object
	 * 
	 * @param i 
	 */
	Integer::Integer(int i){
		sign = true;
		while(i > 0){
			nums.insert(nums.begin() + 0, i%10);
			i /= 10;
		}

		if(nums.size() == 0){
			nums.push_back(0);
		}
	}

	/**
	 * @brief checks for the sign in the input
	 * 
	 * @return Integer 
	 */
	Integer Integer::abs() const {
		if (sign) {
			return *this;
		} else {
			return Integer(!sign, nums);
		}
	}

	/**
	 * @brief Construct a new Integer:: Integer object. Sets the sign and the number
	 * 
	 * @param sign 
	 * @param nums 
	 */
	Integer::Integer(bool sign, const std::vector<int> nums) {
		this->sign = sign;
		this->nums = nums;
	}
    
	/**
	 * @brief 
	 * 
	 * @param s 
	 */
	void Integer::setData(std::string s) {
		if (s.size() > 0) {
			sign = true;

			for (size_t i = 0; i < s.size(); i++) {
				char ch = s[i];
				if (ch >= '0' && ch <= '9') {
					nums.push_back(ch - '0');
				} else if (i == 0 && ch == '+') {
					sign = true;
				} else if (i == 0 && ch == '-') {
					sign = false;
				}
			}
		} else {
			sign = true;
			nums.push_back(0);
		}
	}

	/**
	 * @brief Construct a new Integer:: Integer object
	 * 
	 * @param s 
	 */
	Integer::Integer(std::string s) {
		setData(s);
	}
	
	/**
	 * @brief Construct a new Integer:: Integer object
	 * 
	 * @param integer 
	 */
	Integer::Integer(const Integer& integer) {
		sign = integer.sign;
		nums = integer.nums;
	}

	/**
	 * @brief 
	 * 
	 * @param os 
	 * @param integer 
	 * @return std::ostream& 
	 */
	std::ostream& operator<<(std::ostream& os, const Integer& integer) {
		if (!integer.sign) {
			os << "-";
		}

		for (size_t i = 0; i < integer.nums.size(); i++) {
			os << integer.nums[i];
		}
		return os;
	}
    
	/**
	 * @brief 
	 * 
	 * @param is 
	 * @param integer 
	 * @return std::istream& 
	 */
	std::istream& operator>>(std::istream& is, Integer& integer) {
		std::string str;
		is >> str;

		integer.setData(str);
		return is;
	}
    
	Integer::~Integer(){
	}
    
	/**
	 * @brief checks for the operator
	 * 
	 * @param integer 
	 * @return Integer& 
	 */
	Integer& Integer::operator =(const Integer& integer) {
		sign = integer.sign;
		nums = integer.nums;
		return *this;
	}
    
	/**
	 * @brief checks for the operator
	 * 
	 * @return Integer 
	 */
	Integer Integer::operator +() const {
		return Integer(sign, nums);
	}
    
	/**
	 * @brief checks for the operator
	 * 
	 * @return Integer 
	 */
	Integer Integer::operator -() const {
		return Integer(!sign, nums);
	}

	/**
	 * @brief methods removes the leading zero from the number
	 * 
	 * @param nums 
	 * @return std::vector<int> 
	 */
	std::vector<int> Integer::removeLeadingZero(const std::vector<int>& nums) const {
		std::vector<int> result;

		int start = -1;
		for (size_t i = 0; i < nums.size(); i++) {
			if (nums[i] != 0) {
				start = i;
				break;
			}
		}

		if (start == -1) {
			result.push_back(0);
		} else {
			for (size_t i = start; i < nums.size(); i++) {
				result.push_back(nums[i]);
			}
		}

		return result;
	}
	
	/**
	 * @brief compares 2 values
	 * 
	 * @param a 
	 * @param b 
	 * @return int 
	 */
	int Integer::compare(const std::vector<int>& a,const std::vector<int>& b) const {
		if (a.size() == b.size()) {
			for (size_t i = 0; i < a.size(); i++) {
				if (a[i] > b[i]) {
					return 1;
				} else if (a[i] < b[i]) {
					return -1;
				}
			}
			return 0;
		} else {
			if (a.size() > b.size()) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	/**
	 * @brief method reads a vector and returns it in reverse of the input
	 * 
	 * @param nums 
	 * @return std::vector<int> 
	 */
	std::vector<int> Integer::reverse(const std::vector<int>& nums) const {
		std::vector<int> result;
		for (int i = nums.size() - 1; i >= 0; i--) {
			result.push_back(nums[i]);
		}
		return result;
	}

	/**
	 * @brief adds values together
	 * 
	 * @param a 
	 * @param b 
	 * @return std::vector<int> 
	 */
	std::vector<int> Integer::add(const std::vector<int>& a,const std::vector<int>& b) const {
		std::vector<int> result;

		int carry = 0;
		int i = a.size() - 1;
		int j = b.size() - 1;
		while (i >= 0 && j >= 0) {
			int sum = a[i] + b[j] + carry;
			int v = sum % 10;
			carry = sum / 10;
			result.push_back(v);

			i--;
			j--;
		}

		while (i >= 0) {
			int sum = a[i] + carry;
			int v = sum % 10;
			carry = sum / 10;
			result.push_back(v);

			i--;
		}

		while (j >= 0) {
			int sum = b[j] + carry;
			int v = sum % 10;
			carry = sum / 10;
			result.push_back(v);

			j--;
		}

		if (carry > 0) {
			result.push_back(carry);
		}

		result = reverse(result);
		return result;
	}

	/**
	 * @brief minuses values
	 * 
	 * @param a 
	 * @param b 
	 * @return std::vector<int> 
	 */
	std::vector<int> Integer::sub(const std::vector<int>& a,const std::vector<int>& b) const {
		std::vector<int> result;

		int borrow = 0;
		int i = a.size() - 1;
		int j = b.size() - 1;
		while (i >= 0 && j >= 0) {
			int diff = a[i] - borrow - b[j];

			if (diff < 0) {
				borrow = 1;
				int v = 10 + diff;
				result.push_back(v);
			} else {
				borrow = 0;
				int v = diff;
				result.push_back(v);
			}

			i--;
			j--;
		}

		while (i >= 0) {
			int diff = a[i] - borrow - 0;

			if (diff < 0) {
				borrow = 1;
				int v = 10 + diff;
				result.push_back(v);
			} else {
				borrow = 0;
				int v = diff;
				result.push_back(v);
			}

			i--;
		}

		result = reverse(result);
		result = removeLeadingZero(result);
		return result;
	}
    
	/**
	 * @brief checks for + value and then adds them togther
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator +(const Integer& integer) const {
		if ((sign && integer.sign) || (!sign && !integer.sign)) {
			std::vector<int> nums_result = add(nums, integer.nums);
            
			Integer result;
			if (!sign && !integer.sign) {
				result.sign = false;
			}
			result.nums = nums_result;

			if(result.nums.size()==1 && result.nums[0] == 0){
				result.sign = true;
			}
			return result;
		} else if (sign && !integer.sign) {
			return this->operator -(-integer);
		} else {
			return integer.operator -(-(*this));
		}
	}
    
	/**
	 * @brief checks for - value and then subs them 
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator -(const Integer& integer) const {
		if ((sign && !integer.sign)) {
			return this->operator +(-integer);
		} else if ((!sign && integer.sign)) {
			return -(integer.operator +(-(*this)));
		} else if (sign && integer.sign) {
			if (*this >= integer) {
				Integer result;
				result.sign = true;
				result.nums = sub(this->nums, integer.nums);

				return result;
			} else {
				Integer result;
				result.sign = false;
				result.nums = sub(integer.nums, this->nums);

				if(result.nums.size()==1 && result.nums[0] == 0){
					result.sign = true;
				}
				return result;
			}
		} else {
			Integer temp = -integer;
			return temp.operator -(*this);
		}
	}
    
	/**
	 * @brief checks for * value and then multiples them togther
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator *(const Integer& integer) const {
		Integer a = this->abs();
		Integer b = integer.abs();

		Integer zero;
		Integer one("1");
        
		Integer result;
		while (b > zero) {
			result += a;
			b -= one;
		}

		if (sign && integer.sign) {
			return result;
		} else if (!sign && !integer.sign) {
			return result;
		} else {
			if(result.nums.size()==1 && result.nums[0] == 0){
				return result;
			}else{
				result.sign = false;
				return result;
			}
		}
	}

	/**
	 * @brief checks for / value and then divides them 
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator /(const Integer& integer) const {

		Integer result; 
		Integer temp; 

		result.nums.clear();
		temp.nums.clear();

		result.sign = this->sign && integer.sign;

		Integer a = this->abs();
		Integer b = integer.abs();

		for (int i = 0, k = 0; i < a.nums.size(); i++) {
			temp.nums.push_back(a.nums[i]);
			if (temp >= b) {
				int r = 0;
				while (temp >= b) {
					temp = temp - b;
					r++;
				}
				result.nums.push_back(r);
			}
		}
		return result;
	}

	/**
	 * @brief checks for % value and then gets the modulus of them
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator %(const Integer& integer) const {
		Integer iabs = integer.abs();

		Integer result = this->abs();
		while (result >= iabs) {
			result -= iabs;
		}

		if (sign && integer.sign) {
			return result;
		} else if (!sign && !integer.sign) {
			return result;
		} else {
			if(result.nums.size()==1 && result.nums[0] == 0){
				return result;
			}else{
				result.sign = false;
				return result;
			}
		}
	}
    
	/**
	 * @brief checks for += value and then adds it onto the total
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator +=(const Integer& integer) {
		Integer result = *this + integer;
		this->sign = result.sign;
		this->nums = result.nums;
		return result;
	}
    
	/**
	 * @brief checks for -= value and then minues it off the total
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator -=(const Integer& integer) {
		Integer result = *this - integer;
		this->sign = result.sign;
		this->nums = result.nums;
		return result;
	}
    
	/**
	 * @brief checks for *= value and then mutliples it by the total
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator *=(const Integer& integer) {
		Integer result = *this * integer;
		this->sign = result.sign;
		this->nums = result.nums;
		return result;
	}
    
	/**
	 * @brief checks for /= value and then divides it of the total
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator /=(const Integer& integer) {
		Integer result = *this / integer;
		this->sign = result.sign;
		this->nums = result.nums;
		return result;
	}
    
	/**
	 * @brief checks for %= value and then gets the modulus of the total
	 * 
	 * @param integer 
	 * @return Integer 
	 */
	Integer Integer::operator %=(const Integer& integer) {
		Integer a = this->abs();
		while (a > integer) {
			a -= integer;
		}

		a.sign = sign;
		return a;
	}
    
	/**
	 * @brief checks for == value and them calls the comapre method
	 * 
	 * @param integer 
	 * @return true 
	 * @return false 
	 */
	bool Integer::operator ==(const Integer& integer) const {
		return compare(nums, integer.nums) == 0 && sign == integer.sign;
	}
    
	/**
	 * @brief checks for != value and then it looks for not equale to the other
	 * 
	 * @param integer 
	 * @return true 
	 * @return false 
	 */
	bool Integer::operator !=(const Integer& integer) const {
		return !this->operator ==(integer);
	}
    
	/**
	 * @brief checks for < and then calls the compare method to see if it less or greater than zero
	 * 
	 * @param integer 
	 * @return true 
	 * @return false 
	 */
	bool Integer::operator <(const Integer& integer) const {
		if (sign && integer.sign) {
			return compare(nums, integer.nums) < 0;
		} else if (!sign && !integer.sign) {
			return compare(nums, integer.nums) > 0;
		} else if (sign && !integer.sign) {
			return false;
		} else {
			return true;
		}
	}
    
	/**
	 * @brief this checks for <= and then compares to see if is the same or less than the integer
	 * 
	 * @param integer 
	 * @return true 
	 * @return false 
	 */
	bool Integer::operator <=(const Integer& integer) const {
		return *this < integer || *this == integer;
	}
    
	/**
	 * @brief this checks for the > sign and then see if it is bigger than the integer
	 * 
	 * @param integer 
	 * @return true 
	 * @return false 
	 */
	bool Integer::operator >(const Integer& integer) const {
		return !(*this < integer) && *this != integer;
	}
    
	/**
	 * @brief this checks for the >= sign and then makes sure it is not less than the integer
	 * 
	 * @param integer 
	 * @return true 
	 * @return false 
	 */
	bool Integer::operator >=(const Integer& integer) const {
		return !(*this < integer);
	}

	/**
	 * @brief gets remainders and recurisively calles untill integer2 equals 0 and then returns integer1
	 * 
	 * @param integer1 
	 * @param integer2 
	 * @return Integer 
	 */
	Integer Integer::gcd(const Integer& integer1, const Integer& integer2) const{
		if(integer2 == Integer("0")) {
			return integer1;
		}
		return gcd(integer2, integer1 % integer2);
	}
}
