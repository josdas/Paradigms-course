#include "big_integer.h"

#include <cstring>
#include <stdexcept>
#include <algorithm>

template <typename T>
bool is_neg(T x)
{
	return x < 0;
}

vector<ull> transformNumber(string const& str)
{
	vector<ull> temp;
	int end;
	if (str[0] == '-')
	{
		end = 1;
	}
	else
	{
		end = 0;
	}
	for (int i = static_cast<int>(str.size()) - 1; i >= end; i--)
	{
		if (str[i] < '0' || str[i] > '9')
		{
			throw exception("incorrect_char");
		}
		temp.push_back(str[i] - '0');
	}
	return temp;
}

uint getDigitFromVector(vector<ull>& num, ull base)
{
	if (base == 0)
	{
		throw exception("base_equals_zero");
	}
	ull carry = 0;
	for (int i = static_cast<int>(num.size()) - 1; i >= 0; i--)
	{
		ull cur = num[i] + carry * base_10;
		num[i] = cur / base;
		carry = cur % base;
	}
	while (num.size() > 0 && num.back() == 0)
	{
		num.pop_back();
	}
	return static_cast<uint>(carry);
}

big_integer::big_integer()
{
	sign = false;
}

big_integer::big_integer(big_integer const& T)
{
	data = T.data;
	sign = T.sign;
}

big_integer::big_integer(int x)
{
	if (x == 0)
	{
		sign = false;
	}
	else
	{
		sign = is_neg(x);
		data = vector<uint>(1, x & (dig - 1));
		normalize();
	}
}

big_integer::big_integer(uint a)
{
	data = vector<uint>(1, a);
	sign = false;
}

big_integer::big_integer(vector<uint> const& T, bool _sign) : sign(_sign), data(T)
{
	normalize();
}

big_integer::big_integer(string const& str)
{
	if (str.size() == 0)
	{
		throw exception("empty_string");
	}
	bool isNeg = str[0] == '-';
	vector<ull> temp = transformNumber(str);
	vector<uint> tempData;
	while (temp.size() > 0)
	{
		tempData.push_back(getDigitFromVector(temp, dig));
	}
	sign = false;
	data = tempData;
	if (isNeg)
	{
		*this = -*this;
	}
	normalize();
}

big_integer& big_integer::operator=(big_integer const& other)
{
	big_integer T(other);
	swap(T);
	return *this;
}

big_integer operator*(big_integer const& first, big_integer const& second)
{
	bool sign = first.is_negative() ^ second.is_negative();
	big_integer absFirst(first.absolute_value());
	big_integer absSecond(second.absolute_value());
	if (absFirst.length() > absSecond.length())
	{
		absFirst.swap(absSecond);
	}
	size_t size = absFirst.length() + absSecond.length();
	big_integer result;
	for (size_t i = 0; i < absFirst.length(); i++)
	{
		vector<uint> temp1(size);
		vector<uint> temp2(size + 1);
		for (size_t j = 0; j < absSecond.length(); j++)
		{
			size_t k = i + j;
			ull tmp = static_cast<ull>(absFirst.get_digit(i)) * absSecond.get_digit(j);
			temp1[k] = tmp & (dig - 1);
			temp2[k + 1] = tmp >> pow_dig;
		}
		result = result + big_integer(temp1, false) + big_integer(temp2, false);
	}
	if (sign)
	{
		result = -result;
	}
	return result;
}

big_integer get_higher_digits(big_integer const& number, size_t n)
{
	vector<uint> temp(min(n, number.length()));
	for (int i = 0; i < temp.size(); i++)
	{
		if (i < number.length())
		{
			temp[temp.size() - i - 1] = number.get_digit(number.length() - i - 1);
		}
	}
	return big_integer(temp, false);
}

