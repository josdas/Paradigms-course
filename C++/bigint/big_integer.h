#ifndef BIG_INTEGER_H
#define BIG_INTEGER_H

#include <cstddef>
#include <iosfwd>
#include <vector>
#include <string>

using namespace std;
typedef unsigned int uint;
typedef unsigned long long ull;
typedef long long ll;


const ll pow_dig = 32;
const ll dig = 1ll << pow_dig;
const ull base_10 = 10;

struct big_integer
{
    big_integer();
    big_integer(big_integer const& other);
    big_integer(int a);
	big_integer(uint a);
	explicit big_integer(vector<uint> const& T, bool _sign);
    explicit big_integer(string const& str);


	big_integer& operator=(big_integer const& other);


	friend big_integer operator-(big_integer const&, big_integer const&);
	friend big_integer operator+(big_integer const&, big_integer const&);
	friend big_integer operator*(big_integer const&, big_integer const&); 
	friend big_integer operator/(big_integer const&, big_integer const&); 
	friend big_integer operator%(big_integer const&, big_integer const&); 


	friend big_integer operator/(big_integer const&, int);
	//friend big_integer operator/(big_integer const&, uint);
	friend int operator%(big_integer const&, int);            


	friend big_integer operator&(big_integer const&, big_integer const&); 
	friend big_integer operator|(big_integer const&, big_integer const&); 
	friend big_integer operator^(big_integer const&, big_integer const&);


	friend big_integer operator-(big_integer const&);               
	friend big_integer operator+(big_integer const&);
	friend big_integer operator~(big_integer const&);
	friend big_integer operator++(big_integer&);   


	friend big_integer operator<<(big_integer const&, uint);
	friend big_integer operator>>(big_integer const&, uint);

	big_integer& operator<<=(int rhs);
	big_integer& operator>>=(int rhs);


	friend std::string to_string(big_integer const&);
	friend std::string to_bin_string(big_integer const&);
	friend std::string to_string(big_integer const& a);


	friend bool operator==(big_integer const& a, big_integer const& b);
	friend bool operator!=(big_integer const& a, big_integer const& b);
	friend bool operator<(big_integer const& a, big_integer const& b);
	friend bool operator>(big_integer const& a, big_integer const& b);
	friend bool operator<=(big_integer const& a, big_integer const& b);
	friend bool operator>=(big_integer const& a, big_integer const& b);


	big_integer& operator+=(big_integer const& rhs);
	big_integer& operator-=(big_integer const& rhs);
	big_integer& operator*=(big_integer const& rhs);
	big_integer& operator/=(big_integer const& rhs);
	big_integer& operator%=(big_integer const& rhs);


	big_integer& operator/=(int rhs);
	big_integer& operator%=(int rhs);


	big_integer& operator&=(big_integer const& rhs);
	big_integer& operator|=(big_integer const& rhs);
	big_integer& operator^=(big_integer const& rhs);

	void swap(big_integer& t) noexcept;
	size_t length() const;
	int get_sign() const;
	bool is_negative() const;
	uint get_digit(size_t i) const;
	uint get_inf_digit(size_t i) const;
	big_integer absolute_value() const;
private:
	bool sign;
	std::vector<uint> data;
	void normalize();
};
string to_bin_string(big_integer const& arg);
string to_string(big_integer const& arg);

#endif // BIG_INTEGER_H
