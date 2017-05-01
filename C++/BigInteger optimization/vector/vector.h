#pragma once
#ifndef VECTOR
#define VECTOR
#include <type_traits>
#include <memory>

template <class T>
struct vector {
	vector();
	vector(vector const& a);
	vector(size_t size, T const& a);
	explicit vector(size_t size);
	~vector();

	friend bool operator==(vector const& a, vector const& b) {
		if (a.size() != b.size()) {
			return false;
		}
		for (size_t i = 0; i < a.size(); i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	vector& operator=(vector a);

	T& operator[](size_t ind);
	T const& operator[](size_t ind) const;

	T& front();
	T const& front() const;

	T& back();
	T const& back() const;

	bool empty() const;
	size_t size() const;

	void reserve(size_t n);

	void push_back(T const& value);
	void pop_back();

	void swap(vector& a) noexcept;

private:
	static const size_t size_small = 8;
	size_t _size;
	bool _is_big;

	T& get_small_ind(size_t ind) {
		return _obj.small._data[ind];
	}

	T& get_big_ind(size_t ind) {
		change();
		return _obj.big._data.get()[ind];
	}

	T const& get_small_ind_c(size_t ind) const {
		return _obj.small._data[ind];
	}

	T const& get_big_ind_c(size_t ind) const {
		return _obj.big._data.get()[ind];
	}

	T& (vector::*function_get)(size_t);
	T const& (vector::*function_get_const)(size_t) const;

	struct small_big_type { // it can be union
		struct big_type {
			std::shared_ptr<T> _data;
			size_t _capacity;
			big_type() : _data(nullptr), _capacity(0) { }
		} big;

		struct small_type {
			T _data[size_small];
		} small;
	} _obj;

	static size_t get_new_capacity(size_t n);
	void set_capacity(size_t capacity);

	void delete_all();
	void change();

	bool is_big() const;
	void transform_to_big(size_t n);
};

template <class T>
vector<T>::vector() :
	_size(0),
	_is_big(false),
	function_get(&vector<T>::get_small_ind),
	function_get_const(&vector<T>::get_small_ind_c) { }

template <class T>
vector<T>::vector(vector const& a) :
	_size(a.size()),
	_is_big(a._is_big),
	function_get(a.function_get),
	function_get_const(a.function_get_const),
	_obj(a._obj) { }

template <class T>
vector<T>::vector(size_t size, T const& a) : vector() {
	reserve(size);
	for (size_t i = 0; i < size; i++) {
		push_back(a);
	}
}

template <class T>
vector<T>::vector(size_t size) : vector(size, T()) { }

template <class T>
vector<T>::~vector() {
	delete_all();
}

template <class T>
vector<T>& vector<T>::operator=(vector a) {
	swap(a);
	return *this;
}

template <class T>
T& vector<T>::operator[](size_t ind) {
	return (this ->* function_get)(ind);
}

template <class T>
T const& vector<T>::operator[](size_t ind) const {
	return (this ->* function_get_const)(ind);
}

template <class T>
T& vector<T>::front() {
	change();
	return (*this)[0];
}

template <class T>
T const& vector<T>::front() const {
	return (*this)[0];
}

template <class T>
T& vector<T>::back() {
	change();
	return (*this)[size() - 1];
}

template <class T>
T const& vector<T>::back() const {
	return (*this)[size() - 1];
}

template <class T>
bool vector<T>::empty() const {
	return _size == 0;
}

template <class T>
size_t vector<T>::size() const {
	return _size;
}

template <class T>
void vector<T>::reserve(size_t n) {
	if (is_big()) {
		change();
		if (_obj.big._capacity < n) {
			set_capacity(get_new_capacity(n));
		}
	}
	else {
		if (n > size_small) {
			transform_to_big(n);
		}
	}
}

template <class T>
void vector<T>::push_back(T const& value) {
	if (is_big()) {
		change();
		if (size() == _obj.big._capacity) {
			size_t capacity = get_new_capacity(size() + 1);
			T* temp = copy_data(capacity, _obj.big._data.get(), size());
			new(&temp[size()]) T(value);
			delete_all();
			_obj.big._data.reset(temp);
			_obj.big._capacity = capacity;
		}
		else {
			new(&(*this)[size()]) T(value);
		}
	}
	else {
		if (size() == size_small) {
			transform_to_big(size() + 1);
		}
		new(&(*this)[size()]) T(value);
	}
	_size++;
}

template <class T>
void vector<T>::pop_back() {
	if (is_big()) {
		change();
		_obj.big._data.get()[size() - 1].~T();
	}
	else {
		_obj.small._data[size() - 1].~T();
	}
	_size--;
}

template <class T>
void vector<T>::swap(vector& a) noexcept {
	using std::swap;
	swap(a._obj, _obj);
	swap(a._size, _size);
	swap(a._is_big, _is_big);
	swap(a.function_get, function_get);
	swap(a.function_get_const, function_get_const);
}

template <class T>
size_t vector<T>::get_new_capacity(size_t n) {
	if (n == 0) {
		return 8;
	}
	return n * 3 / 2;
}

template <class T>
void vector<T>::set_capacity(size_t capacity) {
	if (is_big()) {
		T* temp = copy_data(capacity, _obj.big._data.get(), size());
		delete_all();
		_obj.big._data.reset(temp);
		_obj.big._capacity = capacity;
	}
	else {
		if (capacity > size_small) {
			transform_to_big(capacity);
		}
	}
}

template <class T>
void vector<T>::delete_all() {
	if (is_big()) {
		if (_obj.big._capacity > 0) {
			delete_data(_obj.big._data.get(), size());
		}
	}
	else {
		delete_data(_obj.small._data, size());
	}
}

template <class T>
void vector<T>::change() {
	if (!_obj.big._data.unique()) {
		_obj.big._data.reset(copy_data(size(), _obj.big._data.get(), size()));
	}
}

template <class T>
bool vector<T>::is_big() const {
	return _is_big;
}

template <class T>
void vector<T>::transform_to_big(size_t n) {
	_is_big = true;
	function_get = &vector<T>::get_big_ind;
	function_get_const = &vector<T>::get_big_ind_c;
	T* temp = copy_data(n, _obj.small._data, size());
	delete_data(_obj.small._data, size());
	_obj.big._data.reset(temp);
	_obj.big._capacity = n;
}

template <class T>
T* copy_data(size_t capacity, T* data, size_t size,
             typename std::enable_if<!std::is_trivially_constructible<T>::value>::type* = nullptr) {
	T* temp = static_cast<T*>(operator new(capacity * sizeof(T)));
	for (size_t i = 0; i < size; i++) {
		try {
			new(&temp[i]) T(data[i]);
		}
		catch (...) {
			delete_data(temp, i);
			operator delete(temp);
			throw;
		}
	}
	return temp;
}

template <class T>
T* copy_data(size_t capacity, T* data, size_t size,
             typename std::enable_if<std::is_trivially_constructible<T>::value>::type* = nullptr) {
	T* temp = static_cast<T*>(operator new(capacity * sizeof(T)));
	memcpy(temp, data, sizeof(T) * size);
	return temp;
}

template <class T>
void delete_data(T* data, size_t size,
                 typename std::enable_if<!std::is_trivially_destructible<T>::value>::type* = nullptr) {
	for (size_t j = 0; j < size; j++) {
		data[j].~T();
	}
}

template <class T>
void delete_data(T* data, size_t size,
                 typename std::enable_if<std::is_trivially_destructible<T>::value>::type* = nullptr) { }
#endif // VECTOR