uint get_next_digit(big_integer const& first, big_integer const& second)
{
	if (second == 0)
	{
		throw exception("divition_by_zero");
	}
	big_integer absFirst = get_higher_digits(first, 4);
	big_integer absSecond = get_higher_digits(second, 3);
	if (absFirst < absSecond)
	{
		return 0;
	}
	uint t = static_cast<uint>(absFirst.length() - absSecond.length());
	absSecond = absSecond << t * pow_dig;
	for (uint i = 0; i <= t; i++)
	{
		uint dig = 0;
		if (absFirst >= absSecond)
		{
			for (int i = 31; i >= 0; i--)
			{
				uint nw = dig + (1ll << i);
				if (nw * absSecond <= absFirst)
				{
					dig = nw;
				}
			}
			absFirst = absFirst - (dig * absSecond);
		}
		if (dig > 0)
		{
			return dig;
		}
		absSecond = absSecond >> pow_dig;
	}
	return 0;
}

big_integer operator/(big_integer const& first, big_integer const& second)
{
	if (second == 0)
	{
		throw exception("divition_by_zero");
	}
	bool sign = first.is_negative() ^ second.is_negative();
	big_integer absFirst(first.absolute_value());
	big_integer absSecond(second.absolute_value());
	if (absFirst < absSecond)
	{
		return big_integer(0);
	}
	uint t = static_cast<uint>(absFirst.length() - absSecond.length());
	absSecond = absSecond << t * pow_dig;
	vector<uint> temp;
	for (uint i = 0; i <= t; i++)
	{
		uint dig = 0;
		if (absFirst >= absSecond)
		{
			dig = get_next_digit(absFirst, absSecond);
			big_integer tmp = dig * absSecond;
			if (absFirst >= tmp + absSecond)
			{
				tmp += absSecond;
			}
			absFirst = absFirst - tmp;
		}
		temp.push_back(dig);
		absSecond = absSecond >> pow_dig;
	}
	reverse(temp.begin(), temp.end());
	big_integer result(temp, false);
	if (sign)
	{
		result = -result;
	}
	return result;
}

big_integer operator%(big_integer const& first, big_integer const& second)
{
	return first - (first / second) * second;
}

big_integer operator+(big_integer const& first, big_integer const& second)
{
	ull carr = 0;
	size_t size = max(first.length(), second.length()) + 2;
	vector<uint> temp(size);
	for (size_t i = 0; i < size; i++)
	{
		ull val = carr + first.get_inf_digit(i) + second.get_inf_digit(i);
		carr = val / dig;
		temp[i] = val % dig;
	}
	bool sgn;
	if (temp[size - 1] >> (pow_dig - 1))
	{
		sgn = true;
	}
	else
	{
		sgn = false;
	}
	return big_integer(temp, sgn);
}

bool operator==(big_integer const& a, big_integer const& b)
{
	return a.data == b.data && a.sign == b.sign;
}

bool operator!=(big_integer const& a, big_integer const& b)
{
	return !(a == b);
}

bool operator<(big_integer const& first, big_integer const& second)
{
	if (first.get_sign() != second.get_sign())
	{
		return first.get_sign() < second.get_sign();
	}
	if (first.length() != second.length())
	{
		return first.length() < second.length();
	}
	for (int i = static_cast<int>(first.length()) - 1; i >= 0; i--)
	{
		if (first.get_digit(i) != second.get_digit(i))
		{
			return first.get_digit(i) < second.get_digit(i);
		}
	}
	return false;
}

bool operator>(big_integer const& a, big_integer const& b)
{
	return b < a;
}

bool operator<=(big_integer const& a, big_integer const& b)
{
	return !(a > b);
}

bool operator>=(big_integer const& a, big_integer const& b)
{
	return !(a < b);
}

string to_bin_string(big_integer const& arg)
{
	string result;
	big_integer absArg = arg.absolute_value();
	bool isNeg = arg.is_negative();
	for (size_t i = 0; i < absArg.length(); i++)
	{
		uint t = absArg.get_digit(i);
		for (uint j = 0; j < pow_dig; j++)
		{
			result.push_back('0' + ((t >> j) & 1));
		}
	}
	while (result.size() > 1 && result.back() == '0')
	{
		result.pop_back();
	}
	if (isNeg)
	{
		result.push_back('-');
	}
	reverse(result.begin(), result.end());
	return result;
}

string to_string(big_integer const& arg)
{
	string result;
	big_integer absArg = arg.absolute_value();
	bool isNeg = arg.is_negative();
	while (absArg.length() > 0)
	{
		const int block = 1000 * 1000 * 1000;
		const int pow_10_block = 9;
		int t = absArg % block;
		absArg = absArg / block;
		for (int i = 0; i < pow_10_block; i++)
		{
			result.push_back('0' + t % 10);
			t /= 10;
		}
	}
	while (result.size() > 1 && result.back() == '0')
	{
		result.pop_back();
	}
	if (result.empty())
	{
		result.push_back('0');
	}
	if (isNeg)
	{
		result.push_back('-');
	}
	reverse(result.begin(), result.end());
	return result;
}

big_integer operator/(big_integer const& first, int second)
{
	if (second == 0)
	{
		throw exception("divition_by_zero");
	}
	big_integer absFirst(first.absolute_value());
	bool isNeg = first.is_negative() ^ is_neg(second);
	ll div = abs(static_cast<ll>(second));
	size_t size = first.length();
	vector<uint> temp(size);
	ull carry = 0;
	for (int i = static_cast<int>(size) - 1; i >= 0; i--)
	{
		ull cur = absFirst.get_digit(i) + carry * dig;
		temp[i] = static_cast<uint>(cur / div);
		carry = cur % div;
	}
	big_integer T(temp, false);
	if (isNeg)
	{
		T = -T;
	}
	return T;
}

/*
big_integer operator/(big_integer const& first, uint second)
{
	if (second == 0)
	{
		throw exception("divition_by_zero");
	}
	big_integer absFirst(first.absolute_value());
	bool is_negative = first.is_negative();
	size_t size = first.length();
	vector<uint> temp(size);
	ull carry = 0;
	for (int i = static_cast<int>(size) - 1; i >= 0; i--)
	{
		ull cur = absFirst.get_digit(i) + carry * dig;
		temp[i] = static_cast<uint>(cur / second);
		carry = cur % second;
	}
	big_integer T(temp, false);
	if (is_negative)
	{
		T = -T;
	}
	return T;
}*/

int operator%(big_integer const& first, int second)
{
	if (second == 0)
	{
		throw exception("mod_by_zero");
	}
	big_integer absFirst(first.absolute_value());
	bool isNeg = first.is_negative();
	size_t size = first.length();
	int carry = 0;
	for (int i = static_cast<int>(size) - 1; i >= 0; i--)
	{
		ull cur = absFirst.get_digit(i) + carry * dig;
		carry = static_cast<int>(cur % second);
	}
	return carry;
}

big_integer operator-(big_integer const& first)
{
	return (~first) + 1;
}

big_integer operator-(big_integer const& first, big_integer const& second)
{
	return first + (-second);
}

big_integer operator+(big_integer const& first)
{
	return big_integer(first);
}

big_integer operator++(big_integer& first)
{
	big_integer temp = first + 1;
	first.swap(temp);
	return temp;
}

big_integer operator~(big_integer const& first)
{
	size_t size = first.length();
	vector<uint> temp(size);
	for (size_t i = 0; i < size; i++)
	{
		temp[i] = ~first.get_inf_digit(i) & static_cast<uint>(dig - 1);
	}
	return big_integer(temp, !first.is_negative());
}

big_integer operator&(big_integer const& first, big_integer const& second)
{
	size_t size = max(first.length(), second.length());
	vector<uint> temp(size);
	for (size_t i = 0; i < size; i++)
	{
		temp[i] = first.get_inf_digit(i) & second.get_inf_digit(i);
	}
	return big_integer(temp, first.is_negative() & second.is_negative());
}

big_integer operator|(big_integer const& first, big_integer const& second)
{
	size_t size = max(first.length(), second.length());
	vector<uint> temp(size);
	for (size_t i = 0; i < size; i++)
	{
		temp[i] = first.get_inf_digit(i) | second.get_inf_digit(i);
	}
	return big_integer(temp, first.is_negative() | second.is_negative());
}

big_integer operator^(big_integer const& first, big_integer const& second)
{
	size_t size = max(first.length(), second.length());
	vector<uint> temp(size);
	for (size_t i = 0; i < size; i++)
	{
		temp[i] = first.get_inf_digit(i) ^ second.get_inf_digit(i);
	}
	return big_integer(temp, first.is_negative() ^ second.is_negative());
}

big_integer operator>>(big_integer const& first, uint shift)
{
	if (shift == 0)
	{
		return big_integer(first);
	}
	ull mod = shift % pow_dig;
	ull div = shift / pow_dig;
	size_t size = div < first.length() ? first.length() - div : 0;
	vector<uint> temp(size);
	for (size_t i = 0; i < size; i++)
	{
		temp[i] = (static_cast<ull>(first.get_inf_digit(i + div)) >> mod)
			| (static_cast<ull>(first.get_inf_digit(i + div + 1)) << (pow_dig - mod));
		temp[i] &= dig - 1;
	}
	return big_integer(temp, first.is_negative());
}

big_integer operator<<(big_integer const& first, uint shift)
{
	if (shift == 0)
	{
		return big_integer(first);
	}
	ull mod = shift % pow_dig;
	ull div = shift / pow_dig;
	size_t size = first.length() + div + 1;
	vector<uint> temp(size);
	for (size_t i = div; i < size; i++)
	{
		temp[i] = (first.get_inf_digit(i - div) << mod);
		if (i > div)
		{
			uint t = static_cast<ull>(first.get_inf_digit(i - div - 1)) >> (pow_dig - mod);
			temp[i] = temp[i] | t;
		}
		temp[i] &= dig - 1;
	}
	return big_integer(temp, first.is_negative());
}

big_integer& big_integer::operator&=(big_integer const& rhs)
{
	big_integer T = *this & rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator|=(big_integer const& rhs)
{
	big_integer T = *this | rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator^=(big_integer const& rhs)
{
	big_integer T = *this ^ rhs;
	swap(T);
	return *this;
}

void big_integer::swap(big_integer& t) noexcept
{
	using std::swap;
	swap(sign, t.sign);
	swap(data, t.data);
}

size_t big_integer::length() const
{
	return data.size();
}

int big_integer::get_sign() const
{
	return sign ? -1 : 1;
}

bool big_integer::is_negative() const
{
	return sign;
}

uint big_integer::get_digit(size_t i) const
{
	return data[i];
}

uint big_integer::get_inf_digit(size_t i) const
{
	if (i < length())
	{
		return get_digit(i);
	}
	if (get_sign() == -1)
	{
		return dig - 1;
	}
	return 0;
}

big_integer big_integer::absolute_value() const
{
	if (is_negative())
	{
		return -*this;
	}
	return +*this;
}

void big_integer::normalize()
{
	while (data.size() > 0
		&& (get_sign() == -1 && data.back() == dig - 1
			|| get_sign() == 1 && data.back() == 0))
	{
		data.pop_back();
	}
}

big_integer& big_integer::operator<<=(int rhs)
{
	big_integer T = *this << rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator>>=(int rhs)
{
	big_integer T = *this >> rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator+=(big_integer const& rhs)
{
	big_integer T = *this + rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator-=(big_integer const& rhs)
{
	big_integer T = *this - rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator*=(big_integer const& rhs)
{
	big_integer T = *this * rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator/=(big_integer const& rhs)
{
	big_integer T = *this / rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator%=(big_integer const& rhs)
{
	big_integer T = *this % rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator/=(int rhs)
{
	big_integer T = *this / rhs;
	swap(T);
	return *this;
}

big_integer& big_integer::operator%=(int rhs)
{
	big_integer T = *this % rhs;
	swap(T);
	return *this;
}